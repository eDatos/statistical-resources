package org.siemac.metamac.statistical_resources.rest.external.service;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface StatisticalResourcesRestExternalCommonService {

    public PagedResult<DatasetVersion> findDatasetVersions(String agencyID, String resourceID, String version, List<ConditionalCriteria> conditionalCriteria, PagingParameter pagingParameter);
    public DatasetVersion retrieveDatasetVersion(String agencyID, String resourceID, String version);

    public PublicationVersion retrievePublicationVersion(String agencyID, String resourceID, String version);
    public QueryVersion retrieveQueryVersion(String agencyID, String resourceID);
}
