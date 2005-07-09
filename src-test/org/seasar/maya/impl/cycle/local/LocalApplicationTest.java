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
package org.seasar.maya.impl.cycle.local;

import junit.framework.TestCase;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class LocalApplicationTest extends TestCase {

    public LocalApplicationTest(String name) {
        super(name);
    }
    
    public void testGetScopeName() {
        LocalApplication application = new LocalApplication();
        assertEquals("application", application.getScopeName());
    }
    
    public void testGetAttribute() {
        LocalApplication application = new LocalApplication();
        application.setAttribute("test", "test attr");
        assertEquals("test attr", application.getAttribute("test"));
        application.setAttribute("test", null);
        assertNull(application.getAttribute("test"));
    }
    
}