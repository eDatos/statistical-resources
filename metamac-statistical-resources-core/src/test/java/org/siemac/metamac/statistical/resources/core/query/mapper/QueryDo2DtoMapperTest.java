package org.siemac.metamac.statistical.resources.core.query.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_02_BASIC_ORDERED_01_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_03_BASIC_ORDERED_02_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_05_WITH_DATASET_VERSION_NAME;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
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
    private QueryMockFactory queryMockFactory;

    @Test
    @MetamacMock(QUERY_01_BASIC_NAME)
    public void testQueryDo2DtoWithoutDatasetVersion() {
        Query expected = queryMockFactory.getMock(QUERY_01_BASIC_NAME);
        QueryDto actual = queryDo2DtoMapper.queryDoToDto(expected);
        assertEqualsQuery(expected, actual);
    }

    @Test
    @MetamacMock(QUERY_05_WITH_DATASET_VERSION_NAME)
    public void testQueryDo2DtoWithDatasetVersion() {
        Query expected = queryMockFactory.getMock(QUERY_05_WITH_DATASET_VERSION_NAME);
        QueryDto actual = queryDo2DtoMapper.queryDoToDto(expected);
        assertEqualsQuery(expected, actual);
        assertTrue(actual.getDatasetVersion().equals(expected.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn()));
    }

    @Test
    @MetamacMock({QUERY_02_BASIC_ORDERED_01_NAME, QUERY_03_BASIC_ORDERED_02_NAME, QUERY_05_WITH_DATASET_VERSION_NAME})
    public void testQueryDoListToDtoList() {
        List<Query> expected = new ArrayList<Query>();
        expected.add(queryMockFactory.getMock(QUERY_02_BASIC_ORDERED_01_NAME));
        expected.add(queryMockFactory.getMock(QUERY_03_BASIC_ORDERED_02_NAME));
        expected.add(queryMockFactory.getMock(QUERY_05_WITH_DATASET_VERSION_NAME));
        
        List<QueryDto> actual = queryDo2DtoMapper.queryDoListToDtoList(expected);

        assertEquals(expected.size(), actual.size());
        assertEqualsQueryDoAndDtoCollection(expected, actual);
    }

}
