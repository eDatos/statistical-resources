package org.siemac.metamac.statistical.resources.core.lifecycle;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.springframework.stereotype.Component;

@Component
public class LifecycleFiller {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void applySendToProductionValidationActions(ServiceContext ctx, HasLifecycleStatisticalResource resource) {
        resource.getLifeCycleStatisticalResource().setProductionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setProductionValidationUser(ctx.getUserId());
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void applySendToDiffusionValidationActions(ServiceContext ctx, HasLifecycleStatisticalResource resource) {
        resource.getLifeCycleStatisticalResource().setDiffusionValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setDiffusionValidationUser(ctx.getUserId());
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
    }

    // ------------------------------------------------------------------------------------------------------
    // VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public void applySendToValidationRejectedActions(ServiceContext ctx, HasLifecycleStatisticalResource resource) {
        resource.getLifeCycleStatisticalResource().setRejectValidationDate(new DateTime());
        resource.getLifeCycleStatisticalResource().setRejectValidationUser(ctx.getUserId());
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.VALIDATION_REJECTED);
    }

    // ------------------------------------------------------------------------------------------------------
    // PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void applySendToPublishedActions(ServiceContext ctx, HasLifecycleStatisticalResource resource, HasLifecycleStatisticalResource previousResource) throws MetamacException {
        DateTime publicationDate = new DateTime();
        
        // Actual version
        resource.getLifeCycleStatisticalResource().setPublicationDate(publicationDate);
        resource.getLifeCycleStatisticalResource().setPublicationUser(ctx.getUserId());
        resource.getLifeCycleStatisticalResource().setValidFrom(publicationDate);
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);

        // Previous version
        RelatedResource replacedByVersionResource = new RelatedResource();
        if (resource instanceof DatasetVersion) {
            replacedByVersionResource.setType(TypeRelatedResourceEnum.DATASET_VERSION);
            replacedByVersionResource.setDatasetVersion((DatasetVersion)resource);
        } else if (resource instanceof PublicationVersion) {
            replacedByVersionResource.setType(TypeRelatedResourceEnum.PUBLICATION_VERSION);
            replacedByVersionResource.setPublicationVersion((PublicationVersion)resource);
        } else {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Undefined resource type");
        }

        previousResource.getLifeCycleStatisticalResource().setIsReplacedByVersion(replacedByVersionResource);
        previousResource.getLifeCycleStatisticalResource().setValidTo(publicationDate);
    }
}
