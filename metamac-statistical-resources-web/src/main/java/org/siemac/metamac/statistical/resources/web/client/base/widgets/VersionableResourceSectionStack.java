package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.model.ds.VersionableResourceDS;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridSectionStack;

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
}
