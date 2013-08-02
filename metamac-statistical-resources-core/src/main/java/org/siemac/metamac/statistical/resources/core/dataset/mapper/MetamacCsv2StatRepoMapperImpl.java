package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.utils.ManipulateDataUtils;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.InternationalStringDto;
import com.arte.statistic.dataset.repository.dto.LocalisedStringDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.csv.domain.CsvObservation;
import com.arte.statistic.parser.csv.domain.CsvObservationAttribute;
import com.arte.statistic.parser.csv.domain.CsvObservationCodeDimension;

@Component(MetamacCsv2StatRepoMapper.BEAN_ID)
public class MetamacCsv2StatRepoMapperImpl implements MetamacCsv2StatRepoMapper {

    @Override
    public ObservationExtendedDto toObservation(CsvObservation observation, String datasourceId) throws MetamacException {
        ObservationExtendedDto observationExtendedDto = null;

        if (observation == null) {
            return null; // Reached end of file
        }

        observationExtendedDto = new ObservationExtendedDto();

        // Key
        observationExtendedDto.getCodesDimension().addAll(processKeyOfObservation(observation.getCodesDimensions()));

        // Data
        observationExtendedDto.setPrimaryMeasure(StringUtils.isEmpty(observation.getObservationValue()) ? null : observation.getObservationValue());

        // Attributes
        observationExtendedDto.addAttribute(ManipulateDataUtils.createDataSourceIdentificationAttribute(observationExtendedDto.getCodesDimension(), datasourceId)); // Add identification datasource

        for (CsvObservationAttribute csvObservationAttribute : observation.getAttributes()) {
            // All attributes of CSV are in observation level
            AttributeDto attributeDto = processAttribute(observationExtendedDto.getCodesDimension(), csvObservationAttribute);
            observationExtendedDto.addAttribute(attributeDto);
        }

        return observationExtendedDto;
    }
    private List<CodeDimensionDto> processKeyOfObservation(List<CsvObservationCodeDimension> observations) {
        List<CodeDimensionDto> codeDimensionDtos = new ArrayList<CodeDimensionDto>(observations.size());

        for (CsvObservationCodeDimension csvObservationCodeDimension : observations) {
            codeDimensionDtos.add(new CodeDimensionDto(csvObservationCodeDimension.getDimensionId(), csvObservationCodeDimension.getCodeDimensionId()));
        }

        return codeDimensionDtos;
    }

    /**
     * Generate a attributeDto form idValuePairDto and keys
     * 
     * @param keys
     * @param idValuePair
     * @return AttributeDto
     */
    private AttributeDto processAttribute(List<CodeDimensionDto> keys, CsvObservationAttribute csvObservationAttribute) {
        AttributeDto attributeDto = new AttributeDto();
        if (StringUtils.isEmpty(csvObservationAttribute.getAttributeValue())) {
            return null;
        }

        // Keys
        attributeDto.getCodesDimension().addAll(keys);

        // Data
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();
        localisedStringDto.setLabel(csvObservationAttribute.getAttributeValue());
        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
        localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
        internationalStringDto.addText(localisedStringDto);

        attributeDto.setValue(internationalStringDto);

        // Attribute Id
        attributeDto.setAttributeId(csvObservationAttribute.getAttributeId());
        return attributeDto;
    }
}
