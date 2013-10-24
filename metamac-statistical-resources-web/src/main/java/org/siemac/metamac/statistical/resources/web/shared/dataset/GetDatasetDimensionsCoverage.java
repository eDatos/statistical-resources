package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasetDimensionsCoverage {

    @In(1)
    String                         datasetVersionUrn;

    @In(2)
    List<String>                   dimensionsIds;

    @In(3)
    MetamacWebCriteria             criteria;

    @Out(1)
    Map<String, List<CodeItemDto>> codesDimensions;

}
