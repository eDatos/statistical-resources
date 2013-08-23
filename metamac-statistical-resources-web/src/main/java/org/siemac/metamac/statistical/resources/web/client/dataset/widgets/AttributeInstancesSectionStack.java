package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeInstanceDS;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;
import org.siemac.metamac.web.common.client.widgets.CustomListGridSectionStack;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;

import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class AttributeInstancesSectionStack extends CustomListGridSectionStack {

    public AttributeInstancesSectionStack() {
        super(new BaseCustomListGrid(), getConstants().datasetAttributeInstances(), "versionSectionStackStyle");

        // ToolStrip

        ToolStrip toolStrip = new ToolStrip();
        CustomToolStripButton newInstance = new CustomToolStripButton(MetamacWebCommon.getConstants().actionNew(), GlobalResources.RESOURCE.newListGrid().getURL());
        CustomToolStripButton deleteInstance = new CustomToolStripButton(MetamacWebCommon.getConstants().actionDelete(), GlobalResources.RESOURCE.deleteListGrid().getURL());
        toolStrip.addButton(newInstance);
        toolStrip.addButton(deleteInstance);

        // ListGrid

        CustomListGridField valueField = new CustomListGridField(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue());
        listGrid.setFields(valueField);

        // Add listGrid to sectionStack
        defaultSection.setItems(toolStrip, listGrid);
    }

    public void showInstances(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        setSectionTitle(getMessages().datasetAttributeIntances(dsdAttributeDto.getIdentifier()));
        show();
    }

    public void setSectionTitle(String title) {
        defaultSection.setTitle(title);
    }
}
