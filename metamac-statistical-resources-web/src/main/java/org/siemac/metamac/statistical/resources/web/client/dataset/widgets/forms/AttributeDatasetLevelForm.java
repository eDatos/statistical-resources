package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeDS;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class AttributeDatasetLevelForm extends AttributeBaseForm {

    @Override
    protected void buildEnumeratedRepresentationForm(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        ExternalItemLinkItem value = new ExternalItemLinkItem(DsdAttributeDS.VALUE, getConstants().datasetAttributeValue());
        if (dsdAttributeInstanceDtos != null && !dsdAttributeInstanceDtos.isEmpty() && dsdAttributeInstanceDtos.get(0).getValue() != null) {
            value.setExternalItem(dsdAttributeInstanceDtos.get(0).getValue().getExternalItemValue());
        } else {
            value.setExternalItem(null);
        }
        setFields(value);
    }

    @Override
    protected void buildNonEnumeratedRepresentationForm(List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        ViewTextItem value = new ViewTextItem(DsdAttributeDS.VALUE, getConstants().datasetAttributeValue());
        if (dsdAttributeInstanceDtos != null && !dsdAttributeInstanceDtos.isEmpty() && dsdAttributeInstanceDtos.get(0).getValue() != null) {
            value.setValue(dsdAttributeInstanceDtos.get(0).getValue().getStringValue());
        }
        setFields(value);
    }
}
