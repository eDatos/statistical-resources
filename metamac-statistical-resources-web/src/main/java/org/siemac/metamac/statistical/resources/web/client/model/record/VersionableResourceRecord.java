package org.siemac.metamac.statistical.resources.web.client.model.record;

import org.siemac.metamac.statistical.resources.web.client.model.ds.VersionableResourceDS;

public class VersionableResourceRecord extends NameableResourceRecord {

    public VersionableResourceRecord() {
    }

    public void setVersionLogic(String value) {
        setAttribute(VersionableResourceDS.VERSION, value);
    }
}
