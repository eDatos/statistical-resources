package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import org.siemac.metamac.statistical.resources.web.client.widgets.filters.base.VersionableStatisticalResourceFilterBaseForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;

public class DsdFilterForm extends VersionableStatisticalResourceFilterBaseForm<DsdWebCriteria> {

    private String fixedDsdCode;

    public DsdFilterForm() {
        fixedDsdCode = null;
    }

    public void setFixedDsdCode(String fixedDsdCode) {
        this.fixedDsdCode = fixedDsdCode;
    }

    @Override
    public DsdWebCriteria getSearchCriteria() {
        DsdWebCriteria criteria = super.getSearchCriteria();
        criteria.setFixedDsdCode(fixedDsdCode);
        return criteria;
    }

    @Override
    protected DsdWebCriteria buildEmptySearchCriteria() {
        return new DsdWebCriteria();
    }

}
