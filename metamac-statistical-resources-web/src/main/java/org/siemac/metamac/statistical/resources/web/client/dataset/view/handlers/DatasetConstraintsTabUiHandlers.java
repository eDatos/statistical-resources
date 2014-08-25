package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface DatasetConstraintsTabUiHandlers extends UiHandlers {

    void createConstraint();
    void deleteConstraint(ContentConstraintDto contentConstraintDto, RegionValueDto regionValueDto);
}
