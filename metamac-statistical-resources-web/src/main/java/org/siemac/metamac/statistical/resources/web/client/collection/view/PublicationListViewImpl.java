package org.siemac.metamac.statistical.resources.web.client.collection.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;
import static org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.collection.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.collection.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.PublicationListPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.utils.PublicationClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.collection.view.handlers.PublicationListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.collection.widgets.NewPublicationWindow;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;
import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.SearchSectionStack;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PublicationListViewImpl extends ViewImpl implements PublicationListPresenter.PublicationListView {

    private PublicationListUiHandlers uiHandlers;

    private VLayout                   panel;

    private SearchSectionStack        searchSectionStack;
    private PaginatedCheckListGrid    collectionListGrid;

    private ToolStripButton           newPublicationButton;
    private ToolStripButton           deletePublicationButton;

    private DeleteConfirmationWindow  deleteConfirmationWindow;

    @Inject
    public PublicationListViewImpl() {
        super();
        panel = new VLayout();

        // ToolStrip

        ToolStrip toolStrip = new ToolStrip();
        toolStrip.setWidth100();
        newPublicationButton = new ToolStripButton(getConstants().actionNew(), RESOURCE.newListGrid().getURL());
        newPublicationButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final NewPublicationWindow newPublicationWindow = new NewPublicationWindow(getConstants().collectionCreate());
                newPublicationWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        if (newPublicationWindow.validateForm()) {
                            uiHandlers.createPublication(newPublicationWindow.getNewPublicationDto());
                            newPublicationWindow.destroy();
                        }
                    }
                });
            }
        });
        newPublicationButton.setVisibility(PublicationClientSecurityUtils.canCreatePublication() ? Visibility.VISIBLE : Visibility.HIDDEN);

        deleteConfirmationWindow = new DeleteConfirmationWindow(getMessages().collectionDeleteConfirmationTitle(), getMessages().collectionDeleteConfirmation());
        deleteConfirmationWindow.setVisibility(Visibility.HIDDEN);
        deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.deletePublication(getUrnsFromSelectedPublications());
                deleteConfirmationWindow.hide();
            }
        });

        deletePublicationButton = new ToolStripButton(getConstants().actionDelete(), RESOURCE.deleteListGrid().getURL());
        deletePublicationButton.setVisibility(Visibility.HIDDEN);
        deletePublicationButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                deleteConfirmationWindow.show();
            }
        });

        toolStrip.addButton(newPublicationButton);
        toolStrip.addButton(deletePublicationButton);

        // Search

        searchSectionStack = new SearchSectionStack();
        searchSectionStack.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                uiHandlers.retrievePublications(PublicationListPresenter.PUBLICATION_LIST_FIRST_RESULT, PublicationListPresenter.PUBLICATION_LIST_MAX_RESULTS, searchSectionStack.getSearchCriteria());
            }
        });

        // Publication list

        collectionListGrid = new PaginatedCheckListGrid(PublicationListPresenter.PUBLICATION_LIST_MAX_RESULTS, new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                uiHandlers.retrievePublications(firstResult, maxResults, null);
            }
        });
        collectionListGrid.getListGrid().setAutoFitMaxRecords(PublicationListPresenter.PUBLICATION_LIST_MAX_RESULTS);
        collectionListGrid.getListGrid().setAutoFitData(Autofit.VERTICAL);
        collectionListGrid.getListGrid().setDataSource(new PublicationDS());
        collectionListGrid.getListGrid().setUseAllDataSourceFields(false);
        collectionListGrid.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                if (collectionListGrid.getListGrid().getSelectedRecords().length > 0) {
                    // Show delete button
                    showListGridDeleteButton();
                } else {
                    deletePublicationButton.hide();
                }
            }
        });

        collectionListGrid.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getFieldNum() != 0) { // Clicking checkBox will be ignored
                    String urn = ((PublicationRecord) event.getRecord()).getAttribute(PublicationDS.URN);
                    uiHandlers.goToPublication(urn);
                }
            }
        });

        ListGridField fieldCode = new ListGridField(PublicationDS.IDENTIFIER, getConstants().collectionIdentifier());
        fieldCode.setAlign(Alignment.LEFT);
        ListGridField fieldName = new ListGridField(PublicationDS.TITLE, getConstants().collectionTitle());
        ListGridField status = new ListGridField(PublicationDS.PROC_STATUS, getConstants().lifeCycleProcStatus());
        collectionListGrid.getListGrid().setFields(fieldCode, fieldName, status);

        panel.addMember(toolStrip);
        panel.addMember(searchSectionStack);
        panel.addMember(collectionListGrid);
    }

    @Override
    public void setPublicationPaginatedList(List<PublicationDto> collectionDtos, int firstResult, int totalResults) {
        PublicationRecord[] records = new PublicationRecord[collectionDtos.size()];
        int index = 0;
        for (PublicationDto scheme : collectionDtos) {
            records[index++] = StatisticalResourcesRecordUtils.getPublicationRecord(scheme);
        }
        collectionListGrid.getListGrid().setData(records);
    }

    @Override
    public void setUiHandlers(PublicationListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == PublicationListPresenter.TYPE_SetContextAreaContentOperationResourcesToolBar) {
            if (content != null) {
                Canvas[] canvas = ((ToolStrip) content).getMembers();
                for (int i = 0; i < canvas.length; i++) {
                    if (canvas[i] instanceof ToolStripButton) {
                        if (StatisticalResourcesToolStripButtonEnum.COLLECTIONS.getValue().equals(((ToolStripButton) canvas[i]).getID())) {
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

    @Override
    public Widget asWidget() {
        return panel;
    }

    private void showListGridDeleteButton() {
        if (PublicationClientSecurityUtils.canDeletePublication()) {
            deletePublicationButton.show();
        }
    }

    private List<String> getUrnsFromSelectedPublications() {
        List<String> urns = new ArrayList<String>();
        for (ListGridRecord record : collectionListGrid.getListGrid().getSelectedRecords()) {
            PublicationRecord collectionRecord = (PublicationRecord) record;
            urns.add(collectionRecord.getUrn());
        }
        return urns;
    }

}
