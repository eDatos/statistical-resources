package org.siemac.metamac.statistical.resources.web.server.handlers.multidataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.SaveMultidatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.SaveMultidatasetVersionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveMultidatasetVersionActionHandler extends SecurityActionHandler<SaveMultidatasetVersionAction, SaveMultidatasetVersionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SaveMultidatasetVersionActionHandler() {
        super(SaveMultidatasetVersionAction.class);
    }

    @Override
    public SaveMultidatasetVersionResult executeSecurityAction(SaveMultidatasetVersionAction action) throws ActionException {

        MultidatasetVersionDto multidatasetVersionToSave = action.getMultidatasetVersionDto();
        MultidatasetVersionDto multidatasetVersionSaved = null;
        try {
            if (multidatasetVersionToSave.getId() == null) {
                multidatasetVersionSaved = statisticalResourcesServiceFacade.createMultidataset(ServiceContextHolder.getCurrentServiceContext(), multidatasetVersionToSave,
                        action.getStatisticalOperationDto());
            } else {
                multidatasetVersionSaved = statisticalResourcesServiceFacade.updateMultidatasetVersion(ServiceContextHolder.getCurrentServiceContext(), multidatasetVersionToSave);
            }
            return new SaveMultidatasetVersionResult(multidatasetVersionSaved);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
