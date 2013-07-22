package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public interface DatasetsDo2RestMapperV10 {

    public Dataset toDataset(DatasetVersion source, Map<String, List<String>> dimensions, List<String> selectedLanguages, boolean includeMetadata, boolean includeData) throws Exception;
    public Resource toResource(DatasetVersion source, List<String> selectedLanguages);
}
