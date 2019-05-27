package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.enume.dataset.domain.DataSourceTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasourceDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasourceRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter.DatasetDatasourcesTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DatasourceMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.ImportDatasourceWithMappingWindow;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.ImportDatasourcesWindow;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.ImportDbDatasourceWindow;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetCodelistsWithVariableResult;
import org.siemac.metamac.web.common.client.listener.UploadListener;
import org.siemac.metamac.web.common.client.utils.DateUtils;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

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

    private VLayout                  panel;

    private DatasourceMainFormLayout datasourceMainFormLayout;

    private DatasourcesListPanel datasourcesListPanel;

    private DatasetVersionDto    datasetVersionDto;

    public DatasetDatasourcesTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(5);
        panel.setHeight100();
        panel.setMembersMargin(10);

        datasourceMainFormLayout = new DatasourceMainFormLayout();

        datasourcesListPanel = new DatasourcesListPanel();
        datasourcesListPanel.setWidth("99%");

        panel.addMember(datasourceMainFormLayout);
        panel.addMember(datasourcesListPanel);
    }

    @Override
    public void setDatasetVersion(DatasetVersionDto datasetVersionDto) {
        this.datasetVersionDto = datasetVersionDto;
        datasourceMainFormLayout.setDatasetVersionDto(datasetVersionDto);
        datasourcesListPanel.updateDataSourceType(); // TODO METAMAC-2866 check this!
        datasourcesListPanel.updateDateLastTimeDataImport();
        datasourcesListPanel.updateButtonsVisibility();
    }

    @Override
    public void setDatasources(String datasetUrn, List<DatasourceDto> datasources) {
        setDatasetUrn(datasetUrn);
        datasourcesListPanel.setDatasources(datasources);
        datasourcesListPanel.updateImportDbDatasourcesButtonVisibility(datasources);
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
        datasourceMainFormLayout.setUiHandlers(uiHandlers);
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
        private CustomToolStripButton             importDbDatasourcesButton;
        private CustomToolStripButton             importDatasourceButton;
        private CustomListGrid                    datasourcesList;

        private DeleteConfirmationWindow          deleteConfirmationWindow;
        private ImportDatasourcesWindow           importDatasourcesWindow;
        private ImportDbDatasourceWindow          importDbDatasourceWindow;
        private ImportDatasourceWithMappingWindow importDatasourceWithMappingWindow;
        private ViewTextItem                      dataSourceTypeItem;
        private ViewTextItem                      dateLastTimeDataImportItem;

        public DatasourcesListPanel() {

            dataSourceTypeItem = new ViewTextItem(DatasetDS.DATA_SOURCE_TYPE, getConstants().datasetVersionDataSourceType());
            dataSourceTypeItem.setValueMap(CommonUtils.getDataSourceTypeHashMap());
            dataSourceTypeItem.setAlign(Alignment.LEFT);
            dataSourceTypeItem.setCanEdit(Boolean.FALSE);

            dateLastTimeDataImportItem = new ViewTextItem(DatasetDS.DATE_LAST_TIME_DATA_IMPORT, getConstants().dateLastTimeDataImport());
            dateLastTimeDataImportItem.setAlign(Alignment.LEFT);
            dateLastTimeDataImportItem.setCanEdit(Boolean.FALSE);

            CustomDynamicForm form = new CustomDynamicForm();
            form.setIsGroup(Boolean.FALSE);
            form.setNumCols(4);
            form.setColWidths("8%", "42%", "8%", "42%");
            form.setFields(dataSourceTypeItem, dateLastTimeDataImportItem);

            // Toolstrip

            ToolStrip toolStrip = new ToolStrip();
            toolStrip.setWidth100();

            deleteDatasourceButton = createDeleteDatasourcesButton();
            toolStrip.addButton(deleteDatasourceButton);

            importDatasourceButton = createImportDatasourceButton();
            toolStrip.addButton(importDatasourceButton);

            importZipDatasourcesButton = createImportZipDatasourcesButton();
            toolStrip.addButton(importZipDatasourcesButton);

            importDbDatasourcesButton = createImportDbDatasourcesButton();
            toolStrip.addButton(importDbDatasourcesButton);

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

            // Import datasource from DB window

            importDbDatasourceWindow = new ImportDbDatasourceWindow();
            importDbDatasourceWindow.setVisible(Boolean.FALSE);

            // Import datasources window

            addMember(form);
            addMember(toolStrip);
            addMember(datasourcesList);
            bindEvents();

        }

        public void updateDateLastTimeDataImport() {
            dateLastTimeDataImportItem.setValue(DateUtils.getFormattedDateTime(datasetVersionDto.getDateLastTimeDataImport()));
        }

        public void updateDataSourceType() {
            dataSourceTypeItem.setValue(datasetVersionDto.getDataSourceType());
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
            importDatasourcesButton.setVisible(Boolean.FALSE);
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

        private CustomToolStripButton createImportDbDatasourcesButton() {
            CustomToolStripButton importDatasourcesButton = new CustomToolStripButton(getConstants().actionLoadDatasourceDatabase(),
                    org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE.databaseImport().getURL());
            importDatasourcesButton.setVisible(Boolean.FALSE);
            importDatasourcesButton.addClickHandler(new ClickHandler() { // NOSONAR

                @Override
                public void onClick(ClickEvent event) {
                    if (importDbDatasourceWindow != null) {
                        importDbDatasourceWindow.show();
                    }
                }
            });
            return importDatasourcesButton;
        }

        private CustomToolStripButton createImportDatasourceButton() {
            CustomToolStripButton importDatasourcesButton = new CustomToolStripButton(getConstants().actionLoadDatasource(),
                    org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE.importResource().getURL());
            importDatasourcesButton.setVisible(Boolean.FALSE);
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

            importDbDatasourceWindow.getSaveButtonHandlers().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() { // NOSONAR

                @Override
                public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                    if (importDbDatasourceWindow.validate()) {
                        getUiHandlers().dbDatasourceImportation(datasetVersionDto.getUrn(), importDbDatasourceWindow.getTableNameItemValue());
                        importDbDatasourceWindow.hide();
                    }

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
            importZipDatasourcesButton.setVisible(getButtonsVisibility(datasetVersionDto, DataSourceTypeEnum.FILE));
            importDatasourceButton.setVisible(getButtonsVisibility(datasetVersionDto, DataSourceTypeEnum.FILE));
            importDbDatasourcesButton.setVisible(getButtonsVisibility(datasetVersionDto, DataSourceTypeEnum.DATABASE)); // TODO METAMAC-2866 check security for import datasources from DB

            updateListGridButtonsVisibilityBasedOnSelection(datasourcesList.getSelectedRecords());
        }

        private boolean getButtonsVisibility(DatasetVersionDto datasetVersionDto, DataSourceTypeEnum dataSourceTypeEnum) {
            return dataSourceTypeEnum.equals(datasetVersionDto.getDataSourceType()) && DatasetClientSecurityUtils.canImportDatasourcesInDatasetVersion(datasetVersionDto);
        }

        private void updateImportDbDatasourcesButtonVisibility(List<DatasourceDto> datasources) {
            if (DataSourceTypeEnum.DATABASE.equals(datasetVersionDto.getDataSourceType()) && datasources != null && !datasources.isEmpty()) {
                importDbDatasourcesButton.setVisible(Boolean.FALSE);
            }
        }
    }
}
