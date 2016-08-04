package org.siemac.metamac.statistical.resources.web.server.handlers.publication;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationStructureElementAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationStructureElementResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class SavePublicationStructureElementActionHandler extends SecurityActionHandler<SavePublicationStructureElementAction, SavePublicationStructureElementResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public SavePublicationStructureElementActionHandler() {
        super(SavePublicationStructureElementAction.class);
    }

    @Override
    public SavePublicationStructureElementResult executeSecurityAction(SavePublicationStructureElementAction action) throws ActionException {
        try {

            NameableStatisticalResourceDto savedElement = null;

            if (action.getElement() instanceof ChapterDto) {

                // The element is a CHAPTER

                ChapterDto chapterToSave = (ChapterDto) action.getElement();
                if (chapterToSave.getId() == null) {
                    savedElement = statisticalResourcesServiceFacade.createChapter(ServiceContextHolder.getCurrentServiceContext(), action.getPublicationVersionUrn(), chapterToSave);
                } else {
                    savedElement = statisticalResourcesServiceFacade.updateChapter(ServiceContextHolder.getCurrentServiceContext(), chapterToSave);
                }

            } else if (action.getElement() instanceof CubeDto) {

                // The element is a CUBE

                CubeDto cubeToSave = (CubeDto) action.getElement();
                if (cubeToSave.getId() == null) {
                    savedElement = statisticalResourcesServiceFacade.createCube(ServiceContextHolder.getCurrentServiceContext(), action.getPublicationVersionUrn(), cubeToSave);
                } else {
                    savedElement = statisticalResourcesServiceFacade.updateCube(ServiceContextHolder.getCurrentServiceContext(), cubeToSave);
                }
            }

            PublicationStructureDto publicationStructureDto = statisticalResourcesServiceFacade.retrievePublicationVersionStructure(ServiceContextHolder.getCurrentServiceContext(),
                    action.getPublicationVersionUrn());
            return new SavePublicationStructureElementResult(publicationStructureDto, savedElement);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
