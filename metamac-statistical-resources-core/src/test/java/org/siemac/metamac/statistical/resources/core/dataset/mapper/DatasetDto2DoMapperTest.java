package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_01_BASIC_NAME;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
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
    private DatasetDto2DoMapper             datasetDto2DoMapper;

    @Autowired
    private StatisticOfficialityMockFactory statisticOfficialityMockFactory;

    @Autowired
    private DatasetVersionMockFactory       datasetVersionMockFactory;

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
        DatasetVersionDto dto = StatisticalResourcesDtoMocks.mockDatasetVersionDto(officiality);
        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);
        assertEqualsDatasetVersion(dto, entity);
    }

    @Test
    @MetamacMock({DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME, STATISTIC_OFFICIALITY_01_BASIC_NAME})
    public void testDatasetDtoToDoDetectDateNextUpdateChange() throws MetamacException {
        StatisticOfficiality officiality = statisticOfficialityMockFactory.retrieveMock(STATISTIC_OFFICIALITY_01_BASIC_NAME);

        DatasetVersion source = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME);

        DateTime newNextUpdate = new DateTime();
        DatasetVersionDto dto = StatisticalResourcesDtoMocks.mockDatasetVersionDto(officiality);
        dto.setDateNextUpdate(newNextUpdate.toDate());
        dto.setId(source.getId());
        dto.setOptimisticLockingVersion(source.getVersion());

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        Assert.assertTrue(entity.getUserModifiedDateNextUpdate());
        BaseAsserts.assertEqualsDate(newNextUpdate, entity.getDateNextUpdate());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, STATISTIC_OFFICIALITY_01_BASIC_NAME})
    public void testDatasetDtoToDoDetectDateNextUpdateChangNullInSource() throws MetamacException {
        StatisticOfficiality officiality = statisticOfficialityMockFactory.retrieveMock(STATISTIC_OFFICIALITY_01_BASIC_NAME);

        DatasetVersion source = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DateTime newNextUpdate = new DateTime();
        DatasetVersionDto dto = StatisticalResourcesDtoMocks.mockDatasetVersionDto(officiality);
        dto.setDateNextUpdate(newNextUpdate.toDate());
        dto.setId(source.getId());
        dto.setOptimisticLockingVersion(source.getVersion());

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        Assert.assertTrue(entity.getUserModifiedDateNextUpdate());
        BaseAsserts.assertEqualsDate(newNextUpdate, entity.getDateNextUpdate());
    }

    @Test
    @MetamacMock({DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME, STATISTIC_OFFICIALITY_01_BASIC_NAME})
    public void testDatasetDtoToDoNoDateNextUpdateChange() throws MetamacException {
        StatisticOfficiality officiality = statisticOfficialityMockFactory.retrieveMock(STATISTIC_OFFICIALITY_01_BASIC_NAME);

        DatasetVersion source = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME);
        DateTime originalDateNextVersion = source.getDateNextUpdate();

        DatasetVersionDto dto = StatisticalResourcesDtoMocks.mockDatasetVersionDto(officiality);
        dto.setDateNextUpdate(source.getDateNextUpdate().toDate());
        dto.setOptimisticLockingVersion(source.getVersion());
        dto.setId(source.getId());

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        Assert.assertFalse(entity.getUserModifiedDateNextUpdate());
        BaseAsserts.assertEqualsDate(originalDateNextVersion, entity.getDateNextUpdate());
    }

}
