package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.SaveChapterAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SaveChapterResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SaveChapterActionHandler extends SecurityActionHandler<SaveChapterAction, SaveChapterResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SaveChapterActionHandler() {
        super(SaveChapterAction.class);
    }

    @Override
    public SaveChapterResult executeSecurityAction(SaveChapterAction action) throws ActionException {
        try {
            ChapterDto chapterToSave = action.getChapterDto();
            if (chapterToSave.getId() == null) {
                statisticalResourcesServiceFacade.createChapter(ServiceContextHolder.getCurrentServiceContext(), action.getPublicationVersionUrn(), chapterToSave);
            } else {
                statisticalResourcesServiceFacade.updateChapter(ServiceContextHolder.getCurrentServiceContext(), chapterToSave);
            }
            PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(ServiceContextHolder.getCurrentServiceContext(),
                    action.getPublicationVersionUrn());
            return new SaveChapterResult(publicationStructureDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
