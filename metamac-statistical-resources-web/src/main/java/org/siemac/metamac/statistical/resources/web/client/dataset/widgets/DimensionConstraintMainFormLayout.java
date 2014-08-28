package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.ItemDto;
import org.siemac.metamac.statistical.resources.core.enume.dataset.domain.DimensionTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetConstraintsTabUiHandlers;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.utils.FormUtils;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class DimensionConstraintMainFormLayout extends MainFormLayout {

    private DatasetConstraintsTabUiHandlers uiHandlers;

    private GroupDynamicForm                enumeratedValuesSelectionForm;
    private GroupDynamicForm                enumeratedValuesSelectionEditionForm;

    private GroupDynamicForm                nonEnumeratedValuesSelectionForm;
    private GroupDynamicForm                nonEnumeratedValuesSelectionEditionForm;

    private RegionValueDto                  regionValueDto;

    public DimensionConstraintMainFormLayout() {
        setCanEdit(true);

        // ENUMERATED VALUES SELECTION FORMS

        enumeratedValuesSelectionForm = new GroupDynamicForm(StringUtils.EMPTY);
        addViewCanvas(enumeratedValuesSelectionForm);

        enumeratedValuesSelectionEditionForm = new GroupDynamicForm(StringUtils.EMPTY);
        addEditionCanvas(enumeratedValuesSelectionEditionForm);

        // NON ENUMERATED VALUES SELECTION FORMS

        nonEnumeratedValuesSelectionForm = new GroupDynamicForm(StringUtils.EMPTY);
        addViewCanvas(nonEnumeratedValuesSelectionForm);

        nonEnumeratedValuesSelectionEditionForm = new GroupDynamicForm(StringUtils.EMPTY);
        addEditionCanvas(nonEnumeratedValuesSelectionEditionForm);

        // Bind events

        getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (enumeratedValuesSelectionEditionForm.isVisible()) {
                    if (enumeratedValuesSelectionEditionForm.validate(false)) {
                        // TODO METAMAC-1985
                    }
                } else if (nonEnumeratedValuesSelectionEditionForm.isVisible()) {
                    if (nonEnumeratedValuesSelectionEditionForm.validate(false)) {
                        // TODO METAMAC-1985
                    }
                }
            }
        });
    }

    public void showDimensionConstraints(RegionValueDto regionValueDto, DsdDimensionDto dimension) {
        this.regionValueDto = regionValueDto;
        hideAllForms();
        if (!StringUtils.isBlank(dimension.getCodelistRepresentationUrn())) {
            getUiHandlers().retrieveCodes(dimension);
        } else if (!StringUtils.isBlank(dimension.getConceptSchemeRepresentationUrn())) {
            getUiHandlers().retrieveConcepts(dimension);
        } else if (DimensionTypeEnum.TEMPORAL.equals(dimension.getType())) {
            // TODO METAMAC-1985
        }
    }

    public void setCodes(DsdDimensionDto dsdDimensionDto, ExternalItemDto itemScheme, List<ItemDto> itemDtos) {
        FormUtils.setGroupTitle(dsdDimensionDto.getDimensionId(), enumeratedValuesSelectionForm, enumeratedValuesSelectionEditionForm);
        // TODO METAMAC-1985
        showEnumeratedValuesSelectionForms();
    }

    public void setConcepts(DsdDimensionDto dsdDimensionDto, ExternalItemDto itemScheme, List<ItemDto> itemDtos) {
        FormUtils.setGroupTitle(dsdDimensionDto.getDimensionId(), enumeratedValuesSelectionForm, enumeratedValuesSelectionEditionForm);
        // TODO METAMAC-1985
        showEnumeratedValuesSelectionForms();
    }

    private void showEnumeratedValuesSelectionForms() {
        enumeratedValuesSelectionForm.show();
        enumeratedValuesSelectionEditionForm.show();
        show();
    }

    private void showNonEnumeratedValuesSelectionForms() {
        nonEnumeratedValuesSelectionForm.show();
        nonEnumeratedValuesSelectionEditionForm.show();
        show();
    }

    private void hideAllForms() {
        enumeratedValuesSelectionForm.hide();
        enumeratedValuesSelectionEditionForm.hide();
        nonEnumeratedValuesSelectionForm.hide();
        nonEnumeratedValuesSelectionEditionForm.hide();
        hide();
    }

    public void setUiHandlers(DatasetConstraintsTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public DatasetConstraintsTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }
}
