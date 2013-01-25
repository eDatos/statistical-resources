package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;

public interface DatasetDto2DoMapper {

    public Datasource datasourceDtoToDo(DatasourceDto datasourceDto) throws MetamacException;

    public DatasetVersion datasetVersionDtoToDo(DatasetDto source) throws MetamacException;
}
