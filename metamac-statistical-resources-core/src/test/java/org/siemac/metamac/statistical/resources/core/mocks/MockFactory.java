package org.siemac.metamac.statistical.resources.core.mocks;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.statistical.resources.core.MetamacReflectionUtils;

public abstract class MockFactory<Model> {

    protected static final String NAME_FIELD_SUFFIX = "_NAME";

    public abstract Model getMock(String id);

    public List<Model> getMocks(String... ids) {
        List<Model> list = new ArrayList<Model>();
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

}
