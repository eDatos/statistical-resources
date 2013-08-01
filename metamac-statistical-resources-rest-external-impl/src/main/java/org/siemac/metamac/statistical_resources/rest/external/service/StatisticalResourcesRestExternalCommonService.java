package org.siemac.metamac.statistical_resources.rest.external.service;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface StatisticalResourcesRestExternalCommonService {

    public DatasetVersion retrieveDatasetVersion(String agencyID, String resourceID, String version);
    public PublicationVersion retrievePublicationVersion(String agencyID, String resourceID, String version);
    public QueryVersion retrieveQueryVersion(String agencyID, String resourceID);
}
