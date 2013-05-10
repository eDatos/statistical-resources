package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataRequired;
import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.List;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LifecycleChecker {

    private static final int               PROCESSING_MINUTES_DELAY = 30;

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToProductionValidation(HasLifecycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED);

        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);

        checkLifeCycleMetadataSendToProductionValidation(resource, metadataName, exceptionItems);
    }

    private void checkLifeCycleMetadataSendToProductionValidation(HasLifecycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        if (isFirstVersion(resource)) {
            if (!checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(resource)) {
                exceptionItems.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_INCORRECT, addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)));
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToDiffusionValidation(HasLifecycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, ProcStatusEnum.PRODUCTION_VALIDATION);
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToValidationRejected(HasLifecycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, ProcStatusEnum.PRODUCTION_VALIDATION, ProcStatusEnum.DIFFUSION_VALIDATION);
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToPublished(HasLifecycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, ProcStatusEnum.DIFFUSION_VALIDATION);
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
        checkMetadataRequired(resource.getLifeCycleStatisticalResource().getValidFrom(), addParameter(metadataName, ServiceExceptionSingleParameters.VALID_FROM), exceptionItems);

        if (resource.getLifeCycleStatisticalResource().getValidFrom() != null && (resource.getLifeCycleStatisticalResource().getValidFrom().plusMinutes(PROCESSING_MINUTES_DELAY)).isBeforeNow()) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT, addParameter(metadataName, ServiceExceptionSingleParameters.VALID_FROM)));
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // PROTECTED COMMON METHODS
    // ------------------------------------------------------------------------------------------------------

    protected boolean isFirstVersion(HasLifecycleStatisticalResource resource) {
        return VersionUtil.isInitialVersion(resource.getLifeCycleStatisticalResource().getVersionLogic());
    }

    protected boolean checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(HasLifecycleStatisticalResource resource) {
        if (resource.getLifeCycleStatisticalResource().getVersionRationaleTypes().size() == 1) {
            return VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE.equals(resource.getLifeCycleStatisticalResource().getVersionRationaleTypes().get(0).getValue());
        } else if (resource.getLifeCycleStatisticalResource().getVersionRationaleTypes().size() == 0) {
            return true;
        }
        return false;
    }

    /*
     * This is a metadata core that should always be checked, this validations are always needed independently of the action
     */
    protected void checkLifeCycleMetadataAllActions(HasLifecycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(resource, metadataName, exceptionItems);
    }
}
