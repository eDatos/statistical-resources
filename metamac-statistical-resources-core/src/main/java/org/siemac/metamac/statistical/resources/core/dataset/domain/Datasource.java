package org.siemac.metamac.statistical.resources.core.dataset.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.DateTime;

/**
 * Entity representing Datasource.
 * <p>
 * This class is responsible for the domain object related business logic for Datasource. Properties and associations are implemented in the generated base class
 * {@link org.siemac.metamac.statistical.resources.core.dataset.domain.DatasourceBase}.
 */
@Entity
@Table(name = "TB_DATASOURCES")
public class Datasource extends DatasourceBase {

    private static final long serialVersionUID = 1L;

    public Datasource() {
    }

    public static String generateDataSourceId(String fileName, DateTime createdDate) {
        return fileName + "_" + createdDate.toString();
    }
}
