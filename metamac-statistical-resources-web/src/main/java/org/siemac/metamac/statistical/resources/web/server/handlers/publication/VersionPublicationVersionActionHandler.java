package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.VersionPublicationVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.VersionPublicationVersionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class VersionPublicationVersionActionHandler extends SecurityActionHandler<VersionPublicationVersionAction, VersionPublicationVersionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public VersionPublicationVersionActionHandler() {
        super(VersionPublicationVersionAction.class);
    }

    @Override
    public VersionPublicationVersionResult executeSecurityAction(VersionPublicationVersionAction action) throws ActionException {
        try {
            PublicationVersionDto publicationDto = statisticalResourcesServiceFacade.versioningPublicationVersion(ServiceContextHolder.getCurrentServiceContext(), action.getPublicationVersionUrn(),
                    action.getVersionType());
            return new VersionPublicationVersionResult(publicationDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
