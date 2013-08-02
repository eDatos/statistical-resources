package org.siemac.metamac.statistical.resources.web.client.query.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.LifeCycleBaseListViewImpl;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.client.query.model.record.QueryRecord;
import org.siemac.metamac.statistical.resources.web.client.query.presenter.QueryListPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionsResult;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGridField;
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
                getUiHandlers().retrieveQueries(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, criteria);
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

        ListGridField fieldCode = new ListGridField(QueryDS.CODE, getConstants().identifiableStatisticalResourceCode());
        fieldCode.setAlign(Alignment.LEFT);
        ListGridField fieldName = new ListGridField(QueryDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        ListGridField status = new ListGridField(QueryDS.PROC_STATUS, getConstants().lifeCycleStatisticalResourceProcStatus());
        ListGridField type = new ListGridField(QueryDS.TYPE, getConstants().queryType());
        listGrid.getListGrid().setFields(fieldCode, fieldName, status, type);

        // Delete confirmation window

        deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().deleteQueries(getUrnsFromSelected());
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

    private List<String> getUrnsFromSelected() {
        List<String> codes = new ArrayList<String>();
        for (ListGridRecord record : listGrid.getListGrid().getSelectedRecords()) {
            QueryRecord schemeRecord = (QueryRecord) record;
            codes.add(schemeRecord.getUrn());
        }
        return codes;
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
        getUiHandlers().retrieveQueries(firstResult, maxResults, null);
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
}
