package org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.RelatedResourceBaseListItem;
import org.siemac.metamac.web.common.client.widgets.handlers.ListRecordNavigationClickHandler;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class RelatedResourceListItem extends RelatedResourceBaseListItem<RelatedResourceDto> {

    public RelatedResourceListItem(String name, String title, boolean editionMode, ListRecordNavigationClickHandler recordClickHandler) {
        super(name, title, editionMode, recordClickHandler);
    }

    @Override
    protected List<PlaceRequest> buildLocation(RelatedResourceDto relatedResourceDto) {
        return PlaceRequestUtils.buildAbsoluteResourcePlaceRequest(relatedResourceDto);
    }
}
