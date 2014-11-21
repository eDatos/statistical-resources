package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleSrmItemSchemeWindow;
import org.siemac.metamac.statistical.resources.web.shared.utils.StatisticalResourcesSharedTokens;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.UploadResourceWithPreviewWindow;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;

import com.google.gwt.core.client.Scheduler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public abstract class ImportDatasourceWithMappingWindow extends UploadResourceWithPreviewWindow {

    private Map<String, SearchSingleSrmItemSchemeWindow> dimensionWindowsMap             = new HashMap<String, SearchSingleSrmItemSchemeWindow>();
    private Map<String, String>                          dimensionsMapping;
    private DatasetDatasourcesTabUiHandlers              uiHandlers;

    private static final String                          DIMENSION_SELECTION_NAME_PREFIX = "select-dim-";

    public ImportDatasourceWithMappingWindow(Map<String, String> dimensionsMapping) {
        super(getConstants().actionLoadDatasource());
        this.dimensionsMapping = dimensionsMapping;
        addDimensionFieldsInMainForm();
        addDimensionFieldsInExtraForm();
        setAutoSize(true);
    }

    @Override
    protected UploadForm buildMainUploadForm() {
        return new UploadDatasourceForm();
    }

    @Override
    protected CustomDynamicForm buildExtraForm() {
        CustomDynamicForm form = new CustomDynamicForm();
        form.setWidth(350);
        form.setVisible(false);
        return form;
    }

    private void addDimensionFieldsInMainForm() {
        List<FormItem> itemsToAdd = new ArrayList<FormItem>();
        if (dimensionsMapping != null && !dimensionsMapping.isEmpty()) {
            for (String dimensionId : dimensionsMapping.keySet()) {
                itemsToAdd.add(new HiddenItem(getDimensionHiddenFieldName(dimensionId)));
            }
        }
        mainForm.addFields(itemsToAdd.toArray(new HiddenItem[itemsToAdd.size()]));
    }

    private void addDimensionFieldsInExtraForm() {

        List<FormItem> items = new ArrayList<FormItem>();

        if (!dimensionsMapping.isEmpty()) {
            StaticTextItem mappingLabel = new StaticTextItem("header");
            mappingLabel.setShowTitle(false);
            mappingLabel.setValue(getMessages().datasourceImportationDimensionMapping());
            mappingLabel.setColSpan(2);

            items.add(mappingLabel);

            for (String dimensionId : dimensionsMapping.keySet()) {
                items.add(createItemForDimension(extraForm, dimensionId, dimensionsMapping.get(dimensionId)));
            }
        }

        CustomButtonItem uploadButton = new CustomButtonItem("button-import", MetamacWebCommon.getConstants().accept());
        uploadButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                submitIfValid();
            }
        });

        items.add(uploadButton);

        extraForm.setFields(items.toArray(new FormItem[items.size()]));
    }

    @Override
    protected void copyHiddenValuesToMainForm(UploadForm mainForm, DynamicForm extraForm) {
        for (String dimensionId : dimensionWindowsMap.keySet()) {
            SearchExternalItemLinkItem item = (SearchExternalItemLinkItem) extraForm.getItem(getDimensionSelectionFieldName(dimensionId));
            if (item.getExternalItemDto() != null) {
                mainForm.setValue(getDimensionHiddenFieldName(dimensionId), item.getExternalItemDto().getUrn());
            } else {
                mainForm.setValue(getDimensionHiddenFieldName(dimensionId), StringUtils.EMPTY);
            }
        }
    }

    @Override
    protected void onPreviewComplete(String response) {
        extraForm.clearValues();
        clearExtraFormValues();
        // TODO METAMAC-1979 set values in extraForm
        extraForm.setVisible(true);
    }

    @Override
    public String getRelativeURL(String url) {
        return StatisticalResourcesWeb.getRelativeURL(url);
    }

    @Override
    protected void onPreviewFailed(String errorMessage) {
        uploadFailed(errorMessage);
    }

    @Override
    protected void onSubmitComplete(String response) {
        uploadSuccess(response);
    }

    @Override
    protected void onSubmitFailed(String errorMessage) {
        uploadFailed(errorMessage);
    }

    public void setDatasetVersion(String datasetVersionUrn) {
        ((HiddenItem) mainForm.getItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DATASET_VERSION_URN)).setDefaultValue(datasetVersionUrn);
    }

    protected abstract void uploadFailed(String error);
    protected abstract void uploadSuccess(String message);

    public void setUiHandlers(DatasetDatasourcesTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    private DatasetDatasourcesTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    private SearchExternalItemLinkItem createItemForDimension(final CustomDynamicForm form, final String dimensionId, final String variableUrn) {

        final String name = getDimensionSelectionFieldName(dimensionId);
        String title = dimensionId;

        final SearchExternalItemLinkItem item = new SearchExternalItemLinkItem(name, title) {

            @Override
            public void onSearch() {
                final SearchSingleSrmItemSchemeWindow searchWindow = new SearchSingleSrmItemSchemeWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<SrmExternalResourceRestCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, SrmExternalResourceRestCriteria criteria) {
                                getUiHandlers().retrieveAlternativeCodelistsForVariable(dimensionId, variableUrn, firstResult, maxResults, criteria);
                            }
                        });

                searchWindow.retrieveItems();
                searchWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        ExternalItemDto selectedResource = searchWindow.getSelectedResource();
                        searchWindow.markForDestroy();
                        form.setValue(name, RecordUtils.getExternalItemRecord(selectedResource));
                        form.getItem(name).validate();
                    }
                });
                dimensionWindowsMap.put(dimensionId, searchWindow);
            }
        };
        return item;
    }

    private String getDimensionHiddenFieldName(String dimensionId) {
        return StatisticalResourcesSharedTokens.UPLOAD_PARAM_DIM_PREFIX + dimensionId;
    }

    private String getDimensionSelectionFieldName(String dimensionId) {
        return DIMENSION_SELECTION_NAME_PREFIX + dimensionId;
    }

    @Override
    public void show() {
        clearExtraFormValues();
        extraForm.hide();
        super.show();
    }

    public void setCodelistsForDimension(String dimensionId, List<ExternalItemDto> codelists, int firstResult, int totalResults) {
        SearchSingleSrmItemSchemeWindow window = dimensionWindowsMap.get(dimensionId);
        if (window != null) {
            window.setResources(codelists);
            window.refreshSourcePaginationInfo(firstResult, codelists.size(), totalResults);
        }
    }

    private void clearExtraFormValues() {
        for (FormItem formItem : extraForm.getFields()) {
            if (StringUtils.startsWith(formItem.getName(), DIMENSION_SELECTION_NAME_PREFIX)) {
                formItem.clearValue();
            }
        }
    }

    private class UploadDatasourceForm extends UploadForm {

        private UploadItem uploadItem;

        public UploadDatasourceForm() {
            super();

            uploadItem = new UploadItem("file-name");
            uploadItem.setTitle(getConstants().datasetDatasource());
            uploadItem.setWidth(400);
            uploadItem.setRequired(true);
            uploadItem.setTitleStyle("requiredFormLabel");

            uploadItem.addChangeHandler(new com.smartgwt.client.widgets.form.fields.events.ChangeHandler() {

                @Override
                public void onChange(com.smartgwt.client.widgets.form.fields.events.ChangeEvent event) {
                    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

                        @Override
                        public void execute() {
                            submitPreviewIfValid();
                        }
                    });
                }
            });

            HiddenItem datasetVersionUrnItem = new HiddenItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DATASET_VERSION_URN);

            setFields(uploadItem, datasetVersionUrnItem);
        }

        @Override
        public UploadItem getUploadItem() {
            return uploadItem;
        }
    }
}
