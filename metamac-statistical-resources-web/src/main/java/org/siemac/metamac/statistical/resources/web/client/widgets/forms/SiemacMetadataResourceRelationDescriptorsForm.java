package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setRelatedResourceValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setRelatedResourcesValue;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceLinkItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceListItem;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;

public class SiemacMetadataResourceRelationDescriptorsForm extends NavigationEnabledDynamicForm {

    private BaseUiHandlers uiHandlers;

    public SiemacMetadataResourceRelationDescriptorsForm() {
        super(getConstants().formResourceRelationDescriptors());

        RelatedResourceLinkItem replaces = new RelatedResourceLinkItem(StatisticalResourceDS.REPLACES, getConstants().siemacMetadataStatisticalResourceReplaces(),
                getCustomLinkItemNavigationClickHandler());
        RelatedResourceLinkItem isReplacedBy = new RelatedResourceLinkItem(StatisticalResourceDS.IS_REPLACED_BY, getConstants().siemacMetadataStatisticalResourceIsReplacedBy(),
                getCustomLinkItemNavigationClickHandler());
        RelatedResourceListItem requires = new RelatedResourceListItem(StatisticalResourceDS.REQUIRES, getConstants().siemacMetadataStatisticalResourceRequires(), false, getRecordNavigationHandler());
        RelatedResourceListItem isRequiredBy = new RelatedResourceListItem(StatisticalResourceDS.IS_REQUIRED_BY, getConstants().siemacMetadataStatisticalResourceIsRequiredBy(), false,
                getRecordNavigationHandler());
        RelatedResourceListItem hasPart = new RelatedResourceListItem(StatisticalResourceDS.HAS_PART, getConstants().siemacMetadataStatisticalResourceHasPart(), false, getRecordNavigationHandler());
        RelatedResourceListItem isPartOf = new RelatedResourceListItem(StatisticalResourceDS.IS_PART_OF, getConstants().siemacMetadataStatisticalResourceIsPartOf(), false,
                getRecordNavigationHandler());

        setFields(replaces, isReplacedBy, requires, isRequiredBy, hasPart, isPartOf);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        setRelatedResourceValue(getItem(StatisticalResourceDS.REPLACES), dto.getReplaces());
        setRelatedResourceValue(getItem(StatisticalResourceDS.IS_REPLACED_BY), dto.getIsReplacedBy());

        setRelatedResourcesValue(getItem(StatisticalResourceDS.REQUIRES), dto.getRequires());
        setRelatedResourcesValue(getItem(StatisticalResourceDS.IS_REQUIRED_BY), dto.getIsRequiredBy());

        setRelatedResourcesValue(getItem(StatisticalResourceDS.HAS_PART), dto.getHasPart());
        setRelatedResourcesValue(getItem(StatisticalResourceDS.IS_PART_OF), dto.getIsPartOf());
    }

    public void setUiHandlers(BaseUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
