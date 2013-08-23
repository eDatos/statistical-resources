package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.AttributeDatasetLevelEditionForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.AttributeDatasetLevelForm;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;

public class DsdAttributeMainFormLayout extends MainFormLayout {

    private AttributeDatasetLevelForm        attributeDatasetLevelForm;
    private AttributeDatasetLevelEditionForm attributeDatasetLevelEditionForm;

    public DsdAttributeMainFormLayout() {

        // DATASET LEVEL FORMS

        attributeDatasetLevelForm = new AttributeDatasetLevelForm();
        addViewCanvas(attributeDatasetLevelForm);

        attributeDatasetLevelEditionForm = new AttributeDatasetLevelEditionForm();
        addEditionCanvas(attributeDatasetLevelEditionForm);

        // DIMENSION LEVEL FORMS

        // TODO
    }

    public void showAttribute(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        hideAllForms();
        switch (dsdAttributeDto.getAttributeRelationship().getRelationshipType()) {
            case NO_SPECIFIED_RELATIONSHIP:
                showDataLevelForm(dsdAttributeDto, dsdAttributeInstanceDtos);
                break;
            case DIMENSION_RELATIONSHIP:
                // TODO
                break;
            default:
                break;
        }
    }

    private void showDataLevelForm(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        attributeDatasetLevelForm.setDsdAttributeDto(dsdAttributeDto, dsdAttributeInstanceDtos);
        attributeDatasetLevelEditionForm.setDsdAttributeDto(dsdAttributeDto, dsdAttributeInstanceDtos);

        attributeDatasetLevelForm.show();
        attributeDatasetLevelEditionForm.show();
        show();
    }

    private void hideAllForms() {
        attributeDatasetLevelForm.hide();
        attributeDatasetLevelEditionForm.hide();
        hide();
    }
}
