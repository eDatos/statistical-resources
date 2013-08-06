package org.siemac.metamac.statistical.resources.web.client.query.view;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.LifeCycleBaseListViewImpl;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.client.query.model.record.QueryRecord;
import org.siemac.metamac.statistical.resources.web.client.query.presenter.QueryListPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.ResourceFieldUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionsResult;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class QueryListViewImpl extends LifeCycleBaseListViewImpl<QueryListUiHandlers> implements QueryListPresenter.QueryListView {

    @Inject
    public QueryListViewImpl() {
        super();

        // Search

        searchSectionStack.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                StatisticalResourceWebCriteria criteria = new StatisticalResourceWebCriteria(searchSectionStack.getSearchCriteria());
                getUiHandlers().retrieveQueriesByStatisticalOperation(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, criteria);
            }
        });

        // List

        listGrid.getListGrid().setDataSource(new QueryDS());
        listGrid.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getFieldNum() != 0) { // Clicking checkBox will be ignored
                    QueryRecord record = (QueryRecord) event.getRecord();
                    getUiHandlers().goToQuery(record.getUrn());
                }
            }
        });
        listGrid.getListGrid().setFields(ResourceFieldUtils.getQueryListGridFields());

        // Delete confirmation window

        deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().deleteQueries(getSelectedResourcesUrns());
            }
        });
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    public void setQueriesPaginatedList(GetQueryVersionsResult result) {
        QueryRecord[] records = new QueryRecord[result.getQueriesList().size()];
        int index = 0;
        for (QueryVersionDto queryDto : result.getQueriesList()) {
            records[index++] = StatisticalResourcesRecordUtils.getQueryRecord(queryDto);
        }
        listGrid.getListGrid().setData(records);
        listGrid.refreshPaginationInfo(result.getPageNumber(), result.getQueriesList().size(), result.getTotalResults());
    }

    @Override
    public void goToQueryListLastPageAfterCreate() {
        listGrid.goToLastPageAfterCreate();
    }

    @Override
    public void setQueryPaginatedList(GetQueryVersionsResult queriesPaginatedList) {
        setQueriesPaginatedList(queriesPaginatedList);
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == QueryListPresenter.TYPE_SetContextAreaContentOperationResourcesToolBar) {
            if (content != null) {
                Canvas[] canvas = ((ToolStrip) content).getMembers();
                for (int i = 0; i < canvas.length; i++) {
                    if (canvas[i] instanceof ToolStripButton) {
                        if (StatisticalResourcesToolStripButtonEnum.QUERIES.getValue().equals(((ToolStripButton) canvas[i]).getID())) {
                            ((ToolStripButton) canvas[i]).select();
                        }
                    }
                }
                panel.addMember(content, 0);
            }
        } else {
            // To support inheritance in your views it is good practice to call super.setInSlot when you can't handle the call.
            // Who knows, maybe the parent class knows what to do with this slot.
            super.setInSlot(slot, content);
        }
    }

    //
    // LISTGRID
    //

    @Override
    public void retrieveResultSet(int firstResult, int maxResults) {
        // TODO why the criteria is null?
        getUiHandlers().retrieveQueriesByStatisticalOperation(firstResult, maxResults, null);
    }

    //
    // LISTGRID BUTTONS
    //

    // Create

    @Override
    public ClickHandler getNewButtonClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().goToNewQuery();
            }
        };
    }

    // Sent to production validation

    @Override
    protected ClickHandler getSendToProductionValidationClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<QueryVersionDto> queryVersionDtos = StatisticalResourcesRecordUtils.getQueryVersionDtosFromListGridRecords(listGrid.getListGrid().getSelectedRecords());
                getUiHandlers().sendToProductionValidation(queryVersionDtos);
            }
        };
    }

    // Send to diffusion validation

    @Override
    protected ClickHandler getSendToDiffusionValidationClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<QueryVersionDto> queryVersionDtos = StatisticalResourcesRecordUtils.getQueryVersionDtosFromListGridRecords(listGrid.getListGrid().getSelectedRecords());
                getUiHandlers().sendToDiffusionValidation(queryVersionDtos);
            }
        };
    }

    // Reject validation

    @Override
    protected ClickHandler getRejectValidationClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<QueryVersionDto> queryVersionDtos = StatisticalResourcesRecordUtils.getQueryVersionDtosFromListGridRecords(listGrid.getListGrid().getSelectedRecords());
                getUiHandlers().rejectValidation(queryVersionDtos);
            }
        };
    }

    // Publish

    @Override
    protected ClickHandler getPublishClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<QueryVersionDto> queryVersionDtos = StatisticalResourcesRecordUtils.getQueryVersionDtosFromListGridRecords(listGrid.getListGrid().getSelectedRecords());
                getUiHandlers().publish(queryVersionDtos);
            }
        };
    }

    // Program publication

    @Override
    protected ClickHandler getProgramPublicationClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                List<QueryVersionDto> queryVersionDtos = StatisticalResourcesRecordUtils.getQueryVersionDtosFromListGridRecords(listGrid.getListGrid().getSelectedRecords());
                getUiHandlers().programPublication(queryVersionDtos);
            }
        };
    }

    // Cancel programmed publication

    @Override
    protected ClickHandler getCancelProgrammedPublicationClickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // TODO Auto-generated method stub

            }
        };
    }

    //
    // LISTGRID ACTIONS
    //

    @Override
    protected boolean canDelete(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canSendToProductionValidation(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canSendToDiffusionValidation(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canRejectValidation(ListGridRecord record) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected boolean canPublish(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canProgramPublication(ListGridRecord record) {
        // TODO Security
        return true;
    }

    @Override
    protected boolean canCancelProgrammedPublication(ListGridRecord record) {
        // TODO Security
        return true;
    }
}
