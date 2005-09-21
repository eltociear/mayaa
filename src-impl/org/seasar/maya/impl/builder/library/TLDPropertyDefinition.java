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
package org.seasar.maya.impl.builder.library;

import org.seasar.maya.engine.specification.NodeAttribute;
import org.seasar.maya.engine.specification.QName;
import org.seasar.maya.engine.specification.SpecificationNode;
import org.seasar.maya.impl.engine.processor.ProcessorPropertyImpl;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class TLDPropertyDefinition extends PropertyDefinitionImpl {
    
    public Object createProcessorProperty(SpecificationNode injected) {
    	if(injected == null) {
    		throw new IllegalArgumentException();
    	}
        QName qName = getQName(injected);
        NodeAttribute attribute = injected.getAttribute(qName);
        if(attribute != null) {
            Class propertyType = getPropertyType();
            if(propertyType == null) {
                // real property not found on the tag.
                return null;
            }
            String value = attribute.getValue();
            return new ProcessorPropertyImpl(attribute, value, propertyType);
        } else if(isRequired()) {
            String processorName = getProcessorDefinition().getName();
            throw new NoRequiredPropertyException(processorName, qName);
        }
        return null;
    }
    
}