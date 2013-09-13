package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts.assertEqualsRelatedResourceResultDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_13_WITH_DRAFT_AND_PUBLISHED__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_14_WITH_PUBLISHED_AND_NOT_VISIBLE_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_15_WITH_TWO_PUBLISHED_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_15_DRAFT_NOT_READY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_73_V01_FOR_DATASET_11_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_74_V02_FOR_DATASET_11_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_75_V01_FOR_DATASET_12_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_76_V02_FOR_DATASET_12_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_77_NO_PUB_REPLACES_DATASET_78_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_78_PUB_IS_REPLACED_BY_DATASET_77_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_79_NO_PUB_REPLACES_DATASET_80_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_80_NO_PUB_IS_REPLACED_BY_DATASET_79_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_81_PUB_NOT_VISIBLE_REPLACES_DATASET_82_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_82_PUB_IS_REPLACED_BY_DATASET_81_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_83_PUB_REPLACES_DATASET_84_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_84_PUB_IS_REPLACED_BY_DATASET_83_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_85_LAST_VERSION_NOT_PUBLISHED__IS_PART_OF_PUBLICATIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_43_DRAFT_HAS_PART_DATASET_VERSION_85_FIRST_LEVEL_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_44_DRAFT_HAS_PART_DATASET_VERSION_85_NO_FIRST_LEVEL_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_45_DRAFT_HAS_PART_DATASET_VERSION_85_MULTI_CUBE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_47_PUBLISHED_V02_FOR_PUBLICATION_07_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_48_PUBLISHED_V01_FOR_PUBLICATION_08_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_49_PUBLISHED_NOT_VISIBLE_V02_FOR_PUBLICATION_08_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_50_DRAFT_V01_FOR_PUBLICATION_09_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_52_PUBLISHED_V02_FOR_PUBLICATION_10_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_53_PUBLISHED_V01_FOR_PUBLICATION_11_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_54_PUBLISHED_NOT_VISIBLE_V02_FOR_PUBLICATION_11_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_55_DRAFT_V01_FOR_PUBLICATION_12_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_57_PUBLISHED_V02_FOR_PUBLICATION_13_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_58_PUBLISHED_V01_FOR_PUBLICATION_14_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_59_PUBLISHED_NOT_VISIBLE_V02_FOR_PUBLICATION_14_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_60_DRAFT_V01_FOR_PUBLICATION_15_NAME;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetVersionRepositoryTest extends StatisticalResourcesBaseTest implements DatasetVersionRepositoryTestBase {

    @Autowired
    private DatasetVersionRepository datasetVersionRepository;

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testRetrieveByUrn() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveByUrn(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_01_BASIC_NAME), actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, URN_NOT_EXISTS));

        datasetVersionRepository.retrieveByUrn(URN_NOT_EXISTS);
    }

    @Override
    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLastVersion() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME})
    public void testRetrieveLastVersionWithAllVersionsPublished() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME,
            DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME})
    public void testRetrieveLastVersionWithLatestVersionNoVisible() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_26_V2_PUBLISHED_NO_VISIBLE_FOR_DATASET_06_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLastPublishedVersion() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastPublishedVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveLastPublishedVersionWithAllVersionsPublished() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_24_V3_PUBLISHED_FOR_DATASET_05_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastPublishedVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_VERSION_15_DRAFT_NOT_READY_NAME})
    public void testRetrieveLastPublishedVersionWithoutVersionsPublished() throws Exception {
        String datasetUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME).getDataset().getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = null;
        DatasetVersion actual = datasetVersionRepository.retrieveLastPublishedVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_VERSION_15_DRAFT_NOT_READY_NAME,
            DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME})
    public void testRetrieveLastPublishedVersionWithLatestVersionNoVisible() throws Exception {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME).getIdentifiableStatisticalResource().getUrn();
        DatasetVersion expected = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_25_V1_PUBLISHED_FOR_DATASET_06_NAME);
        DatasetVersion actual = datasetVersionRepository.retrieveLastPublishedVersion(datasetUrn);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testIsLastVersion() throws Exception {
        DatasetVersion notLastDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        DatasetVersion lastDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME);

        assertTrue(datasetVersionRepository.isLastVersion(lastDatasetVersion.getSiemacMetadataStatisticalResource().getUrn()));
        assertFalse(datasetVersionRepository.isLastVersion(notLastDatasetVersion.getSiemacMetadataStatisticalResource().getUrn()));
    }

    @Override
    @Test
    @MetamacMock({DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME, DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveByVersion() throws Exception {
        DatasetVersion actual = datasetVersionRepository.retrieveByVersion(datasetMockFactory.retrieveMock(DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME).getId(), datasetVersionMockFactory
                .retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEqualsDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION_NAME), actual);
    }

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME)
    public void testRetrieveDimensionsIds() throws Exception {
        DatasetVersion notLastDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME);

        List<String> dimensionIds = datasetVersionRepository.retrieveDimensionsIds(notLastDatasetVersion);
        assertEquals(dimensionIds, Arrays.asList("dim1", "dim2", "dim3"));
    }

    @Override
    @Test
    @MetamacMock(DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME)
    public void testRetrieveIsRequiredBy() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME);

        DatasetVersion firstDatasetVersion = dataset.getVersions().get(0);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredBy(firstDatasetVersion);
            Assert.assertNotNull(resources);
            Assert.assertEquals(2, resources.size());
        }

        DatasetVersion lastDatasetVersion = dataset.getVersions().get(1);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredBy(lastDatasetVersion);
            Assert.assertNotNull(resources);
            Assert.assertEquals(1, resources.size());
        }
    }

    @Override
    @Test
    @MetamacMock(DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME)
    public void testRetrieveIsRequiredByOnlyLastPublished() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME);

        DatasetVersion firstDatasetVersion = dataset.getVersions().get(0);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredByOnlyLastPublished(firstDatasetVersion);
            Assert.assertNotNull(resources);
            Assert.assertEquals(2, resources.size());
        }

        DatasetVersion lastDatasetVersion = dataset.getVersions().get(1);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredByOnlyLastPublished(lastDatasetVersion);
            Assert.assertNotNull(resources);
            Assert.assertEquals(1, resources.size());
        }
    }

    @Test
    @MetamacMock(DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE_NAME)
    public void testRetrieveIsRequiredByQueriesNotVisible() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE_NAME);

        DatasetVersion firstDatasetVersion = dataset.getVersions().get(0);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredByOnlyLastPublished(firstDatasetVersion);
            Assert.assertNotNull(resources);
            assertEquals(3, resources.size());
        }

        DatasetVersion lastDatasetVersion = dataset.getVersions().get(1);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredByOnlyLastPublished(lastDatasetVersion);
            Assert.assertNotNull(resources);
            Assert.assertEquals(0, resources.size());
        }
    }

    @Test
    @MetamacMock(DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE_NAME)
    public void testRetrieveIsRequiredByOnlyLastPublishedQueriesNotVisible() throws Exception {
        DatasetVersion firstDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_73_V01_FOR_DATASET_11_NAME);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredByOnlyLastPublished(firstDatasetVersion);
            Assert.assertNotNull(resources);
            Assert.assertEquals(3, resources.size());
        }
    }

    @Test
    @MetamacMock(DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT_NAME)
    public void testRetrieveIsRequiredByOnlyLastPublishedQueriesDraft() throws Exception {
        DatasetVersion firstDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_75_V01_FOR_DATASET_12_NAME);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredByOnlyLastPublished(firstDatasetVersion);
            Assert.assertNotNull(resources);
            Assert.assertEquals(0, resources.size());
        }

        DatasetVersion lastDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_76_V02_FOR_DATASET_12_NAME);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredByOnlyLastPublished(lastDatasetVersion);
            Assert.assertNotNull(resources);
            Assert.assertEquals(0, resources.size());
        }
    }

    @Test
    @MetamacMock(DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT_NAME)
    public void testRetrieveIsRequiredByQueriesDraft() throws Exception {
        DatasetVersion firstDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_75_V01_FOR_DATASET_12_NAME);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredBy(firstDatasetVersion);
            Assert.assertNotNull(resources);
            Assert.assertEquals(1, resources.size());
        }

        DatasetVersion lastDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_76_V02_FOR_DATASET_12_NAME);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredBy(lastDatasetVersion);
            Assert.assertNotNull(resources);
            Assert.assertEquals(1, resources.size());
        }
    }

    @Test
    @MetamacMock(DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT_NAME)
    public void testRetrieveIsRequiredByQueriesResultMapping() throws Exception {
        DatasetVersion firstDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_75_V01_FOR_DATASET_12_NAME);
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsRequiredBy(firstDatasetVersion);
            assertNotNull(resources);
            assertEquals(1, resources.size());
            RelatedResourceResult resource = resources.get(0);
            assertEquals("Q01", resource.getCode());
            assertEquals(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(new String[]{"agency01"}, "Q01", "001.000"), resource.getUrn());
            assertEquals("OPER01", resource.getStatisticalOperationCode());
            assertEquals(GeneratorUrnUtils.generateSiemacStatisticalOperationUrn("OPER01"), resource.getStatisticalOperationUrn());
            assertNotNull(resource.getTitle());
            assertEquals("title-Q01 en Espanol", resource.getTitle().get("es"));
            assertEquals("title-Q01 in English", resource.getTitle().get("en"));
            assertEquals(TypeRelatedResourceEnum.QUERY_VERSION, resource.getType());
            assertEquals("agency01", resource.getMaintainerNestedCode());
            assertEquals("001.000", resource.getVersion());
        }
    }

    @Override
    @Test
    @MetamacMock(DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT_NAME)
    public void testRetrieveIsReplacedByVersionOnlyLastPublished() throws Exception {
        DatasetVersion firstDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_75_V01_FOR_DATASET_12_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedByVersionOnlyLastPublished(firstDatasetVersion);
        assertNull(resource);
    }

    @Override
    @Test
    @MetamacMock(DATASET_12_WITH_TWO_VERSIONS_WITH_QUERIES_IN_DRAFT_NAME)
    public void testRetrieveIsReplacedByVersion() throws Exception {
        DatasetVersion firstDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_75_V01_FOR_DATASET_12_NAME);
        DatasetVersion secondDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_76_V02_FOR_DATASET_12_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedByVersion(firstDatasetVersion);
        assertNotNull(resource);
        CommonAsserts.assertEqualsRelatedResourceResultDatasetVersion(secondDatasetVersion, resource);
    }

    @Test
    @MetamacMock(DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE_NAME)
    public void testRetrieveIsReplacedByVersionOnlyLastPublishedSecondVersionNotVisible() throws Exception {
        DatasetVersion firstDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_73_V01_FOR_DATASET_11_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedByVersionOnlyLastPublished(firstDatasetVersion);
        assertNull(resource);
    }

    @Test
    @MetamacMock(DATASET_11_WITH_TWO_VERSIONS_WITH_3_QUERIES__FIRST_VERSION_IS_PUBLISHED__SECOND_VERSION_IS_NOT_VISIBLE_ALL_QUERIES_COMPATIBLE_NAME)
    public void testRetrieveIsReplacedBySecondVersionNotVisible() throws Exception {
        DatasetVersion firstDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_73_V01_FOR_DATASET_11_NAME);
        DatasetVersion secondDatasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_74_V02_FOR_DATASET_11_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedByVersion(firstDatasetVersion);
        assertNotNull(resource);
        CommonAsserts.assertEqualsRelatedResourceResultDatasetVersion(secondDatasetVersion, resource);
    }

    @Test
    @MetamacMock(DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME)
    public void testRetrieveIsReplacedByVersionOnlyLastPublishedSecondVersionPublishedAndVisible() throws Exception {
        DatasetVersion firstDatasetVersion = datasetMockFactory
                .retrieveMock(DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME).getVersions().get(0);
        DatasetVersion secondDatasetVersion = datasetMockFactory
                .retrieveMock(DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME).getVersions().get(1);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedByVersionOnlyLastPublished(firstDatasetVersion);
        assertNotNull(resource);
        CommonAsserts.assertEqualsRelatedResourceResultDatasetVersion(secondDatasetVersion, resource);
    }

    @Test
    @MetamacMock(DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME)
    public void testRetrieveIsReplacedBySecondVersionPublishedAndVisible() throws Exception {
        DatasetVersion firstDatasetVersion = datasetMockFactory
                .retrieveMock(DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME).getVersions().get(0);
        DatasetVersion secondDatasetVersion = datasetMockFactory
                .retrieveMock(DATASET_10_WITH_TWO_VERSIONS_WITH_QUERIES__FIRST_VERSION_IS_REQUIRED_BY_TWO_QUERY_VERSIONS__SECOND_IS_REQUIRED_BY_ONE_QUERY_VERSION_NAME).getVersions().get(1);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedByVersion(firstDatasetVersion);
        assertNotNull(resource);
        CommonAsserts.assertEqualsRelatedResourceResultDatasetVersion(secondDatasetVersion, resource);
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_77_NO_PUB_REPLACES_DATASET_78_NAME})
    public void testRetrieveIsReplacedBy() throws Exception {
        DatasetVersion notPublishedDataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_77_NO_PUB_REPLACES_DATASET_78_NAME);
        DatasetVersion publishedDatasetReplaced = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_78_PUB_IS_REPLACED_BY_DATASET_77_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedBy(publishedDatasetReplaced);
        assertNotNull(resource);
        CommonAsserts.assertEqualsRelatedResourceResultDatasetVersion(notPublishedDataset, resource);
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_77_NO_PUB_REPLACES_DATASET_78_NAME})
    public void testRetrieveIsReplacedByOnlyLastPublished() throws Exception {
        DatasetVersion publishedDatasetReplaced = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_78_PUB_IS_REPLACED_BY_DATASET_77_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedByOnlyLastPublished(publishedDatasetReplaced);
        assertNull(resource);
    }

    @Test
    @MetamacMock({DATASET_VERSION_79_NO_PUB_REPLACES_DATASET_80_NAME})
    public void testRetrieveResourceThatReplacesDatasetBothNotPublished() throws Exception {
        DatasetVersion notPublishedDataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_79_NO_PUB_REPLACES_DATASET_80_NAME);
        DatasetVersion notPublishedDatasetReplaced = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_80_NO_PUB_IS_REPLACED_BY_DATASET_79_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedBy(notPublishedDatasetReplaced);
        assertNotNull(resource);
        CommonAsserts.assertEqualsRelatedResourceResultDatasetVersion(notPublishedDataset, resource);
    }

    @Test
    @MetamacMock({DATASET_VERSION_79_NO_PUB_REPLACES_DATASET_80_NAME})
    public void testRetrieveLastPublishedVersionResourceThatReplacesDatasetBothNotPublished() throws Exception {
        DatasetVersion notPublishedDatasetReplaced = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_80_NO_PUB_IS_REPLACED_BY_DATASET_79_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedByOnlyLastPublished(notPublishedDatasetReplaced);
        assertNull(resource);
    }

    @Test
    @MetamacMock({DATASET_VERSION_81_PUB_NOT_VISIBLE_REPLACES_DATASET_82_NAME})
    public void testRetrieveResourceThatReplacesDatasetWhichReplacesIsNotVisible() throws Exception {
        DatasetVersion notVisibleDataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_81_PUB_NOT_VISIBLE_REPLACES_DATASET_82_NAME);
        DatasetVersion publishedDatasetReplaced = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_82_PUB_IS_REPLACED_BY_DATASET_81_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedBy(publishedDatasetReplaced);
        assertNotNull(resource);
        CommonAsserts.assertEqualsRelatedResourceResultDatasetVersion(notVisibleDataset, resource);
    }

    @Test
    @MetamacMock({DATASET_VERSION_81_PUB_NOT_VISIBLE_REPLACES_DATASET_82_NAME})
    public void testRetrieveLastPublishedVersionResourceThatReplacesDatasetWhichReplacesIsNotVisible() throws Exception {
        DatasetVersion publishedDatasetReplaced = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_82_PUB_IS_REPLACED_BY_DATASET_81_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedByOnlyLastPublished(publishedDatasetReplaced);
        assertNull(resource);
    }

    @Test
    @MetamacMock({DATASET_VERSION_83_PUB_REPLACES_DATASET_84_NAME})
    public void testRetrieveResourceThatReplacesDatasetBothPublished() throws Exception {
        DatasetVersion publishedDataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_83_PUB_REPLACES_DATASET_84_NAME);
        DatasetVersion publishedDatasetReplaced = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_84_PUB_IS_REPLACED_BY_DATASET_83_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedBy(publishedDatasetReplaced);
        assertNotNull(resource);
        CommonAsserts.assertEqualsRelatedResourceResultDatasetVersion(publishedDataset, resource);
    }

    @Test
    @MetamacMock({DATASET_VERSION_83_PUB_REPLACES_DATASET_84_NAME})
    public void testRetrieveLastPublishedVersionResourceThatReplacesDatasetBothPublished() throws Exception {
        DatasetVersion publishedDataset = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_83_PUB_REPLACES_DATASET_84_NAME);
        DatasetVersion publishedDatasetReplaced = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_84_PUB_IS_REPLACED_BY_DATASET_83_NAME);

        RelatedResourceResult resource = datasetVersionRepository.retrieveIsReplacedByOnlyLastPublished(publishedDatasetReplaced);
        assertNotNull(resource);
        assertEqualsRelatedResourceResultDatasetVersion(publishedDataset, resource);
    }

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_85_LAST_VERSION_NOT_PUBLISHED__IS_PART_OF_PUBLICATIONS_NAME, PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME})
    public void testRetrieveIsPartOf() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_85_LAST_VERSION_NOT_PUBLISHED__IS_PART_OF_PUBLICATIONS_NAME);
        PublicationVersion publicationDraftFirstLevel = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_43_DRAFT_HAS_PART_DATASET_VERSION_85_FIRST_LEVEL_NAME);
        PublicationVersion publicationDraftNoFirstLevel = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_44_DRAFT_HAS_PART_DATASET_VERSION_85_NO_FIRST_LEVEL_NAME);
        PublicationVersion publicationDraftMultiCube = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_45_DRAFT_HAS_PART_DATASET_VERSION_85_MULTI_CUBE_NAME);

        List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOf(datasetVersion);
        CommonAsserts.assertEqualsRelatedResourceResultCollectionToPublicationVersionCollection(Arrays.asList(publicationDraftFirstLevel, publicationDraftNoFirstLevel, publicationDraftMultiCube),
                resources);
    }

    @Test
    @MetamacMock({DATASET_13_WITH_DRAFT_AND_PUBLISHED__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME, PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME})
    public void testRetrieveIsPartOfDraftAndPublishedVersion() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_13_WITH_DRAFT_AND_PUBLISHED__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME);
        DatasetVersion publishedVersion = dataset.getVersions().get(0);
        DatasetVersion draftVersion = dataset.getVersions().get(1);

        // last version
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOf(draftVersion);

            PublicationVersion publication01 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_47_PUBLISHED_V02_FOR_PUBLICATION_07_NAME);
            PublicationVersion publication02 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_49_PUBLISHED_NOT_VISIBLE_V02_FOR_PUBLICATION_08_NAME);
            PublicationVersion publication03 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_50_DRAFT_V01_FOR_PUBLICATION_09_NAME);

            CommonAsserts.assertEqualsRelatedResourceResultCollectionToPublicationVersionCollection(Arrays.asList(publication01, publication02, publication03), resources);
        }
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOf(publishedVersion);
            assertTrue(resources.isEmpty()); // is not last version, no publication links to it
        }
    }

    @Test
    @MetamacMock({DATASET_14_WITH_PUBLISHED_AND_NOT_VISIBLE_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME, PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME})
    public void testRetrieveIsPartOfPublishedVersionAndNotVisibleVersion() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_14_WITH_PUBLISHED_AND_NOT_VISIBLE_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME);
        DatasetVersion publishedVersion = dataset.getVersions().get(0);
        DatasetVersion publishedNotVisibleVersion = dataset.getVersions().get(1);

        // last version
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOf(publishedNotVisibleVersion);

            PublicationVersion publication01 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_52_PUBLISHED_V02_FOR_PUBLICATION_10_NAME);
            PublicationVersion publication02 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_54_PUBLISHED_NOT_VISIBLE_V02_FOR_PUBLICATION_11_NAME);
            PublicationVersion publication03 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_55_DRAFT_V01_FOR_PUBLICATION_12_NAME);

            CommonAsserts.assertEqualsRelatedResourceResultCollectionToPublicationVersionCollection(Arrays.asList(publication01, publication02, publication03), resources);
        }
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOf(publishedVersion);
            assertTrue(resources.isEmpty()); // is not last version, no publication links to it
        }
    }

    @Test
    @MetamacMock({DATASET_15_WITH_TWO_PUBLISHED_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME, PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME})
    public void testRetrieveIsPartOfTwoPublishedVersions() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_15_WITH_TWO_PUBLISHED_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME);
        DatasetVersion publishedVersion = dataset.getVersions().get(0);
        DatasetVersion publishedLastVersion = dataset.getVersions().get(1);

        // last version
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOf(publishedLastVersion);

            PublicationVersion publication01 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_57_PUBLISHED_V02_FOR_PUBLICATION_13_NAME);
            PublicationVersion publication02 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_59_PUBLISHED_NOT_VISIBLE_V02_FOR_PUBLICATION_14_NAME);
            PublicationVersion publication03 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_60_DRAFT_V01_FOR_PUBLICATION_15_NAME);

            CommonAsserts.assertEqualsRelatedResourceResultCollectionToPublicationVersionCollection(Arrays.asList(publication01, publication02, publication03), resources);
        }
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOf(publishedVersion);
            assertTrue(resources.isEmpty()); // is not last version, no publication links to it
        }
    }

    @Override
    @Test
    @MetamacMock({DATASET_13_WITH_DRAFT_AND_PUBLISHED__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME, PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME})
    public void testRetrieveIsPartOfOnlyLastPublished() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_13_WITH_DRAFT_AND_PUBLISHED__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME);
        DatasetVersion publishedVersion = dataset.getVersions().get(0);
        DatasetVersion draftVersion = dataset.getVersions().get(1);

        // last version
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOfOnlyLastPublished(draftVersion);
            assertTrue(resources.isEmpty()); // is not last version, no publication links to it
        }

        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOfOnlyLastPublished(publishedVersion);

            PublicationVersion publication01 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_47_PUBLISHED_V02_FOR_PUBLICATION_07_NAME);
            PublicationVersion publication02 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_48_PUBLISHED_V01_FOR_PUBLICATION_08_NAME);

            CommonAsserts.assertEqualsRelatedResourceResultCollectionToPublicationVersionCollection(Arrays.asList(publication01, publication02), resources);
        }

    }

    @Test
    @MetamacMock({DATASET_14_WITH_PUBLISHED_AND_NOT_VISIBLE_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME, PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME})
    public void testRetrieveIsPartOfOnlyLastPublishedPublishedVersionAndNotVisibleVersion() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_14_WITH_PUBLISHED_AND_NOT_VISIBLE_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME);
        DatasetVersion publishedVersion = dataset.getVersions().get(0);
        DatasetVersion notVisibleVersion = dataset.getVersions().get(1);

        // last version
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOfOnlyLastPublished(notVisibleVersion);
            assertTrue(resources.isEmpty()); // is not last version, no publication links to it
        }

        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOfOnlyLastPublished(publishedVersion);

            PublicationVersion publication01 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_52_PUBLISHED_V02_FOR_PUBLICATION_10_NAME);
            PublicationVersion publication02 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_53_PUBLISHED_V01_FOR_PUBLICATION_11_NAME);

            CommonAsserts.assertEqualsRelatedResourceResultCollectionToPublicationVersionCollection(Arrays.asList(publication01, publication02), resources);
        }

    }

    @Test
    @MetamacMock({DATASET_15_WITH_TWO_PUBLISHED_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME, PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME})
    public void testRetrieveIsPartOfOnlyLastPublishedTwoPublishedVersions() throws Exception {
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_15_WITH_TWO_PUBLISHED_VERSIONS__IS_PART_OF_PUBLICATIONS_DIFFERENT_STATUS_NAME);
        DatasetVersion publishedVersion = dataset.getVersions().get(0);
        DatasetVersion publishedLastVersion = dataset.getVersions().get(1);

        // last version
        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOfOnlyLastPublished(publishedLastVersion);

            PublicationVersion publication01 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_57_PUBLISHED_V02_FOR_PUBLICATION_13_NAME);
            PublicationVersion publication02 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_58_PUBLISHED_V01_FOR_PUBLICATION_14_NAME);
            CommonAsserts.assertEqualsRelatedResourceResultCollectionToPublicationVersionCollection(Arrays.asList(publication01, publication02), resources);
        }

        {
            List<RelatedResourceResult> resources = datasetVersionRepository.retrieveIsPartOfOnlyLastPublished(publishedVersion);
            assertTrue(resources.isEmpty()); // all links point to last version
        }

    }

}
