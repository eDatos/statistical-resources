package org.siemac.metamac.statistical.resources.core.publication.utils.structure;

import java.util.LinkedList;

public class PublicationStructure {

    private String              publicationName;
    private LinkedList<Element> elements = new LinkedList<Element>();

    public String getPublicationName() {
        return publicationName;
    }

    public void setPublicationName(String name) {
        this.publicationName = name;
    }

    public LinkedList<Element> getElements() {
        return elements;
    }

    public void addElement(Element element) {
        getElements().add(element);
    }
}
