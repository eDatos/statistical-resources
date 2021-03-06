package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetCodelistsWithVariable {

    @In(1)
    String                          variableUrn;

    @In(2)
    int                             firstResult;

    @In(3)
    int                             maxResults;

    @In(4)
    SrmExternalResourceRestCriteria criteria;

    @Out(1)
    List<ExternalItemDto>           codelists;

    @Out(2)
    Integer                         firstResultOut;

    @Out(3)
    Integer                         totalResults;
}
