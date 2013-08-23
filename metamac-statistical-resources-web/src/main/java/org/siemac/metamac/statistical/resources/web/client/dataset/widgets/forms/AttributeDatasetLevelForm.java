package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeInstanceDS;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class AttributeDatasetLevelForm extends AttributeBaseForm {

    @Override
    protected void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        ExternalItemLinkItem value = new ExternalItemLinkItem(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue());
        if (dsdAttributeInstanceDto.getValue() != null) {
            value.setExternalItem(dsdAttributeInstanceDto.getValue().getExternalItemValue());
        } else {
            value.setExternalItem(null);
        }
        setFields(value);
    }

    @Override
    protected void buildNonEnumeratedRepresentationForm(DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        ViewTextItem value = new ViewTextItem(DsdAttributeInstanceDS.VALUE, getConstants().datasetAttributeValue());
        if (dsdAttributeInstanceDto.getValue() != null) {
            value.setValue(dsdAttributeInstanceDto.getValue().getStringValue());
        }
        setFields(value);
    }
}
