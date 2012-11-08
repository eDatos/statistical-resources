package org.siemac.metamac.statistical.resources.core.query.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.*;
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
    private QueryDto2DoMapper queryDto2DoMapper;

    @Test
    public void testQueryDtoToDo() throws MetamacException {
        QueryDto expected = StatisticalResourcesDtoMocks.mockQueryDto();
        Query actual = queryDto2DoMapper.queryDtoToDo(expected);
        assertEqualsQuery(expected, actual);
    }
    
}
