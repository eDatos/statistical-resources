package org.siemac.metamac.statistical.resources.core.base.validators;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public abstract class BaseInvocationValidator {

    // ------------------------------------------------------------------------------------
    // STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewStatisticalResource(StatisticalResource statisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(statisticalResource, ServiceExceptionParameters.STATISTICAL_RESOURCE, exceptions);
        if (statisticalResource == null) {
            return;
        }

        checkStatisticalResource(statisticalResource, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getId(), ServiceExceptionParameters.STATISTICAL_RESOURCE_ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getVersion(), ServiceExceptionParameters.STATISTICAL_RESOURCE_VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getCreatedDate(), ServiceExceptionParameters.STATISTICAL_RESOURCE_CREATED_DATE, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getCreatedBy(), ServiceExceptionParameters.STATISTICAL_RESOURCE_CREATED_BY, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getLastUpdated(), ServiceExceptionParameters.STATISTICAL_RESOURCE_LAST_UPDATED, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getLastUpdatedBy(), ServiceExceptionParameters.STATISTICAL_RESOURCE_LAST_UPDATED_BY, exceptions);
    }

    protected static void checkExistingStatisticalResource(StatisticalResource statisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(statisticalResource, ServiceExceptionParameters.STATISTICAL_RESOURCE, exceptions);
        if (statisticalResource == null) {
            return;
        }

        checkStatisticalResource(statisticalResource, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getId(), ServiceExceptionParameters.STATISTICAL_RESOURCE_ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getVersion(), ServiceExceptionParameters.STATISTICAL_RESOURCE_VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getCreatedDate(), ServiceExceptionParameters.STATISTICAL_RESOURCE_CREATED_DATE, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getCreatedBy(), ServiceExceptionParameters.STATISTICAL_RESOURCE_CREATED_BY, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getLastUpdated(), ServiceExceptionParameters.STATISTICAL_RESOURCE_LAST_UPDATED, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getLastUpdatedBy(), ServiceExceptionParameters.STATISTICAL_RESOURCE_LAST_UPDATED_BY, exceptions);
    }

    private static void checkStatisticalResource(StatisticalResource statisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getOperation(), ServiceExceptionParameters.STATISTICAL_RESOURCE_OPERATION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getUuid(), ServiceExceptionParameters.STATISTICAL_RESOURCE_UUID, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // IDENTIFIABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewIdentifiableStatisticalResource(IdentifiableStatisticalResource identifiableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(identifiableStatisticalResource, ServiceExceptionParameters.IDENTIFIABLE_RESOURCE, exceptions);
        if (identifiableStatisticalResource == null) {
            return;
        }

        checkNewStatisticalResource(identifiableStatisticalResource, exceptions);
        checkIdentifiableStatisticalResource(identifiableStatisticalResource, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(identifiableStatisticalResource.getUrn(), ServiceExceptionParameters.IDENTIFIABLE_RESOURCE_URN, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(identifiableStatisticalResource.getUrn(), ServiceExceptionParameters.IDENTIFIABLE_RESOURCE_URI, exceptions);
    }

    protected static void checkExistingIdentifiableStatisticalResource(IdentifiableStatisticalResource identifiableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(identifiableStatisticalResource, ServiceExceptionParameters.IDENTIFIABLE_RESOURCE, exceptions);
        if (identifiableStatisticalResource == null) {
            return;
        }

        checkExistingStatisticalResource(identifiableStatisticalResource, exceptions);
        checkIdentifiableStatisticalResource(identifiableStatisticalResource, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(identifiableStatisticalResource.getUrn(), ServiceExceptionParameters.IDENTIFIABLE_RESOURCE_URN, exceptions);
    }

    private static void checkIdentifiableStatisticalResource(IdentifiableStatisticalResource identifiableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(identifiableStatisticalResource.getCode(), ServiceExceptionParameters.IDENTIFIABLE_RESOURCE_CODE, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // NAMEABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewNameableStatisticalResource(NameableStatisticalResource nameableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(nameableStatisticalResource, ServiceExceptionParameters.NAMEABLE_RESOURCE, exceptions);
        if (nameableStatisticalResource == null) {
            return;
        }

        checkNewIdentifiableStatisticalResource(nameableStatisticalResource, exceptions);
        checkNameableStatisticalResource(nameableStatisticalResource, exceptions);
    }

    protected static void checkExistingNameableStatisticalResource(NameableStatisticalResource nameableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(nameableStatisticalResource, ServiceExceptionParameters.NAMEABLE_RESOURCE, exceptions);
        if (nameableStatisticalResource == null) {
            return;
        }

        checkExistingIdentifiableStatisticalResource(nameableStatisticalResource, exceptions);
        checkNameableStatisticalResource(nameableStatisticalResource, exceptions);
    }

    private static void checkNameableStatisticalResource(NameableStatisticalResource nameableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(nameableStatisticalResource.getTitle(), ServiceExceptionParameters.NAMEABLE_RESOURCE_TITLE, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataOptionalIsValid(nameableStatisticalResource.getDescription(), ServiceExceptionParameters.NAMEABLE_RESOURCE_DESCRIPTION, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // VERSIONABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewVersionableStatisticalResource(VersionableStatisticalResource versionableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(versionableStatisticalResource, ServiceExceptionParameters.VERSIONABLE_RESOURCE, exceptions);
        if (versionableStatisticalResource == null) {
            return;
        }

        checkNewNameableStatisticalResource(versionableStatisticalResource, exceptions);
        checkVersionableStatisticalResource(versionableStatisticalResource, exceptions);

        // Metadata that must be empty when the entity is created
        StatisticalResourcesValidationUtils.checkMetadataEmpty(versionableStatisticalResource.getVersionLogic(), ServiceExceptionParameters.VERSIONABLE_RESOURCE_VERSION_LOGIC, exceptions);
    }

    protected static void checkExistingVersionableStatisticalResource(VersionableStatisticalResource versionableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(versionableStatisticalResource, ServiceExceptionParameters.VERSIONABLE_RESOURCE, exceptions);
        if (versionableStatisticalResource == null) {
            return;
        }

        checkExistingNameableStatisticalResource(versionableStatisticalResource, exceptions);
        checkVersionableStatisticalResource(versionableStatisticalResource, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(versionableStatisticalResource.getVersionLogic(), ServiceExceptionParameters.VERSIONABLE_RESOURCE_VERSION_LOGIC, exceptions);
    }

    private static void checkVersionableStatisticalResource(VersionableStatisticalResource versionableStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataOptionalIsValid(versionableStatisticalResource.getVersionRationale(), ServiceExceptionParameters.VERSIONABLE_RESOURCE_VERSION_RATIONALE,
                exceptions);
    }

    // ------------------------------------------------------------------------------------
    // LIFE CYCLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewLifeCycleStatisticalResource(LifeCycleStatisticalResource lifeCycleStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(lifeCycleStatisticalResource, ServiceExceptionParameters.LIFE_CYCLE_RESOURCE, exceptions);
        if (lifeCycleStatisticalResource == null) {
            return;
        }

        checkNewVersionableStatisticalResource(lifeCycleStatisticalResource, exceptions);
        checkLifeCycleStatisticalResource(lifeCycleStatisticalResource, exceptions);
    }

    protected static void checkExistingLifeCycleStatisticalResource(LifeCycleStatisticalResource lifeCycleStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(lifeCycleStatisticalResource, ServiceExceptionParameters.LIFE_CYCLE_RESOURCE, exceptions);
        if (lifeCycleStatisticalResource == null) {
            return;
        }

        checkExistingVersionableStatisticalResource(lifeCycleStatisticalResource, exceptions);
        checkLifeCycleStatisticalResource(lifeCycleStatisticalResource, exceptions);
    }

    private static void checkLifeCycleStatisticalResource(LifeCycleStatisticalResource lifeCycleStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(lifeCycleStatisticalResource.getProcStatus(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_PROC_STATUS, exceptions);

        StatisticalResourcesValidationUtils.checkMetadataOptionalIsValid(lifeCycleStatisticalResource.getCreator(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_CREATOR, exceptions);
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(lifeCycleStatisticalResource.getContributor(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_CONTRIBUTOR, exceptions);
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(lifeCycleStatisticalResource.getPublisher(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_PUBLISHER, exceptions);
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(lifeCycleStatisticalResource.getMediator(), ServiceExceptionParameters.LIFE_CYCLE_RESOURCE_MEDIATOR, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // SIEMAC METADATA STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource siemacMetadataStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(siemacMetadataStatisticalResource, ServiceExceptionParameters.SIEMAC_METADATA_RESOURCE, exceptions);
        if (siemacMetadataStatisticalResource == null) {
            return;
        }

        checkSiemacMetadataStatisticalResource(siemacMetadataStatisticalResource, exceptions);
        checkNewLifeCycleStatisticalResource(siemacMetadataStatisticalResource, exceptions);
    }

    protected static void checkExistingSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource siemacMetadataStatisticalResource, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(siemacMetadataStatisticalResource, ServiceExceptionParameters.SIEMAC_METADATA_RESOURCE, exceptions);
        if (siemacMetadataStatisticalResource == null) {
            return;
        }

        checkSiemacMetadataStatisticalResource(siemacMetadataStatisticalResource, exceptions);
        checkExistingLifeCycleStatisticalResource(siemacMetadataStatisticalResource, exceptions);
    }

    private static void checkSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource siemacMetadataStatisticalResource, List<MetamacExceptionItem> exceptions) {
    }
}
