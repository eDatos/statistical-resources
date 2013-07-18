package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationsAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeletePublicationsActionHandler extends SecurityActionHandler<DeletePublicationsAction, DeletePublicationsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeletePublicationsActionHandler() {
        super(DeletePublicationsAction.class);
    }

    @Override
    public DeletePublicationsResult executeSecurityAction(DeletePublicationsAction action) throws ActionException {
        try {
            for (String urn : action.getUrns()) {
                statisticalResourcesServiceFacade.deletePublicationVersion(ServiceContextHolder.getCurrentServiceContext(), urn);
            }
            return new DeletePublicationsResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
