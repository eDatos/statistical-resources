package org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;

public interface DatasetAttributesTabUiHandlers extends BaseUiHandlers {

    void retrieveAttributeInstances(DsdAttributeDto dsdAttributeDto);
}
