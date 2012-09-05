package org.siemac.metamac.statistical.resources.web.server.handlers.operation;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.utils.UrnUtils;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetStatisticalOperationActionHandler extends SecurityActionHandler<GetStatisticalOperationAction, GetStatisticalOperationResult> {
    
    @Autowired
    StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;
    
    public GetStatisticalOperationActionHandler() {
        super(GetStatisticalOperationAction.class);
    }
    
    @Override
    public GetStatisticalOperationResult executeSecurityAction(GetStatisticalOperationAction action) throws ActionException {
        String code = UrnUtils.removePrefix(action.getUrn());
        Operation operation = statisticalOperationsRestInternalFacade.retrieveOperation(code);
        
        ExternalItemDto externalItemDto = new ExternalItemDto(operation.getId(), operation.getSelfLink(), operation.getUrn(), TypeExternalArtefactsEnum.STATISTICAL_OPERATION,
                DtoUtils.getInternationalStringDtoFromInternationalString(operation.getTitle()));
        
        return new GetStatisticalOperationResult(externalItemDto);
    }

}
