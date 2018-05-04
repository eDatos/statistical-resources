package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.multidataset;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Multidataset;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Multidatasets;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

public interface MultidatasetsDo2RestMapperV10 {

    public Multidatasets toMultidatasets(PagedResult<MultidatasetVersion> sources, String agencyID, String resourceID, String query, String orderBy, Integer limit, List<String> selectedLanguages);
    public Multidataset toMultidataset(MultidatasetVersion source, List<String> selectedLanguages, boolean includeMetadata, boolean includeData) throws Exception;
    public Resource toResource(MultidatasetVersion source, List<String> selectedLanguages);
    public Resource toResource(RelatedResourceResult source, List<String> selectedLanguages);
}
