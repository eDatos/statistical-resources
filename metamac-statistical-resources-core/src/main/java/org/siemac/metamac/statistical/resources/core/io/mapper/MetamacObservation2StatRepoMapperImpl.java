package org.siemac.metamac.statistical.resources.core.io.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.io.utils.ManipulateDataUtils;
import org.springframework.stereotype.Component;

import com.arte.statistic.parser.generic.domain.Observation;
import com.arte.statistic.parser.generic.domain.ObservationAttribute;
import com.arte.statistic.parser.generic.domain.ObservationCodeDimension;

import es.gobcan.istac.edatos.dataset.repository.dto.AttributeInstanceObservationDto;
import es.gobcan.istac.edatos.dataset.repository.dto.CodeDimensionDto;
import es.gobcan.istac.edatos.dataset.repository.dto.InternationalStringDto;
import es.gobcan.istac.edatos.dataset.repository.dto.LocalisedStringDto;
import es.gobcan.istac.edatos.dataset.repository.dto.ObservationExtendedDto;

@Component(MetamacObservation2StatRepoMapper.BEAN_ID)
public class MetamacObservation2StatRepoMapperImpl implements MetamacObservation2StatRepoMapper {

    @Override
    public ObservationExtendedDto toObservation(Observation observation, String datasourceId) throws MetamacException {
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

        for (ObservationAttribute csvObservationAttribute : observation.getAttributes()) {
            // All attributes of CSV are in observation level
            AttributeInstanceObservationDto attributeObservationDto = processAttribute(observationExtendedDto.getCodesDimension(), csvObservationAttribute);
            observationExtendedDto.addAttribute(attributeObservationDto);
        }

        return observationExtendedDto;
    }
    private List<CodeDimensionDto> processKeyOfObservation(List<ObservationCodeDimension> observations) {
        List<CodeDimensionDto> codeDimensionDtos = new ArrayList<>(observations.size());

        for (ObservationCodeDimension csvObservationCodeDimension : observations) {
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
    private AttributeInstanceObservationDto processAttribute(List<CodeDimensionDto> keys, ObservationAttribute csvObservationAttribute) {
        if (StringUtils.isEmpty(csvObservationAttribute.getAttributeValue())) {
            return null;
        }

        // Keys

        // Data
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();
        localisedStringDto.setLabel(csvObservationAttribute.getAttributeValue());
        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
        localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
        internationalStringDto.addText(localisedStringDto);

        AttributeInstanceObservationDto attributeObservationDto = new AttributeInstanceObservationDto(csvObservationAttribute.getAttributeId(), internationalStringDto);

        return attributeObservationDto;
    }
}
