package org.siemac.metamac.statistical.resources.core.invocation.utils;

import static org.siemac.metamac.rest.api.constants.RestApiConstants.BLANK;
import static org.siemac.metamac.rest.api.constants.RestApiConstants.COMMA;
import static org.siemac.metamac.rest.api.constants.RestApiConstants.LEFT_PARENTHESIS;
import static org.siemac.metamac.rest.api.constants.RestApiConstants.QUOTE;
import static org.siemac.metamac.rest.api.constants.RestApiConstants.RIGHT_PARENTHESIS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;

public class RestCriteriaUtils {

    @SuppressWarnings("rawtypes")
    public static String fieldComparison(Enum field, ComparisonOperator operator, Object value) {
        StringBuilder conditionBuilder = new StringBuilder();
        conditionBuilder.append(field).append(BLANK).append(operator.name()).append(BLANK);
        if (ComparisonOperator.IN.equals(operator)) {
            conditionBuilder.append(LEFT_PARENTHESIS).append(value).append(RIGHT_PARENTHESIS);
        } else {
            conditionBuilder.append(QUOTE).append(value).append(QUOTE);
        }
        return conditionBuilder.toString();
    }

    public static void appendConditionToQuery(StringBuilder queryBuilder, String condition) {
        if (queryBuilder.length() > 0) {
            queryBuilder.append(BLANK).append(LogicalOperator.AND.name()).append(BLANK);
        }
        queryBuilder.append(condition);
    }

    public static void appendConditionToQuery(StringBuilder queryBuilder, LogicalOperator operator, String condition) {
        if (queryBuilder.length() > 0) {
            queryBuilder.append(BLANK).append(operator.name()).append(BLANK);
        }
        queryBuilder.append(condition);
    }

    public static void appendCommaSeparatedQuotedElement(StringBuilder stringBuilder, String element) {
        if (stringBuilder.length() > 0) {
            stringBuilder.append(COMMA);
        }
        stringBuilder.append(QUOTE).append(element).append(QUOTE);
    }

    public static String transformListIntoQuotedCommaSeparatedString(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (String item : list) {
            appendCommaSeparatedQuotedElement(result, item);
        }
        return result.toString();
    }

    public static List<String> processExternalItemsUrns(Collection<ExternalItem> externalItems) {
        List<String> urns = new ArrayList<String>();
        for (ExternalItem item : externalItems) {
            urns.add(item.getUrn());
        }
        return urns;
    }

}
