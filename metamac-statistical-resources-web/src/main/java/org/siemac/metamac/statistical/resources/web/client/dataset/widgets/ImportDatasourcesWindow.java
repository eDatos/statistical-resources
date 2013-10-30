package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWebMessages;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleSrmItemSchemeWindow;
import org.siemac.metamac.statistical.resources.web.shared.utils.StatisticalResourcesSharedTokens;
import org.siemac.metamac.web.common.client.widgets.ImportResourceWindow;
import org.siemac.metamac.web.common.client.widgets.InformationLabel;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacVersionableWebCriteria;

import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class ImportDatasourcesWindow extends ImportResourceWindow {

    private Map<String, SearchSingleSrmItemSchemeWindow> dimensionWindowsMap             = new HashMap<String, SearchSingleSrmItemSchemeWindow>();
    private DatasetDatasourcesTabUiHandlers              uiHandlers;

    private static final String                          DIMENSION_SELECTION_NAME_PREFIX = "select-dim-";

    public ImportDatasourcesWindow(Map<String, String> dimensionsMapping) {
        super(getConstants().actionLoadDatasources());

        InformationLabel informationLabel = new InformationLabel(getMessages().datasourceImportationInfoMessage());
        informationLabel.setWidth(300);
        informationLabel.setMargin(10);
        body.addMember(informationLabel);

        UploadDatasourceForm form = new UploadDatasourceForm(dimensionsMapping);
        setForm(form);
    }

    public ImportDatasourcesWindow() {
        super(getConstants().actionLoadDatasources());

        InformationLabel informationLabel = new InformationLabel(getMessages().datasourceImportationInfoMessage());
        informationLabel.setWidth(300);
        informationLabel.setMargin(10);
        body.addMember(informationLabel);

        UploadDatasourceForm form = new UploadDatasourceForm();
        setForm(form);
    }

    @Override
    public void setForm(UploadForm form) {
        this.form = form;
        form.setTarget(TARGET);
        form.getUploadButton().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                String displayValue = ImportDatasourcesWindow.this.form.getUploadItem().getDisplayValue();
                if (ImportDatasourcesWindow.this.form.validate() && !StringUtils.isBlank(displayValue)) {
                    for (String dimensionId : dimensionWindowsMap.keySet()) {
                        SearchExternalItemLinkItem item = (SearchExternalItemLinkItem) ImportDatasourcesWindow.this.form.getItem(DIMENSION_SELECTION_NAME_PREFIX + dimensionId);
                        if (item.getExternalItemDto() != null) {
                            ImportDatasourcesWindow.this.form.setValue(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DIM_PREFIX + dimensionId, item.getExternalItemDto().getUrn());
                        } else {
                            ImportDatasourcesWindow.this.form.setValue(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DIM_PREFIX + dimensionId, "");
                        }
                    }

                    ImportDatasourcesWindow.this.form.submitForm();
                    hide();
                }
            }
        });
        body.addMember(form);
    }
    public void setDatasetVersion(String datasetVersionUrn) {
        ((HiddenItem) form.getItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DATASET_VERSION_URN)).setDefaultValue(datasetVersionUrn);
        setStatisticalOperation(StatisticalResourcesDefaults.getSelectedStatisticalOperation().getUrn());
    }

    public void setStatisticalOperation(String statisticalOperationUrn) {
        ((HiddenItem) form.getItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_OPERATION_URN)).setDefaultValue(statisticalOperationUrn);
    }

    public void setCodelistsForDimension(String dimensionId, List<ExternalItemDto> codelists, int firstResult, int totalResults) {
        SearchSingleSrmItemSchemeWindow window = dimensionWindowsMap.get(dimensionId);
        if (window != null) {
            window.setResources(codelists);
            window.refreshSourcePaginationInfo(firstResult, codelists.size(), totalResults);
        }
    }

    private class UploadDatasourceForm extends UploadForm {

        public UploadDatasourceForm() {
            super(getConstants().datasources());

            HiddenItem datasetVersionUrnItem = new HiddenItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DATASET_VERSION_URN);
            HiddenItem operationUrnItem = new HiddenItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_OPERATION_URN);

            addFieldsInThePenultimePosition(datasetVersionUrnItem, operationUrnItem);
        }

        public UploadDatasourceForm(Map<String, String> dimensionsMapping) {
            this();

            if (!dimensionsMapping.isEmpty()) {
                StaticTextItem mappingLabel = new StaticTextItem("header");
                mappingLabel.setShowTitle(false);
                mappingLabel.setValue(getMessages().datasourceImportationDimensionMapping());
                mappingLabel.setColSpan(2);

                addFieldsInThePenultimePosition(mappingLabel);

                for (String dimensionId : dimensionsMapping.keySet()) {
                    addItemsForDimension(dimensionId, dimensionsMapping.get(dimensionId));
                }
            }
        }

        private void addItemsForDimension(String dimensionId, String variableUrn) {
            SearchExternalItemLinkItem item = createItemForDimension(dimensionId, variableUrn);
            HiddenItem hiddenItem = new HiddenItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DIM_PREFIX + dimensionId);
            addFieldsInThePenultimePosition(item, hiddenItem);
        }

        private SearchExternalItemLinkItem createItemForDimension(final String dimensionId, final String variableUrn) {

            String name = DIMENSION_SELECTION_NAME_PREFIX + dimensionId;
            String title = dimensionId;

            final SearchExternalItemLinkItem item = new SearchExternalItemLinkItem(name, title);
            item.setExternalItem(null);
            item.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

                @Override
                public void onFormItemClick(FormItemIconClickEvent event) {
                    final SearchSingleSrmItemSchemeWindow searchWindow = new SearchSingleSrmItemSchemeWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                            new SearchPaginatedAction<MetamacVersionableWebCriteria>() {

                                @Override
                                public void retrieveResultSet(int firstResult, int maxResults, MetamacVersionableWebCriteria criteria) {
                                    getUiHandlers().retrieveAlternativeCodelistsForVariable(dimensionId, variableUrn, firstResult, maxResults, criteria);
                                }

                            });

                    getUiHandlers().retrieveAlternativeCodelistsForVariable(dimensionId, variableUrn, 0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, new MetamacVersionableWebCriteria());

                    searchWindow.setSaveAction(new ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                            ExternalItemDto selectedResource = searchWindow.getSelectedResource();
                            searchWindow.markForDestroy();
                            item.setExternalItem(selectedResource);
                            item.validate();
                        }
                    });
                    dimensionWindowsMap.put(dimensionId, searchWindow);
                }
            });
            return item;
        }

    }

    private DatasetDatasourcesTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(DatasetDatasourcesTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public String getRelativeURL(String url) {
        return StatisticalResourcesWeb.getRelativeURL(url);
    }
}
