package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetCodelistsWithVariableAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetCodelistsWithVariableResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetCodelistsWithVariableActionHandler extends SecurityActionHandler<GetCodelistsWithVariableAction, GetCodelistsWithVariableResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetCodelistsWithVariableActionHandler() {
        super(GetCodelistsWithVariableAction.class);
    }

    @Override
    public GetCodelistsWithVariableResult executeSecurityAction(GetCodelistsWithVariableAction action) throws ActionException {
        ExternalItemsResult result = srmRestInternalFacade.findCodelistsWithVariable(action.getVariableUrn(), action.getFirstResult(), action.getMaxResults(), action.getCriteria());

        return new GetCodelistsWithVariableResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
    }
}
