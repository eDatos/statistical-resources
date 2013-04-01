package org.siemac.metamac.statistical.resources.web.client.operation.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.operation.model.ds.OperationDS;
import org.siemac.metamac.statistical.resources.web.client.operation.model.record.OperationRecord;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationListPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.view.handlers.OperationListUiHandlers;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.PaginatedListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class OperationListViewImpl extends ViewImpl implements OperationListPresenter.OperationListView {

    private OperationListUiHandlers uiHandlers;
    private VLayout                 panel;

    private PaginatedListGrid       operationsList;

    @Inject
    public OperationListViewImpl() {
        super();

        operationsList = new PaginatedListGrid(OperationListPresenter.OPERATION_LIST_MAX_RESULTS, new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                uiHandlers.retrieveOperations(firstResult, maxResults, null);
            }
        });
        operationsList.getListGrid().setAutoFitMaxRecords(OperationListPresenter.OPERATION_LIST_MAX_RESULTS);
        operationsList.getListGrid().setAutoFitData(Autofit.VERTICAL);
        operationsList.getListGrid().setDataSource(new OperationDS());
        operationsList.getListGrid().setUseAllDataSourceFields(false);

        ListGridField fieldIdentifier = new ListGridField(OperationDS.IDENTIFIER, getConstants().identifiableStatisticalResourceCode());
        fieldIdentifier.setAlign(Alignment.LEFT);
        ListGridField fieldTitle = new ListGridField(OperationDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        operationsList.getListGrid().setFields(fieldIdentifier, fieldTitle);

        panel = new VLayout();
        panel.addMember(operationsList);

        bindEvents();
    }

    private void bindEvents() {
        operationsList.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                String urn = ((OperationRecord) event.getRecord()).getAttribute(OperationDS.URN);
                uiHandlers.goToOperation(urn);
            }
        });
    }

    @Override
    public void setOperationPaginatedList(GetStatisticalOperationsPaginatedListResult operationsPaginatedList) {
        setOperationList(operationsPaginatedList.getOperationsList());
        operationsList.refreshPaginationInfo(operationsPaginatedList.getPageNumber(), operationsPaginatedList.getOperationsList().size(), operationsPaginatedList.getTotalResults());
    }

    private void setOperationList(List<ExternalItemDto> operationsExternalDtos) {
        OperationRecord[] records = new OperationRecord[operationsExternalDtos.size()];
        int index = 0;
        for (ExternalItemDto operationDto : operationsExternalDtos) {
            records[index++] = new OperationRecord(operationDto.getCode(), InternationalStringUtils.getLocalisedString(operationDto.getTitle()), operationDto.getUri(), operationDto.getUrn(),
                    operationDto);
        }
        operationsList.getListGrid().setData(records);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(OperationListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
