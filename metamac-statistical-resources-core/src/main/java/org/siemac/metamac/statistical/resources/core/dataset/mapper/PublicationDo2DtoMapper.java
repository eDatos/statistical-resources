package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;

public interface PublicationDo2DtoMapper {

    public DatasourceDto datasourceDoToDto(Datasource datasource);
    public List<DatasourceDto> datasourceDoListToDtoList(List<Datasource> sources);

    public DatasetDto datasetVersionDoToDto(DatasetVersion source);

}
