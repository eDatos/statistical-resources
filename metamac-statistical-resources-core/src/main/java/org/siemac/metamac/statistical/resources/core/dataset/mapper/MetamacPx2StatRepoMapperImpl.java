package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.utils.ManipulateDataUtils;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeBasicDto;
import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.InternationalStringDto;
import com.arte.statistic.dataset.repository.dto.LocalisedStringDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.util.DtoUtils;
import com.arte.statistic.parser.domain.InternationalString;
import com.arte.statistic.parser.px.domain.PxAttribute;
import com.arte.statistic.parser.px.domain.PxAttributeCodes;
import com.arte.statistic.parser.px.domain.PxAttributeDimension;
import com.arte.statistic.parser.px.domain.PxDimension;
import com.arte.statistic.parser.px.domain.PxModel;
import com.arte.statistic.parser.px.domain.PxObservation;
import com.arte.statistic.parser.px.domain.PxObservationCodeDimension;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;

@Component(MetamacPx2StatRepoMapper.BEAN_ID)
public class MetamacPx2StatRepoMapperImpl implements MetamacPx2StatRepoMapper {

    public static final String ATTR_OBS_NOTE = "OBS_NOTE";

    /**
     * Transform attributes (global, dimensions, observations...) removing dimensions with '*' as codes, and assigning new attributes identifiers
     * IMPORTANT: Change pxModel!
     */
    @Override
    public void reviewPxAttributesIdentifiersAndDimensions(PxModel pxModel) {
        _reviewPxAttributesIdentifiersAndDimensions(pxModel.getAttributesGlobal());
        _reviewPxAttributesIdentifiersAndDimensions(pxModel.getAttributesDimensions());
        _reviewPxAttributesIdentifiersAndDimensions(pxModel.getAttributesObservations());
    }

    @Override
    public ObservationExtendedDto toObservation(PxObservation observation, String datasourceId, Map<String, List<AttributeBasicDto>> attributesObservations) throws MetamacException {

        ObservationExtendedDto observationExtendedDto = null;

        if (observation == null) {
            return null; // Reached end of file
        }

        observationExtendedDto = new ObservationExtendedDto();

        // Key
        observationExtendedDto.getCodesDimension().addAll(processKeyOfObservation(observation.getCodesDimensions()));

        // Data
        observationExtendedDto.setPrimaryMeasure(observation.getObservationValue());

        // Attributes: Identification data source attribute
        observationExtendedDto.addAttribute(ManipulateDataUtils.createDataSourceIdentificationAttribute(observationExtendedDto.getCodesDimension(), datasourceId));

        // Attributes: Other attributes at observation level (only OBS_NOTE)

        addValidAttributesForCurrentObservation(observationExtendedDto.getCodesDimension(), attributesObservations, observationExtendedDto, new LinkedList<String>(), 0);

        return observationExtendedDto;
    }

    private List<CodeDimensionDto> processKeyOfObservation(List<PxObservationCodeDimension> observations) {
        List<CodeDimensionDto> codeDimensionDtos = new ArrayList<CodeDimensionDto>(observations.size());

        for (PxObservationCodeDimension pxObservationCodeDimension : observations) {
            codeDimensionDtos.add(new CodeDimensionDto(pxObservationCodeDimension.getDimensionId(), pxObservationCodeDimension.getCodeDimensionId()));
        }

        return codeDimensionDtos;
    }

    /**
     * Transform attributes, removing dimensions with '*' as codes, and assigning new attributes identifiers
     */
    private void _reviewPxAttributesIdentifiersAndDimensions(List<PxAttribute> pxAttributes) {

        Map<String, Integer> attributesIdentifiers = new HashMap<String, Integer>(); // key: original attribute identifier; value: counter of attributes with same identifier
        Map<String, PxAttribute> attributesDefinitions = new HashMap<String, PxAttribute>(); // key: dimensionIdentifier; value: existing attribute with same dimensions

        for (PxAttribute pxAttribute : pxAttributes) {
            // Remove dimensions with '*' as code
            List<PxAttributeDimension> pxAttributesDimensions = new ArrayList<PxAttributeDimension>();
            for (PxAttributeDimension pxAttributeDimension : pxAttribute.getDimensions()) {
                if (!PxAttributeCodes.ALL_DIMENSION_CODES.equals(pxAttributeDimension.getDimensionCode())) {
                    pxAttributesDimensions.add(pxAttributeDimension);
                }
            }
            // Generate identifier
            String key = generateKeyAttributeToMap(pxAttribute.getIdentifier(), pxAttributesDimensions);
            PxAttribute pxAttributeWithSameDimension = attributesDefinitions.get(key);
            if (pxAttributeWithSameDimension != null) {
                pxAttribute.setIdentifier(pxAttributeWithSameDimension.getIdentifier());
            } else {
                String originalIdentifier = null;
                if (pxAttributesDimensions.size() == 0) {
                    // global
                    originalIdentifier = pxAttribute.getIdentifier().endsWith("X") ? PxAttributeCodes.NOTEX : PxAttributeCodes.NOTE;
                } else if (pxAttributesDimensions.size() != pxAttribute.getDimensions().size()) {
                    // dimension
                    originalIdentifier = pxAttribute.getIdentifier().endsWith("X") ? PxAttributeCodes.VALUENOTEX : PxAttributeCodes.VALUENOTE;
                } else {
                    // observation
                    originalIdentifier = pxAttribute.getIdentifier();
                }
                pxAttribute.setIdentifier(generatePxAttributeIdentifier(attributesIdentifiers, originalIdentifier));
                attributesDefinitions.put(key, pxAttribute);
            }

            // Reset attachment dimensions
            pxAttribute.getDimensions().clear();
            pxAttribute.getDimensions().addAll(pxAttributesDimensions);
        }
    }

    /**
     * Generate unique identifier to attribute
     */
    private String generatePxAttributeIdentifier(Map<String, Integer> attributesIdentifiers, String identifierOriginal) {

        String identifier = identifierOriginal;
        String identifierUnique = null;
        if (!attributesIdentifiers.containsKey(identifier)) {
            attributesIdentifiers.put(identifier, Integer.valueOf(0));
        }
        Integer count = Integer.valueOf(attributesIdentifiers.get(identifier) + 1);
        identifierUnique = identifier + "_" + count;
        attributesIdentifiers.put(identifier, count);

        return identifierUnique;
    }

    /**
     * Generate key to attribute with dimensions identifiers
     */
    private String generateKeyAttributeToMap(String attributeCode, List<PxAttributeDimension> attributeDimensions) {
        StringBuilder key = new StringBuilder();
        key.append(attributeCode);
        key.append("_");
        for (PxAttributeDimension pxAttributeDimension : attributeDimensions) {
            key.append(pxAttributeDimension.getDimension()).append("_");
        }
        return key.toString();
    }

    /**
     * Transform observations attributes (only attributes for one observation)
     * Only are valids CELLNOTE y CELNOTEX attributes.
     * 
     * @return Map with key = unique key of observations; value = list of attributes of observation
     */
    @Override
    public Map<String, List<AttributeBasicDto>> toAttributesObservations(PxModel pxModel, String preferredLanguage, List<ComponentInfo> dimensionsInfos, Map<String, Integer> dimensionsOrderPxMap)
            throws MetamacException {

        Map<String, List<AttributeBasicDto>> attributes = new HashMap<String, List<AttributeBasicDto>>();

        for (PxAttribute pxAttributeDto : pxModel.getAttributesObservations()) {
            if (PxAttributeCodes.CELLNOTE.equals(pxAttributeDto.getIdentifier()) || PxAttributeCodes.CELLNOTEX.equals(pxAttributeDto.getIdentifier())) {

                AttributeDto attributeDto = new AttributeDto();
                attributeDto.setAttributeId(ATTR_OBS_NOTE);
                attributeDto.setValue(toInternationalStringStatisticRepository(pxAttributeDto.getValue(), preferredLanguage, pxModel.getLanguage()));

                // key
                for (ComponentInfo componentInfo : dimensionsInfos) {
                    Integer order = dimensionsOrderPxMap.get(componentInfo.getCode());
                    if (order != null) {
                        PxAttributeDimension pxAttributeDimension = pxAttributeDto.getDimensions().get(order);
                        CodeDimensionDto codeDimensionDto = new CodeDimensionDto();
                        codeDimensionDto.setDimensionId(pxAttributeDimension.getDimension());
                        codeDimensionDto.setCodeDimensionId(pxAttributeDimension.getDimensionCode());
                        attributeDto.addCodesDimension(codeDimensionDto);
                    }
                }
                String uniqueKey = DtoUtils.generateUniqueKey(attributeDto.getCodesDimension());
                if (!attributes.containsKey(uniqueKey)) {
                    attributes.put(uniqueKey, new ArrayList<AttributeBasicDto>());
                    attributes.get(uniqueKey).add(attributeDto);
                } else {
                    List<AttributeBasicDto> previousObsNotes = attributes.get(uniqueKey);
                    previousObsNotes.get(0).setValue(concatInternationalStrings(previousObsNotes.get(0).getValue(), attributeDto.getValue()));
                }
            }
        }

        return attributes;
    }
    public void addValidAttributesForCurrentObservation(List<CodeDimensionDto> keys, Map<String, List<AttributeBasicDto>> attributesObservations, ObservationExtendedDto observationExtendedDto,
            final List<String> codesDimension, int index) {

        if (attributesObservations == null || attributesObservations.isEmpty()) {
            return;
        }

        if (index == keys.size()) {
            String generateUniqueKeyWithCodes = DtoUtils.generateUniqueKeyWithCodes(codesDimension);
            if (attributesObservations.get(generateUniqueKeyWithCodes) != null) {
                observationExtendedDto.getAttributes().addAll(attributesObservations.get(generateUniqueKeyWithCodes));
            }

            return;
        }

        // Generate Keys

        // One key with '*'
        List<String> candidateCodesDimension = new LinkedList<String>(codesDimension);
        candidateCodesDimension.add("*");

        addValidAttributesForCurrentObservation(keys, attributesObservations, observationExtendedDto, candidateCodesDimension, index + 1);

        // One key with the specific value
        candidateCodesDimension = new LinkedList<String>(codesDimension);
        candidateCodesDimension.add(keys.get(index).getCodeDimensionId());
        addValidAttributesForCurrentObservation(keys, attributesObservations, observationExtendedDto, candidateCodesDimension, index + 1);
    }

    @Override
    public Map<String, Integer> generateDimensionsOrderPxMap(PxModel pxModel) throws MetamacException {
        Map<String, Integer> pxDimensionOrder = new HashMap<String, Integer>();
        int i = 0;
        for (PxDimension stubDimension : pxModel.getStub()) {
            pxDimensionOrder.put(stubDimension.getIdentifier(), i++);
        }
        for (PxDimension headingDimension : pxModel.getHeading()) {
            pxDimensionOrder.put(headingDimension.getIdentifier(), i++);
        }
        return pxDimensionOrder;
    }

    private com.arte.statistic.dataset.repository.dto.InternationalStringDto toInternationalStringStatisticRepository(InternationalString source, String preferredLanguage, String defaultLanguage)
            throws MetamacException {
        if (source == null) {
            return null;
        }

        com.arte.statistic.dataset.repository.dto.InternationalStringDto target = new com.arte.statistic.dataset.repository.dto.InternationalStringDto();

        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repository exists the code of enumerated representation, never the i18n of code.

        String localisedLabel = source.getLocalisedLabel(preferredLanguage);
        if (localisedLabel == null) {
            // Get in default language
            localisedLabel = source.getLocalisedLabel(defaultLanguage);
            if (localisedLabel == null) {
                return null;
            }
        }

        com.arte.statistic.dataset.repository.dto.LocalisedStringDto targetLocalisedString = new com.arte.statistic.dataset.repository.dto.LocalisedStringDto();
        targetLocalisedString.setLabel(localisedLabel);
        targetLocalisedString.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);

        target.getTexts().add(targetLocalisedString);

        return target;
    }

    private InternationalStringDto concatInternationalStrings(InternationalStringDto iStringA, InternationalStringDto iStringB) throws MetamacException {
        if (iStringA == null) {
            return iStringB;
        }
        if (iStringB == null) {
            return iStringA;
        }

        List<LocalisedStringDto> textsA = iStringA.getTexts();
        List<LocalisedStringDto> textsB = iStringB.getTexts();
        if (!textsA.isEmpty() && !textsB.isEmpty()) {
            textsA.get(0).setLabel(textsA.get(0).getLabel() + " | " + textsB.get(0).getLabel());
        }

        return iStringA;
    }
}
