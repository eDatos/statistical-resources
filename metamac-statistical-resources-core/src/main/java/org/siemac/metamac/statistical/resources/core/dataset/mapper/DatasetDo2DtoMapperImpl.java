package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;

@org.springframework.stereotype.Component("datasetDo2DtoMapper")
public class DatasetDo2DtoMapperImpl extends BaseDo2DtoMapperImpl implements DatasetDo2DtoMapper {


    @Override
    public DatasourceDto datasourceDoToDto(Datasource source) {
        if (source == null) {
            return null;
        }
        DatasourceDto target = new DatasourceDto();
        datasourceDoToDto(source, target);
        return target;
    }
    
    @Override
    public List<DatasourceDto> datasourceDoListToDtoList(List<Datasource> sources) {
        List<DatasourceDto> targets = new ArrayList<DatasourceDto>();
        for (Datasource source : sources) {
            targets.add(datasourceDoToDto(source));
        }
        return targets;
    }

    private DatasourceDto datasourceDoToDto(Datasource source, DatasourceDto target) {
        if (source == null) {
            return null;
        }

        // Hierarchy
        identifiableStatisticalResourceDoToDto(source.getIdentifiableStatisticalResource(), target);

        // Identity
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setVersion(source.getVersion());
        
        // Other 
        target.setDatasetVersionUrn(source.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn());

        return target;
    }

}
