package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import org.siemac.metamac.statistical.resources.core.dto.publication.ElementLevelDto;

public interface TreeNodeClickAction {

    void onNodeClick(ElementLevelDto elementLevelDto);
}
