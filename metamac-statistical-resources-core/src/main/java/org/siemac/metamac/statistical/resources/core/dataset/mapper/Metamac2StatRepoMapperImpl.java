package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeRelationshipType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.InternationalStringDto;
import com.arte.statistic.dataset.repository.dto.LocalisedStringDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoTypeEnum;
import com.arte.statistic.parser.sdmx.v2_1.domain.DataContainer;
import com.arte.statistic.parser.sdmx.v2_1.domain.Group;
import com.arte.statistic.parser.sdmx.v2_1.domain.IdValuePair;
import com.arte.statistic.parser.sdmx.v2_1.domain.Observation;
import com.arte.statistic.parser.sdmx.v2_1.domain.Serie;

@Component(value = "metamac2StatRepoMapper")
public class Metamac2StatRepoMapperImpl implements Metamac2StatRepoMapper {

    @Override
    public void populateDatas(DataContainer dataContainer, Map<String, Object> attributesMap, List<ObservationExtendedDto> dataDtos, List<AttributeDto> attributeDtos) throws MetamacException {
        if (dataDtos == null || attributeDtos == null) {
            return;
        }

        // Data in Series
        if (!dataContainer.getSeries().isEmpty()) {
            processDatasInSeries(dataContainer, attributesMap, dataDtos, attributeDtos);
        } else if (!dataContainer.getObservations().isEmpty()) {
            processDatasInFlat(dataContainer, attributesMap, dataDtos, attributeDtos);
        }

    }

    private void processDatasInFlat(DataContainer dataContainer, Map<String, Object> attributesMap, List<ObservationExtendedDto> dataDtos, List<AttributeDto> attributeDtos) {
        // Data in observations
        for (Observation observation : dataContainer.getObservations()) {
            ObservationExtendedDto dataDto = new ObservationExtendedDto();

            // Keys
            dataDto.getCodesDimension().addAll(processKeyOfObservation(observation));

            // Data
            dataDto.setPrimaryMeasure(observation.getObservationValue().getValue());

            // Attributes *************************
            for (IdValuePair idValuePair : observation.getAttributes()) {
                AttributeDto attributeDto = processAttribute(dataDto.getCodesDimension(), idValuePair);

                if (isAttributeAtObservationLevel(attributesMap.get(attributeDto.getAttributeId()))) {
                    dataDto.addAttribute(attributeDto); // Add Attribute at observation level
                } else {
                    attributeDtos.add(attributeDto); // Add an attribute to different level of observation.
                }
            }

            dataDtos.add(dataDto); // Add Data
        }
    }

    public static boolean isAttributeAtObservationLevel(Object attribute) {
        if (attribute instanceof ReportingYearStartDayType) {
            return (((ReportingYearStartDayType) attribute).getAttributeRelationship().getPrimaryMeasure() != null);
        } else if (attribute instanceof AttributeType) {
            return (((AttributeType) attribute).getAttributeRelationship().getPrimaryMeasure() != null);
        }
        throw new RuntimeException("Attribute object not supported!");
    }

    private void processDatasInSeries(DataContainer dataContainer, Map<String, Object> attributesMap, List<ObservationExtendedDto> dataDtos, List<AttributeDto> attributeDtos) {
        for (Serie serie : dataContainer.getSeries()) {

            for (Observation observation : serie.getObs()) {
                // Data ***********************
                ObservationExtendedDto dataDto = new ObservationExtendedDto();

                // Keys
                dataDto.getCodesDimension().addAll(processKeyOfObservation(serie.getSeriesKey(), observation));

                // Observation
                dataDto.setPrimaryMeasure(observation.getObservationValue().getValue());

                // Attributes *************************
                for (IdValuePair idValuePair : observation.getAttributes()) {
                    AttributeDto attributeDto = processAttribute(dataDto.getCodesDimension(), idValuePair);

                    if (isAttributeAtObservationLevel(attributesMap.get(attributeDto.getAttributeId()))) {
                        dataDto.addAttribute(attributeDto); // Add Attribute at observation level
                    } else {
                        attributeDtos.add(attributeDto); // Add an attribute to different level of observation.
                    }
                }
                dataDtos.add(dataDto); // Add Data

            }

            // Attributes *************************
            if (!serie.getAttributes().isEmpty()) {

                List<CodeDimensionDto> codeDimensionDtos = processKeyOfObservation(serie.getSeriesKey(), null);

                for (IdValuePair idValuePair : serie.getAttributes()) {
                    attributeDtos.add(processAttribute(codeDimensionDtos, idValuePair)); // Add attributeDtos
                }
            }

        }
    }

    @Override
    public void processGroupAttribute(List<Group> groups, List<AttributeDto> attributeDtos) throws MetamacException {
        if (attributeDtos == null) {
            return;
        }

        for (Group group : groups) {
            // Group Keys
            List<CodeDimensionDto> keys = new ArrayList<CodeDimensionDto>();
            for (IdValuePair idValuePair : group.getGroupKey()) {
                keys.add(new CodeDimensionDto(idValuePair.getCode(), idValuePair.getValue()));
            }

            // Attributes
            for (IdValuePair idValuePair : group.getAttributes()) {
                attributeDtos.add(processAttribute(keys, idValuePair)); // Add attributeDtos
            }
        }

    }

    @Override
    public void processDatasetAttribute(List<IdValuePair> attributes, List<AttributeDto> attributeDtos) throws MetamacException {
        if (attributeDtos == null) {
            return;
        }

        // Attributes
        for (IdValuePair attribute : attributes) {
            attributeDtos.add(processAttribute(new ArrayList<CodeDimensionDto>(), attribute));
        }
    }

    @Override
    public String generateAttributeKeyInAttachmentLevel(AttributeDto attributeDto, AttributeRelationshipType attributeRelationship, List<ComponentInfo> dimensionsInfo,
            Map<String, List<ComponentInfo>> groupDimensionMapInfo) throws MetamacException {

        StringBuilder customKeyAttribute = new StringBuilder();

        // Key attribute computation (partial keys)

        // Attachment Level = DATASET
        if (attributeRelationship.getNone() != null) {
            customKeyAttribute.append(attributeDto.getAttributeId());
            return customKeyAttribute.toString();
        }

        // Attachment Level = OBSERVATION
        if (attributeRelationship.getPrimaryMeasure() != null) {
            // Nothing: the key is full
            customKeyAttribute.append(attributeDto.getAttributeId());
            for (CodeDimensionDto codeDimensionDto : attributeDto.getCodesDimension()) {
                customKeyAttribute.append(codeDimensionDto.getDimensionId()).append(codeDimensionDto.getCodeDimensionId());
            }
            return customKeyAttribute.toString();
        }

        // Attachment Level = GROUP
        if (attributeRelationship.getGroup() != null) {
            List<CodeDimensionDto> codeDimensionDtos = new ArrayList<CodeDimensionDto>(); // Dimensions and values to insert

            // For all dimensions key in the attachment of current attribute
            for (ComponentInfo componentInfo : dimensionsInfo) {
                // Search key Value of this attribute
                for (CodeDimensionDto codeDimensionDto : attributeDto.getCodesDimension()) {
                    // Find the key value
                    if (componentInfo.getCode().equals(codeDimensionDto.getDimensionId())) {
                        codeDimensionDtos.add(codeDimensionDto);
                        customKeyAttribute.append(codeDimensionDto.getDimensionId()).append(codeDimensionDto.getCodeDimensionId());
                        break;
                    }
                }
            }
            attributeDto.getCodesDimension().clear();
            attributeDto.getCodesDimension().addAll(codeDimensionDtos);
        }

        // Dimension relationship
        if (!attributeRelationship.getDimensions().isEmpty() || !attributeRelationship.getAttachmentGroups().isEmpty()) {
            Map<String, CodeDimensionDto> codeDimensionDtosMap = new HashMap<String, CodeDimensionDto>(); // Dimensions and values to insert
            boolean foundTimeDimension = false; // Is time Dimension presented

            // For all dimensions key in the attachment of current attribute
            for (ComponentInfo componentInfo : dimensionsInfo) {
                // Search key Value of this attribute
                for (CodeDimensionDto codeDimensionDto : attributeDto.getCodesDimension()) {
                    // Find the key value
                    if (componentInfo.getCode().equals(codeDimensionDto.getDimensionId())) {
                        foundTimeDimension = (ComponentInfoTypeEnum.TIME_DIMENSION.equals(componentInfo.getTypeComponentInfo()));
                        codeDimensionDtosMap.put(codeDimensionDto.getDimensionId(), codeDimensionDto);
                        customKeyAttribute.append(codeDimensionDto.getDimensionId()).append(codeDimensionDto.getCodeDimensionId());
                        break;
                    }
                }
            }

            // Note that if one of the referenced dimensions is the time dimension, the groups referenced here will be ignored.
            // See: SDMXStructureDataStructure.xsd => AttributeRelationshipType.AttachmentGroup
            // TODO Hablar con rita q signfica esto, pq me aprece q no es apra compactar sino para validar la estructura del mensaje, luego esto sería para el parser
            // no para aquí

            // if (!foundTimeDimension) {
            // // For all groups in this attachment of current attribute
            // for (LocalGroupKeyDescriptorReferenceType localGroupKeyDescriptorReferenceType : attributeRelationship.getAttachmentGroups()) {
            // List<ComponentInfo> dimensionsGroup = groupDimensionMapInfo.get(localGroupKeyDescriptorReferenceType.getRef().getId());
            // // For all dimensions key in the group attachment of current attribute
            // for (ComponentInfo dimensionKey : dimensionsGroup) {
            // // Search key Value of this attribute
            // for (CodeDimensionDto codeDimensionDto : attributeDto.getCodesDimension()) {
            // // Find the key value
            // if (dimensionKey.getCode().equals(codeDimensionDto.getDimensionId())) {
            // codeDimensionDtosMap.put(codeDimensionDto.getDimensionId(), codeDimensionDto);
            // customKeyAttribute.append(codeDimensionDto.getDimensionId()).append(codeDimensionDto.getCodeDimensionId());
            // break;
            // }
            // }
            // }
            // }
            // }
            attributeDto.getCodesDimension().clear();
            attributeDto.getCodesDimension().addAll(codeDimensionDtosMap.values());
        }

        return StringUtils.EMPTY;
    }
    /**************************************************************************
     * PRIVATE
     *************************************************************************/

    /**
     * Generate a attributeDto form idValuePairDto and keys
     * 
     * @param keys
     * @param idValuePair
     * @return AttributeDto
     */
    private AttributeDto processAttribute(List<CodeDimensionDto> keys, IdValuePair idValuePair) {
        AttributeDto attributeDto = new AttributeDto();

        // Keys
        attributeDto.getCodesDimension().addAll(keys);

        // Data
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();
        localisedStringDto.setLabel(idValuePair.getValue());
        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
        localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
        internationalStringDto.addText(localisedStringDto);

        attributeDto.setValue(internationalStringDto);

        // Attribute Id
        attributeDto.setAttributeId(idValuePair.getCode());
        return attributeDto;
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

        List<CodeDimensionDto> codeDimensionDtos = new ArrayList<CodeDimensionDto>();

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
