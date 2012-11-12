package org.siemac.metamac.statistical.resources.core.base.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.mocks.QueryMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationVersionMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class IdentifiableStatisticalResourceRepositoryTest extends StatisticalResourcesBaseTest implements IdentifiableStatisticalResourceRepositoryTestBase {

    @Autowired
    protected IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;

    @Test
    @MetamacMock({QUERY_01_BASIC_NAME, QUERY_02_BASIC_ORDERED_01_NAME, DATASET_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    public void testRetrieveByUrn() throws Exception {
        {
            IdentifiableStatisticalResource actual = identifiableStatisticalResourceRepository.retrieveByUrn(QUERY_01_BASIC.getNameableStatisticalResource().getUrn());
            assertEqualsIdentifiableStatisticalResource(QUERY_01_BASIC.getNameableStatisticalResource(), actual);
        }

        {
            IdentifiableStatisticalResource actual = identifiableStatisticalResourceRepository.retrieveByUrn(DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn());
            assertEqualsIdentifiableStatisticalResource(DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource(), actual);
        }
        
        {
            IdentifiableStatisticalResource actual = identifiableStatisticalResourceRepository.retrieveByUrn(PUBLICATION_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn());
            assertEqualsIdentifiableStatisticalResource(PUBLICATION_VERSION_01_BASIC.getSiemacMetadataStatisticalResource(), actual);
        }

    }

    @Test
    public void testRetrieveByUrnNotFound() {
        try {
            identifiableStatisticalResourceRepository.retrieveByUrn(URN_NOT_EXISTS);
            fail("not found");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.IDENTIFIABLE_STATISTICAL_RESOURCE_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }
}
