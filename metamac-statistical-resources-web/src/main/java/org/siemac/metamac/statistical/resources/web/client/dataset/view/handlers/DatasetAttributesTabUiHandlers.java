package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.RepresentationDto;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface DatasetAttributesTabUiHandlers extends BaseUiHandlers {

    void retrieveAttributeInstances(DsdAttributeDto dsdAttributeDto);
    void retrieveItemsFromItemSchemeForDatasetLevelAttribute(RepresentationDto representationDto, int firstResult, int maxResult, MetamacWebCriteria condition);
    void retrieveDimensionCoverage(String dimensionId, MetamacWebCriteria metamacWebCriteria);
}
