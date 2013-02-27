package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceNextVersionEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

public class StatisticalResourcesDtoMocks extends MetamacMocks {

    private static final String URN_RELATED_RESOURCE_MOCK = "urn:lorem.ipsum.dolor.infomodel.package.Resource=" + mockString(10);
    
    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------

    public static QueryDto mockQueryDto(DatasetVersion datasetVersion) {
        QueryDto queryDto = new QueryDto();

        mockNameableStatisticalResorceDto(queryDto);
        queryDto.setDatasetVersion(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        queryDto.setType(QueryTypeEnum.FIXED);
        
        
        Map<String, Set<String>> selection = new HashMap<String, Set<String>>();
        selection.put("SEX", new HashSet<String>(Arrays.asList("FEMALE")));
        selection.put("REGION", new HashSet<String>(Arrays.asList("TENERIFE", "LA_GOMERA")));
        queryDto.setSelection(selection );

        return queryDto;
    }
    

    // -----------------------------------------------------------------
    // DATASOURCES
    // -----------------------------------------------------------------

    public static DatasourceDto mockDatasourceDto() {
        DatasourceDto datasourceDto = new DatasourceDto();

        datasourceDto.setDatasetVersionUrn(URN_RELATED_RESOURCE_MOCK);
        mockIdentifiableStatisticalResourceDto(datasourceDto);

        return datasourceDto;
    }

    // -----------------------------------------------------------------
    // DATASETS
    // -----------------------------------------------------------------

    public static DatasetDto mockDatasetDto(StatisticOfficiality officiality) {
        DatasetDto datasetDto = new DatasetDto();

        datasetDto.addGeographicGranularity(mockCodeExternalItemDto());
        datasetDto.addGeographicGranularity(mockCodeExternalItemDto());
        datasetDto.addGeographicCoverage(mockCodeExternalItemDto());
        datasetDto.addGeographicCoverage(mockCodeExternalItemDto());

        datasetDto.addTemporalGranularity(mockCodeExternalItemDto());
        datasetDto.addTemporalGranularity(mockCodeExternalItemDto());
        datasetDto.addTemporalCoverage(mockCodeExternalItemDto());
        datasetDto.addTemporalCoverage(mockCodeExternalItemDto());

        datasetDto.setDateStart(mockDate());
        datasetDto.setDateEnd(mockDate());

        datasetDto.addStatisticalUnit(mockConceptExternalItemDto());
        datasetDto.addStatisticalUnit(mockConceptExternalItemDto());

        datasetDto.addMeasure(mockConceptExternalItemDto());
        datasetDto.addMeasure(mockConceptExternalItemDto());

        datasetDto.setRelatedDsd(mockDsdExternalItemDto());

        datasetDto.setFormatExtentDimensions(5);
        datasetDto.setFormatExtentObservations(8);

        datasetDto.setDateNextUpdate(mockDate());
        datasetDto.setUpdateFrequency(mockCodeExternalItemDto());
        datasetDto.setStatisticOfficiality(createStatisticOfficialityDtoFromDo(officiality));
        datasetDto.setBibliographicCitation(mockInternationalStringDto());

        mockSiemacMetadataStatisticalResource(datasetDto, StatisticalResourceTypeEnum.DATASET);

        return datasetDto;
    }
    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------

    private static void mockSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto, StatisticalResourceTypeEnum type) {
        siemacMetadataStatisticalResourceDto.setLanguage(mockCodeExternalItemDto());
        siemacMetadataStatisticalResourceDto.addLanguage(siemacMetadataStatisticalResourceDto.getLanguage());
        siemacMetadataStatisticalResourceDto.addLanguage(mockCodeExternalItemDto());

        siemacMetadataStatisticalResourceDto.setStatisticalOperation(mockStatisticalOperationItem());
        siemacMetadataStatisticalResourceDto.setStatisticalOperationInstance(mockStatisticalOperationInstanceItem());

        siemacMetadataStatisticalResourceDto.setSubtitle(mockInternationalStringDto());
        siemacMetadataStatisticalResourceDto.setTitleAlternative(mockInternationalStringDto());
        siemacMetadataStatisticalResourceDto.setAbstractLogic(mockInternationalStringDto());
        // TODO: KEYWORDS
        siemacMetadataStatisticalResourceDto.setType(type);

        siemacMetadataStatisticalResourceDto.setMaintainer(mockAgencyExternalItemDto());
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

        siemacMetadataStatisticalResourceDto.setReplaces(null);
        siemacMetadataStatisticalResourceDto.setReplacesVersion(null);
        siemacMetadataStatisticalResourceDto.setIsReplacedBy(null);
        siemacMetadataStatisticalResourceDto.setIsReplacedByVersion(null);
        siemacMetadataStatisticalResourceDto.addRequire(null);
        siemacMetadataStatisticalResourceDto.addIsRequiredBy(null);
        siemacMetadataStatisticalResourceDto.addHasPart(null);
        siemacMetadataStatisticalResourceDto.addIsPartOf(null);

        siemacMetadataStatisticalResourceDto.setRightsHolder(mockOrganizationUnitExternalItemDto());
        siemacMetadataStatisticalResourceDto.setCopyrightedDate(mockDate());
        siemacMetadataStatisticalResourceDto.setLicense(mockInternationalStringDto());
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
        lifeCycleStatisticalResourceDto.setInternalPublicationDate(new DateTime().toDate());
        lifeCycleStatisticalResourceDto.setInternalPublicationUser(mockString(10));
        lifeCycleStatisticalResourceDto.setExternalPublicationDate(new DateTime().toDate());
        lifeCycleStatisticalResourceDto.setExternalPublicationUser(mockString(10));
        lifeCycleStatisticalResourceDto.setExternalPublicationFailedDate(new DateTime().toDate());
        lifeCycleStatisticalResourceDto.setExternalPublicationFailed(true);

        // Cant mock replacedByVersion and replacesVersion, we don't know the type of resource

        mockVersionableStatisticalResourceDto(lifeCycleStatisticalResourceDto);
    }

    private static void mockVersionableStatisticalResourceDto(VersionableStatisticalResourceDto versionableStatisticalResourceDto) {
        versionableStatisticalResourceDto.setVersionLogic("01.500");
        versionableStatisticalResourceDto.setNextVersionDate(new DateTime().toDate());
        versionableStatisticalResourceDto.setValidFrom(new DateTime().toDate());
        versionableStatisticalResourceDto.setValidTo(new DateTime().toDate());

        versionableStatisticalResourceDto.setVersionRationaleType(StatisticalResourceVersionRationaleTypeEnum.MINOR_OTHER);
        versionableStatisticalResourceDto.setVersionRationale(mockInternationalStringDto());

        versionableStatisticalResourceDto.setNextVersion(StatisticalResourceNextVersionEnum.NON_SCHEDULED_UPDATE);
        versionableStatisticalResourceDto.setIsLastVersion(true);

        mockNameableStatisticalResorceDto(versionableStatisticalResourceDto);
    }

    private static void mockNameableStatisticalResorceDto(NameableStatisticalResourceDto nameableResourceDto) {
        nameableResourceDto.setTitle(mockInternationalStringDto());
        nameableResourceDto.setDescription(mockInternationalStringDto());

        mockIdentifiableStatisticalResourceDto(nameableResourceDto);
    }

    private static void mockIdentifiableStatisticalResourceDto(IdentifiableStatisticalResourceDto resource) {
        resource.setCode("resource-" + mockString(10));

        mockStatisticalResourceDto(resource);
    }

    private static void mockStatisticalResourceDto(StatisticalResourceDto resource) {
        // resource.setOperation(mockExternalItemDto(URN_RELATED_RESOURCE_MOCK, TypeExternalArtefactsEnum.STATISTICAL_OPERATION));
    }


    // EXTERNAL ITEM DTOs

    public static ExternalItemDto mockStatisticalOperationItem() {
        return createExternalItemDtoMockFromDoMock(StatisticalResourcesDoMocks.mockStatisticalOperationItem());
    }

    public static ExternalItemDto mockStatisticalOperationInstanceItem() {
        return createExternalItemDtoMockFromDoMock(StatisticalResourcesDoMocks.mockStatisticalOperationInstanceItem());
    }

    public static ExternalItemDto mockAgencyExternalItemDto() {
        return createExternalItemDtoMockFromDoMock(StatisticalResourcesDoMocks.mockAgencyExternalItem());
    }

    public static ExternalItemDto mockOrganizationUnitExternalItemDto() {
        return createExternalItemDtoMockFromDoMock(StatisticalResourcesDoMocks.mockOrganizationUnitExternalItem());
    }

    public static ExternalItemDto mockConceptExternalItemDto() {
        return createExternalItemDtoMockFromDoMock(StatisticalResourcesDoMocks.mockConceptExternalItem());
    }

    public static ExternalItemDto mockConceptSchemeExternalItemDto() {
        return createExternalItemDtoMockFromDoMock(StatisticalResourcesDoMocks.mockConceptSchemeExternalItem());
    }

    public static ExternalItemDto mockCodeListSchemeExternalItemDto() {
        return createExternalItemDtoMockFromDoMock(StatisticalResourcesDoMocks.mockCodeListSchemeExternalItem());
    }

    public static ExternalItemDto mockCodeExternalItemDto() {
        return createExternalItemDtoMockFromDoMock(StatisticalResourcesDoMocks.mockCodeExternalItem());
    }

    public static ExternalItemDto mockDsdExternalItemDto() {
        return createExternalItemDtoMockFromDoMock(StatisticalResourcesDoMocks.mockDsdExternalItem());
    }

    private static ExternalItemDto createExternalItemDtoMockFromDoMock(ExternalItem item) {
        ExternalItemDto itemDto = new ExternalItemDto(item.getCode(), item.getUri(), item.getUrn(), item.getType());
        itemDto.setVersion(Long.valueOf(0));
        return itemDto;
    }

    public static RelatedResourceDto mockDatasetVersionRelatedDto() {
        RelatedResourceDto resource = createRelatedResourceDtoMockFromDoMock(StatisticalResourcesDoMocks.mockDatasetVersionRelated());
        return resource;
    }

    private static RelatedResourceDto createRelatedResourceDtoMockFromDoMock(RelatedResource item) {
        RelatedResourceDto itemDto = new RelatedResourceDto(item.getCode(), item.getUri(), item.getUrn(), item.getType());
        itemDto.setVersion(Long.valueOf(0));
        return itemDto;
    }
    
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