package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetConstraintAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetConstraintResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteDatasetConstraintActionHandler extends SecurityActionHandler<DeleteDatasetConstraintAction, DeleteDatasetConstraintResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteDatasetConstraintActionHandler() {
        super(DeleteDatasetConstraintAction.class);
    }

    @Override
    public DeleteDatasetConstraintResult executeSecurityAction(DeleteDatasetConstraintAction action) throws ActionException {
        try {
            statisticalResourcesServiceFacade.deleteRegion(ServiceContextHolder.getCurrentServiceContext(), action.getConstraintDto().getUrn(), action.getRegionValueDto().getCode());
            statisticalResourcesServiceFacade.deleteContentConstraint(ServiceContextHolder.getCurrentServiceContext(), action.getConstraintDto().getUrn());
            return new DeleteDatasetConstraintResult();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
