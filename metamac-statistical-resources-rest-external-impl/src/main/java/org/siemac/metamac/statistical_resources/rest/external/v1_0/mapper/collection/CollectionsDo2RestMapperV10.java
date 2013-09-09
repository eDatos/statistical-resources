package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.collection;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Collection;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Collections;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public interface CollectionsDo2RestMapperV10 {

    public Collections toCollections(PagedResult<PublicationVersion> sources, String agencyID, String resourceID, String query, String orderBy, Integer limit, List<String> selectedLanguages);
    public Collection toCollection(PublicationVersion source, List<String> selectedLanguages, boolean includeMetadata, boolean includeData) throws Exception;
    public Resource toResource(PublicationVersion source, List<String> selectedLanguages);
    public Resource toResource(RelatedResourceResult source, List<String> selectedLanguages);
}
