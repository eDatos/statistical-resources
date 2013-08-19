package org.siemac.metamac.statistical.resources.web.shared.publication;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetVersionsOfPublication {

    @In(1)
    String                      publicationVersionUrn;

    @Out(1)
    List<PublicationVersionDto> publicationVersionDtos;
}
