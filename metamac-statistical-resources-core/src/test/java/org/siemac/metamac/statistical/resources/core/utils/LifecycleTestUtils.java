package org.siemac.metamac.statistical.resources.core.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class LifecycleTestUtils {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    
    public static void prepareToProductionValidation(HasSiemacMetadataStatisticalResource resource) {
        prepareToProductionValidation((HasLifecycleStatisticalResource)resource);
        
        prepareToLifecycleCommonSiemacResource(resource);
    }

    public static void prepareToProductionValidation(HasLifecycleStatisticalResource resource) {
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);
        
        prepareToLifecycleCommonLifeCycleResource(resource);
    }
    
    
    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    
    public static void prepareToDiffusionValidation(HasSiemacMetadataStatisticalResource resource) {
        prepareToProductionValidation(resource);
        prepareToDiffusionValidation((HasLifecycleStatisticalResource)resource);
    }
    
    public static void prepareToDiffusionValidation(HasLifecycleStatisticalResource resource) {
        prepareToProductionValidation(resource);
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        resource.getLifeCycleStatisticalResource().setProductionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setProductionValidationUser("PRODUCTION_VALIDATION_USER");
    }


    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------
    
    public static void prepareToValidationRejected(HasSiemacMetadataStatisticalResource resource) {
        prepareToProductionValidation(resource);
        prepareToValidationRejected((HasLifecycleStatisticalResource)resource);
    }
    
    public static void prepareToValidationRejected(HasLifecycleStatisticalResource resource) {
        prepareToProductionValidation(resource);
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        resource.getLifeCycleStatisticalResource().setProductionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setProductionValidationUser("PRODUCTION_VALIDATION_USER");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------
    
    public static void prepareToPublished(HasSiemacMetadataStatisticalResource resource) {
        prepareToDiffusionValidation(resource);
        
        prepareToPublished((HasLifecycleStatisticalResource)resource);
    }
    
    public static void prepareToPublished(HasLifecycleStatisticalResource resource) {
        prepareToDiffusionValidation(resource);
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        resource.getLifeCycleStatisticalResource().setDiffusionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setDiffusionValidationUser("DIFFUSION_VALIDATION_USER");
        resource.getLifeCycleStatisticalResource().setValidFrom(new DateTime());
    }
    
    public static void createPublished(HasSiemacMetadataStatisticalResource resource) {
        prepareToPublished(resource);
        createPublished((HasLifecycleStatisticalResource)resource);
    }
    
    public static void createPublished(HasLifecycleStatisticalResource resource) {
        prepareToPublished(resource);
        
        DateTime publicationDate = new DateTime();
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
        resource.getLifeCycleStatisticalResource().setPublicationDate(publicationDate);
        resource.getLifeCycleStatisticalResource().setValidFrom(publicationDate);
        resource.getLifeCycleStatisticalResource().setPublicationUser("PUBLICATION_VALIDATION_USER");
    }

    
    private static void prepareToLifecycleCommonSiemacResource(HasSiemacMetadataStatisticalResource resource) {
        resource.getSiemacMetadataStatisticalResource().setLanguage(StatisticalResourcesPersistedDoMocks.mockCodeExternalItem());
        resource.getSiemacMetadataStatisticalResource().getLanguages().clear();
        resource.getSiemacMetadataStatisticalResource().addLanguage(StatisticalResourcesPersistedDoMocks.mockCodeExternalItem());
        resource.getSiemacMetadataStatisticalResource().addLanguage(StatisticalResourcesPersistedDoMocks.mockCodeExternalItem());

        ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationItem();
        resource.getSiemacMetadataStatisticalResource().setStatisticalOperation(operation);

        resource.getSiemacMetadataStatisticalResource().setMaintainer(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        resource.getSiemacMetadataStatisticalResource().setCreator(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());
        resource.getSiemacMetadataStatisticalResource().setLastUpdate(new DateTime().minusMinutes(10));

        resource.getSiemacMetadataStatisticalResource().getPublisher().clear();
        resource.getSiemacMetadataStatisticalResource().addPublisher(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());

        resource.getSiemacMetadataStatisticalResource().setRightsHolder(StatisticalResourcesPersistedDoMocks.mockOrganizationUnitExternalItem());
        resource.getSiemacMetadataStatisticalResource().setLicense(StatisticalResourcesPersistedDoMocks.mockInternationalString());

    }

    private static void prepareToLifecycleCommonLifeCycleResource(HasLifecycleStatisticalResource resource) {
        resource.getLifeCycleStatisticalResource().setVersionLogic("002.000");
        resource.getLifeCycleStatisticalResource().getVersionRationaleTypes().clear();
        resource.getLifeCycleStatisticalResource().addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnum.MINOR_DATA_UPDATE));
        resource.getLifeCycleStatisticalResource().setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);

        resource.getLifeCycleStatisticalResource().setTitle(StatisticalResourcesPersistedDoMocks.mockInternationalString());
        resource.getLifeCycleStatisticalResource().setDescription(StatisticalResourcesPersistedDoMocks.mockInternationalString());
    }

}
