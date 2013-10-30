package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.Map;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasetDimensionsVariableMapping {

    @In(1)
    String              datasetVersionUrn;

    @Out(1)
    Map<String, String> dimensionsMapping;
}
