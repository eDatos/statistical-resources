package org.siemac.metamac.statistical.resources.web.shared.dataset;

import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasetConstraint {

    @In(1)
    String               datasetVersionUrn;

    @Out(1)
    DatasetVersionDto    datasetVersion;

    @Optional
    @Out(2)
    ContentConstraintDto contentConstraint;

    @Optional
    @Out(3)
    RegionValueDto       region;
}
