package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.AttributeDatasetLevelEditionForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.AttributeDatasetLevelForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.AttributeDimensionLevelEditionForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.AttributeDimensionLevelForm;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;

public class AttributeMainFormLayout extends MainFormLayout {

    private AttributeDatasetLevelForm          attributeDatasetLevelForm;
    private AttributeDatasetLevelEditionForm   attributeDatasetLevelEditionForm;

    private AttributeDimensionLevelForm        attributeDimensionLevelForm;
    private AttributeDimensionLevelEditionForm attributeDimensionLevelEditionForm;

    public AttributeMainFormLayout() {

        // DATASET LEVEL FORMS

        attributeDatasetLevelForm = new AttributeDatasetLevelForm();
        addViewCanvas(attributeDatasetLevelForm);

        attributeDatasetLevelEditionForm = new AttributeDatasetLevelEditionForm();
        addEditionCanvas(attributeDatasetLevelEditionForm);

        // DIMENSION LEVEL FORMS

        attributeDimensionLevelForm = new AttributeDimensionLevelForm();
        addViewCanvas(attributeDimensionLevelForm);

        attributeDimensionLevelEditionForm = new AttributeDimensionLevelEditionForm();
        addEditionCanvas(attributeDimensionLevelEditionForm);
    }

    public void showInstance(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        hideAllForms();
        switch (dsdAttributeDto.getAttributeRelationship().getRelationshipType()) {
            case NO_SPECIFIED_RELATIONSHIP:
                showDataLevelForm(dsdAttributeDto, dsdAttributeInstanceDto);
                break;
            case DIMENSION_RELATIONSHIP:
                showDimensionLevelForm(dsdAttributeDto, dsdAttributeInstanceDto);
                break;
            default:
                break;
        }
    }

    private void showDataLevelForm(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        attributeDatasetLevelForm.setAttribute(dsdAttributeDto, dsdAttributeInstanceDto);
        attributeDatasetLevelForm.show();

        attributeDatasetLevelEditionForm.setAttribute(dsdAttributeDto, dsdAttributeInstanceDto);
        attributeDatasetLevelEditionForm.show();

        show();
    }

    private void showDimensionLevelForm(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        attributeDimensionLevelForm.setAttribute(dsdAttributeDto, dsdAttributeInstanceDto);
        attributeDimensionLevelForm.show();

        attributeDimensionLevelEditionForm.setAttribute(dsdAttributeDto, dsdAttributeInstanceDto);
        attributeDimensionLevelEditionForm.show();

        show();
    }

    private void hideAllForms() {
        attributeDatasetLevelForm.hide();
        attributeDatasetLevelEditionForm.hide();
        attributeDimensionLevelForm.hide();
        attributeDimensionLevelEditionForm.hide();
        hide();
    }

    public void setUiHandlers(DatasetAttributesTabUiHandlers uiHandlers) {
        attributeDatasetLevelEditionForm.setUiHandlers(uiHandlers);
        attributeDimensionLevelEditionForm.setUiHandlers(uiHandlers);
    }

    public void setDimensionCoverageValues(String dimensionId, List<CodeItemDto> codeItemDtos) {
        attributeDimensionLevelEditionForm.setDimensionCoverageValues(dimensionId, codeItemDtos);
    }

    //
    // RELATED RESOURCES
    //

    public void setItemsForDatasetLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        attributeDatasetLevelEditionForm.setItemsForDatasetLevelAttributeValueSelection(externalItemDtos, firstResult, totalResults);
    }
}
