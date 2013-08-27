package org.siemac.metamac.statistical.resources.core.io.serviceimpl.validators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.arte.statistic.dataset.repository.dto.AttributeBasicDto;
import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoTypeEnum;

public class ValidateDataVersusDsd {

    private DataStructure                          dataStructure                              = null;
    private SrmRestInternalService                 srmRestInternalService                     = null;

    // DSD: Calculated and cache data
    private Map<String, ComponentInfo>             dimensionsInfoMap                          = null;
    private List<ComponentInfo>                    dimensionsInfoList                         = null;
    private List<ComponentInfo>                    attributesInfoList                         = null;
    private MultiMap                               enumerationRepresentationsMultimap         = null; // Key: URN, Value: Set<String>
    private Map<String, DsdProcessor.DsdDimension> dimensionsProcessorMap                     = null;
    private Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap                     = null;
    private DsdProcessor.DsdPrimaryMeasure         dsdPrimaryMeasure                          = null;
    private Set<String>                            dimensionsCodeSet                          = null;
    private Set<String>                            attributesCodeSet                          = null;
    private Set<String>                            attributeIdsAtObservationLevelSet          = null;
    private Set<String>                            mandatoryAttributeIdsAtObservationLevel    = null;
    private Map<String, List<ComponentInfo>>       groupDimensionMapInfo                      = null;
    private boolean                                isExtraValidationForPrimaryMeasureRequired = false;

    public ValidateDataVersusDsd(DataStructure dataStructure, SrmRestInternalService srmRestInternalService) throws MetamacException {
        this.srmRestInternalService = srmRestInternalService;
        this.dataStructure = dataStructure;

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
            for (AttributeBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
                if (!this.attributeIdsAtObservationLevelSet.contains(attributeBasicDto.getAttributeId()) && !ManipulateDataUtils.DATA_SOURCE_ID.equals(attributeBasicDto.getAttributeId())) {
                    exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_ATTR_NOT_MATCH, attributeBasicDto.getAttributeId()));
                }
            }
            if (exceptions.size() != previousExceptionSize) {
                continue;
            }

            // Mandatory attributes at observation level
            previousExceptionSize = exceptions.size();
            Set<String> attributesInCurrentObservation = new HashSet<String>();
            for (AttributeBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
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
                checkDimensionEnumeratedRepresentation(codeDimensionDto.getDimensionId(), codeDimensionDto.getCodeDimensionId(), exceptions);

                // Non Enumerated representation
                checkDimensionNonEnumeratedRepresentation(codeDimensionDto.getDimensionId(), codeDimensionDto.getCodeDimensionId(),
                        ManipulateDataUtils.generateKeyForObservation(overExtendedDto.getCodesDimension()), exceptions);
            }
            if (exceptions.size() != previousExceptionSize) {
                continue;
            }

            // The codes of attributes must be defined in the enumerated representation
            previousExceptionSize = exceptions.size();
            for (AttributeBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
                if (!ManipulateDataUtils.DATA_SOURCE_ID.equals(attributeBasicDto.getAttributeId())) {

                    String value = attributeBasicDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);

                    // Enumerated representation
                    checkAttributeEnumeratedRepresentation(attributeBasicDto.getAttributeId(), value, exceptions);

                    // Non Enumerated representation
                    checkAttributeNonEnumeratedRepresentation(attributeBasicDto.getAttributeId(), value, ManipulateDataUtils.generateKeyForObservation(overExtendedDto.getCodesDimension()), exceptions);
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
                        ManipulateDataUtils.generateKeyForObservation(overExtendedDto.getCodesDimension()), exceptions);

                // Extra validation for primary measure
                if (this.isExtraValidationForPrimaryMeasureRequired) {
                    NonEnumeratedRepresentationValidator.checkExtraValidationForPrimaryMeasure(ManipulateDataUtils.generateKeyForObservation(overExtendedDto.getCodesDimension()),
                            overExtendedDto.getPrimaryMeasure(), exceptions);
                }
            }
            if (exceptions.size() != previousExceptionSize) {
                continue;
            }

        }

        ExceptionUtils.throwIfException(exceptions);
    }

    public void checkAttributes(List<AttributeDto> attributeDtos) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new LinkedList<MetamacExceptionItem>();

        int previousExceptionSize = 0;
        for (AttributeDto attributeDto : attributeDtos) {
            previousExceptionSize = exceptions.size();

            // The used attribute if correct
            if (!this.attributesCodeSet.contains(attributeDto.getAttributeId()) || this.attributeIdsAtObservationLevelSet.contains(attributeDto.getAttributeId())) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_ATTR_NOT_MATCH, attributeDto.getAttributeId()));
                continue;
            }

            String value = attributeDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);

            // Enumerated representation of attributes
            checkAttributeEnumeratedRepresentation(attributeDto.getAttributeId(), value, exceptions);

            // Non Enumerated representation
            checkAttributeNonEnumeratedRepresentation(attributeDto.getAttributeId(), value, ManipulateDataUtils.generateKeyForAttribute(attributeDto.getCodesByDimension()), exceptions);

            if (exceptions.size() != previousExceptionSize) {
                continue;
            }
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
        Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap = new HashMap<String, DsdProcessor.DsdAttribute>();
        Map<String, ComponentInfo> attributesInfoMap = new HashMap<String, ComponentInfo>();
        List<ComponentInfo> attributesInfoList = new LinkedList<ComponentInfo>();
        Set<String> attributeIdsAtObservationLevelSet = new HashSet<String>();
        Set<String> mandatoryAttributeIdsAtObservationLevel = new HashSet<String>();

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
                }
            }
        }

        this.attributesProcessorMap = attributesProcessorMap;
        this.attributesInfoList = attributesInfoList;
        this.attributeIdsAtObservationLevelSet = attributeIdsAtObservationLevelSet;
        this.mandatoryAttributeIdsAtObservationLevel = mandatoryAttributeIdsAtObservationLevel;
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
            if (codelistRepresentationUrn != null && !enumerationRepresentationsMultimap.containsKey(codelistRepresentationUrn)) {
                Codes codes = srmRestInternalService.retrieveCodesOfCodelistEfficiently(codelistRepresentationUrn);

                for (CodeResourceInternal codeType : codes.getCodes()) {
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
