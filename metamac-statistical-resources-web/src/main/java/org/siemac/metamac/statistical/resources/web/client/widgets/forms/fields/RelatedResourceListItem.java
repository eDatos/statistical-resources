package org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.web.common.client.widgets.form.fields.RelatedResourceBaseListItem;

public class RelatedResourceListItem extends RelatedResourceBaseListItem<RelatedResourceDto> {

    public RelatedResourceListItem(String name, String title, boolean editionMode) {
        super(name, title, editionMode);
    }
}
