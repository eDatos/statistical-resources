package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.serviceimpl.validators.QueryServiceInvocationValidatorImpl;
import org.springframework.stereotype.Component;

@Component
public class QueryLifecycleServiceInvocationValidator extends LifecycleInvocationValidatorBase<QueryVersion> {

    @Override
    public void checkSendToProductionValidationInternal(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptionItems) {
        QueryServiceInvocationValidatorImpl.checkExistingQueryVersion(resource, exceptionItems);
    }

    @Override
    public void checkSendToDiffusionValidationInternal(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptionItems) {
        QueryServiceInvocationValidatorImpl.checkExistingQueryVersion(resource, exceptionItems);
    }

    @Override
    public void checkSendToValidationRejectedInternal(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptionItems) {
        QueryServiceInvocationValidatorImpl.checkExistingQueryVersion(resource, exceptionItems);
    }

    @Override
    protected void checkSendToPublishedInternal(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptionItems) {
        QueryServiceInvocationValidatorImpl.checkExistingQueryVersion(resource, exceptionItems);
    }

    @Override
    protected void checkVersioningInternal(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptionItems) {
        QueryServiceInvocationValidatorImpl.checkExistingQueryVersion(resource, exceptionItems);
    }
}
