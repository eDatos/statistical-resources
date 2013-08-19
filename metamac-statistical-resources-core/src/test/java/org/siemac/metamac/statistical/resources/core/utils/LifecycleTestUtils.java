package org.siemac.metamac.statistical.resources.core.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
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
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        resource.getLifeCycleStatisticalResource().setProductionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setProductionValidationUser("PRODUCTION_VALIDATION_USER");
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
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        resource.getLifeCycleStatisticalResource().setProductionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setProductionValidationUser("PRODUCTION_VALIDATION_USER");
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
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        resource.getLifeCycleStatisticalResource().setDiffusionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setDiffusionValidationUser("DIFFUSION_VALIDATION_USER");
        resource.getLifeCycleStatisticalResource().setValidFrom(new DateTime());
    }

    public static void createPublished(HasSiemacMetadata resource) {
        prepareToPublished(resource);
        createPublished((HasLifecycle) resource);
    }

    public static void createPublished(HasLifecycle resource) {
        prepareToPublished(resource);

        DateTime publicationDate = new DateTime();
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
        resource.getLifeCycleStatisticalResource().setPublicationDate(publicationDate);
        resource.getLifeCycleStatisticalResource().setValidFrom(publicationDate);
        resource.getLifeCycleStatisticalResource().setPublicationUser("PUBLICATION_VALIDATION_USER");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    public static void createVersioned(HasSiemacMetadata resource) {
        createVersioned((HasLifecycle) resource);
    }

    public static void createVersioned(HasLifecycle resource) {
        ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationExternalItem();
        resource.getLifeCycleStatisticalResource().setStatisticalOperation(operation);

        resource.getLifeCycleStatisticalResource().setVersionLogic("002.000");
        resource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
        resource.getLifeCycleStatisticalResource().addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_CATEGORIES));
        resource.getLifeCycleStatisticalResource().setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);
        resource.getLifeCycleStatisticalResource().setNextVersionDate(null);

        resource.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesPersistedDoMocks.mockInternationalString());
        resource.getLifeCycleStatisticalResource().setDescription(StatisticalResourcesPersistedDoMocks.mockInternationalString());

        resource.getLifeCycleStatisticalResource().setMaintainer(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());

        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);
        resource.getLifeCycleStatisticalResource().setCreationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setCreationUser("VERSIONED_USER");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> COMMON UTILS
    // ------------------------------------------------------------------------------------------------------

    private static void prepareToLifecycleCommonSiemacResource(HasSiemacMetadata resource) {
        resource.getSiemacMetadataStatisticalResource().setLanguage(StatisticalResourcesPersistedDoMocks.mockCodeExternalItem());
        resource.getSiemacMetadataStatisticalResource().getLanguages().clear();
        resource.getSiemacMetadataStatisticalResource().addLanguage(StatisticalResourcesPersistedDoMocks.mockCodeExternalItem());
        resource.getSiemacMetadataStatisticalResource().addLanguage(StatisticalResourcesPersistedDoMocks.mockCodeExternalItem());

        resource.getSiemacMetadataStatisticalResource().setCommonMetadata(StatisticalResourcesPersistedDoMocks.mockCommonConfigurationExternalItem());

        resource.getSiemacMetadataStatisticalResource().setCreator(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());
        resource.getSiemacMetadataStatisticalResource().setLastUpdate(new DateTime().minusMinutes(10));

        resource.getSiemacMetadataStatisticalResource().getPublisher().clear();
        resource.getSiemacMetadataStatisticalResource().addPublisher(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());
    }

    private static void prepareToLifecycleCommonLifeCycleResource(HasLifecycle resource) {

        if (resource.getLifeCycleStatisticalResource().getStatisticalOperation() == null) {
            ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationExternalItem();
            resource.getLifeCycleStatisticalResource().setStatisticalOperation(operation);
        }

        resource.getLifeCycleStatisticalResource().setVersionLogic("001.000");
        resource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
        resource.getLifeCycleStatisticalResource().addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE));
        resource.getLifeCycleStatisticalResource().setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);
        resource.getLifeCycleStatisticalResource().setNextVersionDate(null);
        // ReplacesVersion can not be filled because depends of the type: DatasetVersion, PublicationVersion or QueryVersion

        resource.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesPersistedDoMocks.mockInternationalString());
        resource.getLifeCycleStatisticalResource().setDescription(StatisticalResourcesPersistedDoMocks.mockInternationalString());

        resource.getLifeCycleStatisticalResource().setMaintainer(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
    }
}
