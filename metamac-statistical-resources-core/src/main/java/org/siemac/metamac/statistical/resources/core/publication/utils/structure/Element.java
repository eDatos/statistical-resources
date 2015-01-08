package org.siemac.metamac.statistical.resources.core.publication.utils.structure;

import java.util.LinkedList;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

public class Element {

    private String                      title;
    private TypeRelatedResourceEnum     type;
    private StatisticalResourceTypeEnum relatedResourceType;
    private String                      relatedResourceCode;
    private LinkedList<Element>         elements = new LinkedList<Element>();
    private int                         lineNumber;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}