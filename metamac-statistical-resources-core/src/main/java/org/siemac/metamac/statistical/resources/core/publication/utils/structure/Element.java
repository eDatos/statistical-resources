package org.siemac.metamac.statistical.resources.core.publication.utils.structure;

import java.util.LinkedList;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

public class Element {

    private String                      name;
    private TypeRelatedResourceEnum     type;
    private StatisticalResourceTypeEnum relatedResourceType;
    private String                      relatedResourceCode;
    private LinkedList<Element>         elements = new LinkedList<Element>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeRelatedResourceEnum getType() {
        return type;
    }

    public void setType(TypeRelatedResourceEnum type) {
        this.type = type;
    }

    public StatisticalResourceTypeEnum getRelatedResourceType() {
        return relatedResourceType;
    }

    public void setRelatedResourceType(StatisticalResourceTypeEnum relatedResourceType) {
        this.relatedResourceType = relatedResourceType;
    }

    public String getRelatedResourceCode() {
        return relatedResourceCode;
    }

    public void setRelatedResourCode(String relatedResourceCode) {
        this.relatedResourceCode = relatedResourceCode;
    }

    public LinkedList<Element> getElements() {
        return elements;
    }

    public void addElement(Element element) {
        getElements().add(element);
    }
}