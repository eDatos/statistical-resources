package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeleteChapterAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeleteChapterResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteChapterActionHandler extends SecurityActionHandler<DeleteChapterAction, DeleteChapterResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteChapterActionHandler() {
        super(DeleteChapterAction.class);
    }

    @Override
    public DeleteChapterResult executeSecurityAction(DeleteChapterAction action) throws ActionException {
        try {
            statisticalResourcesServiceFacade.deleteChapter(ServiceContextHolder.getCurrentServiceContext(), action.getChapterUrn());
            PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(ServiceContextHolder.getCurrentServiceContext(),
                    action.getPublicationVersionUrn());
            return new DeleteChapterResult(publicationStructureDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
