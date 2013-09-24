package org.siemac.metamac.statistical.resources.core.dataset.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.DateTime;

/**
 * Associates an Dataset with a Category
 */
@Entity
@Table(name = "TB_CATEGORISATIONS")
public class Categorisation extends CategorisationBase {

    private static final long serialVersionUID = 1L;

    public Categorisation() {
    }

    @Override
    public DateTime getValidFromEffective() {
        if (this.getVersionableStatisticalResource().getValidFrom() != null) {
            return this.getVersionableStatisticalResource().getValidFrom();
        } else {
            return this.getDatasetVersion().getSiemacMetadataStatisticalResource().getValidFrom();
        }
    }

    @Override
    public DateTime getValidToEffective() {
        if (this.getVersionableStatisticalResource().getValidTo() != null) {
            return this.getVersionableStatisticalResource().getValidTo();
        } else {
            return this.getDatasetVersion().getSiemacMetadataStatisticalResource().getValidTo();
        }
    }
}
