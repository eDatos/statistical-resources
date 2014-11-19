package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDimensionRepresentationMapping;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DimensionRepresentationMappingMockFactory.DIMENSION_REPRESENTATION_MAPPING_01_DATASOURCE_01_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DimensionRepresentationMappingRepositoryTest extends StatisticalResourcesBaseTest implements DimensionRepresentationMappingRepositoryTestBase {

    @Autowired
    protected DimensionRepresentationMappingRepository dimensionRepresentationMappingRepository;

    @Override
    @Test
    @MetamacMock({DIMENSION_REPRESENTATION_MAPPING_01_DATASOURCE_01_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testFindByDatasetAndDatasourceFilename() throws MetamacException {
        DimensionRepresentationMapping expected = dimensionRepresentationMappingMockFactory.retrieveMock(DIMENSION_REPRESENTATION_MAPPING_01_DATASOURCE_01_NAME);
        DimensionRepresentationMapping actual = dimensionRepresentationMappingRepository.findByDatasetAndDatasourceFilename(expected.getDatasetVersion().getSiemacMetadataStatisticalResource()
                .getUrn(), expected.getDatasourceFilename());
        assertEqualsDimensionRepresentationMapping(expected, actual);
    }

    @Override
    public void testFindByDatasetAndDatasourceFilenames() throws Exception {
        // TODO METAMAC-1979
    }
}
