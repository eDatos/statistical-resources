package org.siemac.metamac.statistical.resources.core.query.mapper;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_01_BASIC;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_02_BASIC_ORDERED_01;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_02_BASIC_ORDERED_01_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_03_BASIC_ORDERED_02;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.QUERY_03_BASIC_ORDERED_02_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryDoAndDtoCollection;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
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
    @MetamacMock(QUERY_01_BASIC_NAME)
    public void testQueryDo2Dto() {
        Query expected = QUERY_01_BASIC;
        QueryDto actual = queryDo2DtoMapper.queryDoToDto(expected);
        assertEqualsQuery(expected, actual);
    }

    @Test
    @MetamacMock({QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME})
    public void testQueryDoListToDtoList() {
        List<Query> expected = new ArrayList<Query>();
        expected.add(QUERY_02_BASIC_ORDERED_01);
        expected.add(QUERY_03_BASIC_ORDERED_02);
        
        List<QueryDto> actual = queryDo2DtoMapper.queryDoListToDtoList(expected);
        
        assertEquals(expected.size(), actual.size());
        assertEqualsQueryDoAndDtoCollection(expected, actual);
    }

}
