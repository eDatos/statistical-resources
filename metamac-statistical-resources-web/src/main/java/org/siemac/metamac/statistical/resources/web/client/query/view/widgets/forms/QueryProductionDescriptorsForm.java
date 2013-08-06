package org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NavigationEnabledDynamicForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.CodeItemListItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceLinkItem;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.widgets.form.fields.FormItem;

public class QueryProductionDescriptorsForm extends NavigationEnabledDynamicForm {

    private BaseUiHandlers uiHandlers;

    public QueryProductionDescriptorsForm() {
        super(getConstants().formProductionDescriptors());

        ExternalItemLinkItem maintainer = new ExternalItemLinkItem(QueryDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer());

        RelatedResourceLinkItem datasetVersion = new RelatedResourceLinkItem(QueryDS.RELATED_DATASET_VERSION, getConstants().queryDatasetVersion(), getCustomLinkItemNavigationClickHandler());

        ViewTextItem status = new ViewTextItem(QueryDS.STATUS, getConstants().queryStatus());

        setFields(maintainer, datasetVersion, status);
    }

    public void setQueryDto(QueryVersionDto queryDto) {
        List<FormItem> fields = new ArrayList<FormItem>();

        ExternalItemLinkItem maintainer = new ExternalItemLinkItem(QueryDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer());
        fields.add(maintainer);

        RelatedResourceLinkItem datasetVersion = new RelatedResourceLinkItem(QueryDS.RELATED_DATASET_VERSION, getConstants().queryDatasetVersion(), getCustomLinkItemNavigationClickHandler());
        fields.add(datasetVersion);

        ViewTextItem status = new ViewTextItem(QueryDS.STATUS, getConstants().queryStatus());
        fields.add(status);

        if (queryDto.getSelection() != null) {
            for (String dimensionId : queryDto.getSelection().keySet()) {
                fields.add(createCodeListItemForDimension(dimensionId));
            }
        }
        setFields(fields.toArray(new FormItem[fields.size()]));

        if (queryDto.getSelection() != null) {
            for (String dimensionId : queryDto.getSelection().keySet()) {

                CodeItemListItem item = (CodeItemListItem) getItem(QueryDS.SELECTION + "_" + dimensionId);
                item.setCodeItems(queryDto.getSelection().get(dimensionId));
            }
        }

        StatisticalResourcesFormUtils.setRelatedResourceValue(getItem(QueryDS.RELATED_DATASET_VERSION), queryDto.getRelatedDatasetVersion());
        StatisticalResourcesFormUtils.setExternalItemValue(getItem(QueryDS.MAINTAINER), queryDto.getMaintainer());

        // Status
        setValue(QueryDS.STATUS, CommonUtils.getQueryStatusName(queryDto));
    }

    private CodeItemListItem createCodeListItemForDimension(final String dimensionId) {
        CodeItemListItem item = new CodeItemListItem(QueryDS.SELECTION + "_" + dimensionId, dimensionId, false);
        return item;
    }

    public void setUiHandlers(BaseUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
