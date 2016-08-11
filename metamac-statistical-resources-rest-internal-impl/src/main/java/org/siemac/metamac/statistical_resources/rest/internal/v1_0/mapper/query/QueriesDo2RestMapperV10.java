package org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.query;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Queries;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Query;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface QueriesDo2RestMapperV10 {

    public Queries toQueries(PagedResult<QueryVersion> sources, String agencyID, String query, String orderBy, Integer limit, List<String> selectedLanguages);
    public Query toQuery(QueryVersion source, List<String> selectedLanguages, boolean includeMetadata, boolean includeData) throws Exception;
    public ResourceLink toQuerySelfLink(LifeCycleStatisticalResourceDto source);
    public ResourceLink toQuerySelfLink(LifeCycleStatisticalResourceBaseDto source);
    public ResourceInternal toResource(QueryVersion source, List<String> selectedLanguages);
    public ResourceInternal toResource(RelatedResourceResult source, List<String> selectedLanguages);
}
