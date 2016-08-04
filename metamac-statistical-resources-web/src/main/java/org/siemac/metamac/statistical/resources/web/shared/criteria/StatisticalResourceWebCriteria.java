package org.siemac.metamac.statistical.resources.web.shared.criteria;

import org.siemac.metamac.statistical.resources.web.shared.criteria.base.HasStatisticalOperationCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class StatisticalResourceWebCriteria extends MetamacWebCriteria implements HasStatisticalOperationCriteria {

    private static final long serialVersionUID = 1L;

    private String            statisticalOperationUrn;

    public StatisticalResourceWebCriteria() {
    }

    public StatisticalResourceWebCriteria(String criteria) {
        super(criteria);
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
