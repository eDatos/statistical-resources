package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptsPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetConceptsPaginatedListActionHandler extends SecurityActionHandler<GetConceptsPaginatedListAction, GetConceptsPaginatedListResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetConceptsPaginatedListActionHandler() {
        super(GetConceptsPaginatedListAction.class);
    }

    @Override
    public GetConceptsPaginatedListResult executeSecurityAction(GetConceptsPaginatedListAction action) throws ActionException {
        ExternalItemsResult result = srmRestInternalFacade.findConcepts(action.getFirstResult(), action.getMaxResults(), action.getCriteria());
        return new GetConceptsPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }
}
