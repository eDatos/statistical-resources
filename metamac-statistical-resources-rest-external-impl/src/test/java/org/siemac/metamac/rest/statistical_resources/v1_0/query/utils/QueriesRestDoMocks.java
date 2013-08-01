package org.siemac.metamac.rest.statistical_resources.v1_0.query.utils;

import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class QueriesRestDoMocks {

    public StatisticalResourcesPersistedDoMocks coreDoMocks;

    public QueriesRestDoMocks(StatisticalResourcesPersistedDoMocks coreDoMocks) {
        this.coreDoMocks = coreDoMocks;
    }

    public QueryVersion mockQueryVersion(String agencyID, String resourceID, String version) {
        QueryVersion target = mockQueryVersionBasic(agencyID, resourceID, version);
        return target;
    }

    public QueryVersion mockQueryVersionBasic(String agencyID, String resourceID, String version) {
        QueryVersion target = coreDoMocks.mockQueryVersionWithGeneratedDatasetVersion();
        target.getLifeCycleStatisticalResource().setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Query=" + agencyID + ":" + resourceID + "(" + version + ")");
        target.getLifeCycleStatisticalResource().getMaintainer().setCodeNested(agencyID);
        target.getLifeCycleStatisticalResource().setCode(resourceID);
        target.getLifeCycleStatisticalResource().setVersionLogic(version);
        return target;
    }

}