package org.siemac.metamac.statistical.resources.core.tests.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.dto.QueryDto;
import org.siemac.metamac.statistical.resources.core.mapper.Dto2DoMapper;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesAsserts;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDtoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class StatisticalResourceDto2DoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private Dto2DoMapper dto2DoMapper;

    @Test
    public void testQueryDtoToDo() throws MetamacException {
        QueryDto dto = StatisticalResourcesDtoMocks.mockQueryDto();
        Query entity = dto2DoMapper.queryDtoToDo(dto);
        StatisticalResourcesAsserts.assertEqualsQuery(entity, dto);
    }
    
}
