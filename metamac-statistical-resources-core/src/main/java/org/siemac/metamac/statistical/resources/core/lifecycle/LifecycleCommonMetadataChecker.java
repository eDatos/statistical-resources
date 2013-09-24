package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataEmpty;
import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataRequired;
import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;
import org.springframework.stereotype.Component;

@Component
public class LifecycleCommonMetadataChecker {

    public void checkLifecycleCommonMetadata(HasLifecycle resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        LifeCycleStatisticalResource lifeCycleStatisticalResource = resource.getLifeCycleStatisticalResource();

        // StatisticalResource
        checkMetadataRequired(lifeCycleStatisticalResource.getStatisticalOperation(), addParameter(metadataName, ServiceExceptionSingleParameters.STATISTICAL_OPERATION), exceptionItems);

        // IdentifiableResource
        checkMetadataRequired(lifeCycleStatisticalResource.getCode(), addParameter(metadataName, ServiceExceptionSingleParameters.CODE), exceptionItems);
        checkMetadataRequired(lifeCycleStatisticalResource.getUrn(), addParameter(metadataName, ServiceExceptionSingleParameters.URN), exceptionItems);

        // NameableResource
        checkMetadataRequired(lifeCycleStatisticalResource.getTitle(), addParameter(metadataName, ServiceExceptionSingleParameters.TITLE), exceptionItems);
        checkMetadataRequired(lifeCycleStatisticalResource.getDescription(), addParameter(metadataName, ServiceExceptionSingleParameters.DESCRIPTION), exceptionItems);

        // VersionableResource
        checkMetadataRequired(lifeCycleStatisticalResource.getVersionLogic(), addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_LOGIC), exceptionItems);
        checkMetadataRequired(lifeCycleStatisticalResource.getVersionRationaleTypes(), addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES), exceptionItems);
        for (VersionRationaleType versionRationaleType : lifeCycleStatisticalResource.getVersionRationaleTypes()) {
            if (VersionRationaleTypeEnum.MINOR_ERRATA.equals(versionRationaleType.getValue())) {
                checkMetadataRequired(lifeCycleStatisticalResource.getVersionRationale(), addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE), exceptionItems);
            }
        }

        checkMetadataRequired(lifeCycleStatisticalResource.getNextVersion(), addParameter(metadataName, ServiceExceptionSingleParameters.NEXT_VERSION), exceptionItems);
        if (lifeCycleStatisticalResource.getNextVersion() != null && !NextVersionTypeEnum.SCHEDULED_UPDATE.equals(lifeCycleStatisticalResource.getNextVersion())) {
            checkMetadataEmpty(lifeCycleStatisticalResource.getNextVersionDate(), addParameter(metadataName, ServiceExceptionSingleParameters.NEXT_VERSION_DATE), exceptionItems);
        }

        // LifeCycleResource
        checkMetadataRequired(lifeCycleStatisticalResource.getProcStatus(), addParameter(metadataName, ServiceExceptionSingleParameters.PROC_STATUS), exceptionItems);
        if (StringUtils.isNotBlank(lifeCycleStatisticalResource.getVersionLogic()) && !StatisticalResourcesVersionUtils.isInitialVersion(lifeCycleStatisticalResource.getVersionLogic())) {
            checkMetadataRequired(lifeCycleStatisticalResource.getReplacesVersion(), addParameter(metadataName, ServiceExceptionSingleParameters.REPLACES_VERSION), exceptionItems);
        }
        checkMetadataRequired(lifeCycleStatisticalResource.getMaintainer(), addParameter(metadataName, ServiceExceptionSingleParameters.MAINTAINER), exceptionItems);
    }

    public void checkSiemacCommonMetadata(HasSiemacMetadata resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        SiemacMetadataStatisticalResource siemacMetadataStatisticalResource = resource.getSiemacMetadataStatisticalResource();

        checkMetadataRequired(siemacMetadataStatisticalResource.getLanguage(), addParameter(metadataName, ServiceExceptionSingleParameters.LANGUAGE), exceptionItems);
        checkMetadataRequired(siemacMetadataStatisticalResource.getLanguages(), addParameter(metadataName, ServiceExceptionSingleParameters.LANGUAGES), exceptionItems);

        // TODO: Robert, Comprobar si aquí deberían estar las KEYWORDS

        checkMetadataRequired(siemacMetadataStatisticalResource.getType(), addParameter(metadataName, ServiceExceptionSingleParameters.TYPE), exceptionItems);

        checkMetadataRequired(siemacMetadataStatisticalResource.getCreator(), addParameter(metadataName, ServiceExceptionSingleParameters.CREATOR), exceptionItems);
        checkMetadataRequired(siemacMetadataStatisticalResource.getLastUpdate(), addParameter(metadataName, ServiceExceptionSingleParameters.LAST_UPDATE), exceptionItems);

        checkMetadataRequired(siemacMetadataStatisticalResource.getPublisher(), addParameter(metadataName, ServiceExceptionSingleParameters.PUBLISHER), exceptionItems);

        checkMetadataRequired(siemacMetadataStatisticalResource.getCommonMetadata(), addParameter(metadataName, ServiceExceptionSingleParameters.COMMON_METADATA), exceptionItems);
    }

    public void checkDatasetVersionCommonMetadata(DatasetVersion resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {

        checkMetadataRequired(resource.getRelatedDsd(), addParameter(metadataName, ServiceExceptionSingleParameters.RELATED_DSD), exceptionItems);

        checkMetadataRequired(resource.getGeographicGranularities(), addParameter(metadataName, ServiceExceptionSingleParameters.GEOGRAPHIC_GRANULARITIES), exceptionItems);
        checkMetadataRequired(resource.getTemporalGranularities(), addParameter(metadataName, ServiceExceptionSingleParameters.TEMPORAL_GRANULARITIES), exceptionItems);

        checkMetadataRequired(resource.getUpdateFrequency(), addParameter(metadataName, ServiceExceptionSingleParameters.UPDATE_FREQUENCY), exceptionItems);
        checkMetadataRequired(resource.getStatisticOfficiality(), addParameter(metadataName, ServiceExceptionSingleParameters.STATISTIC_OFFICIALITY), exceptionItems);

        if (resource.getDatasources() == null || resource.getDatasources().isEmpty()) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.DATASET_EMPTY_DATASOURCES, resource.getSiemacMetadataStatisticalResource().getUrn()));
        } else {
            if (!hasAnyDatasourceDateNextUpdate(resource)) {
                checkMetadataRequired(resource.getDateNextUpdate(), addParameter(metadataName, ServiceExceptionSingleParameters.DATE_NEXT_UPDATE), exceptionItems);
            }
        }
    }

    private boolean hasAnyDatasourceDateNextUpdate(DatasetVersion resource) {
        for (Datasource datasource : resource.getDatasources()) {
            if (datasource.getDateNextUpdate() != null) {
                return true;
            }
        }
        return false;
    }

    public void checkPublicationVersionCommonMetadata(PublicationVersion resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        // Metadata specific of publicationVersion are only required for published: formatExtentResources and at least one cube per level.
        checkMetadataEmpty(resource.getFormatExtentResources(), addParameter(metadataName, ServiceExceptionSingleParameters.FORMAT_EXTENT_RESOURCES), exceptionItems);
    }

    public void checkQueryVersionCommonMetadata(QueryVersion resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        StatisticalResourcesValidationUtils.checkMetadataRequiredIncompatible(resource.getFixedDatasetVersion(), addParameter(metadataName, ServiceExceptionSingleParameters.FIXED_DATASET_VERSION),
                resource.getDataset(), addParameter(metadataName, ServiceExceptionSingleParameters.DATASET), exceptionItems);
        checkMetadataRequired(resource.getStatus(), addParameter(metadataName, ServiceExceptionSingleParameters.STATUS), exceptionItems);
        checkMetadataRequired(resource.getType(), addParameter(metadataName, ServiceExceptionSingleParameters.TYPE), exceptionItems);
        checkMetadataRequired(resource.getSelection(), addParameter(metadataName, ServiceExceptionSingleParameters.SELECTION), exceptionItems);

        if (QueryTypeEnum.LATEST_DATA.equals(resource.getType())) {
            checkMetadataRequired(resource.getLatestDataNumber(), addParameter(metadataName, ServiceExceptionSingleParameters.LATEST_DATA_NUMBER), exceptionItems);
            checkMetadataRequired(resource.getLatestTemporalCodeInCreation(), addParameter(metadataName, ServiceExceptionSingleParameters.LATEST_TEMPORAL_CODE_IN_CREATION), exceptionItems);
        }
    }
}
