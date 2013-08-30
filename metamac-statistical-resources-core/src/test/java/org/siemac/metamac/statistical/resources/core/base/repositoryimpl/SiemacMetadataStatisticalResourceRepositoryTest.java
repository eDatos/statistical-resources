package org.siemac.metamac.statistical.resources.core.base.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_07_VALID_CODE_000001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_08_VALID_CODE_000002_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_09_OPER_0001_CODE_000003_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_10_OPER_0002_CODE_000001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SiemacMetadataStatisticalResourceRepositoryTest extends StatisticalResourcesBaseTest implements SiemacMetadataStatisticalResourceRepositoryTestBase {

    @Autowired
    protected SiemacMetadataStatisticalResourceRepository siemacMetadataStatisticalResourceRepository;

    @Autowired
    private DatasetVersionMockFactory                     datasetVersionMockFactory;

    @Autowired
    private PublicationVersionMockFactory                 publicationVersionMockFactory;

    @Test
    @MetamacMock({DATASET_VERSION_08_VALID_CODE_000002_NAME, DATASET_VERSION_07_VALID_CODE_000001_NAME})
    public void testFindLastUsedCodeForResourceType() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_07_VALID_CODE_000001_NAME);
        String code = siemacMetadataStatisticalResourceRepository.findLastUsedCodeForResourceType(datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn(),
                StatisticalResourceTypeEnum.DATASET);
        assertNotNull(code);
        assertEquals("000002", code);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_000002_NAME})
    public void testFindLastUsedCodeForResourceTypePublication() throws Exception {
        PublicationVersion resource = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME);
        String code = siemacMetadataStatisticalResourceRepository.findLastUsedCodeForResourceType(resource.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn(),
                StatisticalResourceTypeEnum.COLLECTION);
        assertNotNull(code);
        assertEquals("000002", code);
    }

    @Test
    @MetamacMock({DATASET_VERSION_08_VALID_CODE_000002_NAME, DATASET_VERSION_07_VALID_CODE_000001_NAME, DATASET_VERSION_10_OPER_0002_CODE_000001_NAME, DATASET_VERSION_09_OPER_0001_CODE_000003_NAME})
    public void testFindLastUsedCodeForResourceTypeMultiOperation() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_09_OPER_0001_CODE_000003_NAME);
        String code = siemacMetadataStatisticalResourceRepository.findLastUsedCodeForResourceType(datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn(),
                StatisticalResourceTypeEnum.DATASET);
        assertNotNull(code);
        assertEquals("000003", code);

        datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_10_OPER_0002_CODE_000001_NAME);
        code = siemacMetadataStatisticalResourceRepository.findLastUsedCodeForResourceType(datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn(),
                StatisticalResourceTypeEnum.DATASET);
        assertNotNull(code);
        assertEquals("000001", code);

        code = siemacMetadataStatisticalResourceRepository.findLastUsedCodeForResourceType(CODE_NOT_EXISTS, StatisticalResourceTypeEnum.DATASET);
        assertNull(code);
    }

    @Test
    @MetamacMock({DATASET_VERSION_08_VALID_CODE_000002_NAME, DATASET_VERSION_07_VALID_CODE_000001_NAME, PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME})
    public void testFindLastUsedCodeForResourceTypeWhenTheCodeHasBeenUsedForAnotherResourceType() throws Exception {
        PublicationVersion resource = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_05_OPERATION_0001_CODE_000001_NAME);
        String code = siemacMetadataStatisticalResourceRepository.findLastUsedCodeForResourceType(resource.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn(),
                StatisticalResourceTypeEnum.COLLECTION);
        assertNotNull(code);
        assertEquals("000001", code);
    }
}
