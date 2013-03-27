package org.siemac.metamac.statistical.resources.web.shared.collection;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class VersionPublication {

    @In(1)
    String          urn;

    @In(2)
    VersionTypeEnum versionType;

    @Out(1)
    PublicationDto   publicationDto;

}
