package org.siemac.metamac.statistical.resources.web.client.model.record;

import org.siemac.metamac.statistical.resources.web.client.model.ds.NameableResourceDS;

public class NameableResourceRecord extends IdentifiableResourceRecord {

    public NameableResourceRecord() {
    }

    public void setTitle(String value) {
        setAttribute(NameableResourceDS.TITLE, value);
    }
}
