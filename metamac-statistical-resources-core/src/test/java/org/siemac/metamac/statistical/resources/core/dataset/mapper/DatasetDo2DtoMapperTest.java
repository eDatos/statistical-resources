package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesAsserts;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
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
        Datasource entity = StatisticalResourcesDoMocks.mockDatasource();
        DatasourceDto dto = datasetDo2DtoMapper.datasourceDoToDto(entity);
        StatisticalResourcesAsserts.assertEqualsDatasource(entity, dto);
    }

    @Test
    public void testDatasourceDoListToDtoList() {
        List<Datasource> datasources = new ArrayList<Datasource>();
        datasources.add(StatisticalResourcesDoMocks.mockDatasource());
        datasources.add(StatisticalResourcesDoMocks.mockDatasource());
        
        List<DatasourceDto> datasourcesDto = datasetDo2DtoMapper.datasourceDoListToDtoList(datasources);
        
        assertEquals(datasources.size(), datasourcesDto.size());
        StatisticalResourcesAsserts.assertEqualsDatasourceDoAndDtoCollection(datasources, datasourcesDto);
    }

}
