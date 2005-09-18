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
package org.seasar.maya.impl.provider.factory;

import org.seasar.maya.builder.TemplateBuilder;
import org.seasar.maya.impl.util.XMLUtil;
import org.seasar.maya.provider.Parameterizable;
import org.xml.sax.Attributes;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class TemplateBuilderTagHandler 
        extends AbstractParameterizableTagHandler {
    
    private ServiceTagHandler _parent;
    private TemplateBuilder _templateBuilder;
    
    public TemplateBuilderTagHandler(ServiceTagHandler parent) {
        super("templateBuilder");
        if(parent == null) {
            throw new IllegalArgumentException();
        }
        _parent = parent;
        putHandler(new ResolverTagHandler(this));
    }
    
    protected void start(Attributes attributes) {
        _templateBuilder = (TemplateBuilder)XMLUtil.getObjectValue(
                attributes, "class", null, TemplateBuilder.class);
        _parent.getServiceProvider().setTemplateBuilder(_templateBuilder);
    }
    
    protected void end(String body) {
        if(_templateBuilder == null) {
            throw new IllegalStateException();
        }
        _templateBuilder = null;
    }
    
    public TemplateBuilder getTemplateBuilder() {
        if(_templateBuilder == null) {
            throw new IllegalStateException();
        }
        return _templateBuilder;
    }
    
    public Parameterizable getParameterizable() {
        return getTemplateBuilder();
    }

}
