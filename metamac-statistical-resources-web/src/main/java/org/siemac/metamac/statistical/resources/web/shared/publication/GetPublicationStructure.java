package org.siemac.metamac.statistical.resources.web.shared.publication;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetPublicationStructure {

    @In(1)
    String                  publicationVersionUrn;

    @Out(1)
    PublicationVersionDto   publicationVersionDto;

    @Out(2)
    PublicationStructureDto publicationStructureDto;
}
