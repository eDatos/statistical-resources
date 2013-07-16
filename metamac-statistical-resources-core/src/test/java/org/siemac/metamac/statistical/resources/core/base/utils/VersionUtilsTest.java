package org.siemac.metamac.statistical.resources.core.base.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;

public class VersionUtilsTest {
    
    @Test
    public void testInitialVersion() throws Exception {
        assertEquals("001.000", StatisticalResourcesVersionUtils.createInitialVersion());
    }

    @Test
    public void testIsInitialVersion() throws Exception {
        assertTrue(StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion("001.000")));

        assertEquals(false, StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion("002.000")));

        assertEquals(false, StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion("001.001")));
    }
    
    @Test(expected=Exception.class)
    public void testIsInitialVersionErrorVersionRequired() throws Exception {
        StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion(null));
    }

    @Test
    public void testCreateNextVersion() throws Exception {
        assertEquals("001.001", StatisticalResourcesVersionUtils.createNextVersion(mockHasLifecycleWithVersion("001.000"), VersionTypeEnum.MINOR));
        assertEquals("002.000", StatisticalResourcesVersionUtils.createNextVersion(mockHasLifecycleWithVersion("001.000"), VersionTypeEnum.MAJOR));
    }

    private HasLifecycle mockHasLifecycleWithVersion(final String version) {
        return new HasLifecycle() {

            @Override
            public LifeCycleStatisticalResource getLifeCycleStatisticalResource() {
                LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
                resource.setVersionLogic(version);
                return resource;
            }
        };
    }
}
