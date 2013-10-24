package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.server.handlers.UpdateResourceProcStatusBaseActionHandler;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionProcStatusResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateQueryVersionProcStatusActionHandler extends UpdateResourceProcStatusBaseActionHandler<UpdateQueryVersionProcStatusAction, UpdateQueryVersionProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdateQueryVersionProcStatusActionHandler() {
        super(UpdateQueryVersionProcStatusAction.class);
    }

    @Override
    public UpdateQueryVersionProcStatusResult executeSecurityAction(UpdateQueryVersionProcStatusAction action) throws ActionException {

        LifeCycleActionEnum lifeCycleAction = action.getLifeCycleAction();

        QueryVersionDto queryVersionDto = null;

        try {

            switch (lifeCycleAction) {
                case SEND_TO_PRODUCTION_VALIDATION:
                    queryVersionDto = statisticalResourcesServiceFacade.sendQueryVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(),
                            action.getQueryVersionToUpdateProcStatus());
                    break;

                case SEND_TO_DIFFUSION_VALIDATION:
                    queryVersionDto = statisticalResourcesServiceFacade.sendQueryVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(),
                            action.getQueryVersionToUpdateProcStatus());
                    break;

                case REJECT_VALIDATION:
                    queryVersionDto = statisticalResourcesServiceFacade.sendQueryVersionToValidationRejected(ServiceContextHolder.getCurrentServiceContext(),
                            action.getQueryVersionToUpdateProcStatus());
                    break;

                case PUBLISH:
                    if (action.getValidFrom() != null) {
                        queryVersionDto = statisticalResourcesServiceFacade.programPublicationQueryVersion(ServiceContextHolder.getCurrentServiceContext(), action.getQueryVersionToUpdateProcStatus(),
                                action.getValidFrom());
                    } else {
                        queryVersionDto = statisticalResourcesServiceFacade.publishQueryVersion(ServiceContextHolder.getCurrentServiceContext(), action.getQueryVersionToUpdateProcStatus());
                    }
                    break;
                case CANCEL_PROGRAMMED_PUBLICATION:
                    queryVersionDto = statisticalResourcesServiceFacade.cancelPublicationQueryVersion(ServiceContextHolder.getCurrentServiceContext(), action.getQueryVersionToUpdateProcStatus());
                    break;
                default:
                    throw new UnsupportedOperationException("Lifecycle operation unsupported " + lifeCycleAction);
            }

            // TODO remove this retrieve (it is here until the optimisticLocking error in the CORE were solved!)
            queryVersionDto = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(ServiceContextHolder.getCurrentServiceContext(), action.getQueryVersionToUpdateProcStatus().getUrn());

            return new UpdateQueryVersionProcStatusResult(queryVersionDto);

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
