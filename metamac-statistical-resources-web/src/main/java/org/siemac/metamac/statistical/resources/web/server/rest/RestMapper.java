package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeRelationship;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.RelationshipDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.RepresentationDto;
import org.siemac.metamac.statistical.resources.core.enume.dataset.domain.AttributeRelationshipTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.dataset.domain.AttributeRepresentationTypeEnum;

public class RestMapper {

    public static List<DsdAttributeDto> buildDsdAttributeDtosFromDsdAttributes(List<DsdAttribute> dsdAttributes) {
        List<DsdAttributeDto> dsdAttributeDtos = new ArrayList<DsdAttributeDto>();
        for (DsdAttribute dsdAttribute : dsdAttributes) {
            dsdAttributeDtos.add(buildDsdAttributeDtoFromDsdAttribute(dsdAttribute));
        }
        return dsdAttributeDtos;
    }

    public static DsdAttributeDto buildDsdAttributeDtoFromDsdAttribute(DsdAttribute dsdAttribute) {
        DsdAttributeDto dsdAttributeDto = new DsdAttributeDto();
        dsdAttributeDto.setIdentifier(dsdAttribute.getComponentId());
        dsdAttributeDto.setAttributeRelationship(buildRelationShipDtoFromAttributeRelationship(dsdAttribute.getAttributeRelationship()));
        dsdAttributeDto.setAttributeRepresentation(buildRepresentationDtoFromDsdAttribute(dsdAttribute));
        return dsdAttributeDto;
    }

    public static RelationshipDto buildRelationShipDtoFromAttributeRelationship(AttributeRelationship attributeRelationship) {
        RelationshipDto relationshipDto = new RelationshipDto();
        relationshipDto.setRelationshipType(getTypeRelathionship(attributeRelationship));
        return relationshipDto;
    }

    public static RepresentationDto buildRepresentationDtoFromDsdAttribute(DsdAttribute dsdAttribute) {
        RepresentationDto representationDto = new RepresentationDto();
        if (StringUtils.isNotBlank(dsdAttribute.getCodelistRepresentationUrn()) || StringUtils.isNotBlank(dsdAttribute.getConceptSchemeRepresentationUrn())) {
            representationDto.setRepresentationType(AttributeRepresentationTypeEnum.ENUMERATION);
        } else if (dsdAttribute.getTextFormatRepresentation() != null) {
            representationDto.setRepresentationType(AttributeRepresentationTypeEnum.TEXT_FORMAT);
        }
        representationDto.setCodelistRepresentationUrn(dsdAttribute.getCodelistRepresentationUrn());
        representationDto.setConceptSchemeRepresentationUrn(dsdAttribute.getConceptSchemeRepresentationUrn());
        return representationDto;
    }

    public static AttributeRelationshipTypeEnum getTypeRelathionship(AttributeRelationship attributeRelationship) {
        if (StringUtils.isNotBlank(attributeRelationship.getPrimaryMeasure())) {
            return AttributeRelationshipTypeEnum.PRIMARY_MEASURE_RELATIONSHIP;
        }
        if (StringUtils.isNotBlank(attributeRelationship.getGroup())) {
            return AttributeRelationshipTypeEnum.GROUP_RELATIONSHIP;
        }
        if (attributeRelationship.getDimensions() != null && !attributeRelationship.getDimensions().isEmpty()) {
            return AttributeRelationshipTypeEnum.DIMENSION_RELATIONSHIP;
        }
        if (attributeRelationship.getNone() != null) {
            return AttributeRelationshipTypeEnum.NO_SPECIFIED_RELATIONSHIP;
        }
        return null;
    }
}
