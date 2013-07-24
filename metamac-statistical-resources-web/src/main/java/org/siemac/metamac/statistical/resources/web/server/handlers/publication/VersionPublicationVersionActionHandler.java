package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.statistical.resources.web.shared.publication.VersionPublicationVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.VersionPublicationVersionResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class VersionPublicationVersionActionHandler extends SecurityActionHandler<VersionPublicationVersionAction, VersionPublicationVersionResult> {

    public VersionPublicationVersionActionHandler() {
        super(VersionPublicationVersionAction.class);
    }

    @Override
    public VersionPublicationVersionResult executeSecurityAction(VersionPublicationVersionAction action) throws ActionException {
        // try {
        // PublicationDto collectionDto = MockServices.versionPublication(action.getUrn(), action.getVersionType());
        // return new VersionPublicationResult(collectionDto);
        // } catch (MetamacException e) {
        // throw WebExceptionUtils.createMetamacWebException(e);
        // }
        // FIXME: CALL CORE
        return new VersionPublicationVersionResult(null);
    }
}
