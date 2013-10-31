package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCategorySchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCategorySchemesPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetCategorySchemesPaginatedListActionHandler extends SecurityActionHandler<GetCategorySchemesPaginatedListAction, GetCategorySchemesPaginatedListResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetCategorySchemesPaginatedListActionHandler() {
        super(GetCategorySchemesPaginatedListAction.class);
    }

    @Override
    public GetCategorySchemesPaginatedListResult executeSecurityAction(GetCategorySchemesPaginatedListAction action) throws ActionException {
        ExternalItemsResult result = srmRestInternalFacade.findCategorySchemes(action.getFirstResult(), action.getMaxResults(), action.getCriteria());
        return new GetCategorySchemesPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }
}
