package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("datasetDo2DtoMapper")
public class DatasetDo2DtoMapperImpl extends BaseDo2DtoMapperImpl implements DatasetDo2DtoMapper {

    @Autowired
    private TaskService taskService;
    // ---------------------------------------------------------------------------------------------------------
    // DATASOURCES
    // ---------------------------------------------------------------------------------------------------------

    @Override
    public DatasourceDto datasourceDoToDto(Datasource source) throws MetamacException {
        if (source == null) {
            return null;
        }
        DatasourceDto target = new DatasourceDto();
        datasourceDoToDto(source, target);
        return target;
    }

    @Override
    public List<DatasourceDto> datasourceDoListToDtoList(List<Datasource> sources) throws MetamacException {
        List<DatasourceDto> targets = new ArrayList<DatasourceDto>();
        for (Datasource source : sources) {
            targets.add(datasourceDoToDto(source));
        }
        return targets;
    }

    private DatasourceDto datasourceDoToDto(Datasource source, DatasourceDto target) throws MetamacException {
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

    // ---------------------------------------------------------------------------------------------------------
    // DATASETS
    // ---------------------------------------------------------------------------------------------------------


    @Override
    public RelatedResourceDto datasetVersionDoToDatasetRelatedResourceDto(DatasetVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }
        RelatedResourceDto target = new RelatedResourceDto();
        datasetVersionDoToDatasetRelatedResourceDto(source, target);
        return target;
    }
    
    private RelatedResourceDto datasetVersionDoToDatasetRelatedResourceDto(DatasetVersion source, RelatedResourceDto target) {
        if (source == null) {
            return null;
        }

        // Identity
        target.setId(source.getDataset().getId());
        target.setUuid(source.getDataset().getUuid());
        target.setVersion(source.getDataset().getVersion());

        // Type
        target.setType(TypeRelatedResourceEnum.DATASET);
        
        // Identifiable Fields
        target.setCode(source.getDataset().getIdentifiableStatisticalResource().getCode());
        target.setCodeNested(null);
        target.setUrn(source.getDataset().getIdentifiableStatisticalResource().getUrn());
        
        // Nameable Fields
        target.setTitle(internationalStringDoToDto(source.getSiemacMetadataStatisticalResource().getTitle()));
        
        return target;
    }
    

    // ---------------------------------------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ---------------------------------------------------------------------------------------------------------

    @Override
    public DatasetVersionDto datasetVersionDoToDto(ServiceContext ctx, DatasetVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }
        DatasetVersionDto target = new DatasetVersionDto();
        datasetVersionDoToDto(ctx, source, target);
        return target;
    }

    private DatasetVersionDto datasetVersionDoToDto(ServiceContext ctx, DatasetVersion source, DatasetVersionDto target) throws MetamacException {
        if (source == null) {
            return null;
        }

        // Hierarchy
        siemacMetadataStatisticalResourceDoToDto(source.getSiemacMetadataStatisticalResource(), target);

        // Identity
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setVersion(source.getVersion());

        // Other
        target.getGeographicCoverage().clear();
        target.getGeographicCoverage().addAll(externalItemDoCollectionToDtoCollection(source.getGeographicCoverage()));

        target.getTemporalCoverage().clear();
        target.getTemporalCoverage().addAll(temporalCodeDoCollectionToDtoCollection(source.getTemporalCoverage()));

        target.getMeasureCoverage().clear();
        target.getMeasureCoverage().addAll(externalItemDoCollectionToDtoCollection(source.getMeasureCoverage()));

        target.getGeographicGranularities().clear();
        target.getGeographicGranularities().addAll(externalItemDoCollectionToDtoCollection(source.getGeographicGranularities()));

        target.getTemporalGranularities().clear();
        target.getTemporalGranularities().addAll(externalItemDoCollectionToDtoCollection(source.getTemporalGranularities()));

        target.getStatisticalUnit().clear();
        target.getStatisticalUnit().addAll(externalItemDoCollectionToDtoCollection(source.getStatisticalUnit()));

        target.setDateStart(dateDoToDto(source.getDateStart()));
        target.setDateEnd(dateDoToDto(source.getDateEnd()));

        target.setRelatedDsd(externalItemDoToDto(source.getRelatedDsd()));

        target.setFormatExtentDimensions(source.getFormatExtentDimensions());
        target.setFormatExtentObservations(source.getFormatExtentObservations());

        target.setDateNextUpdate(dateDoToDto(source.getDateNextUpdate()));
        target.setUpdateFrequency(externalItemDoToDto(source.getUpdateFrequency()));
        target.setStatisticOfficiality(statisticOfficialityDo2Dto(source.getStatisticOfficiality()));
        target.setBibliographicCitation(internationalStringDoToDto(source.getBibliographicCitation()));
        
        target.setIsTaskInBackground(taskService.existImportationTaskInDataset(ctx, source.getSiemacMetadataStatisticalResource().getUrn()));

        return target;
    }

    // ---------------------------------------------------------------------------------------------------------
    // STATISTIC OFFICIALITY
    // ---------------------------------------------------------------------------------------------------------

    @Override
    public StatisticOfficialityDto statisticOfficialityDo2Dto(StatisticOfficiality source) {
        if (source == null) {
            return null;
        }

        StatisticOfficialityDto target = new StatisticOfficialityDto();

        // Identity
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setVersion(source.getVersion());

        // Other
        target.setIdentifier(source.getIdentifier());
        target.setDescription(internationalStringDoToDto(source.getDescription()));

        return target;
    }
    
    @Override
    public List<StatisticOfficialityDto> statisticOfficialityDoList2DtoList(List<StatisticOfficiality> sources) throws MetamacException {
        List<StatisticOfficialityDto> targets = new ArrayList<StatisticOfficialityDto>();
        for (StatisticOfficiality source : sources) {
            targets.add(statisticOfficialityDo2Dto(source));
        }
        return targets;
    }

    // ---------------------------------------------------------------------------------------------------------
    // CODE ITEM
    // ---------------------------------------------------------------------------------------------------------

    @Override
    public CodeItemDto codeDimensionDoToCodeItemDto(CodeDimension source) throws MetamacException {
        if (source == null) {
            return null;
        }
        CodeItemDto dto = new CodeItemDto();
        dto.setCode(source.getIdentifier());
        dto.setTitle(source.getTitle());
        return dto;
    }

    // ---------------------------------------------------------------------------------------------------------
    // CODE DIMENSION
    // ---------------------------------------------------------------------------------------------------------

    @Override
    public List<CodeItemDto> codeDimensionDoListToCodeItemDtoList(List<CodeDimension> sources) throws MetamacException {
        List<CodeItemDto> targets = new ArrayList<CodeItemDto>();
        for (CodeDimension source : sources) {
            targets.add(codeDimensionDoToCodeItemDto(source));
        }
        return targets;
    }
}
