package org.siemac.metamac.statistical.resources.web.server.handlers;

import java.util.ArrayList;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetOperationPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetOperationPaginatedListResult;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetOperationPaginatedListActionHandler extends AbstractActionHandler<GetOperationPaginatedListAction, GetOperationPaginatedListResult> {
    
    
    public GetOperationPaginatedListActionHandler() {
        super(GetOperationPaginatedListAction.class);
    }
    
    @Override
    public GetOperationPaginatedListResult execute(GetOperationPaginatedListAction action, ExecutionContext context) throws ActionException {
        // TODO COMPLETAR
        GetOperationPaginatedListResult result = new GetOperationPaginatedListResult(new ArrayList<ExternalItemDto>(), 0, 0);
        return result;
    }
    
    @Override
    public void undo(GetOperationPaginatedListAction action, GetOperationPaginatedListResult result, ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub
        
    }

}
