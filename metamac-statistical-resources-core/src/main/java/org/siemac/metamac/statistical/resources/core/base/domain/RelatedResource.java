package org.siemac.metamac.statistical.resources.core.base.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

/**
 * Entity for store information about ExternalItems.
 */
@Entity
@Table(name = "TB_RELATED_RESOURCES")
public class RelatedResource extends RelatedResourceBase {

    private static final long serialVersionUID = 1L;

    protected RelatedResource() {
    }

    public RelatedResource(String code, String uri, String urn, TypeRelatedResourceEnum type) {
        super(code, uri, urn, type);
    }
}
