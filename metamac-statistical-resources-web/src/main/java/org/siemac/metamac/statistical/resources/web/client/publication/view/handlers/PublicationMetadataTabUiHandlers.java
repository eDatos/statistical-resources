package org.siemac.metamac.statistical.resources.web.client.publication.view.handlers;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;

import com.gwtplatform.mvp.client.UiHandlers;

public interface PublicationMetadataTabUiHandlers extends UiHandlers {

    void retrieveAgencies(int firstResult, int maxResults, String queryText);
    void savePublication(PublicationDto publicationDto);

    // RELATED PUBLICATIONS

    void retrievePublicationsForReplaces(int firstResult, int maxResults, String criteria);
    void retrievePublicationsForIsReplacedBy(int firstResult, int maxResults, String criteria);

    // LIFECYCLE

    void sendToProductionValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void sendToDiffusionValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void rejectValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void publish(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void version(String urn, VersionTypeEnum versionType);
}
