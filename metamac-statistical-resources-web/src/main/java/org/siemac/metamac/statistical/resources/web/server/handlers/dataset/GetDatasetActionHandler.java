package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasetActionHandler extends SecurityActionHandler<GetDatasetAction, GetDatasetResult> {

    public GetDatasetActionHandler() {
        super(GetDatasetAction.class);
    }

    @Override
    public GetDatasetResult executeSecurityAction(GetDatasetAction action) throws ActionException {
        try {
            return new GetDatasetResult(MockServices.retrieveDataset(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetUrn()));
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
