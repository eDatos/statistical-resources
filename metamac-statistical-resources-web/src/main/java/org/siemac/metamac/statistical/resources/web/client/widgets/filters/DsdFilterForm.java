package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;

public class DsdFilterForm extends VersionableStatisticalResourceFilterForm<DsdWebCriteria> {

    private String fixedDsdCode;

    public DsdFilterForm() {
        fixedDsdCode = null;
    }

    public void setFixedDsdCode(String fixedDsdCode) {
        this.fixedDsdCode = fixedDsdCode;
    }

    @Override
    public DsdWebCriteria getSearchCriteria() {
        DsdWebCriteria criteria = new DsdWebCriteria();
        populateVersionableStatisticalResourceSearchCriteria(criteria);
        criteria.setFixedDsdCode(fixedDsdCode);
        return criteria;
    }
}
