package org.siemac.metamac.statistical.resources.core.base.domain;

import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity for store information about RelatedResources.
 */
@Entity
@Table(name = "TB_RELATED_RESOURCES")
public class RelatedResource extends RelatedResourceBase {
    private static final long serialVersionUID = 1L;

    protected RelatedResource() {
    }

    public RelatedResource(TypeRelatedResourceEnum type) {
        super(type);
    }
}
