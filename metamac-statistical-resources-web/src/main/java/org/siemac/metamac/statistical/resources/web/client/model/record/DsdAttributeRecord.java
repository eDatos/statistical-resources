package org.siemac.metamac.statistical.resources.web.client.model.record;

import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeDS;
import org.siemac.metamac.statistical.resources.web.shared.DTO.DsdAttributeDto;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DsdAttributeRecord extends ListGridRecord {

    public DsdAttributeRecord() {
    }

    public void setCode(String value) {
        setAttribute(DsdAttributeDS.CODE, value);
    }

    public String getCode() {
        return getAttributeAsString(DsdAttributeDS.CODE);
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
