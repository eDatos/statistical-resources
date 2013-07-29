package org.siemac.metamac.statistical.resources.web.shared.publication;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdatePublicationVersionProcStatus {

    @In(1)
    PublicationVersionDto publicationVersionToUpdateProcStatus;

    @In(2)
    ProcStatusEnum        nextProcStatus;

    @Out(1)
    PublicationVersionDto publicationVersionDto;
}
