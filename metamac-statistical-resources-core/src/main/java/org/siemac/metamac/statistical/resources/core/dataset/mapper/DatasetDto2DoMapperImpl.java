package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasourceRepository;
import org.siemac.metamac.statistical.resources.core.dataset.exception.DatasetVersionNotFoundException;
import org.siemac.metamac.statistical.resources.core.dataset.exception.DatasourceNotFoundException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("datasetDto2DoMapper")
public class DatasetDto2DoMapperImpl extends BaseDto2DoMapperImpl implements DatasetDto2DoMapper {

    @Autowired
    private DatasetRepository        datasetRepository;

    @Autowired
    private DatasourceRepository     datasourceRepository;

    @Autowired
    private DatasetVersionRepository datasetVersionRepository;

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
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.DATASET_VERSION_NOT_FOUND)
                        .withMessageParameters(ServiceExceptionParameters.DATASET_VERSION).withLoggedLevel(ExceptionLevelEnum.ERROR).build();
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
        siemacMetadataStatisticalResourceDtoToDo(source, target.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION);

        // Non modifiable after creation

        // TODO: It's not clear when coverages are manual, geoDimension attribute missing? same for timeDimension

        externalItemDtoListToDoList(source.getGeographicGranularities(), target.getGeographicGranularities(), ServiceExceptionParameters.DATASET_VERSION__GEOGRAPHIC_GRANULARITIES);
        externalItemDtoListToDoList(source.getTemporalGranularities(), target.getTemporalGranularities(), ServiceExceptionParameters.DATASET_VERSION__TEMPORAL_GRANULARITIES);
        externalItemDtoListToDoList(source.getStatisticalUnit(), target.getStatisticalUnit(), ServiceExceptionParameters.DATASET_VERSION__STATISTICAL_UNIT);

        // TODO: when measures can be filled?

        target.setRelatedDsd(externalItemDtoToDo(source.getRelatedDsd(), target.getRelatedDsd(), ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD));

        // TODO: how do i know if date_next_update can be updated?
        target.setUpdateFrequency(externalItemDtoToDo(source.getUpdateFrequency(), target.getUpdateFrequency(), ServiceExceptionParameters.DATASET_VERSION__UPDATE_FREQUENCY));
        // TODO statistic_officiality

        return target;
    }
}
