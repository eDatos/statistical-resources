package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.server.handlers.UpdateResourceProcStatusBaseActionHandler;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionsProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionsProcStatusResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateQueryVersionsProcStatusActionHandler extends UpdateResourceProcStatusBaseActionHandler<UpdateQueryVersionsProcStatusAction, UpdateQueryVersionsProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdateQueryVersionsProcStatusActionHandler() {
        super(UpdateQueryVersionsProcStatusAction.class);
    }

    @Override
    public UpdateQueryVersionsProcStatusResult executeSecurityAction(UpdateQueryVersionsProcStatusAction action) throws ActionException {

        List<QueryVersionDto> queryVersionsToUpdateProcStatus = action.getQueryVersionsToUpdateProcStatus();
        LifeCycleActionEnum lifeCycleAction = action.getLifeCycleAction();

        MetamacException metamacException = new MetamacException();

        for (QueryVersionDto queryVersionDto : queryVersionsToUpdateProcStatus) {
            try {

                switch (lifeCycleAction) {
                    case SEND_TO_PRODUCTION_VALIDATION:
                        statisticalResourcesServiceFacade.sendQueryVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(), queryVersionDto);
                        break;

                    case SEND_TO_DIFFUSION_VALIDATION:
                        statisticalResourcesServiceFacade.sendQueryVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(), queryVersionDto);
                        break;

                    case REJECT_VALIDATION:
                        statisticalResourcesServiceFacade.sendQueryVersionToValidationRejected(ServiceContextHolder.getCurrentServiceContext(), queryVersionDto);
                        break;

                    case PUBLISH:
                        // TODO
                        break;

                    case VERSION:
                        // TODO
                        break;

                    default:
                        break;
                }

            } catch (MetamacException e) {
                addExceptionsItemToMetamacException(lifeCycleAction, queryVersionDto, metamacException, e);
            }
        }

        if (metamacException.getExceptionItems() == null || metamacException.getExceptionItems().isEmpty()) {
            return new UpdateQueryVersionsProcStatusResult();
        } else {
            throw WebExceptionUtils.createMetamacWebException(metamacException);
        }
    }
}
