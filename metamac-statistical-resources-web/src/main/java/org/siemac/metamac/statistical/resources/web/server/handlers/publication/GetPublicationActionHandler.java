package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetPublicationActionHandler extends SecurityActionHandler<GetPublicationAction, GetPublicationResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetPublicationActionHandler() {
        super(GetPublicationAction.class);
    }

    @Override
    public GetPublicationResult executeSecurityAction(GetPublicationAction action) throws ActionException {

        // try {
        // return new GetPublicationResult(MockServices.retrievePublication(ServiceContextHolder.getCurrentServiceContext(), action.getUrn()));
        // } catch (MetamacException e) {
        // throw WebExceptionUtils.createMetamacWebException(e);
        // }

        // FIXME INVOKE CORE
        return new GetPublicationResult(null);
    }
}
