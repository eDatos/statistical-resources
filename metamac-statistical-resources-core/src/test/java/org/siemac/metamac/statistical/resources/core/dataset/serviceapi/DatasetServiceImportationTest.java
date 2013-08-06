package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.validators.DatasetServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.DatasetServiceImpl;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;

public class DatasetServiceImportationTest extends StatisticalResourcesBaseTest {
    
    private static final String DATASET_MOCK_URN = "MOCK_URN";

    private DatasetVersionMockFactory         datasetVersionMockFactory = new DatasetVersionMockFactory();

    @InjectMocks
    private DatasetService                    datasetService            = new DatasetServiceImpl();

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
        datasetVersionMockFactory.setStatisticalResourcesPersistedDoMocks(new StatisticalResourcesPersistedDoMocks());
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
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

        assertTaskInfoSingleFile(datasetVersion, filename, DatasetFileFormatEnum.PX, argument.getValue());
    }

    @Autowired
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
    
    private void assertTaskInfoSingleFile(DatasetVersion datasetVersion, String filename, DatasetFileFormatEnum type, TaskInfoDataset taskInfo) {
        assertEquals(datasetVersion.getRelatedDsd().getUrn(), taskInfo.getDataStructureUrn());
        assertEquals(datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), taskInfo.getDatasetVersionId());
        assertEquals(1, taskInfo.getFiles().size());
        assertEquals(filename, taskInfo.getFiles().get(0).getFileName());
        assertEquals(type, taskInfo.getFiles().get(0).getDatasetFileFormatEnum());
    }
    
    private URL buildFileUrl(String filename) throws Exception {
        return new URL("file", null, filename);
    }
}
