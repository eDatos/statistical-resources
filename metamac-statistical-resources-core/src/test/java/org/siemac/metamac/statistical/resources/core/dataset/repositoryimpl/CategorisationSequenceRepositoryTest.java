package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CategorisationMockFactory.CATEGORISATION_MAINTAINER_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CategorisationMockFactory.CATEGORISATION_SEQUENCE_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CategorisationSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CategorisationSequenceRepositoryTest extends StatisticalResourcesBaseTest implements CategorisationSequenceRepositoryTestBase {

    @Autowired
    protected CategorisationSequenceRepository categorisationSequenceRepository;

    @Override
    @Test
    @MetamacMock({CATEGORISATION_SEQUENCE_NAME, CATEGORISATION_MAINTAINER_NAME})
    public void testGenerateNextSequence() throws Exception {
        String maintainerUrn = "urn:sdmx:org.sdmx.infomodel.base.Agency=MaintainerMock:AgencySchemeMock(1.0).agency01";
        assertEquals(Long.valueOf(101), categorisationSequenceRepository.generateNextSequence(maintainerUrn));
        assertEquals(Long.valueOf(102), categorisationSequenceRepository.generateNextSequence(maintainerUrn));
        assertEquals(Long.valueOf(103), categorisationSequenceRepository.generateNextSequence(maintainerUrn));

        assertEquals(Long.valueOf(1), categorisationSequenceRepository.generateNextSequence("newMaintainer"));
    }
}
