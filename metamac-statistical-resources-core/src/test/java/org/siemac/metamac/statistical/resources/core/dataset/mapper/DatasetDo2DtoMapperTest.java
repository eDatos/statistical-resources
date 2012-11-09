package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.DATASOURCE_01_BASIC;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceDoAndDtoCollection;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetDo2DtoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetDo2DtoMapper datasetDo2DtoMapper;
    
    @Test
    @MetamacMock(DATASOURCE_01_BASIC_NAME)
    public void testDatasourceDo2Dto() {
        Datasource expected = DATASOURCE_01_BASIC;
        DatasourceDto actual = datasetDo2DtoMapper.datasourceDoToDto(expected);
        assertEqualsDatasource(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_03_FOR_DATASET_03_NAME})
    public void testDatasourceDoListToDtoList() {
        List<Datasource> expected = DATASET_VERSION_03_FOR_DATASET_03.getDatasources();
        
        List<DatasourceDto> actual = datasetDo2DtoMapper.datasourceDoListToDtoList(expected);
        
        assertEquals(expected.size(), actual.size());
        assertEqualsDatasourceDoAndDtoCollection(expected, actual);
    }
}
