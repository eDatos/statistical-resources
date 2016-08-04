package org.siemac.metamac.statistical.resources.web.client.widgets.filters;

import org.siemac.metamac.statistical.resources.web.client.widgets.filters.base.VersionableStatisticalResourceFilterBaseForm;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;

public class VersionableStatisticalResourceFilterForm extends VersionableStatisticalResourceFilterBaseForm<VersionableStatisticalResourceWebCriteria> {

    public VersionableStatisticalResourceFilterForm() {
        super();
    }

    @Override
    protected VersionableStatisticalResourceWebCriteria buildEmptySearchCriteria() {
        return new VersionableStatisticalResourceWebCriteria();
    }
}
