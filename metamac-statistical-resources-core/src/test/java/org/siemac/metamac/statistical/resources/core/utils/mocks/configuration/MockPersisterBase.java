package org.siemac.metamac.statistical.resources.core.utils.mocks.configuration;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.MockFactory;

public abstract class MockPersisterBase implements MockPersister {

    public final void persistMocks(String... ids) throws Exception {
        List<Object> mocks = locateMocks(ids);

        List<Object> orderedMocks = sortMocksBasedOnDependencies(mocks);

        persistMocks(orderedMocks);
    }

    protected abstract List<Object> sortMocksBasedOnDependencies(List<Object> mocks);

    protected abstract void persistMocks(List<Object> mocks) throws Exception;

    private List<Object> locateMocks(String... ids) {
        List<Object> mocks = new ArrayList<Object>();
        for (String id : ids) {
            Object obj = lookupMock(id);
            if (obj != null) {
                mocks.add(obj);
            } else {
                throw new IllegalArgumentException("Mock with id " + id + " was not found");
            }
        }
        return mocks;
    }

    @SuppressWarnings("rawtypes")
    private Object lookupMock(String id) {
        List<MockFactory> factories = getAllFactories();
        Object found = null;
        for (MockFactory factory : factories) {
            if (factory.getMock(id) != null) {
                found = factory.getMock(id);
            }
        }
        return found;
    }

    @SuppressWarnings("rawtypes")
    private List<MockFactory> getAllFactories() {
        String[] mockFactories = ApplicationContextProvider.getApplicationContext().getBeanNamesForType(MockFactory.class);
        List<MockFactory> beans = new ArrayList<MockFactory>();
        for (String beanName : mockFactories) {
            beans.add(ApplicationContextProvider.getApplicationContext().getBean(beanName, MockFactory.class));
        }
        return beans;
    }

}
