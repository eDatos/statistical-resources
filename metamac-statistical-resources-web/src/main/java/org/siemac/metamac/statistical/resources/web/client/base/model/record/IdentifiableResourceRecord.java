package org.siemac.metamac.statistical.resources.web.client.base.model.record;

import org.siemac.metamac.statistical.resources.web.client.model.ds.IdentifiableResourceDS;
import org.siemac.metamac.web.common.client.widgets.NavigableListGridRecord;

public class IdentifiableResourceRecord extends NavigableListGridRecord {

    public IdentifiableResourceRecord() {
    }

    public void setId(Long value) {
        setAttribute(IdentifiableResourceDS.ID, value);
    }

    public void setCode(String value) {
        setAttribute(IdentifiableResourceDS.CODE, value);
    }

    public void setUrn(String value) {
        setAttribute(IdentifiableResourceDS.URN, value);
    }

    public String getUrn() {
        return getAttributeAsString(IdentifiableResourceDS.URN);
    }
}
