package org.siemac.metamac.statistical.resources.web.server.handlers.collection;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.collection.SavePublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.SavePublicationResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SavePublicationActionHandler extends SecurityActionHandler<SavePublicationAction, SavePublicationResult> {

    public SavePublicationActionHandler() {
        super(SavePublicationAction.class);
    }

    @Override
    public SavePublicationResult executeSecurityAction(SavePublicationAction action) throws ActionException {
//        PublicationDto collectionToSave = action.getPublicationDto();
//        PublicationDto collectionSaved = null;
//        try {
//            if (collectionToSave.getId() == null) {
//                collectionSaved = MockServices.createPublication(ServiceContextHolder.getCurrentServiceContext(), collectionToSave);
//            } else {
//                collectionSaved = MockServices.updatePublication(ServiceContextHolder.getCurrentServiceContext(), collectionToSave);
//            }
//            return new SavePublicationResult(collectionSaved);
//        } catch (MetamacException e) {
//            throw WebExceptionUtils.createMetamacWebException(e);
//        }
        //FIXME: invoke core
        return new SavePublicationResult(null);
    }

}
