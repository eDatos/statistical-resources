package org.siemac.metamac.core.common.ent.repositoryimpl;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class InternationalStringRepositoryTest extends StatisticalResourcesBaseTest implements InternationalStringRepositoryTestBase {
    
    @Autowired
    protected InternationalStringRepository internationalStringRepository;

    @Override
    @Test(expected=UnsupportedOperationException.class)
    public void testDeleteInternationalStringsEfficiently() throws Exception {
        internationalStringRepository.deleteInternationalStringsEfficiently(Arrays.asList(1L));
    }
}
