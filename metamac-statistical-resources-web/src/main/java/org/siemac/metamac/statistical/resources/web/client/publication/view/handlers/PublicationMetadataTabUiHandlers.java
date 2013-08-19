package org.siemac.metamac.statistical.resources.web.client.publication.view.handlers;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface PublicationMetadataTabUiHandlers extends StatisticalResourceUiHandlers, BaseUiHandlers {

    void savePublication(PublicationVersionDto publicationDto);
    void deletePublication(String urn);

    void previewData(PublicationVersionDto publicationDto);

    // RELATED PUBLICATIONS

    void retrievePublicationsForReplaces(int firstResult, int maxResults, MetamacWebCriteria criteria);

    // LIFECYCLE

    void sendToProductionValidation(PublicationVersionDto publication);
    void sendToDiffusionValidation(PublicationVersionDto publication);
    void rejectValidation(PublicationVersionDto publication);
    void programPublication(PublicationVersionDto publication);
    void cancelProgrammedPublication(PublicationVersionDto publication);
    void publish(PublicationVersionDto publication);
    void version(PublicationVersionDto publication, VersionTypeEnum versionType);
}
