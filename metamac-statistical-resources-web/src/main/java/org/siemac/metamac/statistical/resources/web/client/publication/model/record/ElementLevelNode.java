package org.siemac.metamac.statistical.resources.web.client.publication.model.record;

import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.ElementLevelDS;
import org.siemac.metamac.web.common.client.widgets.NavigableTreeNode;

public class ElementLevelNode extends NavigableTreeNode {

    public ElementLevelNode() {
    }

    public void setUrn(Long value) {
        setAttribute(ElementLevelDS.URN, value);
    }

    @Override
    public void setTitle(String value) {
        setAttribute(ElementLevelDS.TITLE, value);
    }

    public void setDescription(String value) {
        setAttribute(ElementLevelDS.DESCRIPTION, value);
    }

    public void setOrderInLevel(String value) {
        setAttribute(ElementLevelDS.ORDER_IN_LEVEL, value);
    }

    public void setParentChapterUrn(String value) {
        setAttribute(ElementLevelDS.PARENT_CHAPTER_URN, value);
    }
}
