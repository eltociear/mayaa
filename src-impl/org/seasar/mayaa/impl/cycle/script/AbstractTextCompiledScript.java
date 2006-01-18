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
package org.seasar.mayaa.impl.cycle.script;

import org.seasar.mayaa.cycle.script.CompiledScript;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public abstract class AbstractTextCompiledScript
        implements CompiledScript {

    private static final Class[] ZERO_ARGS_TYPE = new Class[0];

    private String _text;
    private Class _expectedClass = Object.class;
    private Class[] _methodArgClasses;

    public AbstractTextCompiledScript(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }
        _text = text;
    }

    protected String getText() {
        return _text;
    }

    public void setExpectedClass(Class expectedClass) {
        if (expectedClass == null) {
            expectedClass = Object.class;
        }
        _expectedClass = expectedClass;
    }

    public Class getExpectedClass() {
        return _expectedClass;
    }

    public void setMethodArgClasses(Class[] methodArgClasses) {
        if (methodArgClasses == null) {
            methodArgClasses = ZERO_ARGS_TYPE;
        }
        _methodArgClasses = methodArgClasses;
    }

    public Class[] getMethodArgClasses() {
        return _methodArgClasses;
    }

    public boolean isLiteral() {
        return false;
    }

    public boolean isReadOnly() {
        return false;
    }

    public String getScriptText() {
        return ScriptUtil.getBlockSignedText(_text);
    }

}
