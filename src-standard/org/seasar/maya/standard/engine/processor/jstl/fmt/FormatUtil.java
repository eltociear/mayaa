package org.seasar.maya.standard.engine.processor.jstl.fmt;

import java.util.Locale;

import org.seasar.maya.impl.util.StringUtil;

public class FormatUtil {
    /**
     * Locale��������p�[�X���ALocale�I�u�W�F�N�g��Ԃ��B
     * 
     * @param localeString
     *            Locale������
     * @return LocaleObject
     * @throws IllegalArgumentException
     *             �`���s���̏ꍇ
     */
    public static Locale parseLocale(String localeString) {
        int index = -1;
        String language;
        String country;

        if (( index = localeString.indexOf('-')) >= 0 ||
            ( index = localeString.indexOf('_')) >= 0 ){
            language = localeString.substring(0, index);
            country  = localeString.substring(index+1);
        } else {
            language = localeString;
            country = null;
        }

        if (StringUtil.isEmpty(language)
                || (country != null && country.length() == 0)) {
            throw new IllegalArgumentException("invalid Locale: " + localeString);
        }

        if (country == null) {
            country = "";
        }

        return new Locale(language, country);
    }
}