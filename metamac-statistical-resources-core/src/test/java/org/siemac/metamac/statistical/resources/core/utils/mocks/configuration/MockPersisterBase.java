package org.siemac.metamac.statistical.resources.core.utils.mocks.configuration;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.ApplicationContextProvider;

public abstract class MockPersisterBase implements MockPersister {

    @Override
    public final void persistMocks(String... ids) throws Exception {
        prepareFactories();
        
        List<Object> mocks = locateMocks(ids);
        
        persistMocks(mocks);
    }

    private void prepareFactories() {
        MockFactory.clearFactory();
    }

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
        
        //Get All dependencies registered for mocks
        mocks.addAll(MockFactory.getDependencies());
        
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
        
        for (MockFactory factory : factories) {
            factory.getDependencies();
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
