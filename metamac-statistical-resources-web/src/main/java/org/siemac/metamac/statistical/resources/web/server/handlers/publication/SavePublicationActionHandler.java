package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
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

        PublicationVersionDto publicationVersionToSave = action.getPublicationVersionDto();
        PublicationVersionDto publicationVersionSaved = null;
        try {
            if (publicationVersionToSave.getId() == null) {
                publicationVersionSaved = statisticalResourcesServiceFacade.createPublication(ServiceContextHolder.getCurrentServiceContext(), publicationVersionToSave,
                        action.getStatisticalOperationDto());
            } else {
                publicationVersionSaved = statisticalResourcesServiceFacade.updatePublicationVersion(ServiceContextHolder.getCurrentServiceContext(), publicationVersionToSave);
            }
            return new SavePublicationResult(publicationVersionSaved);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
