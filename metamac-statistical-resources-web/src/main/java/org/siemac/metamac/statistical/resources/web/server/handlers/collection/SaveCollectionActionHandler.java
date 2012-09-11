package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.collection.SaveCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.SaveCollectionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveCollectionActionHandler extends SecurityActionHandler<SaveCollectionAction, SaveCollectionResult> {

    public SaveCollectionActionHandler() {
        super(SaveCollectionAction.class);
    }

    @Override
    public SaveCollectionResult executeSecurityAction(SaveCollectionAction action) throws ActionException {
        CollectionDto collectionToSave = action.getCollectionDto();
        CollectionDto collectionSaved = null;
        try {
            if (collectionToSave.getId() == null) {
                collectionSaved = MockServices.createCollection(ServiceContextHolder.getCurrentServiceContext(), collectionToSave);
            } else {
                collectionSaved = MockServices.updateCollection(ServiceContextHolder.getCurrentServiceContext(), collectionToSave);
            }
            return new SaveCollectionResult(collectionSaved);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
