package org.siemac.metamac.statistical.resources.core.base.serviceimpl;

import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataEmpty;
import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataRequired;
import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.base.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceNextVersionEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtils;
import org.springframework.stereotype.Service;

@Service("lifecycleService")
public class LifecycleServiceImpl implements LifecycleService {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    public void checkSendToProductionValidation(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.DRAFT, StatisticalResourceProcStatusEnum.VALIDATION_REJECTED);

        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);

        checkLifeCycleMetadataSendToProductionValidation(resource, metadataName, exceptionItems);
    }

    @Override
    public void applySendToProductionValidationActions(ServiceContext ctx, LifeCycleStatisticalResource resource) {
        // It's neccesary because the resource may have been rejected
        resource.setDiffusionValidationDate(null);
        resource.setDiffusionValidationUser(null);
        
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

    @Override
    public void checkSendToDiffusionValidation(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION);
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
    }

    @Override
    public void applySendToDiffusionValidationActions(ServiceContext ctx, LifeCycleStatisticalResource resource) {
        resource.setDiffusionValidationDate(new DateTime());
        resource.setDiffusionValidationUser(ctx.getUserId());
        resource.setProcStatus(StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION);
    }

    // ------------------------------------------------------------------------------------------------------
    // VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    public void checkSendToValidationRejected(LifeCycleStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION, StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION);
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
    }

    @Override
    public void applySendToValidationRejectedActions(ServiceContext ctx, LifeCycleStatisticalResource resource) {
        resource.setRejectValidationDate(new DateTime());
        resource.setRejectValidationUser(ctx.getUserId());
        resource.setProcStatus(StatisticalResourceProcStatusEnum.VALIDATION_REJECTED);
    }
    
    // ------------------------------------------------------------------------------------------------------
    // PROTECTED COMMON METHODS
    // ------------------------------------------------------------------------------------------------------

    protected boolean isFirstVersion(LifeCycleStatisticalResource resource) {
        return !StringUtils.isEmpty(resource.getVersionLogic()) && "01.000".equals(resource.getVersionLogic());
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
        checkMetadataRequired(resource.getCode(), addParameter(metadataName, ServiceExceptionSingleParameters.CODE), exceptionItems);
        checkMetadataRequired(resource.getUrn(), addParameter(metadataName, ServiceExceptionSingleParameters.URN), exceptionItems);

        checkMetadataRequired(resource.getTitle(), addParameter(metadataName, ServiceExceptionSingleParameters.TITLE), exceptionItems);
        checkMetadataRequired(resource.getDescription(), addParameter(metadataName, ServiceExceptionSingleParameters.DESCRIPTION), exceptionItems);

        checkMetadataRequired(resource.getVersionLogic(), addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_LOGIC), exceptionItems);
        checkMetadataRequired(resource.getVersionRationaleTypes(), addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES), exceptionItems);
        for (VersionRationaleType versionRationaleType : resource.getVersionRationaleTypes()) {
            if (StatisticalResourceVersionRationaleTypeEnum.MINOR_ERRATA.equals(versionRationaleType.getValue())) {
                checkMetadataRequired(resource.getVersionRationale(), addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE), exceptionItems);
            }
        }

        checkMetadataRequired(resource.getNextVersion(), addParameter(metadataName, ServiceExceptionSingleParameters.NEXT_VERSION), exceptionItems);
        if (resource.getNextVersion() != null && !StatisticalResourceNextVersionEnum.SCHEDULED_UPDATE.equals(resource.getNextVersion())) {
            checkMetadataEmpty(resource.getNextVersionDate(), addParameter(metadataName, ServiceExceptionSingleParameters.NEXT_VERSION_DATE), exceptionItems);
        }
        checkMetadataRequired(resource.getProcStatus(), addParameter(metadataName, ServiceExceptionSingleParameters.PROC_STATUS), exceptionItems);
    }
}
