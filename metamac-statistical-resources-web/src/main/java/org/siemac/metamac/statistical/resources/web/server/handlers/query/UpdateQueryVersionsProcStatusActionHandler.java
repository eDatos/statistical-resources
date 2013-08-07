package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.server.handlers.UpdateResourceProcStatusBaseActionHandler;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionsProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionsProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionsProcStatusResult.Builder;
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
                if (queryVersionsToUpdateProcStatus.size() == 1) {
                    // If there was only one resource, throw the exception
                    throw WebExceptionUtils.createMetamacWebException(e);
                } else {
                    // If there were more than one resource, the messages should be shown in a tree structure
                    addExceptionsItemToMetamacException(lifeCycleAction, queryVersionDto, metamacException, e);
                }
            }
        }

        if (metamacException.getExceptionItems() == null || metamacException.getExceptionItems().isEmpty()) {

            // If there were no exceptions...

            Builder builder = new UpdateQueryVersionsProcStatusResult.Builder();

            if (queryVersionsToUpdateProcStatus.size() == 1) {
                try {
                    QueryVersionDto queryVersionDto = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(ServiceContextHolder.getCurrentServiceContext(),
                            queryVersionsToUpdateProcStatus.get(0).getUrn());
                    builder.queryVersionDto(queryVersionDto);
                } catch (MetamacException e) {
                    throw WebExceptionUtils.createMetamacWebException(e);
                }
            }

            return builder.build();

        } else {

            // Throw the captured exceptions
            throw WebExceptionUtils.createMetamacWebException(metamacException);
        }
    }
}
