/*
 * Copyright (c) 2004-2005 the Seasar Project and the Others.
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
package org.seasar.maya.standard.engine.processor.jstl.xml;

import javax.servlet.jsp.PageContext;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.seasar.maya.engine.processor.ProcessorProperty;
import org.seasar.maya.engine.specification.QName;
import org.seasar.maya.standard.util.AttributeScopeUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author maruo_syunsuke
 */
public class OutProcessor extends org.seasar.maya.standard.engine.processor.jstl.core.OutProcessor {
    public void setSelect(final String select) {
        super.setValue(new ProcessorProperty() {
            public QName getQName() {
                return null;
            }
            public String getPrefix() {
                return null;
            }
            public String getLiteral() {
                return getDocumentVariantName(select) ;
            }
            public boolean isDynamic() {
                return true;
            }
            public Object getValue(PageContext context) {
                return getNode(context,select).getNodeValue() ;
            }
            public void setValue(PageContext context, Object value) {
            }
        });
    }
    private Document getDocument(PageContext context, String docVarName) {
        int scopeSeparaterIndex = docVarName.indexOf(':');

        if( scopeSeparaterIndex >= 0 ){
            String scopeName     = docVarName.substring(0,scopeSeparaterIndex);
            String attributeName = docVarName.substring(scopeSeparaterIndex+1);
            int    scopeNumber   = AttributeScopeUtil.convertScopeStringToInt(scopeName);
            return (Document)context.getAttribute(attributeName,scopeNumber);
        }else{
            String attributeName = docVarName;
            return (Document)context.getAttribute(attributeName);
        }
    }
    private String getDocumentVariantName(String select){
        int firstSeparateIndex  = select.indexOf('/');
        String docVarName       = select.substring(0,firstSeparateIndex);
        return docVarName ;
    }
    private Node getNode(PageContext context, String select) {
        int firstSeparateIndex  = select.indexOf('/');
        String docVarName       = getDocumentVariantName(select);
        Document document       = getDocument(context, docVarName);
        String xpathString      = docVarName.substring(firstSeparateIndex+1);
        Node node;
        try {
            node = XPathAPI.selectSingleNode(document,xpathString);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return node;
    }
}
