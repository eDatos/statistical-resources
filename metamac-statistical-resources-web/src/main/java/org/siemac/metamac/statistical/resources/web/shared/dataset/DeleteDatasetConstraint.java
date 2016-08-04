package org.siemac.metamac.statistical.resources.web.shared.dataset;

import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class DeleteDatasetConstraint {

    @In(1)
    ContentConstraintDto constraintDto;

    @In(2)
    RegionValueDto       regionValueDto;
}
