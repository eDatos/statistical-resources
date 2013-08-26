package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;

import com.smartgwt.client.widgets.form.fields.FormItem;

public abstract class AttributeBaseForm extends GroupDynamicForm {

    protected DatasetAttributesTabUiHandlers uiHandlers;

    protected DsdAttributeDto                dsdAttributeDto;

    public void setAttribute(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        clearForm();

        this.dsdAttributeDto = dsdAttributeDto;
        setGroupTitle(dsdAttributeDto.getIdentifier());

        if (CommonUtils.hasEnumeratedRepresentation(dsdAttributeDto)) {
            buildEnumeratedRepresentationForm(dsdAttributeDto, dsdAttributeInstanceDto);
        } else if (CommonUtils.hasNonEnumeratedRepresentation(dsdAttributeDto)) {
            buildNonEnumeratedRepresentationForm(dsdAttributeInstanceDto);
        }
    }

    public AttributeBaseForm() {
        super("Attribute");
    }

    protected void clearForm() {
        setFields(new FormItem[0]);
        clearValues();
        clearErrors(true);
    }

    public DatasetAttributesTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(DatasetAttributesTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    protected abstract void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto);
    protected abstract void buildNonEnumeratedRepresentationForm(DsdAttributeInstanceDto dsdAttributeInstanceDto);
}
