/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.mayaa.impl.cycle.script.rhino;

import java.util.Iterator;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.JavaAdapter;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.WrapFactory;
import org.seasar.mayaa.PositionAware;
import org.seasar.mayaa.cycle.ServiceCycle;
import org.seasar.mayaa.cycle.scope.ApplicationScope;
import org.seasar.mayaa.cycle.scope.AttributeScope;
import org.seasar.mayaa.cycle.script.CompiledScript;
import org.seasar.mayaa.impl.IllegalParameterValueException;
import org.seasar.mayaa.impl.cycle.CycleUtil;
import org.seasar.mayaa.impl.cycle.script.AbstractScriptEnvironment;
import org.seasar.mayaa.impl.cycle.script.LiteralScript;
import org.seasar.mayaa.impl.cycle.script.ScriptBlock;
import org.seasar.mayaa.impl.util.ObjectUtil;
import org.seasar.mayaa.impl.util.StringUtil;
import org.seasar.mayaa.source.SourceDescriptor;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ScriptEnvironmentImpl extends AbstractScriptEnvironment {

    private static Scriptable _standardObjects;
    private static ThreadLocal _parent = new ThreadLocal();

    private WrapFactory _wrap;

    protected CompiledScript compile(
            ScriptBlock scriptBlock, PositionAware position) {
        if(scriptBlock == null) {
            throw new IllegalArgumentException();
        }
        String text = scriptBlock.getBlockString();
        if(scriptBlock.isLiteral()) {
            return new LiteralScript(text);
        }
        return new TextCompiledScriptImpl(text, _wrap, position);
    }

    // ScriptEnvironment implements ----------------------------------

    protected String getSourceMimeType(SourceDescriptor source) {
        if(source == null) {
            throw new IllegalArgumentException();
        }
        String systemID = source.getSystemID();
        ServiceCycle cycle = CycleUtil.getServiceCycle();
        ApplicationScope application = cycle.getApplicationScope();
        return application.getMimeType(systemID);
    }

    public CompiledScript compile(
            SourceDescriptor source, String encoding) {
        if(source == null) {
            throw new IllegalArgumentException();
        }
        return new SourceCompiledScriptImpl(source, encoding, _wrap);
    }

    protected Scriptable getStandardObjects() {
        if(_standardObjects == null) {
            Context cx = Context.enter();
            _standardObjects = cx.initStandardObjects(null, true);
            Context.exit();
        }
        return _standardObjects;
    }

    public void initScope() {
        ServiceCycle cycle = CycleUtil.getServiceCycle();
        cycle.setPageScope(null);
        _parent.set(null);
    }

    public void startScope(Map variables) {
        ServiceCycle cycle = CycleUtil.getServiceCycle();
        AttributeScope scope = cycle.getPageScope();
        Scriptable parent;
        if(scope == null) {
            parent = (Scriptable)_parent.get();
            if(parent == null) {
                Context cx = Context.enter();
                if(_wrap != null) {
                    cx.setWrapFactory(_wrap);
                }
                Scriptable standard = getStandardObjects();
                parent = cx.getWrapFactory().wrapAsJavaObject(
                        cx, standard, cycle, ServiceCycle.class);
                Context.exit();
                _parent.set(parent);
            }
        } else if(scope instanceof PageAttributeScope) {
            parent = (PageAttributeScope)scope;
        } else {
            throw new IllegalStateException();
        }
        PageAttributeScope pageScope = new PageAttributeScope();
        pageScope.setParentScope(parent);
        if(variables != null) {
            for(Iterator it = variables.keySet().iterator(); it.hasNext(); ) {
                String name = it.next().toString();
                Object value = variables.get(name);
                Object variable = Context.javaToJS(value, pageScope);
                ScriptableObject.putProperty(pageScope, name, variable);
            }
        }
        cycle.setPageScope(pageScope);
    }

    public void endScope() {
        ServiceCycle cycle = CycleUtil.getServiceCycle();
        AttributeScope scope = cycle.getPageScope();
        if(scope instanceof PageAttributeScope) {
            PageAttributeScope pageScope = (PageAttributeScope)scope;
            Scriptable current = pageScope.getParentScope();
            if(current instanceof PageAttributeScope) {
                PageAttributeScope parentScope = (PageAttributeScope)current;
                cycle.setPageScope(parentScope);
                return;
            } else if(current != null) {
                cycle.setPageScope(null);
                return;
            }
        }
        throw new IllegalStateException();
    }

    public Object convertFromScriptObject(Object scriptObject) {
        Object result = JavaAdapter.convertResult(scriptObject, Object.class);

        if (result instanceof NativeArray) {
            NativeArray jsArray = (NativeArray) result;
            int length = (int) jsArray.getLength();
            Object[] array = new Object[length];
            for (int i = 0; i < length; i++) {
                array[i] = jsArray.get(i, null);
            }
            result = array;
        }

        return result;
    }

    // Parameterizable implements ------------------------------------

    public void setParameter(String name, String value) {
        if("wrapFactory".equals(name)) {
            if(StringUtil.isEmpty(value)) {
                throw new IllegalParameterValueException(getClass(), name);
            }
            Class clazz = ObjectUtil.loadClass(value, WrapFactory.class);
            _wrap = (WrapFactory)ObjectUtil.newInstance(clazz);
        } else if("blockSign".equals(name)) {
            if(StringUtil.isEmpty(value)) {
                throw new IllegalParameterValueException(getClass(), name);
            }
            setBlockSign(value);
        }
        super.setParameter(name, value);
    }

}
