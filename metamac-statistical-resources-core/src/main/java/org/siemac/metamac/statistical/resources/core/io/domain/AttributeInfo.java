package org.siemac.metamac.statistical.resources.core.io.domain;

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
        return attachmentGroups;
    }

    public List<ComponentInfo> getDimensionsInfoList() {
        return dimensionsInfoList;
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

    public void addDimensionInfo(ComponentInfo componentInfo) {
        if (dimensionsInfoList == null) {
            dimensionsInfoList = new LinkedList<ComponentInfo>();
        }
        dimensionsInfoList.add(componentInfo);
    }

    public void setGroup(GroupInfo group) {
        this.group = group;
    }
}
