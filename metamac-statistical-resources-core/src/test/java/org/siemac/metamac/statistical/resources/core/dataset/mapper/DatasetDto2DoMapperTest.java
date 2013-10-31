package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsCategorisation;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CategorisationMockFactory.CATEGORISATION_01_DATASET_VERSION_01_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_57_DRAFT_INITIAL_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_77_NO_PUB_REPLACES_DATASET_78_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_78_PUB_IS_REPLACED_BY_DATASET_77_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_01_BASIC_NAME;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.datasets.CategorisationDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetDto2DoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetDto2DoMapper datasetDto2DoMapper;

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
    @MetamacMock({DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME})
    public void testDatasetDtoToDoDetectDateNextUpdateChange() throws MetamacException {
        DatasetVersion source = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME);

        DateTime newNextUpdate = new DateTime();
        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(source);
        dto.setDateNextUpdate(newNextUpdate.toDate());

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        Assert.assertTrue(entity.getUserModifiedDateNextUpdate());
        BaseAsserts.assertEqualsDate(newNextUpdate, entity.getDateNextUpdate());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testDatasetDtoToDoDetectDateNextUpdateChangNullInSource() throws MetamacException {
        DatasetVersion source = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);

        DateTime newNextUpdate = new DateTime();
        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(source);
        dto.setDateNextUpdate(newNextUpdate.toDate());

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        Assert.assertTrue(entity.getUserModifiedDateNextUpdate());
        BaseAsserts.assertEqualsDate(newNextUpdate, entity.getDateNextUpdate());
    }

    @Test
    @MetamacMock({DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME})
    public void testDatasetDtoToDoNoDateNextUpdateChange() throws MetamacException {

        DatasetVersion source = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_49_WITH_DATASOURCE_FROM_PX_WITH_NEXT_UPDATE_IN_ONE_MONTH_NAME);
        DateTime originalDateNextVersion = source.getDateNextUpdate();

        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(source);
        dto.setDateNextUpdate(source.getDateNextUpdate().toDate());

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        Assert.assertFalse(entity.getUserModifiedDateNextUpdate());
        BaseAsserts.assertEqualsDate(originalDateNextVersion, entity.getDateNextUpdate());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_77_NO_PUB_REPLACES_DATASET_78_NAME})
    public void testDatasetDtoToDoCanNotReplaceADatasetVersionThatHasBeenReplacedAlready() throws MetamacException {
        DatasetVersion datasetVersionAlreadyReplaced = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_78_PUB_IS_REPLACED_BY_DATASET_77_NAME);
        DatasetVersion datasetVersionThatReplaces = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_77_NO_PUB_REPLACES_DATASET_78_NAME);

        DatasetVersion source = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        System.out.println(datasetVersionThatReplaces.getId());
        System.out.println(source.getId());
        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(source);

        dto.setReplaces(StatisticalResourcesDtoMocks.mockNotPersistedRelatedResourceDatasetVersionDto(datasetVersionAlreadyReplaced));

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_ALREADY_BEEN_REPLACED_BY_OTHER_DATASET_VERSION, source.getSiemacMetadataStatisticalResource().getUrn(),
                datasetVersionAlreadyReplaced.getSiemacMetadataStatisticalResource().getUrn()));

        datasetDto2DoMapper.datasetVersionDtoToDo(dto);
    }

    @Test
    @MetamacMock({DATASET_VERSION_77_NO_PUB_REPLACES_DATASET_78_NAME})
    public void testDatasetDtoToDoReplaceADatasetVersionThatHasBeenReplacedAlreadyByThisDataset() throws MetamacException {
        DatasetVersion datasetVersionAlreadyReplaced = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_78_PUB_IS_REPLACED_BY_DATASET_77_NAME);

        DatasetVersion source = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_77_NO_PUB_REPLACES_DATASET_78_NAME);
        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(source);

        dto.setReplaces(StatisticalResourcesDtoMocks.mockNotPersistedRelatedResourceDatasetVersionDto(datasetVersionAlreadyReplaced));

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);
        assertNotNull(entity.getSiemacMetadataStatisticalResource().getReplaces());
        DatasetVersion replacedDataset = entity.getSiemacMetadataStatisticalResource().getReplaces().getDatasetVersion();
        assertEquals(datasetVersionAlreadyReplaced.getSiemacMetadataStatisticalResource().getUrn(), replacedDataset.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testDatasetDtoToDoCanReplaceADatasetVersionThatHasNotBeenReplacedYet() throws MetamacException {
        DatasetVersion datasetVersionNotReplacedYet = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_02_BASIC_NAME);

        DatasetVersion source = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(source);

        dto.setReplaces(StatisticalResourcesDtoMocks.mockNotPersistedRelatedResourceDatasetVersionDto(datasetVersionNotReplacedYet));

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        assertNotNull(entity.getSiemacMetadataStatisticalResource().getReplaces());
        DatasetVersion replacedDataset = entity.getSiemacMetadataStatisticalResource().getReplaces().getDatasetVersion();
        DatasetsAsserts.assertEqualsDatasetVersion(datasetVersionNotReplacedYet, replacedDataset);
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testDatasetDtoToDoReplacesItself() throws MetamacException {
        DatasetVersion source = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME);
        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(source);

        dto.setReplaces(StatisticalResourcesDtoMocks.mockNotPersistedRelatedResourceDatasetVersionDto(source));

        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_CANT_REPLACE_ITSELF, source.getSiemacMetadataStatisticalResource().getUrn()));

        datasetDto2DoMapper.datasetVersionDtoToDo(dto);
    }

    @Test
    @MetamacMock({DATASET_VERSION_57_DRAFT_INITIAL_VERSION_NAME, DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION_NAME, DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION_NAME,
            DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION_NAME, DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION_NAME})
    public void testChangeDsdByOtherVersionInInitialVersion() throws MetamacException {

        checkCanChangeDsdByOtherVersion(DATASET_VERSION_57_DRAFT_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOtherVersion(DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOtherVersion(DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION_NAME);

        checkCanChangeDsdByOtherVersion(DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOtherVersion(DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION_NAME);
    }

    @Test
    @MetamacMock({DATASET_VERSION_57_DRAFT_INITIAL_VERSION_NAME, DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION_NAME, DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION_NAME,
            DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION_NAME, DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION_NAME})
    public void testChangeDsdByOtherInInitialVersion() throws MetamacException {

        checkCanChangeDsdByOther(DATASET_VERSION_57_DRAFT_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOther(DATASET_VERSION_58_PRODUCTION_VALIDATION_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOther(DATASET_VERSION_59_DIFFUSION_VALIDATION_INITIAL_VERSION_NAME);

        checkCanChangeDsdByOther(DATASET_VERSION_60_VALIDATION_REJECTED_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOther(DATASET_VERSION_61_PUBLISHED_INITIAL_VERSION_NAME);
    }

    @Test
    @MetamacMock({DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION_NAME, DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION_NAME,
            DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION_NAME, DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION_NAME, DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION_NAME})
    public void testChangeDsdByOtherVersionNotInInitialVersion() throws MetamacException {

        checkCanChangeDsdByOtherVersion(DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOtherVersion(DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOtherVersion(DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION_NAME);

        checkCanChangeDsdByOtherVersion(DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOtherVersion(DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION_NAME);
    }

    @Test
    @MetamacMock({DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION_NAME, DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION_NAME,
            DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION_NAME, DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION_NAME, DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION_NAME})
    public void testChangeDsdByOtherNotInInitialVersion() throws MetamacException {

        checkCanNotChangeDsdByOther(DATASET_VERSION_62_DRAFT_NOT_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOther(DATASET_VERSION_63_PRODUCTION_VALIDATION_NOT_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOther(DATASET_VERSION_64_DIFFUSION_VALIDATION_NOT_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOther(DATASET_VERSION_65_VALIDATION_REJECTED_NOT_INITIAL_VERSION_NAME);

        checkCanNotChangeDsdByOther(DATASET_VERSION_66_PUBLISHED_NOT_INITIAL_VERSION_NAME);
    }

    @Test
    public void testCategorisationDtoToDo() throws MetamacException {
        CategorisationDto dto = StatisticalResourcesDtoMocks.mockCategorisationDto();
        Categorisation entity = datasetDto2DoMapper.categorisationDtoToDo(dto);
        assertEqualsCategorisation(dto, entity);
    }

    @Test
    @MetamacMock({CATEGORISATION_01_DATASET_VERSION_01_NAME})
    public void testCategorisationDtoToDoErrorUpdateUnsupported() throws MetamacException {
        Categorisation categorisation = categorisationMockFactory.retrieveMock(CATEGORISATION_01_DATASET_VERSION_01_NAME);
        CategorisationDto dto = new CategorisationDto();
        dto.setId(categorisation.getId());
        expectedMetamacException(new MetamacException(ServiceExceptionType.UNKNOWN, "Categorisation can not be updated"));
        datasetDto2DoMapper.categorisationDtoToDo(dto);
    }

    private void checkCanChangeDsdByOtherVersion(String datasetVersionMockName) throws MetamacException {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(datasetVersionMockName);

        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(datasetVersion);
        dto.setRelatedDsd(generateOtherDsdVersion(datasetVersion.getRelatedDsd()));

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        assertEquals(dto.getRelatedDsd().getUrn(), entity.getRelatedDsd().getUrn());
    }

    private void checkCanNotChangeDsdByOtherVersion(String datasetVersionMockName) throws MetamacException {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(datasetVersionMockName);

        String oldDsdUrn = datasetVersion.getRelatedDsd().getUrn();

        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(datasetVersion);
        dto.setRelatedDsd(generateOtherDsdVersion(datasetVersion.getRelatedDsd()));

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        assertEquals(oldDsdUrn, entity.getRelatedDsd().getUrn());
    }

    private void checkCanChangeDsdByOther(String datasetVersionMockName) throws MetamacException {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(datasetVersionMockName);

        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(datasetVersion);
        dto.setRelatedDsd(generateOtherDsd(datasetVersion.getRelatedDsd()));

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        assertEquals(dto.getRelatedDsd().getUrn(), entity.getRelatedDsd().getUrn());
    }

    private void checkCanNotChangeDsdByOther(String datasetVersionMockName) throws MetamacException {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(datasetVersionMockName);

        String oldDsdUrn = datasetVersion.getRelatedDsd().getUrn();

        DatasetVersionDto dto = buildDatasetVersionDtoFromDo(datasetVersion);
        dto.setRelatedDsd(generateOtherDsd(datasetVersion.getRelatedDsd()));

        DatasetVersion entity = datasetDto2DoMapper.datasetVersionDtoToDo(dto);

        assertEquals(oldDsdUrn, entity.getRelatedDsd().getUrn());
    }

    private DatasetVersionDto buildDatasetVersionDtoFromDo(DatasetVersion datasetVersion) {
        DatasetVersionDto dto = StatisticalResourcesDtoMocks.mockDatasetVersionDto(datasetVersion.getStatisticOfficiality());
        dto.setCode(datasetVersion.getSiemacMetadataStatisticalResource().getCode());
        dto.setUrn(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        dto.setOptimisticLockingVersion(datasetVersion.getVersion());
        dto.setId(datasetVersion.getId());
        return dto;
    }

    private ExternalItemDto generateOtherDsdVersion(ExternalItem relatedDsd) {
        ExternalItemDto dsd = StatisticalResourcesDtoMocks.mockDsdExternalItemDto();
        dsd.setCode(relatedDsd.getCode());
        dsd.setUrn(StatisticalResourcesDtoMocks.mockDsdUrn(relatedDsd.getCode(), "02.035"));
        assertFalse(dsd.getUrn().equals(relatedDsd.getUrn()));
        return dsd;
    }

    private ExternalItemDto generateOtherDsd(ExternalItem relatedDsd) {
        ExternalItemDto dsd = StatisticalResourcesDtoMocks.mockDsdExternalItemDto();
        String newCode = relatedDsd.getCode() + "_new";
        dsd.setCode(newCode);
        dsd.setUrn(StatisticalResourcesDtoMocks.mockDsdUrn(newCode, VersionUtil.PATTERN_XX_YYY_INITIAL_VERSION));
        assertFalse(dsd.getUrn().equals(relatedDsd.getUrn()));
        return dsd;
    }
}
