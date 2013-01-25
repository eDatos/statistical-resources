package org.siemac.metamac.statistical.resources.core.base.domain;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity for store information about ExternalItems.
 */
@Entity
@Table(name = "TB_RELATED_RESOURCES")
public class RelatedResource extends RelatedResourceBase {
    private static final long serialVersionUID = 1L;

    protected RelatedResource() {
    }

    public RelatedResource(String code, String uri, String urn,
        TypeExternalArtefactsEnum type) {
        super(code, uri, urn, type);
    }
}
