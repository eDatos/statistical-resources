package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;

public interface DatasetDto2DoMapper extends BaseDto2DoMapper {

    public Datasource datasourceDtoToDo(DatasourceDto datasourceDto) throws MetamacException;

    public DatasetVersion datasetVersionDtoToDo(DatasetVersionDto source) throws MetamacException;

    public void checkOptimisticLocking(DatasetVersionBaseDto datasetVersionDto) throws MetamacException;
}
