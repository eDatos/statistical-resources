package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCodesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCodesPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetCodesPaginatedListActionHandler extends SecurityActionHandler<GetCodesPaginatedListAction, GetCodesPaginatedListResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetCodesPaginatedListActionHandler() {
        super(GetCodesPaginatedListAction.class);
    }

    @Override
    public GetCodesPaginatedListResult executeSecurityAction(GetCodesPaginatedListAction action) throws ActionException {
        ExternalItemsResult result = srmRestInternalFacade.findCodes(action.getFirstResult(), action.getMaxResults(), action.getCriteria());
        return new GetCodesPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }
}
