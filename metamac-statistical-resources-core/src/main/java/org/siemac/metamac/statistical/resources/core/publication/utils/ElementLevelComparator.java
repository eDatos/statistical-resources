package org.siemac.metamac.statistical.resources.core.publication.utils;

import java.util.Comparator;

import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;

public class ElementLevelComparator implements Comparator<ElementLevel> {

    @Override
    public int compare(ElementLevel o1, ElementLevel o2) {
        return o1.getOrderInLevel().compareTo(o2.getOrderInLevel());
    }
}
