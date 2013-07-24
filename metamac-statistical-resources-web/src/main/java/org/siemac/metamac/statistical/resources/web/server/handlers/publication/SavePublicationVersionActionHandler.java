package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationVersionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SavePublicationVersionActionHandler extends SecurityActionHandler<SavePublicationVersionAction, SavePublicationVersionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SavePublicationVersionActionHandler() {
        super(SavePublicationVersionAction.class);
    }

    @Override
    public SavePublicationVersionResult executeSecurityAction(SavePublicationVersionAction action) throws ActionException {

        PublicationVersionDto publicationVersionToSave = action.getPublicationVersionDto();
        PublicationVersionDto publicationVersionSaved = null;
        try {
            if (publicationVersionToSave.getId() == null) {
                publicationVersionSaved = statisticalResourcesServiceFacade.createPublication(ServiceContextHolder.getCurrentServiceContext(), publicationVersionToSave,
                        action.getStatisticalOperationDto());
            } else {
                publicationVersionSaved = statisticalResourcesServiceFacade.updatePublicationVersion(ServiceContextHolder.getCurrentServiceContext(), publicationVersionToSave);
            }
            return new SavePublicationVersionResult(publicationVersionSaved);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
