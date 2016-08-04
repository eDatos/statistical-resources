package org.siemac.metamac.statistical.resources.web.shared.dataset;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class CreateDatasetConstraint {

    @In(1)
    String               datasetVersionUrn;

    @In(2)
    ExternalItemDto      maintainer;

    @Out(1)
    ContentConstraintDto contentConstraint;
}
