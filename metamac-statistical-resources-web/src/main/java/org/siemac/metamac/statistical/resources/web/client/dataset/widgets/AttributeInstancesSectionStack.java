package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeInstanceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;
import org.siemac.metamac.web.common.client.widgets.CustomListGridSectionStack;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;

import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class AttributeInstancesSectionStack extends CustomListGridSectionStack {

    private CustomToolStripButton newInstanceButton;
    private CustomToolStripButton deleteInstanceButton;

    public AttributeInstancesSectionStack() {
        super(new CustomListGrid(), getConstants().datasetAttributeInstances(), "versionSectionStackStyle");

        // ToolStrip

        ToolStrip toolStrip = new ToolStrip();
        newInstanceButton = new CustomToolStripButton(MetamacWebCommon.getConstants().actionNew(), GlobalResources.RESOURCE.newListGrid().getURL());
        deleteInstanceButton = new CustomToolStripButton(MetamacWebCommon.getConstants().actionDelete(), GlobalResources.RESOURCE.deleteListGrid().getURL());
        toolStrip.addButton(newInstanceButton);
        toolStrip.addButton(deleteInstanceButton);

        // ListGrid

        CustomListGridField valueField = new CustomListGridField(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue());
        listGrid.setFields(valueField);

        // Add listGrid to sectionStack
        defaultSection.setItems(toolStrip, listGrid);
    }

    public void showInstances(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        setSectionTitle(getMessages().datasetAttributeIntances(dsdAttributeDto.getIdentifier()));
        listGrid.setData(StatisticalResourcesRecordUtils.getDsdAttributeInstanceRecords(dsdAttributeInstanceDtos));
        show();
    }

    public HasClickHandlers getNewInstanceButton() {
        return newInstanceButton;
    }

    private void setSectionTitle(String title) {
        defaultSection.setTitle(title);
        markForRedraw();
    }
}
