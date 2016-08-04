package org.siemac.metamac.statistical.resources.web.client.model.record;

import org.siemac.metamac.statistical.resources.core.dto.datasets.TemporalCodeDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.TemporalCodeDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TemporalCodeRecord extends ListGridRecord {

    public TemporalCodeRecord() {
        super();
    }

    public void setCode(String value) {
        setAttribute(TemporalCodeDS.CODE, value);
    }

    public String getCode() {
        return getAttributeAsString(TemporalCodeDS.CODE);
    }

    public void setTitle(String value) {
        setAttribute(TemporalCodeDS.TITLE, value);
    }

    public String getTitle() {
        return getAttributeAsString(TemporalCodeDS.TITLE);
    }

    public void setTemporalCodeDto(TemporalCodeDto codeItemDto) {
        setAttribute(TemporalCodeDS.DTO, codeItemDto);
    }

    public TemporalCodeDto getTemporalCodeDto() {
        return (TemporalCodeDto) getAttributeAsObject(TemporalCodeDS.DTO);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof TemporalCodeDto) {
            TemporalCodeDto other = (TemporalCodeDto) obj;
            if (other.getIdentifier() != null) {
                return other.getIdentifier().equals(getCode());
            } else if (getCode() != null) {
                return getCode().equals(other.getIdentifier());
            }
        }
        return false;
    }
}
