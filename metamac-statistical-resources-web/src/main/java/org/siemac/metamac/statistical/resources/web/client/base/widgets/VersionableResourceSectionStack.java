package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.model.ds.VersionableResourceDS;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridSectionStack;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.grid.ListGridField;

public class VersionableResourceSectionStack extends CustomListGridSectionStack {

    public VersionableResourceSectionStack(String title) {
        super(new BaseCustomListGrid(), title, "versionSectionStackStyle");

        // Add fields to listGrid
        ListGridField codeField = new ListGridField(VersionableResourceDS.CODE, getConstants().identifiableStatisticalResourceCode());
        codeField.setWidth("30%");
        ListGridField nameField = new ListGridField(VersionableResourceDS.TITLE, getConstants().nameableStatisticalResourceTitle());
        ListGridField versionField = new ListGridField(VersionableResourceDS.VERSION, getConstants().versionableStatisticalResourceVersionLogic());
        versionField.setWidth("15%");
        listGrid.setFields(codeField, nameField, versionField);

        // Add listGrid to sectionStack
        defaultSection.setItems(listGrid);
    }

    public void selectRecord(String fieldName, String value) {
        RecordList recordList = listGrid.getRecordList();
        final int index = recordList.findIndex(fieldName, value);
        if (index >= 0) {
            listGrid.selectRecord(index);
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {

                @Override
                public void execute() {
                    listGrid.scrollToRow(index);
                }
            });
        }
    }
}
