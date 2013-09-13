package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_34_FOR_IMPORT_IN_OPERATION_0001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_35_FOR_IMPORT_IN_OPERATION_0001_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_36_FOR_IMPORT_IN_OPERATION_0002_NAME;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.MetamacCollectionUtils;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.validators.DatasetServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.DatasetServiceImpl;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.utils.TaskInfoPredicateByDatasetId;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;

public class DatasetServiceImportationTest extends StatisticalResourcesBaseTest {

    private static final String               DATASET_MOCK_URN = "MOCK_URN";

    @InjectMocks
    private DatasetService                    datasetService   = new DatasetServiceImpl();

    @SuppressWarnings("unused")
    @Mock
    private DatasetServiceInvocationValidator datasetServiceInvocationValidator;

    @Mock
    private DatasetVersionRepository          datasetVersionRepository;

    @Mock
    private DatasetRepository                 datasetRepository;

    @Mock
    private TaskService                       taskService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testImportDatasourcesInDatasetVersion() throws Exception {
        String filename = "prueba.px";
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.eq(datasetVersionUrn))).thenReturn(datasetVersion);
        Mockito.when(datasetRepository.findDatasetUrnLinkedToDatasourceFile(filename)).thenReturn(null);

        List<URL> urls = Arrays.asList(buildFileUrl(filename));
        datasetService.importDatasourcesInDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersionUrn, urls);

        ArgumentCaptor<TaskInfoDataset> argument = ArgumentCaptor.forClass(TaskInfoDataset.class);
        verify(taskService).planifyImportationDataset(any(ServiceContext.class), argument.capture());

        assertTaskInfoFile(datasetVersion, 1, argument.getValue());
        assertTaskFile(filename, DatasetFileFormatEnum.PX, argument.getValue().getFiles().get(0));
    }

    @Test
    public void testImportDatasourcesInDatasetVersionInvalidFile() throws Exception {
        String filename = "prueba.px";
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.eq(datasetVersionUrn))).thenReturn(datasetVersion);
        Mockito.when(datasetRepository.findDatasetUrnLinkedToDatasourceFile(filename)).thenReturn(DATASET_MOCK_URN);

        expectedMetamacException(new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.INVALID_FILE_FOR_DATASET_VERSION, "prueba.px", datasetVersionUrn))));

        List<URL> urls = Arrays.asList(buildFileUrl(filename));
        datasetService.importDatasourcesInDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersionUrn, urls);
    }

    @Test
    public void testImportDatasourcesInStatisticalOperation() throws Exception {
        String filename = "prueba.px";
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
        String datasetUrn = datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn();
        String statisticalOperationUrn = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn();

        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.eq(datasetVersionUrn))).thenReturn(datasetVersion);
        Mockito.when(datasetRepository.findDatasetUrnLinkedToDatasourceFile(filename)).thenReturn(datasetUrn);
        Mockito.when(datasetVersionRepository.retrieveLastVersion(Mockito.eq(datasetUrn))).thenReturn(datasetVersion);

        List<URL> urls = Arrays.asList(buildFileUrl(filename));
        datasetService.importDatasourcesInStatisticalOperation(getServiceContextWithoutPrincipal(), statisticalOperationUrn, urls);

        ArgumentCaptor<TaskInfoDataset> argument = ArgumentCaptor.forClass(TaskInfoDataset.class);
        verify(taskService).planifyImportationDataset(any(ServiceContext.class), argument.capture());

        assertTaskInfoFile(datasetVersion, 1, argument.getValue());
        assertTaskFile(filename, DatasetFileFormatEnum.PX, argument.getValue().getFiles().get(0));
    }

    @Test
    public void testImportDatasourcesInStatisticalOperationMultiple() throws Exception {
        String filename01 = "prueba01.px";
        String filename02 = "prueba02.px";
        String statisticalOperationUrn = null;
        DatasetVersion datasetVersion01 = null;
        DatasetVersion datasetVersion02 = null;
        {
            DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_34_FOR_IMPORT_IN_OPERATION_0001_NAME);
            String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
            String datasetUrn = datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn();
            statisticalOperationUrn = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn();
            datasetVersion01 = datasetVersion;

            Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.eq(datasetVersionUrn))).thenReturn(datasetVersion);
            Mockito.when(datasetRepository.findDatasetUrnLinkedToDatasourceFile(filename01)).thenReturn(datasetUrn);
            Mockito.when(datasetVersionRepository.retrieveLastVersion(Mockito.eq(datasetUrn))).thenReturn(datasetVersion);
        }
        {
            DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_35_FOR_IMPORT_IN_OPERATION_0001_NAME);
            String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
            String datasetUrn = datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn();
            datasetVersion02 = datasetVersion;

            Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.eq(datasetVersionUrn))).thenReturn(datasetVersion);
            Mockito.when(datasetRepository.findDatasetUrnLinkedToDatasourceFile(filename02)).thenReturn(datasetUrn);
            Mockito.when(datasetVersionRepository.retrieveLastVersion(Mockito.eq(datasetUrn))).thenReturn(datasetVersion);
        }

        List<URL> urls = Arrays.asList(buildFileUrl(filename01), buildFileUrl(filename02));
        datasetService.importDatasourcesInStatisticalOperation(getServiceContextWithoutPrincipal(), statisticalOperationUrn, urls);

        ArgumentCaptor<TaskInfoDataset> argument = ArgumentCaptor.forClass(TaskInfoDataset.class);
        verify(taskService, Mockito.times(2)).planifyImportationDataset(any(ServiceContext.class), argument.capture());

        TaskInfoDataset taskDataset01 = MetamacCollectionUtils.find(argument.getAllValues(), new TaskInfoPredicateByDatasetId(datasetVersion01.getSiemacMetadataStatisticalResource().getUrn()));
        assertTaskInfoFile(datasetVersion01, 1, taskDataset01);
        assertTaskFile(filename01, DatasetFileFormatEnum.PX, taskDataset01.getFiles().get(0));

        TaskInfoDataset taskDataset02 = MetamacCollectionUtils.find(argument.getAllValues(), new TaskInfoPredicateByDatasetId(datasetVersion02.getSiemacMetadataStatisticalResource().getUrn()));
        assertTaskInfoFile(datasetVersion02, 1, taskDataset02);
        assertTaskFile(filename02, DatasetFileFormatEnum.PX, taskDataset02.getFiles().get(0));
    }

    @Test
    public void testImportDatasourcesInStatisticalOperationFilesNotEverMappedToAnyDataset() throws Exception {
        String filename01 = "prueba01.px";
        String filename02 = "prueba02.px";
        String statisticalOperationUrn = null;
        {
            DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_34_FOR_IMPORT_IN_OPERATION_0001_NAME);
            String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
            String datasetUrn = datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn();
            statisticalOperationUrn = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn();

            Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.eq(datasetVersionUrn))).thenReturn(datasetVersion);
            Mockito.when(datasetRepository.findDatasetUrnLinkedToDatasourceFile(filename01)).thenReturn(null);
            Mockito.when(datasetVersionRepository.retrieveLastVersion(Mockito.eq(datasetUrn))).thenReturn(datasetVersion);
        }
        {
            DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_35_FOR_IMPORT_IN_OPERATION_0001_NAME);
            String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
            String datasetUrn = datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn();

            Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.eq(datasetVersionUrn))).thenReturn(datasetVersion);
            Mockito.when(datasetRepository.findDatasetUrnLinkedToDatasourceFile(filename02)).thenReturn(datasetUrn);
            Mockito.when(datasetVersionRepository.retrieveLastVersion(Mockito.eq(datasetUrn))).thenReturn(datasetVersion);
        }

        expectedMetamacException(new MetamacException(ServiceExceptionType.FILE_NOT_LINKED_TO_ANY_DATASET_IN_STATISTICAL_OPERATION, filename01, statisticalOperationUrn));

        List<URL> urls = Arrays.asList(buildFileUrl(filename01), buildFileUrl(filename02));
        datasetService.importDatasourcesInStatisticalOperation(getServiceContextWithoutPrincipal(), statisticalOperationUrn, urls);

    }

    @Test
    public void testImportDatasourcesInStatisticalOperationFileAlreadyLinkedInOtherStatisticalOperation() throws Exception {
        String filename01 = "prueba01.px";
        String filename02 = "prueba02.px";
        String statisticalOperationUrn = null;
        {
            DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_34_FOR_IMPORT_IN_OPERATION_0001_NAME);
            String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
            String datasetUrn = datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn();
            statisticalOperationUrn = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn();

            Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.eq(datasetVersionUrn))).thenReturn(datasetVersion);
            Mockito.when(datasetRepository.findDatasetUrnLinkedToDatasourceFile(filename01)).thenReturn(datasetUrn);
            Mockito.when(datasetVersionRepository.retrieveLastVersion(Mockito.eq(datasetUrn))).thenReturn(datasetVersion);
        }
        {
            DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_36_FOR_IMPORT_IN_OPERATION_0002_NAME);
            String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
            String datasetUrn = datasetVersion.getDataset().getIdentifiableStatisticalResource().getUrn();

            Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.eq(datasetVersionUrn))).thenReturn(datasetVersion);
            Mockito.when(datasetRepository.findDatasetUrnLinkedToDatasourceFile(filename02)).thenReturn(datasetUrn);
            Mockito.when(datasetVersionRepository.retrieveLastVersion(Mockito.eq(datasetUrn))).thenReturn(datasetVersion);
        }

        expectedMetamacException(new MetamacException(ServiceExceptionType.FILE_NOT_LINKED_TO_ANY_DATASET_IN_STATISTICAL_OPERATION, filename02, statisticalOperationUrn));

        List<URL> urls = Arrays.asList(buildFileUrl(filename01), buildFileUrl(filename02));
        datasetService.importDatasourcesInStatisticalOperation(getServiceContextWithoutPrincipal(), statisticalOperationUrn, urls);

    }

    private void assertTaskInfoFile(DatasetVersion datasetVersion, int numFiles, TaskInfoDataset taskInfo) {
        assertEquals(datasetVersion.getRelatedDsd().getUrn(), taskInfo.getDataStructureUrn());
        assertEquals(datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), taskInfo.getDatasetVersionId());
        assertEquals(numFiles, taskInfo.getFiles().size());
    }

    private void assertTaskFile(String filename, DatasetFileFormatEnum type, FileDescriptor fileDescriptor) {
        assertEquals(filename, fileDescriptor.getFileName());
        assertEquals(type, fileDescriptor.getDatasetFileFormatEnum());
        assertNotNull(fileDescriptor.getDatasetFileFormatEnum());
    }

    private URL buildFileUrl(String filename) throws Exception {
        return new URL("file", null, filename);
    }

}
