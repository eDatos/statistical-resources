package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.web.common.client.widgets.BaseNavigableTreeGrid;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class NavigableTreeGrid extends BaseNavigableTreeGrid<RelatedResourceDto> {

    @Override
    protected List<PlaceRequest> buildLocation(RelatedResourceDto relatedResourceDto) {
        return PlaceRequestUtils.buildAbsoluteResourcePlaceRequest(relatedResourceDto);
    }
}
