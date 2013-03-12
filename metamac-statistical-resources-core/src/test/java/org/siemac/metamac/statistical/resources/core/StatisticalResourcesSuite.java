package org.siemac.metamac.statistical.resources.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.siemac.metamac.statistical.resources.core.base.repositoryimpl.IdentifiableStatisticalResourceRepositoryTest;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDo2DtoMapperTest;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapperTest;
import org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl.DatasetRepositoryTest;
import org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl.DatasetVersionRepositoryTest;
import org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl.DatasourceRepositoryTest;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetServiceTest;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtilsTest;
import org.siemac.metamac.statistical.resources.core.enume.utils.QueryStatusEnumUtilsTest;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesOptimisticLockingTest;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacadeTest;
import org.siemac.metamac.statistical.resources.core.publication.repositoryimpl.PublicationRepositoryTest;
import org.siemac.metamac.statistical.resources.core.publication.repositoryimpl.PublicationVersionRepositoryTest;
import org.siemac.metamac.statistical.resources.core.publication.serviceapi.PublicationServiceTest;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDo2DtoMapperTest;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDto2DoMapperTest;
import org.siemac.metamac.statistical.resources.core.query.repositoryimpl.QueryRepositoryTest;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.QueryServiceTest;

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({StatisticalResourcesServiceFacadeTest.class,
                     StatisticalResourcesOptimisticLockingTest.class,
                     IdentifiableStatisticalResourceRepositoryTest.class,
                     QueryRepositoryTest.class, 
                     QueryServiceTest.class, 
                     QueryDo2DtoMapperTest.class,
                     QueryDto2DoMapperTest.class,
                     DatasourceRepositoryTest.class,
                     DatasetVersionRepositoryTest.class, 
                     DatasetRepositoryTest.class,
                     DatasetDo2DtoMapperTest.class,
                     DatasetDto2DoMapperTest.class,
                     DatasetServiceTest.class, 
                     PublicationRepositoryTest.class,
                     PublicationVersionRepositoryTest.class,
                     PublicationServiceTest.class,
                     ProcStatusEnumUtilsTest.class, 
                     QueryStatusEnumUtilsTest.class})
public class StatisticalResourcesSuite {
}
