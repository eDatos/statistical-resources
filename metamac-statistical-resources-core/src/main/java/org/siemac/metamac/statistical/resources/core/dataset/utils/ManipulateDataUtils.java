package org.siemac.metamac.statistical.resources.core.dataset.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

import com.arte.statistic.dataset.repository.dto.AttributeObservationDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.InternationalStringDto;
import com.arte.statistic.dataset.repository.dto.LocalisedStringDto;

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
    public static String generateKeyForAttribute(Map<String, List<String>> codesByDimension) {
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
    public static String generateKeyForObservation(List<CodeDimensionDto> codesDimension) {
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
                key.append('*');
            } else {
                key.append(entry.getValue());
            }
            key.append(entry.getKey()).append(',');
        }

        if (key.charAt(key.length() - 1) == ',') {
            key.deleteCharAt(key.length() - 1);
        }

        return key.toString();
    }

}
