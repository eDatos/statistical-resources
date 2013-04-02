package org.siemac.metamac.statistical.resources.web.shared.criteria;

public class StatisticalResourceWebCriteria extends MetamacWebCriteria {

    private static final long serialVersionUID = 1L;

    private String            statisticalOperationUrn;

    public StatisticalResourceWebCriteria() {
    }

    public StatisticalResourceWebCriteria(String criteria) {
        super(criteria);
    }

    public String getStatisticalOperationUrn() {
        return statisticalOperationUrn;
    }

    public void setStatisticalOperationUrn(String statisticalOperationUrn) {
        this.statisticalOperationUrn = statisticalOperationUrn;
    }
}
