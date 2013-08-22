package org.siemac.metamac.statistical.resources.web.shared.DTO;

import java.util.HashSet;
import java.util.Set;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.IdentityDto;

public class RelationshipDto extends IdentityDto {

    private static final long    serialVersionUID                  = 1L;
    private AttributeRelathionshipTypeEnum    typeRelathionship;
    private ExternalItemDto      groupKeyForGroupRelationship;
    private Set<ExternalItemDto> groupKeyForDimensionRelationship  = new HashSet<ExternalItemDto>();
    private Set<ExternalItemDto> dimensionForDimensionRelationship = new HashSet<ExternalItemDto>();

    public RelationshipDto() {
    }

    public RelationshipDto(AttributeRelathionshipTypeEnum typeRelathionship) {
        super();
        this.typeRelathionship = typeRelathionship;
    }

    public AttributeRelathionshipTypeEnum getTypeRelathionship() {
        return typeRelathionship;
    }

    public void setTypeRelathionship(AttributeRelathionshipTypeEnum typeRelathionship) {
        this.typeRelathionship = typeRelathionship;
    }

    public ExternalItemDto getGroupKeyForGroupRelationship() {
        return groupKeyForGroupRelationship;
    }

    public Set<ExternalItemDto> getGroupKeyForDimensionRelationship() {
        return groupKeyForDimensionRelationship;
    }

    public Set<ExternalItemDto> getDimensionForDimensionRelationship() {
        return dimensionForDimensionRelationship;
    }

    public void setGroupKeyForGroupRelationship(ExternalItemDto groupKeyForGroupRelationship) {
        this.groupKeyForGroupRelationship = groupKeyForGroupRelationship;
    }

    public void setGroupKeyForDimensionRelationship(Set<ExternalItemDto> groupKeyForDimensionRelationship) {
        this.groupKeyForDimensionRelationship = groupKeyForDimensionRelationship;
    }

    public void setDimensionForDimensionRelationship(Set<ExternalItemDto> dimensionForDimensionRelationship) {
        this.dimensionForDimensionRelationship = dimensionForDimensionRelationship;
    }
}
