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
package org.seasar.maya.source;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import org.seasar.maya.provider.Parameterizable;

/**
 * テンプレートファイルや設定XMLファイルのディスクリプタ。
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface SourceDescriptor extends Serializable, Parameterizable {
    
    /**
     * ソースパス中のSystemID。
     * @return SystemID。
     */
    String getSystemID();

    /**
     * ソースが存在するかどうかを取得する。
     * @return ファイルが存在すればtrue。無ければfalse。
     */
    boolean exists();

    /**
     * ファイルのインプットストリームを取得する。
     * @return ストリーム。もしファイルが無い場合は、null。
     */
    InputStream getInputStream();

    /**
     * ファイルの日付を取得する。
     * @return ファイルの最終更新日付。ファイルが無い場合は「new Date(0)」を返す。
     */
    Date getTimestamp();

    /**
     * ソースの付加属性の取得。
     * @param name 属性名。
     * @return 属性値。
     */
    String getAttribute(String name);
    
}
