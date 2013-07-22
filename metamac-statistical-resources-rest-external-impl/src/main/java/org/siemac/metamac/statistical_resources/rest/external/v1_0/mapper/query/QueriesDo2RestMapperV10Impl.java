package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.query;

import java.util.List;

import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.BaseDo2RestMapperV10Impl;
import org.springframework.stereotype.Component;

@Component
public class QueriesDo2RestMapperV10Impl extends BaseDo2RestMapperV10Impl implements QueriesDo2RestMapperV10 {

    @Override
    public Resource toResource(QueryVersion source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getLifeCycleStatisticalResource().getCode());
        target.setUrn(source.getLifeCycleStatisticalResource().getUrn());
        target.setKind(RestExternalConstants.KIND_QUERY);
        target.setSelfLink(toQuerySelfLink(source));
        target.setName(toInternationalString(source.getLifeCycleStatisticalResource().getTitle(), selectedLanguages));
        return target;
    }

    private ResourceLink toQuerySelfLink(QueryVersion source) {
        return toResourceLink(RestExternalConstants.KIND_QUERY, toQueryLink(source));
    }

    private String toQueryLink(QueryVersion source) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_QUERIES;
        String agencyID = source.getLifeCycleStatisticalResource().getMaintainer().getCodeNested();
        String resourceID = source.getLifeCycleStatisticalResource().getCode();
        String version = null; // no devolver versión
        return toResourceLink(resourceSubpath, agencyID, resourceID, version);
    }
}