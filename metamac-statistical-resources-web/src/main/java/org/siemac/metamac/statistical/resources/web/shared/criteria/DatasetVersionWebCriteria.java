package org.siemac.metamac.statistical.resources.web.shared.criteria;

import org.siemac.metamac.statistical.resources.web.shared.criteria.base.HasDataCriteria;

public class DatasetVersionWebCriteria extends VersionableStatisticalResourceWebCriteria implements HasDataCriteria {

    /**
     * 
     */
    private static final long serialVersionUID = 7050528031069849463L;
    private Boolean           hasData;

    public DatasetVersionWebCriteria() {
        super();
        this.hasData = null;
    }

    public DatasetVersionWebCriteria(String criteria, boolean hasData) {
        super(criteria);
        this.hasData = hasData;
    }

    public DatasetVersionWebCriteria(String criteria) {
        super(criteria);
        this.hasData = null;
    }

    @Override
    public Boolean getHasData() {
        return hasData;
    }
    @Override
    public void setHasData(Boolean hasData) {
        this.hasData = hasData;
    }
}
