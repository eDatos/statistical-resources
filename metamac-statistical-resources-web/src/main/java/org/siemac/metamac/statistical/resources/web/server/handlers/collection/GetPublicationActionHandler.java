package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetPublicationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetPublicationActionHandler extends SecurityActionHandler<GetPublicationAction, GetPublicationResult> {

    public GetPublicationActionHandler() {
        super(GetPublicationAction.class);
    }

    @Override
    public GetPublicationResult executeSecurityAction(GetPublicationAction action) throws ActionException {
//        try {
//            return new GetPublicationResult(MockServices.retrievePublication(ServiceContextHolder.getCurrentServiceContext(), action.getUrn()));
//        } catch (MetamacException e) {
//            throw WebExceptionUtils.createMetamacWebException(e);
//        }
        
        //FIXME INVOKE CORE 
         return new GetPublicationResult(null);
    }

}
