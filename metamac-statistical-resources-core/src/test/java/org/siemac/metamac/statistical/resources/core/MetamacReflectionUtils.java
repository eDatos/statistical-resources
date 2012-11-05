package org.siemac.metamac.statistical.resources.core;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;


public class MetamacReflectionUtils {
    
    public static Set<Field> getDeclaredFieldsWithType(Class clazz, Class typeClass) {
        Set<Field> fields = new HashSet<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (typeClass.equals(field.getGenericType())) {
                fields.add(field);
            }
        }
        return fields;
    }
    
    
    public static Object getDeclaredStaticFieldValue(Class clazz, String name) throws Exception {
        Field field = clazz.getDeclaredField(name);
        if (field.isAccessible()) {
            return field.get(null);
        } else {
            field.setAccessible(true);
            Object value = field.get(null);
            field.setAccessible(false);
            return value;
        }
    }
    
    
}
