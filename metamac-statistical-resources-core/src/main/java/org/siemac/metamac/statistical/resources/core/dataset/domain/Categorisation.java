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
        // We don't use the datasetVersion to calculate the effective validTo because the end of
        // validity of the dataset does not mean the end of validity of the categorisation (METAMAC-2157)
        return this.getVersionableStatisticalResource().getValidTo();
    }
}
