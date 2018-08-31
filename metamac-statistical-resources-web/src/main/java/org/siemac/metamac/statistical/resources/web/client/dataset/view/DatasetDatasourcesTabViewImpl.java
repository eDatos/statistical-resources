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
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.ImportDatasourceWithMappingWindow;
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

        private CustomToolStripButton             deleteDatasourceButton;
        private CustomToolStripButton             importZipDatasourcesButton;
        private CustomToolStripButton             importDatasourceButton;
        private CustomListGrid                    datasourcesList;

        private DeleteConfirmationWindow          deleteConfirmationWindow;
        private ImportDatasourcesWindow           importDatasourcesWindow;
        private ImportDatasourceWithMappingWindow importDatasourceWithMappingWindow;

        public DatasourcesListPanel() {
            // Toolstrip

            ToolStrip toolStrip = new ToolStrip();
            toolStrip.setWidth100();

            deleteDatasourceButton = createDeleteDatasourcesButton();
            toolStrip.addButton(deleteDatasourceButton);

            importDatasourceButton = createImportDatasourceButton();
            toolStrip.addButton(importDatasourceButton);

            importZipDatasourcesButton = createImportZipDatasourcesButton();
            toolStrip.addButton(importZipDatasourcesButton);

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
            if (importDatasourceWithMappingWindow != null) {
                importDatasourceWithMappingWindow.setUiHandlers(uiHandlers);
            }
        }

        public void setCodeListsInVariable(String dimensionId, GetCodelistsWithVariableResult result) {
            importDatasourceWithMappingWindow.setCodelistsForDimension(dimensionId, result.getCodelists(), result.getFirstResultOut(), result.getTotalResults());
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

        private CustomToolStripButton createImportZipDatasourcesButton() {
            CustomToolStripButton importDatasourcesButton = new CustomToolStripButton(getConstants().actionLoadDatasourcesZip(),
                    org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE.importResource().getURL());
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

        private CustomToolStripButton createImportDatasourceButton() {
            CustomToolStripButton importDatasourcesButton = new CustomToolStripButton(getConstants().actionLoadDatasource(),
                    org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE.importResource().getURL());
            importDatasourcesButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if (importDatasourceWithMappingWindow != null) {
                        importDatasourceWithMappingWindow.show();
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

        public void setDatasetUrn(String datasetVersionUrn) {
            getUiHandlers().retrieveDimensionVariablesForDataset(datasetVersionUrn);
            createImportDatasourcesWindow(datasetVersionUrn);
        }

        public void setDimensionVariablesForDataset(String datasetVersionUrn, Map<String, String> dimensionsMapping) {
            createImportDatasourceWithMappingWindow(datasetVersionUrn, dimensionsMapping);
        }

        public void setDatasources(List<DatasourceDto> datasources) {
            DatasourceRecord[] records = new DatasourceRecord[datasources.size()];
            int index = 0;
            for (DatasourceDto datasourceDto : datasources) {
                records[index++] = StatisticalResourcesRecordUtils.getDatasourceRecord(datasourceDto);
            }
            datasourcesList.setData(records);
            datasourcesList.setAutoFitMaxRecords(datasources.size());
        }

        private void createImportDatasourcesWindow(String datasetVersionUrn) {
            importDatasourcesWindow = new ImportDatasourcesWindow();
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
            importDatasourcesWindow.setDatasetVersion(datasetVersionUrn);
        }

        private void createImportDatasourceWithMappingWindow(String datasetVersionUrn, Map<String, String> dimensionsMapping) {
            importDatasourceWithMappingWindow = new ImportDatasourceWithMappingWindow(dimensionsMapping) {

                @Override
                protected void uploadSuccess(String message) {
                    getUiHandlers().datasourcesImportationSucceed(message);
                }

                @Override
                protected void uploadFailed(String error) {
                    getUiHandlers().datasourcesImportationFailed(error);
                }
            };
            importDatasourceWithMappingWindow.setDatasetVersion(datasetVersionUrn);
            importDatasourceWithMappingWindow.setUiHandlers(getUiHandlers());
        }

        private void updateButtonsVisibility() {
            importZipDatasourcesButton.setVisible(DatasetClientSecurityUtils.canImportDatasourcesInDatasetVersion(datasetVersionDto));
            importDatasourceButton.setVisible(DatasetClientSecurityUtils.canImportDatasourcesInDatasetVersion(datasetVersionDto));
            updateListGridButtonsVisibilityBasedOnSelection(datasourcesList.getSelectedRecords());
        }
    }
}
