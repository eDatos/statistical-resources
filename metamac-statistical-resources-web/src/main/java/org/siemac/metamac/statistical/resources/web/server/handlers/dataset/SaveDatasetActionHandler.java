package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveDatasetActionHandler extends SecurityActionHandler<SaveDatasetAction, SaveDatasetResult> {

    public SaveDatasetActionHandler() {
        super(SaveDatasetAction.class);
    }

    @Override
    public SaveDatasetResult executeSecurityAction(SaveDatasetAction action) throws ActionException {
        try {
            if (action.getDataset().getUuid() == null) {
                return new SaveDatasetResult(MockServices.createDataset(ServiceContextHolder.getCurrentServiceContext(), action.getDataset()));
            } else {
                return new SaveDatasetResult(MockServices.updateDataset(ServiceContextHolder.getCurrentServiceContext(), action.getDataset()));
            }
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
