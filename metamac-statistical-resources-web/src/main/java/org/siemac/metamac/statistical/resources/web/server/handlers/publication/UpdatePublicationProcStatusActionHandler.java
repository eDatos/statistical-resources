package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationProcStatusResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdatePublicationProcStatusActionHandler extends SecurityActionHandler<UpdatePublicationProcStatusAction, UpdatePublicationProcStatusResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdatePublicationProcStatusActionHandler() {
        super(UpdatePublicationProcStatusAction.class);
    }

    @Override
    public UpdatePublicationProcStatusResult executeSecurityAction(UpdatePublicationProcStatusAction action) throws ActionException {

        // FIXME: invoke core

        String urn = action.getUrn();

        ProcStatusEnum procStatus = action.getNextProcStatus();
        PublicationVersionDto publicationDto = null;
        return new UpdatePublicationProcStatusResult(publicationDto);
    }
}
