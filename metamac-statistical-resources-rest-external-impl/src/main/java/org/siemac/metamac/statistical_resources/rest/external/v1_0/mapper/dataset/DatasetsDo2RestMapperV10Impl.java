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
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
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
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponentType;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
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

    // TODO Map<String, List<String>> dimensions, List<String> selectedLanguages
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
        target.setName(toInternationalString(source.getSiemacMetadataStatisticalResource().getTitle()));
        target.setParentLink(toDatasetParentLink(source));
        target.setChildLinks(toDatasetChildLinks(source));
        // TODO resto de metadatos públicos

        if (includeMetadata) {
            target.setMetadata(toDatasetMetadata(source));
        }
        if (includeData) {
            target.setData(toDatasetData(source, selectedLanguages, selectedDimensions));
        }
        return target;
    }

    private DatasetMetadata toDatasetMetadata(DatasetVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }
        DatasetMetadata target = new DatasetMetadata();
        // Dsd
        DataStructure dataStructure = srmRestExternalFacade.retrieveDataStructureByUrn(source.getRelatedDsd().getUrnInternal());
        target.setDataStructureDefinition(toDataStructureDefinition(source.getRelatedDsd(), dataStructure));
        // Dimensions
        target.setDimensions(toDimensions(source.getSiemacMetadataStatisticalResource().getUrn(), dataStructure));
        return target;
    }

    private DataStructureDefinition toDataStructureDefinition(ExternalItem source, DataStructure dataStructure) {
        if (source == null) {
            return null;
        }
        DataStructureDefinition target = new DataStructureDefinition();
        toResourceExternalItemSrm(source, target);
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

    private Dimensions toDimensions(String datasetVersionUrn, DataStructure dataStructure) throws MetamacException {

        List<String> dimensionsId = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, datasetVersionUrn);
        if (CollectionUtils.isEmpty(dimensionsId)) {
            return null;
        }

        Dimensions targets = new Dimensions();
        List<DsdDimension> dimensionsType = DsdProcessor.getDimensions(dataStructure);
        for (DsdDimension dsdDimension : dimensionsType) {
            targets.getDimensions().add(toDimension(datasetVersionUrn, dsdDimension));
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensions().size()));
        return targets;
    }

    private Dimension toDimension(String datasetVersionUrn, DsdDimension source) throws MetamacException {
        if (source == null) {
            return null;
        }
        Dimension target = new Dimension();
        target.setId(source.getComponentId());
        {
            // TODO NAME del concept identity
            InternationalString name = new InternationalString();
            LocalisedString nameLocalisedString = new LocalisedString();
            nameLocalisedString.setLang("es");
            nameLocalisedString.setValue(source.getComponentId());
            name.getTexts().add(nameLocalisedString);
            target.setName(name);
        }
        target.setType(toDimensionType(source.getType()));

        // Codes
        target.setDimensionCodes(toDimensionCodes(datasetVersionUrn, source));
        return target;
    }
    // TODO dónde hay que tener en cuenta el conceptIdentity?
    private DimensionCodes toDimensionCodes(String datasetVersionUrn, DsdDimension dimension) throws MetamacException {
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
            toDimensionCodeFromCodelist(coveragesById, dimension.getCodelistRepresentationUrn(), targets);
        } else if (dimension.getConceptSchemeRepresentationUrn() != null) {
            toDimensionCodeFromConceptScheme(coveragesById, dimension.getConceptSchemeRepresentationUrn(), targets);
        } else if (dimension.getTimeTextFormatType() != null) {
            toDimensionCodeFromTimeTextFormatType(coveragesById, dimension.getTimeTextFormatType(), targets);
        }

        targets.setTotal(BigInteger.valueOf(targets.getDimensionCodes().size()));
        return targets;
    }

    private void toDimensionCodeFromCodelist(Map<String, CodeDimension> coveragesById, String codelistUrn, DimensionCodes targets) throws MetamacException {
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
            targets.getDimensionCodes().add(toDimensionCode(code));
        }
    }

    private void toDimensionCodeFromConceptScheme(Map<String, CodeDimension> coveragesById, String conceptSchemeUrn, DimensionCodes targets) throws MetamacException {
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
            targets.getDimensionCodes().add(toDimensionCode(concept));
        }
    }

    private void toDimensionCodeFromTimeTextFormatType(Map<String, CodeDimension> coveragesById, TimeTextFormatType timeTextFormatType, DimensionCodes targets) throws MetamacException {
        if (timeTextFormatType == null) {
            return;
        }
        for (String coverageId : coveragesById.keySet()) {
            CodeDimension codeDimension = coveragesById.get(coverageId);
            targets.getDimensionCodes().add(toDimensionCode(codeDimension));
        }
    }

    private DimensionCode toDimensionCode(CodeType source) throws MetamacException {
        if (source == null) {
            return null;
        }
        DimensionCode target = new DimensionCode();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setName(toInternationalString(source.getNames()));
        // TODO resource! y resto de metadatos (representation...)
        return target;
    }

    private DimensionCode toDimensionCode(ConceptType source) throws MetamacException {
        if (source == null) {
            return null;
        }
        DimensionCode target = new DimensionCode();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setName(toInternationalString(source.getNames()));
        // TODO resource! y resto de metadatos (representation...)
        return target;
    }

    private DimensionCode toDimensionCode(CodeDimension source) throws MetamacException {
        if (source == null) {
            return null;
        }
        DimensionCode target = new DimensionCode();
        target.setId(source.getIdentifier());
        target.setUrn(null);
        {
            // TODO NAME?
            InternationalString name = new InternationalString();
            LocalisedString nameLocalisedString = new LocalisedString();
            nameLocalisedString.setLang("es"); // TODO locale?
            nameLocalisedString.setValue(source.getTitle());
            name.getTexts().add(nameLocalisedString);
            target.setName(name);
        }

        // TODO resource! y resto de metadatos (representation...)
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

        return StringUtils.join(dataObservations.iterator(), ", "); // TODO así?
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