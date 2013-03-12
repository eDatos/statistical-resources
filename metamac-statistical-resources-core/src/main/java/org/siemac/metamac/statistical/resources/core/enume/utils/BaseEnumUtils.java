package org.siemac.metamac.statistical.resources.core.enume.utils;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;

public abstract class BaseEnumUtils {

    protected static String enumToString(Object... possibleProcStatus) {
        return join(possibleProcStatus, ", ");
    }
    
    protected static String join(Object[] enumArray, String separator) {
        if (enumArray == null) {
            return null;
        }
        return join(enumArray, separator, 0, enumArray.length);
    }

    private static String join(Object[] enumArray, String separator, int startIndex, int endIndex) {
        if (enumArray == null) {
            return null;
        }
        if (separator == null) {
            separator = StringUtils.EMPTY;
        }

        // endIndex - startIndex > 0: Len = NofStrings *(len(firstString) + len(separator))
        // (Assuming that all Strings are roughly equally long)
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return StringUtils.EMPTY;
        }

        bufSize *= ((enumArray[startIndex] == null ? 16 : enumArray[startIndex].toString().length()) + separator.length());

        StrBuilder buf = new StrBuilder(bufSize);

        try {
            for (int i = startIndex; i < endIndex; i++) {
                if (i > startIndex) {
                    buf.append(separator);
                }
                if (enumArray[i] != null) {
                    Method method = enumArray[i].getClass().getDeclaredMethod("getName");
                    method.setAccessible(true);
                    String name = (String) method.invoke(enumArray[i]);
                    buf.append(name);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return buf.toString();
    }
}
