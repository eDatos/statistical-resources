package org.siemac.metamac.statistical.resources.web.client.base.model.record;

import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;

public class LifeCycleResourceRecord extends VersionableResourceRecord {

    public LifeCycleResourceRecord() {
    }

    public void setProcStatus(String value) {
        setAttribute(LifeCycleResourceDS.PROC_STATUS, value);
    }
}
