package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.collection.DeleteCollectionListAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.DeleteCollectionListResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteCollectionListActionHandler  extends SecurityActionHandler<DeleteCollectionListAction, DeleteCollectionListResult>{

    public DeleteCollectionListActionHandler() {
        super(DeleteCollectionListAction.class);
    }
    
    @Override
    public DeleteCollectionListResult executeSecurityAction(DeleteCollectionListAction action) throws ActionException {
        try {
            for (String urn : action.getUrns()) {
                MockServices.deleteCollection(ServiceContextHolder.getCurrentServiceContext(), urn);
            }
        } catch (MetamacException e) {
            WebExceptionUtils.createMetamacWebException(e);
        }
        return new DeleteCollectionListResult();
    }
}
