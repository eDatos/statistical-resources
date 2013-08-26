package org.siemac.metamac.statistical.resources.web.client.model.record;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeInstanceDS;
import org.siemac.metamac.web.common.client.widgets.NavigableListGridRecord;

public class DsdAttributeInstanceRecord extends NavigableListGridRecord {

    public DsdAttributeInstanceRecord() {
    }

    public void setStringValue(String value) {
        setAttribute(DsdAttributeInstanceDS.VALUE, value);
    }

    public void setExternalItemValue(ExternalItemDto externalItemDto) {
        setExternalItem(DsdAttributeInstanceDS.VALUE, externalItemDto);
    }

    public void setDsdAttributeInstaceDto(DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        setAttribute(DsdAttributeInstanceDS.DTO, dsdAttributeInstanceDto);
    }

    public DsdAttributeInstanceDto getDsdAttributeInstanceDto() {
        return (DsdAttributeInstanceDto) getAttributeAsObject(DsdAttributeInstanceDS.DTO);
    }
}
