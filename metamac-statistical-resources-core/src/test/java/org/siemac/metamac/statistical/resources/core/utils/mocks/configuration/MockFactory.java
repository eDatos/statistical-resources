package org.siemac.metamac.statistical.resources.core.utils.mocks.configuration;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arte.libs.lang.ObjectUtils;

/*
 * All Mock factories should extend from this class.
 * The MetamacMock Annotation will look for factories that extend from this one
 * and will look for the mock id in every and each one of them
 * Each mock factory will produce mocks using the lazy initialization pattern, and must ensure
 * that relations among mocks get resolved at the end of the method that produces the mock.
 * so cycles are prevented
 */
public abstract class MockFactory<EntityMock> {

    protected static final String              NAME_FIELD_SUFFIX = "_NAME";

    private static Map<String, MockDescriptor> mocks             = new HashMap<String, MockDescriptor>();

    @SuppressWarnings("unchecked")
    public EntityMock retrieveMock(String mockName) {
        EntityMock templateInstance;
        try {
            templateInstance = (EntityMock) (getRealClassInTemplateClassParameter()).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error getting instance from generic", e);
        }
        EntityMock mock = getMock(mockName);
        if (mock != null) {
            return ObjectUtils.deepCopyInParent(mock, templateInstance);
        }
        return null;
    }

    public List<EntityMock> retrieveMocks(String... names) {
        List<EntityMock> list = new ArrayList<EntityMock>();
        for (String name : names) {
            list.add(retrieveMock(name));
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    protected EntityMock getMock(String id) {
        MockDescriptor mockDesc = getMockWithDependencies(id);
        if (mockDesc != null) {
            return (EntityMock) (mockDesc.getTargetMock());
        }
        return null;
    }

    protected MockDescriptor getMockWithDependencies(String id) {
        if (mocks.get(id) == null) {
            MockDescriptor mockDesc = getMocksInMethod(id);
            if (mockDesc != null) {
                mocks.put(id, mockDesc);
            }
        }
        return mocks.get(id);
    }

    public void registerMock(String id, Object mock) {
        mocks.put(id, new MockDescriptor(mock));
    }

    private MockDescriptor getMocksInMethod(String id) {
        String methodName = getMethodNameFromId(id);
        try {
            Method method = getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            Object obj = method.invoke(this);
            if (obj instanceof MockDescriptor) {
                return (MockDescriptor) obj;
            } else if (obj instanceof List) {
                throw new RuntimeException("Mock con retorno invalido " + id);
            }
            return new MockDescriptor(obj);
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

    // @SuppressWarnings({"rawtypes", "unchecked"})
    // public static void registerMocks(Object mockFactoryObj, Class modelClass, Map mocks) {
    // Class factoryClass = mockFactoryObj.getClass();
    // Set<Field> queryFields = MetamacReflectionUtils.getDeclaredFieldsWithType(factoryClass, modelClass);
    // for (Field field : queryFields) {
    // String nameFieldValue = null;
    // Object mockFieldValue = null;
    // try {
    // nameFieldValue = (String) MetamacReflectionUtils.getDeclaredFieldValue(mockFactoryObj, field.getName() + NAME_FIELD_SUFFIX);
    // } catch (Exception e) {
    // throw new IllegalStateException("An error ocurred loading value of name field for mock " + field.getName());
    // }
    // try {
    // mockFieldValue = modelClass.cast(MetamacReflectionUtils.getDeclaredFieldValue(mockFactoryObj, field.getName()));
    // } catch (Exception e) {
    // throw new IllegalStateException("An error ocurred loading value of mock field for mock " + field.getName());
    // }
    // mocks.put(nameFieldValue, mockFieldValue);
    // }
    // }

    private String getMethodNameFromId(String id) {
        String[] sections = id.split("_+");
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

    private Class getRealClassInTemplateClassParameter() {
        // This is the only way to know what is the real class of the parameter EntityMock
        // For more information http://stackoverflow.com/a/75345/1259208
        return (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
