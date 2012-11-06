package org.siemac.metamac.statistical.resources.core.utils;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;

public class BaseInvocationValidator {

    // ------------------------------------------------------------------------------------
    // STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------
    public static void checkStatisticalResource(StatisticalResource statisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(statisticalResource, ServiceExceptionParameters.STATISTICAL_RESOURCE, exceptions);
        if (statisticalResource == null) {
            return;
        }
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getOperation(), ServiceExceptionParameters.STATISTICAL_RESOURCE_OPERATION, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // IDENTIFIABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------
    public static void checkIdentifiableStatisticalResource(IdentifiableStatisticalResource identifiableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(identifiableStatisticalResource, ServiceExceptionParameters.IDENTIFIABLE_RESOURCE, exceptions);
        if (identifiableStatisticalResource == null) {
            return;
        }

        StatisticalResourcesValidationUtils.checkMetadataRequired(identifiableStatisticalResource.getCode(), ServiceExceptionParameters.IDENTIFIABLE_RESOURCE_CODE, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(identifiableStatisticalResource.getUrn(), ServiceExceptionParameters.IDENTIFIABLE_RESOURCE_URN, exceptions);

        checkStatisticalResource(identifiableStatisticalResource, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // NAMEABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    public static void checkNameableStatisticalResource(NameableStatisticalResource nameableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(nameableStatisticalResource, ServiceExceptionParameters.NAMEABLE_RESOURCE, exceptions);
        if (nameableStatisticalResource == null) {
            return;
        }
        
        StatisticalResourcesValidationUtils.checkMetadataRequired(nameableStatisticalResource.getTitle(),ServiceExceptionParameters.NAMEABLE_RESOURCE_TITLE, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataOptionalIsValid(nameableStatisticalResource.getDescription(),ServiceExceptionParameters.NAMEABLE_RESOURCE_DESCRIPTION, exceptions);
        
        checkIdentifiableStatisticalResource(nameableStatisticalResource, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // VERSIONABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------
    
    public static void checkVersionableStatisticalResource(VersionableStatisticalResource versionableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(versionableStatisticalResource, ServiceExceptionParameters.VERSIONABLE_RESOURCE, exceptions);
        if (versionableStatisticalResource == null) {
            return;
        }
        if (versionableStatisticalResource.getId() != null) {
            StatisticalResourcesValidationUtils.checkMetadataRequired(versionableStatisticalResource.getVersionLogic(), ServiceExceptionParameters.VERSIONABLE_RESOURCE_VERSION_LOGIC, exceptions);
         // TODO: hay que añadir algo mas?
        } else {
            // metadata must be empty when the entity is created
            StatisticalResourcesValidationUtils.checkMetadataEmpty(versionableStatisticalResource.getVersionLogic(), ServiceExceptionParameters.VERSIONABLE_RESOURCE_VERSION_LOGIC, exceptions);
         // TODO: hay que añadir algo mas?
//            StatisticalResourcesValidationUtils.checkMetadataEmpty(versionableStatisticalResource.getValidFrom(), ServiceExceptionParameters.MAINTAINABLE_ARTEFACT_VALID_FROM, exceptions);
//            StatisticalResourcesValidationUtils.checkMetadataEmpty(versionableStatisticalResource.getValidTo(), ServiceExceptionParameters.MAINTAINABLE_ARTEFACT_VALID_TO, exceptions);
        }
        
        StatisticalResourcesValidationUtils.checkMetadataOptionalIsValid(versionableStatisticalResource.getVersionRationale(),ServiceExceptionParameters.VERSIONABLE_RESOURCE_VERSION_RATIONALE, exceptions);
        
        checkNameableStatisticalResource(versionableStatisticalResource, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // LIFE CYCLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------
    public static void checkLifeCycleStatisticalResource(LifeCycleStatisticalResource lifeCycleStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(lifeCycleStatisticalResource, ServiceExceptionParameters.LIFE_CYCLE_RESOURCE, exceptions);
        if (lifeCycleStatisticalResource == null) {
            return;
        }
        // TODO: Comprobar si añadir algo mas
        StatisticalResourcesValidationUtils.checkMetadataRequired(lifeCycleStatisticalResource.getProcStatus(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_PROC_STATUS, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataOptionalIsValid(lifeCycleStatisticalResource.getCreator(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_CREATOR, exceptions);
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(lifeCycleStatisticalResource.getContributor(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_CONTRIBUTOR, exceptions);
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(lifeCycleStatisticalResource.getPublisher(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_PUBLISHER, exceptions);
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(lifeCycleStatisticalResource.getMediator(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_MEDIATOR, exceptions);
        
        checkVersionableStatisticalResource(lifeCycleStatisticalResource, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // SIEMAC METADATA STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------
    public static void checkSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource siemacMetadataStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(siemacMetadataStatisticalResource, ServiceExceptionParameters.SIEMAC_METADATA_RESOURCE, exceptions);
        if (siemacMetadataStatisticalResource == null) {
            return;
        }
        
        // TODO: Añadir metadatos requeridos
        
        checkLifeCycleStatisticalResource(siemacMetadataStatisticalResource, exceptions);
    }
}
