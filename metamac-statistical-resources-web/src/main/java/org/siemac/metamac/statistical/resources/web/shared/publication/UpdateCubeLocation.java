package org.siemac.metamac.statistical.resources.web.shared.publication;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateCubeLocation {

    @In(1)
    String                  publicationVersionUrn;

    @In(2)
    String                  cubeUrn;

    @In(3)
    String                  parentTargetUrn;

    @In(4)
    Long                    orderInLevel;

    @Out(1)
    PublicationStructureDto publicationStructureDto;
}
