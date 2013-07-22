package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.SaveCubeAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SaveCubeResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveCubeActionHandler extends SecurityActionHandler<SaveCubeAction, SaveCubeResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SaveCubeActionHandler() {
        super(SaveCubeAction.class);
    }

    @Override
    public SaveCubeResult executeSecurityAction(SaveCubeAction action) throws ActionException {
        try {
            CubeDto cubeToSave = action.getCubeDto();
            if (cubeToSave.getId() == null) {
                statisticalResourcesServiceFacade.createCube(ServiceContextHolder.getCurrentServiceContext(), action.getPublicationVersionUrn(), cubeToSave);
            } else {
                statisticalResourcesServiceFacade.updateCube(ServiceContextHolder.getCurrentServiceContext(), cubeToSave);
            }
            PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(ServiceContextHolder.getCurrentServiceContext(),
                    action.getPublicationVersionUrn());
            return new SaveCubeResult(publicationStructureDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
