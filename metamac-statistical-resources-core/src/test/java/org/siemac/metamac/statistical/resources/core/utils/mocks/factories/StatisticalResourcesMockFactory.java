package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.util.ReflectionUtils;

import com.arte.libs.lang.ObjectUtils;

public abstract class StatisticalResourcesMockFactory<EntityMock> extends MockFactory<EntityMock> {

    private static StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks;

    public static final String                          OPERATION_01_CODE = "C00025A";
    public static final String                          OPERATION_02_CODE = "C00025B";
    public static final String                          OPERATION_03_CODE = "C00025C";

    public static final String                          INIT_VERSION      = "001.000";
    public static final String                          SECOND_VERSION    = "002.000";
    public static final String                          THIRD_VERSION     = "003.000";

    protected static StatisticalResourcesPersistedDoMocks getStatisticalResourcesPersistedDoMocks() {
        if (statisticalResourcesPersistedDoMocks == null) {
            statisticalResourcesPersistedDoMocks = ApplicationContextProvider.getApplicationContext().getBean(StatisticalResourcesPersistedDoMocks.class);
        }
        return statisticalResourcesPersistedDoMocks;
    }

    public void setStatisticalResourcesPersistedDoMocks(StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks) {
        StatisticalResourcesMockFactory.statisticalResourcesPersistedDoMocks = statisticalResourcesPersistedDoMocks;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public EntityMock retrieveMock(String mockName) {
        EntityMock templateInstance;
        try {
            // This is the only way to know what is the real class of the parameter EntityMock
            // For more information http://stackoverflow.com/a/75345/1259208
            templateInstance = (EntityMock)((Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error getting instance from generic",e);
        }
        EntityMock mock = getMock(mockName);
        return ObjectUtils.deepCopyInParent(mock, templateInstance);
    }

    public List<EntityMock> retrieveMocks(String... names) {
        List<EntityMock> list = new ArrayList<EntityMock>();
        for (String name : names) {
            list.add(retrieveMock(name));
        }
        return list;
    }
}
