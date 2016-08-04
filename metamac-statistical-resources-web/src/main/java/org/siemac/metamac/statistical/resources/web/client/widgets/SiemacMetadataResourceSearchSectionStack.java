package org.siemac.metamac.statistical.resources.web.client.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.statistical.resources.web.shared.criteria.SiemacMetadataStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;

import com.smartgwt.client.widgets.form.fields.TextItem;

public abstract class SiemacMetadataResourceSearchSectionStack extends LifeCycleResourceSearchSectionStack {

    public SiemacMetadataResourceSearchSectionStack() {
    }

    @Override
    protected void createAdvancedSearchForm() {
        super.createAdvancedSearchForm();
        TextItem titleAlternative = new TextItem(SiemacMetadataDS.TITLE_ALTERNATIVE, getConstants().siemacMetadataStatisticalResourceTitleAlternative());
        TextItem keywords = new TextItem(SiemacMetadataDS.KEYWORDS, getConstants().siemacMetadataStatisticalResourceKeywords());
        CustomDateItem newNessUntilDate = new CustomDateItem(SiemacMetadataDS.NEWNESS_UNTIL_DATE, getConstants().siemacMetadataStatisticalResourceNewnessUntilDate());
        advancedSearchForm.addFieldsInThePenultimePosition(titleAlternative, keywords, newNessUntilDate);
    }

    public SiemacMetadataStatisticalResourceWebCriteria getSiemacMetadataStatisticalResourceWebCriteria(SiemacMetadataStatisticalResourceWebCriteria siemacMetadataStatisticalResourceWebCriteria) {
        siemacMetadataStatisticalResourceWebCriteria = (SiemacMetadataStatisticalResourceWebCriteria) getLifeCycleResourceWebCriteria(siemacMetadataStatisticalResourceWebCriteria);
        siemacMetadataStatisticalResourceWebCriteria.setTitleAlternative(advancedSearchForm.getValueAsString(SiemacMetadataDS.TITLE_ALTERNATIVE));
        siemacMetadataStatisticalResourceWebCriteria.setKeywords(advancedSearchForm.getValueAsString(SiemacMetadataDS.KEYWORDS));
        siemacMetadataStatisticalResourceWebCriteria.setNewnessUtilDate(((CustomDateItem) advancedSearchForm.getItem(SiemacMetadataDS.NEWNESS_UNTIL_DATE)).getValueAsDate());
        return siemacMetadataStatisticalResourceWebCriteria;
    }
}
