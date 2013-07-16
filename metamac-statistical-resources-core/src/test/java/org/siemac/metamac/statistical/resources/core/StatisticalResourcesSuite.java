package org.siemac.metamac.statistical.resources.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.siemac.metamac.core.common.ent.repositoryimpl.InternationalStringRepositoryTest;
import org.siemac.metamac.statistical.resources.core.base.repositoryimpl.IdentifiableStatisticalResourceRepositoryTest;
import org.siemac.metamac.statistical.resources.core.base.repositoryimpl.SiemacMetadataStatisticalResourceRepositoryTest;
import org.siemac.metamac.statistical.resources.core.base.utils.VersionUtilsTest;
import org.siemac.metamac.statistical.resources.core.common.mapper.CommonDo2DtoMapperTest;
import org.siemac.metamac.statistical.resources.core.common.mapper.CommonDto2DoMapperTest;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDo2DtoMapperTest;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapperTest;
import org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl.DatasetRepositoryTest;
import org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl.DatasetVersionRepositoryTest;
import org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl.DatasourceRepositoryTest;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DataManipulateTest;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetServiceTest;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtilsTest;
import org.siemac.metamac.statistical.resources.core.enume.utils.QueryStatusEnumUtilsTest;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesOptimisticLockingTest;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacadeTest;
import org.siemac.metamac.statistical.resources.core.invocation.CommonMetadataRestExternalServiceTest;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalServiceTest;
import org.siemac.metamac.statistical.resources.core.invocation.StatisticalOperationsRestInternalServiceTest;
import org.siemac.metamac.statistical.resources.core.invocation.utils.RestCriteriaUtilsTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCheckerTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataCheckerTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleFillerTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleCheckerTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleFillerTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker.ExternalItemCheckerTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset.DatasetLifecycleServiceInvocationValidatorTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset.DatasetLifecycleServiceTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.publication.PublicationLifecycleServiceInvocationValidatorTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.publication.PublicationLifecycleServiceTest;
import org.siemac.metamac.statistical.resources.core.publication.mapper.PublicationDo2DtoMapperTest;
import org.siemac.metamac.statistical.resources.core.publication.mapper.PublicationDto2DoMapperTest;
import org.siemac.metamac.statistical.resources.core.publication.repositoryimpl.ChapterRepositoryTest;
import org.siemac.metamac.statistical.resources.core.publication.repositoryimpl.CubeRepositoryTest;
import org.siemac.metamac.statistical.resources.core.publication.repositoryimpl.PublicationVersionRepositoryTest;
import org.siemac.metamac.statistical.resources.core.publication.serviceapi.PublicationServiceTest;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDo2DtoMapperTest;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDto2DoMapperTest;
import org.siemac.metamac.statistical.resources.core.query.repositoryimpl.QueryRepositoryTest;
import org.siemac.metamac.statistical.resources.core.query.repositoryimpl.QueryVersionRepositoryTest;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.QueryServiceTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({StatisticalResourcesServiceFacadeTest.class,
                     StatisticalResourcesOptimisticLockingTest.class,
                     InternationalStringRepositoryTest.class,
                     SiemacMetadataStatisticalResourceRepositoryTest.class,
                     IdentifiableStatisticalResourceRepositoryTest.class,
                     QueryRepositoryTest.class,
                     QueryVersionRepositoryTest.class, 
                     QueryServiceTest.class,  
                     QueryDo2DtoMapperTest.class,
                     QueryDto2DoMapperTest.class,
                     DatasourceRepositoryTest.class,
                     DatasetRepositoryTest.class,
                     DatasetVersionRepositoryTest.class, 
                     DatasetDo2DtoMapperTest.class,
                     DatasetDto2DoMapperTest.class,
                     DatasetServiceTest.class, 
                     PublicationVersionRepositoryTest.class,
                     CubeRepositoryTest.class,
                     ChapterRepositoryTest.class,
                     PublicationServiceTest.class,
                     PublicationDo2DtoMapperTest.class,
                     PublicationDto2DoMapperTest.class,
                     ProcStatusEnumUtilsTest.class, 
                     QueryStatusEnumUtilsTest.class,
                     LifecycleCommonMetadataCheckerTest.class,
                     LifecycleCheckerTest.class,
                     SiemacLifecycleCheckerTest.class,
                     LifecycleFillerTest.class,
                     SiemacLifecycleFillerTest.class, 
                     DatasetLifecycleServiceInvocationValidatorTest.class,
                     DatasetLifecycleServiceTest.class,
                     PublicationLifecycleServiceInvocationValidatorTest.class,
                     PublicationLifecycleServiceTest.class,
                     VersionUtilsTest.class,
                     CommonDto2DoMapperTest.class,
                     CommonDo2DtoMapperTest.class,
                     DataManipulateTest.class,
                     ExternalItemCheckerTest.class,
                     RestCriteriaUtilsTest.class,
                     StatisticalOperationsRestInternalServiceTest.class,
                     CommonMetadataRestExternalServiceTest.class,
                     SrmRestInternalServiceTest.class})
public class StatisticalResourcesSuite {
}
