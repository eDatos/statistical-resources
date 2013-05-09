package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;

public interface DatasetDo2DtoMapper extends BaseDo2DtoMapper {

    // Datasource
    public DatasourceDto datasourceDoToDto(Datasource datasource);
    public List<DatasourceDto> datasourceDoListToDtoList(List<Datasource> sources);

    // StatisticOfficiality
    public StatisticOfficialityDto statisticOfficialityDo2Dto(StatisticOfficiality source);
    
    // Dataset
    public DatasetDto datasetVersionDoToDto(DatasetVersion source);
}
