package org.siemac.metamac.statistical.resources.web.shared.publication;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.PublicationVersionWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetPublicationVersions {

    @In(1)
    int                             firstResult;

    @In(2)
    int                             maxResults;

    @In(3)
    PublicationVersionWebCriteria   criteria;

    @Out(1)
    List<PublicationVersionBaseDto> publicationBaseDtos;

    @Out(2)
    Integer                         firstResultOut;

    @Out(3)
    Integer                         totalResults;
}
