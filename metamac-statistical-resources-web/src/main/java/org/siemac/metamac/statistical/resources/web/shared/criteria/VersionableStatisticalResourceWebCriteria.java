package org.siemac.metamac.statistical.resources.web.shared.criteria;

import org.siemac.metamac.statistical.resources.web.shared.criteria.base.HasStatisticalOperationCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacVersionableWebCriteria;

public class VersionableStatisticalResourceWebCriteria extends MetamacVersionableWebCriteria implements HasStatisticalOperationCriteria {

    private static final long serialVersionUID = 1L;

    private String            statisticalOperationUrn;

    public VersionableStatisticalResourceWebCriteria() {
        super();
    }

    public VersionableStatisticalResourceWebCriteria(String criteria) {
        super(criteria, true);
    }

    @Override
    public String getStatisticalOperationUrn() {
        return statisticalOperationUrn;
    }

    @Override
    public void setStatisticalOperationUrn(String statisticalOperationUrn) {
        this.statisticalOperationUrn = statisticalOperationUrn;
    }

}
