package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class QueryVersionMock extends QueryVersion {

    private static final long serialVersionUID = 3915367587722854680L;

    @Override
    public LifeCycleStatisticalResource getLifeCycleStatisticalResource() {
        if (super.getLifeCycleStatisticalResource() == null) {
            setLifeCycleStatisticalResource(new LifeCycleStatisticalResource());
        }
        return super.getLifeCycleStatisticalResource();
    }

    public void setStatisticalOperationCode(String operationCode) {
        getLifeCycleStatisticalResource().setStatisticalOperation(StatisticalResourcesPersistedDoMocks.mockStatisticalOperationInstanceExternalItem(operationCode));
    }

    public void setMaintainerCode(String maintainerCode) {
        getLifeCycleStatisticalResource().setMaintainer(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem(maintainerCode));
    }

    public void setLastVersion(boolean lastVersion) {
        getLifeCycleStatisticalResource().setLastVersion(lastVersion);
    }

    public void setVersionLogic(String version) {
        getLifeCycleStatisticalResource().setVersionLogic(version);
    }

}