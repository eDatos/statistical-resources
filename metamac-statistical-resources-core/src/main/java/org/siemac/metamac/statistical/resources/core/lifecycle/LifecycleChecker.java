package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LifecycleChecker {

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToProductionValidation(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.DRAFT, StatisticalResourceProcStatusEnum.VALIDATION_REJECTED);

        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);

        checkLifeCycleMetadataSendToProductionValidation(resource, metadataName, exceptionItems);
    }

    public void applySendToProductionValidationActions(ServiceContext ctx, LifeCycleStatisticalResource resource) {
        resource.setProductionValidationDate(new DateTime());
        resource.setProductionValidationUser(ctx.getUserId());
        resource.setProcStatus(StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION);
    }

    private void checkLifeCycleMetadataSendToProductionValidation(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        if (isFirstVersion(resource)) {
            if (!checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(resource)) {
                exceptionItems.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_INCORRECT, addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)));
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToDiffusionValidation(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION);
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
    }

    public void applySendToDiffusionValidationActions(ServiceContext ctx, LifeCycleStatisticalResource resource) {
        resource.setDiffusionValidationDate(new DateTime());
        resource.setDiffusionValidationUser(ctx.getUserId());
        resource.setProcStatus(StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION);
    }

    // ------------------------------------------------------------------------------------------------------
    // VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToValidationRejected(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION, StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION);
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
    }

    public void applySendToValidationRejectedActions(ServiceContext ctx, LifeCycleStatisticalResource resource) {
        resource.setRejectValidationDate(new DateTime());
        resource.setRejectValidationUser(ctx.getUserId());
        resource.setProcStatus(StatisticalResourceProcStatusEnum.VALIDATION_REJECTED);
    }

    // ------------------------------------------------------------------------------------------------------
    // PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToPublished(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION);
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
    }

    public void applySendToPublishedActions(ServiceContext ctx, LifeCycleStatisticalResource resource) {
        resource.setPublicationDate(new DateTime());
        resource.setPublicationUser(ctx.getUserId());
        resource.setProcStatus(StatisticalResourceProcStatusEnum.PUBLISHED);
    }

    // ------------------------------------------------------------------------------------------------------
    // PROTECTED COMMON METHODS
    // ------------------------------------------------------------------------------------------------------

    protected boolean isFirstVersion(LifeCycleStatisticalResource resource) {
        return VersionUtil.isInitialVersion(resource.getVersionLogic());
    }

    protected boolean checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(LifeCycleStatisticalResource resource) {
        if (resource.getVersionRationaleTypes().size() == 1) {
            return StatisticalResourceVersionRationaleTypeEnum.MAJOR_NEW_RESOURCE.equals(resource.getVersionRationaleTypes().get(0).getValue());
        } else if (resource.getVersionRationaleTypes().size() == 0) {
            return true;
        }
        return false;
    }

    /*
     * This is a metadata core that should always be checked, this validations are always needed independently of the action
     */
    protected void checkLifeCycleMetadataAllActions(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(resource, metadataName, exceptionItems);
    }
}
