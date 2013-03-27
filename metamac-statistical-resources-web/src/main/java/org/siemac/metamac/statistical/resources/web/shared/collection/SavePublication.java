package org.siemac.metamac.statistical.resources.web.shared.collection;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SavePublication {

    @In(1)
    PublicationDto publicationDto;

    @Out(1)
    PublicationDto savedPublication;

}
