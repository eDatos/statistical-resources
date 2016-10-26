package org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;
import org.springframework.stereotype.Component;

@Component
public class LifecycleInvocationValidatorBase {

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToProductionValidation(ServiceContext ctx, String urn) throws MetamacException {
        onlyUrnRequired(urn);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToDiffusionValidation(ServiceContext ctx, String urn) throws MetamacException {
        onlyUrnRequired(urn);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToValidationRejected(ServiceContext ctx, String urn) throws MetamacException {
        onlyUrnRequired(urn);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToPublished(ServiceContext ctx, String urn) throws MetamacException {
        onlyUrnRequired(urn);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    public void checkVersioning(ServiceContext ctx, String urn) throws MetamacException {
        onlyUrnRequired(urn);
    }

    protected void onlyUrnRequired(String urn) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();
        StatisticalResourcesValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);
        ExceptionUtils.throwIfException(exceptions);
    }
}
