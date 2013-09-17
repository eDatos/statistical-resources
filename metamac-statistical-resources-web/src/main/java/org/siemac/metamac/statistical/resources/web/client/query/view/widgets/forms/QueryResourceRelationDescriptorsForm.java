package org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setRelatedResourcesValue;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NavigationEnabledDynamicForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceListItem;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;

public class QueryResourceRelationDescriptorsForm extends NavigationEnabledDynamicForm {

    private QueryUiHandlers uiHandlers;

    public QueryResourceRelationDescriptorsForm() {
        super(getConstants().formResourceRelationDescriptors());

        RelatedResourceListItem isPartOf = new RelatedResourceListItem(QueryDS.IS_PART_OF, getConstants().siemacMetadataStatisticalResourceIsPartOf(), false, getRecordNavigationHandler());

        addFields(isPartOf);
    }

    public void setQueryDto(QueryVersionDto dto) {
        setRelatedResourcesValue(getItem(QueryDS.IS_PART_OF), dto.getIsPartOf());
    }

    public void setUiHandlers(QueryUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
