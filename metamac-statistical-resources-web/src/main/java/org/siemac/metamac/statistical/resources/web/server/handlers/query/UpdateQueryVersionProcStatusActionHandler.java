package org.siemac.metamac.statistical.resources.web.server.handlers.query;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionProcStatusResult.Builder;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwtplatform.dispatch.shared.ActionException;

public class UpdateQueryVersionProcStatusActionHandler extends SecurityActionHandler<UpdateQueryVersionProcStatusAction, UpdateQueryVersionProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdateQueryVersionProcStatusActionHandler() {
        super(UpdateQueryVersionProcStatusAction.class);
    }

    @Override
    public UpdateQueryVersionProcStatusResult executeSecurityAction(UpdateQueryVersionProcStatusAction action) throws ActionException {

        try {

            QueryVersionDto queryVersionResult = null;

            ProcStatusEnum nextProcStatus = action.getNextProcStatus();

            switch (nextProcStatus) {
                case PRODUCTION_VALIDATION: {
                    for (QueryVersionDto datasetVersionDto : action.getQueryVersionsToUpdateProcStatus()) {
                        // TODO queryVersionResult = statisticalResourcesServiceFacade.sendQueryVersionToProductionValidation(ServiceContextHolder.getCurrentServiceContext(), datasetVersionDto);
                    }
                    break;
                }
                case DIFFUSION_VALIDATION: {
                    for (QueryVersionDto datasetVersionDto : action.getQueryVersionsToUpdateProcStatus()) {
                        // TODO queryVersionResult = statisticalResourcesServiceFacade.sendQueryVersionToDiffusionValidation(ServiceContextHolder.getCurrentServiceContext(), datasetVersionDto);
                    }
                    break;
                }
                case VALIDATION_REJECTED: {
                    // TODO
                }
                case PUBLISHED: {
                    // TODO
                }
                default:
                    break;
            }

            // TODO Remove this retrieve: this is here because the DTO that is returned by the CORE is not updated (its optimisticLocking value is not updated)
            // TODO use queryVersionResult instead of action.getQueryVersionsToUpdateProcStatus...
            QueryVersionDto updatedQueryVersionDto = statisticalResourcesServiceFacade.retrieveQueryVersionByUrn(ServiceContextHolder.getCurrentServiceContext(), action
                    .getQueryVersionsToUpdateProcStatus().get(0).getUrn());

            Builder builder = new UpdateQueryVersionProcStatusResult.Builder();
            builder.queryVersionDto(updatedQueryVersionDto);
            return builder.build();

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
