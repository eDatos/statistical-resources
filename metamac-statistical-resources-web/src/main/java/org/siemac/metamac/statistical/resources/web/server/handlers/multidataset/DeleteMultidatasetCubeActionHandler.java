package org.siemac.metamac.statistical.resources.web.server.handlers.multidataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.DeleteMultidatasetCubeAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.DeleteMultidatasetCubeResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteMultidatasetCubeActionHandler extends SecurityActionHandler<DeleteMultidatasetCubeAction, DeleteMultidatasetCubeResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteMultidatasetCubeActionHandler() {
        super(DeleteMultidatasetCubeAction.class);
    }

    @Override
    public DeleteMultidatasetCubeResult executeSecurityAction(DeleteMultidatasetCubeAction action) throws ActionException {
        try {
            String cubeUrn = action.getMultidatasetCubeUrn();

            statisticalResourcesServiceFacade.deleteMultidatasetCube(ServiceContextHolder.getCurrentServiceContext(), cubeUrn);
            MultidatasetVersionDto multidatasetVersionDto = statisticalResourcesServiceFacade.retrieveMultidatasetVersionByUrn(ServiceContextHolder.getCurrentServiceContext(),
                    action.getMultidatasetVersionUrn());
            return new DeleteMultidatasetCubeResult(multidatasetVersionDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
