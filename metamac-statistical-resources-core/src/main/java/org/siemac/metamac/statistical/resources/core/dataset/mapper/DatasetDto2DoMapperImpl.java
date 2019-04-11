package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.base.checks.MetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.checks.DatasetMetadataEditionChecks;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasourceRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficialityRepository;
import org.siemac.metamac.statistical.resources.core.dataset.exception.DatasetVersionNotFoundException;
import org.siemac.metamac.statistical.resources.core.dataset.exception.DatasourceNotFoundException;
import org.siemac.metamac.statistical.resources.core.dataset.exception.StatisticOfficialityNotFoundException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.CategorisationDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
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
    public void checkOptimisticLocking(DatasetVersionBaseDto source) throws MetamacException {
        if (source != null && source.getId() != null) {
            try {
                DatasetVersion target = datasetVersionRepository.findById(source.getId());
                if (target.getId() != null) {
                    OptimisticLockingUtils.checkVersion(target.getSiemacMetadataStatisticalResource().getVersion(), source.getOptimisticLockingVersion());
                }
            } catch (DatasetVersionNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.DATASET_VERSION_NOT_FOUND).withMessageParameters(source.getUrn())
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
        }
    }

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
    public DatasetVersion datasetVersionDtoToDo(DatasetVersionDto source) throws MetamacException {
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

    private DatasetVersion datasetVersionDtoToDo(DatasetVersionDto source, DatasetVersion target) throws MetamacException {
        if (target == null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.DATASET_VERSION).build();
        }

        // Check replaces, can't replace a dataset already replaced by other dataset
        checkCanDatasetReplacesOtherDataset(source);

        // Hierarchy
        siemacMetadataStatisticalResourceDtoToDo(source, target.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.DATASET_VERSION);

        // modifiable
        externalItemDtoCollectionToDoList(source.getGeographicGranularities(), target.getGeographicGranularities(), ServiceExceptionParameters.DATASET_VERSION__GEOGRAPHIC_GRANULARITIES);
        externalItemDtoCollectionToDoList(source.getTemporalGranularities(), target.getTemporalGranularities(), ServiceExceptionParameters.DATASET_VERSION__TEMPORAL_GRANULARITIES);
        externalItemDtoCollectionToDoList(source.getStatisticalUnit(), target.getStatisticalUnit(), ServiceExceptionParameters.DATASET_VERSION__STATISTICAL_UNIT);

        target.setRelatedDsdChanged(false);
        if (source.getRelatedDsd() != null && DatasetMetadataEditionChecks.canDsdBeEdited(target.getId(), target.getSiemacMetadataStatisticalResource().getProcStatus())) {
            datasetVersionDtoRelatedDsdToDo(source, target);
        }

        if (MetadataEditionChecks.canNextVersionDateBeEdited(target.getSiemacMetadataStatisticalResource().getNextVersion())) {
            boolean dateNextUpdateModified = hasDateBeModified(target.getDateNextUpdate(), source.getDateNextUpdate());
            if (dateNextUpdateModified) {
                target.setUserModifiedDateNextUpdate(true);
                target.setDateNextUpdate(dateDtoToDo(source.getDateNextUpdate()));
            }
        } else {
            target.setUserModifiedDateNextUpdate(false);
            target.setDateNextUpdate(null);
        }

        target.setUpdateFrequency(externalItemDtoToDo(source.getUpdateFrequency(), target.getUpdateFrequency(), ServiceExceptionParameters.DATASET_VERSION__UPDATE_FREQUENCY));
        target.setStatisticOfficiality(statisticOfficialityDtoToDo(source.getStatisticOfficiality(), target.getStatisticOfficiality(),
                ServiceExceptionParameters.DATASET_VERSION__STATISTIC_OFFICIALITY));

        target.setKeepAllData(source.isKeepAllData());

        return target;
    }

    protected void checkCanDatasetReplacesOtherDataset(DatasetVersionDto source) throws MetamacException {
        if (source.getReplaces() != null) {
            String currentUrn = source.getUrn();
            RelatedResource resourceReplaced = relatedResourceDtoToDo(source.getReplaces(), null, ServiceExceptionParameters.DATASET_VERSION__REPLACES);
            if (currentUrn.equals(resourceReplaced.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn())) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.DATASET_VERSION_CANT_REPLACE_ITSELF).withMessageParameters(currentUrn).build();
            } else {
                RelatedResource resourceAlreadyReplacing = resourceReplaced.getDatasetVersion().getSiemacMetadataStatisticalResource().getIsReplacedBy();
                if (resourceAlreadyReplacing != null && !resourceAlreadyReplacing.getDatasetVersion().getLifeCycleStatisticalResource().getUrn().equals(source.getUrn())) {
                    throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.DATASET_VERSION_ALREADY_BEEN_REPLACED_BY_OTHER_DATASET_VERSION)
                            .withMessageParameters(currentUrn, source.getReplaces().getUrn()).build();
                }
            }
        }
    }

    private boolean hasDateBeModified(DateTime previous, Date current) {
        if ((previous == null && current != null) || (previous != null && current == null)) {
            return true;
        } else if (previous == null && current == null) {
            return false;
        } else if (!previous.toDate().equals(current)) {
            return true;
        } else {
            return false;
        }
    }

    // source.relatedDsd is supposed not to be null
    private void datasetVersionDtoRelatedDsdToDo(DatasetVersionDto source, DatasetVersion target) throws MetamacException {
        if (target.getRelatedDsd() == null) {
            target.setRelatedDsd(externalItemDtoToDo(source.getRelatedDsd(), target.getRelatedDsd(), ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD));
        } else {
            boolean changedDsd = false;
            if (areDifferentDsd(target.getRelatedDsd(), source.getRelatedDsd())) {
                if (DatasetMetadataEditionChecks.canDsdBeReplacedByAnyOtherDsd(target.getId(), target.getSiemacMetadataStatisticalResource().getVersionLogic(), target
                        .getSiemacMetadataStatisticalResource().getProcStatus())) {
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

    private boolean areSameDsdDifferentVersion(ExternalItem oldDsd, ExternalItemDto newDsdDto) {
        String[] oldDsdIdentifiers = UrnUtils.splitUrnStructure(oldDsd.getUrn());
        String[] newDsdIdentifiers = UrnUtils.splitUrnStructure(newDsdDto.getUrn());

        String newDsdAgency = oldDsdIdentifiers[0];
        String oldDsdAgency = newDsdIdentifiers[0];

        String newDsdCode = oldDsdIdentifiers[1];
        String oldDsdCode = newDsdIdentifiers[1];

        String newDsdVersion = oldDsdIdentifiers[2];
        String oldDsdVersion = newDsdIdentifiers[2];

        boolean sameDsd = StringUtils.equals(oldDsdAgency, newDsdAgency) && StringUtils.equals(oldDsdCode, newDsdCode);
        boolean differentDsdVersion = !StringUtils.equals(oldDsdVersion, newDsdVersion);

        return sameDsd && differentDsdVersion;
    }

    public StatisticOfficiality statisticOfficialityDtoToDo(StatisticOfficialityDto source, StatisticOfficiality target, String metadataName) throws MetamacException {
        if (source == null) {
            return null;
        }

        try {
            target = statisticOfficialityRepository.findById(source.getId());
        } catch (StatisticOfficialityNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.STATISTIC_OFFICIALITY_NOT_FOUND, source.getId());
        }

        return target;
    }

    @Override
    public Categorisation categorisationDtoToDo(CategorisationDto source) throws MetamacException {
        if (source == null) {
            return null;
        }
        Categorisation target = null;
        if (source.getId() != null || source.getUrn() != null) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Categorisation can not be updated");
        }
        target = new Categorisation();
        target.setVersionableStatisticalResource(new VersionableStatisticalResource());
        target.setMaintainer(externalItemDtoToDo(source.getMaintainer(), target.getMaintainer(), ServiceExceptionParameters.CATEGORISATION__MAINTAINER));
        target.setCategory(externalItemDtoToDo(source.getCategory(), target.getCategory(), ServiceExceptionParameters.CATEGORISATION__CATEGORY));
        return target;
    }
}
