package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;


public class StatisticalResourcesDtoMocks extends MetamacMocks {
    private static final String URN_RELATED_RESOURCE_MOCK = "urn:lorem.ipsum.dolor.infomodel.package.Resource=" + mockString(10);

    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------

    public static QueryVersionDto mockQueryVersionDto(DatasetVersion datasetVersion) {
        QueryVersionDto queryVersionDto = new QueryVersionDto();

        mockLifeCycleStatisticalResourceDto(queryVersionDto);

        // code is not setting in nameable becasuse some resources have generated code
        queryVersionDto.setCode(mockString(8));

        queryVersionDto.setRelatedDatasetVersion(mockPersistedRelatedResourceDatasetVersionDto(datasetVersion));
        queryVersionDto.setType(QueryTypeEnum.FIXED);

        Map<String, List<CodeItemDto>> selection = new HashMap<String, List<CodeItemDto>>();
        selection.put("SEX", Arrays.asList(new CodeItemDto("FEMALE", "Female")));
        selection.put("REGION", Arrays.asList(new CodeItemDto("TENERIFE", "Tenerife"), new CodeItemDto("LA_GOMERA", "La gomera")));
        queryVersionDto.setSelection(selection);

        return queryVersionDto;
    }

    // -----------------------------------------------------------------
    // DATASOURCES
    // -----------------------------------------------------------------

    public static DatasourceDto mockDatasourceDto() {
        DatasourceDto datasourceDto = new DatasourceDto();

        mockIdentifiableStatisticalResourceDto(datasourceDto);

        // TODO: Esto debe eliminarse cuando el código pase a ser generado automáticamente
        // code is not setting in nameable becasuse some resources have generated code
        datasourceDto.setCode(mockString(8));

        datasourceDto.setDatasetVersionUrn(URN_RELATED_RESOURCE_MOCK);

        return datasourceDto;
    }

    // -----------------------------------------------------------------
    // DATASETS
    // -----------------------------------------------------------------

    public static DatasetVersionDto mockDatasetVersionDto(StatisticOfficiality officiality) {
        DatasetVersionDto datasetVersionDto = new DatasetVersionDto();

        datasetVersionDto.addGeographicGranularity(mockCodeExternalItemDto());
        datasetVersionDto.addGeographicGranularity(mockCodeExternalItemDto());

        datasetVersionDto.addTemporalGranularity(mockCodeExternalItemDto());
        datasetVersionDto.addTemporalGranularity(mockCodeExternalItemDto());

        datasetVersionDto.setDateStart(mockDate());
        datasetVersionDto.setDateEnd(mockDate());

        datasetVersionDto.addStatisticalUnit(mockConceptExternalItemDto());
        datasetVersionDto.addStatisticalUnit(mockConceptExternalItemDto());

        datasetVersionDto.setRelatedDsd(mockDsdExternalItemDto());

        datasetVersionDto.setFormatExtentDimensions(5);
        datasetVersionDto.setFormatExtentObservations(8);

        datasetVersionDto.setDateNextUpdate(mockDate());
        datasetVersionDto.setUpdateFrequency(mockCodeExternalItemDto());
        datasetVersionDto.setStatisticOfficiality(createStatisticOfficialityDtoFromDo(officiality));
        datasetVersionDto.setBibliographicCitation(mockInternationalStringDto());

        mockSiemacMetadataStatisticalResource(datasetVersionDto, StatisticalResourceTypeEnum.DATASET);

        return datasetVersionDto;
    }

    // -----------------------------------------------------------------
    // PUBLICATIONS
    // -----------------------------------------------------------------

    public static PublicationVersionDto mockPublicationVersionDto() {
        PublicationVersionDto publicationVersionDto = new PublicationVersionDto();

        publicationVersionDto.setFormatExtentResources(RandomUtils.nextInt(999));

        mockSiemacMetadataStatisticalResource(publicationVersionDto, StatisticalResourceTypeEnum.COLLECTION);

        return publicationVersionDto;
    }

    public static ChapterDto mockChapterDto() {
        ChapterDto chapterDto = mockChapterDtoWithParent(null);

        return chapterDto;
    }

    public static ChapterDto mockChapterDtoWithParent(String parentChapterUrn) {
        ChapterDto chapterDto = new ChapterDto();

        chapterDto.setOrderInLevel(Long.valueOf(2));
        chapterDto.setParentChapterUrn(parentChapterUrn);

        mockNameableStatisticalResorceDto(chapterDto);

        return chapterDto;
    }

    public static CubeDto mockDatasetCubeDto(String datasetUrn) {
        CubeDto cubeDto = mockCubeDto(null, datasetUrn, null);
        return cubeDto;
    }

    public static CubeDto mockDatasetCubeDtoWithParent(String parentChapterUrn, String datasetUrn) {
        CubeDto cubeDto = mockCubeDto(parentChapterUrn, datasetUrn, null);
        return cubeDto;
    }

    public static CubeDto mockQueryCubeDto(String queryUrn) {
        CubeDto cubeDto = mockCubeDto(null, null, queryUrn);
        return cubeDto;
    }

    public static CubeDto mockQueryCubeDtoWithParent(String parentChapterUrn, String queryUrn) {
        CubeDto cubeDto = mockCubeDto(parentChapterUrn, null, queryUrn);
        return cubeDto;
    }

    private static CubeDto mockCubeDto(String parentChapterUrn, String datasetUrn, String queryUrn) {
        CubeDto cubeDto = new CubeDto();

        cubeDto.setDatasetUrn(datasetUrn);
        cubeDto.setQueryUrn(queryUrn);
        cubeDto.setOrderInLevel(Long.valueOf(2));
        cubeDto.setParentChapterUrn(parentChapterUrn);

        mockNameableStatisticalResorceDto(cubeDto);

        return cubeDto;
    }

    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------

    private static void mockSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto, StatisticalResourceTypeEnum type) {
        siemacMetadataStatisticalResourceDto.setLanguage(mockCodeExternalItemDto());
        siemacMetadataStatisticalResourceDto.addLanguage(mockCodeExternalItemDto());
        siemacMetadataStatisticalResourceDto.addLanguage(mockCodeExternalItemDto());

        // siemacMetadataStatisticalResourceDto.setStatisticalOperation(mockStatisticalOperationItem());

        siemacMetadataStatisticalResourceDto.getStatisticalOperationInstances().clear();
        siemacMetadataStatisticalResourceDto.getStatisticalOperationInstances().add(mockStatisticalOperationInstanceExternalItemDto());

        siemacMetadataStatisticalResourceDto.setSubtitle(mockInternationalStringDto());
        siemacMetadataStatisticalResourceDto.setTitleAlternative(mockInternationalStringDto());
        siemacMetadataStatisticalResourceDto.setAbstractLogic(mockInternationalStringDto());
        siemacMetadataStatisticalResourceDto.setKeywords(mockInternationalStringDto());
        siemacMetadataStatisticalResourceDto.setType(type);

        siemacMetadataStatisticalResourceDto.setCreator(mockOrganizationUnitExternalItemDto());
        siemacMetadataStatisticalResourceDto.addContributor(mockOrganizationUnitExternalItemDto());
        siemacMetadataStatisticalResourceDto.setResourceCreatedDate(mockDate());
        siemacMetadataStatisticalResourceDto.setLastUpdate(mockDate());
        siemacMetadataStatisticalResourceDto.setConformsTo(mockInternationalStringDto());
        siemacMetadataStatisticalResourceDto.setConformsToInternal(mockInternationalStringDto());

        siemacMetadataStatisticalResourceDto.addPublisher(mockOrganizationUnitExternalItemDto());
        siemacMetadataStatisticalResourceDto.addPublisherContributor(mockOrganizationUnitExternalItemDto());
        siemacMetadataStatisticalResourceDto.addMediator(mockOrganizationUnitExternalItemDto());
        siemacMetadataStatisticalResourceDto.setNewnessUntilDate(mockDate());

        // TODO: mock Related resources
        siemacMetadataStatisticalResourceDto.setReplacesVersion(null);
        siemacMetadataStatisticalResourceDto.setIsReplacedByVersion(null);
        siemacMetadataStatisticalResourceDto.addRequire(null);
        siemacMetadataStatisticalResourceDto.addIsRequiredBy(null);
        siemacMetadataStatisticalResourceDto.addHasPart(null);
        siemacMetadataStatisticalResourceDto.addIsPartOf(null);

        siemacMetadataStatisticalResourceDto.setCommonMetadata(mockCommonMetadataExternalItemDto());
        siemacMetadataStatisticalResourceDto.setCopyrightedDate(mockDate());
        siemacMetadataStatisticalResourceDto.setAccessRights(mockInternationalStringDto());

        mockLifeCycleStatisticalResourceDto(siemacMetadataStatisticalResourceDto);
    }

    private static void mockLifeCycleStatisticalResourceDto(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {

        lifeCycleStatisticalResourceDto.setCreationDate(new DateTime().toDate());
        lifeCycleStatisticalResourceDto.setCreationUser(mockString(10));
        lifeCycleStatisticalResourceDto.setProductionValidationDate(new DateTime().toDate());
        lifeCycleStatisticalResourceDto.setProductionValidationUser(mockString(10));
        lifeCycleStatisticalResourceDto.setDiffusionValidationDate(new DateTime().toDate());
        lifeCycleStatisticalResourceDto.setDiffusionValidationUser(mockString(10));
        lifeCycleStatisticalResourceDto.setRejectValidationDate(new DateTime().toDate());
        lifeCycleStatisticalResourceDto.setRejectValidationUser(mockString(10));
        lifeCycleStatisticalResourceDto.setPublicationDate(new DateTime().toDate());
        lifeCycleStatisticalResourceDto.setPublicationUser(mockString(10));

        lifeCycleStatisticalResourceDto.setMaintainer(mockAgencyExternalItemDto());
        
        // Can not mock replacedByVersion and replacesVersion, we don't know the type of resource

        mockVersionableStatisticalResourceDto(lifeCycleStatisticalResourceDto);
    }

    private static void mockVersionableStatisticalResourceDto(VersionableStatisticalResourceDto versionableStatisticalResourceDto) {
        versionableStatisticalResourceDto.setVersionLogic("01.500");
        versionableStatisticalResourceDto.setNextVersionDate(new DateTime().toDate());
        versionableStatisticalResourceDto.setValidFrom(new DateTime().toDate());
        versionableStatisticalResourceDto.setValidTo(new DateTime().toDate());

        versionableStatisticalResourceDto.addVersionRationaleType(new VersionRationaleTypeDto(VersionRationaleTypeEnum.MINOR_OTHER));
        versionableStatisticalResourceDto.setVersionRationale(mockInternationalStringDto());

        versionableStatisticalResourceDto.setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);

        mockNameableStatisticalResorceDto(versionableStatisticalResourceDto);
    }

    private static void mockNameableStatisticalResorceDto(NameableStatisticalResourceDto nameableResourceDto) {
        nameableResourceDto.setTitle(mockInternationalStringDto());
        nameableResourceDto.setDescription(mockInternationalStringDto());

        mockIdentifiableStatisticalResourceDto(nameableResourceDto);
    }

    private static void mockIdentifiableStatisticalResourceDto(IdentifiableStatisticalResourceDto resource) {
        mockStatisticalResourceDto(resource);
    }

    private static void mockStatisticalResourceDto(StatisticalResourceDto resource) {
        // resource.setOperation(mockExternalItemDto(URN_RELATED_RESOURCE_MOCK, TypeExternalArtefactsEnum.STATISTICAL_OPERATION));
    }

    // EXTERNAL ITEMS DTOs

    public static ExternalItemDto mockExternalItemDto(String code, String codeNested, String uri, String urnProvider, String urn, TypeExternalArtefactsEnum type) {
        ExternalItemDto target = new ExternalItemDto();
        target.setCode(code);
        target.setCodeNested(codeNested);
        target.setUri(uri);
        target.setUrn(urn);
        target.setUrnProvider(urnProvider);
        target.setType(type);
        return target;
    }

    // RELATED RESOURCE DTOs

    public static RelatedResourceDto mockNotPersistedRelatedResourceDatasetVersionDto(DatasetVersion datasetVersion) {
        RelatedResourceDto resource = new RelatedResourceDto();
        populateNotPersistedRelatedResourceIdentifiable(resource, datasetVersion.getSiemacMetadataStatisticalResource(), TypeRelatedResourceEnum.DATASET_VERSION);
        return resource;
    }

    public static RelatedResourceDto mockPersistedRelatedResourceDatasetVersionDto(DatasetVersion datasetVersion) {
        RelatedResourceDto resource = new RelatedResourceDto();
        populatePersistedRelatedResourceNameable(resource, datasetVersion.getSiemacMetadataStatisticalResource(), TypeRelatedResourceEnum.DATASET_VERSION);
        return resource;
    }

    private static void populateNotPersistedRelatedResourceIdentifiable(RelatedResourceDto relatedDto, IdentifiableStatisticalResource identifiable, TypeRelatedResourceEnum type) {
        relatedDto.setUrn(identifiable.getUrn());
        relatedDto.setType(type);
    }

    private static void populatePersistedRelatedResourceIdentifiable(RelatedResourceDto relatedDto, IdentifiableStatisticalResource identifiable, TypeRelatedResourceEnum type) {
        relatedDto.setUrn(identifiable.getUrn());
        relatedDto.setType(type);
        relatedDto.setCode(identifiable.getCode());
    }

    private static void populatePersistedRelatedResourceNameable(RelatedResourceDto relatedDto, NameableStatisticalResource nameable, TypeRelatedResourceEnum type) {
        populatePersistedRelatedResourceIdentifiable(relatedDto, nameable, type);
        relatedDto.setTitle(createInternationalStringDtoFromDo(nameable.getTitle()));
    }

    // STATISTIC OFFICIALITY DTOs

    private static StatisticOfficialityDto createStatisticOfficialityDtoFromDo(StatisticOfficiality officiality) {
        StatisticOfficialityDto dto = new StatisticOfficialityDto();
        dto.setIdentifier(officiality.getIdentifier());
        dto.setUuid(officiality.getUuid());
        dto.setId(officiality.getId());
        dto.setDescription(createInternationalStringDtoFromDo(officiality.getDescription()));
        return dto;
    }

    private static InternationalStringDto createInternationalStringDtoFromDo(InternationalString intString) {
        InternationalStringDto dto = new InternationalStringDto();

        dto.setId(intString.getId());
        for (LocalisedString loc : intString.getTexts()) {
            LocalisedStringDto locDto = new LocalisedStringDto();
            locDto.setId(loc.getId());
            locDto.setIsUnmodifiable(loc.getIsUnmodifiable());
            locDto.setLabel(loc.getLabel());
            locDto.setLocale(loc.getLocale());
            locDto.setVersion(loc.getVersion());
            dto.addText(locDto);
        }
        return dto;
    }

    // UTILS

    private static Date mockDate() {
        return mockDateTime().toDate();
    }

}