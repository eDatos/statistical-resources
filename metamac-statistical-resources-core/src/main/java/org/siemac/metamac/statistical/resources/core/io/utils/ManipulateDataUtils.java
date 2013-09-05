package org.siemac.metamac.statistical.resources.core.io.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

import com.arte.statistic.dataset.repository.dto.AttributeBasicDto;
import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.AttributeObservationDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.InternationalStringDto;
import com.arte.statistic.dataset.repository.dto.LocalisedStringDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoTypeEnum;
import com.arte.statistic.parser.sdmx.v2_1.domain.DimensionCodeInfo;

public class ManipulateDataUtils {

    public static String DATA_SOURCE_ID = "DATA_SOURCE_ID";
    public static String DATASET        = "DATASET";

    /**
     * Create a data source extra identification attribute
     * 
     * @param keys
     * @param dataSourceId
     * @return AttributeDto
     */
    public static AttributeObservationDto createDataSourceIdentificationAttribute(List<CodeDimensionDto> keys, String dataSourceId) throws MetamacException {

        if (StringUtils.isEmpty(dataSourceId)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.TASK_DATASOURCE_ID).build();
        }

        // Data
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();
        localisedStringDto.setLabel(dataSourceId);
        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
        localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
        internationalStringDto.addText(localisedStringDto);

        AttributeObservationDto attributeDto = new AttributeObservationDto(DATA_SOURCE_ID, internationalStringDto);

        return attributeDto;
    }

    /**
     * Generate key for attribute
     * 
     * @param codesByDimension
     * @return
     */
    public static String toStringUnorderedKeyForAttribute(Map<String, List<String>> codesByDimension) {
        if (codesByDimension == null || codesByDimension.isEmpty()) {
            return DATASET;
        }

        return addKey(codesByDimension);
    }

    /**
     * Generate key for observation
     * 
     * @param codesDimension
     * @return
     */
    public static String toStringUnorderedKeyForObservation(List<CodeDimensionDto> codesDimension) {
        if (codesDimension == null || codesDimension.size() == 0) {
            return null;
        }
        Map<String, List<String>> codesByDimensionMap = new HashMap<String, List<String>>();
        for (CodeDimensionDto codeDimension : codesDimension) {
            codesByDimensionMap.put(codeDimension.getDimensionId(), Arrays.asList(codeDimension.getCodeDimensionId()));
        }

        return addKey(codesByDimensionMap);
    }

    protected static String addKey(Map<String, List<String>> codesByDimension) {
        StringBuilder key = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : codesByDimension.entrySet()) {
            key.append(entry.getKey()).append(':');
            if (entry.getValue() == null || entry.getValue().isEmpty()) {
                key.append("[*]");
            } else {
                key.append(entry.getValue());
            }
            key.append(',');
        }

        if (key.charAt(key.length() - 1) == ',') {
            key.deleteCharAt(key.length() - 1);
        }

        return key.toString();
    }

    /**
     * Transform all attributes in map indexed by key
     * 
     * @param currentTransformedAttributes
     * @param dimensions
     * @param attributeDtos
     * @return
     */
    public static Map<String, List<AttributeBasicDto>> addTransformAttributesToCurrentTransformedAttributes(Map<String, List<AttributeBasicDto>> currentTransformedAttributes,
            List<DimensionCodeInfo> dimensions, List<AttributeDto> attributeDtos) {

        for (AttributeDto attributeDto : attributeDtos) {
            // For current attribute
            transformAttributes(currentTransformedAttributes, dimensions, 0, new LinkedList<CodeDimensionDto>(), attributeDto);
        }

        return currentTransformedAttributes;
    }

    private static void transformAttributes(Map<String, List<AttributeBasicDto>> attributesMap, List<DimensionCodeInfo> dimensions, int index, List<CodeDimensionDto> codesDimension,
            AttributeDto attributeDto) {

        // If the key of current attribute is fully generated
        if (index == attributeDto.getCodesByDimension().size()) {
            String key = generateKeyFromCodesDimensions(codesDimension);

            // Add current attribute to this key in the map
            if (attributesMap.containsKey(key)) {
                attributesMap.get(key).add(attributeDto);
            } else {
                List<AttributeBasicDto> attributeDtos = new LinkedList<AttributeBasicDto>();
                attributeDtos.add(attributeDto);
                attributesMap.put(key, attributeDtos);
            }

            return;
        }

        // Next dimension to process code
        ComponentInfo dimension = dimensions.get(index);

        // If the attribute contains the current dimension in his key
        if (attributeDto.getCodesByDimension() != null && !attributeDto.getCodesByDimension().get(dimension.getCode()).isEmpty()) {
            List<String> codes = attributeDto.getCodesByDimension().get(dimension.getCode());
            // Generate all keys with the query codes of the current dimension
            for (String code : codes) {
                CodeDimensionDto codeDimensionAuxDto = new CodeDimensionDto(dimension.getCode(), code);

                List<CodeDimensionDto> conditions = new ArrayList<CodeDimensionDto>(codesDimension.size() + 1);
                conditions.addAll(codesDimension);
                conditions.add(codeDimensionAuxDto);

                transformAttributes(attributesMap, dimensions, index + 1, conditions, attributeDto);
            }
        } else {
            transformAttributes(attributesMap, dimensions, index + 1, codesDimension, attributeDto);
        }
    }

    public static String generateKeyFromCodesDimensions(List<CodeDimensionDto> codesDimension) {
        StringBuilder key = new StringBuilder();

        for (CodeDimensionDto codeDimensionDto : codesDimension) {
            key.append(codeDimensionDto.getDimensionId()).append(':').append(codeDimensionDto.getCodeDimensionId()).append(',');
        }

        if (key.charAt(key.length() - 1) == ',') {
            key.deleteCharAt(key.length() - 1);
        }
        return key.toString();
    }

    public static void main(String[] args) {

        Map<String, List<String>> codesByDimension = new HashMap<String, List<String>>();

        codesByDimension.put("FREQ", Arrays.asList("M", "Y", "D"));
        codesByDimension.put("CURRENCY", Arrays.asList("USD", "GBP"));
        codesByDimension.put("CURRENCY_DENOM", Arrays.asList("EUR"));
        codesByDimension.put("EXR_TYPE", Arrays.asList("SP00"));
        codesByDimension.put("EXR_VAR", Arrays.asList("E", "S", "F"));
        codesByDimension.put("TIME_PERIOD", Arrays.asList("2010-10", "2011-10", "2012-10"));
        // codesByDimension.put("KAKA", new ArrayList<String>());

        // System.out.println(generateKeyForAttribute(codesByDimension));

        // AtributeDto
        AttributeDto attributeDto = new AttributeDto();
        attributeDto.setAttributeId("ATTRIBUTE");
        attributeDto.setCodesByDimension(codesByDimension);
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();
        localisedStringDto.setLabel("hola");
        localisedStringDto.setLocale("es");
        internationalStringDto.addText(localisedStringDto);
        attributeDto.setValue(internationalStringDto);

        // List<ComponentInfo> dimensions,
        List<DimensionCodeInfo> dimensions = new ArrayList<DimensionCodeInfo>();
        dimensions.add(new DimensionCodeInfo("FREQ", ComponentInfoTypeEnum.DIMENSION));
        dimensions.add(new DimensionCodeInfo("CURRENCY", ComponentInfoTypeEnum.DIMENSION));
        dimensions.add(new DimensionCodeInfo("CURRENCY_DENOM", ComponentInfoTypeEnum.DIMENSION));
        dimensions.add(new DimensionCodeInfo("EXR_TYPE", ComponentInfoTypeEnum.DIMENSION));
        dimensions.add(new DimensionCodeInfo("EXR_VAR", ComponentInfoTypeEnum.DIMENSION));
        dimensions.add(new DimensionCodeInfo("TIME_PERIOD", ComponentInfoTypeEnum.DIMENSION));
        dimensions.add(new DimensionCodeInfo("KAKA", ComponentInfoTypeEnum.DIMENSION));

        Map<String, List<AttributeBasicDto>> attributesMap = addTransformAttributesToCurrentTransformedAttributes(new HashMap<String, List<AttributeBasicDto>>(), dimensions,
                Arrays.asList(attributeDto));

        for (Map.Entry<String, List<AttributeBasicDto>> entry : attributesMap.entrySet()) {
            System.out.println(entry.getKey());
            List<AttributeBasicDto> value = entry.getValue();
            System.out.println(toStringUnorderedKeyForAttribute(((AttributeDto) value.iterator().next()).getCodesByDimension()));
            System.out.println("----------------");
        }

    }
}
