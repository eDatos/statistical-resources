package org.siemac.metamac.statistical.resources.core.io.domain;

import java.util.LinkedList;
import java.util.List;

public class AttributeInfo {

    public enum AttachmentLevel {
        DATASET, GROUP, DIMENSION, OBSERVATION;
    }

    private String          attributeId      = null;
    private AttachmentLevel attachmentLevel  = null;
    private List<String>    attachmentGroups = null;

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

    public List<String> getAttachmentGroups() {
        return attachmentGroups;
    }

    public void addAttachmentGroup(String attachmenGroup) {
        if (attachmentGroups == null) {
            attachmentGroups = new LinkedList<String>();
        }
        attachmentGroups.add(attachmenGroup);
    }
}
