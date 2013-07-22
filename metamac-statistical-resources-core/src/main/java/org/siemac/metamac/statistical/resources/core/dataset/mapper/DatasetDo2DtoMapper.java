package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;

public interface DatasetDo2DtoMapper extends BaseDo2DtoMapper {

    // Datasource
    public DatasourceDto datasourceDoToDto(Datasource datasource);
    public List<DatasourceDto> datasourceDoListToDtoList(List<Datasource> sources);

    // StatisticOfficiality
    public StatisticOfficialityDto statisticOfficialityDo2Dto(StatisticOfficiality source);

    // Dataset
    public RelatedResourceDto datasetVersionDoToDatasetRelatedResourceDto(DatasetVersion source) throws MetamacException;
    
    // Dataset version
    public DatasetVersionDto datasetVersionDoToDto(DatasetVersion source) throws MetamacException;
    
    //Code dimension
    public CodeItemDto codeDimensionDoToCodeItemDto(CodeDimension codeDimension) throws MetamacException;
    public List<CodeItemDto> codeDimensionDoListToCodeItemDtoList(List<CodeDimension> codeDimensions) throws MetamacException;
}
