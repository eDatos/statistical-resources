package org.siemac.metamac.statistical.resources.web.client.model.record;

import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeDS;

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
}
