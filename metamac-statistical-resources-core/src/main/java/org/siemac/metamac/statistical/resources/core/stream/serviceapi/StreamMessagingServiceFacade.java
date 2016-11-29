package org.siemac.metamac.statistical.resources.core.stream.serviceapi;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;

public interface StreamMessagingServiceFacade {

    public void sendNewVersionPublished(HasSiemacMetadata version) throws MetamacException;

}
