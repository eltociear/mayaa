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
package org.seasar.mayaa.impl.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.seasar.mayaa.FactoryFactory;
import org.seasar.mayaa.cycle.scope.ApplicationScope;
import org.seasar.mayaa.impl.ParameterAwareImpl;
import org.seasar.mayaa.impl.cycle.CycleUtil;
import org.seasar.mayaa.impl.util.StringUtil;
import org.seasar.mayaa.source.SourceDescriptor;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class ApplicationSourceDescriptor
        extends ParameterAwareImpl implements SourceDescriptor {

    private static final long serialVersionUID = -2775274363708858237L;

    public static final String WEB_INF = "/WEB-INF";

    private String _root = "";

    private File _file;
    private ApplicationScope _application;

    private boolean _denyWebInf = true;

    // use while building ServiceProvider.
    public void setApplicationScope(ApplicationScope application) {
        if (application == null) {
            throw new IllegalArgumentException();
        }
        _application = application;
    }

    public ApplicationScope getApplicationScope() {
        if (_application == null) {
            _application = FactoryFactory.getApplicationScope();
        }
        return _application;
    }

    public void setDenyWebInf(boolean denyWebInf) {
        _denyWebInf = denyWebInf;
    }

    // use at InternalApplicationSourceScanner.FileToSourceIterator
    public void setFile(File file) {
        _file = file;
    }

    public File getFile() {
        exists();
        return _file;
    }

    public void setRoot(String root) {
        _root = StringUtil.preparePath(root);
    }

    public String getRoot() {
        return _root;
    }

    public void setSystemID(String systemID) {
        if (_denyWebInf) {
            if (systemID != null && systemID.indexOf(WEB_INF) != -1) {
                throw new ForbiddenPathException(systemID);
            }
        }
        super.setSystemID(StringUtil.preparePath(systemID));
    }

    public boolean exists() {
        if (_file == null) {
            String realPath =
                getApplicationScope().getRealPath(_root + getSystemID());
            if (StringUtil.hasValue(realPath)) {
                 File file = new File(realPath);
                 if (file.exists()) {
                     _file = file;
                 }
            }
        }
        return (_file != null) && _file.exists();
    }

    public InputStream getInputStream() {
        if (exists()) {
            if (_file.isFile()) {
                try {
                    return new FileInputStream(_file);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (_file.isDirectory()) {
                CycleUtil.getServiceCycle().redirect(_file.getName() + "/");
            }
        }
        return null;
    }

    public Date getTimestamp() {
        if (exists()) {
            return new Date(_file.lastModified());
        }
        return new Date(0);
    }

}
