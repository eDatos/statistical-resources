package org.siemac.metamac.statistical.resources.core.io.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;

public class AttributeInfo {

    public enum AttachmentLevel {
        DATASET, GROUP, DIMENSION, OBSERVATION;
    }

    private String              attributeId        = null;
    private AttachmentLevel     attachmentLevel    = null;

    private List<GroupInfo>     attachmentGroups   = null; // Optional and only for AttachmentLevel.DIMENSION
    private List<ComponentInfo> dimensionsInfoList = null; // Only for AttachmentLevel.DIMENSION

    private GroupInfo           group              = null; // Only for AttachmentLevel.GROUP

    public AttributeInfo(String attributeId, AttachmentLevel attachmentLevel) {
        this.attributeId = attributeId;
        this.attachmentLevel = attachmentLevel;
    }

    public AttachmentLevel getAttachmentLevel() {
        return attachmentLevel;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public List<GroupInfo> getAttachmentGroups() {
        return Collections.unmodifiableList(attachmentGroups);
    }

    public List<ComponentInfo> getDimensionsInfoList() {
        return Collections.unmodifiableList(dimensionsInfoList);
    }

    public GroupInfo getGroup() {
        return group;
    }

    public void addAttachmentGroup(GroupInfo attachmenGroup) {
        if (attachmentGroups == null) {
            attachmentGroups = new LinkedList<GroupInfo>();
        }
        attachmentGroups.add(attachmenGroup);
    }

    /**
     * Note: the list must be in order respect to the DSD dimensions definitions
     * 
     * @param componentInfo
     */
    public void addAllDimensionInfo(List<ComponentInfo> componentInfo) {
        if (dimensionsInfoList == null) {
            dimensionsInfoList = new LinkedList<ComponentInfo>();
        }
        dimensionsInfoList.addAll(componentInfo);
    }

    public void setGroup(GroupInfo group) {
        this.group = group;
    }
}
