package org.siemac.metamac.statistical.resources.core.utils.mocks.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.statistical.resources.core.MetamacReflectionUtils;

/*
 * All Mock factories should extend from this class.
 * The MetamacMock Annotation will look for factories that extend from this one
 * and will look for the mock id in every and each one of them
 * Each mock factory will produce mocks using the lazy initialization pattern, and must ensure
 * that relations among mocks get resolved at the end of the method that produces the mock.
 * so cycles are prevented
 */
public abstract class MockFactory<EntityMock> {

    protected static final String NAME_FIELD_SUFFIX = "_NAME";

    @SuppressWarnings("unchecked")
    protected EntityMock getMock(String id) {
        String methodName = getMethodNameFromId(id);
        try {
            Method method = getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            Object obj = method.invoke(this);
            return (EntityMock) obj;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error creating mock " + id + " accesing to method " + methodName, e);
        }
    }

    protected List<EntityMock> getMocks(String... ids) {
        List<EntityMock> list = new ArrayList<EntityMock>();
        for (String id : ids) {
            list.add(getMock(id));
        }
        return list;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void registerMocks(Object mockFactoryObj, Class modelClass, Map mocks) {
        Class factoryClass = mockFactoryObj.getClass();
        Set<Field> queryFields = MetamacReflectionUtils.getDeclaredFieldsWithType(factoryClass, modelClass);
        for (Field field : queryFields) {
            String nameFieldValue = null;
            Object mockFieldValue = null;
            try {
                nameFieldValue = (String) MetamacReflectionUtils.getDeclaredFieldValue(mockFactoryObj, field.getName() + NAME_FIELD_SUFFIX);
            } catch (Exception e) {
                throw new IllegalStateException("An error ocurred loading value of name field for mock " + field.getName());
            }
            try {
                mockFieldValue = modelClass.cast(MetamacReflectionUtils.getDeclaredFieldValue(mockFactoryObj, field.getName()));
            } catch (Exception e) {
                throw new IllegalStateException("An error ocurred loading value of mock field for mock " + field.getName());
            }
            mocks.put(nameFieldValue, mockFieldValue);
        }
    }

    private String getMethodNameFromId(String id) {
        String[] sections = id.split("_");
        StringBuilder builder = new StringBuilder("get");
        for (String section : sections) {
            String firstUpper = toProperCase(section);
            builder.append(firstUpper);
        }
        return builder.toString();
    }

    private String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
