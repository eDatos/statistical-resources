package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
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
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticOfficialityMockFactory.STATISTIC_OFFICIALITY_01_BASIC_NAME;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
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
