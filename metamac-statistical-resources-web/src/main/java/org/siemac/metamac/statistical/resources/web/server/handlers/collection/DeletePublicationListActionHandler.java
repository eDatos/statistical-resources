package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import org.siemac.metamac.statistical.resources.web.shared.collection.DeletePublicationListAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.DeletePublicationListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeletePublicationListActionHandler  extends SecurityActionHandler<DeletePublicationListAction, DeletePublicationListResult>{

    public DeletePublicationListActionHandler() {
        super(DeletePublicationListAction.class);
    }
    
    @Override
    public DeletePublicationListResult executeSecurityAction(DeletePublicationListAction action) throws ActionException {
        //FIXME: invoke core
//        try {
//            for (String urn : action.getUrns()) {
//                MockServices.deletePublication(ServiceContextHolder.getCurrentServiceContext(), urn);
//            }
//        } catch (MetamacException e) {
//            WebExceptionUtils.createMetamacWebException(e);
//        }
        return new DeletePublicationListResult();
    }
}
