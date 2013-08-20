package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsDate;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts.assertEqualsVersionedIdentifiableStatisticalResource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts.assertEqualsVersioningSiemacMetadata;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts.assertEqualsExternalItem;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts.assertEqualsExternalItemCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER_NAME;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.enume.domain.VersionPatternEnum;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCollectionUtils;
import org.siemac.metamac.statistical.resources.core.utils.TaskMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/include/task-mockito.xml",
        "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DatasetVersioningServiceTest extends StatisticalResourcesBaseTest {

    private static final String              TEMPORAL_CODE_COMPARE_FIELD = "identifier";

    @Autowired
    private DatasetVersionMockFactory        datasetVersionMockFactory;

    @Autowired
    @Qualifier("datasetLifecycleService")
    private LifecycleService<DatasetVersion> datasetVersionLifecycleService;
    
    @Autowired
    private DatasetService datasetService;

    @Autowired
    private TaskService                      taskService;

    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDatasetVersion() throws Exception {
        String datasetVersionUrn = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME).getSiemacMetadataStatisticalResource().getUrn();

        DatasetVersion newDatasetVersion = datasetVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), datasetVersionUrn, VersionTypeEnum.MINOR);
        DatasetVersion previousDatasetVersion = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), datasetVersionUrn);

        assertNotNull(newDatasetVersion);
        assertFalse(previousDatasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic().equals(newDatasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        checkNewDatasetVersionCreated(previousDatasetVersion, newDatasetVersion);
    }

    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDatasetVersionCheckUrnIsCorrectForMinorChange() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        
        // Necessary data for construct URN
        VersionTypeEnum versionType = VersionTypeEnum.MINOR;
        String datasetVersionCode = datasetVersion.getSiemacMetadataStatisticalResource().getCode();
        String[] maintainer = new String[]{datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};
        String versionBefore = datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic();

        // New dataset version
        DatasetVersion newDatasetVersion = datasetVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), datasetVersion.getSiemacMetadataStatisticalResource()
                .getUrn(), versionType);
        datasetVersion = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        
        // Expected URN
        String versionAfter = VersionUtil.createNextVersion(versionBefore, VersionPatternEnum.XXX_YYY, versionType);
        
        // Compare URNS
        String expectedUrn = GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetVersionUrn(maintainer, datasetVersionCode, versionAfter);
        assertEquals(expectedUrn, newDatasetVersion.getSiemacMetadataStatisticalResource().getUrn());
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDatasetVersionCheckUrnIsCorrectForMayorChange() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        
        // Necessary data for construct URN
        VersionTypeEnum versionType = VersionTypeEnum.MAJOR;
        String datasetVersionCode = datasetVersion.getSiemacMetadataStatisticalResource().getCode();
        String[] maintainer = new String[]{datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};
        String versionBefore = datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic();

        // New dataset version
        DatasetVersion newDatasetVersion = datasetVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), datasetVersion.getSiemacMetadataStatisticalResource()
                .getUrn(), versionType);
        datasetVersion = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        
        // Expected URN
        String versionAfter = VersionUtil.createNextVersion(versionBefore, VersionPatternEnum.XXX_YYY, versionType);
        
        // Compare URNS
        String expectedUrn = GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetVersionUrn(maintainer, datasetVersionCode, versionAfter);
        assertEquals(expectedUrn, newDatasetVersion.getSiemacMetadataStatisticalResource().getUrn());
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER_NAME)
    public void testVersioningDatasetVersionCheckUrnIsCorrectForANonRootAgency() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_69_PUBLISHED_NO_ROOT_MAINTAINER_NAME);
        
        // Necessary data for construct URN
        VersionTypeEnum versionType = VersionTypeEnum.MINOR;
        String datasetVersionCode = datasetVersion.getSiemacMetadataStatisticalResource().getCode();
        String[] maintainer = new String[]{datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};
        String versionBefore = datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic();

        // New dataset version
        DatasetVersion newDatasetVersion = datasetVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), datasetVersion.getSiemacMetadataStatisticalResource()
                .getUrn(), versionType);
        datasetVersion = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        
        // Expected URN
        String versionAfter = VersionUtil.createNextVersion(versionBefore, VersionPatternEnum.XXX_YYY, versionType);
        
        // Compare URNS
        String expectedUrn = GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetVersionUrn(maintainer, datasetVersionCode, versionAfter);
        assertEquals(expectedUrn, newDatasetVersion.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDatasetVersionImportationTaskInProgress() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        String urn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
        mockTaskInProgressForResource(urn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, urn));

        datasetVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), urn, VersionTypeEnum.MINOR);
    }
    
    // -------------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // -------------------------------------------------------------------------------------------
    
    private static void checkNewDatasetVersionCreated(DatasetVersion previous, DatasetVersion next) throws Exception {
        assertEqualsVersioningSiemacMetadata(previous.getSiemacMetadataStatisticalResource(), next.getSiemacMetadataStatisticalResource());
        checkInheritMetadata(previous, next);
        checkNotInheritMetadta(next);
        checkRelations(previous, next);
    }

    private static void checkRelations(DatasetVersion previous, DatasetVersion next) {
        // Dataset
        assertEquals(previous.getDataset().getId(), next.getDataset().getId());
        assertEquals(previous.getDataset().getIdentifiableStatisticalResource().getUrn(), next.getDataset().getIdentifiableStatisticalResource().getUrn());
        
        // Dataset repository Id
        assertNotSame(previous.getDatasetRepositoryId(), next.getDatasetRepositoryId());
        assertNotNull(next.getDatasetRepositoryId());
        
        // Datasources
        assertEqualsVersionedDatasourcesCollection(previous.getDatasources(), next.getDatasources());
    }
    

    private static void checkNotInheritMetadta(DatasetVersion next) {
        assertNotNull(next.getSiemacMetadataStatisticalResource().getUrn());
        assertEquals(next.getSiemacMetadataStatisticalResource().getUrn(), next.getDatasetRepositoryId());
        assertNull(next.getDateNextUpdate());
        assertNull(next.getBibliographicCitation());
        assertFalse(next.getUserModifiedDateNextUpdate());
    }

    private static void checkInheritMetadata(DatasetVersion previous, DatasetVersion next) throws Exception {
        assertEqualsExternalItemCollection(previous.getGeographicCoverage(), next.getGeographicCoverage());
        StatisticalResourcesCollectionUtils.equalsCollectionByField(previous.getTemporalCoverage(), next.getTemporalCoverage(), TEMPORAL_CODE_COMPARE_FIELD, TEMPORAL_CODE_COMPARE_FIELD);
        assertEqualsExternalItemCollection(previous.getMeasureCoverage(), next.getMeasureCoverage());
        assertEqualsExternalItemCollection(previous.getGeographicGranularities(), next.getGeographicGranularities());
        assertEqualsExternalItemCollection(previous.getTemporalGranularities(), next.getTemporalGranularities());

        assertEqualsDate(previous.getDateStart(), next.getDateStart());
        assertEqualsDate(previous.getDateEnd(), next.getDateEnd());

        assertEqualsExternalItemCollection(previous.getStatisticalUnit(), next.getStatisticalUnit());

        assertEqualsExternalItem(previous.getRelatedDsd(), next.getRelatedDsd());

        assertEquals(previous.getFormatExtentObservations(), next.getFormatExtentObservations());
        assertEquals(previous.getFormatExtentDimensions(), next.getFormatExtentDimensions());

        assertEqualsExternalItem(previous.getUpdateFrequency(), next.getUpdateFrequency());
        DatasetsAsserts.assertEqualsStatisticOfficiality(previous.getStatisticOfficiality(), next.getStatisticOfficiality());
    }

    public static void assertEqualsVersionedDatasourcesCollection(List<Datasource> expected, List<Datasource> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (Datasource expectedItem : expected) {
                boolean match = false;
                for (Datasource actualItem : actual) {
                    try {
                        assertEqualsVersionedDatasource(expectedItem, actualItem);
                        match = true;
                    } catch (AssertionError e) {
                        continue;
                    }

                }

                if (!match) {
                    fail("Found elements in expected collection, which are not contained in actual collection. Element: " + expectedItem.getId());
                }
            }
        } else {
            assertNull(actual);
        }
    }

    private static void assertEqualsVersionedDatasource(Datasource expected, Datasource actual) {
        assertEqualsVersionedIdentifiableStatisticalResource(expected.getIdentifiableStatisticalResource(), actual.getIdentifiableStatisticalResource());
        assertEquals(expected.getFilename(), actual.getFilename());
        assertNotSame(expected.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn(), actual.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn());
    }
    
    
    private void mockTaskInProgressForResource(String datasetVersionUrn, boolean status) throws MetamacException {
        TaskMockUtils.mockTaskInProgressForDatasetVersion(taskService, datasetVersionUrn, status);
    }

}
