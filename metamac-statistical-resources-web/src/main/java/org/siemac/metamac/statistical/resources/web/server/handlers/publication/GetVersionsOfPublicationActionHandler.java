package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetVersionsOfPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetVersionsOfPublicationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetVersionsOfPublicationActionHandler extends SecurityActionHandler<GetVersionsOfPublicationAction, GetVersionsOfPublicationResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetVersionsOfPublicationActionHandler() {
        super(GetVersionsOfPublicationAction.class);
    }

    @Override
    public GetVersionsOfPublicationResult executeSecurityAction(GetVersionsOfPublicationAction action) throws ActionException {
        try {
            List<PublicationVersionBaseDto> publicationVersionBaseDtos = statisticalResourcesServiceFacade.retrievePublicationVersions(ServiceContextHolder.getCurrentServiceContext(),
                    action.getPublicationVersionUrn());
            return new GetVersionsOfPublicationResult(publicationVersionBaseDtos);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
