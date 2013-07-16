package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset;

import static org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants.SERVICE_CONTEXT;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CodeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ConceptType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Item;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CodeRepresentation;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CodeRepresentations;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DataStructureDefinition;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DatasetData;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DatasetMetadata;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimension;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionCode;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionCodes;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionRepresentation;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionRepresentations;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionType;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionsId;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Resources;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.SelectedLanguages;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.StatisticalResourceType;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleTypes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponentType;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_resources.rest.external.invocation.SrmRestExternalFacade;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.BaseDo2RestMapperV10Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.ConditionDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

@Component
public class DatasetsDo2RestMapperV10Impl extends BaseDo2RestMapperV10Impl implements DatasetsDo2RestMapperV10 {

    @Autowired
    private SrmRestExternalFacade            srmRestExternalFacade;

    @Autowired
    private DatasetService                   datasetService;

    @Autowired
    private DatasetRepositoriesServiceFacade statisticsDatasetRepositoriesServiceFacade;

    @Autowired
    private ConfigurationService             configurationService;

    @Override
    public Dataset toDataset(DatasetVersion source, Map<String, List<String>> selectedDimensions, List<String> selectedLanguages, boolean includeMetadata, boolean includeData) throws Exception {
        if (source == null) {
            return null;
        }
        selectedLanguages = toSelectedLanguages(selectedLanguages);

        Dataset target = new Dataset();
        target.setKind(RestExternalConstants.KIND_DATASET);
        target.setId(source.getSiemacMetadataStatisticalResource().getCode());
        target.setUrn(source.getSiemacMetadataStatisticalResource().getUrn());
        target.setSelfLink(toDatasetSelfLink(source));
        target.setName(toInternationalString(source.getSiemacMetadataStatisticalResource().getTitle(), selectedLanguages));
        target.setDescription(toInternationalString(source.getSiemacMetadataStatisticalResource().getDescription(), selectedLanguages));
        target.setParentLink(toDatasetParentLink(source));
        target.setChildLinks(toDatasetChildLinks(source));
        target.setSelectedLanguages(toLanguages(selectedLanguages));
        // TODO resto de metadatos públicos

        if (includeMetadata) {
            target.setMetadata(toDatasetMetadata(source, selectedLanguages));
        }
        if (includeData) {
            target.setData(toDatasetData(source, selectedLanguages, selectedDimensions));
        }
        return target;
    }

    /**
     * Create list with requested languages and default language in service
     */
    private List<String> toSelectedLanguages(List<String> sources) throws MetamacException {
        List<String> targets = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(sources)) {
            targets.addAll(sources);
        }
        String languageDefault = configurationService.retrieveLanguageDefault();
        if (!targets.contains(languageDefault)) {
            targets.add(languageDefault);
        }
        return targets;
    }

    private SelectedLanguages toLanguages(List<String> selectedLanguages) {
        SelectedLanguages target = new SelectedLanguages();
        target.getLanguages().addAll(selectedLanguages);
        target.setTotal(BigInteger.valueOf(target.getLanguages().size()));
        return target;
    }

    private DatasetMetadata toDatasetMetadata(DatasetVersion source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        DatasetMetadata target = new DatasetMetadata();
        // Dsd
        DataStructure dataStructure = srmRestExternalFacade.retrieveDataStructureByUrn(source.getRelatedDsd().getUrnInternal());
        target.setRelatedDsd(toDataStructureDefinition(source.getRelatedDsd(), dataStructure, selectedLanguages));
        // Dimensions
        target.setDimensions(toDimensions(source.getSiemacMetadataStatisticalResource().getUrn(), dataStructure, selectedLanguages));

        // Other metadata
        target.setGeographicCoverages(toResourcesExternalItemsSrm(source.getGeographicCoverage(), selectedLanguages));
        // TODO TemporalCoverage, title?
        target.setGeographicGranularities(toResourcesExternalItemsSrm(source.getGeographicGranularities(), selectedLanguages));
        target.setTemporalGranularities(toResourcesExternalItemsSrm(source.getTemporalGranularities(), selectedLanguages));
        target.setDateStart(toDate(source.getDateStart()));
        target.setDateEnd(toDate(source.getDateEnd()));
        target.setStatisticalUnit(toResourcesExternalItemsSrm(source.getStatisticalUnit(), selectedLanguages));
        target.setMeasures(toResourcesExternalItemsSrm(source.getMeasureCoverage(), selectedLanguages));
        target.setFormatExtentObservations(toBigInteger(source.getFormatExtentObservations()));
        target.setFormatExtentDimensions(toBigInteger(source.getFormatExtentDimensions()));
        target.setDateNextUpdate(toDate(source.getDateNextUpdate()));
        target.setUpdateFrequency(toResourceExternalItemSrm(source.getUpdateFrequency(), selectedLanguages));
        target.setStatisticOfficiality(toStatisticOfficiality(source.getStatisticOfficiality(), selectedLanguages));
        target.setBibliographicCitation(toInternationalString(source.getBibliographicCitation(), selectedLanguages));

        toDatasetMetadataStatisticalResource(source.getSiemacMetadataStatisticalResource(), target, selectedLanguages);

        return target;
    }

    // TODO item?
    private Item toStatisticOfficiality(StatisticOfficiality source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getDescription(), selectedLanguages));
        return target;
    }

    // TODO hacer específicos? TODO si no, pasar Resources a rest-api-1.0
    private Resources toResourcesExternalItemsSrm(List<ExternalItem> sources, List<String> selectedLanguages) throws MetamacException {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        Resources targets = new Resources();
        for (ExternalItem source : sources) {
            Resource target = toResourceExternalItemSrm(source, selectedLanguages);
            targets.getResources().add(target);
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }
    // TODO hacer específicos? TODO si no, pasar Resources a rest-api-1.0
    private Resources toResourcesExternalItemsStatisticalOperations(List<ExternalItem> sources, List<String> selectedLanguages) throws MetamacException {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        Resources targets = new Resources();
        for (ExternalItem source : sources) {
            Resource target = toResourceExternalItemStatisticalOperations(source, selectedLanguages);
            targets.getResources().add(target);
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    private void toDatasetMetadataStatisticalResource(SiemacMetadataStatisticalResource source, DatasetMetadata target, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return;
        }
        target.setLanguage(toResourceExternalItemSrm(source.getLanguage(), selectedLanguages));
        target.setLanguages(toResourcesExternalItemsSrm(source.getLanguages(), selectedLanguages));
        target.setStatisticalOperation(toResourceExternalItemStatisticalOperations(source.getStatisticalOperation(), selectedLanguages));
        target.setStatisticalOperationInstances(toResourcesExternalItemsStatisticalOperations(source.getStatisticalOperationInstances(), selectedLanguages));
        target.setSubtitle(toInternationalString(source.getSubtitle(), selectedLanguages));
        target.setTitleAlternative(toInternationalString(source.getTitleAlternative(), selectedLanguages));
        target.setAbstract(toInternationalString(source.getAbstractLogic(), selectedLanguages));
        target.setKeywords(toInternationalString(source.getKeywords(), selectedLanguages));
        target.setType(toStatisticalResourceType(source.getType()));
        target.setMaintainer(toResourceExternalItemSrm(source.getMaintainer(), selectedLanguages));
        target.setCreator(toResourceExternalItemSrm(source.getCreator(), selectedLanguages));
        target.setContributors(toResourcesExternalItemsSrm(source.getContributor(), selectedLanguages));
        target.setCreatedDate(toDate(source.getResourceCreatedDate()));
        target.setLastUpdate(toDate(source.getLastUpdate()));
        target.setConformsTo(toInternationalString(source.getConformsTo(), selectedLanguages));
        target.setPublishers(toResourcesExternalItemsSrm(source.getPublisher(), selectedLanguages));
        target.setPublisherContributors(toResourcesExternalItemsSrm(source.getPublisherContributor(), selectedLanguages));
        target.setMediators(toResourcesExternalItemsSrm(source.getMediator(), selectedLanguages));
        target.setNewnessUntilDate(toDate(source.getNewnessUntilDate()));

        // TODO resto de metadatos
        // SOURCE
        // REPLACES
        // IS_REPLACED_BY
        // REQUIRES
        // IS_REQUIRED_BY
        // HAS_PART
        // IS_PART_OF
        // IS_REFERENCE_BY
        // REFERENCES
        // IS_FORMAT_OF
        // HAS_FORMAT

        target.setRightsHolder(toResourceExternalItemSrm(source.getRightsHolder(), selectedLanguages));
        target.setCopyrightedDate(toDate(source.getCopyrightedDate()));
        target.setLicense(toInternationalString(source.getLicense(), selectedLanguages));
        target.setAccessRights(toInternationalString(source.getAccessRights(), selectedLanguages));

        // Lifecycle
        toDatasetMetadataLifeCycleStatisticalResource(source, target, selectedLanguages);

    }

    private void toDatasetMetadataLifeCycleStatisticalResource(LifeCycleStatisticalResource source, DatasetMetadata target, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return;
        }

        // TODO lifecycle?

        // Versionable
        toDatasetMetadataVersionableStatisticalResource(source, target, selectedLanguages);
    }

    private void toDatasetMetadataVersionableStatisticalResource(VersionableStatisticalResource source, DatasetMetadata target, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return;
        }
        target.setVersion(source.getVersionLogic());
        target.setVersionRationaleTypes(toVersionRationaleTypes(source.getVersionRationaleTypes(), selectedLanguages));
        target.setVersionRationale(toInternationalString(source.getVersionRationale(), selectedLanguages));
        target.setValidFrom(toDate(source.getValidFrom()));
        target.setValidTo(toDate(source.getValidTo()));
    }

    private VersionRationaleTypes toVersionRationaleTypes(List<VersionRationaleType> sources, List<String> selectedLanguages) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        VersionRationaleTypes targets = new VersionRationaleTypes();
        for (VersionRationaleType source : sources) {
            targets.getVersionRationaleTypes().add(toVersionRationaleType(source.getValue()));
        }
        targets.setTotal(BigInteger.valueOf(targets.getVersionRationaleTypes().size()));
        return targets;
    }

    private DataStructureDefinition toDataStructureDefinition(ExternalItem source, DataStructure dataStructure, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        DataStructureDefinition target = new DataStructureDefinition();
        toResourceExternalItemSrm(source, target, selectedLanguages);
        target.setHeading(toDimensionsId(dataStructure.getHeading()));
        target.setStub(toDimensionsId(dataStructure.getStub()));
        return target;
    }

    private DimensionsId toDimensionsId(org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimensions sources) {
        if (sources == null) {
            return null;
        }
        DimensionsId targets = new DimensionsId();
        for (LocalDimensionReferenceType source : sources.getDimensions()) {
            targets.getDimensionIds().add(toDimensionId(source));
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensionIds().size()));
        return targets;
    }

    private String toDimensionId(LocalDimensionReferenceType source) {
        if (source == null) {
            return null;
        }
        return source.getRef().getId();
    }

    private Dimensions toDimensions(String datasetVersionUrn, DataStructure dataStructure, List<String> selectedLanguages) throws MetamacException {

        List<String> dimensionsId = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, datasetVersionUrn);
        if (CollectionUtils.isEmpty(dimensionsId)) {
            return null;
        }

        Dimensions targets = new Dimensions();
        List<DsdDimension> dimensionsType = DsdProcessor.getDimensions(dataStructure);
        for (DsdDimension dsdDimension : dimensionsType) {
            targets.getDimensions().add(toDimension(datasetVersionUrn, dsdDimension, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensions().size()));
        return targets;
    }

    private Dimension toDimension(String datasetVersionUrn, DsdDimension source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        Dimension target = new Dimension();
        target.setId(source.getComponentId());
        target.setType(toDimensionType(source.getType()));

        Concept conceptIdentity = srmRestExternalFacade.retrieveConceptByUrn(source.getConceptIdentityUrn());
        target.setName(toInternationalString(conceptIdentity.getNames(), selectedLanguages));

        // Codes
        target.setDimensionCodes(toDimensionCodes(datasetVersionUrn, source, selectedLanguages));
        return target;
    }

    private DimensionCodes toDimensionCodes(String datasetVersionUrn, DsdDimension dimension, List<String> selectedLanguages) throws MetamacException {
        if (dimension == null) {
            return null;
        }
        DimensionCodes targets = new DimensionCodes();

        List<CodeDimension> coverages = datasetService.retrieveCoverageForDatasetVersionDimension(SERVICE_CONTEXT, datasetVersionUrn, dimension.getComponentId());
        if (CollectionUtils.isEmpty(coverages)) {
            return null;
        }
        Map<String, CodeDimension> coveragesById = new HashMap<String, CodeDimension>(coverages.size());
        for (CodeDimension coverage : coverages) {
            coveragesById.put(coverage.getIdentifier(), coverage);
        }

        if (dimension.getCodelistRepresentationUrn() != null) {
            toDimensionCodeFromCodelist(coveragesById, dimension.getCodelistRepresentationUrn(), targets, selectedLanguages);
        } else if (dimension.getConceptSchemeRepresentationUrn() != null) {
            toDimensionCodeFromConceptScheme(coveragesById, dimension.getConceptSchemeRepresentationUrn(), targets, selectedLanguages);
        } else if (dimension.getTimeTextFormatType() != null) {
            toDimensionCodeFromTimeTextFormatType(coveragesById, dimension.getTimeTextFormatType(), targets, selectedLanguages);
        }

        targets.setTotal(BigInteger.valueOf(targets.getDimensionCodes().size()));
        return targets;
    }

    private void toDimensionCodeFromCodelist(Map<String, CodeDimension> coveragesById, String codelistUrn, DimensionCodes targets, List<String> selectedLanguages) throws MetamacException {
        if (codelistUrn == null) {
            return;
        }
        Codelist codelist = srmRestExternalFacade.retrieveCodelistByUrn(codelistUrn);
        for (CodeType code : codelist.getCodes()) {
            String id = code.getId();
            if (!coveragesById.containsKey(id)) {
                // skip to include only codes in coverage
                continue;
            }
            targets.getDimensionCodes().add(toDimensionCode(code, selectedLanguages));
        }
    }

    private void toDimensionCodeFromConceptScheme(Map<String, CodeDimension> coveragesById, String conceptSchemeUrn, DimensionCodes targets, List<String> selectedLanguages) throws MetamacException {
        if (conceptSchemeUrn == null) {
            return;
        }
        ConceptScheme conceptScheme = srmRestExternalFacade.retrieveConceptSchemeByUrn(conceptSchemeUrn);
        for (ConceptType concept : conceptScheme.getConcepts()) {
            String id = concept.getId();
            if (!coveragesById.containsKey(id)) {
                // skip to include only concepts in coverage
                continue;
            }
            targets.getDimensionCodes().add(toDimensionCode(concept, selectedLanguages));
        }
    }

    private void toDimensionCodeFromTimeTextFormatType(Map<String, CodeDimension> coveragesById, TimeTextFormatType timeTextFormatType, DimensionCodes targets, List<String> selectedLanguages)
            throws MetamacException {
        if (timeTextFormatType == null) {
            return;
        }
        for (String coverageId : coveragesById.keySet()) {
            CodeDimension codeDimension = coveragesById.get(coverageId);
            targets.getDimensionCodes().add(toDimensionCode(codeDimension, selectedLanguages));
        }
    }

    private DimensionCode toDimensionCode(CodeType source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        DimensionCode target = new DimensionCode();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setName(toInternationalString(source.getNames(), selectedLanguages));
        if (source.getParent() != null) {
            target.setParent(source.getParent().getRef().getId());
        }
        // TODO resource
        return target;
    }

    private DimensionCode toDimensionCode(ConceptType source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        DimensionCode target = new DimensionCode();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setName(toInternationalString(source.getNames(), selectedLanguages));
        if (source.getParent() != null) {
            target.setParent(source.getParent().getRef().getId());
        }
        // TODO resource
        return target;
    }

    private DimensionCode toDimensionCode(CodeDimension source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        DimensionCode target = new DimensionCode();
        target.setId(source.getIdentifier());
        target.setUrn(null);
        {
            // TODO name
            InternationalString name = new InternationalString();
            for (String selectedLanguage : selectedLanguages) {
                LocalisedString nameLocalisedString = new LocalisedString();
                nameLocalisedString.setLang(selectedLanguage);
                nameLocalisedString.setValue(source.getTitle());
                name.getTexts().add(nameLocalisedString);
                target.setName(name);
            }
        }
        // TODO parent?
        // TODO resource
        return target;
    }

    private DimensionType toDimensionType(DsdComponentType source) {
        switch (source) {
            case OTHER:
                return DimensionType.DIMENSION;
            case SPATIAL:
                return DimensionType.GEOGRAPHIC_DIMENSION;
            case TEMPORAL:
                return DimensionType.TIME_DIMENSION;
            case MEASURE:
                return DimensionType.MEASURE_DIMENSION;
            default:
                logger.error("DsdComponentType unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private StatisticalResourceType toStatisticalResourceType(StatisticalResourceTypeEnum source) {
        switch (source) {
            case DATASET:
                return StatisticalResourceType.DATASET;
            case QUERY:
                return StatisticalResourceType.QUERY;
            case COLLECTION:
                return StatisticalResourceType.COLLECTION;
            default:
                logger.error("StatisticalResourceTypeEnum unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType toVersionRationaleType(VersionRationaleTypeEnum source) {
        switch (source) {
            case MAJOR_NEW_RESOURCE:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MAJOR_NEW_RESOURCE;
            case MAJOR_ESTIMATORS:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MAJOR_ESTIMATORS;
            case MAJOR_CATEGORIES:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MAJOR_CATEGORIES;
            case MAJOR_VARIABLES:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MAJOR_VARIABLES;
            case MAJOR_OTHER:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MAJOR_OTHER;
            case MINOR_ERRATA:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MINOR_ERRATA;
            case MINOR_METADATA:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MINOR_METADATA;
            case MINOR_DATA_UPDATE:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MINOR_DATA_UPDATE;
            case MINOR_SERIES_UPDATE:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MINOR_SERIES_UPDATE;
            case MINOR_OTHER:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MINOR_OTHER;
            default:
                logger.error("VersionRationaleTypeEnum unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }
    // TODO attributes
    private DatasetData toDatasetData(DatasetVersion source, List<String> languagesSelected, Map<String, List<String>> dimensionCodesSelected) throws Exception {
        if (source == null) {
            return null;
        }
        DatasetData target = new DatasetData();

        // Filter codeDimension // TODO validate codes in coverage?
        List<String> dimensions = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, source.getSiemacMetadataStatisticalResource().getUrn());
        Map<String, List<String>> dimensionsCodesSelectedEffective = buildDimensionsSelectedWithCodes(source, dimensionCodesSelected, dimensions);

        // Observations
        String observations = toDatasetDataObservations(source, dimensions, dimensionCodesSelected, dimensionsCodesSelectedEffective);
        target.setObservations(observations);

        // Dimensions
        // List<List<String>> dataFormat = new ArrayList<List<String>>();
        // dataFormat.add(new ArrayList<String>(dimensions.size()));
        // dataFormat.add(new ArrayList<String>(dimensions.size()));
        target.setDimensions(new DimensionRepresentations()); // TODO método privado?

        for (String dimension : dimensions) {
            DimensionRepresentation representation = new DimensionRepresentation();
            representation.setDimensionId(dimension);
            representation.setRepresentations(new CodeRepresentations());

            int codesSize = 0;
            for (String dimensionCode : dimensionsCodesSelectedEffective.get(dimension)) {
                CodeRepresentation codeRepresentation = new CodeRepresentation();
                codeRepresentation.setCode(dimensionCode);
                codeRepresentation.setIndex(codesSize);
                representation.getRepresentations().getRepresentations().add(codeRepresentation);
                codesSize++;
            }
            representation.getRepresentations().setTotal(BigInteger.valueOf(representation.getRepresentations().getRepresentations().size()));
            target.getDimensions().getDimensions().add(representation);
            // dataFormat.get(0).add(dimensionDto.getIdentifier());
            // dataFormat.get(1).add(String.valueOf(codesSize));
        }
        target.getDimensions().setTotal(BigInteger.valueOf(target.getDimensions().getDimensions().size()));

        return target;
    }

    /**
     * Build dimensions selected, with codes selected or all codes if codes are not selected to one dimension
     */
    private Map<String, List<String>> buildDimensionsSelectedWithCodes(DatasetVersion source, Map<String, List<String>> dimensionsSelected, List<String> dimensions) throws MetamacException {
        Map<String, List<String>> dimensionsCodesSelected = new HashMap<String, List<String>>();
        for (String dimension : dimensions) {
            List<String> dimensionCodes = dimensionsSelected.get(dimension);
            List<String> dimensionCodesSelected = new ArrayList<String>();
            if (CollectionUtils.isEmpty(dimensionCodes)) {
                // if dimension is not selected in query, retrieve all codes from coverage
                List<CodeDimension> codeDimensions = datasetService.retrieveCoverageForDatasetVersionDimension(SERVICE_CONTEXT, source.getSiemacMetadataStatisticalResource().getUrn(), dimension);
                for (CodeDimension codeDimension : codeDimensions) {
                    dimensionCodesSelected.add(codeDimension.getIdentifier());
                }
            } else {
                dimensionCodesSelected.addAll(dimensionCodes);
            }
            dimensionsCodesSelected.put(dimension, dimensionCodesSelected);
        }
        return dimensionsCodesSelected;
    }

    /**
     * Retrieve observations of selected code
     */
    private String toDatasetDataObservations(DatasetVersion source, List<String> dimensions, Map<String, List<String>> dimensionsSelected, Map<String, List<String>> dimensionsCodesSelectedEffective)
            throws Exception {

        // Search observations in repository
        List<ConditionDimensionDto> conditions = generateConditions(dimensionsSelected);
        Map<String, ObservationExtendedDto> datas = statisticsDatasetRepositoriesServiceFacade.findObservationsExtendedByDimensions(source.getDatasetRepositoryId(), conditions);

        // Observation Size
        int sizeObservation = 1;
        for (String dimension : dimensionsCodesSelectedEffective.keySet()) {
            sizeObservation = sizeObservation * dimensionsCodesSelectedEffective.get(dimension).size();
        }
        // OBSERVATIONS (return sorted) // TODO método privado
        List<String> dataObservations = new ArrayList<String>(sizeObservation);
        Stack<OrderingStackElement> stack = new Stack<OrderingStackElement>();
        stack.push(new OrderingStackElement(StringUtils.EMPTY, -1));
        ArrayList<String> entryId = new ArrayList<String>(dimensions.size());
        for (int i = 0; i < dimensions.size(); i++) {
            entryId.add(i, StringUtils.EMPTY);
        }

        int lastDimension = dimensions.size() - 1;
        int current = 0;
        while (stack.size() > 0) {
            // POP
            OrderingStackElement elem = stack.pop();
            int elemDimension = elem.getDimNum();
            String elemCode = elem.getCodeId();

            // The first time we don't need a hash (#)
            if (elemDimension != -1) {
                entryId.set(elemDimension, elemCode);
            }

            // The entry is complete
            if (elemDimension == lastDimension) {
                String id = StringUtils.join(entryId, "#");

                // We have the full entry here
                ObservationExtendedDto value = datas.get(id);
                if (value != null) {
                    dataObservations.add(value.getPrimaryMeasure());
                    // TODO atributos
                } else {
                    dataObservations.add(null); // Return observation null
                }
                entryId.set(elemDimension, StringUtils.EMPTY);
                current++;
            } else {
                String dimension = dimensions.get(elemDimension + 1);
                List<String> dimensionCodes = dimensionsCodesSelectedEffective.get(dimension);
                for (int i = dimensionCodes.size() - 1; i >= 0; i--) {
                    OrderingStackElement temp = new OrderingStackElement(dimensionCodes.get(i), elemDimension + 1);
                    stack.push(temp);
                }
            }
        }
        return StringUtils.join(dataObservations.iterator(), " | ");
    }

    private List<ConditionDimensionDto> generateConditions(Map<String, List<String>> dimensions) {
        List<ConditionDimensionDto> conditionDimensionDtos = new ArrayList<ConditionDimensionDto>();
        for (Map.Entry<String, List<String>> entry : dimensions.entrySet()) {
            ConditionDimensionDto conditionDimensionDto = new ConditionDimensionDto();
            conditionDimensionDto.setDimensionId(entry.getKey());
            for (String value : entry.getValue()) {
                conditionDimensionDto.getCodesDimension().add(value);
            }
            conditionDimensionDtos.add(conditionDimensionDto);
        }
        return conditionDimensionDtos;
    }

    private ResourceLink toDatasetParentLink(DatasetVersion source) {
        return toDatasetsSelfLink(null, null, null);
    }

    private ChildLinks toDatasetChildLinks(DatasetVersion source) {
        // nothing
        return null;
    }

    private ResourceLink toDatasetsSelfLink(String agencyID, String resourceID, String version) {
        return toResourceLink(RestExternalConstants.KIND_DATASETS, toDatasetsLink(agencyID, resourceID, version));
    }

    private String toDatasetsLink(String agencyID, String resourceID, String version) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_DATASETS;
        return toStatisticalResourceLink(resourceSubpath, agencyID, resourceID, version);
    }

    private ResourceLink toDatasetSelfLink(DatasetVersion source) {
        return toResourceLink(RestExternalConstants.KIND_DATASET, toDatasetLink(source));
    }

    private String toDatasetLink(DatasetVersion source) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_DATASETS;
        return toStatisticalResourceLink(resourceSubpath, source.getSiemacMetadataStatisticalResource());
    }

    private class OrderingStackElement {

        private String codeId = null;
        private int    dimNum = -1;

        public OrderingStackElement(String codeId, int dimNum) {
            super();
            this.codeId = codeId;
            this.dimNum = dimNum;
        }

        public String getCodeId() {
            return codeId;
        }

        public int getDimNum() {
            return dimNum;
        }
    }
}