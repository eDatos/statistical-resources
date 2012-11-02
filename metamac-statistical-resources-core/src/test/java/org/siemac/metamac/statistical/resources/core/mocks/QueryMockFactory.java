package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class QueryMockFactory extends MockFactory<Query> {
    
    public static final String QueryBasic01                = "QueryBasic01";
    
    public static final String QueryBasicOrdered01                = "QueryBasicOrdered01"; 
    public static final String QueryBasicOrdered02                = "QueryBasicOrdered02"; 
    public static final String QueryBasicOrdered03                = "QueryBasicOrdered03"; 
    
    private static Map<String,Query> mocks;
    
    static {
        mocks = new HashMap<String, Query>();
        mocks.put(QueryBasic01, StatisticalResourcesDoMocks.mockQuery());
        
        
        {
            Query q = StatisticalResourcesDoMocks.mockQuery();
            q.getNameableStatisticalResource().setCode("a"+q.getNameableStatisticalResource().getCode());
            mocks.put(QueryBasicOrdered01, q);
            
            q = StatisticalResourcesDoMocks.mockQuery();
            q.getNameableStatisticalResource().setCode("b"+q.getNameableStatisticalResource().getCode());
            mocks.put(QueryBasicOrdered02, q);
            
            q = StatisticalResourcesDoMocks.mockQuery();
            q.getNameableStatisticalResource().setCode("c"+q.getNameableStatisticalResource().getCode());
            mocks.put(QueryBasicOrdered03, q);
        }
        
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
    

}

