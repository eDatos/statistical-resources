package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.px.PxParser.PxDataByDimensionsIterator;
import com.arte.statistic.parser.px.domain.PxAttribute;
import com.arte.statistic.parser.px.domain.PxAttributeCodes;
import com.arte.statistic.parser.px.domain.PxAttributeDimension;
import com.arte.statistic.parser.px.domain.PxModel;
import com.arte.statistic.parser.px.domain.PxObservation;
import com.arte.statistic.parser.px.domain.PxObservationCodeDimension;

@Component(value = "metamacPx2StatRepoMapper")
public class MetamacPx2StatRepoMapperImpl implements MetamacPx2StatRepoMapper {

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
    public ObservationExtendedDto toObservation(PxDataByDimensionsIterator iterator) throws MetamacException {

        PxObservation observation = null;
        ObservationExtendedDto observationExtendedDto = null;

        try {
            observation = iterator.next();

            if (observation == null) {
                return null; // Reached end of file
            }

            observationExtendedDto = new ObservationExtendedDto();

            // Key
            observationExtendedDto.getCodesDimension().addAll(processKeyOfObservation(observation.getCodesDimensions()));

            // Data
            observationExtendedDto.setPrimaryMeasure(observation.getObservationValue());

            // Attributes
            // TODO añadir atributos a nivel de observación
            // if (this.attributesObservations.containsKey(observationExtendedDto.getUniqueKey())) {
            // observationExtendedDto.getAttributes().addAll(this.attributesObservations.get(observationExtendedDto.getUniqueKey()));
            // }
        } catch (IOException e) {
            // TODO Lanzar excepción
            e.printStackTrace();
        }

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
}
