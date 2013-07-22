package org.siemac.metamac.statistical.resources.web.shared.publication;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SavePublication {

    @In(1)
    PublicationVersionDto publicationVersionDto;

    @In(2)
    ExternalItemDto       statisticalOperationDto;

    @Out(1)
    PublicationVersionDto savedPublicationVersion;
}
