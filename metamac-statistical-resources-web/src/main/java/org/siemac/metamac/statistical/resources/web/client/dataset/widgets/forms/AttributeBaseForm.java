package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;

import com.smartgwt.client.widgets.form.fields.FormItem;

public abstract class AttributeBaseForm extends GroupDynamicForm {

    protected DatasetAttributesTabUiHandlers uiHandlers;

    protected DsdAttributeDto                dsdAttributeDto;

    public void setDsdAttributeDto(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        clearForm();

        this.dsdAttributeDto = dsdAttributeDto;
        setGroupTitle(dsdAttributeDto.getIdentifier());

        if (CommonUtils.hasEnumeratedRepresentation(dsdAttributeDto)) {
            buildEnumeratedRepresentationForm(dsdAttributeDto, dsdAttributeInstanceDtos);
        } else if (CommonUtils.hasNonEnumeratedRepresentation(dsdAttributeDto)) {
            buildNonEnumeratedRepresentationForm(dsdAttributeInstanceDtos);
        }
    }

    public AttributeBaseForm() {
        super(StringUtils.EMPTY);
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

    protected abstract void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos);
    protected abstract void buildNonEnumeratedRepresentationForm(List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos);
}
