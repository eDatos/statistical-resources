package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetCollectionActionHandler extends SecurityActionHandler<GetCollectionAction, GetCollectionResult> {

    public GetCollectionActionHandler() {
        super(GetCollectionAction.class);
    }

    @Override
    public GetCollectionResult executeSecurityAction(GetCollectionAction action) throws ActionException {
        try {
            return new GetCollectionResult(MockServices.retrieveCollection(ServiceContextHolder.getCurrentServiceContext(), action.getUrn()));
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
