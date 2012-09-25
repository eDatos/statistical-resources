package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasourceAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasourceResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveDatasourceActionHandler extends SecurityActionHandler<SaveDatasourceAction, SaveDatasourceResult> {
    
    public SaveDatasourceActionHandler() {
        super(SaveDatasourceAction.class);
    }
    
    @Override
    public SaveDatasourceResult executeSecurityAction(SaveDatasourceAction action) throws ActionException {
        try {
            SaveDatasourceResult result = null;
            if (action.getDatasource().getUrn() != null) {
                result = new SaveDatasourceResult(MockServices.updateDatasource(ServiceContextHolder.getCurrentServiceContext(), action.getDatasource()));
            } else {
                result = new SaveDatasourceResult(MockServices.createDatasource(ServiceContextHolder.getCurrentServiceContext(), action.getDatasource()));
            }
            return result;
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
        
    }

}
