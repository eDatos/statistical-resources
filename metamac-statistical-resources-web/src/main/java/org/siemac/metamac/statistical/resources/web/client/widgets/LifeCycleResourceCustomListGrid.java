package org.siemac.metamac.statistical.resources.web.client.widgets;

import org.siemac.metamac.web.common.client.utils.ListGridUtils;

public class LifeCycleResourceCustomListGrid extends NavigableListGrid {

    public LifeCycleResourceCustomListGrid() {
        ListGridUtils.setCheckBoxSelectionType(this);
    }
}
