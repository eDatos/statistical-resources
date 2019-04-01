package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.MultidatasetsAsserts.assertRelaxedEqualsMultidatasetCube;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_22_SIMPLE_LINKED_TO_PUB_VERSION_17_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.MultidatasetCubeMockFactory.MULTIDATASET_CUBE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.MultidatasetCubeMockFactory.MULTIDATASET_CUBE_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.MultidatasetCubeMockFactory.MULTIDATASET_CUBE_03_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.MultidatasetMockFactory.MULTIDATASET_04_STRUCTURED_WITH_2_MULTIDATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.MultidatasetVersionMockFactory.MULTIDATASET_VERSION_17_WITH_STRUCTURE_FOR_MULTIDATASET_VERSION_04_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.MultidatasetVersionMockFactory.MULTIDATASET_VERSION_18_WITH_STRUCTURE_FOR_MULTIDATASET_VERSION_04_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_09_SINGLE_VERSION_USED_IN_PUB_VERSION_17_NAME;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCubeRepository;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticalResourcesMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class MultidatasetCubeRepositoryTest extends StatisticalResourcesBaseTest implements MultidatasetCubeRepositoryTestBase {

    @Autowired
    protected MultidatasetCubeRepository multidatasetCubeRepository;

    @Override
    @Test
    @MetamacMock({MULTIDATASET_CUBE_01_BASIC_NAME, MULTIDATASET_CUBE_02_BASIC_NAME, MULTIDATASET_CUBE_03_BASIC_NAME, MULTIDATASET_04_STRUCTURED_WITH_2_MULTIDATASET_VERSIONS_NAME})
    public void testRetrieveCubeByUrn() throws Exception {
        MultidatasetCube expected = multidatasetCubeMockFactory.retrieveMock(MULTIDATASET_CUBE_01_BASIC_NAME);
        MultidatasetCube actual = multidatasetCubeRepository.retrieveCubeByUrn(expected.getNameableStatisticalResource().getUrn());
        assertRelaxedEqualsMultidatasetCube(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({MULTIDATASET_04_STRUCTURED_WITH_2_MULTIDATASET_VERSIONS_NAME})
    public void testExistAnyCubeInMultidataset() throws Exception {
        boolean result = multidatasetCubeRepository.existAnyCubeInMultidataset(
                multidatasetMockFactory.retrieveMock(MULTIDATASET_04_STRUCTURED_WITH_2_MULTIDATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getCode(),
                StatisticalResourcesMockFactory.INIT_VERSION);
        assertTrue(result);
    }

    @Test
    @MetamacMock({MULTIDATASET_04_STRUCTURED_WITH_2_MULTIDATASET_VERSIONS_NAME})
    public void testExistAnyCubeInMultidatasetReturnFalse() throws Exception {
        boolean result = multidatasetCubeRepository.existAnyCubeInMultidataset(
                multidatasetMockFactory.retrieveMock(MULTIDATASET_04_STRUCTURED_WITH_2_MULTIDATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getCode(),
                StatisticalResourcesMockFactory.SECOND_VERSION);
        assertFalse(result);
    }

    @Override
    @Test
    @MetamacMock({MULTIDATASET_VERSION_17_WITH_STRUCTURE_FOR_MULTIDATASET_VERSION_04_NAME})
    public void testFindDatasetsLinkedWithMultidatasetVersion() throws Exception {
        List<String> result = multidatasetCubeRepository.findDatasetsLinkedWithMultidatasetVersion(
                multidatasetVersionMockFactory.retrieveMock(MULTIDATASET_VERSION_17_WITH_STRUCTURE_FOR_MULTIDATASET_VERSION_04_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(2, result.size());
        assertTrue(result.contains(datasetMockFactory.retrieveMock(DATASET_22_SIMPLE_LINKED_TO_PUB_VERSION_17_NAME).getIdentifiableStatisticalResource().getUrn()));
    }

    @Test
    @MetamacMock({MULTIDATASET_VERSION_18_WITH_STRUCTURE_FOR_MULTIDATASET_VERSION_04_AND_LAST_VERSION_NAME})
    public void testFindDatasetsLinkedWithMultidatasetVersionWithoutDatasets() throws Exception {
        List<String> result = multidatasetCubeRepository.findDatasetsLinkedWithMultidatasetVersion(
                multidatasetVersionMockFactory.retrieveMock(MULTIDATASET_VERSION_18_WITH_STRUCTURE_FOR_MULTIDATASET_VERSION_04_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(0, result.size());
    }

    @Override
    @Test
    @MetamacMock({MULTIDATASET_VERSION_17_WITH_STRUCTURE_FOR_MULTIDATASET_VERSION_04_NAME})
    public void testFindQueriesLinkedWithMultidatasetVersion() throws Exception {
        List<String> result = multidatasetCubeRepository.findQueriesLinkedWithMultidatasetVersion(
                multidatasetVersionMockFactory.retrieveMock(MULTIDATASET_VERSION_17_WITH_STRUCTURE_FOR_MULTIDATASET_VERSION_04_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(1, result.size());
        assertTrue(result.contains(queryMockFactory.retrieveMock(QUERY_09_SINGLE_VERSION_USED_IN_PUB_VERSION_17_NAME).getIdentifiableStatisticalResource().getUrn()));
    }

    @Test
    @MetamacMock({MULTIDATASET_VERSION_18_WITH_STRUCTURE_FOR_MULTIDATASET_VERSION_04_AND_LAST_VERSION_NAME})
    public void testFindQueriesLinkedWithMultidatasetVersionWithoutQueries() throws Exception {
        List<String> result = multidatasetCubeRepository.findQueriesLinkedWithMultidatasetVersion(
                multidatasetVersionMockFactory.retrieveMock(MULTIDATASET_VERSION_18_WITH_STRUCTURE_FOR_MULTIDATASET_VERSION_04_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(0, result.size());
    }
}
