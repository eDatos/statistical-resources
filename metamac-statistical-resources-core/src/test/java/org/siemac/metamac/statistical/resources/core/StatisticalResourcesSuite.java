package org.siemac.metamac.statistical.resources.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDo2DtoMapperTest;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapperTest;
import org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl.DatasetVersionRepositoryTest;
import org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl.DatasourceRepositoryTest;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetServiceTest;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacadeTest;
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
                     QueryRepositoryTest.class, 
                     QueryServiceTest.class, 
                     QueryDo2DtoMapperTest.class, 
                     QueryDto2DoMapperTest.class,
                     DatasetVersionRepositoryTest.class, 
                     DatasourceRepositoryTest.class,
                     DatasetDo2DtoMapperTest.class,
                     DatasetDto2DoMapperTest.class,
                     DatasetServiceTest.class, 
                     PublicationVersionRepositoryTest.class,
                     PublicationServiceTest.class})
public class StatisticalResourcesSuite {
}
