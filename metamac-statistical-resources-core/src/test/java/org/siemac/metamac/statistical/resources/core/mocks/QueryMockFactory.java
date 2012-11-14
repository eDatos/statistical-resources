package org.siemac.metamac.statistical.resources.core.mocks;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks.*;
import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.springframework.stereotype.Component;

@Component
public class QueryMockFactory extends MockFactory<Query> {

    public static final String        QUERY_01_BASIC_NAME            = "QUERY_01_BASIC";
    public static final Query         QUERY_01_BASIC                 = mockPersistedQuery(); ;

    public static final String        QUERY_02_BASIC_ORDERED_01_NAME = "QUERY_02_BASIC_ORDERED_01";
    public static final Query         QUERY_02_BASIC_ORDERED_01      = mockPersistedQueryWithCode("a");

    public static final String        QUERY_03_BASIC_ORDERED_02_NAME = "QUERY_03_BASIC_ORDERED_02";
    public static final Query         QUERY_03_BASIC_ORDERED_02      = mockPersistedQueryWithCode("b");

    public static final String        QUERY_04_BASIC_ORDERED_03_NAME = "QUERY_04_BASIC_ORDERED_03";
    public static final Query         QUERY_04_BASIC_ORDERED_03      = mockPersistedQueryWithCode("c");

    private static Map<String, Query> mocks;

    static {
        mocks = new HashMap<String, Query>();
        registerMocks(QueryMockFactory.class, Query.class, mocks);
    }

    @Override
    public Query getMock(String id) {
        return mocks.get(id);
    }

    private static Query mockPersistedQueryWithCode(String code) {
        Query query = mockPersistedQuery();
        query.getNameableStatisticalResource().setCode(code);
        return query;
    }
}
