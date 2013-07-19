package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasetDimensionCoverage {

    @In(1)
    String            datasetVersionUrn;

    @In(2)
    String            dimensionId;

    @Out(1)
    List<CodeItemDto> codesDimension;

}
