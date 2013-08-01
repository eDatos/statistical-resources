package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.query;

import java.util.List;

import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface QueriesDo2RestMapperV10 {

    public Query toQuery(QueryVersion source, List<String> selectedLanguages, boolean includeMetadata, boolean includeData) throws Exception;
    public Resource toResource(QueryVersion source, List<String> selectedLanguages);
}
