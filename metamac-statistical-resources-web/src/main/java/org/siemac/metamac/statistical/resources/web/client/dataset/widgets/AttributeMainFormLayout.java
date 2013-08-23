package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.AttributeDatasetLevelEditionForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.AttributeDatasetLevelForm;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;

public class AttributeMainFormLayout extends MainFormLayout {

    private AttributeDatasetLevelForm        attributeDatasetLevelForm;
    private AttributeDatasetLevelEditionForm attributeDatasetLevelEditionForm;

    public AttributeMainFormLayout() {

        // DATASET LEVEL FORMS

        attributeDatasetLevelForm = new AttributeDatasetLevelForm();
        addViewCanvas(attributeDatasetLevelForm);

        attributeDatasetLevelEditionForm = new AttributeDatasetLevelEditionForm();
        addEditionCanvas(attributeDatasetLevelEditionForm);

        // DIMENSION LEVEL FORMS

        // TODO
    }

    public void showInstance(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        hideAllForms();
        switch (dsdAttributeDto.getAttributeRelationship().getRelationshipType()) {
            case NO_SPECIFIED_RELATIONSHIP:
                showDataLevelForm(dsdAttributeDto, dsdAttributeInstanceDto);
                break;
            case DIMENSION_RELATIONSHIP:
                // TODO
                break;
            default:
                break;
        }
    }

    private void showDataLevelForm(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        attributeDatasetLevelForm.setDsdAttributeDto(dsdAttributeDto, dsdAttributeInstanceDto);
        attributeDatasetLevelEditionForm.setDsdAttributeDto(dsdAttributeDto, dsdAttributeInstanceDto);

        attributeDatasetLevelForm.show();
        attributeDatasetLevelEditionForm.show();
        show();
    }

    private void hideAllForms() {
        attributeDatasetLevelForm.hide();
        attributeDatasetLevelEditionForm.hide();
        hide();
    }

    public void setUiHandlers(DatasetAttributesTabUiHandlers uiHandlers) {
        attributeDatasetLevelEditionForm.setUiHandlers(uiHandlers);
    }

    //
    // RELATED RESOURCES
    //

    public void setItemsForDatasetLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        attributeDatasetLevelEditionForm.setItemsForDatasetLevelAttributeValueSelection(externalItemDtos, firstResult, totalResults);
    }
}
