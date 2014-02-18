package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCategoriesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCategoriesPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetCategoriesPaginatedListActionHandler extends SecurityActionHandler<GetCategoriesPaginatedListAction, GetCategoriesPaginatedListResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetCategoriesPaginatedListActionHandler() {
        super(GetCategoriesPaginatedListAction.class);
    }

    @Override
    public GetCategoriesPaginatedListResult executeSecurityAction(GetCategoriesPaginatedListAction action) throws ActionException {
        ExternalItemsResult result = srmRestInternalFacade.findCategories(action.getFirstResult(), action.getMaxResults(), action.getCriteria());
        return new GetCategoriesPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }
}
