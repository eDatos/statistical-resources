package org.siemac.metamac.statistical.resources.web.client.multidataset.model.ds;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetCubeDto;
import org.siemac.metamac.web.common.client.widgets.NavigableTreeNode;

public class MultidatasetCubeTreeNode extends NavigableTreeNode {

    public MultidatasetCubeTreeNode() {
    }

    public void setUrn(String value) {
        setAttribute(MultidatasetCubeDS.URN, value);
    }

    public String getUrn() {
        return getAttributeAsString(MultidatasetCubeDS.URN);
    }

    public void setIdentifier(String value) {
        setAttribute(MultidatasetCubeDS.IDENTIFIER, value);
    }

    @Override
    public void setTitle(String value) {
        setAttribute(MultidatasetCubeDS.TITLE, value);
    }

    public void setDescription(String value) {
        setAttribute(MultidatasetCubeDS.DESCRIPTION, value);
    }

    public void setOrderInMultidataset(Long value) {
        setAttribute(MultidatasetCubeDS.ORDER_IN_LEVEL, value);
    }

    public void setMultidatasetCubeDto(MultidatasetCubeDto MultidatasetCubeDto) {
        setAttribute(MultidatasetCubeDS.DTO, MultidatasetCubeDto);
    }

    public MultidatasetCubeDto getMultidatasetCubeDto() {
        return (MultidatasetCubeDto) getAttributeAsObject(MultidatasetCubeDS.DTO);
    }
}
