package org.siemac.metamac.statistical.resources.core.io.domain;

import java.util.List;

import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;

public class GroupInfo {

    private String              groupId            = null;
    private List<ComponentInfo> dimensionsInfoList = null;

    public GroupInfo(String groupId, List<ComponentInfo> dimensionsInfoList) {
        this.groupId = groupId;
        this.dimensionsInfoList = dimensionsInfoList;
    }

    public List<ComponentInfo> getDimensionsInfoList() {
        return dimensionsInfoList;
    }

    public String getGroupId() {
        return groupId;
    }
}
