package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.publication;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.serviceimpl.validators.PublicationServiceInvocationValidatorImpl;
import org.springframework.stereotype.Component;

@Component
public class PublicationLifecycleServiceInvocationValidator extends LifecycleInvocationValidatorBase<PublicationVersion> {

    @Override
    public void checkSendToProductionValidationInternal(ServiceContext ctx, PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) {
        PublicationServiceInvocationValidatorImpl.checkExistingPublicationVersion(resource, exceptionItems);
    }

    @Override
    public void checkSendToDiffusionValidationInternal(ServiceContext ctx, PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) {
        PublicationServiceInvocationValidatorImpl.checkExistingPublicationVersion(resource, exceptionItems);
    }

    @Override
    public void checkSendToValidationRejectedInternal(ServiceContext ctx, PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) {
        PublicationServiceInvocationValidatorImpl.checkExistingPublicationVersion(resource, exceptionItems);
    }

    @Override
    protected void checkSendToPublishedInternal(ServiceContext ctx, PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) {
        PublicationServiceInvocationValidatorImpl.checkExistingPublicationVersion(resource, exceptionItems);
    }

    @Override
    protected void checkVersioningInternal(ServiceContext ctx, PublicationVersion resource, List<MetamacExceptionItem> exceptionItems) {
        PublicationServiceInvocationValidatorImpl.checkExistingPublicationVersion(resource, exceptionItems);
    }
}
