package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleSrmItemSchemeWindow;
import org.siemac.metamac.statistical.resources.web.shared.utils.StatisticalResourcesSharedTokens;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.ImportResourceWindow;
import org.siemac.metamac.web.common.client.widgets.InformationLabel;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class ImportDatasourcesWindow extends ImportResourceWindow {

    private UploadDatasourceWithDimensionsForm           uploadDatasourceWithDimensionsForm;

    private Map<String, SearchSingleSrmItemSchemeWindow> dimensionWindowsMap             = new HashMap<String, SearchSingleSrmItemSchemeWindow>();
    private DatasetDatasourcesTabUiHandlers              uiHandlers;

    private static final String                          DIMENSION_SELECTION_NAME_PREFIX = "select-dim-";

    public ImportDatasourcesWindow(Map<String, String> dimensionsMapping) {
        super(getConstants().actionLoadDatasources());

        InformationLabel informationLabel = new InformationLabel(getMessages().datasourceImportationInfoMessage());
        informationLabel.setWidth(getWidth());
        informationLabel.setMargin(5);
        body.addMember(informationLabel);

        UploadDatasourceForm uploadDatasourceForm = new UploadDatasourceForm(dimensionsMapping);
        setForm(uploadDatasourceForm);

        uploadDatasourceWithDimensionsForm = new UploadDatasourceWithDimensionsForm(dimensionsMapping);
        body.addMember(uploadDatasourceWithDimensionsForm);
    }

    public ImportDatasourcesWindow() {
        super(getConstants().actionLoadDatasources());

        InformationLabel informationLabel = new InformationLabel(getMessages().datasourceImportationInfoMessage());
        informationLabel.setWidth(getWidth());
        informationLabel.setMargin(5);
        body.addMember(informationLabel);

        UploadDatasourceForm form = new UploadDatasourceForm(null);
        setForm(form);
    }

    public void setDatasetVersion(String datasetVersionUrn) {
        ((HiddenItem) form.getItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DATASET_VERSION_URN)).setDefaultValue(datasetVersionUrn);
        setStatisticalOperation(StatisticalResourcesDefaults.getSelectedStatisticalOperation().getCode());
    }

    public void setStatisticalOperation(String statisticalOperationUrn) {
        ((HiddenItem) form.getItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_OPERATION_CODE)).setDefaultValue(statisticalOperationUrn);
    }

    public void setCodelistsForDimension(String dimensionId, List<ExternalItemDto> codelists, int firstResult, int totalResults) {
        SearchSingleSrmItemSchemeWindow window = dimensionWindowsMap.get(dimensionId);
        if (window != null) {
            window.setResources(codelists);
            window.refreshSourcePaginationInfo(firstResult, codelists.size(), totalResults);
        }
    }

    private class UploadDatasourceForm extends UploadForm {

        public UploadDatasourceForm(Map<String, String> dimensionsMapping) {
            super(getConstants().datasources());

            HiddenItem datasetVersionUrnItem = new HiddenItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DATASET_VERSION_URN);
            HiddenItem operationUrnItem = new HiddenItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_OPERATION_CODE);

            List<FormItem> itemsToAdd = new ArrayList<FormItem>();
            itemsToAdd.add(datasetVersionUrnItem);
            itemsToAdd.add(operationUrnItem);

            if (dimensionsMapping != null && !dimensionsMapping.isEmpty()) {
                for (String dimensionId : dimensionsMapping.keySet()) {
                    itemsToAdd.add(new HiddenItem(getDimensionHiddenFieldName(dimensionId)));
                }
                setFields(uploadItem);
                addFields(itemsToAdd.toArray(new FormItem[itemsToAdd.size()]));
            } else {
                addFieldsInThePenultimePosition(itemsToAdd.toArray(new FormItem[itemsToAdd.size()]));
            }
        }
    }

    private class UploadDatasourceWithDimensionsForm extends CustomDynamicForm {

        public UploadDatasourceWithDimensionsForm(Map<String, String> dimensionsMapping) {
            setAutoHeight();
            setWidth100();
            setWidth(ImportDatasourcesWindow.this.getWidth());
            setMargin(8);
            setNumCols(2);
            setCellPadding(4);
            setWrapItemTitles(false);

            if (!dimensionsMapping.isEmpty()) {
                StaticTextItem mappingLabel = new StaticTextItem("header");
                mappingLabel.setShowTitle(false);
                mappingLabel.setValue(getMessages().datasourceImportationDimensionMapping());
                mappingLabel.setColSpan(2);

                addFields(mappingLabel);

                for (String dimensionId : dimensionsMapping.keySet()) {
                    addItemsForDimension(dimensionId, dimensionsMapping.get(dimensionId));
                }
            }

            CustomButtonItem uploadButton = new CustomButtonItem("button-import", MetamacWebCommon.getConstants().accept());
            uploadButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                @Override
                public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                    String displayValue = ImportDatasourcesWindow.this.form.getUploadItem().getDisplayValue();
                    if (ImportDatasourcesWindow.this.form.validate() && !StringUtils.isBlank(displayValue)) {
                        for (String dimensionId : dimensionWindowsMap.keySet()) {
                            SearchExternalItemLinkItem item = (SearchExternalItemLinkItem) UploadDatasourceWithDimensionsForm.this.getItem(getDimensionSelectionFieldName(dimensionId));
                            if (item.getExternalItemDto() != null) {
                                ImportDatasourcesWindow.this.form.setValue(getDimensionHiddenFieldName(dimensionId), item.getExternalItemDto().getUrn());
                            } else {
                                ImportDatasourcesWindow.this.form.setValue(getDimensionHiddenFieldName(dimensionId), StringUtils.EMPTY);
                            }
                        }

                        ImportDatasourcesWindow.this.form.submitForm();
                        ImportDatasourcesWindow.this.hide();
                        getUiHandlers().showWaitPopup();
                    }
                }
            });
            addFields(uploadButton);
        }

        private void addItemsForDimension(String dimensionId, String variableUrn) {
            SearchExternalItemLinkItem item = createItemForDimension(dimensionId, variableUrn);
            addFields(item);
        }

        private SearchExternalItemLinkItem createItemForDimension(final String dimensionId, final String variableUrn) {

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
                            UploadDatasourceWithDimensionsForm.this.setValue(name, RecordUtils.getExternalItemRecord(selectedResource));
                            UploadDatasourceWithDimensionsForm.this.getItem(name).validate();
                        }
                    });
                    dimensionWindowsMap.put(dimensionId, searchWindow);
                }
            };
            return item;
        }
    }

    private DatasetDatasourcesTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    private String getDimensionHiddenFieldName(String dimensionId) {
        return StatisticalResourcesSharedTokens.UPLOAD_PARAM_DIM_PREFIX + dimensionId;
    }

    private String getDimensionSelectionFieldName(String dimensionId) {
        return DIMENSION_SELECTION_NAME_PREFIX + dimensionId;
    }

    public void setUiHandlers(DatasetDatasourcesTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public void show() {
        if (uploadDatasourceWithDimensionsForm != null) {
            for (FormItem formItem : uploadDatasourceWithDimensionsForm.getFields()) {
                if (StringUtils.startsWith(formItem.getName(), DIMENSION_SELECTION_NAME_PREFIX)) {
                    formItem.clearValue();
                }
            }
        }
        super.show();
    }

    @Override
    public String getRelativeURL(String url) {
        return StatisticalResourcesWeb.getRelativeURL(url);
    }

    @Override
    public void showWaitPopup() {
        // no need to show the waitPopup here
    }
}
