package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDsdsPaginatedListActionHandler extends SecurityActionHandler<GetDsdsPaginatedListAction, GetDsdsPaginatedListResult>  {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;
    
    
    public GetDsdsPaginatedListActionHandler() {
        super(GetDsdsPaginatedListAction.class);
    }
    
    @Override
    public GetDsdsPaginatedListResult executeSecurityAction(GetDsdsPaginatedListAction action) throws ActionException {
        int firstResult = 0;
        int totalResults = 0;
        List<ExternalItemDto> externalItemDtos = srmRestInternalFacade.findDsds(action.getFirstResult(), action.getMaxResults(), action.getCriteria());
        return new GetDsdsPaginatedListResult(externalItemDtos, firstResult, totalResults);
    }
}
