package org.siemac.metamac.statistical.resources.core.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class LifecycleTestUtils {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public static void prepareToProductionValidation(HasSiemacMetadata resource) {
        prepareToProductionValidation((HasLifecycle) resource);

        prepareToLifecycleCommonSiemacResource(resource);
    }

    public static void prepareToProductionValidation(HasLifecycle resource) {
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);

        prepareToLifecycleCommonLifeCycleResource(resource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public static void prepareToDiffusionValidation(HasSiemacMetadata resource) {
        prepareToProductionValidation(resource);
        prepareToDiffusionValidation((HasLifecycle) resource);
    }

    public static void prepareToDiffusionValidation(HasLifecycle resource) {
        prepareToProductionValidation(resource);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = resource.getLifeCycleStatisticalResource();

        lifeCycleStatisticalResource.setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        if (lifeCycleStatisticalResource.getProductionValidationDate() == null) {
            lifeCycleStatisticalResource.setProductionValidationDate(new DateTime());
        }
        if (lifeCycleStatisticalResource.getProductionValidationUser() == null) {
            lifeCycleStatisticalResource.setProductionValidationUser("PRODUCTION_VALIDATION_USER");
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public static void prepareToValidationRejected(HasSiemacMetadata resource) {
        prepareToProductionValidation(resource);
        prepareToValidationRejected((HasLifecycle) resource);
    }

    public static void prepareToValidationRejected(HasLifecycle resource) {
        prepareToProductionValidation(resource);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = resource.getLifeCycleStatisticalResource();

        lifeCycleStatisticalResource.setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        if (lifeCycleStatisticalResource.getProductionValidationDate() == null) {
            lifeCycleStatisticalResource.setProductionValidationDate(new DateTime());
        }
        if (lifeCycleStatisticalResource.getProductionValidationUser() == null) {
            lifeCycleStatisticalResource.setProductionValidationUser("PRODUCTION_VALIDATION_USER");
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public static void prepareToPublished(HasSiemacMetadata resource) {
        prepareToDiffusionValidation(resource);

        prepareToPublished((HasLifecycle) resource);
    }

    public static void prepareToPublished(HasLifecycle resource) {
        prepareToDiffusionValidation(resource);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = resource.getLifeCycleStatisticalResource();

        lifeCycleStatisticalResource.setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);

        if (lifeCycleStatisticalResource.getDiffusionValidationDate() == null) {
            lifeCycleStatisticalResource.setDiffusionValidationDate(new DateTime());
        }
        if (lifeCycleStatisticalResource.getDiffusionValidationUser() == null) {
            lifeCycleStatisticalResource.setDiffusionValidationUser("DIFFUSION_VALIDATION_USER");
        }
        if (lifeCycleStatisticalResource.getValidFrom() == null) {
            lifeCycleStatisticalResource.setValidFrom(new DateTime());
        }
    }

    public static void createPublished(HasSiemacMetadata resource) {
        prepareToPublished(resource);
        createPublished((HasLifecycle) resource);
    }

    public static void createPublished(HasLifecycle resource) {
        prepareToPublished(resource);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = resource.getLifeCycleStatisticalResource();

        lifeCycleStatisticalResource.setProcStatus(ProcStatusEnum.PUBLISHED);

        if (lifeCycleStatisticalResource.getPublicationDate() == null) {
            lifeCycleStatisticalResource.setPublicationDate(new DateTime());
        }

        if (lifeCycleStatisticalResource.getValidFrom() == null) {
            lifeCycleStatisticalResource.setValidFrom(new DateTime());
        }
        if (lifeCycleStatisticalResource.getPublicationUser() == null) {
            lifeCycleStatisticalResource.setPublicationUser("PUBLICATION_VALIDATION_USER");
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    public static void createVersioned(HasSiemacMetadata resource) {
        createVersioned((HasLifecycle) resource);
    }

    public static void createVersioned(HasLifecycle resource) {
        LifeCycleStatisticalResource lifeCycleStatisticalResource = resource.getLifeCycleStatisticalResource();

        if (lifeCycleStatisticalResource.getStatisticalOperation() == null) {
            ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationExternalItem();
            lifeCycleStatisticalResource.setStatisticalOperation(operation);
        }

        if (lifeCycleStatisticalResource.getVersionLogic() == null) {
            lifeCycleStatisticalResource.setVersionLogic("002.000");
        }

        if (lifeCycleStatisticalResource.getVersionRationaleTypes().isEmpty()) {
            lifeCycleStatisticalResource.addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_CATEGORIES));
        }

        if (lifeCycleStatisticalResource.getNextVersion() == null) {
            lifeCycleStatisticalResource.setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);
        }

        if (lifeCycleStatisticalResource.getTitle() == null) {
            lifeCycleStatisticalResource.setTitle(StatisticalResourcesPersistedDoMocks.mockInternationalString());
        }

        if (lifeCycleStatisticalResource.getDescription() == null) {
            lifeCycleStatisticalResource.setDescription(StatisticalResourcesPersistedDoMocks.mockInternationalString());
        }

        if (lifeCycleStatisticalResource.getMaintainer() == null) {
            lifeCycleStatisticalResource.setMaintainer(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        }

        lifeCycleStatisticalResource.setProcStatus(ProcStatusEnum.DRAFT);

        if (lifeCycleStatisticalResource.getCreationDate() == null) {
            lifeCycleStatisticalResource.setCreationDate(new DateTime());
        }

        if (lifeCycleStatisticalResource.getCreationUser() == null) {
            lifeCycleStatisticalResource.setCreationUser("VERSIONED_USER");
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> COMMON UTILS
    // ------------------------------------------------------------------------------------------------------

    public static void fillOptionalExternalItemsSiemacResource(HasSiemacMetadata resource) {
        resource.getSiemacMetadataStatisticalResource().getStatisticalOperationInstances().clear();
        resource.getSiemacMetadataStatisticalResource().addStatisticalOperationInstance(StatisticalResourcesPersistedDoMocks.mockStatisticalOperationInstanceExternalItem());
        resource.getSiemacMetadataStatisticalResource().addStatisticalOperationInstance(StatisticalResourcesPersistedDoMocks.mockStatisticalOperationInstanceExternalItem());

        resource.getSiemacMetadataStatisticalResource().getContributor().clear();
        resource.getSiemacMetadataStatisticalResource().addContributor(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());
        resource.getSiemacMetadataStatisticalResource().addContributor(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());

        resource.getSiemacMetadataStatisticalResource().getPublisherContributor().clear();
        resource.getSiemacMetadataStatisticalResource().addPublisherContributor(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());
        resource.getSiemacMetadataStatisticalResource().addPublisherContributor(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());

        resource.getSiemacMetadataStatisticalResource().getMediator().clear();
        resource.getSiemacMetadataStatisticalResource().addMediator(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());
        resource.getSiemacMetadataStatisticalResource().addMediator(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());
    }

    private static void prepareToLifecycleCommonSiemacResource(HasSiemacMetadata resource) {
        SiemacMetadataStatisticalResource siemacResource = resource.getSiemacMetadataStatisticalResource();

        if (siemacResource.getLanguage() == null) {
            siemacResource.setLanguage(StatisticalResourcesPersistedDoMocks.mockCodeExternalItem());
        }

        if (siemacResource.getLanguages().isEmpty()) {
            siemacResource.addLanguage(StatisticalResourcesPersistedDoMocks.mockCodeExternalItem(siemacResource.getLanguage().getCode()));
            siemacResource.addLanguage(StatisticalResourcesPersistedDoMocks.mockCodeExternalItem());
        }

        if (siemacResource.getCommonMetadata() == null) {
            siemacResource.setCommonMetadata(StatisticalResourcesPersistedDoMocks.mockCommonConfigurationExternalItem());
        }

        if (siemacResource.getCreator() == null) {
            siemacResource.setCreator(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());
        }

        if (siemacResource.getLastUpdate() == null) {
            siemacResource.setLastUpdate(new DateTime().minusMinutes(10));
        }

        if (siemacResource.getPublisher().isEmpty()) {
            siemacResource.addPublisher(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());
        }
    }

    private static void prepareToLifecycleCommonLifeCycleResource(HasLifecycle resource) {
        LifeCycleStatisticalResource lifeCycleStatisticalResource = resource.getLifeCycleStatisticalResource();

        if (lifeCycleStatisticalResource.getStatisticalOperation() == null) {
            ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationExternalItem();
            lifeCycleStatisticalResource.setStatisticalOperation(operation);
        }

        if (lifeCycleStatisticalResource.getVersionLogic() == null) {
            lifeCycleStatisticalResource.setVersionLogic("001.000");
        }

        if (lifeCycleStatisticalResource.getVersionRationaleTypes().isEmpty()) {
            lifeCycleStatisticalResource.addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE));
        }

        if (lifeCycleStatisticalResource.getNextVersion() == null) {
            lifeCycleStatisticalResource.setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);
        }

        // ReplacesVersion can not be filled because depends of the type: DatasetVersion, PublicationVersion or QueryVersion

        if (lifeCycleStatisticalResource.getTitle() == null) {
            lifeCycleStatisticalResource.setTitle(StatisticalResourcesPersistedDoMocks.mockInternationalString());
        }

        if (lifeCycleStatisticalResource.getDescription() == null) {
            lifeCycleStatisticalResource.setDescription(StatisticalResourcesPersistedDoMocks.mockInternationalString());
        }

        if (lifeCycleStatisticalResource.getMaintainer() == null) {
            lifeCycleStatisticalResource.setMaintainer(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        }
    }
}
