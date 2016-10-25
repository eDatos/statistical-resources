package org.siemac.metamac.statistical.resources.core.stream.serviceapi;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;

public interface StreamMessagingServiceFacade {

    public void sendNewDatasetVersionPublished(DatasetVersionDto datasetVersionDto) throws MetamacException;

}
