package org.siemac.metamac.statistical.resources.core.utils.predicates;

import org.apache.commons.lang3.StringUtils;
import org.siemac.metamac.core.common.util.MetamacPredicate;
import org.siemac.metamac.statistical.resources.core.utils.MetamacReflectionUtils;


public class ObjectEqualsStringFieldPredicate extends MetamacPredicate<Object> {

    private final String stringFieldName;
    private final String stringFieldValue;

    public ObjectEqualsStringFieldPredicate(String fieldName, String fieldValue) {
        this.stringFieldName = fieldName;
        this.stringFieldValue = fieldValue;
    }

    @Override
    protected boolean eval(Object obj) {
        String objectValue = StringUtils.EMPTY;
        try {
            objectValue = (String) MetamacReflectionUtils.getComplexFieldValue(obj, stringFieldName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.equals(stringFieldValue, objectValue);
    }
}
