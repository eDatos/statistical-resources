package org.siemac.metamac.statistical.resources.core.lifecycle;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiemacLifecycleFiller {

    @Autowired
    private LifecycleFiller lifecycleFiller;

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void applySendToProductionValidationActions(ServiceContext ctx, HasSiemacMetadata resource) {
        lifecycleFiller.applySendToProductionValidationActions(ctx, resource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void applySendToDiffusionValidationActions(ServiceContext ctx, HasSiemacMetadata resource) {
        lifecycleFiller.applySendToDiffusionValidationActions(ctx, resource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public void applySendToValidationRejectedActions(ServiceContext ctx, HasSiemacMetadata resource) {
        lifecycleFiller.applySendToValidationRejectedActions(ctx, resource);
        // Do not need to do anything else because appliers of the above PROC_STATUS not fill any specific metadata
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void applySendToPublishedCurrentResourceActions(ServiceContext ctx, HasSiemacMetadata resource, HasSiemacMetadata previousResource) throws MetamacException {
        lifecycleFiller.applySendToPublishedCurrentResourceActions(ctx, resource, previousResource);
        resource.getSiemacMetadataStatisticalResource().setCopyrightedDate(resource.getLifeCycleStatisticalResource().getValidFrom().getYear());
    }

    public void applySendToPublishedPreviousResourceActions(ServiceContext ctx, HasSiemacMetadata resource, HasSiemacMetadata previousResource, RelatedResource currentAsRelatedResource)
            throws MetamacException {
        lifecycleFiller.applySendToPublishedPreviousResourceActions(ctx, resource, previousResource, currentAsRelatedResource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> CANCEL PUBLICATION
    // ------------------------------------------------------------------------------------------------------

    public void applyCancelPublicationCurrentResourceActions(ServiceContext ctx, HasSiemacMetadata resource, HasSiemacMetadata previousResource) throws MetamacException {
        lifecycleFiller.applyCancelPublicationCurrentResourceActions(ctx, resource, previousResource);
        resource.getSiemacMetadataStatisticalResource().setCopyrightedDate(null);
    }

    public void applyCancelPublicationPreviousResourceActions(ServiceContext ctx, HasSiemacMetadata resource, HasSiemacMetadata previousResource, RelatedResource currentAsRelatedResource)
            throws MetamacException {
        lifecycleFiller.applyCancelPublicationPreviousResourceActions(ctx, resource, previousResource, currentAsRelatedResource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    public void applyVersioningNewResourceActions(ServiceContext ctx, HasSiemacMetadata resource, HasSiemacMetadata previousResource, VersionTypeEnum versionType) throws MetamacException {
        lifecycleFiller.applyVersioningNewResourceActions(ctx, resource, previousResource, versionType);
        resource.getSiemacMetadataStatisticalResource().setLastUpdate(resource.getSiemacMetadataStatisticalResource().getCreationDate());
    }

    public void applyVersioningPreviousResourceActions(ServiceContext ctx, HasSiemacMetadata resource, HasSiemacMetadata previousResource, VersionTypeEnum versionType) throws MetamacException {
        lifecycleFiller.applyVersioningPreviousResourceActions(ctx, resource, previousResource, versionType);
    }
}
