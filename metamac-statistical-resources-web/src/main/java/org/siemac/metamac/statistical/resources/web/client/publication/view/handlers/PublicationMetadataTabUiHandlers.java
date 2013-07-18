package org.siemac.metamac.statistical.resources.web.client.publication.view.handlers;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;

public interface PublicationMetadataTabUiHandlers extends StatisticalResourceUiHandlers, BaseUiHandlers {

    void savePublication(PublicationDto publicationDto);

    // RELATED PUBLICATIONS

    void retrievePublicationsForReplaces(int firstResult, int maxResults, String criteria);
    void retrievePublicationsForIsReplacedBy(int firstResult, int maxResults, String criteria);

    // LIFECYCLE

    void sendToProductionValidation(String urn, ProcStatusEnum currentProcStatus);
    void sendToDiffusionValidation(String urn, ProcStatusEnum currentProcStatus);
    void rejectValidation(String urn, ProcStatusEnum currentProcStatus);
    void publish(String urn, ProcStatusEnum currentProcStatus);
    void version(String urn, VersionTypeEnum versionType);
}
