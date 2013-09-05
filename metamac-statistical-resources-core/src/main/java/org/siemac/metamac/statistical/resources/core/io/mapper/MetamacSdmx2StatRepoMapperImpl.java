package org.siemac.metamac.statistical.resources.core.io.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.io.utils.ManipulateDataUtils;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceObservationDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ConditionDimensionDto;
import com.arte.statistic.dataset.repository.dto.InternationalStringDto;
import com.arte.statistic.dataset.repository.dto.LocalisedStringDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.DataContainer;
import com.arte.statistic.parser.sdmx.v2_1.domain.DimensionCodeInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.IdValuePair;
import com.arte.statistic.parser.sdmx.v2_1.domain.Observation;
import com.arte.statistic.parser.sdmx.v2_1.domain.Serie;

@Component(value = "metamacSdmx2StatRepoMapper")
public class MetamacSdmx2StatRepoMapperImpl implements MetamacSdmx2StatRepoMapper {

    public static String ATTRIBUTE_GEN_KEY_SEPARATOR = ":";

    @Override
    public void populateDatas(DataContainer dataContainer, Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap, List<ObservationExtendedDto> dataDtos, String datasourceId)
            throws MetamacException {
        if (dataDtos == null) {
            return;
        }

        // Data in Series
        if (!dataContainer.getSeries().isEmpty()) {
            processDatasInSeries(dataContainer, attributesProcessorMap, dataDtos, datasourceId);
        } else if (!dataContainer.getObservations().isEmpty()) {
            processDatasInFlat(dataContainer, attributesProcessorMap, dataDtos, datasourceId);
        }

    }

    private void processDatasInFlat(DataContainer dataContainer, Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap, List<ObservationExtendedDto> dataDtos, String datasourceId)
            throws MetamacException {
        // Data in observations
        for (Observation observation : dataContainer.getObservations()) {
            ObservationExtendedDto dataDto = new ObservationExtendedDto();

            // Keys
            dataDto.getCodesDimension().addAll(processKeyOfObservation(observation));

            // Data
            dataDto.setPrimaryMeasure(observation.getObservationValue().getValue());

            // Attributes *************************
            dataDto.addAttribute(ManipulateDataUtils.createDataSourceIdentificationAttribute(dataDto.getCodesDimension(), datasourceId)); // Add identification datasource attribute

            for (IdValuePair idValuePair : observation.getAttributes()) {

                // If the attribute value is not empty
                if (!StringUtils.isEmpty(idValuePair.getValue())) {
                    if (attributesProcessorMap.containsKey(idValuePair.getCode())) {
                        if (attributesProcessorMap.get(idValuePair.getCode()).isAttributeAtObservationLevel()) {
                            dataDto.addAttribute(processAttributeObservation(idValuePair)); // Add Attribute at observation level
                        } else {
                            // Add an attribute to different level of observation (in Metamac the attributes that not are in observation level are not imported)
                        }
                    }
                }
            }

            dataDtos.add(dataDto); // Add Data
        }
    }

    private void processDatasInSeries(DataContainer dataContainer, Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap, List<ObservationExtendedDto> dataDtos, String datasourceId)
            throws MetamacException {
        for (Serie serie : dataContainer.getSeries()) {

            for (Observation observation : serie.getObs()) {
                // Data ***********************
                ObservationExtendedDto dataDto = new ObservationExtendedDto();

                // Keys
                dataDto.getCodesDimension().addAll(processKeyOfObservation(serie.getSeriesKey(), observation));

                // Observation
                dataDto.setPrimaryMeasure(observation.getObservationValue().getValue());

                // Attributes *************************
                dataDto.addAttribute(ManipulateDataUtils.createDataSourceIdentificationAttribute(dataDto.getCodesDimension(), datasourceId)); // Add identification datasource attribute

                for (IdValuePair idValuePair : observation.getAttributes()) {

                    // If the attribute value is not empty
                    if (!StringUtils.isEmpty(idValuePair.getValue())) {
                        if (attributesProcessorMap.containsKey(idValuePair.getCode())) {
                            if (attributesProcessorMap.get(idValuePair.getCode()).isAttributeAtObservationLevel()) {
                                dataDto.addAttribute(processAttributeObservation(idValuePair)); // Add Attribute at observation level
                            } else {
                                // Add an attribute to different level of observation (in Metamac the attributes that not are in observation level are not imported)
                            }
                        }
                    }
                }
                dataDtos.add(dataDto); // Add Data
            }
        }
    }

    @Override
    public List<ConditionDimensionDto> conditionsToRepositoryList(List<DimensionCodeInfo> serieConditions) throws MetamacException {
        if (serieConditions == null) {
            return null;
        }

        List<ConditionDimensionDto> conditions = new ArrayList<ConditionDimensionDto>(serieConditions.size());
        for (DimensionCodeInfo dimensionCodeInfo : serieConditions) {
            ConditionDimensionDto conditionDimensionDto = new ConditionDimensionDto();
            conditionDimensionDto.setDimensionId(dimensionCodeInfo.getCode());
            conditionDimensionDto.getCodesDimension().addAll(dimensionCodeInfo.getCodes());
            conditions.add(conditionDimensionDto);
        }

        return conditions;
    }

    @Override
    public Map<String, List<String>> conditionsToRepositoryMap(List<DimensionCodeInfo> serieConditions) throws MetamacException {
        Map<String, List<String>> conditionsMap = new HashMap<String, List<String>>();
        for (DimensionCodeInfo dimensionCodeInfo : serieConditions) {
            conditionsMap.put(dimensionCodeInfo.getCode(), new ArrayList<String>(dimensionCodeInfo.getCodes()));
        }

        return conditionsMap;
    }

    /**************************************************************************
     * PRIVATE
     *************************************************************************/

    /**
     * Generate a attributeObservationDtoDto form idValuePairDto and keys
     * 
     * @param keys
     * @param idValuePair
     * @return AttributeDto
     */
    private AttributeInstanceObservationDto processAttributeObservation(IdValuePair idValuePair) {
        // AttributeDto attributeDto = new AttributeDto();
        if (StringUtils.isEmpty(idValuePair.getValue())) {
            return null;
        }

        // Data
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();
        localisedStringDto.setLabel(idValuePair.getValue());
        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
        localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
        internationalStringDto.addText(localisedStringDto);

        AttributeInstanceObservationDto attributeObservationDto = new AttributeInstanceObservationDto(idValuePair.getCode(), internationalStringDto);

        return attributeObservationDto;
    }

    /**
     * Generate a list of keys for an observation
     * 
     * @param observation
     * @return list of keys for an observation
     */
    private List<CodeDimensionDto> processKeyOfObservation(Observation observation) {
        return processKeyOfObservation(null, observation);
    }

    /**
     * Generate a list of keys for an observation
     * 
     * @param sliceKey
     * @param observation
     * @return list of keys for an observation
     */
    private List<CodeDimensionDto> processKeyOfObservation(List<IdValuePair> sliceKey, Observation observation) {

        List<CodeDimensionDto> codeDimensionDtos = new LinkedList<CodeDimensionDto>();

        // Add slice key of Observation
        if (sliceKey != null) {
            for (IdValuePair obsKey : sliceKey) {
                codeDimensionDtos.add(new CodeDimensionDto(obsKey.getCode(), obsKey.getValue()));
            }
        }

        // Add key at observation level
        if (observation != null && observation.getObservationKey() != null) {
            for (IdValuePair obsKey : observation.getObservationKey()) {
                codeDimensionDtos.add(new CodeDimensionDto(obsKey.getCode(), obsKey.getValue()));
            }
        }

        return codeDimensionDtos;
    }

}
