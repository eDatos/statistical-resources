package org.siemac.metamac.statistical.resources.core.multidataset.utils;

import java.util.Comparator;

import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;

public class MultidatasetCubeComparator implements Comparator<MultidatasetCube> {

    @Override
    public int compare(MultidatasetCube o1, MultidatasetCube o2) {
        return o1.getOrderInMultidataset().compareTo(o2.getOrderInMultidataset());
    }
}
