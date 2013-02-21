package org.siemac.metamac.statistical.resources.core.query.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity representing CodeItem.
 * <p>
 * This class is responsible for the domain object related
 * business logic for CodeItem. Properties and associations are
 * implemented in the generated base class {@link org.siemac.metamac.statistical.resources.core.query.domain.CodeItemBase}.
 */
@Entity
@Table(name = "TB_CODE_ITEMS", uniqueConstraints = {@UniqueConstraint(columnNames = {"CODE", "QUERY_SELECTION_ITEM_FK"})})
public class CodeItem extends CodeItemBase {
    private static final long serialVersionUID = 1L;

    public CodeItem() {
    }
}
