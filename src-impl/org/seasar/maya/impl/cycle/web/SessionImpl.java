/*
 * Copyright (c) 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Seasar Software License, v1.1 (aka "the License");
 * you may not use this file except in compliance with the License which
 * accompanies this distribution, and is available at
 *
 *     http://www.seasar.org/SEASAR-LICENSE.TXT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.seasar.maya.impl.cycle.web;

import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.seasar.maya.cycle.ServiceCycle;
import org.seasar.maya.cycle.Session;
import org.seasar.maya.impl.provider.UnsupportedParameterException;
import org.seasar.maya.impl.util.ScriptUtil;
import org.seasar.maya.impl.util.StringUtil;
import org.seasar.maya.impl.util.collection.EnumerationIterator;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class SessionImpl implements Session {

    private static final long serialVersionUID = -3211729351966533995L;

    private HttpSession _httpSession;
    
    private void check() {
        if(_httpSession == null) {
            throw new IllegalStateException();
        }
    }
    
    public void setUnderlyingObject(Object context) {
        if(context == null || context instanceof HttpSession == false) {
            throw new IllegalArgumentException();
        }
        _httpSession = (HttpSession)context;
    }

    public Object getUnderlyingObject() {
        check();
        return _httpSession;
    }
    
    public String getSessionID() {
        check();
        return _httpSession.getId();
    }

    public String getScopeName() {
        return ServiceCycle.SCOPE_SESSION;
    }
    
    public Iterator iterateAttributeNames() {
        check();
        return EnumerationIterator.getInstance(_httpSession.getAttributeNames());
    }

    public boolean hasAttribute(String name) {
        check();
        if(StringUtil.isEmpty(name)) {
            return false;
        }
        for(Enumeration e = _httpSession.getAttributeNames();
        		e.hasMoreElements(); ) {
			if(e.nextElement().equals(name)) {
				return true;
			}
		}
		return false;
	}

    public Object getAttribute(String name) {
        check();
        if(StringUtil.isEmpty(name)) {
            return null;
        }
        return ScriptUtil.convertFromScriptObject(
                _httpSession.getAttribute(name));
    }

    public boolean isAttributeWritable() {
		return true;
	}

    public void setAttribute(String name, Object attribute) {
        check();
        if(StringUtil.isEmpty(name)) {
            return;
        }
        _httpSession.setAttribute(name, attribute);
    }
    
    public void removeAttribute(String name) {
        check();
        if(StringUtil.isEmpty(name)) {
            return;
        }
        _httpSession.removeAttribute(name);
    }
    
    public void setParameter(String name, String value) {
        throw new UnsupportedParameterException(getClass(), name);
    }

}
