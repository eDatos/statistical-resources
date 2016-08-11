package org.siemac.metamac.statistical.resources.core.publication.utils.structure;

import java.util.LinkedList;

public class PublicationStructure {

    private String              publicationTitle;
    private LinkedList<Element> elements = new LinkedList<Element>();

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public void setPublicationTitle(String title) {
        this.publicationTitle = title;
    }

    public LinkedList<Element> getElements() {
        return elements;
    }

    public void addElement(Element element) {
        getElements().add(element);
    }
}
