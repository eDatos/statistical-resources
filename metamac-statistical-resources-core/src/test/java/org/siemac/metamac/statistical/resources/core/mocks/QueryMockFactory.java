package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class QueryMockFactory extends MockFactory<Query> {

    public static final String        QUERY_BASIC_01_NAME         = "QUERY_BASIC_01";
    public static final Query         QUERY_BASIC_01              = StatisticalResourcesDoMocks.mockQuery();

    public static final String        QUERY_BASIC_ORDERED_01_NAME = "QUERY_BASIC_ORDERED_01";
    public static final Query         QUERY_BASIC_ORDERED_01      = mockWithCode("a");

    public static final String        QUERY_BASIC_ORDERED_02_NAME = "QUERY_BASIC_ORDERED_02";
    public static final Query         QUERY_BASIC_ORDERED_02      = mockWithCode("b");

    public static final String        QUERY_BASIC_ORDERED_03_NAME = "QUERY_BASIC_ORDERED_03";
    public static final Query         QUERY_BASIC_ORDERED_03      = mockWithCode("c");

    private static Map<String, Query> mocks;

    static {
        mocks = new HashMap<String, Query>();
        registerMocks(QueryMockFactory.class, Query.class, mocks);
    }

    @Override
    public Query getMock(String id) {
        return mocks.get(id);
    }

    public static Query createQueryWithNameableNull() {
        Query query = StatisticalResourcesDoMocks.mockQuery();
        query.setNameableStatisticalResource(null);
        return query;
    }

    private static Query mockWithCode(String code) {
        Query q = StatisticalResourcesDoMocks.mockQuery();
        q.getNameableStatisticalResource().setCode(code);
        return q;
    }

}
