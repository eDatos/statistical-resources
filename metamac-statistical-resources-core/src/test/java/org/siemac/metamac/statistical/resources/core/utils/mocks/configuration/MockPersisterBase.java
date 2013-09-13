package org.siemac.metamac.statistical.resources.core.utils.mocks.configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

public abstract class MockPersisterBase implements MockPersister {

    private static List<MockFactory> factories = null;

    @Override
    public final void persistMocks(String... ids) throws Exception {
        List<Object> mocks = locateMocks(ids);
        persistMocks(mocks);
    }

    protected abstract void persistMocks(List<Object> mocks) throws Exception;

    private List<Object> locateMocks(String... ids) {
        List<Object> mocks = new ArrayList<Object>();
        for (String id : ids) {
            MockDescriptor mockDesc = lookupMockWithDependencies(id);
            if (mockDesc != null) {
                mocks.addAll(mockDesc.getAllMocks());
            } else {
                throw new IllegalArgumentException("Mock with id " + id + " was not found");
            }
        }

        return mocks;
    }

    @SuppressWarnings("rawtypes")
    private MockDescriptor lookupMockWithDependencies(String id) {
        List<MockFactory> factories = getAllFactories();
        Object found = null;
        for (MockFactory factory : factories) {
            MockDescriptor mockDesc = factory.getMockWithDependencies(id);
            if (mockDesc != null) {
                return mockDesc;
            }
        }
        return null;
    }
    @SuppressWarnings("rawtypes")
    private List<MockFactory> getAllFactories() {
        if (factories == null) {
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AssignableTypeFilter(MockFactory.class));

            Set<BeanDefinition> results = scanner.findCandidateComponents("org.siemac.metamac.statistical.resources.core");

            factories = new ArrayList<MockFactory>();
            try {
                for (BeanDefinition bean : results) {
                    String className = bean.getBeanClassName();
                    Class factoryClass = Class.forName(className);
                    Method method = factoryClass.getDeclaredMethod("getInstance");
                    factories.add((MockFactory) (method.invoke(null)));
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Error finding Mock factories", e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Error finding getInstance method in MockFactory", e);
            } catch (Exception e) {
                throw new RuntimeException("Error invoking getInstance on mock factory", e);
            }
        }
        return factories;

    }
}
