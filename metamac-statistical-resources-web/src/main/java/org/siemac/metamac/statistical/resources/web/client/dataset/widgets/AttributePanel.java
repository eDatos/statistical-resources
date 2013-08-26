package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.model.record.DsdAttributeInstanceRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class AttributePanel extends VLayout {

    private AttributeInstancesSectionStack instancesSectionStack;
    private AttributeMainFormLayout        mainFormLayout;

    private DsdAttributeDto                dsdAttributeDto;

    public AttributePanel() {

        // Instances SectionStack

        instancesSectionStack = new AttributeInstancesSectionStack();
        instancesSectionStack.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getRecord() instanceof DsdAttributeInstanceRecord) {
                    DsdAttributeInstanceDto dsdAttributeInstanceDto = ((DsdAttributeInstanceRecord) event.getRecord()).getDsdAttributeInstanceDto();
                    // TODO
                }
            }
        });

        instancesSectionStack.getNewInstanceButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                if (CommonUtils.hasDimensionRelationshipType(dsdAttributeDto)) {
                    DsdAttributeInstanceDto dsdAttributeInstanceDto = createNewAttributeInstance(dsdAttributeDto.getAttributeRelationship().getDimensions());
                    mainFormLayout.showInstance(dsdAttributeDto, dsdAttributeInstanceDto);
                } else if (CommonUtils.hasGroupRelationshipType(dsdAttributeDto)) {
                    // TODO
                }
            }
        });

        addMember(instancesSectionStack);

        // Main form layout

        mainFormLayout = new AttributeMainFormLayout();
        addMember(mainFormLayout);
    }

    public void showAttributeInstances(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {

        this.dsdAttributeDto = dsdAttributeDto;

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

    public void setDimensionCoverageValues(String dimensionId, List<CodeItemDto> codeItemDtos) {
        mainFormLayout.setDimensionCoverageValues(dimensionId, codeItemDtos);
    }

    private void showDatasetAttributeInstance(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        // Attributes with dataset relationship only have one instance
        DsdAttributeInstanceDto dsdAttributeInstanceDto = dsdAttributeInstanceDtos != null && !dsdAttributeInstanceDtos.isEmpty() ? dsdAttributeInstanceDtos.get(0) : new DsdAttributeInstanceDto();
        mainFormLayout.showInstance(dsdAttributeDto, dsdAttributeInstanceDto);
    }

    private void showDimensionAttributeInstances(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        instancesSectionStack.showInstances(dsdAttributeDto, dsdAttributeInstanceDtos);
    }

    private DsdAttributeInstanceDto createNewAttributeInstance(List<String> dimensionIds) {
        DsdAttributeInstanceDto attributeInstance = new DsdAttributeInstanceDto();
        attributeInstance.setCodeDimensions(new HashMap<String, List<CodeItemDto>>());
        for (String dimensionId : dimensionIds) {
            attributeInstance.getCodeDimensions().put(dimensionId, new ArrayList<CodeItemDto>());
        }
        return attributeInstance;
    }

    public void setUiHandlers(DatasetAttributesTabUiHandlers uiHandlers) {
        mainFormLayout.setUiHandlers(uiHandlers);
    }

    public void hideInstances() {
        instancesSectionStack.hide();
        mainFormLayout.hide();
    }

    //
    // RELATED RESOURCES
    //

    public void setItemsForDatasetLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        mainFormLayout.setItemsForDatasetLevelAttributeValueSelection(externalItemDtos, firstResult, totalResults);
    }
}
