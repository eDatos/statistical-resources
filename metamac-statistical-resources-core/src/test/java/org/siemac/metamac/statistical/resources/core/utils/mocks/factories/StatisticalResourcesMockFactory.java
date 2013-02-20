package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

import com.arte.lang.ObjectUtils;

public abstract class StatisticalResourcesMockFactory<EntityMock> extends MockFactory<EntityMock> {

    private static StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks;

    protected static StatisticalResourcesPersistedDoMocks getStatisticalResourcesPersistedDoMocks() {
        if (statisticalResourcesPersistedDoMocks == null) {
            statisticalResourcesPersistedDoMocks = ApplicationContextProvider.getApplicationContext().getBean(StatisticalResourcesPersistedDoMocks.class);
        }
        return statisticalResourcesPersistedDoMocks;
    }
    
    public EntityMock retrieveMock(String mockName) {
        return ObjectUtils.deepCopy((getMock(mockName)));
    }
    
    public List<EntityMock> retrieveMocks(String... names) {
        List<EntityMock> list = new ArrayList<EntityMock>();
        for (String name : names) {
            list.add(retrieveMock(name));
        }
        return list;
    }
}
