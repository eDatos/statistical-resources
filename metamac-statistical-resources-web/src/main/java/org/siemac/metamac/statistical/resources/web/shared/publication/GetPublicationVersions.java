package org.siemac.metamac.statistical.resources.web.shared.publication;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetPublicationVersions {

    @In(1)
    int                                       firstResult;

    @In(2)
    int                                       maxResults;

    @In(3)
    VersionableStatisticalResourceWebCriteria criteria;

    @Out(1)
    List<PublicationVersionDto>               publicationDtos;

    @Out(2)
    Integer                                   firstResultOut;

    @Out(3)
    Integer                                   totalResults;
}