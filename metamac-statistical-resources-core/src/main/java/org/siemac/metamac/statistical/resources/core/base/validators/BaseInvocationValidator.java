package org.siemac.metamac.statistical.resources.core.base.validators;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public abstract class BaseInvocationValidator {

    // ------------------------------------------------------------------------------------
    // STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewStatisticalResource(StatisticalResource statisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource, metadataName, exceptions);
        if (statisticalResource == null) {
            return;
        }

        checkStatisticalResource(statisticalResource, metadataName, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getId(), metadataName + ServiceExceptionSingleParameters.ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getVersion(), metadataName + ServiceExceptionSingleParameters.VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getCreatedDate(), metadataName + ServiceExceptionSingleParameters.CREATED_DATE, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getCreatedBy(), metadataName + ServiceExceptionSingleParameters.CREATED_BY, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getLastUpdated(), metadataName + ServiceExceptionSingleParameters.LAST_UPDATED, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(statisticalResource.getLastUpdatedBy(), metadataName + ServiceExceptionSingleParameters.LAST_UPDATED_BY, exceptions);
    }

    protected static void checkExistingStatisticalResource(StatisticalResource statisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource, metadataName, exceptions);
        if (statisticalResource == null) {
            return;
        }

        checkStatisticalResource(statisticalResource, metadataName, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getId(), metadataName + ServiceExceptionSingleParameters.ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getVersion(), metadataName + ServiceExceptionSingleParameters.VERSION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getCreatedDate(), metadataName + ServiceExceptionSingleParameters.CREATED_DATE, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getCreatedBy(), metadataName + ServiceExceptionSingleParameters.CREATED_BY, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getLastUpdated(), metadataName + ServiceExceptionSingleParameters.LAST_UPDATED, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getLastUpdatedBy(), metadataName + ServiceExceptionSingleParameters.LAST_UPDATED_BY, exceptions);
    }

    private static void checkStatisticalResource(StatisticalResource statisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        // StatisticalResourcesValidationUtils.checkMetadataRequired(statisticalResource.getUuid(), metadataName + ServiceExceptionSingleParameters.UUID, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // IDENTIFIABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewIdentifiableStatisticalResource(IdentifiableStatisticalResource identifiableStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(identifiableStatisticalResource, metadataName, exceptions);
        if (identifiableStatisticalResource == null) {
            return;
        }

        checkNewStatisticalResource(identifiableStatisticalResource, metadataName, exceptions);
        checkIdentifiableStatisticalResource(identifiableStatisticalResource, metadataName, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(identifiableStatisticalResource.getUrn(), metadataName + ServiceExceptionSingleParameters.URN, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(identifiableStatisticalResource.getUrn(), metadataName + ServiceExceptionSingleParameters.URI, exceptions);
    }

    protected static void checkExistingIdentifiableStatisticalResource(IdentifiableStatisticalResource identifiableStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(identifiableStatisticalResource, metadataName, exceptions);
        if (identifiableStatisticalResource == null) {
            return;
        }

        checkExistingStatisticalResource(identifiableStatisticalResource, metadataName, exceptions);
        checkIdentifiableStatisticalResource(identifiableStatisticalResource, metadataName, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(identifiableStatisticalResource.getUrn(), metadataName + ServiceExceptionSingleParameters.URN, exceptions);
    }

    private static void checkIdentifiableStatisticalResource(IdentifiableStatisticalResource identifiableStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(identifiableStatisticalResource.getCode(), metadataName + ServiceExceptionSingleParameters.CODE, exceptions);
        StatisticalResourcesValidationUtils.checkSemanticIdentifierAsIDType(identifiableStatisticalResource.getCode(), metadataName + ServiceExceptionSingleParameters.CODE, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // NAMEABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewNameableStatisticalResource(NameableStatisticalResource nameableStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(nameableStatisticalResource, metadataName, exceptions);
        if (nameableStatisticalResource == null) {
            return;
        }

        checkNewIdentifiableStatisticalResource(nameableStatisticalResource, metadataName, exceptions);
        checkNameableStatisticalResource(nameableStatisticalResource, metadataName, exceptions);
    }

    protected static void checkExistingNameableStatisticalResource(NameableStatisticalResource nameableStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(nameableStatisticalResource, metadataName, exceptions);
        if (nameableStatisticalResource == null) {
            return;
        }

        checkExistingIdentifiableStatisticalResource(nameableStatisticalResource, metadataName, exceptions);
        checkNameableStatisticalResource(nameableStatisticalResource, metadataName, exceptions);
    }

    private static void checkNameableStatisticalResource(NameableStatisticalResource nameableStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(nameableStatisticalResource.getTitle(), metadataName + ServiceExceptionSingleParameters.TITLE, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataOptionalIsValid(nameableStatisticalResource.getDescription(), metadataName + ServiceExceptionSingleParameters.DESCRIPTION, exceptions);
    }

    // ------------------------------------------------------------------------------------
    // VERSIONABLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewVersionableStatisticalResource(VersionableStatisticalResource versionableStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(versionableStatisticalResource, metadataName, exceptions);
        if (versionableStatisticalResource == null) {
            return;
        }

        checkNewNameableStatisticalResource(versionableStatisticalResource, metadataName, exceptions);
        checkVersionableStatisticalResource(versionableStatisticalResource, metadataName, exceptions);

        // Metadata that must be empty when the entity is created
        StatisticalResourcesValidationUtils.checkMetadataEmpty(versionableStatisticalResource.getVersionLogic(), metadataName + ServiceExceptionSingleParameters.VERSION_LOGIC, exceptions);
    }

    protected static void checkExistingVersionableStatisticalResource(VersionableStatisticalResource versionableStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(versionableStatisticalResource, metadataName, exceptions);
        if (versionableStatisticalResource == null) {
            return;
        }

        checkExistingNameableStatisticalResource(versionableStatisticalResource, metadataName, exceptions);
        checkVersionableStatisticalResource(versionableStatisticalResource, metadataName, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(versionableStatisticalResource.getVersionLogic(), metadataName + ServiceExceptionSingleParameters.VERSION_LOGIC, exceptions);
    }

    private static void checkVersionableStatisticalResource(VersionableStatisticalResource versionableStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataOptionalIsValid(versionableStatisticalResource.getVersionRationale(), metadataName + ServiceExceptionSingleParameters.VERSION_RATIONALE,
                exceptions);
    }

    // ------------------------------------------------------------------------------------
    // LIFE CYCLE STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewLifeCycleStatisticalResource(LifeCycleStatisticalResource lifeCycleStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(lifeCycleStatisticalResource, metadataName, exceptions);
        if (lifeCycleStatisticalResource == null) {
            return;
        }

        checkNewVersionableStatisticalResource(lifeCycleStatisticalResource, metadataName, exceptions);
        checkLifeCycleStatisticalResource(lifeCycleStatisticalResource, metadataName, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(lifeCycleStatisticalResource.getProcStatus(), metadataName + ServiceExceptionSingleParameters.PROC_STATUS, exceptions);
    }

    protected static void checkExistingLifeCycleStatisticalResource(LifeCycleStatisticalResource lifeCycleStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(lifeCycleStatisticalResource, metadataName, exceptions);
        if (lifeCycleStatisticalResource == null) {
            return;
        }

        checkExistingVersionableStatisticalResource(lifeCycleStatisticalResource, metadataName, exceptions);
        checkLifeCycleStatisticalResource(lifeCycleStatisticalResource, metadataName, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(lifeCycleStatisticalResource.getProcStatus(), metadataName + ServiceExceptionSingleParameters.PROC_STATUS, exceptions);
    }

    private static void checkLifeCycleStatisticalResource(LifeCycleStatisticalResource lifeCycleStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
    }

    // ------------------------------------------------------------------------------------
    // SIEMAC METADATA STATISTICAL RESOURCE
    // ------------------------------------------------------------------------------------

    protected static void checkNewSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource siemacMetadataStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(siemacMetadataStatisticalResource, metadataName, exceptions);
        if (siemacMetadataStatisticalResource == null) {
            return;
        }

        checkSiemacMetadataStatisticalResource(siemacMetadataStatisticalResource, metadataName, exceptions);
        checkNewLifeCycleStatisticalResource(siemacMetadataStatisticalResource, metadataName, exceptions);
    }

    protected static void checkExistingSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource siemacMetadataStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(siemacMetadataStatisticalResource, metadataName, exceptions);
        if (siemacMetadataStatisticalResource == null) {
            return;
        }

        checkSiemacMetadataStatisticalResource(siemacMetadataStatisticalResource, metadataName, exceptions);
        checkExistingLifeCycleStatisticalResource(siemacMetadataStatisticalResource, metadataName, exceptions);
    }

    private static void checkSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource siemacMetadataStatisticalResource, String metadataName, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataOptionalIsValid(siemacMetadataStatisticalResource.getStatisticalOperationInstance(),
                ServiceExceptionSingleParameters.STATISTICAL_OPERATION_INSTANCE, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataOptionalIsValid(siemacMetadataStatisticalResource.getCreator(), metadataName + ServiceExceptionSingleParameters.CREATOR, exceptions);
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(siemacMetadataStatisticalResource.getContributor(), metadataName + ServiceExceptionSingleParameters.CONTRIBUTOR,
                exceptions);
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(siemacMetadataStatisticalResource.getPublisher(), metadataName + ServiceExceptionSingleParameters.PUBLISHER, exceptions);
        StatisticalResourcesValidationUtils.checkListMetadataOptionalIsValid(siemacMetadataStatisticalResource.getMediator(), metadataName + ServiceExceptionSingleParameters.MEDIATOR, exceptions);
    }
}
