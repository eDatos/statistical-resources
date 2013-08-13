package org.siemac.metamac.statistical.resources.web.client.publication.view.handlers;

import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.shared.criteria.PublicationVersionWebCriteria;

public interface PublicationListUiHandlers extends NewStatisticalResourceUiHandlers {

    void createPublication(PublicationVersionDto publicationDto);
    void deletePublication(List<String> urns);
    void retrievePublications(int firstResult, int maxResults, PublicationVersionWebCriteria criteria);
    void goToPublication(String urn);

    // LifeCycle

    void sendToProductionValidation(List<PublicationVersionDto> publicationVersionDtos);
    void sendToDiffusionValidation(List<PublicationVersionDto> publicationVersionDtos);
    void rejectValidation(List<PublicationVersionDto> publicationVersionDtos);
    void publish(List<PublicationVersionDto> publicationVersionDtos);
    void programPublication(List<PublicationVersionDto> publicationVersionDtos);
    void version(List<PublicationVersionDto> publicationVersionDtos, VersionTypeEnum versionType);
}
