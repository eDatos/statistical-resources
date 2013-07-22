package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdateChapterLocationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdateChapterLocationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateChapterLocationActionHandler extends SecurityActionHandler<UpdateChapterLocationAction, UpdateChapterLocationResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdateChapterLocationActionHandler() {
        super(UpdateChapterLocationAction.class);
    }

    @Override
    public UpdateChapterLocationResult executeSecurityAction(UpdateChapterLocationAction action) throws ActionException {
        try {
            statisticalResourcesServiceFacade.updateChapterLocation(ServiceContextHolder.getCurrentServiceContext(), action.getChapterUrn(), action.getParentTargetUrn(), action.getOrderInLevel());
            PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(ServiceContextHolder.getCurrentServiceContext(),
                    action.getPublicationVersionUrn());
            return new UpdateChapterLocationResult(publicationStructureDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
