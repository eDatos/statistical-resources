package org.siemac.metamac.statistical.resources.web.server.handlers.multidataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.UpdateMultidatasetCubeLocationAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.UpdateMultidatasetCubeLocationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateMultidatasetCubeLocationActionHandler extends SecurityActionHandler<UpdateMultidatasetCubeLocationAction, UpdateMultidatasetCubeLocationResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdateMultidatasetCubeLocationActionHandler() {
        super(UpdateMultidatasetCubeLocationAction.class);
    }

    @Override
    public UpdateMultidatasetCubeLocationResult executeSecurityAction(UpdateMultidatasetCubeLocationAction action) throws ActionException {
        try {
            String cubeUrn = action.getMultidatasetCubeUrn();

            statisticalResourcesServiceFacade.updateMultidatasetCubeLocation(ServiceContextHolder.getCurrentServiceContext(), cubeUrn, action.getOrderInMultidataset());

            MultidatasetVersionDto multidatasetVersionDto = statisticalResourcesServiceFacade.retrieveMultidatasetVersionByUrn(ServiceContextHolder.getCurrentServiceContext(),
                    action.getMultidatasetVersionUrn());
            return new UpdateMultidatasetCubeLocationResult(multidatasetVersionDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
