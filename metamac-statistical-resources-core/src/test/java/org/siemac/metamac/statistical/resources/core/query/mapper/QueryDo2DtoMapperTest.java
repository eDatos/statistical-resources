package org.siemac.metamac.statistical.resources.core.query.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesAsserts;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class QueryDo2DtoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private QueryDo2DtoMapper queryDo2DtoMapper;
    
    @Test
    public void testQueryDo2Dto() {
        Query entity = StatisticalResourcesDoMocks.mockQuery();
        QueryDto dto = queryDo2DtoMapper.queryDoToDto(entity);
        StatisticalResourcesAsserts.assertEqualsQuery(entity, dto);
    }

    @Test
    public void testQueryDoListToDtoList() {
        List<Query> queries = new ArrayList<Query>();
        queries.add(StatisticalResourcesDoMocks.mockQuery());
        queries.add(StatisticalResourcesDoMocks.mockQuery());
        
        List<QueryDto> queriesDto = queryDo2DtoMapper.queryDoListToDtoList(queries);
        
        assertEquals(queries.size(), queriesDto.size());
        StatisticalResourcesAsserts.assertEqualsQueryDoAndDtoCollection(queries, queriesDto);
    }

}
