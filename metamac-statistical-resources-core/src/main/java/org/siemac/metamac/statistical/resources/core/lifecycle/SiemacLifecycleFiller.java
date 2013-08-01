package org.siemac.metamac.statistical.resources.core.lifecycle;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
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
        // FIXME: clear metadata computed in production validation and diffusion validation

    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void applySendToPublished(ServiceContext ctx, HasSiemacMetadata resource, HasSiemacMetadata previousResource) throws MetamacException {
        lifecycleFiller.applySendToPublishedActions(ctx, resource, previousResource);
        resource.getSiemacMetadataStatisticalResource().setCopyrightedDate(resource.getLifeCycleStatisticalResource().getValidFrom());

        // TODO: Metadatos de relaciones entre recursos
    }
}
