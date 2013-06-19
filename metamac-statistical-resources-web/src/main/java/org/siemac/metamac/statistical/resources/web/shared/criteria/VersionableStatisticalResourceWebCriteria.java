package org.siemac.metamac.statistical.resources.web.shared.criteria;

import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class VersionableStatisticalResourceWebCriteria extends MetamacWebCriteria {

    private static final long serialVersionUID = 1L;

    private String            statisticalOperationUrn;
    
    //This filter is used if only last version can be specified
    private boolean onlyLastVersion;

    public VersionableStatisticalResourceWebCriteria() {
        onlyLastVersion = false;
    }

    public VersionableStatisticalResourceWebCriteria(String criteria) {
        super(criteria);
    }

    public String getStatisticalOperationUrn() {
        return statisticalOperationUrn;
    }

    public void setStatisticalOperationUrn(String statisticalOperationUrn) {
        this.statisticalOperationUrn = statisticalOperationUrn;
    }
    
    public void setOnlyLastVersion(boolean onlyLastVersion) {
        this.onlyLastVersion = onlyLastVersion;
    }
    
    public boolean isOnlyLastVersion() {
        return onlyLastVersion;
    }
}
