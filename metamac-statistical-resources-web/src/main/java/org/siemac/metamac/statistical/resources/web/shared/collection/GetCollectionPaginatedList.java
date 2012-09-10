package org.siemac.metamac.statistical.resources.web.shared.collection;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetCollectionPaginatedList {

    @In(1)
    String              operationUrn;

    @In(2)
    int                 firstResult;

    @In(3)
    int                 maxResults;

    @In(4)
    String              collection;

    @Out(1)
    List<CollectionDto> collectionList;

    @Out(2)
    Integer             firstResultOut;

    @Out(3)
    Integer             totalResults;
}
