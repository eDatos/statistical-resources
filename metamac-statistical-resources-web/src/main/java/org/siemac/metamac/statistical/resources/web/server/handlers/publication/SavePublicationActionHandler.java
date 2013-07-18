package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SavePublicationActionHandler extends SecurityActionHandler<SavePublicationAction, SavePublicationResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SavePublicationActionHandler() {
        super(SavePublicationAction.class);
    }

    @Override
    public SavePublicationResult executeSecurityAction(SavePublicationAction action) throws ActionException {

        PublicationDto publicationToSave = action.getPublicationDto();
        PublicationDto publicationSaved = null;
        try {
            if (publicationToSave.getId() == null) {
                publicationSaved = statisticalResourcesServiceFacade.createPublication(ServiceContextHolder.getCurrentServiceContext(), publicationToSave, action.getStatisticalOperationDto());
            } else {
                publicationSaved = statisticalResourcesServiceFacade.updatePublicationVersion(ServiceContextHolder.getCurrentServiceContext(), publicationToSave);
            }
            return new SavePublicationResult(publicationSaved);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
