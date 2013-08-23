package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;

import com.smartgwt.client.widgets.layout.VLayout;

public class AttributePanel extends VLayout {

    private AttributeInstancesSectionStack instacesSectionStack;
    private AttributeMainFormLayout        mainFormLayout;

    public AttributePanel() {

        // Instances SectionStack

        instacesSectionStack = new AttributeInstancesSectionStack();
        addMember(instacesSectionStack);

        // Main form layout

        mainFormLayout = new AttributeMainFormLayout();
        addMember(mainFormLayout);
    }

    public void showAttributeInstances(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        hideInstances();
        if (CommonUtils.hasDatasetRelationshipType(dsdAttributeDto)) {
            showDatasetAttributeInstance(dsdAttributeDto, dsdAttributeInstanceDtos);
        } else if (CommonUtils.hasDimensionRelationshipType(dsdAttributeDto)) {
            showDimensionAttributeInstances(dsdAttributeDto, dsdAttributeInstanceDtos);
        } else if (CommonUtils.hasGroupRelationshipType(dsdAttributeDto)) {
            // TODO
        }
        show();
    }

    private void showDatasetAttributeInstance(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        // Attributes with dataset relationship only have one instance
        DsdAttributeInstanceDto dsdAttributeInstanceDto = dsdAttributeInstanceDtos != null && !dsdAttributeInstanceDtos.isEmpty() ? dsdAttributeInstanceDtos.get(0) : new DsdAttributeInstanceDto();
        mainFormLayout.showInstance(dsdAttributeDto, dsdAttributeInstanceDto);
    }

    private void showDimensionAttributeInstances(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        instacesSectionStack.showInstances(dsdAttributeDto, dsdAttributeInstanceDtos);
    }

    public void setUiHandlers(DatasetAttributesTabUiHandlers uiHandlers) {
        mainFormLayout.setUiHandlers(uiHandlers);
    }

    public void hideInstances() {
        instacesSectionStack.hide();
        mainFormLayout.hide();
    }

    //
    // RELATED RESOURCES
    //

    public void setItemsForDatasetLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        mainFormLayout.setItemsForDatasetLevelAttributeValueSelection(externalItemDtos, firstResult, totalResults);
    }
}
