package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;

public interface DatasetDo2DtoMapper {

    public DatasourceDto datasourceDoToDto(Datasource datasource);
    public List<DatasourceDto> datasourceDoListToDtoList(List<Datasource> sources);

}
