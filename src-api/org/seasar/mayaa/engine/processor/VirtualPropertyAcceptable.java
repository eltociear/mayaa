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
package org.seasar.mayaa.engine.processor;

import org.seasar.mayaa.engine.specification.PrefixAwareName;

/**
 * あらかじめ、MLD（Mayaa Library Definition）ファイルに記述されているが、
 * プロセッサに固有のプロパティが無い場合（バーチャルプロパティ）
 * の受け入れを行うインターフェイス。
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public interface VirtualPropertyAcceptable
        extends ProcessorTreeWalker {

    /**
     * バーチャルプロパティへの値設定。
     * @param name プロパティ名。
     * @param property プロパティ値。
     */
    void addVirtualProperty(PrefixAwareName name, Object property);

}
