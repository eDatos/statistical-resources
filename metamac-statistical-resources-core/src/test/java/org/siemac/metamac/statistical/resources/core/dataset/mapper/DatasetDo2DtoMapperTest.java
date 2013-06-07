package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_05_FOR_DATASET_04_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DatasetDo2DtoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetDo2DtoMapper       datasetDo2DtoMapper;

    @Autowired
    private DatasetVersionMockFactory datasetVersionMockFactory;
    
    @Autowired
    private DatasourceMockFactory datasourceMockFactory;

    @Test
    @MetamacMock(DATASOURCE_01_BASIC_NAME)
    public void testDatasourceDo2Dto() {
        Datasource expected = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME);
        DatasourceDto actual = datasetDo2DtoMapper.datasourceDoToDto(expected);
        assertEqualsDatasource(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_03_FOR_DATASET_03_NAME})
    public void testDatasourceDoListToDtoList() {
        List<Datasource> expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getDatasources();

        List<DatasourceDto> actual = datasetDo2DtoMapper.datasourceDoListToDtoList(expected);

        assertEquals(expected.size(), actual.size());
        assertEqualsDatasourceDoAndDtoCollection(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_05_FOR_DATASET_04_NAME})
    public void testDatasetDoToDto() throws MetamacException {
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_05_FOR_DATASET_04_NAME);

        DatasetDto actual = datasetDo2DtoMapper.datasetVersionDoToDto(expected);

        assertEqualsDatasetVersion(expected, actual);
    }
    
    @Test
    @MetamacMock({DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME})
    public void testDatasetDoToDtoWithStatisticOfficiality() throws MetamacException {
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);

        DatasetDto actual = datasetDo2DtoMapper.datasetVersionDoToDto(expected);

        assertEqualsDatasetVersion(expected, actual);
    }
}
