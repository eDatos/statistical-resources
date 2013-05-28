package org.siemac.metamac.statistical.resources.web.shared.criteria;

import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;


public class CommonConfigurationWebCriteria extends MetamacWebCriteria {
    /**
     * 
     */
    private static final long serialVersionUID = 5792305181490687772L;
    private boolean onlyEnabled;
    
    public CommonConfigurationWebCriteria() {
        this.onlyEnabled = true;
    }

    public boolean isOnlyEnabled() {
        return onlyEnabled;
    }

    public void setOnlyEnabled(boolean onlyEnabled) {
        this.onlyEnabled = onlyEnabled;
    }
}
