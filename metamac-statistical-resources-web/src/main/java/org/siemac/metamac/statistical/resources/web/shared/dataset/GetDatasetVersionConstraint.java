package org.siemac.metamac.statistical.resources.web.shared.dataset;

import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasetVersionConstraint {

    @In(1)
    String               datasetVersionUrn;

    @Optional
    @Out(1)
    ContentConstraintDto contentConstraint;
}
