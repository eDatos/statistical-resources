package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetVersionMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasetMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesDoMocks;
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
    protected DatasetService datasetService;

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME, DATASET_VERSION_02_BASIC_NAME})
    public void testCreateDatasource() throws Exception {
        // TODO NOTA MENTAL: Es normal que falle este test hasta que los servicios de dataset estén implementados
        Datasource expected = StatisticalResourcesDoMocks.mockDatasourceWithDatasetVersionNull();
        Datasource actual = datasetService.createDatasource(getServiceContextWithoutPrincipal(), DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn(), expected);
        assertEqualsDatasource(expected, actual);
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testCreateDatasourceErrorIdentifiableResourceRequired() throws Exception {
        Datasource expected = StatisticalResourcesDoMocks.mockDatasourceWithIdentifiableAndDatasetVersionNull();
        try {
            datasetService.createDatasource(getServiceContextWithoutPrincipal(), DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn(), expected);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            BaseAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, 1, new String[]{ServiceExceptionParameters.IDENTIFIABLE_RESOURCE}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASET_VERSION_01_BASIC_NAME})
    public void testCreateDatasourceErrorDatasetVersionMustBeEmpty() throws Exception {
        Datasource expected = StatisticalResourcesDoMocks.mockDatasource();
        try {
            datasetService.createDatasource(getServiceContextWithoutPrincipal(), DATASET_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn(), expected);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            BaseAsserts
                    .assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_UNEXPECTED, 1, new String[]{ServiceExceptionParameters.DATASOURCE_DATASET_VERSION}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testUpdateDatasource() throws Exception {
        Datasource expected = DATASOURCE_01_BASIC;
        expected.getIdentifiableStatisticalResource().setCode("NEW-CODE");

        Datasource actual = datasetService.updateDatasource(getServiceContextWithoutPrincipal(), expected);
        assertEqualsDatasource(expected, actual);
    }

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testRetrieveDatasourceByUrn() throws Exception {
        Datasource actual = datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), DATASOURCE_01_BASIC.getIdentifiableStatisticalResource().getUrn());
        assertEqualsDatasource(DATASOURCE_01_BASIC, actual);
    }

    @Test
    public void testRetrieveDatasourceByUrnParameterRequired() throws MetamacException {
        try {
            datasetService.retrieveDatasourceByUrn(getServiceContextWithoutPrincipal(), EMPTY);
            fail("parameter required");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionParameters.URN}, e.getExceptionItems().get(0));
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

    @Test
    @MetamacMock({DATASOURCE_01_BASIC_NAME, DATASOURCE_02_BASIC_NAME})
    public void testDeleteDatasource() throws Exception {
        String datasourceUrn = DATASOURCE_01_BASIC.getIdentifiableStatisticalResource().getUrn();
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

    @Test
    @MetamacMock({DATASET_03_BASIC_WITH_2_DATASET_VERSIONS_NAME})
    public void testRetrieveDatasourcesByDatasetVersion() throws Exception {
        // Version DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03
        {
            String datasetVersionUrn = DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03.getSiemacMetadataStatisticalResource().getUrn();
            List<Datasource> expected = DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03.getDatasources();

            List<Datasource> actual = datasetService.retrieveDatasourcesByDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersionUrn);
            assertEquals(expected.size(), actual.size());
            assertEqualsDatasourceCollection(expected, actual);
        }

        // Version DATASET_VERSION_03_ASSOCIATED_WITH_DATASET_03
        {
            String datasetVersionUrn = DATASET_VERSION_04_ASSOCIATED_WITH_DATASET_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getUrn();
            List<Datasource> expected = DATASET_VERSION_04_ASSOCIATED_WITH_DATASET_03_AND_LAST_VERSION.getDatasources();

            List<Datasource> actual = datasetService.retrieveDatasourcesByDatasetVersion(getServiceContextWithoutPrincipal(), datasetVersionUrn);
            assertEquals(expected.size(), actual.size());
            assertEqualsDatasourceCollection(expected, actual);
        }
    }

}
