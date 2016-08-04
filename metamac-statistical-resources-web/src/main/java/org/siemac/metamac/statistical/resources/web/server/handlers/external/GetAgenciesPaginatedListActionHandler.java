package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetAgenciesPaginatedListActionHandler extends SecurityActionHandler<GetAgenciesPaginatedListAction, GetAgenciesPaginatedListResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetAgenciesPaginatedListActionHandler() {
        super(GetAgenciesPaginatedListAction.class);
    }

    @Override
    public GetAgenciesPaginatedListResult executeSecurityAction(GetAgenciesPaginatedListAction action) throws ActionException {
        ExternalItemsResult result = srmRestInternalFacade.findOrganisations(action.getFirstResult(), action.getMaxResults(), action.getCriteria(), TypeExternalArtefactsEnum.AGENCY);
        return new GetAgenciesPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }

}
