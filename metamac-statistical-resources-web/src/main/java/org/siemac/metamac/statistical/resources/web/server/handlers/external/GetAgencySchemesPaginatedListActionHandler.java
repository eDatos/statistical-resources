package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetAgencySchemesPaginatedListActionHandler extends SecurityActionHandler<GetAgencySchemesPaginatedListAction, GetAgencySchemesPaginatedListResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetAgencySchemesPaginatedListActionHandler() {
        super(GetAgencySchemesPaginatedListAction.class);
    }

    @Override
    public GetAgencySchemesPaginatedListResult executeSecurityAction(GetAgencySchemesPaginatedListAction action) throws ActionException {
        ExternalItemsResult result = srmRestInternalFacade.findOrganisationSchemes(action.getFirstResult(), action.getMaxResults(), action.getCriteria(), TypeExternalArtefactsEnum.AGENCY_SCHEME);
        return new GetAgencySchemesPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }
}
