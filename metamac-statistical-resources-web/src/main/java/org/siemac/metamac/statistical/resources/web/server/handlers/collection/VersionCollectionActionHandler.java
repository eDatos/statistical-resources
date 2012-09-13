package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.collection.VersionCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.VersionCollectionResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class VersionCollectionActionHandler extends SecurityActionHandler<VersionCollectionAction, VersionCollectionResult> {

    public VersionCollectionActionHandler() {
        super(VersionCollectionAction.class);
    }

    @Override
    public VersionCollectionResult executeSecurityAction(VersionCollectionAction action) throws ActionException {
        try {
            CollectionDto collectionDto = MockServices.versionCollection(action.getUrn(), action.getVersionType());
            return new VersionCollectionResult(collectionDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
