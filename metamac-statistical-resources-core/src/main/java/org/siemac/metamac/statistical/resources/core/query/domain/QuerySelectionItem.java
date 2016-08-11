package org.siemac.metamac.statistical.resources.core.query.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity representing QuerySelectionItem.
 * <p>
 * This class is responsible for the domain object related
 * business logic for QuerySelectionItem. Properties and associations are
 * implemented in the generated base class {@link org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItemBase}.
 */
@Entity
@Table(name = "TB_QUERY_SELECTION_ITEMS", uniqueConstraints = {@UniqueConstraint(columnNames = {"DIMENSION", "QUERY_FK"})})
public class QuerySelectionItem extends QuerySelectionItemBase {
    private static final long serialVersionUID = 1L;

    public QuerySelectionItem() {
    }
}
