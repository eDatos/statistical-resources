package org.siemac.metamac.statistical.resources.core.stream.serviceapi;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.enume.domain.StreamMessageStatusEnum;

public interface StreamMessagingServiceFacade {

    public void updateMessageStatus(HasSiemacMetadata version, StreamMessageStatusEnum status);

    public void sendNewVersionPublished(HasSiemacMetadata version) throws MetamacException;

}
