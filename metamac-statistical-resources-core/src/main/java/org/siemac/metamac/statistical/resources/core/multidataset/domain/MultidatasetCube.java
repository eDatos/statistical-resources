package org.siemac.metamac.statistical.resources.core.multidataset.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.RandomStringUtils;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;

/**
 * Multidataset cube, forked from TB_CUBES because they dont have elementLevel
 */
@Entity
@Table(name = "TB_MD_CUBES")
public class MultidatasetCube extends MultidatasetCubeBase {

    private static final long serialVersionUID = 1L;

    private static int        CODE_MAX_LENGTH  = 10;

    public MultidatasetCube() {
    }

    public String getDatasetUrn() {
        return super.getDataset() != null ? super.getDataset().getIdentifiableStatisticalResource().getUrn() : null;
    }

    public String getQueryUrn() {
        return super.getQuery() != null ? super.getQuery().getIdentifiableStatisticalResource().getUrn() : null;
    }

    public void fillCodeAndUrn() {
        String code = RandomStringUtils.randomAlphanumeric(CODE_MAX_LENGTH);
        getNameableStatisticalResource().setCode(code);
        getNameableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionCubeUrn(code));
    }
}
