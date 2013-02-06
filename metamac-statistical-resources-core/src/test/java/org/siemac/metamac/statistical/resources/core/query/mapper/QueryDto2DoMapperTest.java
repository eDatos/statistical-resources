package org.siemac.metamac.statistical.resources.core.query.mapper;

import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDtoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class QueryDto2DoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetVersionMockFactory datasetVersionMockFactory;
    
    @Autowired
    private QueryDto2DoMapper queryDto2DoMapper;

    @Test
    public void testQueryDtoToDoWithoutDatasetVersion() throws MetamacException {
        QueryDto expected = StatisticalResourcesDtoMocks.mockQueryDto();
        Query actual = queryDto2DoMapper.queryDtoToDo(expected);
        assertEqualsQuery(expected, actual);
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testQueryDtoToDoWithDatasetVersion() throws MetamacException {
        QueryDto expected = StatisticalResourcesDtoMocks.mockQueryDtoWithDatasetVersion(datasetVersionMockFactory.getMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        Query actual = queryDto2DoMapper.queryDtoToDo(expected);
        assertEqualsQuery(expected, actual);
        assertTrue(expected.getDatasetVersion().equals(actual.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn()));
    }
    
}
