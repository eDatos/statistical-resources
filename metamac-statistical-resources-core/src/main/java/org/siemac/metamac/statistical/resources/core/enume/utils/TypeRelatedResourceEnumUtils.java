package org.siemac.metamac.statistical.resources.core.enume.utils;

import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

public class TypeRelatedResourceEnumUtils extends BaseEnumUtils {

    public static boolean checkIfTypeRelatedResourceIsAny(TypeRelatedResourceEnum typeSearchedFor, TypeRelatedResourceEnum... possibleTypesRelatedResource) {
        for (TypeRelatedResourceEnum item : possibleTypesRelatedResource) {
            if (item.equals(typeSearchedFor)) {
                return true;
            }
        }
        return false;
    }
}
