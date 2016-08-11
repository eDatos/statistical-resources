package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.CategorisationDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DimensionRepresentationMappingDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;

public interface DatasetDo2DtoMapper extends BaseDo2DtoMapper {

    // Datasource
    public DatasourceDto datasourceDoToDto(Datasource datasource) throws MetamacException;
    public List<DatasourceDto> datasourceDoListToDtoList(List<Datasource> sources) throws MetamacException;
    public DimensionRepresentationMappingDto dimensionRepresentationMappingDoToDto(DimensionRepresentationMapping source) throws MetamacException;
    public List<DimensionRepresentationMappingDto> dimensionRepresentationMappingDoToDtoList(List<DimensionRepresentationMapping> sources) throws MetamacException;

    // StatisticOfficiality
    public StatisticOfficialityDto statisticOfficialityDo2Dto(StatisticOfficiality source) throws MetamacException;
    public List<StatisticOfficialityDto> statisticOfficialityDoList2DtoList(List<StatisticOfficiality> sources) throws MetamacException;

    // Dataset
    public RelatedResourceDto datasetVersionDoToDatasetRelatedResourceDto(DatasetVersion source) throws MetamacException;
    public RelatedResourceDto datasetVersionDoToDatasetVersionRelatedResourceDto(DatasetVersion source) throws MetamacException;

    // Dataset version
    public DatasetVersionDto datasetVersionDoToDto(ServiceContext ctx, DatasetVersion source) throws MetamacException;
    public DatasetVersionBaseDto datasetVersionDoToBaseDto(ServiceContext ctx, DatasetVersion source) throws MetamacException;

    // Code dimension
    public CodeItemDto codeDimensionDoToCodeItemDto(CodeDimension codeDimension) throws MetamacException;
    public List<CodeItemDto> codeDimensionDoListToCodeItemDtoList(List<CodeDimension> codeDimensions) throws MetamacException;

    // Categorisation
    public CategorisationDto categorisationDoToDto(Categorisation source) throws MetamacException;
    public List<CategorisationDto> categorisationDoListToDtoList(List<Categorisation> sources) throws MetamacException;
}
