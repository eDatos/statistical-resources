package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dataset.checks.DatasetMetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasourceRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficialityRepository;
import org.siemac.metamac.statistical.resources.core.dataset.exception.DatasetVersionNotFoundException;
import org.siemac.metamac.statistical.resources.core.dataset.exception.DatasourceNotFoundException;
import org.siemac.metamac.statistical.resources.core.dataset.exception.StatisticOfficialityNotFoundException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("datasetDto2DoMapper")
public class DatasetDto2DoMapperImpl extends BaseDto2DoMapperImpl implements DatasetDto2DoMapper {

    @Autowired
    private DatasourceRepository           datasourceRepository;

    @Autowired
    private DatasetVersionRepository       datasetVersionRepository;

    @Autowired
    private StatisticOfficialityRepository statisticOfficialityRepository;

    @Override
    public Datasource datasourceDtoToDo(DatasourceDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity.
        Datasource target = null;
        if (source.getId() == null) {
            target = new Datasource();
            target.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        } else {
            try {
                target = datasourceRepository.findById(source.getId());
            } catch (DatasourceNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.DATASOURCE_NOT_FOUND).withMessageParameters(ServiceExceptionParameters.DATASOURCE)
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
        }

        datasourceDtoToDo(source, target);

        return target;
    }

    private Datasource datasourceDtoToDo(DatasourceDto source, Datasource target) throws MetamacException {
        if (target == null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.DATASOURCE).build();
        }

        // Hierarchy
        identifiableStatisticalResourceDtoToDo(source, target.getIdentifiableStatisticalResource(), ServiceExceptionParameters.DATASOURCE);

        // Non modifiable after creation

        return target;
    }

    @Override
    public DatasetVersion datasetVersionDtoToDo(DatasetDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity.
        DatasetVersion target = null;
        if (source.getId() == null) {
            target = new DatasetVersion();
            target.setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        } else {
            try {
                target = datasetVersionRepository.findById(source.getId());
            } catch (DatasetVersionNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.DATASET_VERSION_NOT_FOUND).withMessageParameters(source.getUrn())
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
        }

        datasetVersionDtoToDo(source, target);

        return target;
    }

    private DatasetVersion datasetVersionDtoToDo(DatasetDto source, DatasetVersion target) throws MetamacException {
        if (target == null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.DATASET_VERSION).build();
        }

        // Hierarchy
        siemacMetadataStatisticalResourceDtoToDo(source, target.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE);

        // modifiable
        externalItemDtoListToDoList(source.getGeographicGranularities(), target.getGeographicGranularities(), ServiceExceptionParameters.DATASET_VERSION__GEOGRAPHIC_GRANULARITIES);
        externalItemDtoListToDoList(source.getTemporalGranularities(), target.getTemporalGranularities(), ServiceExceptionParameters.DATASET_VERSION__TEMPORAL_GRANULARITIES);
        externalItemDtoListToDoList(source.getStatisticalUnit(), target.getStatisticalUnit(), ServiceExceptionParameters.DATASET_VERSION__STATISTICAL_UNIT);

        target.setRelatedDsdChanged(false);
        if (source.getRelatedDsd() != null && DatasetMetadataEditionChecks.canDsdBeEdited(target.getId(), target.getSiemacMetadataStatisticalResource().getProcStatus())) {
            datasetVersionDtoRelatedDsdToDo(source, target);
        }
            
        if (DatasetMetadataEditionChecks.canDateNextUpdateBeEdited()) {
            target.setDateNextUpdate(dateDtoToDo(source.getDateNextUpdate()));
        }

        target.setUpdateFrequency(externalItemDtoToDo(source.getUpdateFrequency(), target.getUpdateFrequency(), ServiceExceptionParameters.DATASET_VERSION__UPDATE_FREQUENCY));
        target.setStatisticOfficiality(statisticOfficialityDtoToDo(source.getStatisticOfficiality(), target.getStatisticOfficiality(), ServiceExceptionParameters.DATASET_VERSION__STATISTIC_OFFICIALITY));

        return target;
    }

    //source.relatedDsd is supposed not to be null
    private void datasetVersionDtoRelatedDsdToDo(DatasetDto source, DatasetVersion target) throws MetamacException {
        if (target.getRelatedDsd() == null) {
            target.setRelatedDsd(externalItemDtoToDo(source.getRelatedDsd(), target.getRelatedDsd(), ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD));
        } else {
            boolean changedDsd = false;
            if (areDifferentDsd(target.getRelatedDsd(), source.getRelatedDsd())) {
                if (DatasetMetadataEditionChecks.canDsdBeReplacedByAnyOtherDsd(target.getId(), target.getSiemacMetadataStatisticalResource().getVersionLogic(), target.getSiemacMetadataStatisticalResource().getProcStatus())) {
                    changedDsd = true;
                }
            } else if (areSameDsdDifferentVersion(target.getRelatedDsd(), source.getRelatedDsd())) {
                changedDsd = true;
            }
            target.setRelatedDsdChanged(changedDsd);
            if (changedDsd) {
                target.setRelatedDsd(externalItemDtoToDo(source.getRelatedDsd(), target.getRelatedDsd(), ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD));
            }
        }
    }
    
    // Check if its the same dsd
    private boolean areDifferentDsd(ExternalItem dsd, ExternalItemDto dsdDto) {
        return !dsd.getCode().equals(dsdDto.getCode());
    }
    
    private boolean areSameDsdDifferentVersion(ExternalItem dsd, ExternalItemDto dsdDto) {
        return dsd.getCode().equals(dsdDto.getCode()) && !dsd.getUrn().equals(dsdDto.getUrn());
    }

    public StatisticOfficiality statisticOfficialityDtoToDo(StatisticOfficialityDto source, StatisticOfficiality target, String metadataName) throws MetamacException {
        if (source == null) {
            return null;
        }

        if (target == null) {
            try {
                target = statisticOfficialityRepository.findById(source.getId());
            } catch (StatisticOfficialityNotFoundException e) {
                throw new MetamacException(ServiceExceptionType.STATISTIC_OFFICIALITY_NOT_FOUND, source.getId());
            }
        }

        return target;
    }
}
