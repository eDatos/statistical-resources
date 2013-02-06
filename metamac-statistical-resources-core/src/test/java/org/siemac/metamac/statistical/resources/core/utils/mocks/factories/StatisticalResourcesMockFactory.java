package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

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
}
