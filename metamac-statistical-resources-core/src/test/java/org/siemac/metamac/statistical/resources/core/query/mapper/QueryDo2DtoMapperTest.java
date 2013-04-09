package org.siemac.metamac.statistical.resources.core.query.mapper;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersionDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_01_WITH_SELECTION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_02_BASIC_ORDERED_01_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_03_BASIC_ORDERED_02_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_05_BASIC_NAME;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
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
    private QueryDo2DtoMapper  queryDo2DtoMapper;

    @Autowired
    private QueryVersionMockFactory queryMockFactory;

    @Test
    @MetamacMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
    public void testQueryDo2Dto() {
        QueryVersion expected = queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME);
        QueryDto actual = queryDo2DtoMapper.queryVersionDoToDto(expected);
        assertEqualsQueryVersion(expected, actual);
    }

    @Test
    @MetamacMock({QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME, QUERY_VERSION_05_BASIC_NAME, QUERY_VERSION_01_WITH_SELECTION_NAME})
    public void testQueryDoListToDtoList() {
        List<QueryVersion> expected = new ArrayList<QueryVersion>();
        expected.add(queryMockFactory.retrieveMock(QUERY_VERSION_02_BASIC_ORDERED_01_NAME));
        expected.add(queryMockFactory.retrieveMock(QUERY_VERSION_03_BASIC_ORDERED_02_NAME));
        expected.add(queryMockFactory.retrieveMock(QUERY_VERSION_05_BASIC_NAME));
        expected.add(queryMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME));
        
        List<QueryDto> actual = queryDo2DtoMapper.queryVersionDoListToDtoList(expected);

        assertEquals(expected.size(), actual.size());
        assertEqualsQueryVersionDoAndDtoCollection(expected, actual);
    }
}
