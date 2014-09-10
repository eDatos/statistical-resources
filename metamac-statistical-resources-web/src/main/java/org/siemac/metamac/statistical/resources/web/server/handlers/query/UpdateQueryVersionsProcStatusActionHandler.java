package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
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

        List<QueryVersionBaseDto> queryVersionsToUpdateProcStatus = action.getQueryVersionsToUpdateProcStatus();
        LifeCycleActionEnum lifeCycleAction = action.getLifeCycleAction();

        MetamacException metamacException = new MetamacException();

        for (QueryVersionBaseDto queryVersionBaseDto : queryVersionsToUpdateProcStatus) {
            try {

                switch (lifeCycleAction) {
                    case SEND_TO_PRODUCTION_VALIDATION:
                        statisticalResourcesServiceFacade.sendQueryVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(), queryVersionBaseDto);
                        break;

                    case SEND_TO_DIFFUSION_VALIDATION:
                        statisticalResourcesServiceFacade.sendQueryVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(), queryVersionBaseDto);
                        break;

                    case REJECT_VALIDATION:
                        statisticalResourcesServiceFacade.sendQueryVersionToValidationRejected(ServiceContextHolder.getCurrentServiceContext(), queryVersionBaseDto);

                        String reasonOfRejection = action.getReasonOfRejection();
                        // TODO METAMAC-2112

                        break;

                    case PUBLISH:
                        statisticalResourcesServiceFacade.publishQueryVersion(ServiceContextHolder.getCurrentServiceContext(), queryVersionBaseDto);
                        break;

                    case PROGRAM_PUBLICATION:
                        statisticalResourcesServiceFacade.programPublicationQueryVersion(ServiceContextHolder.getCurrentServiceContext(), queryVersionBaseDto, action.getValidFrom());
                        break;

                    case CANCEL_PROGRAMMED_PUBLICATION:
                        statisticalResourcesServiceFacade.cancelPublicationQueryVersion(ServiceContextHolder.getCurrentServiceContext(), queryVersionBaseDto);
                        break;

                    case VERSION:
                        statisticalResourcesServiceFacade.versioningQueryVersion(ServiceContextHolder.getCurrentServiceContext(), queryVersionBaseDto, action.getVersionType());
                        break;
                    default:
                        break;
                }

            } catch (MetamacException e) {
                addExceptionsItemToMetamacException(lifeCycleAction, queryVersionBaseDto, metamacException, e);
            }
        }

        if (metamacException.getExceptionItems() == null || metamacException.getExceptionItems().isEmpty()) {
            return new UpdateQueryVersionsProcStatusResult();
        } else {
            throw WebExceptionUtils.createMetamacWebException(metamacException);
        }
    }
}
