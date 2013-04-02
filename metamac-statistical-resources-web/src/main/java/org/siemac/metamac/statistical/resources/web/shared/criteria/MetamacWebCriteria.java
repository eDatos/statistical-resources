package org.siemac.metamac.statistical.resources.web.shared.criteria;

import java.io.Serializable;

public class MetamacWebCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String          criteria;

    public MetamacWebCriteria() {
    }

    public MetamacWebCriteria(String criteria) {
        setCriteria(criteria);
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
