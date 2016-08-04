package org.siemac.metamac.statistical.resources.web.shared.criteria;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

public class LifeCycleStatisticalResourceWebCriteria extends VersionableStatisticalResourceWebCriteria {

    private static final long serialVersionUID = 1L;

    private ProcStatusEnum    procStatus;

    public LifeCycleStatisticalResourceWebCriteria() {
        super();
    }

    public LifeCycleStatisticalResourceWebCriteria(String criteria) {
        super(criteria);
    }

    public ProcStatusEnum getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(ProcStatusEnum procStatus) {
        this.procStatus = procStatus;
    }
}
