package org.siemac.metamac.statistical.resources.web.shared.DTO;

import org.siemac.metamac.core.common.dto.AuditableDto;
import org.siemac.metamac.core.common.dto.ExternalItemDto;

public class DsdAttributeDto extends AuditableDto {

    private String            code;
    private String            urn;
    private ExternalItemDto   conceptIdentitity;
    private RepresentationDto localRepresentation;
    private RelationshipDto   relateTo;

    private static final long serialVersionUID = 1L;

    public String getCode() {
        return code;
    }

    public String getUrn() {
        return urn;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public DsdAttributeDto() {
    }

    public ExternalItemDto getConceptIdentitity() {
        return conceptIdentitity;
    }

    public RepresentationDto getLocalRepresentation() {
        return localRepresentation;
    }

    public RelationshipDto getRelateTo() {
        return relateTo;
    }

    public void setConceptIdentitity(ExternalItemDto conceptIdentitity) {
        this.conceptIdentitity = conceptIdentitity;
    }

    public void setLocalRepresentation(RepresentationDto localRepresentation) {
        this.localRepresentation = localRepresentation;
    }

    public void setRelateTo(RelationshipDto relateTo) {
        this.relateTo = relateTo;
    }
}
