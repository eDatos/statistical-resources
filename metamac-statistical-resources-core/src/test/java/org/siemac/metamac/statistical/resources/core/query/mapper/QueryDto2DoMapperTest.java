package org.siemac.metamac.statistical.resources.core.query.mapper;

import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
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
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testQueryDtoToDo() throws MetamacException {
        QueryVersionDto expected = StatisticalResourcesDtoMocks.mockQueryVersionDto(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));
        QueryVersion actual = queryDto2DoMapper.queryVersionDtoToDo(expected);
        assertEqualsQueryVersion(expected, actual);
        assertTrue(expected.getRelatedDatasetVersion().getUrn().equals(actual.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn()));
    }
}
