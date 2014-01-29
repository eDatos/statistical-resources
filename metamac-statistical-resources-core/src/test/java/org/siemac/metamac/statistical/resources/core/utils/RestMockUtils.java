package org.siemac.metamac.statistical.resources.core.utils;

import static org.siemac.metamac.rest.api.utils.RestCriteriaUtils.appendConditionToQuery;
import static org.siemac.metamac.rest.api.utils.RestCriteriaUtils.fieldComparison;
import static org.siemac.metamac.rest.api.utils.RestCriteriaUtils.transformListIntoQuotedCommaSeparatedString;

import java.math.BigInteger;
import java.util.List;

import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.ListBase;

public class RestMockUtils {

    public static String mockQueryFindPublishedResourcesUrnsAsList(Enum urnPropertyName, Enum procStatusPropertyName, String procStatusValue, List<String> urns) {
        StringBuilder query = new StringBuilder();
        appendConditionToQuery(query, fieldComparison(urnPropertyName, ComparisonOperator.IN, transformListIntoQuotedCommaSeparatedString(urns)));
        if (procStatusPropertyName != null) {
            appendConditionToQuery(query, fieldComparison(procStatusPropertyName, ComparisonOperator.EQ, procStatusValue));
        }
        return query.toString();
    }

    protected static void populateListBaseWithResourcesWithOnlyUrns(ListBase listBase, List<String> urns) {
        listBase.setTotal(BigInteger.valueOf(urns.size()));
    }
}
