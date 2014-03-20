package org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.dataset;

import java.util.List;
import java.util.Map;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Dataset;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Datasets;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public interface DatasetsDo2RestMapperV10 {

    public Datasets toDatasets(PagedResult<DatasetVersion> sources, String agencyID, String resourceID, String query, String orderBy, Integer limit, List<String> selectedLanguages);
    public Dataset toDataset(DatasetVersion source, Map<String, List<String>> dimensions, List<String> selectedLanguages, boolean includeMetadata, boolean includeData) throws Exception;
    public ResourceInternal toResource(DatasetVersion source, List<String> selectedLanguages);
    public ResourceInternal toResourceAsLatest(DatasetVersion source, List<String> selectedLanguages);
    public ResourceInternal toResource(RelatedResourceResult source, List<String> selectedLanguages);
}
