package org.siemac.metamac.statistical.resources.web.client.publication.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.base.view.StatisticalResourceBaseListViewImpl;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.NewStatisticalResourceWindow;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationListPresenter;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.NewPublicationWindow;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PublicationListViewImpl extends StatisticalResourceBaseListViewImpl<PublicationListUiHandlers> implements PublicationListPresenter.PublicationListView {

    private NewPublicationWindow newPublicationWindow;

    @Inject
    public PublicationListViewImpl() {
        super();

        // Search

        searchSectionStack.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                getUiHandlers().retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, searchSectionStack.getSearchCriteria());
            }
        });

        // Publication list

        listGrid.getListGrid().setDataSource(new PublicationDS());
        listGrid.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getFieldNum() != 0) { // Clicking checkBox will be ignored
                    String urn = ((PublicationRecord) event.getRecord()).getAttribute(PublicationDS.URN);
                    getUiHandlers().goToPublication(urn);
                }
            }
        });

        ListGridField fieldCode = new ListGridField(PublicationDS.CODE, getConstants().identifiableStatisticalResourceCode());
        fieldCode.setAlign(Alignment.LEFT);
        ListGridField fieldName = new ListGridField(PublicationDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        ListGridField status = new ListGridField(PublicationDS.PROC_STATUS, getConstants().lifeCycleStatisticalResourceProcStatus());
        listGrid.getListGrid().setFields(fieldCode, fieldName, status);

        // Delete confirmation window

        deleteConfirmationWindow.getYesButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().deletePublication(getSelectedResourcesUrns());
                deleteConfirmationWindow.hide();
            }
        });
    }

    @Override
    public void setPublicationPaginatedList(List<PublicationVersionDto> publicationDtos, int firstResult, int totalResults) {
        PublicationRecord[] records = new PublicationRecord[publicationDtos.size()];
        int index = 0;
        for (PublicationVersionDto scheme : publicationDtos) {
            records[index++] = StatisticalResourcesRecordUtils.getPublicationRecord(scheme);
        }
        listGrid.getListGrid().setData(records);
    }

    @Override
    protected NewStatisticalResourceWindow getNewStatisticalResourceWindow() {
        return newPublicationWindow;
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == PublicationListPresenter.TYPE_SetContextAreaContentOperationResourcesToolBar) {
            if (content != null) {
                Canvas[] canvas = ((ToolStrip) content).getMembers();
                for (int i = 0; i < canvas.length; i++) {
                    if (canvas[i] instanceof ToolStripButton) {
                        if (StatisticalResourcesToolStripButtonEnum.PUBLICATIONS.getValue().equals(((ToolStripButton) canvas[i]).getID())) {
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

    //
    // LISTGRID
    //

    @Override
    public void retrieveResultSet(int firstResult, int maxResults) {
        // TODO why the criteria is null
        getUiHandlers().retrievePublications(firstResult, maxResults, null);
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
                newPublicationWindow = new NewPublicationWindow(getConstants().publicationCreate());
                newPublicationWindow.setUiHandlers(getUiHandlers());
                newPublicationWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        if (newPublicationWindow.validateForm()) {
                            getUiHandlers().createPublication(newPublicationWindow.getNewPublicationDto());
                            newPublicationWindow.destroy();
                        }
                    }
                });
                newPublicationWindow.setDefaultLanguage(StatisticalResourcesDefaults.defaultLanguage);
                newPublicationWindow.setDefaultMaintainer(StatisticalResourcesDefaults.defaultAgency);
            }
        };
    }
}
