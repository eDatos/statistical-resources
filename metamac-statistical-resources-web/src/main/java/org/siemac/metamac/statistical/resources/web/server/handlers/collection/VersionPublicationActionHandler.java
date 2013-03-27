package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import org.siemac.metamac.statistical.resources.web.shared.collection.VersionPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.VersionPublicationResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class VersionPublicationActionHandler extends SecurityActionHandler<VersionPublicationAction, VersionPublicationResult> {

    public VersionPublicationActionHandler() {
        super(VersionPublicationAction.class);
    }

    @Override
    public VersionPublicationResult executeSecurityAction(VersionPublicationAction action) throws ActionException {
        // try {
        // PublicationDto collectionDto = MockServices.versionPublication(action.getUrn(), action.getVersionType());
        // return new VersionPublicationResult(collectionDto);
        // } catch (MetamacException e) {
        // throw WebExceptionUtils.createMetamacWebException(e);
        // }
        // FIXME: CALL CORE
        return new VersionPublicationResult(null);
    }
}
