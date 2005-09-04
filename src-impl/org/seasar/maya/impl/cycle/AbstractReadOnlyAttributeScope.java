/*
 * Copyright (c) 2004-2005 the Seasar Foundation and the Others.
 * 
 * Licensed under the Seasar Software License, v1.1 (aka "the License"); you may
 * not use this file except in compliance with the License which accompanies
 * this distribution, and is available at
 * 
 *     http://www.seasar.org/SEASAR-LICENSE.TXT
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.seasar.maya.impl.cycle;

import org.seasar.maya.cycle.AttributeScope;
import org.seasar.maya.impl.util.ScopeNotWritableException;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public abstract class AbstractReadOnlyAttributeScope 
        implements AttributeScope {

    public boolean isAttributeWritable() {
        return false;
    }

    public void removeAttribute(String name) {
        throw new ScopeNotWritableException(getScopeName());
    }

    public void setAttribute(String name, Object attribute) {
        throw new ScopeNotWritableException(getScopeName());
    }

    public void setParameter(String name, String value) {
        throw new UnsupportedOperationException();
    }
    
}