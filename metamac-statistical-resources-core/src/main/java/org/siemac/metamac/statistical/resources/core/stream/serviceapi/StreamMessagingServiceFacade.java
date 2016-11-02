package org.siemac.metamac.statistical.resources.core.stream.serviceapi;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.StreamMessageStatusEnum;

public interface StreamMessagingServiceFacade {

    public void sendNewDatasetVersionPublished(DatasetVersion datasetVersion) throws MetamacException;

    public void updateMessageStatus(DatasetVersion datasetVersion, StreamMessageStatusEnum status);

}
