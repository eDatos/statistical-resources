package org.siemac.metamac.statistical.resources.core;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

public class MetamacReflectionUtils {

    @SuppressWarnings("rawtypes")
    public static Set<Field> getDeclaredFieldsWithType(Class clazz, Class typeClass) {
        Set<Field> fields = new HashSet<Field>();
        for (Field field : clazz.getDeclaredFields()) {
            if (typeClass.equals(field.getGenericType())) {
                fields.add(field);
            }
        }
        return fields;
    }

    @SuppressWarnings("rawtypes")
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

    public static Object getDeclaredFieldValue(Object obj, String name) throws Exception {
        Field field = obj.getClass().getDeclaredField(name);
        return makeFieldAccesible(obj, field);
    }
    
    public static Object getFieldValue(Object obj, String name) throws Exception {
        Field field = findField(obj.getClass(), name);
        return makeFieldAccesible(obj, field);
    }

    public static Object getComplexFieldValue(Object obj, String name) throws Exception {
        String[] complexName = StringUtils.split(name, ".");
        Field field = null;
        Class<? extends Object> clazz = obj.getClass();
        Object finalObj = obj;

        for (int i = 0; i < complexName.length; i++) {
            field = findField(clazz, complexName[i]);
            clazz = field.getType();
            if (i < complexName.length -1) {
                finalObj = getFieldValue(finalObj, complexName[i]);
            }
        }
        return makeFieldAccesible(finalObj, field);
    }

    private static Object makeFieldAccesible(Object obj, Field field) throws IllegalAccessException {
        if (field.isAccessible()) {
            return field.get(obj);
        } else {
            field.setAccessible(true);
            Object value = field.get(obj);
            field.setAccessible(false);
            return value;
        }
    }

    @SuppressWarnings("rawtypes")
    public static List<Field> getAllFields(Class clazz) {
        final List<Field> fields = new ArrayList<Field>();
        ReflectionUtils.doWithFields(clazz, new FieldCallback() {

            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                fields.add(field);
            }
        });
        return fields;
    }

    @SuppressWarnings("rawtypes")
    private static Field findField(Class clazz, final String name) throws NoSuchFieldException {
        try {
            return ReflectionUtils.findField(clazz, name);
        } catch (Exception e) {
            throw new NoSuchFieldException(name);
        }
    }

    public static Long retrieveObjectId(Object object) {
        try {
            Method method = object.getClass().getMethod("getId");
            method.setAccessible(true);
            Long objectId = (Long) method.invoke(object);
            return objectId;
        } catch (Exception e) {
            fail("The object doesn't have getId method");
            return null;
        }
    }
}
