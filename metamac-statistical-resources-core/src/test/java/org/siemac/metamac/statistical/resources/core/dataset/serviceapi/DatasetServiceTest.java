package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.DATASET_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.getDataset03With2DatasetVersions;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.DATASET_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.DATASOURCE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.DATASOURCE_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersionCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasourceCollection;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesNotPersistedDoMocks;
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
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetServiceTest extends StatisticalResourcesBaseTest implements DatasetServiceTestBase {

    @Autowired
    protected DatasetService                          datasetService;

    @Autowired
    protected DatasetVersionMockFactory               datasetVersionMockFactory;

    @Autowired
    protected DatasourceMockFactory                   datasourceMockFactory;

    @Autowired
    protected StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testCreateDatasource() throws Exception {
        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceForPersist();
        Datasource actual = datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionMockFactory.DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn(),
                expected);
        expected.setDatasetVersion(datasetVersionMockFactory.DATASET_VERSION_01_BASIC);
        assertEqualsDatasource(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testCreateDatasourceErrorIdentifiableResourceRequired() throws Exception {
        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasourceWithIdentifiableAndDatasetVersionNull(datasetVersionMockFactory.DATASET_VERSION_01_BASIC);
        try {
            datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionMockFactory.DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn(), expected);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            BaseAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, 1, new String[]{ServiceExceptionParameters.DATASOURCE__IDENTIFIABLE_STATISTICAL_RESOURCE}, e
                    .getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testCreateDatasourceErrorDatasetVersionMustBeEmpty() throws Exception {
        Datasource expected = statisticalResourcesNotPersistedDoMocks.mockDatasource(datasetVersionMockFactory.DATASET_VERSION_01_BASIC);
        try {
            datasetService.createDatasource(getServiceContextWithoutPrincipal(), datasetVersionMockFactory.DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn(), expected);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            BaseAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_UNEXPECTED, 1, new String[]{ServiceExceptionParameters.DATASOURCE__DATASET_VERSION}, e.getExceptionItems()
                    .get(0));
        }
    }

    @Override
    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testUpdateDatasource() throws Exception {
        Datasource expected = datasourceMockFactory.DATASOURCE_01_BASIC;
        expected.getIdentifiableStatisticalResource().setCode("NEW-CODE");

        Datasource actual = datasetService.updateDatasource(getServiceContextWithoutPrincipal(), expected);
        assertEqualsDatasource(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testRetrieveDatasourceByUrn() throws Exception {
        Datasource actual = datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), datasourceMockFactory.DATASOURCE_01_BASIC.getIdentifiableStatisticalResource().getUrn());
        assertEqualsDatasource(datasourceMockFactory.DATASOURCE_01_BASIC, actual);
    }

    @Test
    public void testRetrieveDatasourceByUrnParameterRequired() throws MetamacException {
        try {
            datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), EMPTY);
            fail("parameter required");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionSingleParameters.URN}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testRetrieveDatasourceByUrnNotExists() throws MetamacException {
        try {
            datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
            fail("not exists");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASOURCE_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testDeleteDatasource() throws Exception {
        String datasourceUrn = datasourceMockFactory.DATASOURCE_01_BASIC.getIdentifiableStatisticalResource().getUrn();
        datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), datasourceUrn);

        try {
            datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), datasourceUrn);
            fail("datasource deleted");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASOURCE_NOT_FOUND, 1, new String[]{datasourceUrn}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testDeleteDatasourceErrorNotExists() throws Exception {
        String datasourceUrn = URN_NOT_EXISTS;

        try {
            datasetService.deleteDatasource(getServiceContextWithoutPrincipal(), datasourceUrn);
            fail("not exists");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASOURCE_NOT_FOUND, 1, new String[]{datasourceUrn}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasourcesByDatasetVersion() throws Exception {
        // Version DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03
        {
            String datasetVersionUrn = datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03.getSiemacMetadataStatisticalResource().getUrn();
            List<Datasource> expected = datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03.getDatasources();

            List<Datasource> actual = datasetService.retrieveDatasourcesByDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersionUrn);
            assertEquals(expected.size(), actual.size());
            assertEqualsDatasourceCollection(expected, actual);
        }

        // Version DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03
        {
            String datasetVersionUrn = datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getUrn();
            List<Datasource> expected = datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION.getDatasources();

            List<Datasource> actual = datasetService.retrieveDatasourcesByDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersionUrn);
            assertEquals(expected.size(), actual.size());
            assertEqualsDatasourceCollection(expected, actual);
        }
    }

    // ------------------------------------------------------------------------
    // DATASETS
    // ------------------------------------------------------------------------

    @Override
    @Test
    public void testCreateDatasetVersion() throws Exception {
        DatasetVersion expected = statisticalResourcesNotPersistedDoMocks.mockDatasetVersion();

        DatasetVersion actual = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected);
        assertEquals("01.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());

        assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    public void testCreateDatasetVersionErrorParameterDatasetRequired() throws Exception {
        try {
            datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), null);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionParameters.DATASET_VERSION}, e.getExceptionItems().get(0));
        }
    }

    @Test
    public void testCreateDatasetVersionErrorMetadataSiemacStatisticalResourceRequired() throws Exception {
        try {
            DatasetVersion expected = statisticalResourcesNotPersistedDoMocks.mockDatasetVersionWithNullableSiemacStatisticalResource();
            datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, 1, new String[]{ServiceExceptionParameters.DATASET_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE}, e
                    .getExceptionItems().get(0));
        }
    }

    @Override
    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testUpdateDatasetVersion() throws Exception {
        DatasetVersion expected = datasetVersionMockFactory.DATASET_VERSION_01_BASIC;
        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        expected.getSiemacMetadataStatisticalResource().setDescription(statisticalResourcesNotPersistedDoMocks.mockInternationalString());

        DatasetVersion actual = datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), expected);
        assertEqualsDatasetVersion(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testUpdateDatasetVersionErrorFinal() throws Exception {
        DatasetVersion finalDataset = datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03;

        try {
            datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), finalDataset);
            fail("dataset version can be edited");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_STATISTICAL_RESOURCE_NOT_MODIFIABLE, 1, new String[]{finalDataset.getSiemacMetadataStatisticalResource().getUrn()}, e
                    .getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testUpdateDatasetVersionErrorIncorrectCode() throws Exception {
        DatasetVersion dataset = datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;

        try {
            dataset.getSiemacMetadataStatisticalResource().setCode("12345");
            datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), dataset);
            fail("incorrect code");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT, 1, new String[]{ServiceExceptionParameters.DATASET_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE__CODE}, e
                    .getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testUpdateDatasetVersionErrorDuplicatedCode() throws Exception {
        DatasetVersion dataset = datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION;
        String duplicatedCode = datasetVersionMockFactory.DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getCode();

        try {
            dataset.getSiemacMetadataStatisticalResource().setCode(duplicatedCode);
            datasetService.updateDatasetVersion(getServiceContextWithoutPrincipal(), dataset);
            fail("duplicated code");
            // TODO: NOTA MENTAL: Es normal que este test no falle hasta que en el servicio se cumplimenten correctamente las URN en función de los CODE
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.IDENTIFIABLE_STATISTICAL_RESOURCE_URN_DUPLICATED, 1, new String[]{duplicatedCode}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasetVersionByUrn() throws Exception {
        String urn = datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03.getSiemacMetadataStatisticalResource().getUrn();
        DatasetVersion actual = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsDatasetVersion(datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03, actual);
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasetVersionByUrnV2() throws Exception {
        String urn = datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getUrn();
        DatasetVersion actual = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsDatasetVersion(datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION, actual);
    }

    @Test
    public void testRetrieveDatasetVersionByUrnErrorParameterRequiered() throws Exception {
        try {
            datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), null);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionSingleParameters.URN}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasetVersionByUrnErrorNotFound() throws Exception {
        try {
            datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_01_BASIC_NAME, DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME})
    public void testRetrieveDatasetVersions() throws Exception {
        String urn = datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03.getSiemacMetadataStatisticalResource().getUrn();
        List<DatasetVersion> actual = datasetService.retrieveDatasetVersions(getServiceContextWithoutPrincipal(), urn);

        assertEquals(2, actual.size());
        assertEqualsDatasetVersionCollection(getDataset03With2DatasetVersions().getVersions(), actual);
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_01_BASIC_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testFindDatasetVersionsByCondition() throws Exception {
        {
            // Find by last version
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class)
                    .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().isLastVersion()).eq(Boolean.TRUE)
                    .orderBy(DatasetVersionProperties.siemacMetadataStatisticalResource().id()).ascending().build();

            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<DatasetVersion> datasetVersionPagedResult = datasetService.findDatasetVersionsByCondition(getServiceContextWithoutPrincipal(), conditions, pagingParameter);
            assertEquals(1, datasetVersionPagedResult.getTotalRows());
            assertEquals(datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getUrn(), datasetVersionPagedResult.getValues().get(0)
                    .getSiemacMetadataStatisticalResource().getUrn());
        }

        {
            // Find by version number
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class)
                    .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().versionLogic()).eq("02.000").orderBy(DatasetVersionProperties.siemacMetadataStatisticalResource().id())
                    .ascending().build();

            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<DatasetVersion> datasetVersionPagedResult = datasetService.findDatasetVersionsByCondition(getServiceContextWithoutPrincipal(), conditions, pagingParameter);
            assertEquals(1, datasetVersionPagedResult.getTotalRows());
            assertEquals(datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getUrn(), datasetVersionPagedResult.getValues().get(0)
                    .getSiemacMetadataStatisticalResource().getUrn());
        }
    }

    @Override
    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_01_BASIC_NAME, DATASET_VERSION_01_BASIC_NAME})
    public void testDeleteDatasetVersion() throws Exception {
        String urn = datasetVersionMockFactory.DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn();

        // Delete dataset version
        datasetService.deleteDatasetVersion(getServiceContextWithoutPrincipal(), urn);

        // Validation
        try {
            datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), urn);
            fail("datasetVersion deleted");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, 1, new String[]{urn}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME, DATASET_02_BASIC_WITH_GENERATED_VERSION_NAME})
    public void testDeleteDatasetVersionWithTwoVersions() throws Exception {
        String urnV1 = datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03.getSiemacMetadataStatisticalResource().getUrn();
        String urnV2 = datasetVersionMockFactory.DATASET_VERSION_04_FOR_DATASET_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getUrn();

        // Delete dataset version
        datasetService.deleteDatasetVersion(getServiceContextWithoutPrincipal(), urnV2);

        // Validation
        try {
            datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), urnV2);
            fail("datasetVersion deleted");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, 1, new String[]{urnV2}, e.getExceptionItems().get(0));
        }

        DatasetVersion datasetVersionV1 = datasetService.retrieveDatasetVersionByUrn(getServiceContextWithoutPrincipal(), urnV1);
        assertTrue(datasetVersionV1.getSiemacMetadataStatisticalResource().getIsLastVersion());
        assertNull(datasetVersionV1.getSiemacMetadataStatisticalResource().getIsReplacedBy());
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testDeleteDatasetVersionErrorNotExists() throws Exception {
        try {
            datasetService.deleteDatasetVersion(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
            fail("datasetVersion not exists");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testDeleteDatasetVersionErrorNoLastVersion() throws Exception {
        String urnV1 = datasetVersionMockFactory.DATASET_VERSION_03_FOR_DATASET_03.getSiemacMetadataStatisticalResource().getUrn();

        try {
            datasetService.deleteDatasetVersion(getServiceContextWithoutPrincipal(), urnV1);
            fail("datasetVersion can not be deleted");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_STATISTICAL_RESOURCE_NOT_MODIFIABLE, 1, new String[]{urnV1}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    public void testVersioningDatasetVersion() throws Exception {
        fail("not implemented");

    }

}
