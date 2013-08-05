package org.siemac.metamac.statistical.resources.web.client.model.record;

import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;

public class LifeCycleResourceRecord extends VersionableResourceRecord {

    public LifeCycleResourceRecord() {
    }

    public void setProcStatus(String value) {
        setAttribute(LifeCycleResourceDS.PROC_STATUS, value);
    }

    public ProcStatusEnum getProcStatusEnum() {
        return ((LifeCycleStatisticalResourceDto) getAttributeAsObject(LifeCycleResourceDS.DTO)).getProcStatus();
    }

    public void setCreationDate(String value) {
        setAttribute(LifeCycleResourceDS.CREATION_DATE, value);
    }

    public void setPublicationDate(String value) {
        setAttribute(LifeCycleResourceDS.PUBLICATION_DATE, value);
    }
}
