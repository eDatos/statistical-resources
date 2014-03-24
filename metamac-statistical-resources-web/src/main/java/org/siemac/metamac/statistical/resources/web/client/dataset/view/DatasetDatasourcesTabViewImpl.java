package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasourceDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasourceRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter.DatasetDatasourcesTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.ImportDatasourcesWindow;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetCodelistsWithVariableResult;
import org.siemac.metamac.web.common.client.listener.UploadListener;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class DatasetDatasourcesTabViewImpl extends ViewWithUiHandlers<DatasetDatasourcesTabUiHandlers> implements DatasetDatasourcesTabView {

    private VLayout              panel;

    private DatasourcesListPanel datasourcesListPanel;

    private DatasetVersionDto    datasetVersionDto;

    public DatasetDatasourcesTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(5);
        panel.setHeight100();

        datasourcesListPanel = new DatasourcesListPanel();
        datasourcesListPanel.setWidth("99%");

        panel.addMember(datasourcesListPanel);
    }

    @Override
    public void setDatasetVersion(DatasetVersionDto datasetVersionDto) {
        this.datasetVersionDto = datasetVersionDto;
        datasourcesListPanel.updateButtonsVisibility();
    }

    @Override
    public void setDatasources(String datasetUrn, List<DatasourceDto> datasources) {
        setDatasetUrn(datasetUrn);
        datasourcesListPanel.setDatasources(datasources);
    }

    private void setDatasetUrn(String urn) {
        datasourcesListPanel.setDatasetUrn(urn);
    }

    @Override
    public void setCodelistsInVariable(String dimensionId, GetCodelistsWithVariableResult result) {
        datasourcesListPanel.setCodeListsInVariable(dimensionId, result);
    }

    @Override
    public void setDimensionVariablesForDataset(String datasetVersionUrn, Map<String, String> mapping) {
        datasourcesListPanel.setDimensionVariablesForDataset(datasetVersionUrn, mapping);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(DatasetDatasourcesTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        datasourcesListPanel.setUiHandlers(uiHandlers);
    }

    public List<String> getUrnsFromSelected() {
        List<String> codes = new ArrayList<String>();
        for (ListGridRecord record : datasourcesListPanel.datasourcesList.getSelectedRecords()) {
            DatasourceRecord datasourceRecord = (DatasourceRecord) record;
            codes.add(datasourceRecord.getUrn());
        }
        return codes;
    }

    private class DatasourcesListPanel extends VLayout {

        private CustomToolStripButton    deleteDatasourceButton;
        private CustomToolStripButton    importDatasourcesButton;
        private CustomListGrid           datasourcesList;

        private DeleteConfirmationWindow deleteConfirmationWindow;
        private ImportDatasourcesWindow  importDatasourcesWindow;

        public DatasourcesListPanel() {
            // Toolstrip

            ToolStrip toolStrip = new ToolStrip();
            toolStrip.setWidth100();

            deleteDatasourceButton = createDeleteDatasourcesButton();
            toolStrip.addButton(deleteDatasourceButton);

            importDatasourcesButton = createImportDatasourcesButton();
            toolStrip.addButton(importDatasourcesButton);

            // List

            datasourcesList = new CustomListGrid();
            datasourcesList.setAutoFitMaxRecords(StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS);
            datasourcesList.setAutoFitData(Autofit.VERTICAL);
            datasourcesList.setDataSource(new DatasourceDS());
            datasourcesList.setUseAllDataSourceFields(false);

            ListGridField fieldCode = new ListGridField(DatasourceDS.CODE, getConstants().identifiableStatisticalResourceCode());
            fieldCode.setAlign(Alignment.LEFT);
            datasourcesList.setFields(fieldCode);

            datasourcesList.addSelectionChangedHandler(new SelectionChangedHandler() {

                @Override
                public void onSelectionChanged(SelectionEvent event) {
                    updateListGridButtonsVisibilityBasedOnSelection(event.getSelection());
                }

            });

            // Delete confirmation window

            deleteConfirmationWindow = new DeleteConfirmationWindow(getConstants().actionConfirmDeleteTitle(), getConstants().datasourceDeleteConfirmation());
            deleteConfirmationWindow.setVisible(false);

            // Import datasources window

            addMember(toolStrip);
            addMember(datasourcesList);
            bindEvents();
        }

        private void updateListGridButtonsVisibilityBasedOnSelection(ListGridRecord[] selection) {
            boolean someSelected = selection.length > 0;
            deleteDatasourceButton.setVisible(DatasetClientSecurityUtils.canDeleteDatasources(datasetVersionDto) && someSelected);
        }

        private void setUiHandlers(DatasetDatasourcesTabUiHandlers uiHandlers) {
            if (importDatasourcesWindow != null) {
                importDatasourcesWindow.setUiHandlers(uiHandlers);
            }
        }

        public void setCodeListsInVariable(String dimensionId, GetCodelistsWithVariableResult result) {
            importDatasourcesWindow.setCodelistsForDimension(dimensionId, result.getCodelists(), result.getFirstResultOut(), result.getTotalResults());
        }

        private CustomToolStripButton createDeleteDatasourcesButton() {
            CustomToolStripButton deleteDatasourceButton = new CustomToolStripButton(getConstants().actionDelete(), RESOURCE.deleteListGrid().getURL());
            deleteDatasourceButton.setVisible(false);
            deleteDatasourceButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    deleteConfirmationWindow.show();
                }
            });
            return deleteDatasourceButton;
        }

        private CustomToolStripButton createImportDatasourcesButton() {
            CustomToolStripButton importDatasourcesButton = new CustomToolStripButton(getConstants().actionLoadDatasources(), org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE
                    .importResource().getURL());
            importDatasourcesButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if (importDatasourcesWindow != null) {
                        importDatasourcesWindow.show();
                    }
                }
            });
            return importDatasourcesButton;
        }

        private void bindEvents() {
            datasourcesList.addSelectionChangedHandler(new SelectionChangedHandler() {

                @Override
                public void onSelectionChanged(SelectionEvent event) {
                    if (datasourcesList.getSelectedRecords().length > 0) {
                        showListGridDeleteButton();
                    } else {
                        deleteDatasourceButton.hide();
                    }
                }
            });

            deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    getUiHandlers().deleteDatasources(getUrnsFromSelected());
                }
            });
        }

        private void showListGridDeleteButton() {
            if (DatasetClientSecurityUtils.canDeleteDatasources(datasetVersionDto)) {
                deleteDatasourceButton.show();
            }
        }

        public void setDatasetUrn(String urn) {
            getUiHandlers().retrieveDimensionVariablesForDataset(urn);

        }

        public void setDimensionVariablesForDataset(String datasetUrn, Map<String, String> dimensionsMapping) {
            importDatasourcesWindow = new ImportDatasourcesWindow(dimensionsMapping);
            importDatasourcesWindow.setUploadListener(new UploadListener() {

                @Override
                public void uploadFailed(String errorMessage) {
                    getUiHandlers().datasourcesImportationFailed(errorMessage);
                }

                @Override
                public void uploadComplete(String fileName) {
                    getUiHandlers().datasourcesImportationSucceed(fileName);
                }
            });
            importDatasourcesWindow.setDatasetVersion(datasetUrn);
            importDatasourcesWindow.setUiHandlers(getUiHandlers());
        }

        public void setDatasources(List<DatasourceDto> datasources) {
            DatasourceRecord[] records = new DatasourceRecord[datasources.size()];
            int index = 0;
            for (DatasourceDto datasourceDto : datasources) {
                records[index++] = StatisticalResourcesRecordUtils.getDatasourceRecord(datasourceDto);
            }
            datasourcesList.setData(records);
        }

        private void updateButtonsVisibility() {
            importDatasourcesButton.setVisible(DatasetClientSecurityUtils.canImportDatasourcesInDatasetVersion(datasetVersionDto));
            updateListGridButtonsVisibilityBasedOnSelection(datasourcesList.getSelectedRecords());
        }
    }

}
