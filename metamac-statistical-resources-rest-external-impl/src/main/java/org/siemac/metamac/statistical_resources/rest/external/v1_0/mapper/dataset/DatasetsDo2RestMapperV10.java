package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset;

import java.util.Map;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public interface DatasetsDo2RestMapperV10 {

    public org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset toDataset(DatasetVersion source, Map<String, DsdDimension> dimensionsById, DataStructure dataStructure)
            throws MetamacException;
}
