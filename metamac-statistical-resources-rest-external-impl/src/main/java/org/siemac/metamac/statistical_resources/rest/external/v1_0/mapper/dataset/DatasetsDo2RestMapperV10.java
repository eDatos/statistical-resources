package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public interface DatasetsDo2RestMapperV10 {

    public org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset toDataset(DatasetVersion source) throws MetamacException;
}
