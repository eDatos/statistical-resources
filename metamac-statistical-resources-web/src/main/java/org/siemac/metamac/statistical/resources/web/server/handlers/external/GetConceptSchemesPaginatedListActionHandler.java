package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptSchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptSchemesPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetConceptSchemesPaginatedListActionHandler extends SecurityActionHandler<GetConceptSchemesPaginatedListAction, GetConceptSchemesPaginatedListResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetConceptSchemesPaginatedListActionHandler() {
        super(GetConceptSchemesPaginatedListAction.class);
    }

    @Override
    public GetConceptSchemesPaginatedListResult executeSecurityAction(GetConceptSchemesPaginatedListAction action) throws ActionException {
        ExternalItemsResult result = srmRestInternalFacade.findConceptSchemes(action.getFirstResult(), action.getMaxResults(), action.getCriteria());
        return new GetConceptSchemesPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }

}
