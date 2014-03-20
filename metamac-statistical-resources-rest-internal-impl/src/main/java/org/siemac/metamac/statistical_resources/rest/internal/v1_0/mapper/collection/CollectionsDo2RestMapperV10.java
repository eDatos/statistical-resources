package org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.collection;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Collection;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Collections;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public interface CollectionsDo2RestMapperV10 {

    public Collections toCollections(PagedResult<PublicationVersion> sources, String agencyID, String resourceID, String query, String orderBy, Integer limit, List<String> selectedLanguages);
    public Collection toCollection(PublicationVersion source, List<String> selectedLanguages, boolean includeMetadata, boolean includeData) throws Exception;
    public ResourceInternal toResource(PublicationVersion source, List<String> selectedLanguages);
    public ResourceInternal toResource(RelatedResourceResult source, List<String> selectedLanguages);
}
