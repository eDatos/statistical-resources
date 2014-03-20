package org.siemac.metamac.statistical_resources.rest.internal.service.utils;

public class LookupUtil {

    public static <E extends Enum<E>> E lookup(Class<E> clazz, String id) {
        E result;
        try {
            result = Enum.valueOf(clazz, id);
        } catch (IllegalArgumentException exception) {
            throw new RuntimeException("Invalid value for enum " + clazz.getSimpleName() + ": " + id);
        }
        return result;
    }
}