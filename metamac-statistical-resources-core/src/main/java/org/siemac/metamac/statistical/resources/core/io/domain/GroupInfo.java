package org.siemac.metamac.statistical.resources.core.io.domain;

import java.util.Collections;
import java.util.List;

import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;

public class GroupInfo {

    private String              groupId                  = null;
    private List<ComponentInfo> dimensionsInfoListSorted = null;

    /**
     * @param groupId
     * @param dimensionsInfoListSorted> the list must be in order respect to the DSD dimensions definitions
     */
    public GroupInfo(String groupId, List<ComponentInfo> dimensionsInfoListSorted) {
        this.groupId = groupId;
        this.dimensionsInfoListSorted = dimensionsInfoListSorted;
    }

    public List<ComponentInfo> getDimensionsInfoList() {
        return Collections.unmodifiableList(dimensionsInfoListSorted);
    }

    public String getGroupId() {
        return groupId;
    }

}
