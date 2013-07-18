package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetOrganisationUnitsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetOrganisationUnitsPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetOrganisationUnitsPaginatedListActionHandler extends SecurityActionHandler<GetOrganisationUnitsPaginatedListAction, GetOrganisationUnitsPaginatedListResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetOrganisationUnitsPaginatedListActionHandler() {
        super(GetOrganisationUnitsPaginatedListAction.class);
    }

    @Override
    public GetOrganisationUnitsPaginatedListResult executeSecurityAction(GetOrganisationUnitsPaginatedListAction action) throws ActionException {
        ExternalItemsResult result = srmRestInternalFacade.findOrganisations(action.getFirstResult(), action.getMaxResults(), action.getCriteria(), TypeExternalArtefactsEnum.ORGANISATION_UNIT);
        return new GetOrganisationUnitsPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }
}
