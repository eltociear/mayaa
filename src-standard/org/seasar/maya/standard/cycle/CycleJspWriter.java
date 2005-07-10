/*
 * Copyright (c) 2004-2005 the Seasar Project and the Others.
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
package org.seasar.maya.standard.cycle;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import org.seasar.maya.cycle.CycleWriter;
import org.seasar.maya.standard.CONST_STANDARD;

/**
 * @author Masataka Kurihara (Gluegent, Inc.)
 */
public class CycleJspWriter extends JspWriter implements CONST_STANDARD  {
    
    private CycleWriter _writer;
    
    public CycleJspWriter(CycleWriter writer) {
        super(DEFAULT_BUFFER, false);
        if(writer == null) {
            throw new IllegalArgumentException();
        }
        _writer = writer;
    }

    public void write(char cbuf[], int off, int len) throws IOException {
        if(len == 0) {
            return;
        }
        _writer.write(cbuf, off, len);
    }

    public final void clear() throws IOException {
        _writer.clearBuffer();
    }

    public void clearBuffer() throws IOException {
        _writer.clearBuffer();
    }

    public void close() throws IOException {
    }

    public void flush() throws IOException {
        _writer.flush();
    }

    public int getRemaining() {
        return 0;
    }

    public void newLine() throws IOException {
        write(LINE_SEPARATOR);
    }

    // ----------------------------------------------------------
    public void print(boolean b) throws IOException {
        write(Boolean.toString(b));
    }

    public void print(char c) throws IOException {
        write(Character.toString(c));
    }

    public void print(char c[]) throws IOException {
        write(c);
    }

    public void print(double d) throws IOException {
        write(Double.toString(d));
    }

    public void print(float f) throws IOException {
        write(Float.toString(f));
    }

    public void print(int i) throws IOException {
        write(Integer.toString(i));
    }

    public void print(long l) throws IOException {
        write(Long.toString(l));
    }

    public void print(Object o) throws IOException {
        write(String.valueOf(o));
    }

    public void print(String s) throws IOException {
        write(s);
    }

    // ----------------------------------------------------------
    public void println() throws IOException {
        newLine();
    }

    public void println(boolean b) throws IOException {
        print(b);
        newLine();
    }

    public void println(char c) throws IOException {
        print(c);
        newLine();
    }

    public void println(char c[]) throws IOException {
        print(c);
        newLine();
    }

    public void println(double d) throws IOException {
        print(d);
        newLine();
    }

    public void println(float f) throws IOException {
        print(f);
        newLine();
    }

    public void println(int i) throws IOException {
        print(i);
        newLine();
    }

    public void println(long l) throws IOException {
        print(l);
        newLine();
    }

    public void println(Object o) throws IOException {
        print(o);
        newLine();
    }

    public void println(String s) throws IOException {
        print(s);
        newLine();
    }

}