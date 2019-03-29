package org.siemac.metamac.statistical.resources.core.base.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;

public class VersionUtilsTest {

    @Test
    public void testIsInitialVersion() throws Exception {
        assertTrue(StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion("001.000")));
        assertTrue(StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion("1.0")));

        assertFalse(StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion("002.000")));
        assertFalse(StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion("2.0")));

        assertFalse(StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion("001.001")));
        assertFalse(StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion("1.1")));
    }

    @Test(expected = Exception.class)
    public void testIsInitialVersionErrorVersionRequired() throws Exception {
        StatisticalResourcesVersionUtils.isInitialVersion(mockHasLifecycleWithVersion(null));
    }

    @Test
    public void testCreateNextVersion() throws Exception {
        assertEquals("1.1", StatisticalResourcesVersionUtils.createNextVersion(mockHasLifecycleWithVersion("001.000"), VersionTypeEnum.MINOR));
        assertEquals("1.1", StatisticalResourcesVersionUtils.createNextVersion(mockHasLifecycleWithVersion("1.0"), VersionTypeEnum.MINOR));

        assertEquals("2.0", StatisticalResourcesVersionUtils.createNextVersion(mockHasLifecycleWithVersion("001.000"), VersionTypeEnum.MAJOR));
        assertEquals("2.0", StatisticalResourcesVersionUtils.createNextVersion(mockHasLifecycleWithVersion("1.0"), VersionTypeEnum.MAJOR));

        assertEquals("2.0", StatisticalResourcesVersionUtils.createNextVersion(mockHasLifecycleWithVersion("1.99999"), VersionTypeEnum.MINOR));
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
