package org.siemac.metamac.statistical.resources.web.client.publication.view.handlers;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;

public interface PublicationListUiHandlers extends NewStatisticalResourceUiHandlers {

    void createPublication(PublicationVersionDto publicationDto);
    void deletePublication(List<String> urns);
    void retrievePublications(int firstResult, int maxResults, String criteria);
    void goToPublication(String urn);
}
