package org.siemac.metamac.statistical.resources.web.client.model.record;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DsdAttributeRecord extends ListGridRecord {

    public DsdAttributeRecord() {
    }

    public void setIdentifier(String value) {
        setAttribute(DsdAttributeDS.IDENTIFIER, value);
    }

    public String getIdentifier() {
        return getAttributeAsString(DsdAttributeDS.IDENTIFIER);
    }

    public void setRelationshipType(String value) {
        setAttribute(DsdAttributeDS.RELATIONSHIP_TYPE, value);
    }

    public void setDsdAttributeDto(DsdAttributeDto dsdAttributeDto) {
        setAttribute(DsdAttributeDS.DTO, dsdAttributeDto);
    }

    public DsdAttributeDto getDsdAttributeDto() {
        return (DsdAttributeDto) getAttributeAsObject(DsdAttributeDS.DTO);
    }
}
