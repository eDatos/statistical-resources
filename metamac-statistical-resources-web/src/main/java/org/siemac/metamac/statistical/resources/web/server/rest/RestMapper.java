package org.siemac.metamac.statistical.resources.web.server.rest;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeRelationship;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Representation;
import org.siemac.metamac.statistical.resources.web.server.utils.ExternalItemUtils;
import org.siemac.metamac.statistical.resources.web.shared.DTO.AttributeRelathionshipTypeEnum;
import org.siemac.metamac.statistical.resources.web.shared.DTO.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.web.shared.DTO.RelationshipDto;
import org.siemac.metamac.statistical.resources.web.shared.DTO.RepresentationDto;

public class RestMapper {

    public static DsdAttributeDto buildDsdAttributeDtoFromAttribute(Attribute attribute) {
        DsdAttributeDto dsdAttributeDto = new DsdAttributeDto();
        dsdAttributeDto.setCode(attribute.getId());
        dsdAttributeDto.setUrn(attribute.getUrn());
        dsdAttributeDto.setConceptIdentitity(ExternalItemUtils.buildExternalItemDtoFromResource(attribute.getConceptIdentity(), TypeExternalArtefactsEnum.CONCEPT));
        dsdAttributeDto.setRelateTo(buildRelationShipDtoFromAttributeRelationship(attribute.getAttributeRelationship()));
        dsdAttributeDto.setLocalRepresentation(buildRepresentationDtoFromRepresentation(attribute.getLocalRepresentation()));
        return dsdAttributeDto;
    }

    public static RelationshipDto buildRelationShipDtoFromAttributeRelationship(AttributeRelationship attributeRelationship) {
        RelationshipDto relationshipDto = new RelationshipDto();
        relationshipDto.setTypeRelathionship(getTypeRelathionship(attributeRelationship));
        return relationshipDto;
    }

    public static RepresentationDto buildRepresentationDtoFromRepresentation(Representation representation) {
        RepresentationDto representationDto = new RepresentationDto();
        return representationDto;
    }

    public static AttributeRelathionshipTypeEnum getTypeRelathionship(AttributeRelationship attributeRelationship) {
        if (StringUtils.isNotBlank(attributeRelationship.getPrimaryMeasure())) {
            return AttributeRelathionshipTypeEnum.PRIMARY_MEASURE_RELATIONSHIP;
        }
        if (StringUtils.isNotBlank(attributeRelationship.getGroup())) {
            return AttributeRelathionshipTypeEnum.GROUP_RELATIONSHIP;
        }
        if (attributeRelationship.getDimensions() != null && !attributeRelationship.getDimensions().isEmpty()) {
            return AttributeRelathionshipTypeEnum.DIMENSION_RELATIONSHIP;
        }
        if (attributeRelationship.getNone() != null) {
            return AttributeRelathionshipTypeEnum.NO_SPECIFIED_RELATIONSHIP;
        }
        return null;
    }
}
