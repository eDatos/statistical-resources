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
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleComponentTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Item;
import org.siemac.metamac.rest.common.v1_0.domain.Items;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.mapper.SculptorCriteria2RestCriteria;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CodeRepresentation;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CodeRepresentations;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Data;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DataStructureDefinition;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DatasetMetadata;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Datasets;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimension;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionRepresentation;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionRepresentations;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionType;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionValues;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionsId;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.EnumeratedDimensionValue;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.EnumeratedDimensionValues;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.NonEnumeratedDimensionValue;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.NonEnumeratedDimensionValues;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResource;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionVisualisation;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ShowDecimalPrecision;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponentType;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_resources.rest.external.invocation.SrmRestExternalFacade;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.CommonDo2RestMapperV10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.ConditionDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

@Component
public class DatasetsDo2RestMapperV10Impl implements DatasetsDo2RestMapperV10 {

    private static final Logger              logger = LoggerFactory.getLogger(DatasetsDo2RestMapperV10Impl.class);

    @Autowired
    private SrmRestExternalFacade            srmRestExternalFacade;

    @Autowired
    private DatasetService                   datasetService;

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    @Autowired
    private CommonDo2RestMapperV10           commonDo2RestMapper;

    @Override
    public Datasets toDatasets(PagedResult<DatasetVersion> sources, String agencyID, String resourceID, String query, String orderBy, Integer limit, List<String> selectedLanguages) {

        Datasets targets = new Datasets();
        targets.setKind(RestExternalConstants.KIND_DATASETS);

        // Pagination
        String baseLink = toDatasetsLink(agencyID, resourceID, null);
        SculptorCriteria2RestCriteria.toPagedResult(sources, targets, query, orderBy, limit, baseLink);

        // Values
        for (DatasetVersion source : sources.getValues()) {
            Resource target = toResource(source, selectedLanguages);
            targets.getDatasets().add(target);
        }
        return targets;
    }

    @Override
    public Dataset toDataset(DatasetVersion source, Map<String, List<String>> selectedDimensions, List<String> selectedLanguages, boolean includeMetadata, boolean includeData) throws Exception {
        if (source == null) {
            return null;
        }
        Dataset target = new Dataset();
        target.setKind(RestExternalConstants.KIND_DATASET);
        target.setId(source.getSiemacMetadataStatisticalResource().getCode());
        target.setUrn(source.getSiemacMetadataStatisticalResource().getUrn());
        target.setSelfLink(toDatasetSelfLink(source));
        target.setName(commonDo2RestMapper.toInternationalString(source.getSiemacMetadataStatisticalResource().getTitle(), selectedLanguages));
        target.setDescription(commonDo2RestMapper.toInternationalString(source.getSiemacMetadataStatisticalResource().getDescription(), selectedLanguages));
        target.setParentLink(toDatasetParentLink(source));
        target.setChildLinks(toDatasetChildLinks(source));
        target.setSelectedLanguages(commonDo2RestMapper.toLanguages(selectedLanguages));

        if (includeMetadata) {
            target.setMetadata(toDatasetMetadata(source, selectedLanguages));
        }
        if (includeData) {
            target.setData(toDatasetData(source, selectedLanguages, selectedDimensions));
        }
        return target;
    }

    @Override
    public Resource toResource(DatasetVersion source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getSiemacMetadataStatisticalResource().getCode());
        target.setUrn(source.getSiemacMetadataStatisticalResource().getUrn());
        target.setKind(RestExternalConstants.KIND_DATASET);
        target.setSelfLink(toDatasetSelfLink(source));
        target.setName(commonDo2RestMapper.toInternationalString(source.getSiemacMetadataStatisticalResource().getTitle(), selectedLanguages));
        return target;
    }

    private DatasetMetadata toDatasetMetadata(DatasetVersion source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        DatasetMetadata target = new DatasetMetadata();

        DataStructure dataStructure = srmRestExternalFacade.retrieveDataStructureByUrn(source.getRelatedDsd().getUrn());
        List<DsdDimension> dimensionsType = DsdProcessor.getDimensions(dataStructure);

        // DatasetResource
        target.setGeographicCoverages(commonDo2RestMapper.toResourcesExternalItemsSrm(source.getGeographicCoverage(), selectedLanguages));
        target.setTemporalCoverages(toTemporalCoverages(source.getTemporalCoverage(), selectedLanguages));
        target.setMeasureCoverages(commonDo2RestMapper.toResourcesExternalItemsSrm(source.getMeasureCoverage(), selectedLanguages));
        target.setGeographicGranularities(commonDo2RestMapper.toResourcesExternalItemsSrm(source.getGeographicGranularities(), selectedLanguages));
        target.setTemporalGranularities(commonDo2RestMapper.toResourcesExternalItemsSrm(source.getTemporalGranularities(), selectedLanguages));
        target.setDateStart(commonDo2RestMapper.toDate(source.getDateStart()));
        target.setDateEnd(commonDo2RestMapper.toDate(source.getDateEnd()));
        target.setStatisticalUnit(commonDo2RestMapper.toResourcesExternalItemsSrm(source.getStatisticalUnit(), selectedLanguages));
        target.setRelatedDsd(toDataStructureDefinition(source.getRelatedDsd(), dataStructure, selectedLanguages));
        target.setDimensions(toDimensions(dataStructure, dimensionsType, source.getSiemacMetadataStatisticalResource().getUrn(), selectedLanguages));
        target.setFormatExtentObservations(source.getFormatExtentObservations());
        target.setFormatExtentDimensions(source.getFormatExtentDimensions());
        target.setDateNextUpdate(commonDo2RestMapper.toDate(source.getDateNextUpdate()));
        target.setUpdateFrequency(commonDo2RestMapper.toResourceExternalItemSrm(source.getUpdateFrequency(), selectedLanguages));
        target.setStatisticOfficiality(toStatisticOfficiality(source.getStatisticOfficiality(), selectedLanguages));
        target.setBibliographicCitation(toBibliographicCitation(source.getBibliographicCitation(), toDatasetLink(source), selectedLanguages));

        // StatisticalResource and other
        commonDo2RestMapper.toMetadataStatisticalResource(source.getSiemacMetadataStatisticalResource(), target, selectedLanguages);
        return target;
    }

    private Items toTemporalCoverages(List<TemporalCode> sources, List<String> selectedLanguages) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        Items targets = new Items();
        for (TemporalCode source : sources) {
            targets.getItems().add(toTemporalCoverage(source, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getItems().size()));
        return targets;
    }

    private Item toTemporalCoverage(TemporalCode source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(commonDo2RestMapper.toInternationalString(source.getTitle(), selectedLanguages));
        return target;
    }

    private Item toStatisticOfficiality(StatisticOfficiality source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(commonDo2RestMapper.toInternationalString(source.getDescription(), selectedLanguages));
        return target;
    }

    private DataStructureDefinition toDataStructureDefinition(ExternalItem source, DataStructure dataStructure, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        DataStructureDefinition target = new DataStructureDefinition();
        commonDo2RestMapper.toResourceExternalItemSrm(source, target, selectedLanguages);
        target.setHeading(toDimensionsId(dataStructure.getHeading()));
        target.setStub(toDimensionsId(dataStructure.getStub()));
        target.setAutoOpen(dataStructure.isAutoOpen());
        target.setShowDecimals(dataStructure.getShowDecimals());
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

    private Dimensions toDimensions(DataStructure dataStructure, List<DsdDimension> sources, String datasetVersionUrn, List<String> selectedLanguages) throws MetamacException {

        List<String> dimensionsId = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, datasetVersionUrn);
        if (CollectionUtils.isEmpty(dimensionsId)) {
            return null;
        }

        // TODO pasar a DsdProcessor?
        Map<String, DimensionVisualisation> dimensionVisualisationByDimensionId = null;
        if (dataStructure.getDimensionVisualisations() != null && !CollectionUtils.isEmpty(dataStructure.getDimensionVisualisations().getDimensionVisualisations())) {
            dimensionVisualisationByDimensionId = new HashMap<String, DimensionVisualisation>(dataStructure.getDimensionVisualisations().getDimensionVisualisations().size());
            for (DimensionVisualisation dimensionVisualisation : dataStructure.getDimensionVisualisations().getDimensionVisualisations()) {
                dimensionVisualisationByDimensionId.put(dimensionVisualisation.getDimension().getRef().getId(), dimensionVisualisation);
            }
        }

        Dimensions targets = new Dimensions();
        for (DsdDimension source : sources) {
            DimensionVisualisation dimensionVisualisation = dimensionVisualisationByDimensionId != null ? dimensionVisualisationByDimensionId.get(source.getComponentId()) : null;
            targets.getDimensions().add(toDimension(datasetVersionUrn, dataStructure, source, dimensionVisualisation, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensions().size()));
        return targets;
    }

    private Dimension toDimension(String datasetVersionUrn, DataStructure dataStructure, DsdDimension source, DimensionVisualisation dimensionVisualisation, List<String> selectedLanguages)
            throws MetamacException {
        if (source == null) {
            return null;
        }
        Dimension target = new Dimension();
        target.setId(source.getComponentId());
        target.setType(toDimensionType(source.getType()));

        Concept conceptIdentity = srmRestExternalFacade.retrieveConceptByUrn(source.getConceptIdentityUrn());
        target.setName(commonDo2RestMapper.toInternationalString(conceptIdentity.getNames(), selectedLanguages));

        // Dimension values
        target.setDimensionValues(toDimensionValues(datasetVersionUrn, dataStructure, source, dimensionVisualisation, selectedLanguages));
        return target;
    }

    private DimensionValues toDimensionValues(String datasetVersionUrn, DataStructure dataStructure, DsdDimension dimension, DimensionVisualisation dimensionVisualisation,
            List<String> selectedLanguages) throws MetamacException {
        if (dimension == null) {
            return null;
        }
        List<CodeDimension> coverages = datasetService.retrieveCoverageForDatasetVersionDimension(SERVICE_CONTEXT, datasetVersionUrn, dimension.getComponentId());
        if (CollectionUtils.isEmpty(coverages)) {
            return null;
        }
        Map<String, CodeDimension> coveragesById = new HashMap<String, CodeDimension>(coverages.size());
        for (CodeDimension coverage : coverages) {
            coveragesById.put(coverage.getIdentifier(), coverage);
        }

        DimensionValues targets = null;
        if (dimension.getCodelistRepresentationUrn() != null) {
            targets = toEnumeratedDimensionValuesFromCodelist(coveragesById, dimension.getCodelistRepresentationUrn(), dimensionVisualisation, selectedLanguages);
        } else if (dimension.getConceptSchemeRepresentationUrn() != null) {
            targets = toEnumeratedDimensionValuesFromConceptScheme(coveragesById, dataStructure, dimension.getType(), dimension.getConceptSchemeRepresentationUrn(), selectedLanguages);
        } else if (dimension.getTimeTextFormatRepresentation() != null) {
            targets = toNonEnumeratedDimensionValuesFromTimeTextFormatType(coveragesById, dimension.getTimeTextFormatRepresentation(), selectedLanguages);
        } else if (dimension.getTextFormatRepresentation() != null) {
            targets = toNonEnumeratedDimensionValuesFromTextFormatType(coveragesById, dimension.getTextFormatRepresentation(), selectedLanguages);
        } else {
            logger.error("Dimension definition unsupported for dimension: " + dimension.getComponentId());
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }

        return targets;
    }

    private EnumeratedDimensionValues toEnumeratedDimensionValuesFromCodelist(Map<String, CodeDimension> coveragesById, String codelistUrn, DimensionVisualisation dimensionVisualisation,
            List<String> selectedLanguages) throws MetamacException {
        if (codelistUrn == null) {
            return null;
        }
        EnumeratedDimensionValues targets = new EnumeratedDimensionValues();
        String order = dimensionVisualisation != null ? dimensionVisualisation.getOrder() : null;
        String openness = dimensionVisualisation != null ? dimensionVisualisation.getOpenness() : null;
        Codes codes = srmRestExternalFacade.retrieveCodesByCodelistUrn(codelistUrn, order, openness);
        for (CodeResource code : codes.getCodes()) {
            String id = code.getId();
            if (!coveragesById.containsKey(id)) {
                // skip to include only codes in coverage
                continue;
            }
            targets.getValues().add(toEnumeratedDimensionValue(code, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private EnumeratedDimensionValues toEnumeratedDimensionValuesFromConceptScheme(Map<String, CodeDimension> coveragesById, DataStructure dataStructure, DsdComponentType componentType,
            String conceptSchemeUrn, List<String> selectedLanguages) throws MetamacException {
        if (conceptSchemeUrn == null) {
            return null;
        }
        EnumeratedDimensionValues targets = new EnumeratedDimensionValues();

        Map<String, Integer> showDecimalPrecisionsById = null;
        if (DsdComponentType.MEASURE.equals(componentType)) {
            if (dataStructure.getShowDecimalsPrecisions() != null && !CollectionUtils.isEmpty(dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions())) {
                showDecimalPrecisionsById = new HashMap<String, Integer>(dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions().size());
                for (ShowDecimalPrecision showDecimalPrecision : dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions()) {
                    showDecimalPrecisionsById.put(showDecimalPrecision.getConcept().getRef().getId(), showDecimalPrecision.getShowDecimals());
                }
            }
        }

        Concepts concepts = srmRestExternalFacade.retrieveConceptsByConceptSchemeByUrn(conceptSchemeUrn);
        for (ItemResourceInternal concept : concepts.getConcepts()) {
            String id = concept.getId();
            if (!coveragesById.containsKey(id)) {
                // skip to include only concepts in coverage
                continue;
            }
            targets.getValues().add(toEnumeratedDimensionValue(concept, showDecimalPrecisionsById, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private NonEnumeratedDimensionValues toNonEnumeratedDimensionValuesFromTimeTextFormatType(Map<String, CodeDimension> coveragesById, TimeTextFormatType timeTextFormatType,
            List<String> selectedLanguages) throws MetamacException {
        if (timeTextFormatType == null) {
            return null;
        }
        // note: timeTextFormatType definition is not necessary to define dimension values
        NonEnumeratedDimensionValues targets = new NonEnumeratedDimensionValues();
        for (String coverageId : coveragesById.keySet()) {
            CodeDimension codeDimension = coveragesById.get(coverageId);
            targets.getValues().add(toNonEnumeratedDimensionValue(codeDimension, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private NonEnumeratedDimensionValues toNonEnumeratedDimensionValuesFromTextFormatType(Map<String, CodeDimension> coveragesById, SimpleComponentTextFormatType textFormatType,
            List<String> selectedLanguages) throws MetamacException {
        if (textFormatType == null) {
            return null;
        }
        // note: textFormatType definition is not necessary to define dimension values
        NonEnumeratedDimensionValues targets = new NonEnumeratedDimensionValues();
        for (String coverageId : coveragesById.keySet()) {
            CodeDimension codeDimension = coveragesById.get(coverageId);
            targets.getValues().add(toNonEnumeratedDimensionValue(codeDimension, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private EnumeratedDimensionValue toEnumeratedDimensionValue(CodeResource source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        EnumeratedDimensionValue target = new EnumeratedDimensionValue();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setName(commonDo2RestMapper.toInternationalString(source.getName(), selectedLanguages));
        target.setParent(source.getParent());
        target.setKind(source.getKind());
        target.setSelfLink(source.getSelfLink());

        target.setOpen(source.isOpen());
        target.setOrder(source.getOrder());
        return target;
    }

    private EnumeratedDimensionValue toEnumeratedDimensionValue(ItemResourceInternal source, Map<String, Integer> showDecimalPrecisionsById, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        EnumeratedDimensionValue target = new EnumeratedDimensionValue();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setName(commonDo2RestMapper.toInternationalString(source.getName(), selectedLanguages));
        target.setParent(source.getParent());
        if (showDecimalPrecisionsById != null && showDecimalPrecisionsById.containsKey(source.getId())) {
            target.setShowDecimalsPrecision(showDecimalPrecisionsById.get(source.getId()));
        }
        target.setKind(source.getKind());
        target.setSelfLink(source.getSelfLink());
        return target;
    }

    private NonEnumeratedDimensionValue toNonEnumeratedDimensionValue(CodeDimension source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        NonEnumeratedDimensionValue target = new NonEnumeratedDimensionValue();
        target.setId(source.getIdentifier());
        target.setName(commonDo2RestMapper.toInternationalString(source.getTitle(), selectedLanguages));
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

    private Data toDatasetData(DatasetVersion source, List<String> languagesSelected, Map<String, List<String>> dimensionValuesSelected) throws Exception {
        if (source == null) {
            return null;
        }
        Data target = new Data();

        // Filter codeDimension // TODO validate codes in coverage?
        List<String> dimensions = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, source.getSiemacMetadataStatisticalResource().getUrn());
        Map<String, List<String>> dimensionsCodesSelectedEffective = buildDimensionsSelectedWithCodes(source, dimensionValuesSelected, dimensions);

        // Observations
        String observations = toDatasetDataObservations(source, dimensions, dimensionValuesSelected, dimensionsCodesSelectedEffective);
        target.setObservations(observations);

        // Dimensions
        target.setDimensions(new DimensionRepresentations());

        for (String dimension : dimensions) {
            DimensionRepresentation representation = new DimensionRepresentation();
            representation.setDimensionId(dimension);
            representation.setRepresentations(new CodeRepresentations());

            int codesSize = 0;
            for (String dimensionValue : dimensionsCodesSelectedEffective.get(dimension)) {
                CodeRepresentation codeRepresentation = new CodeRepresentation();
                codeRepresentation.setCode(dimensionValue);
                codeRepresentation.setIndex(codesSize);
                representation.getRepresentations().getRepresentations().add(codeRepresentation);
                codesSize++;
            }
            representation.getRepresentations().setTotal(BigInteger.valueOf(representation.getRepresentations().getRepresentations().size()));
            target.getDimensions().getDimensions().add(representation);
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
            List<String> dimensionValues = dimensionsSelected.get(dimension);
            List<String> dimensionValuesSelected = new ArrayList<String>();
            if (CollectionUtils.isEmpty(dimensionValues)) {
                // if dimension is not selected in query, retrieve all codes from coverage
                List<CodeDimension> codeDimensions = datasetService.retrieveCoverageForDatasetVersionDimension(SERVICE_CONTEXT, source.getSiemacMetadataStatisticalResource().getUrn(), dimension);
                for (CodeDimension codeDimension : codeDimensions) {
                    dimensionValuesSelected.add(codeDimension.getIdentifier());
                }
            } else {
                dimensionValuesSelected.addAll(dimensionValues);
            }
            dimensionsCodesSelected.put(dimension, dimensionValuesSelected);
        }
        return dimensionsCodesSelected;
    }

    /**
     * Retrieve observations of selected code
     * FIXME attributes
     */
    private String toDatasetDataObservations(DatasetVersion source, List<String> dimensions, Map<String, List<String>> dimensionsSelected, Map<String, List<String>> dimensionsCodesSelectedEffective)
            throws Exception {

        if (MapUtils.isEmpty(dimensionsCodesSelectedEffective)) {
            return null;
        }

        // Search observations in repository
        List<ConditionDimensionDto> conditions = generateConditions(dimensionsSelected);
        Map<String, ObservationExtendedDto> datas = datasetRepositoriesServiceFacade.findObservationsExtendedByDimensions(source.getDatasetRepositoryId(), conditions);

        // Observation Size
        int sizeObservation = 1;
        for (String dimension : dimensionsCodesSelectedEffective.keySet()) {
            sizeObservation = sizeObservation * dimensionsCodesSelectedEffective.get(dimension).size();
        }
        // OBSERVATIONS (return sorted)
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
                } else {
                    dataObservations.add(null); // Return observation null
                }
                entryId.set(elemDimension, StringUtils.EMPTY);
                current++;
            } else {
                String dimension = dimensions.get(elemDimension + 1);
                List<String> dimensionValues = dimensionsCodesSelectedEffective.get(dimension);
                for (int i = dimensionValues.size() - 1; i >= 0; i--) {
                    OrderingStackElement temp = new OrderingStackElement(dimensionValues.get(i), elemDimension + 1);
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
        return commonDo2RestMapper.toResourceLink(RestExternalConstants.KIND_DATASETS, toDatasetsLink(agencyID, resourceID, version));
    }

    private String toDatasetsLink(String agencyID, String resourceID, String version) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_DATASETS;
        return commonDo2RestMapper.toResourceLink(resourceSubpath, agencyID, resourceID, version);
    }

    private ResourceLink toDatasetSelfLink(DatasetVersion source) {
        return commonDo2RestMapper.toResourceLink(RestExternalConstants.KIND_DATASET, toDatasetLink(source));
    }

    private String toDatasetLink(DatasetVersion source) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_DATASETS;
        String agencyID = source.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested();
        String resourceID = source.getSiemacMetadataStatisticalResource().getCode();
        String version = source.getSiemacMetadataStatisticalResource().getVersionLogic();
        return commonDo2RestMapper.toResourceLink(resourceSubpath, agencyID, resourceID, version);
    }

    private InternationalString toBibliographicCitation(org.siemac.metamac.core.common.ent.domain.InternationalString sources, String selfLink, List<String> selectedLanguages) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.core.common.ent.domain.LocalisedString source : sources.getTexts()) {
            if (selectedLanguages.contains(source.getLocale())) {
                LocalisedString target = new LocalisedString();
                target.setLang(source.getLocale());
                target.setValue(source.getLabel().replace(StatisticalResourcesConstants.BIBLIOGRAPHIC_CITATION_URI_TOKEN, selfLink));
                targets.getTexts().add(target);
            }
        }
        return targets;
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