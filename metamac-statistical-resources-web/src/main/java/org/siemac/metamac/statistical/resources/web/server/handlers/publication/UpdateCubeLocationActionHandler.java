package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdateCubeLocationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdateCubeLocationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateCubeLocationActionHandler extends SecurityActionHandler<UpdateCubeLocationAction, UpdateCubeLocationResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdateCubeLocationActionHandler() {
        super(UpdateCubeLocationAction.class);
    }

    @Override
    public UpdateCubeLocationResult executeSecurityAction(UpdateCubeLocationAction action) throws ActionException {
        try {
            statisticalResourcesServiceFacade.updateCubeLocation(ServiceContextHolder.getCurrentServiceContext(), action.getCubeUrn(), action.getParentTargetUrn(), action.getOrderInLevel());
            PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(ServiceContextHolder.getCurrentServiceContext(),
                    action.getPublicationVersionUrn());
            return new UpdateCubeLocationResult(publicationStructureDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
