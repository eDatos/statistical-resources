package org.siemac.metamac.statistical.resources.web.shared.DTO;

import org.siemac.metamac.core.common.dto.IdentityDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;

public class RepresentationDto extends IdentityDto {

    private static final long      serialVersionUID = 1L;
    private RepresentationTypeEnum representationType;
    private RelatedResourceDto     enumeration;
    private FacetDto               textFormat;

    public RepresentationDto() {
    }

    public RepresentationDto(RepresentationTypeEnum representationType) {
        super();
        this.representationType = representationType;
    }

    public RepresentationTypeEnum getRepresentationType() {
        return representationType;
    }

    public void setRepresentationType(RepresentationTypeEnum representationType) {
        this.representationType = representationType;
    }

    /**
     * Association to an enumerated list that contains the allowable content for the Component when reported in a data or metadata set. The type of enumerated list that is
     * allowed for any concrete Component is shown in the constraints on the association. (e.g. IdentifierComponent can have any of the sub classes of ItemScheme, whereas Measure
     * Dimension must have a Concept Scheme.
     */
    public RelatedResourceDto getEnumeration() {
        return enumeration;
    }

    /**
     * Association to an enumerated list that contains the allowable content for the Component when reported in a data or metadata set. The type of enumerated list that is
     * allowed for any concrete Component is shown in the constraints on the association. (e.g. IdentifierComponent can have any of the sub classes of ItemScheme, whereas Measure
     * Dimension must have a Concept Scheme.
     */
    public void setEnumeration(RelatedResourceDto enumeration) {
        this.enumeration = enumeration;
    }

    /**
     * Association to a set of Facets that define the allowable format for the content of the Component when reported in a data or metadata set.
     */
    public FacetDto getTextFormat() {
        return textFormat;
    }

    /**
     * Association to a set of Facets that define the allowable format for the content of the Component when reported in a data or metadata set.
     */
    public void setTextFormat(FacetDto textFormat) {
        this.textFormat = textFormat;
    }
}
