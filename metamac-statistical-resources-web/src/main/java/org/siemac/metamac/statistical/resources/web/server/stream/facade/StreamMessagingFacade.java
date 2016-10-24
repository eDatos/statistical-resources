package org.siemac.metamac.statistical.resources.web.server.stream.facade;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;

public interface StreamMessagingFacade {

    public void sendNewDatasetVersionPublished(DatasetVersionDto datasetVersionDto) throws MetamacException;

}
