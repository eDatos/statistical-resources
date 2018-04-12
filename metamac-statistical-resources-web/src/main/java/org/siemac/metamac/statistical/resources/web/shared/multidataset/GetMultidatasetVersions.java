package org.siemac.metamac.statistical.resources.web.shared.multidataset;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.MultidatasetVersionWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetMultidatasetVersions {

    @In(1)
    int                             firstResult;

    @In(2)
    int                             maxResults;

    @In(3)
    MultidatasetVersionWebCriteria   criteria;

    @Out(1)
    List<MultidatasetVersionBaseDto> multidatasetBaseDtos;

    @Out(2)
    Integer                         firstResultOut;

    @Out(3)
    Integer                         totalResults;
}
