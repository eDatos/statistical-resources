package org.siemac.metamac.statistical.resources.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.siemac.metamac.statistical.resources.core.tests.mapper.StatisticalResourceDo2DtoMapperTest;
import org.siemac.metamac.statistical.resources.core.tests.mapper.StatisticalResourceDto2DoMapperTest;
import org.siemac.metamac.statistical.resources.core.tests.repository.QueryRepositoryTest;
import org.siemac.metamac.statistical.resources.core.tests.serviceapi.StatisticalResourcesServiceFacadeTest;
import org.siemac.metamac.statistical.resources.core.tests.serviceapi.StatisticalResourcesServiceTest;

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({QueryRepositoryTest.class, StatisticalResourcesServiceTest.class, StatisticalResourceDo2DtoMapperTest.class, StatisticalResourceDto2DoMapperTest.class, StatisticalResourcesServiceFacadeTest.class})
public class StatisticalResourcesSuite {
}
