package org.siemac.metamac.statistical.resources.core.base.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;


public class ProcStatusUtils {
    public static String procStatusEnumToString(StatisticalResourceProcStatusEnum... possibleProcStatus) {
        return join(possibleProcStatus, ", ");
    }
    
    private static String join(StatisticalResourceProcStatusEnum[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }
    
    private static String join(StatisticalResourceProcStatusEnum[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = StringUtils.EMPTY;
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return StringUtils.EMPTY;
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length())
                        + separator.length());

        StrBuilder buf = new StrBuilder(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i].getName());
            }
        }
        return buf.toString();
    }
}
