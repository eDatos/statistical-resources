package org.siemac.metamac.statistical.resources.core.lifecycle;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;
import org.springframework.stereotype.Component;

@Component
public class LifecycleFiller {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void applySendToProductionValidationActions(ServiceContext ctx, HasLifecycle resource) {
        resource.getLifeCycleStatisticalResource().setProductionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setProductionValidationUser(ctx.getUserId());
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void applySendToDiffusionValidationActions(ServiceContext ctx, HasLifecycle resource) {
        resource.getLifeCycleStatisticalResource().setDiffusionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setDiffusionValidationUser(ctx.getUserId());
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
    }

    // ------------------------------------------------------------------------------------------------------
    // VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public void applySendToValidationRejectedActions(ServiceContext ctx, HasLifecycle resource) {
        resource.getLifeCycleStatisticalResource().setRejectValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setRejectValidationUser(ctx.getUserId());
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.VALIDATION_REJECTED);
    }

    // ------------------------------------------------------------------------------------------------------
    // PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void applySendToPublishedActions(ServiceContext ctx, HasLifecycle resource, HasLifecycle previousVersion) throws MetamacException {
        if (!StatisticalResourcesVersionUtils.isInitialVersion(resource) && ValidationUtils.isEmpty(previousVersion)) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PREVIOUS_VERSION);
        }
        
        DateTime publicationDate = new DateTime();
        
        // Actual version
        resource.getLifeCycleStatisticalResource().setPublicationDate(publicationDate);
        resource.getLifeCycleStatisticalResource().setPublicationUser(ctx.getUserId());
        resource.getLifeCycleStatisticalResource().setValidFrom(publicationDate);
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

        // Previous version
        applySendToPublishedActionsIfExistsPreviousVersion(resource, previousVersion, publicationDate);
    }

    private void applySendToPublishedActionsIfExistsPreviousVersion(HasLifecycle resource, HasLifecycle previousVersion, DateTime publicationDate) throws MetamacException {
        if (previousVersion != null) {
            RelatedResource previousVersionRelatedResource = new RelatedResource();
            if (resource instanceof DatasetVersion) {
                previousVersionRelatedResource.setType(TypeRelatedResourceEnum.DATASET_VERSION);
                previousVersionRelatedResource.setDatasetVersion((DatasetVersion)resource);
            } else if (resource instanceof PublicationVersion) {
                previousVersionRelatedResource.setType(TypeRelatedResourceEnum.PUBLICATION_VERSION);
                previousVersionRelatedResource.setPublicationVersion((PublicationVersion)resource);
            } else {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Undefined resource type");
            }
    
            resource.getLifeCycleStatisticalResource().setReplacesVersion(previousVersionRelatedResource);
            
            previousVersion.getLifeCycleStatisticalResource().setIsReplacedByVersion(previousVersionRelatedResource);
            previousVersion.getLifeCycleStatisticalResource().setValidTo(publicationDate);
        }
    }
}
