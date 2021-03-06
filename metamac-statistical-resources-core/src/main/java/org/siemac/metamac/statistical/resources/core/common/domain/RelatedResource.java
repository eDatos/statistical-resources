package org.siemac.metamac.statistical.resources.core.common.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

/**
 * Entity for store information about RelatedResources.
 */
@Entity
@Table(name = "TB_RELATED_RESOURCES")
public class RelatedResource extends RelatedResourceBase {
    private static final long serialVersionUID = 1L;

    public RelatedResource() {
    }

    public RelatedResource(TypeRelatedResourceEnum type) {
        super(type);
    }
}
