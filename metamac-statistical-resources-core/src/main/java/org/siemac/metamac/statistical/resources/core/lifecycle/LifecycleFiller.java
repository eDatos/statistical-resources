package org.siemac.metamac.statistical.resources.core.lifecycle;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.enume.domain.VersionPatternEnum;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
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

    public void applySendToPublishedCurrentResourceActions(ServiceContext ctx, HasLifecycle resource, HasLifecycle previousVersion) throws MetamacException {
        if (!StatisticalResourcesVersionUtils.isInitialVersion(resource) && ValidationUtils.isEmpty(previousVersion)) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PREVIOUS_VERSION);
        }
        DateTime now = new DateTime();
        DateTime pubDate = resource.getLifeCycleStatisticalResource().getValidFrom().isBefore(now) ? resource.getLifeCycleStatisticalResource().getValidFrom() : now;
        // We don't need to fill validFrom because it is required for publish
        resource.getLifeCycleStatisticalResource().setPublicationDate(pubDate);
        resource.getLifeCycleStatisticalResource().setPublicationUser(ctx.getUserId());
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
    }

    public void applySendToPublishedPreviousResourceActions(ServiceContext ctx, HasLifecycle resource, HasLifecycle previousVersion, RelatedResource currentAsRelatedResource) throws MetamacException {
        DateTime publicationDate = resource.getLifeCycleStatisticalResource().getValidFrom();
        previousVersion.getLifeCycleStatisticalResource().setValidTo(publicationDate);
    }

    // ------------------------------------------------------------------------------------------------------
    // VERSIONING
    // ------------------------------------------------------------------------------------------------------

    public void applyVersioningNewResourceActions(ServiceContext ctx, HasLifecycle resource, HasLifecycle previousVersion, VersionTypeEnum versionType) throws MetamacException {
        if (ValidationUtils.isEmpty(previousVersion)) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PREVIOUS_VERSION);
        }
        RelatedResource previousVersionRelatedResource = RelatedResourceUtils.createRelatedResourceForHasLifecycleResource(previousVersion);

        LifeCycleStatisticalResource lifeCycleResource = resource.getLifeCycleStatisticalResource();

        // Set metadata for actual version
        lifeCycleResource.setReplacesVersion(previousVersionRelatedResource);
        lifeCycleResource.setProcStatus(ProcStatusEnum.DRAFT);
        lifeCycleResource.setCreationDate(new DateTime());
        lifeCycleResource.setCreationUser(ctx.getUserId());
        lifeCycleResource.setLastVersion(true);
        lifeCycleResource.setVersionLogic(VersionUtil.createNextVersion(previousVersion.getLifeCycleStatisticalResource().getVersionLogic(), VersionPatternEnum.XXX_YYY, versionType));

        // Empty metadata
        lifeCycleResource.setProductionValidationDate(null);
        lifeCycleResource.setProductionValidationUser(null);
        lifeCycleResource.setDiffusionValidationDate(null);
        lifeCycleResource.setDiffusionValidationUser(null);
        lifeCycleResource.setRejectValidationDate(null);
        lifeCycleResource.setRejectValidationUser(null);
        lifeCycleResource.setPublicationDate(null);
        lifeCycleResource.setPublicationUser(null);
        lifeCycleResource.setValidFrom(null);
    }

    public void applyVersioningPreviousResourceActions(ServiceContext ctx, HasLifecycle resource, HasLifecycle previousVersion, VersionTypeEnum versionType) throws MetamacException {
        if (ValidationUtils.isEmpty(previousVersion)) {
            throw new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PREVIOUS_VERSION);
        }
        previousVersion.getLifeCycleStatisticalResource().setLastVersion(false);
    }
}
