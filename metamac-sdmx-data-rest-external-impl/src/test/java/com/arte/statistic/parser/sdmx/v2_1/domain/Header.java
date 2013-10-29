package com.arte.statistic.parser.sdmx.v2_1.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Header implements Serializable, Cloneable {

    private static final long     serialVersionUID = 1L;

    // Required
    private String                id;
    private String                prepared;
    private String                senderID;
    private Set<PayloadStructure> structure        = new HashSet<PayloadStructure>();

    private Boolean               test             = false;

    public Header() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public String getPrepared() {
        return "2013-09-18T15:01:18.428+01:00";
    }

    public void setPrepared(String prepared) {
        this.prepared = prepared;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public Set<PayloadStructure> getStructure() {
        return structure;
    }

    @SuppressWarnings("unused")
    private void setStructure(Set<PayloadStructure> structure) {
        this.structure = structure;
    }

    /**
     * Adds an object to the unidirectional to-many
     * association.
     * It is added the collection {@link #getStructure}.
     */
    public void addStructure(PayloadStructure structureElement) {
        getStructure().add(structureElement);
    }

    /**
     * Removes an object from the unidirectional to-many
     * association.
     * It is removed from the collection {@link #getStructure}.
     */
    public void removeStructure(PayloadStructure structureElement) {
        getStructure().remove(structureElement);
    }

    /**
     * Removes all object from the unidirectional
     * to-many association.
     * All elements are removed from the collection {@link #getStructure}.
     */
    public void removeAllStructure() {
        getStructure().clear();
    }
}
