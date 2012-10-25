package org.siemac.metamac.statistical.resources.core.serviceimpl.utils;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.VersionableStatisticalResource;

public class BaseInvocationValidator {

    // ------------------------------------------------------------------------------------
    // STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------
    public static void checkStatisticalResource(StatisticalResource statisticalResource, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(statisticalResource, ServiceExceptionParameters.STATISTICAL_RESOURCE, exceptions);
        if (statisticalResource == null) {
            return;
        }
        ValidationUtils.checkMetadataRequired(statisticalResource.getOperation(), ServiceExceptionParameters.STATISTICAL_RESOURCE_OPERATION, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // IDENTIFIABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------
    public static void checkIdentifiableStatisticalResource(IdentifiableStatisticalResource identifiableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(identifiableStatisticalResource, ServiceExceptionParameters.IDENTIFIABLE_RESOURCE, exceptions);
        if (identifiableStatisticalResource == null) {
            return;
        }

        ValidationUtils.checkMetadataRequired(identifiableStatisticalResource.getCode(), ServiceExceptionParameters.IDENTIFIABLE_RESOURCE_CODE, exceptions);

        checkStatisticalResource(identifiableStatisticalResource, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // NAMEABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    public static void checkNameableStatisticalResource(NameableStatisticalResource nameableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(nameableStatisticalResource, ServiceExceptionParameters.NAMEABLE_RESOURCE, exceptions);
        if (nameableStatisticalResource == null) {
            return;
        }
        
        ValidationUtils.checkMetadataRequired(nameableStatisticalResource.getTitle(),ServiceExceptionParameters.NAMEABLE_RESOURCE_TITLE, exceptions);
        
        checkIdentifiableStatisticalResource(nameableStatisticalResource, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // VERSIONABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------
    
    public static void checkVersionableStatisticalResource(VersionableStatisticalResource versionableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(versionableStatisticalResource, ServiceExceptionParameters.VERSIONABLE_RESOURCE, exceptions);
        if (versionableStatisticalResource == null) {
            return;
        }
        if (versionableStatisticalResource.getId() != null) {
            ValidationUtils.checkMetadataRequired(versionableStatisticalResource.getVersionLogic(), ServiceExceptionParameters.VERSIONABLE_RESOURCE_VERSION_LOGIC, exceptions);
         // TODO: hay que añadir algo mas?
        } else {
            // metadata must be empty when the entity is created
            ValidationUtils.checkMetadataEmpty(versionableStatisticalResource.getVersionLogic(), ServiceExceptionParameters.VERSIONABLE_RESOURCE_VERSION_LOGIC, exceptions);
         // TODO: hay que añadir algo mas?
//            ValidationUtils.checkMetadataEmpty(versionableStatisticalResource.getValidFrom(), ServiceExceptionParameters.MAINTAINABLE_ARTEFACT_VALID_FROM, exceptions);
//            ValidationUtils.checkMetadataEmpty(versionableStatisticalResource.getValidTo(), ServiceExceptionParameters.MAINTAINABLE_ARTEFACT_VALID_TO, exceptions);
        }
        checkNameableStatisticalResource(versionableStatisticalResource, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // LIFE CYCLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------
    public static void checkLifeCycleStatisticalResource(LifeCycleStatisticalResource lifeCycleStatisticalResource, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(lifeCycleStatisticalResource, ServiceExceptionParameters.LIFE_CYCLE_RESOURCE, exceptions);
        if (lifeCycleStatisticalResource == null) {
            return;
        }
        // TODO: Comprobar si añadir algo mas
        ValidationUtils.checkMetadataRequired(lifeCycleStatisticalResource.getProcStatus(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_PROC_STATUS, exceptions);
        
        checkVersionableStatisticalResource(lifeCycleStatisticalResource, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // SIEMAC METADATA STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------
    public static void checkSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource siemacMetadataStatisticalResource, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(siemacMetadataStatisticalResource, ServiceExceptionParameters.SIEMAC_METADATA_RESOURCE, exceptions);
        if (siemacMetadataStatisticalResource == null) {
            return;
        }
        
        // TODO: Añadir metadatos requeridos
        
        checkLifeCycleStatisticalResource(siemacMetadataStatisticalResource, exceptions);
    }
}
