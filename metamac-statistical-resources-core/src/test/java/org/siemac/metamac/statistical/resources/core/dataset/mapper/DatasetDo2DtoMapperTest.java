package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts.assertEqualsStatisticOfficiality;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsCategorisation;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsCategorisationDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDataset;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDimensionRepresentationMapping;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CategorisationMockFactory.CATEGORISATION_01_DATASET_VERSION_01_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CategorisationMockFactory.CATEGORISATION_01_DATASET_VERSION_02_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_05_FOR_DATASET_04_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DimensionRepresentationMappingMockFactory.DIMENSION_REPRESENTATION_MAPPING_01_DATASET_01_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_01_BASIC_NAME;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.CategorisationDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DimensionRepresentationMappingDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetDo2DtoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetDo2DtoMapper datasetDo2DtoMapper;

    @Test
    @MetamacMock(DATASOURCE_01_BASIC_NAME)
    public void testDatasourceDo2Dto() throws Exception {
        Datasource expected = datasourceMockFactory.retrieveMock(DATASOURCE_01_BASIC_NAME);
        DatasourceDto actual = datasetDo2DtoMapper.datasourceDoToDto(expected);
        assertEqualsDatasource(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_03_FOR_DATASET_03_NAME})
    public void testDatasourceDoListToDtoList() throws Exception {
        List<Datasource> expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME).getDatasources();
        List<DatasourceDto> actual = datasetDo2DtoMapper.datasourceDoListToDtoList(expected);

        assertEquals(expected.size(), actual.size());
        assertEqualsDatasourceDoAndDtoCollection(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_05_FOR_DATASET_04_NAME})
    public void testDatasetVersionDoToDto() throws MetamacException {
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_05_FOR_DATASET_04_NAME);
        DatasetVersionDto actual = datasetDo2DtoMapper.datasetVersionDoToDto(getServiceContextAdministrador(), expected);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME})
    public void testDatasetVersionDoToDtoProcStatusPublishedVisible() throws MetamacException {
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        DatasetVersionDto actual = datasetDo2DtoMapper.datasetVersionDoToDto(getServiceContextAdministrador(), expected);
        assertEquals(ProcStatusEnum.PUBLISHED, actual.getProcStatus());
    }

    @Test
    @MetamacMock({DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME})
    public void testDatasetVersionDoToDtoWithStatisticOfficiality() throws MetamacException {
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        DatasetVersionDto actual = datasetDo2DtoMapper.datasetVersionDoToDto(getServiceContextAdministrador(), expected);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    @MetamacMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME)
    public void testDatasetVersionDoToDatasetRelatedResourceDto() throws MetamacException {
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        RelatedResourceDto actual = datasetDo2DtoMapper.datasetVersionDoToDatasetRelatedResourceDto(expected);
        assertEqualsDataset(expected, actual);
    }

    @Test
    @MetamacMock(STATISTIC_OFFICIALITY_01_BASIC_NAME)
    public void testStatisticOfficialityDoToDto() throws MetamacException {
        StatisticOfficiality expected = statisticOfficialityMockFactory.retrieveMock(STATISTIC_OFFICIALITY_01_BASIC_NAME);
        StatisticOfficialityDto actual = datasetDo2DtoMapper.statisticOfficialityDo2Dto(expected);
        assertEqualsStatisticOfficiality(expected, actual);
    }

    @Test
    @MetamacMock(CATEGORISATION_01_DATASET_VERSION_01_NAME)
    public void testCategorisationDo2Dto() throws Exception {
        Categorisation expected = categorisationMockFactory.retrieveMock(CATEGORISATION_01_DATASET_VERSION_01_NAME);
        CategorisationDto actual = datasetDo2DtoMapper.categorisationDoToDto(expected);
        assertEqualsCategorisation(expected, actual);
    }

    @Test
    @MetamacMock({CATEGORISATION_01_DATASET_VERSION_01_NAME, CATEGORISATION_01_DATASET_VERSION_02_NAME})
    public void testCategorisationDoListToDtoList() throws Exception {
        List<Categorisation> expected = new ArrayList<Categorisation>();
        expected.add(categorisationMockFactory.retrieveMock(CATEGORISATION_01_DATASET_VERSION_01_NAME));
        expected.add(categorisationMockFactory.retrieveMock(CATEGORISATION_01_DATASET_VERSION_02_NAME));
        List<CategorisationDto> actual = datasetDo2DtoMapper.categorisationDoListToDtoList(expected);

        assertEquals(expected.size(), actual.size());
        assertEqualsCategorisationDoAndDtoCollection(expected, actual);
    }

    @Test
    @MetamacMock(DIMENSION_REPRESENTATION_MAPPING_01_DATASET_01_NAME)
    public void testDimensionRepresentationMappingDoToDto() throws Exception {
        DimensionRepresentationMapping expected = dimensionRepresentationMappingMockFactory.retrieveMock(DIMENSION_REPRESENTATION_MAPPING_01_DATASET_01_NAME);
        DimensionRepresentationMappingDto actual = datasetDo2DtoMapper.dimensionRepresentationMappingDoToDto(expected);
        assertEqualsDimensionRepresentationMapping(expected, actual);
    }

    @Test
    @MetamacMock(DIMENSION_REPRESENTATION_MAPPING_01_DATASET_01_NAME)
    public void testDimensionRepresentationMappingDoToDtoList() throws Exception {
        DimensionRepresentationMapping expected = dimensionRepresentationMappingMockFactory.retrieveMock(DIMENSION_REPRESENTATION_MAPPING_01_DATASET_01_NAME);
        List<DimensionRepresentationMapping> expectedList = new ArrayList<DimensionRepresentationMapping>();
        expectedList.add(expected);
        List<DimensionRepresentationMappingDto> actualList = datasetDo2DtoMapper.dimensionRepresentationMappingDoToDtoList(expectedList);
        assertEquals(expectedList.size(), actualList.size());
        assertEqualsDimensionRepresentationMapping(expectedList.get(0), actualList.get(0));
    }
}
