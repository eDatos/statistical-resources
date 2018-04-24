package org.siemac.metamac.statistical.resources.web.server.handlers.multidataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetCubeDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.SaveMultidatasetCubeAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.SaveMultidatasetCubeResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveMultidatasetCubeActionHandler extends SecurityActionHandler<SaveMultidatasetCubeAction, SaveMultidatasetCubeResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SaveMultidatasetCubeActionHandler() {
        super(SaveMultidatasetCubeAction.class);
    }

    @Override
    public SaveMultidatasetCubeResult executeSecurityAction(SaveMultidatasetCubeAction action) throws ActionException {
        try {

            MultidatasetCubeDto savedElement = null;
            MultidatasetCubeDto cubeToSave = action.getElement();
            if (cubeToSave.getId() == null) {
                savedElement = statisticalResourcesServiceFacade.createMultidatasetCube(ServiceContextHolder.getCurrentServiceContext(), action.getMultidatasetVersionUrn(), cubeToSave);
            } else {
                savedElement = statisticalResourcesServiceFacade.updateMultidatasetCube(ServiceContextHolder.getCurrentServiceContext(), cubeToSave);
            }

            MultidatasetVersionDto multidatasetVersionDto = statisticalResourcesServiceFacade.retrieveMultidatasetVersionByUrn(ServiceContextHolder.getCurrentServiceContext(),
                    action.getMultidatasetVersionUrn());
            return new SaveMultidatasetCubeResult(multidatasetVersionDto, savedElement);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
