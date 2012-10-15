package org.siemac.metamac.statistical.resources.core.tests.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.dto.QueryDto;
import org.siemac.metamac.statistical.resources.core.mapper.Do2DtoMapper;
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
public class StatisticalResourceDo2DtoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private Do2DtoMapper do2DtoMapper;
    
    @Test
    public void testQueryDo2Dto() {
        Query entity = StatisticalResourcesDoMocks.mockQuery();
        QueryDto dto = do2DtoMapper.queryDoToDto(entity);
        StatisticalResourcesAsserts.assertEqualsQuery(entity, dto);
    }

}
