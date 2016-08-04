package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesUrnParserUtils;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationStructureElementAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationStructureElementResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeletePublicationStructureElementActionHandler extends SecurityActionHandler<DeletePublicationStructureElementAction, DeletePublicationStructureElementResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeletePublicationStructureElementActionHandler() {
        super(DeletePublicationStructureElementAction.class);
    }

    @Override
    public DeletePublicationStructureElementResult executeSecurityAction(DeletePublicationStructureElementAction action) throws ActionException {
        try {
            String elementUrn = action.getElementUrn();
            if (StatisticalResourcesUrnParserUtils.isPublicationChapterUrn(elementUrn)) {
                statisticalResourcesServiceFacade.deleteChapter(ServiceContextHolder.getCurrentServiceContext(), elementUrn);
            } else if (StatisticalResourcesUrnParserUtils.isPublicationCubeUrn(elementUrn)) {
                statisticalResourcesServiceFacade.deleteCube(ServiceContextHolder.getCurrentServiceContext(), elementUrn);
            }
            PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(ServiceContextHolder.getCurrentServiceContext(),
                    action.getPublicationVersionUrn());
            return new DeletePublicationStructureElementResult(publicationStructureDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
