package org.siemac.metamac.statistical.resources.core.stream.serviceapi;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public interface StreamMessagingServiceFacade {

    public void sendNewDatasetVersionPublished(DatasetVersion datasetVersion) throws MetamacException;

}
