package org.siemac.metamac.statistical.resources.core.query.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;

/**
 * Entity representing QueryVersion.
 * <p>
 * This class is responsible for the domain object related
 * business logic for QueryVersion. Properties and associations are
 * implemented in the generated base class {@link org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionBase}.
 */
@Entity
@Table(name = "TB_QUERIES_VERSIONS")
public class QueryVersion extends QueryVersionBase implements HasLifecycle {
    private static final long serialVersionUID = 1L;

    public QueryVersion() {
    }
}
