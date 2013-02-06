package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.getDataset03With2DatasetVersions;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_07_VALID_CODE_0001_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_08_VALID_CODE_0002_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion01Basic;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.getDatasetVersion04LastVersionForDataset03;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
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
public class DatasetVersionRepositoryTest extends StatisticalResourcesBaseTest implements DatasetVersionRepositoryTestBase {

    @Autowired
    protected DatasetVersionRepository  datasetVersionRepository;

    @Autowired
    protected DatasetVersionMockFactory datasetVersionMockFactory;

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testRetrieveByUrn() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveByUrn(getDatasetVersion01Basic().getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsDatasetVersion(getDatasetVersion01Basic(), actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws Exception {
        try {
            datasetVersionRepository.retrieveByUrn(URN_NOT_EXISTS);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLastVersion() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveLastVersion(getDataset03With2DatasetVersions().getId());
        assertEqualsDatasetVersion(getDatasetVersion04LastVersionForDataset03(), actual);
    }

    @Override
    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveByVersion() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveByVersion(getDataset03With2DatasetVersions().getId(), getDatasetVersion04LastVersionForDataset03()
                .getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEqualsDatasetVersion(getDatasetVersion04LastVersionForDataset03(), actual);
    }

    @Test
    @Override
    @MetamacMock({DATASET_VERSION_07_VALID_CODE_0001_NAME, DATASET_VERSION_08_VALID_CODE_0002_NAME})
    public void testGetLastCodeUsedInStatisticalOperation() throws Exception {
        ExternalItem statOper = DatasetVersionMockFactory.getDatasetVersion07ValidCode0001().getSiemacMetadataStatisticalResource().getStatisticalOperation();
        String statisticalOperationUrn = statOper.getUrn();

        String code = datasetVersionRepository.getLastCodeUsedInStatisticalOperation(statisticalOperationUrn);
        assertEquals("OPER_0001_DSC_0002", code);
    }

    @Test
    public void testGetLastCodeUsedInStatisticalOperationEmpty() throws Exception {
        String statisticalOperationUrn = "DUMMY";

        String code = datasetVersionRepository.getLastCodeUsedInStatisticalOperation(statisticalOperationUrn);
        datasetVersionRepository.getLastCodeUsedInStatisticalOperation(statisticalOperationUrn);
        assertNull(code);

    }
}
