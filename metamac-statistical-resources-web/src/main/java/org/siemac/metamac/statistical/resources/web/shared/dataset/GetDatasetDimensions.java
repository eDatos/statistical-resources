package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasetDimensions {

    @In(1)
    String       urn;

    @Out(1)
    List<String> datasetDimensionsIds;
}
