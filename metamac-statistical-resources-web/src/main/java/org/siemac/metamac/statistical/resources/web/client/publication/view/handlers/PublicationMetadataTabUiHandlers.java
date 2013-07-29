package org.siemac.metamac.statistical.resources.web.client.publication.view.handlers;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface PublicationMetadataTabUiHandlers extends StatisticalResourceUiHandlers, BaseUiHandlers {

    void savePublication(PublicationVersionDto publicationDto);

    // RELATED PUBLICATIONS

    void retrievePublicationsForReplaces(int firstResult, int maxResults, MetamacWebCriteria criteria);

    // LIFECYCLE

    void sendToProductionValidation(PublicationVersionDto publicationVersionDto);
    void sendToDiffusionValidation(PublicationVersionDto publicationVersionDto);
    void rejectValidation(PublicationVersionDto publicationVersionDto);
    void publish(PublicationVersionDto publicationVersionDto);
    void version(String urn, VersionTypeEnum versionType);
}
