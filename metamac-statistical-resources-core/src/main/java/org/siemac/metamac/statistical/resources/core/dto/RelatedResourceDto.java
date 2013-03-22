package org.siemac.metamac.statistical.resources.core.dto;

import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

/**
 * Data transfer object for RelatedResourceDto. Properties and associations are
 * implemented in the generated base class {@link org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDtoBase}.
 */
public class RelatedResourceDto extends RelatedResourceDtoBase {

    private static final long serialVersionUID = 1L;

    public RelatedResourceDto() {
    }

    public RelatedResourceDto(String code, String uri, String urn, TypeRelatedResourceEnum type) {
        super(code, uri, urn, type);
    }
}
