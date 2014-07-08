package org.siemac.metamac.statistical.resources.core.io.serviceimpl.validators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Group;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.MeasureDimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.PrimaryMeasure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.TimeDimension;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponent;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdPrimaryMeasure;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.io.utils.ManipulateDataUtils;
import org.siemac.metamac.statistical.resources.core.task.domain.AlternativeEnumeratedRepresentation;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceBasicDto;
import com.arte.statistic.dataset.repository.dto.AttributeInstanceDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoTypeEnum;
import com.arte.statistic.parser.sdmx.v2_1.domain.IdValuePair;

public class ValidateDataVersusDsd {

    private DataStructure                          dataStructure                                 = null;
    private SrmRestInternalService                 srmRestInternalService                        = null;

    // DSD: Calculated and cache data
    private Map<String, ComponentInfo>             dimensionsInfoMap                             = null;
    private List<ComponentInfo>                    dimensionsInfoList                            = null;
    private List<ComponentInfo>                    attributesInfoList                            = null;
    private MultiMap                               enumerationRepresentationsMultimap            = null; // Key: URN representation, Value: Set<String>
    private Map<String, Map<String, String>>       translationEnumRepresentationsMap             = null; // Key: componentId, Value: a map with (Key: alternativeCodeId and Value: normalizedCodeId)
    private Map<String, String>                    alternativeSourceEnumerationRepresentationMap = null; // Key: componentId, Value: URN representation
    private Map<String, DsdProcessor.DsdDimension> dimensionsProcessorMap                        = null;
    private Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap                        = null;
    private DsdProcessor.DsdPrimaryMeasure         dsdPrimaryMeasure                             = null;
    private Set<String>                            dimensionsCodeSet                             = null;
    private Set<String>                            attributesCodeSet                             = null;
    private Set<String>                            attributeIdsAtObservationLevelSet             = null;
    private Set<String>                            mandatoryAttributeIdsAtObservationLevel       = null;
    private Set<String>                            mandatoryAttributeIdsAtNonObservationLevel    = null;
    private Map<String, List<ComponentInfo>>       groupDimensionMapInfo                         = null;
    private boolean                                isExtraValidationForPrimaryMeasureRequired    = false;

    public ValidateDataVersusDsd(DataStructure dataStructure, SrmRestInternalService srmRestInternalService) throws MetamacException {
        this.srmRestInternalService = srmRestInternalService;
        this.dataStructure = dataStructure;
        this.alternativeSourceEnumerationRepresentationMap = new HashMap<String, String>();
        this.translationEnumRepresentationsMap = new HashMap<String, Map<String, String>>();

        calculateCacheInfo();
    }

    public ValidateDataVersusDsd(DataStructure dataStructure, SrmRestInternalService srmRestInternalService, List<AlternativeEnumeratedRepresentation> alternativeRepresentations)
            throws MetamacException {
        this.srmRestInternalService = srmRestInternalService;
        this.dataStructure = dataStructure;
        this.translationEnumRepresentationsMap = new HashMap<String, Map<String, String>>();

        // Alternative representations
        Map<String, String> alternativeSourceEnumerationRepresentationMap = new HashMap<String, String>();
        for (AlternativeEnumeratedRepresentation alternativeEnumeratedRepresentation : alternativeRepresentations) {
            alternativeSourceEnumerationRepresentationMap.put(alternativeEnumeratedRepresentation.getComponentId(), alternativeEnumeratedRepresentation.getUrn());
        }
        this.alternativeSourceEnumerationRepresentationMap = alternativeSourceEnumerationRepresentationMap;

        calculateCacheInfo();
    }

    /**************************************************************************
     * PUBLIC
     **************************************************************************/

    public List<ComponentInfo> retrieveDimensionsInfo() throws Exception {
        return dimensionsInfoList;
    }

    public List<ComponentInfo> retrieveAttributesInfo() throws Exception {
        return attributesInfoList;
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

    public Set<String> getMandatoryAttributeIdsAtNonObservationLevel() {
        return mandatoryAttributeIdsAtNonObservationLevel;
    }

    public Map<String, List<ComponentInfo>> getGroupDimensionMapInfo() {
        return groupDimensionMapInfo;
    }

    /**************************************************************************
     * VALIDATORS
     **************************************************************************/
    public void checkObservation(List<ObservationExtendedDto> dataDtos) throws Exception {
        List<MetamacExceptionItem> exceptions = new LinkedList<MetamacExceptionItem>();
        List<ComponentInfo> dimensionsInfos = retrieveDimensionsInfo();

        int previousExceptionSize = 0;
        for (ObservationExtendedDto overExtendedDto : dataDtos) {

            // Number of dimensions
            if (dimensionsInfos.size() != overExtendedDto.getCodesDimension().size()) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_DIM_KEY_CARDINALITY_NOT_MATCH));
                continue;
            }

            // The used dimension if correct
            previousExceptionSize = exceptions.size();
            for (CodeDimensionDto codeDimensionDto : overExtendedDto.getCodesDimension()) {
                if (!this.dimensionsCodeSet.contains(codeDimensionDto.getDimensionId())) {
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_DIM_NOT_MATCH, codeDimensionDto.getDimensionId()));
                }
            }
            if (exceptions.size() != previousExceptionSize) {
                continue;
            }

            // Number of attributes at observation level, can not exceed the maximum cardinality.
            if (overExtendedDto.getAttributes().size() > this.attributeIdsAtObservationLevelSet.size() + 1) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_ATTR_CARDINALITY_EXCEEDED));
                continue;
            }

            // The used attribute if correct
            previousExceptionSize = exceptions.size();
            for (AttributeInstanceBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
                if (!this.attributeIdsAtObservationLevelSet.contains(attributeBasicDto.getAttributeId())
                        && !StatisticalResourcesConstants.ATTRIBUTE_DATA_SOURCE_ID.equals(attributeBasicDto.getAttributeId())) {
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_ATTR_NOT_MATCH, attributeBasicDto.getAttributeId()));
                }
            }
            if (exceptions.size() != previousExceptionSize) {
                continue;
            }

            // Mandatory attributes at observation level
            previousExceptionSize = exceptions.size();
            Set<String> attributesInCurrentObservation = new HashSet<String>();
            for (AttributeInstanceBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
                attributesInCurrentObservation.add(attributeBasicDto.getAttributeId());
            }
            for (String attributeId : this.mandatoryAttributeIdsAtObservationLevel) {
                if (!attributesInCurrentObservation.contains(attributeId)) {
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_MANDATORY_ATTR_NOT_FOUND, attributeId));
                }
            }
            if (exceptions.size() != previousExceptionSize) {
                continue;
            }

            // Representation of codes of dimensions
            previousExceptionSize = exceptions.size();
            for (CodeDimensionDto codeDimensionDto : overExtendedDto.getCodesDimension()) {
                // Enumerated representation
                checkDimensionEnumeratedRepresentation(codeDimensionDto, exceptions);

                // Non Enumerated representation
                checkDimensionNonEnumeratedRepresentation(codeDimensionDto.getDimensionId(), codeDimensionDto.getCodeDimensionId(),
                        ManipulateDataUtils.toStringUnorderedKeyForObservation(overExtendedDto.getCodesDimension()), exceptions);
            }
            if (exceptions.size() != previousExceptionSize) {
                continue;
            }

            // The codes of attributes must be defined in the enumerated representation
            previousExceptionSize = exceptions.size();
            for (AttributeInstanceBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
                if (!StatisticalResourcesConstants.ATTRIBUTE_DATA_SOURCE_ID.equals(attributeBasicDto.getAttributeId())) {

                    String value = attributeBasicDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);

                    // Enumerated representation
                    checkAttributeEnumeratedRepresentation(attributeBasicDto.getAttributeId(), value, exceptions);

                    // Non Enumerated representation
                    checkAttributeNonEnumeratedRepresentation(attributeBasicDto.getAttributeId(), value, ManipulateDataUtils.toStringUnorderedKeyForObservation(overExtendedDto.getCodesDimension()),
                            exceptions);
                }
            }
            if (exceptions.size() != previousExceptionSize) {
                continue;
            }

            // Representation of PRIMARY_MEASURE
            // If the observation is null, not validation is required
            previousExceptionSize = exceptions.size();
            if (StringUtils.isNotBlank(overExtendedDto.getPrimaryMeasure())) {
                // Enumerated representation
                checkPrimaryMeasureEnumeratedRepresentation(this.dsdPrimaryMeasure.getComponentId(), overExtendedDto.getPrimaryMeasure(), exceptions);

                // Non Enumerated representation
                checkPrimaryMeasureNonEnumeratedRepresentation(this.dsdPrimaryMeasure.getComponentId(), overExtendedDto.getPrimaryMeasure(),
                        ManipulateDataUtils.toStringUnorderedKeyForObservation(overExtendedDto.getCodesDimension()), exceptions);

                // Extra validation for primary measure
                if (this.isExtraValidationForPrimaryMeasureRequired) {
                    NonEnumeratedRepresentationValidator.checkExtraValidationForPrimaryMeasure(ManipulateDataUtils.toStringUnorderedKeyForObservation(overExtendedDto.getCodesDimension()),
                            overExtendedDto.getPrimaryMeasure(), exceptions);
                }
            }
            if (exceptions.size() != previousExceptionSize) {
                continue;
            }

            //@formatter:off
            // TODO METAMAC-1985, check if the observation is included or excluded
            // Para todas las CK definidas para este flujo de datos.
            //      Para todas las Regiones de cada CK
            //          Ver si hace match la observación con alguna clave de region
            //              Si no lo hace y:
            //                  es isIncluded -> Error (en esta región debería de estar)
            //                  es !isIncluded -> OK
            //              Si lo hace y:
            //                  es isIncluded -> OK 
            //                  es !isIncluded -> Error (en esta región no debería de estar)
            //              -- Hay que estudiar que pasa entre las regiones? -> A mi entender ñas claves tienen que ser disjuntas entre las regiones
            //              
            //@formatter:on

        }

        ExceptionUtils.throwIfException(exceptions);
    }

    public void checkAttributesInstancesRepresentation(List<AttributeInstanceDto> attributeInstanceDtos) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new LinkedList<MetamacExceptionItem>();

        int previousExceptionSize = 0;
        for (AttributeInstanceDto attributeInstanceDto : attributeInstanceDtos) {
            previousExceptionSize = exceptions.size();

            // The used attribute if correct
            if (!this.attributesCodeSet.contains(attributeInstanceDto.getAttributeId()) || this.attributeIdsAtObservationLevelSet.contains(attributeInstanceDto.getAttributeId())) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_ATTR_NOT_MATCH, attributeInstanceDto.getAttributeId()));
                continue;
            }

            String value = attributeInstanceDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);

            // Enumerated representation of attributes
            checkAttributeEnumeratedRepresentation(attributeInstanceDto.getAttributeId(), value, exceptions);

            // Non Enumerated representation
            checkAttributeNonEnumeratedRepresentation(attributeInstanceDto.getAttributeId(), value, ManipulateDataUtils.toStringUnorderedKeyForAttribute(attributeInstanceDto.getCodesByDimension()),
                    exceptions);

            if (exceptions.size() != previousExceptionSize) {
                continue;
            }
        }

        ExceptionUtils.throwIfException(exceptions);
    }

    @SuppressWarnings("unchecked")
    public void checkAttributesInstancesAssignmentStatus(String attributeId, List<AttributeInstanceDto> attributeInstanceDenormalizedDtos, Map<String, List<String>> coverage,
            List<MetamacExceptionItem> exceptions) throws Exception {

        if (getMandatoryAttributeIdsAtNonObservationLevel().contains(attributeId)) {

            if (attributesProcessorMap.get(attributeId).isDatasetAttribute()) {
                if (attributeInstanceDenormalizedDtos.isEmpty()) {
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.VALIDATION_NONOBSLEVEL_MANDATORY_ATTR_NOT_FOUND, attributeId, "Dataset"));
                }
            } else {
                // Keys from attributes instances
                Set<String> attributesKeySet = new HashSet<String>();

                List<ComponentInfo> dimensionsInfos = getAttributeDimensions(attributeId);

                for (AttributeInstanceDto attributeInstanceDto : attributeInstanceDenormalizedDtos) {
                    List<IdValuePair> keyList = new ArrayList<IdValuePair>();
                    for (ComponentInfo dimension : dimensionsInfos) {
                        List<String> attributeCodesInDimension = attributeInstanceDto.getCodesByDimension().get(dimension.getCode());
                        if (attributeCodesInDimension != null) {
                            keyList.add(new IdValuePair(dimension.getCode(), attributeCodesInDimension.get(0)));
                        }
                    }

                    // Add current attribute to this key in the map
                    attributesKeySet.add(ManipulateDataUtils.generateKeyFromIdValuePairs(keyList));
                }

                // Keys from coverage
                Set<String> attributesCoverageKeySet = calculateAttributesCoverageKeySet(dimensionsInfos, coverage);

                if (attributesKeySet.size() != attributesCoverageKeySet.size()) {
                    Collection<String> subtractSet = CollectionUtils.subtract(attributesCoverageKeySet, attributesKeySet);
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.VALIDATION_NONOBSLEVEL_MANDATORY_ATTR_NOT_FOUND, attributeId, StringUtils.join(subtractSet.toArray(), " - ")));
                }
            }
        }
    }

    private List<ComponentInfo> getAttributeDimensions(String attributeId) {
        DsdAttribute dsdAttribute = attributesProcessorMap.get(attributeId);
        if (dsdAttribute.isDimensionAttribute()) {
            if (dsdAttribute.getAttributeRelationship().getGroup() != null) {
                return groupDimensionMapInfo.get(dsdAttribute.getAttributeRelationship().getGroup());
            } else {
                List<String> dimensionsIds = dsdAttribute.getAttributeRelationship().getDimensions();
                List<ComponentInfo> dimensions = new ArrayList<ComponentInfo>(dimensionsIds.size());
                for (String dimensionId : dimensionsIds) {
                    dimensions.add(dimensionsInfoMap.get(dimensionId));
                }
                return dimensions;
            }
        }
        return new ArrayList<ComponentInfo>();
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

    public Set<String> calculateAttributesCoverageKeySet(List<ComponentInfo> dimensions, Map<String, List<String>> dimensionsCodesSelectedEffective) throws Exception {
        Set<String> attributesKeySet = new HashSet<String>();

        if (dimensionsCodesSelectedEffective.isEmpty()) {
            return attributesKeySet;
        }

        // Build data
        Stack<OrderingStackElement> stack = new Stack<OrderingStackElement>();
        stack.push(new OrderingStackElement(StringUtils.EMPTY, -1));

        ArrayList<IdValuePair> entryId = new ArrayList<IdValuePair>(dimensions.size());
        for (int i = 0; i < dimensions.size(); i++) {
            entryId.add(i, null);
        }

        int lastDimension = dimensions.size() - 1;
        while (stack.size() > 0) {
            // POP
            OrderingStackElement elem = stack.pop();
            int elemDimension = elem.getDimNum();
            String elemCode = elem.getCodeId();

            // The first time we don't need a key element
            if (elemDimension != -1) {
                entryId.set(elemDimension, new IdValuePair(dimensions.get(elemDimension).getCode(), elemCode));
            }

            // The entry is complete
            if (elemDimension == lastDimension) {
                // We have the full entry here
                String key = ManipulateDataUtils.generateKeyFromIdValuePairs(entryId);
                attributesKeySet.add(key);
                entryId.set(elemDimension, null);
            } else {
                String dimension = dimensions.get(elemDimension + 1).getCode();
                List<String> dimensionValues = dimensionsCodesSelectedEffective.get(dimension);
                for (int i = dimensionValues.size() - 1; i >= 0; i--) {
                    OrderingStackElement temp = new OrderingStackElement(dimensionValues.get(i), elemDimension + 1);
                    stack.push(temp);
                }
            }
        }
        return attributesKeySet;
    }

    @SuppressWarnings("unchecked")
    private void checkDimensionEnumeratedRepresentation(CodeDimensionDto codeDimensionDto, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // Alternative enumerated representation
        if (translationEnumRepresentationsMap.containsKey(codeDimensionDto.getDimensionId())) {
            Map<String, String> translationCodeMap = translationEnumRepresentationsMap.get(codeDimensionDto.getDimensionId());
            if (translationCodeMap.containsKey(codeDimensionDto.getCodeDimensionId())) {
                String translation = translationCodeMap.get(codeDimensionDto.getCodeDimensionId());
                if (translation != null) {
                    codeDimensionDto.setCodeDimensionId(translation);
                } else {
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_DIM_CODE_ENUM_NOT_VALID_TRANSLATION, codeDimensionDto.getCodeDimensionId(), codeDimensionDto
                            .getDimensionId()));
                }
            } else {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_DIM_CODE_ENUM_NOT_VALID, codeDimensionDto.getCodeDimensionId(), codeDimensionDto.getDimensionId(),
                        alternativeSourceEnumerationRepresentationMap.get(codeDimensionDto.getDimensionId())));
            }
        } else {
            // Original Enumerated representation
            String enumeratedRepresentationUrn = dimensionsProcessorMap.get(codeDimensionDto.getDimensionId()).getEnumeratedRepresentationUrn();
            if (enumeratedRepresentationUrn != null) {
                // The codes of dimensions must be defined in the enumerated representation
                Set<String> validDimensionCodes = (Set<String>) enumerationRepresentationsMultimap.get(enumeratedRepresentationUrn);
                if (!validDimensionCodes.contains(codeDimensionDto.getCodeDimensionId())) {
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_DIM_CODE_ENUM_NOT_VALID, codeDimensionDto.getCodeDimensionId(), codeDimensionDto.getDimensionId(),
                            enumeratedRepresentationUrn));
                }
            }
        }
    }

    private void checkDimensionNonEnumeratedRepresentation(String dimensionId, String value, String key, List<MetamacExceptionItem> exceptions) throws MetamacException {
        DsdProcessor.DsdDimension dsdDimension = dimensionsProcessorMap.get(dimensionId);

        if (dsdDimension.getTextFormatRepresentation() != null) {
            NonEnumeratedRepresentationValidator.checkTextFormatType(dsdDimension.getTextFormatRepresentation(), key, value, exceptions);
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
            NonEnumeratedRepresentationValidator.checkTextFormatType(dsdAttribute.getTextFormatRepresentation(), key, value, exceptions);
        }
    }

    @SuppressWarnings("unchecked")
    private void checkPrimaryMeasureEnumeratedRepresentation(String dimensionId, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // Enumerated representation
        String enumeratedRepresentationUrn = this.dsdPrimaryMeasure.getEnumeratedRepresentationUrn();
        if (enumeratedRepresentationUrn != null) {
            // The codes of primary measure must be defined in the enumerated representation
            Set<String> validPrimaryMeasureCodes = (Set<String>) enumerationRepresentationsMultimap.get(enumeratedRepresentationUrn);
            if (!validPrimaryMeasureCodes.contains(value)) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_CODE_ENUM_NOT_VALID, value, dimensionId, enumeratedRepresentationUrn));
            }
        }
    }

    private void checkPrimaryMeasureNonEnumeratedRepresentation(String dimensionId, String value, String key, List<MetamacExceptionItem> exceptions) throws MetamacException {
        if (this.dsdPrimaryMeasure.getTextFormatRepresentation() != null) {
            NonEnumeratedRepresentationValidator.checkTextFormatType(this.dsdPrimaryMeasure.getTextFormatRepresentation(), key, value, exceptions);
        }
    }

    /**************************************************************************
     * PROCESSORS
     **************************************************************************/
    private void calculateCacheInfo() throws MetamacException {
        MultiMap enumerationRepresentationsMultimap = MultiValueMap.decorate(new HashMap<String, Set<String>>(), HashSet.class);

        calculateCacheDimensionInfo(enumerationRepresentationsMultimap);
        calculatedCacheAttributeInfo(enumerationRepresentationsMultimap);
        calculateCachePrimaryMeasureInfo(enumerationRepresentationsMultimap);
        calculateCacheGroupInfo();

        this.enumerationRepresentationsMultimap = enumerationRepresentationsMultimap;

    }

    protected void calculateCacheGroupInfo() {
        // Groups
        Map<String, List<ComponentInfo>> groupDimensionMapInfo = new HashMap<String, List<ComponentInfo>>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getGroups() != null) {
            for (Group sourceGroupType : dataStructure.getDataStructureComponents().getGroups().getGroups()) {
                // Group Dimensions
                List<ComponentInfo> groupDimensions = new LinkedList<ComponentInfo>();
                for (String dimensionId : sourceGroupType.getDimensions().getDimensions()) {
                    groupDimensions.add(this.dimensionsInfoMap.get(dimensionId));
                }
                groupDimensionMapInfo.put(sourceGroupType.getId(), groupDimensions);
            }
        }

        this.groupDimensionMapInfo = groupDimensionMapInfo;
    }

    protected void calculatedCacheAttributeInfo(MultiMap enumerationRepresentationsMultimap) throws MetamacException {
        // Attributes
        Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap = new LinkedHashMap<String, DsdProcessor.DsdAttribute>();
        Map<String, ComponentInfo> attributesInfoMap = new HashMap<String, ComponentInfo>();
        List<ComponentInfo> attributesInfoList = new LinkedList<ComponentInfo>();
        Set<String> attributeIdsAtObservationLevelSet = new HashSet<String>();
        Set<String> mandatoryAttributeIdsAtObservationLevel = new HashSet<String>();
        Set<String> mandatoryAttributeIdsAtNonObservationLevel = new HashSet<String>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getAttributes() != null) {
            for (AttributeBase sourceAttribute : dataStructure.getDataStructureComponents().getAttributes().getAttributes()) {
                DsdAttribute dsdAttribute = new DsdAttribute((Attribute) sourceAttribute);
                // Processor
                attributesProcessorMap.put(((Attribute) sourceAttribute).getId(), new DsdAttribute((Attribute) sourceAttribute));
                // Representation
                cacheEnumerateRepresentation(dsdAttribute, enumerationRepresentationsMultimap);
                // ComponentInfo
                ComponentInfo componentInfo = new ComponentInfo(((Attribute) sourceAttribute).getId(), ComponentInfoTypeEnum.DATA_ATTRIBUTE);
                attributesInfoMap.put(((Attribute) sourceAttribute).getId(), new ComponentInfo(((Attribute) sourceAttribute).getId(), ComponentInfoTypeEnum.DATA_ATTRIBUTE));
                attributesInfoList.add(componentInfo);

                if (dsdAttribute.isAttributeAtObservationLevel()) {
                    attributeIdsAtObservationLevelSet.add(dsdAttribute.getComponentId());
                    if (dsdAttribute.isMandatory()) {
                        mandatoryAttributeIdsAtObservationLevel.add(dsdAttribute.getComponentId());
                    }
                } else {
                    if (dsdAttribute.isMandatory()) {
                        mandatoryAttributeIdsAtNonObservationLevel.add(dsdAttribute.getComponentId());
                    }
                }
            }
        }

        this.attributesProcessorMap = attributesProcessorMap;
        this.attributesInfoList = attributesInfoList;
        this.attributeIdsAtObservationLevelSet = attributeIdsAtObservationLevelSet;
        this.mandatoryAttributeIdsAtObservationLevel = mandatoryAttributeIdsAtObservationLevel;
        this.mandatoryAttributeIdsAtNonObservationLevel = mandatoryAttributeIdsAtNonObservationLevel;
        this.attributesCodeSet = attributesInfoMap.keySet();
    }

    protected MultiMap calculateCacheDimensionInfo(MultiMap enumerationRepresentationsMultimap) throws MetamacException {
        // Dimensions
        Map<String, DsdProcessor.DsdDimension> dimensionsProcessorMap = new HashMap<String, DsdProcessor.DsdDimension>();
        Map<String, ComponentInfo> dimensionsInfoMap = new HashMap<String, ComponentInfo>();
        List<ComponentInfo> dimensionsInfoList = new LinkedList<ComponentInfo>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getDimensions() != null) {
            for (DimensionBase sourceDim : dataStructure.getDataStructureComponents().getDimensions().getDimensions()) {
                if (sourceDim instanceof Dimension) {
                    DsdDimension dsdDimension = new DsdDimension((Dimension) sourceDim);
                    // Processor
                    dimensionsProcessorMap.put(((Dimension) sourceDim).getId(), dsdDimension);
                    // Representation
                    cacheEnumerateRepresentation(dsdDimension, enumerationRepresentationsMultimap);
                    // ComponentInfo
                    ComponentInfo componentInfo = new ComponentInfo(((Dimension) sourceDim).getId(), ComponentInfoTypeEnum.DIMENSION);
                    dimensionsInfoMap.put(((Dimension) sourceDim).getId(), componentInfo);
                    dimensionsInfoList.add(componentInfo);
                } else if (sourceDim instanceof TimeDimension) {
                    // Processor
                    dimensionsProcessorMap.put(((TimeDimension) sourceDim).getId(), new DsdDimension((TimeDimension) sourceDim));
                    // ComponentInfo
                    ComponentInfo componentInfo = new ComponentInfo(((TimeDimension) sourceDim).getId(), ComponentInfoTypeEnum.TIME_DIMENSION);
                    dimensionsInfoMap.put(((TimeDimension) sourceDim).getId(), componentInfo);
                    dimensionsInfoList.add(componentInfo);
                } else if (sourceDim instanceof MeasureDimension) {
                    DsdDimension dsdDimension = new DsdDimension((MeasureDimension) sourceDim);
                    // Processor
                    dimensionsProcessorMap.put(((MeasureDimension) sourceDim).getId(), dsdDimension);
                    // Representation
                    cacheEnumerateRepresentation(dsdDimension, enumerationRepresentationsMultimap);
                    // ComponentInfo
                    ComponentInfo componentInfo = new ComponentInfo(((MeasureDimension) sourceDim).getId(), ComponentInfoTypeEnum.MEASURE_DIMENSION);
                    dimensionsInfoMap.put(((MeasureDimension) sourceDim).getId(), componentInfo);
                    dimensionsInfoList.add(componentInfo);
                }
            }
        }

        this.dimensionsProcessorMap = dimensionsProcessorMap;
        this.dimensionsInfoMap = dimensionsInfoMap;
        this.dimensionsInfoList = dimensionsInfoList;
        this.dimensionsCodeSet = dimensionsInfoMap.keySet();

        return enumerationRepresentationsMultimap;
    }

    protected MultiMap calculateCachePrimaryMeasureInfo(MultiMap enumerationRepresentationsMultimap) throws MetamacException {

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getMeasure() != null) {
            PrimaryMeasure primaryMeasure = dataStructure.getDataStructureComponents().getMeasure().getPrimaryMeasure();

            DsdPrimaryMeasure dsdPrimaryMeasure = new DsdPrimaryMeasure(primaryMeasure);

            // Representation
            cacheEnumerateRepresentation(dsdPrimaryMeasure, enumerationRepresentationsMultimap);

            boolean isExtraValidationForPrimaryMeasureRequired = false;
            // Always perform a extra validation if the enumerate representation is NULL.
            if (dsdPrimaryMeasure.getEnumeratedRepresentationUrn() == null) {
                isExtraValidationForPrimaryMeasureRequired = true;
            }

            this.dsdPrimaryMeasure = dsdPrimaryMeasure;
            this.isExtraValidationForPrimaryMeasureRequired = isExtraValidationForPrimaryMeasureRequired;
        }

        return enumerationRepresentationsMultimap;
    }

    private void cacheEnumerateRepresentation(DsdComponent dsdComponent, MultiMap enumerationRepresentationsMultimap) throws MetamacException {

        {
            // Codelist: If is not currently cached
            String codelistRepresentationUrn = dsdComponent.getCodelistRepresentationUrn();

            if (codelistRepresentationUrn != null) {

                Codes codes = null;

                if (!enumerationRepresentationsMultimap.containsKey(codelistRepresentationUrn)) {
                    codes = srmRestInternalService.retrieveCodesOfCodelistEfficiently(codelistRepresentationUrn);
                    for (CodeResourceInternal codeType : codes.getCodes()) {
                        enumerationRepresentationsMultimap.put(codelistRepresentationUrn, codeType.getId());
                    }
                }

                // Cache translation if is -+necessary
                if (isTranslationNecessary(dsdComponent.getComponentId())) {
                    if (codes == null) {
                        codes = srmRestInternalService.retrieveCodesOfCodelistEfficiently(codelistRepresentationUrn);
                    }
                    Map<String, String> variableElementMapToCodesMap = new HashMap<String, String>();
                    for (CodeResourceInternal codeType : codes.getCodes()) {
                        if (codeType.getVariableElement() != null) {
                            variableElementMapToCodesMap.put(codeType.getVariableElement().getId(), codeType.getId());
                        }
                    }

                    Codes alternativeCodes = srmRestInternalService.retrieveCodesOfCodelistEfficiently(alternativeSourceEnumerationRepresentationMap.get(dsdComponent.getComponentId()));
                    // (Key: alternativeCodeId and Value: normalizedCodeId)
                    Map<String, String> translationCodeMap = new HashMap<String, String>();
                    for (CodeResourceInternal alternativeCodeType : alternativeCodes.getCodes()) {
                        if (alternativeCodeType.getVariableElement() != null) {
                            String originalCode = variableElementMapToCodesMap.get(alternativeCodeType.getVariableElement().getId());
                            if (!StringUtils.isEmpty(originalCode)) {
                                translationCodeMap.put(alternativeCodeType.getId(), variableElementMapToCodesMap.get(alternativeCodeType.getVariableElement().getId()));
                            } else {
                                translationCodeMap.put(alternativeCodeType.getId(), null);
                            }
                        } else {
                            translationCodeMap.put(alternativeCodeType.getId(), null);
                        }
                    }

                    translationEnumRepresentationsMap.put(dsdComponent.getComponentId(), translationCodeMap);
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

    private boolean isTranslationNecessary(String componentId) {
        if (alternativeSourceEnumerationRepresentationMap.containsKey(componentId) && !translationEnumRepresentationsMap.containsKey(componentId)) {
            return true;
        } else {
            return false;
        }
    }
}
