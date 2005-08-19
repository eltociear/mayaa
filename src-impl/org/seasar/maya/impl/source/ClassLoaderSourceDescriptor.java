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
package org.seasar.maya.impl.source;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.seasar.maya.impl.util.StringUtil;
import org.seasar.maya.source.SourceDescriptor;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ClassLoaderSourceDescriptor implements SourceDescriptor {
    
	private static final long serialVersionUID = -4924522601395047024L;

    private String _root;
    private Class _neighbor;
    private String _systemID;
    private InputStream _inputStream;
    private Map _attributes;
    private Date _timestamp;

    public ClassLoaderSourceDescriptor(String root, String systemID, Class neighbor) {
        _root = StringUtil.preparePath(root);
        _systemID = StringUtil.preparePath(systemID);
        _neighbor = neighbor;
    }

    protected String getRoot() {
        return _root;
    }

    public String getSystemID() {
        return _systemID;
    }
    
    public boolean exists() {
        String path = (_root + _systemID).substring(1);
        if(_neighbor != null) {
            _inputStream = _neighbor.getResourceAsStream(path);
        }
        if (_inputStream == null) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            _inputStream = loader.getResourceAsStream(path);
        }
        return _inputStream != null;
    }

    public InputStream getInputStream() {
        if(exists()) {
            return _inputStream;
        }
        return null;
    }
    
    public void setTimestamp(Date timestamp) {
    	if(timestamp == null) {
    		throw new IllegalArgumentException();
    	}
    	_timestamp = timestamp;
    }
    
    public Date getTimestamp() {
    	if(_timestamp != null) {
    		return _timestamp;
    	}
        return new Date(0);
    }

    public void setAttribute(String name, String value) {
        if(StringUtil.isEmpty(name)) {
            throw new IllegalArgumentException();
        }
        if(_attributes == null) {
            _attributes = new HashMap();
        }
        _attributes.put(name, value);
    }
    
    public String getAttribute(String name) {
        if(_attributes == null) {
            return null;
        }
        return (String)_attributes.get(name);
    }

}
