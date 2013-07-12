package org.siemac.metamac.statistical_resources.rest.external.service;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public interface StatisticalResourcesRestExternalCommonService {

    public DatasetVersion retrieveDatasetVersion(String agencyID, String resourceID, String version);
}
