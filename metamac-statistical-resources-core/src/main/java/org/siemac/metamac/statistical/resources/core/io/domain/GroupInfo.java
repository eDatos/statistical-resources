package org.siemac.metamac.statistical.resources.core.io.domain;

import java.util.List;

import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;

public class GroupInfo {

    private String              groupId            = null;
    private List<ComponentInfo> dimensionsInfoList = null;

    public GroupInfo(String attributeId, List<ComponentInfo> dimensionsInfoList) {
        this.groupId = attributeId;
        this.dimensionsInfoList = dimensionsInfoList;
    }

    public List<ComponentInfo> getDimensionsInfoList() {
        return dimensionsInfoList;
    }

    public String getAttributeId() {
        return groupId;
    }
}
