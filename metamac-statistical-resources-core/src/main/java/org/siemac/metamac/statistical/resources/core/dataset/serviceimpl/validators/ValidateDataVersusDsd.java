package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResource;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponent;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.utils.ManipulateDataUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;

import com.arte.statistic.dataset.repository.dto.AttributeBasicDto;
import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoTypeEnum;

public class ValidateDataVersusDsd {

    private DataStructure                          dataStructure                           = null;
    private SrmRestInternalService                 srmRestInternalService                  = null;

    // DSD: Calculated and cache data
    private Map<String, ComponentInfo>             dimensionsInfoMap                       = null;
    private Map<String, ComponentInfo>             attributesInfoMap                       = null;
    private MultiMap                               enumerationRepresentationsMultimap      = null; // Key: URN, Value: Set<String>
    private Map<String, DsdProcessor.DsdDimension> dimensionsProcessorMap                  = null;
    private Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap                  = null;
    private Set<String>                            dimensionsCodeSet                       = null;
    private Set<String>                            attributesCodeSet                       = null;
    private Set<String>                            attributeIdsAtObservationLevelSet       = null;
    private Set<String>                            mandatoryAttributeIdsAtObservationLevel = null;
    private Map<String, List<ComponentInfo>>       groupDimensionMapInfo                   = null;

    public ValidateDataVersusDsd(DataStructure dataStructure, SrmRestInternalService srmRestInternalService) throws MetamacException {
        this.srmRestInternalService = srmRestInternalService;
        this.dataStructure = dataStructure;

        calculateCacheInfo();
    }

    /**************************************************************************
     * PUBLIC
     **************************************************************************/

    public List<ComponentInfo> retrieveDimensionsInfo() throws Exception {
        return new ArrayList<ComponentInfo>(this.dimensionsInfoMap.values());
    }

    public List<ComponentInfo> retrieveAttributesInfo() throws Exception {
        return new ArrayList<ComponentInfo>(this.attributesInfoMap.values());
    }

    public Map<String, DsdProcessor.DsdDimension> getDimensionsProcessorMap() {
        return dimensionsProcessorMap;
    }

    public Map<String, DsdProcessor.DsdAttribute> getAttributesProcessorMap() {
        return attributesProcessorMap;
    }

    public Set<String> getAttributeIdsAtObservationLevelSet() {
        return attributeIdsAtObservationLevelSet;
    }

    public Set<String> getMandatoryAttributeIdsAtObservationLevel() {
        return mandatoryAttributeIdsAtObservationLevel;
    }

    public Map<String, List<ComponentInfo>> getGroupDimensionMapInfo() {
        return groupDimensionMapInfo;
    }

    /**************************************************************************
     * VALIDATORS
     **************************************************************************/
    public void checkObservation(List<ObservationExtendedDto> dataDtos) throws Exception {
        List<MetamacExceptionItem> exceptions = new LinkedList<MetamacExceptionItem>();
        List<ComponentInfo> dimensionsInfos = new ArrayList<ComponentInfo>(this.dimensionsInfoMap.values());

        for (ObservationExtendedDto overExtendedDto : dataDtos) {

            // Number of dimensions
            if (dimensionsInfos.size() != overExtendedDto.getCodesDimension().size()) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_DIM_KEY_CARDINALITY_NOT_MATCH));
            }

            // The used dimension if correct
            for (CodeDimensionDto codeDimensionDto : overExtendedDto.getCodesDimension()) {
                if (!this.dimensionsCodeSet.contains(codeDimensionDto.getDimensionId())) {
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_DIM_NOT_MATCH, codeDimensionDto.getDimensionId()));
                }
            }

            // Number of attributes at observation level, can not exceed the maximum cardinality.
            if (overExtendedDto.getAttributes().size() > this.attributeIdsAtObservationLevelSet.size() + 1) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_ATTR_CARDINALITY_EXCEEDED));
            }

            // The used attribute if correct
            for (AttributeBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
                if (!this.attributeIdsAtObservationLevelSet.contains(attributeBasicDto.getAttributeId()) && !ManipulateDataUtils.DATA_SOURCE_ID.equals(attributeBasicDto.getAttributeId())) {
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_ATTR_NOT_MATCH, attributeBasicDto.getAttributeId()));
                }
            }

            // Mandatory attributes at observation level
            Set<String> attributesInCurrentObservation = new HashSet<String>();
            for (AttributeBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
                attributesInCurrentObservation.add(attributeBasicDto.getAttributeId());
            }
            for (String attributeId : this.mandatoryAttributeIdsAtObservationLevel) {
                if (!attributesInCurrentObservation.contains(attributeId)) {
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_MANDATORY_ATTR_NOT_FOUND, attributeId));
                }
            }

            // Representation of codes of dimensions
            for (CodeDimensionDto codeDimensionDto : overExtendedDto.getCodesDimension()) {
                // Enumerated representation
                checkDimensionEnumeratedRepresentation(codeDimensionDto.getDimensionId(), codeDimensionDto.getCodeDimensionId(), exceptions);

                // Non Enumerated representation
                checkDimensionNonEnumeratedRepresentation(codeDimensionDto.getDimensionId(), codeDimensionDto.getCodeDimensionId(), overExtendedDto.getUniqueKey(), exceptions);
            }

            // The codes of attributes must be defined in the enumerated representation
            for (AttributeBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
                if (!ManipulateDataUtils.DATA_SOURCE_ID.equals(attributeBasicDto.getAttributeId())) {

                    String value = attributeBasicDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);

                    // Enumerated representation
                    checkAttributeEnumeratedRepresentation(attributeBasicDto.getAttributeId(), value, exceptions);

                    // Non Enumerated representation
                    checkAttributeNonEnumeratedRepresentation(attributeBasicDto.getAttributeId(), value, overExtendedDto.getUniqueKey(), exceptions);
                }
            }
        }

        ExceptionUtils.throwIfException(exceptions);
    }

    public void checkAttributes(List<AttributeDto> attributeDtos) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new LinkedList<MetamacExceptionItem>();

        for (AttributeDto attributeDto : attributeDtos) {

            // The used attribute if correct
            if (!this.attributesCodeSet.contains(attributeDto.getAttributeId()) || this.attributeIdsAtObservationLevelSet.contains(attributeDto.getAttributeId())) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.IMPORTATION_ATTR_NOT_MATCH).withMessageParameters(attributeDto.getAttributeId()).build();
            }

            String value = attributeDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);

            // Enumerated representation of attributes
            checkAttributeEnumeratedRepresentation(attributeDto.getAttributeId(), value, exceptions);

            // Non Enumerated representation
            checkAttributeNonEnumeratedRepresentation(attributeDto.getAttributeId(), value, attributeDto.getUniqueKey(), exceptions);
        }

        ExceptionUtils.throwIfException(exceptions);
    }

    @SuppressWarnings("unchecked")
    private void checkDimensionEnumeratedRepresentation(String dimensionId, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // Enumerated representation
        String enumeratedRepresentationUrn = dimensionsProcessorMap.get(dimensionId).getEnumeratedRepresentationUrn();
        if (enumeratedRepresentationUrn != null) {
            // The codes of dimensions must be defined in the enumerated representation
            Set<String> validDimensionCodes = (Set<String>) enumerationRepresentationsMultimap.get(enumeratedRepresentationUrn);
            if (!validDimensionCodes.contains(value)) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_DIM_CODE_ENUM_NOT_VALID, value, dimensionId, enumeratedRepresentationUrn));
            }
        }
    }

    private void checkDimensionNonEnumeratedRepresentation(String dimensionId, String value, String key, List<MetamacExceptionItem> exceptions) throws MetamacException {

        DsdProcessor.DsdDimension dsdDimension = dimensionsProcessorMap.get(dimensionId);

        if (dsdDimension.getTextFormatRepresentation() != null) {
            NonEnumeratedRepresentationValidator.checkSimpleComponentTextFormatType(dsdDimension.getTextFormatRepresentation(), key, value, exceptions);
        } else if (dsdDimension.getTimeTextFormatRepresentation() != null) {
            NonEnumeratedRepresentationValidator.checkTimeTextFormatType(dsdDimension.getTimeTextFormatRepresentation(), key, value, exceptions);
        } else if (dsdDimension.getTextFormatConceptId() != null) {
            NonEnumeratedRepresentationValidator.checkBasicComponentTextFormatType(dsdDimension.getTextFormatConceptId(), key, value, exceptions);
        }
    }
    @SuppressWarnings("unchecked")
    private void checkAttributeEnumeratedRepresentation(String attributeId, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        String enumeratedRepresentationUrn = attributesProcessorMap.get(attributeId).getEnumeratedRepresentationUrn();
        if (enumeratedRepresentationUrn != null) {
            Set<String> validAttributeCodes = (Set<String>) enumerationRepresentationsMultimap.get(enumeratedRepresentationUrn);
            if (!validAttributeCodes.contains(value)) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_ATTR_CODE_ENUM_NOT_VALID, value, attributeId, enumeratedRepresentationUrn));
            }
        }
    }

    private void checkAttributeNonEnumeratedRepresentation(String attributeId, String value, String key, List<MetamacExceptionItem> exceptions) throws MetamacException {
        DsdProcessor.DsdAttribute dsdAttribute = attributesProcessorMap.get(attributeId);

        if (dsdAttribute.getTextFormatRepresentation() != null) {
            NonEnumeratedRepresentationValidator.checkSimpleComponentTextFormatType(dsdAttribute.getTextFormatRepresentation(), key, value, exceptions);
        } else if (dsdAttribute.getReportingYearStartDayTextFormatRepresentation() != null) {
            NonEnumeratedRepresentationValidator.checkReportingYearStartDayTextFormatType(dsdAttribute.getReportingYearStartDayTextFormatRepresentation(), key, value, exceptions);
        }
    }

    /**************************************************************************
     * PROCESSORS
     **************************************************************************/
    private void calculateCacheInfo() throws MetamacException {
        MultiMap enumerationRepresentationsMultimap = MultiValueMap.decorate(new HashMap<String, Set<String>>(), HashSet.class);

        calculateCacheDimensionInfo(enumerationRepresentationsMultimap);
        calculatedCacheAttributeInfo(enumerationRepresentationsMultimap);
        calculateCacheGroupInfo();

        this.enumerationRepresentationsMultimap = enumerationRepresentationsMultimap;

    }

    protected void calculateCacheGroupInfo() {
        // Groups
        Map<String, List<ComponentInfo>> groupDimensionMapInfo = new HashMap<String, List<ComponentInfo>>();

        if (dataStructure.getDataStructureComponents() != null) {
            for (GroupType sourceGroupType : dataStructure.getDataStructureComponents().getGroups()) {
                // Group Dimensions
                List<ComponentInfo> groupDimensions = new LinkedList<ComponentInfo>();
                for (GroupDimensionType groupDimensionType : sourceGroupType.getGroupDimensions()) {
                    groupDimensions.add(this.dimensionsInfoMap.get(groupDimensionType.getDimensionReference().getRef().getId()));
                }
                groupDimensionMapInfo.put(sourceGroupType.getId(), groupDimensions);
            }
        }

        this.groupDimensionMapInfo = groupDimensionMapInfo;
    }

    protected void calculatedCacheAttributeInfo(MultiMap enumerationRepresentationsMultimap) throws MetamacException {
        // Attributes
        Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap = new HashMap<String, DsdProcessor.DsdAttribute>();
        Map<String, ComponentInfo> attributesInfoMap = new HashMap<String, ComponentInfo>();
        Set<String> attributeIdsAtObservationLevelSet = new HashSet<String>();
        Set<String> mandatoryAttributeIdsAtObservationLevel = new HashSet<String>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getAttributeList() != null) {
            for (Object sourceAttribute : dataStructure.getDataStructureComponents().getAttributeList().getAttributesAndReportingYearStartDaies()) {
                DsdAttribute dsdAttribute = null;
                if (sourceAttribute instanceof ReportingYearStartDayType) {
                    dsdAttribute = new DsdAttribute((ReportingYearStartDayType) sourceAttribute);
                    // Processor
                    attributesProcessorMap.put(((ReportingYearStartDayType) sourceAttribute).getId(), dsdAttribute);
                    // ComponentInfo
                    attributesInfoMap.put(((ReportingYearStartDayType) sourceAttribute).getId(), new ComponentInfo(((ReportingYearStartDayType) sourceAttribute).getId(),
                            ComponentInfoTypeEnum.REPORTING_YEAR_START_DAY));
                } else if (sourceAttribute instanceof Attribute) {
                    dsdAttribute = new DsdAttribute((Attribute) sourceAttribute);
                    // Processor
                    attributesProcessorMap.put(((Attribute) sourceAttribute).getId(), new DsdAttribute((Attribute) sourceAttribute));
                    // Representation
                    cacheEnumerateRepresentation(dsdAttribute, enumerationRepresentationsMultimap);
                    // ComponentInfo
                    attributesInfoMap.put(((Attribute) sourceAttribute).getId(), new ComponentInfo(((Attribute) sourceAttribute).getId(), ComponentInfoTypeEnum.DATA_ATTRIBUTE));
                }
                if (dsdAttribute != null) {
                    if (dsdAttribute.isAttributeAtObservationLevel()) {
                        attributeIdsAtObservationLevelSet.add(dsdAttribute.getComponentId());
                        if (dsdAttribute.isMandatory()) {
                            mandatoryAttributeIdsAtObservationLevel.add(dsdAttribute.getComponentId());
                        }
                    }
                }
            }
        }

        this.attributesProcessorMap = attributesProcessorMap;
        this.attributesInfoMap = attributesInfoMap;
        this.attributeIdsAtObservationLevelSet = attributeIdsAtObservationLevelSet;
        this.mandatoryAttributeIdsAtObservationLevel = mandatoryAttributeIdsAtObservationLevel;
        this.attributesCodeSet = attributesInfoMap.keySet();
    }

    protected MultiMap calculateCacheDimensionInfo(MultiMap enumerationRepresentationsMultimap) throws MetamacException {
        // Dimensions
        Map<String, DsdProcessor.DsdDimension> dimensionsProcessorMap = new HashMap<String, DsdProcessor.DsdDimension>();
        Map<String, ComponentInfo> dimensionsInfoMap = new HashMap<String, ComponentInfo>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getDimensionList() != null) {
            for (Object sourceDim : dataStructure.getDataStructureComponents().getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions()) {
                if (sourceDim instanceof Dimension) {
                    DsdDimension dsdDimension = new DsdDimension((Dimension) sourceDim);
                    // Processor
                    dimensionsProcessorMap.put(((Dimension) sourceDim).getId(), dsdDimension);
                    // Representation
                    cacheEnumerateRepresentation(dsdDimension, enumerationRepresentationsMultimap);
                    // ComponentInfo
                    dimensionsInfoMap.put(((Dimension) sourceDim).getId(), new ComponentInfo(((Dimension) sourceDim).getId(), ComponentInfoTypeEnum.DIMENSION));
                } else if (sourceDim instanceof TimeDimensionType) {
                    // Processor
                    dimensionsProcessorMap.put(((TimeDimensionType) sourceDim).getId(), new DsdDimension((TimeDimensionType) sourceDim));
                    // ComponentInfo
                    dimensionsInfoMap.put(((TimeDimensionType) sourceDim).getId(), new ComponentInfo(((TimeDimensionType) sourceDim).getId(), ComponentInfoTypeEnum.TIME_DIMENSION));
                } else if (sourceDim instanceof MeasureDimensionType) {
                    DsdDimension dsdDimension = new DsdDimension((MeasureDimensionType) sourceDim);
                    // Processor
                    dimensionsProcessorMap.put(((MeasureDimensionType) sourceDim).getId(), dsdDimension);
                    // Representation
                    cacheEnumerateRepresentation(dsdDimension, enumerationRepresentationsMultimap);
                    // ComponentInfo
                    dimensionsInfoMap.put(((MeasureDimensionType) sourceDim).getId(), new ComponentInfo(((MeasureDimensionType) sourceDim).getId(), ComponentInfoTypeEnum.MEASURE_DIMENSION));
                }
            }
        }

        this.dimensionsProcessorMap = dimensionsProcessorMap;
        this.dimensionsInfoMap = dimensionsInfoMap;
        this.dimensionsCodeSet = dimensionsInfoMap.keySet();

        return enumerationRepresentationsMultimap;
    }

    private void cacheEnumerateRepresentation(DsdComponent dsdComponent, MultiMap enumerationRepresentationsMultimap) throws MetamacException {

        {
            // Codelist: If is not currently cached
            String codelistRepresentationUrn = dsdComponent.getCodelistRepresentationUrn();
            if (codelistRepresentationUrn != null && !enumerationRepresentationsMultimap.containsKey(codelistRepresentationUrn)) {
                Codes codes = srmRestInternalService.retrieveCodesOfCodelistEfficiently(codelistRepresentationUrn);

                for (CodeResource codeType : codes.getCodes()) {
                    enumerationRepresentationsMultimap.put(codelistRepresentationUrn, codeType.getId());
                }
            }
        }

        {
            // ConceptScheme:
            String conceptSchemeRepresentationUrn = dsdComponent.getConceptSchemeRepresentationUrn();
            if (conceptSchemeRepresentationUrn != null && !enumerationRepresentationsMultimap.containsKey(conceptSchemeRepresentationUrn)) {
                Concepts concepts = srmRestInternalService.retrieveConceptsOfConceptSchemeEfficiently(conceptSchemeRepresentationUrn);

                for (ItemResourceInternal conceptType : concepts.getConcepts()) {
                    enumerationRepresentationsMultimap.put(conceptSchemeRepresentationUrn, conceptType.getId());
                }
            }
        }
    }

}
