package org.siemac.metamac.statistical.resources.web.client.publication.view.handlers;

import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.shared.criteria.PublicationVersionWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface PublicationListUiHandlers extends NewStatisticalResourceUiHandlers {

    void createPublication(PublicationVersionDto publicationDto);
    void deletePublication(List<String> urns);
    void retrievePublications(int firstResult, int maxResults, PublicationVersionWebCriteria criteria);
    void goToPublication(String urn);

    // LifeCycle

    void sendToProductionValidation(List<PublicationVersionBaseDto> publicationVersionBaseDtos);
    void sendToDiffusionValidation(List<PublicationVersionBaseDto> publicationVersionBaseDtos);
    void rejectValidation(List<PublicationVersionBaseDto> publicationVersionBaseDtos, String reasonOfRejection);
    void publish(List<PublicationVersionBaseDto> publicationVersionBaseDtos);

    void version(List<PublicationVersionBaseDto> publicationVersionBaseDtos, VersionTypeEnum versionType);

    // Related resources
    void retrieveStatisticalOperationsForSearchSection(int firstResult, int maxResults, MetamacWebCriteria criteria);
}
