package org.siemac.metamac.statistical.resources.core.base.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts.assertEqualsIdentifiableStatisticalResource;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_01_WITH_SELECTION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_02_BASIC_ORDERED_01_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class IdentifiableStatisticalResourceRepositoryTest extends StatisticalResourcesBaseTest implements IdentifiableStatisticalResourceRepositoryTestBase {

    @Autowired
    private IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;

    @Autowired
    private QueryMockFactory                          queryMockFactory;

    @Autowired
    private DatasetVersionMockFactory                 datasetVersionMockFactory;

    @Autowired
    private PublicationVersionMockFactory             publicationVersionMockFactory;

    @Override
    @Test
    @MetamacMock({QUERY_01_WITH_SELECTION_NAME, QUERY_02_BASIC_ORDERED_01_NAME, DATASET_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    public void testRetrieveByUrn() throws Exception {
        {
            IdentifiableStatisticalResource actual = identifiableStatisticalResourceRepository.retrieveByUrn(queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());
            assertEqualsIdentifiableStatisticalResource(queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource(), actual);
        }

        {
            IdentifiableStatisticalResource actual = identifiableStatisticalResourceRepository.retrieveByUrn(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME)
                    .getSiemacMetadataStatisticalResource().getUrn());
            assertEqualsIdentifiableStatisticalResource(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource(), actual);
        }

        {
            IdentifiableStatisticalResource actual = identifiableStatisticalResourceRepository.retrieveByUrn(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME)
                    .getSiemacMetadataStatisticalResource().getUrn());
            assertEqualsIdentifiableStatisticalResource(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource(), actual);
        }

    }

    @Test
    @MetamacMock({QUERY_01_WITH_SELECTION_NAME, QUERY_02_BASIC_ORDERED_01_NAME, DATASET_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    public void testRetrieveByUrnNotFound() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.IDENTIFIABLE_STATISTICAL_RESOURCE_NOT_FOUND, URN_NOT_EXISTS), 1);
        identifiableStatisticalResourceRepository.retrieveByUrn(URN_NOT_EXISTS);
    }

    @Override
    @Test
    @MetamacMock({QUERY_01_WITH_SELECTION_NAME, QUERY_02_BASIC_ORDERED_01_NAME, DATASET_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    public void testCheckDuplicatedUrn() throws Exception {
        { // Not error because URN not exists
            IdentifiableStatisticalResource identifiableStatisticalResource = new IdentifiableStatisticalResource();
            identifiableStatisticalResource.setUrn(URN_NOT_EXISTS);
            identifiableStatisticalResourceRepository.checkDuplicatedUrn(identifiableStatisticalResource);
        }

        { // Not error because is the same object
            identifiableStatisticalResourceRepository.checkDuplicatedUrn(queryMockFactory.retrieveMock(QUERY_02_BASIC_ORDERED_01_NAME).getLifeCycleStatisticalResource());
        }
    }

    @Test
    @MetamacMock({QUERY_01_WITH_SELECTION_NAME, QUERY_02_BASIC_ORDERED_01_NAME, DATASET_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    public void testCheckDuplicatedUrnErrorAlreadyExists() throws Exception {
        String urn = queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.IDENTIFIABLE_STATISTICAL_RESOURCE_URN_DUPLICATED, urn), 1);

        IdentifiableStatisticalResource identifiableStatisticalResource = new IdentifiableStatisticalResource();
        identifiableStatisticalResource.setUrn(urn);
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(identifiableStatisticalResource);
    }
}
