package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasourceDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasourceRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter.DatasetDatasourcesTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetDatasourcesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasourceContentEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasourceResourceIdentifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasourceResourceIdentifiersForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.IdentifiableResourceIdentifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.IdentifiableResourceIdentifiersForm;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetPaginatedListResult;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class DatasetDatasourcesTabViewImpl extends ViewImpl implements DatasetDatasourcesTabView {

    private DatasetDatasourcesTabUiHandlers uiHandlers;
    private VLayout                         panel;

    private DatasourcesListPanel            datasourcesListPanel;
    private DatasourceFormPanel             datasourceFormPanel;

    private String                          datasetUrn;

    public DatasetDatasourcesTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(5);
        panel.setHeight100();

        datasourcesListPanel = new DatasourcesListPanel();
        datasourcesListPanel.setWidth("99%");
        datasourceFormPanel = new DatasourceFormPanel();
        datasourceFormPanel.setWidth("99%");

        panel.addMember(datasourcesListPanel);
        panel.addMember(datasourceFormPanel);
    }

    @Override
    public void setDatasourcesPaginatedList(String datasetUrn, GetDatasourcesByDatasetPaginatedListResult result) {
        this.datasetUrn = datasetUrn;
        datasourcesListPanel.setDatasourcesPaginatedList(result);
    }

    @Override
    public void setDatasource(DatasourceDto datasourceDto) {
        datasourceFormPanel.selectDatasource(datasourceDto);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(DatasetDatasourcesTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    private class DatasourcesListPanel extends VLayout {

        private ToolStripButton          newDatasourceButton;
        private ToolStripButton          deleteDatasourceButton;
        private PaginatedCheckListGrid   datasourcesList;

        private DeleteConfirmationWindow deleteConfirmationWindow;

        public DatasourcesListPanel() {
            // Toolstrip

            ToolStrip toolStrip = new ToolStrip();
            toolStrip.setWidth100();

            newDatasourceButton = new ToolStripButton(getConstants().actionNew(), RESOURCE.newListGrid().getURL());

            newDatasourceButton.setVisibility(DatasetClientSecurityUtils.canCreateDatasource() ? Visibility.VISIBLE : Visibility.HIDDEN);

            deleteDatasourceButton = new ToolStripButton(getConstants().actionDelete(), RESOURCE.deleteListGrid().getURL());
            deleteDatasourceButton.setVisibility(Visibility.HIDDEN);

            toolStrip.addButton(newDatasourceButton);
            toolStrip.addButton(deleteDatasourceButton);

            // List

            datasourcesList = new PaginatedCheckListGrid(DatasetDatasourcesTabPresenter.DATASOURCE_LIST_MAX_RESULTS, new PaginatedAction() {

                @Override
                public void retrieveResultSet(int firstResult, int maxResults) {
                    uiHandlers.retrieveDatasourcesByDataset(datasetUrn, firstResult, maxResults);
                }
            });

            datasourcesList.getListGrid().setAutoFitMaxRecords(DatasetDatasourcesTabPresenter.DATASOURCE_LIST_MAX_RESULTS);
            datasourcesList.getListGrid().setAutoFitData(Autofit.VERTICAL);
            datasourcesList.getListGrid().setDataSource(new DatasourceDS());
            datasourcesList.getListGrid().setUseAllDataSourceFields(false);

            ListGridField fieldCode = new ListGridField(DatasourceDS.CODE, getConstants().identifiableStatisticalResourceCode());
            fieldCode.setAlign(Alignment.LEFT);
            ListGridField fieldName = new ListGridField(DatasourceDS.TITLE, getConstants().nameableStatisticalResourceTitle());
            datasourcesList.getListGrid().setFields(fieldCode, fieldName);

            deleteConfirmationWindow = new DeleteConfirmationWindow(getConstants().actionConfirmDeleteTitle(), getConstants().datasourceDeleteConfirmation());
            deleteConfirmationWindow.setVisibility(Visibility.HIDDEN);
            deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    uiHandlers.deleteDatasources(getUrnsFromSelected());
                }
            });
            
            // Panel conf
            addMember(toolStrip);
            addMember(datasourcesList);
            bindEvents();
        }

        private void bindEvents() {
            datasourcesList.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

                @Override
                public void onSelectionChanged(SelectionEvent event) {
                    if (datasourcesList.getListGrid().getSelectedRecords().length > 0) {
                        showListGridDeleteButton();
                    } else {
                        deleteDatasourceButton.hide();
                    }
                }
            });

            datasourcesList.getListGrid().addRecordClickHandler(new RecordClickHandler() {

                @Override
                public void onRecordClick(RecordClickEvent event) {
                    if (event.getFieldNum() != 0) { // Clicking checkBox will be ignored
                        DatasourceRecord record = ((DatasourceRecord) event.getRecord());
                        datasourceFormPanel.selectDatasource((DatasourceDto) record.getAttributeAsObject(DatasourceDS.DTO));
                    }
                }
            });
            newDatasourceButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    datasourceFormPanel.createDatasource();
                }
            });
            deleteDatasourceButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    deleteConfirmationWindow.show();
                }
            });
        }

        private void showListGridDeleteButton() {
            if (DatasetClientSecurityUtils.canDeleteDatasource()) {
                deleteDatasourceButton.show();
            }
        }

        public void setDatasourcesPaginatedList(GetDatasourcesByDatasetPaginatedListResult result) {
            DatasourceRecord[] records = new DatasourceRecord[result.getDatasourcesList().size()];
            int index = 0;
            for (DatasourceDto datasourceDto : result.getDatasourcesList()) {
                records[index++] = StatisticalResourcesRecordUtils.getDatasourceRecord(datasourceDto);
            }
            datasourcesList.getListGrid().setData(records);
            datasourcesList.refreshPaginationInfo(result.getPageNumber(), result.getDatasourcesList().size(), result.getTotalResults());
        }
    }
    
    public List<String> getUrnsFromSelected() {
        List<String> codes = new ArrayList<String>();
        for (ListGridRecord record : datasourcesListPanel.datasourcesList.getListGrid().getSelectedRecords()) {
            DatasourceRecord datasourceRecord = (DatasourceRecord) record;
            codes.add(datasourceRecord.getUrn());
        }
        return codes;
    }

    private class DatasourceFormPanel extends VLayout {

        private MainFormLayout   mainFormLayout;
        private DatasourceResourceIdentifiersForm identifiersForm;
        private DatasourceResourceIdentifiersEditionForm identifiersEditionForm;
        private DatasourceContentEditionForm contentEditionForm;

        private DatasourceDto    datasource;

        public DatasourceFormPanel() {
            super();
            setWidth("99%");

            mainFormLayout = new MainFormLayout();
            mainFormLayout.setMargin(0);

            this.addMember(mainFormLayout);
            createViewForm();
            createEditionForm();

            bindEvents();
            this.hide();
        }

        private void bindEvents() {
            mainFormLayout.getSave().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    uiHandlers.saveDatasource(getDatasource());
                }
            });

            mainFormLayout.getCancelToolStripButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if (isCreationMode()) {
                        DatasourceFormPanel.this.hide();
                    }
                }
            });
        }

        private void createViewForm() {
            identifiersForm = new DatasourceResourceIdentifiersForm();
            mainFormLayout.addViewCanvas(identifiersForm);
        }

        private void createEditionForm() {
            identifiersEditionForm = new DatasourceResourceIdentifiersEditionForm();
            contentEditionForm = new DatasourceContentEditionForm();
            mainFormLayout.addEditionCanvas(identifiersEditionForm);
            mainFormLayout.addEditionCanvas(contentEditionForm);
        }

        private void createDatasource() {
            this.datasource = new DatasourceDto();

            mainFormLayout.setTitleLabelContents(getConstants().datasourceNew());
            mainFormLayout.setEditionMode();
            fillViewForm(datasource);
            fillEditionForm(datasource);

            mainFormLayout.redraw();
            this.show();
        }

        private void selectDatasource(DatasourceDto datasourceDto) {
            this.datasource = datasourceDto;
            mainFormLayout.setTitleLabelContents(datasourceDto.getCode());
            mainFormLayout.setViewMode();
            fillViewForm(datasource);
            fillEditionForm(datasource);
            mainFormLayout.redraw();
            this.show();
        }

        private void fillViewForm(DatasourceDto datasourceDto) {
            identifiersForm.setDatasourceDto(datasourceDto);
        }

        private void fillEditionForm(DatasourceDto datasourceDto) {
            identifiersEditionForm.setDatasourceDto(datasourceDto);
        }

        private DatasourceDto getDatasource() {
            datasource = identifiersEditionForm.getDatasourceDto(datasource);
            return datasource;
        }

        private boolean isCreationMode() {
            return StringUtils.isEmpty(this.datasource.getUrn());
        }
    }
}
