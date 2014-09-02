package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;

import com.gwtplatform.mvp.client.UiHandlers;

public interface DatasetConstraintsTabUiHandlers extends UiHandlers {

    void createConstraint();
    void deleteConstraint(ContentConstraintDto contentConstraintDto, RegionValueDto regionValueDto);
    void saveRegion(String contentConstraintUrn, RegionValueDto regionToSave, DsdDimensionDto selectedDimension);
    void retrieveCodes(DsdDimensionDto dsdDimensionDto);
    void retrieveConcepts(DsdDimensionDto dsdDimensionDto);
}
