package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DatasetDto2DoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetDto2DoMapper datasetDto2DoMapper;
    
    @Autowired
    private StatisticOfficialityMockFactory statisticOfficialityMockFactory;

    @Test
    public void testDatasourceDtoToDo() throws MetamacException {
        DatasourceDto dto = StatisticalResourcesDtoMocks.mockDatasourceDto();
        Datasource entity = datasetDto2DoMapper.datasourceDtoToDo(dto);
        assertEqualsDatasource(dto, entity);
    }

    @Test
    @MetamacMock(STATISTIC_OFFICIALITY_01_BASIC_NAME)
    public void testDatasetDtoToDo() throws MetamacException {
        StatisticOfficiality officiality = statisticOfficialityMockFactory.retrieveMock(STATISTIC_OFFICIALITY_01_BASIC_NAME);
        DatasetDto dto = StatisticalResourcesDtoMocks.mockDatasetDto(officiality);
        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);
        assertEqualsDatasetVersion(dto, entity);
    }

}
