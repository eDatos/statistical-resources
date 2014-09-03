package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.client.widgets.BaseNavigableTreeGrid;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class NavigableExternalItemTreeGrid extends BaseNavigableTreeGrid<ExternalItemDto> {

    @Override
    protected List<PlaceRequest> buildLocation(ExternalItemDto relatedResourceDto) {
        throw new UnsupportedOperationException();
    }
}
