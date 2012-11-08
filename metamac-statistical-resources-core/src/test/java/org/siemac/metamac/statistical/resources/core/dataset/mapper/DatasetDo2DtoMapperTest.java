package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceDoAndDtoCollection;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
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
    public void testDatasourceDo2Dto() {
        Datasource expected = StatisticalResourcesDoMocks.mockDatasource();
        DatasourceDto actual = datasetDo2DtoMapper.datasourceDoToDto(expected);
        assertEqualsDatasource(expected, actual);
    }

    @Test
    public void testDatasourceDoListToDtoList() {
        List<Datasource> expected = new ArrayList<Datasource>();
        expected.add(StatisticalResourcesDoMocks.mockDatasource());
        expected.add(StatisticalResourcesDoMocks.mockDatasource());
        
        List<DatasourceDto> actual = datasetDo2DtoMapper.datasourceDoListToDtoList(expected);
        
        assertEquals(expected.size(), actual.size());
        assertEqualsDatasourceDoAndDtoCollection(expected, actual);
    }
}
