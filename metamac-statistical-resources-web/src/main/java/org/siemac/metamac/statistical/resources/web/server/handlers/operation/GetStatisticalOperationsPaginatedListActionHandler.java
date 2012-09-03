package org.siemac.metamac.statistical.resources.web.server.handlers.operation;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetStatisticalOperationsPaginatedListActionHandler extends SecurityActionHandler<GetStatisticalOperationsPaginatedListAction, GetStatisticalOperationsPaginatedListResult> {
    
    
    @Autowired
    StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;

    public GetStatisticalOperationsPaginatedListActionHandler() {
        super(GetStatisticalOperationsPaginatedListAction.class);
    }

    @Override
    public GetStatisticalOperationsPaginatedListResult executeSecurityAction(GetStatisticalOperationsPaginatedListAction action) throws ActionException {
        int firstResult = 0;
        int totalResults = 0;
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>();
        Operations result = statisticalOperationsRestInternalFacade.findOperations(action.getFirstResult(), action.getMaxResults(), action.getOperation());
        if (result != null && result.getOperations() != null) {
            firstResult = result.getOffset().intValue();
            totalResults = result.getTotal().intValue();
            for (Resource resource : result.getOperations()) {
                ExternalItemDto externalItemDto = new ExternalItemDto(resource.getId(), resource.getSelfLink(), resource.getUrn(), TypeExternalArtefactsEnum.STATISTICAL_OPERATION,
                        DtoUtils.getInternationalStringDtoFromInternationalString(resource.getTitle()));
                externalItemDtos.add(externalItemDto);

            }
        }
        return new GetStatisticalOperationsPaginatedListResult(externalItemDtos, firstResult, totalResults);
    }
    
    @Override
    public void undo(GetStatisticalOperationsPaginatedListAction action, GetStatisticalOperationsPaginatedListResult result, ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub
        
    }
}
