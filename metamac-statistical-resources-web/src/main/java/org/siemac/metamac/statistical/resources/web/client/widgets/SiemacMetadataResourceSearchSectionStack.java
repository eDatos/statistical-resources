package org.siemac.metamac.statistical.resources.web.client.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.statistical.resources.web.shared.criteria.SiemacMetadataStatisticalResourceWebCriteria;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;

public abstract class SiemacMetadataResourceSearchSectionStack extends LifeCycleResourceSearchSectionStack {

    public SiemacMetadataResourceSearchSectionStack() {
    }

    @Override
    protected void setFormItemsInAdvancedSearchForm(FormItem[] advancedSearchFormItems) {
        super.setFormItemsInAdvancedSearchForm(advancedSearchFormItems);
    }

    @Override
    protected void createAdvancedSearchForm() {
        super.createAdvancedSearchForm();
        TextItem titleAlternative = new TextItem(SiemacMetadataDS.TITLE_ALTERNATIVE, getConstants().siemacMetadataStatisticalResourceTitleAlternative());
        advancedSearchForm.addFieldsInThePenultimePosition(titleAlternative);
    }

    public SiemacMetadataStatisticalResourceWebCriteria getSiemacMetadataStatisticalResourceWebCriteria(SiemacMetadataStatisticalResourceWebCriteria siemacMetadataStatisticalResourceWebCriteria) {
        siemacMetadataStatisticalResourceWebCriteria = (SiemacMetadataStatisticalResourceWebCriteria) getLifeCycleResourceWebCriteria(siemacMetadataStatisticalResourceWebCriteria);
        siemacMetadataStatisticalResourceWebCriteria.setTitleAlternative(advancedSearchForm.getValueAsString(SiemacMetadataDS.TITLE_ALTERNATIVE));
        return siemacMetadataStatisticalResourceWebCriteria;
    }
}
