package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;

import com.smartgwt.client.widgets.layout.VLayout;

public class AttributePanel extends VLayout {

    private AttributeMainFormLayout mainFormLayout;

    public AttributePanel() {
        mainFormLayout = new AttributeMainFormLayout();
        addMember(mainFormLayout);
    }

    public void showAttribute(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        mainFormLayout.showAttribute(dsdAttributeDto, dsdAttributeInstanceDtos);
        show();
    }

    public void setUiHandlers(DatasetAttributesTabUiHandlers uiHandlers) {
        mainFormLayout.setUiHandlers(uiHandlers);
    }

    //
    // RELATED RESOURCES
    //

    public void setItemsForDatasetLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        mainFormLayout.setItemsForDatasetLevelAttributeValueSelection(externalItemDtos, firstResult, totalResults);
    }
}
