package org.siemac.metamac.statistical.resources.web.shared.agency;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=false)
public class GetAgenciesPaginatedList {
    
    @In(1)
    int                   firstResult;

    @In(2)
    int                   maxResults;

    @In(3)
    String                query;

    @Out(1)
    List<ExternalItemDto> agenciesList;

    @Out(2)
    Integer               pageNumber;

    @Out(3)
    Integer               totalResults;
}
