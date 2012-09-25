package org.siemac.metamac.statistical.resources.web.server.handlers.agency;

import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetAgenciesPaginatedListActionHandler extends SecurityActionHandler<GetAgenciesPaginatedListAction, GetAgenciesPaginatedListResult> {
    
    public GetAgenciesPaginatedListActionHandler() {
        super(GetAgenciesPaginatedListAction.class);
    }
    
    @Override
    public GetAgenciesPaginatedListResult executeSecurityAction(GetAgenciesPaginatedListAction action) throws ActionException {
        try {
            MetamacCriteriaResult<ExternalItemDto> criteriaResult =  MockServices.findAgencies(action.getFirstResult(), action.getMaxResults());
            return new GetAgenciesPaginatedListResult(criteriaResult.getResults(), criteriaResult.getPaginatorResult().getFirstResult(), criteriaResult.getPaginatorResult().getTotalResults());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
    
}
