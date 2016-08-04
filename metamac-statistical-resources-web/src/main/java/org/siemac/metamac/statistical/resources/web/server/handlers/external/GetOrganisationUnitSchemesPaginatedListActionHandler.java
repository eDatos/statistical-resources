package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetOrganisationUnitSchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetOrganisationUnitSchemesPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetOrganisationUnitSchemesPaginatedListActionHandler extends SecurityActionHandler<GetOrganisationUnitSchemesPaginatedListAction, GetOrganisationUnitSchemesPaginatedListResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetOrganisationUnitSchemesPaginatedListActionHandler() {
        super(GetOrganisationUnitSchemesPaginatedListAction.class);
    }

    @Override
    public GetOrganisationUnitSchemesPaginatedListResult executeSecurityAction(GetOrganisationUnitSchemesPaginatedListAction action) throws ActionException {
        ExternalItemsResult result = srmRestInternalFacade.findOrganisationSchemes(action.getFirstResult(), action.getMaxResults(), action.getCriteria(),
                TypeExternalArtefactsEnum.ORGANISATION_UNIT_SCHEME);
        return new GetOrganisationUnitSchemesPaginatedListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }
}
