package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeleteCubeAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeleteCubeResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteCubeActionHandler extends SecurityActionHandler<DeleteCubeAction, DeleteCubeResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteCubeActionHandler() {
        super(DeleteCubeAction.class);
    }

    @Override
    public DeleteCubeResult executeSecurityAction(DeleteCubeAction action) throws ActionException {
        try {
            statisticalResourcesServiceFacade.deleteCube(ServiceContextHolder.getCurrentServiceContext(), action.getCubeUrn());
            PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(ServiceContextHolder.getCurrentServiceContext(),
                    action.getPublicationVersionUrn());
            return new DeleteCubeResult(publicationStructureDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
