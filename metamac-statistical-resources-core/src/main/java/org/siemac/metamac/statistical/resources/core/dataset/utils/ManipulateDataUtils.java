package org.siemac.metamac.statistical.resources.core.dataset.utils;

import java.util.List;

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

        // Keys
        // Map<String, List<String>> codesByDimension = new HashMap<String, List<String>>();
        // for (CodeDimensionDto codeDimensionDto : keys) {
        // if (codesByDimension.containsKey(codeDimensionDto.getDimensionId())) {
        // codesByDimension.get(codeDimensionDto.getDimensionId()).add(codeDimensionDto.getCodeDimensionId());
        // } else {
        // List<String> codes = new LinkedList<String>();
        // codes.add(codeDimensionDto.getCodeDimensionId());
        // }
        // }
        // attributeDto..setCodesByDimension(codesByDimension);

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
}
