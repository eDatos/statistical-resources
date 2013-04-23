package org.siemac.metamac.core.common.ent.repositoryimpl;

import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import static org.junit.Assert.fail;
import org.junit.Test;

import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class InternationalStringRepositoryTest extends AbstractDbUnitJpaTests
    implements InternationalStringRepositoryTestBase {
    @Autowired
    protected InternationalStringRepository internationalStringRepository;

    @Override
    @Test
    public void testDeleteInternationalStringsEfficiently() throws Exception {
        // TODO Auto-generated method stub
        fail("testDeleteInternationalStringsEfficiently not implemented");
    }
}
