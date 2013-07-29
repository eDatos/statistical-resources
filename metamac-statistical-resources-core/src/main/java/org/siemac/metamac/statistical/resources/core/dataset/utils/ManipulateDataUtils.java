package org.siemac.metamac.statistical.resources.core.dataset.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

import com.arte.statistic.dataset.repository.dto.AttributeDto;
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
    public static AttributeDto createDataSourceIdentificationAttribute(List<CodeDimensionDto> keys, String dataSourceId) throws MetamacException {
        AttributeDto attributeDto = new AttributeDto();

        if (StringUtils.isEmpty(dataSourceId)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.TASK_DATASOURCE_ID).build();
        }

        // Keys
        attributeDto.getCodesDimension().addAll(keys);

        // Data
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();
        localisedStringDto.setLabel(dataSourceId);
        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
        localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
        internationalStringDto.addText(localisedStringDto);

        attributeDto.setValue(internationalStringDto);

        // Attribute Id
        attributeDto.setAttributeId(DATA_SOURCE_ID);
        return attributeDto;
    }
}
