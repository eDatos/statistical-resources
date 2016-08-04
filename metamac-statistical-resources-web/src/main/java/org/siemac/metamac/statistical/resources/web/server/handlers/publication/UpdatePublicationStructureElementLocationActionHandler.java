package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesUrnParserUtils;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationStructureElementLocationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationStructureElementLocationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdatePublicationStructureElementLocationActionHandler extends SecurityActionHandler<UpdatePublicationStructureElementLocationAction, UpdatePublicationStructureElementLocationResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public UpdatePublicationStructureElementLocationActionHandler() {
        super(UpdatePublicationStructureElementLocationAction.class);
    }

    @Override
    public UpdatePublicationStructureElementLocationResult executeSecurityAction(UpdatePublicationStructureElementLocationAction action) throws ActionException {
        try {
            String elementUrn = action.getElementUrn();
            if (StatisticalResourcesUrnParserUtils.isPublicationChapterUrn(elementUrn)) {
                statisticalResourcesServiceFacade.updateChapterLocation(ServiceContextHolder.getCurrentServiceContext(), elementUrn, action.getParentTargetUrn(), action.getOrderInLevel());
            } else if (StatisticalResourcesUrnParserUtils.isPublicationCubeUrn(elementUrn)) {
                statisticalResourcesServiceFacade.updateCubeLocation(ServiceContextHolder.getCurrentServiceContext(), elementUrn, action.getParentTargetUrn(), action.getOrderInLevel());
            }
            PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(ServiceContextHolder.getCurrentServiceContext(),
                    action.getPublicationVersionUrn());
            return new UpdatePublicationStructureElementLocationResult(publicationStructureDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
