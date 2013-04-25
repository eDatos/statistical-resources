package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataEmpty;
import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataRequired;
import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.springframework.stereotype.Component;

@Component
public class LifecycleCommonMetadataChecker {

    public void checkLifecycleCommonMetadata(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        checkMetadataRequired(resource.getCode(), addParameter(metadataName, ServiceExceptionSingleParameters.CODE), exceptionItems);
        checkMetadataRequired(resource.getUrn(), addParameter(metadataName, ServiceExceptionSingleParameters.URN), exceptionItems);
        
        checkMetadataRequired(resource.getTitle(), addParameter(metadataName, ServiceExceptionSingleParameters.TITLE), exceptionItems);
        checkMetadataRequired(resource.getDescription(), addParameter(metadataName, ServiceExceptionSingleParameters.DESCRIPTION), exceptionItems);
        
        checkMetadataRequired(resource.getVersionLogic(), addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_LOGIC), exceptionItems);
        checkMetadataRequired(resource.getVersionRationaleTypes(), addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES), exceptionItems);
        for (VersionRationaleType versionRationaleType : resource.getVersionRationaleTypes()) {
            if (VersionRationaleTypeEnum.MINOR_ERRATA.equals(versionRationaleType.getValue())) {
                checkMetadataRequired(resource.getVersionRationale(), addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE), exceptionItems);
            }
        }
        
        checkMetadataRequired(resource.getNextVersion(), addParameter(metadataName, ServiceExceptionSingleParameters.NEXT_VERSION), exceptionItems);
        if (resource.getNextVersion() != null && !NextVersionTypeEnum.SCHEDULED_UPDATE.equals(resource.getNextVersion())) {
            checkMetadataEmpty(resource.getNextVersionDate(),addParameter(metadataName, ServiceExceptionSingleParameters.NEXT_VERSION_DATE), exceptionItems);
        }
        checkMetadataRequired(resource.getProcStatus(), addParameter(metadataName, ServiceExceptionSingleParameters.PROC_STATUS), exceptionItems);
    }
    
    public void checkSiemacCommonMetadata(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        checkMetadataRequired(resource.getLanguage(), addParameter(metadataName, ServiceExceptionSingleParameters.LANGUAGE), exceptionItems);
        checkMetadataRequired(resource.getLanguages(), addParameter(metadataName, ServiceExceptionSingleParameters.LANGUAGES), exceptionItems);
        
        checkMetadataRequired(resource.getStatisticalOperation(), addParameter(metadataName, ServiceExceptionSingleParameters.STATISTICAL_OPERATION), exceptionItems);
        
        checkMetadataRequired(resource.getType(), addParameter(metadataName, ServiceExceptionSingleParameters.TYPE), exceptionItems);
        
        checkMetadataRequired(resource.getMaintainer(), addParameter(metadataName, ServiceExceptionSingleParameters.MAINTAINER), exceptionItems);
        checkMetadataRequired(resource.getCreator(), addParameter(metadataName, ServiceExceptionSingleParameters.CREATOR), exceptionItems);
        checkMetadataRequired(resource.getLastUpdate(), addParameter(metadataName, ServiceExceptionSingleParameters.LAST_UPDATE), exceptionItems);
        
        checkMetadataRequired(resource.getPublisher(), addParameter(metadataName, ServiceExceptionSingleParameters.PUBLISHER), exceptionItems);
        
        checkMetadataRequired(resource.getRightsHolder(), addParameter(metadataName, ServiceExceptionSingleParameters.RIGHTS_HOLDER), exceptionItems);
        checkMetadataRequired(resource.getLicense(), addParameter(metadataName, ServiceExceptionSingleParameters.LICENSE), exceptionItems);
    }
    
    public void checkDatasetVersionCommonMetadata(DatasetVersion resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        checkMetadataRequired(resource.getGeographicGranularities(), addParameter(metadataName, ServiceExceptionSingleParameters.GEOGRAPHIC_GRANULARITIES), exceptionItems);
        checkMetadataRequired(resource.getTemporalGranularities(), addParameter(metadataName, ServiceExceptionSingleParameters.TEMPORAL_GRANULARITIES), exceptionItems);
        checkMetadataRequired(resource.getRelatedDsd(), addParameter(metadataName, ServiceExceptionSingleParameters.RELATED_DSD), exceptionItems);
        checkMetadataRequired(resource.getDateNextUpdate(), addParameter(metadataName, ServiceExceptionSingleParameters.DATE_NEXT_UPDATE), exceptionItems);
        checkMetadataRequired(resource.getUpdateFrequency(), addParameter(metadataName, ServiceExceptionSingleParameters.UPDATE_FREQUENCY), exceptionItems);
        checkMetadataRequired(resource.getStatisticOfficiality(), addParameter(metadataName, ServiceExceptionSingleParameters.STATISTIC_OFFICIALITY), exceptionItems);
    }
    
    public void checkPublicationVersionCommonMetadata(PublicationVersion resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        checkMetadataRequired(resource.getFormatExtentResources(), addParameter(metadataName, ServiceExceptionSingleParameters.FORMAT_EXTENT_RESOURCES), exceptionItems);
    }
}
