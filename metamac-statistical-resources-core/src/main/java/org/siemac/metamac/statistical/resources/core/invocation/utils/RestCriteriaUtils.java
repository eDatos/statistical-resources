package org.siemac.metamac.statistical.resources.core.invocation.utils;

import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;


public class RestCriteriaUtils {

    
    
    
    public static String fieldComparison(Enum field, ComparisonOperator operator, Object value) {
        StringBuilder conditionBuilder = new StringBuilder();
        conditionBuilder.append(field)
                        .append(" ").append(operator.name()).append(" ");
        conditionBuilder.append("\"").append(value).append("\"");
        return conditionBuilder.toString();
    }
    
    
    public static void appendConditionToQuery(StringBuilder queryBuilder, String condition) {
        if (queryBuilder.length() > 0) {
            queryBuilder.append(" ").append(LogicalOperator.AND.name()).append(" ");
        }
        queryBuilder.append(condition);
    }
    
    public static void appendConditionToQuery(StringBuilder queryBuilder, LogicalOperator operator, String condition) {
        if (queryBuilder.length() > 0) {
            queryBuilder.append(" ").append(operator.name()).append(" ");
        }
        queryBuilder.append(condition);
    }
}

