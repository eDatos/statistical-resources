package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import static org.mockito.Mockito.reset;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CategorisationsType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataflowsType;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;

import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.domain.TypeSDMXDataMessageEnum;

public class SdmxRestExternalFacadeV10DataTest extends SdmxRestExternalFacadeV21BaseTest {

    /**************************************************************************
     * XS_ECB_EXR_RG
     **************************************************************************/

    @Test
    public void testData_Specific_FLAT_ECB_EXR_RG1() throws Exception {

        { // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=AllDimensions");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), false);
        }
    }

    @Test
    public void testData_Specific_XS_ECB_EXR_RG() throws Exception {

        { // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=CURRENCY");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testData_Specific_TS_ECB_EXR_RG() throws Exception {

        {
            // All data: specific time series with general format (StructureSpecificTimeSeriesData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=TIME_PERIOD");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_TIME_SERIES_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testData_Specific_TS_GF_ECB_EXR_RG() throws Exception {

        {
            // All data: specific time series with general format (StructureSpecificTimeSeriesData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=TIME_PERIOD");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testData_Generic_FLAT_ECB_EXR_RG() throws Exception {

        { // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=AllDimensions");
            create.accept(TypeSDMXDataMessageEnum.GENERIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testData_Generic_XS_ECB_EXR_RG() throws Exception {

        { // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=CURRENCY");
            create.accept(TypeSDMXDataMessageEnum.GENERIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testData_Generic_TS_ECB_EXR_RG() throws Exception {

        {
            // All data: specific time series with general format (StructureSpecificTimeSeriesData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=TIME_PERIOD");
            create.accept(TypeSDMXDataMessageEnum.GENERIC_TIME_SERIES_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testData_Generic_TS_GF_ECB_EXR_RG() throws Exception {

        {
            // All data: specific time series with general format (StructureSpecificTimeSeriesData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=TIME_PERIOD");
            create.accept(TypeSDMXDataMessageEnum.GENERIC_TIME_SERIES_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testDataInSeriesFrom_ECB_EXR_NG() throws Exception {

        { // All data: specific time series with general format
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_NG?dimensionAtObservation=CURRENCY");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());

            // Timeout
            incrementRequestTimeOut(create);

            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testDataInSeriesFrom_ECB_EXR_SG() throws Exception {

        { // All data: specific time series with general format
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_SG?dimensionAtObservation=CURRENCY");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());

            // Timeout
            incrementRequestTimeOut(create);

            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testDataInDataInFlattedFrom_ECB_EXR_NG() throws Exception {

        { // All data: specific time series with general format
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_NG?dimensionAtObservation=AllDimensions");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());

            // Timeout
            incrementRequestTimeOut(create);

            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testDataInFlattedFrom_ECB_EXR_SG() throws Exception {

        { // All data: specific time series with general format
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_SG?dimensionAtObservation=AllDimensions");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());

            // Timeout
            incrementRequestTimeOut(create);

            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testDataKey() throws Exception {

        String key = "M.CHF.EUR.SP00.E.2010-08";

        {
            // All data: generic time series with general format
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG/" + key + "?dimensionAtObservation=TIME_PERIOD");
            create.accept(TypeSDMXDataMessageEnum.GENERIC_2_1.getValue());
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }

    }

    @Test
    public void testDataKeyWildcards() throws Exception {

        String key = "M..EUR.SP00.E.2010-08";

        {
            // All data: generic time series with general format
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG/" + key + "?dimensionAtObservation=TIME_PERIOD");
            create.accept(TypeSDMXDataMessageEnum.GENERIC_2_1.getValue());
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testDataPeriodParameters() throws Exception {

        String key = "M..EUR.SP00.E.";

        {
            // Only observations from 2010-09
            // All data: generic time series with general format
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG/" + key + "?dimensionAtObservation=TIME_PERIOD&startPeriod=2010-09");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);

        }

        {
            // Only observations between 2010-09 and 2010-09
            // All data: generic time series with general format
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG/" + key + "?dimensionAtObservation=TIME_PERIOD&startPeriod=2010-09&endPeriod=2010-09");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);

        }

        {
            // Only observations until 2010-09
            // All data: generic time series with general format
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG/" + key + "?dimensionAtObservation=TIME_PERIOD&endPeriod=2010-09");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }
    }

    @Test
    public void testDataDetailParameters() throws Exception {

        {
            // FULL
            // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=CURRENCY&detail=full");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }

        {
            // DATA ONLY
            // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=CURRENCY&detail=dataonly");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }

        {
            // SERIES KEY
            // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=CURRENCY&detail=serieskeysonly");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }

        {
            // NO DATA
            // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=CURRENCY&detail=nodata");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = null; // SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/data/structured/FLAT_ECB_EXR_RG.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), true);
        }

    }

    @Test
    public void testDataFlow_ALL() throws Exception {
        {
            // DATAFLOW
            WebClient create = WebClient.create(baseApi + "/dataflow");
            // create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/dataflow/dataflows.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), false);
        }
    }

    @Test
    public void testDataFlow_ALL_Entity() throws Exception {
        {
            // DATAFLOW
            JAXBElement<DataflowsType> findDataFlowsInternal = getSdmxDataRestExternalFacadeClientXml().findDataFlowsInternal();
            assertNotNull(findDataFlowsInternal.getValue());
        }
    }

    @Test
    public void testCategorisations_ALL() throws Exception {
        {
            // CATEGORISATIONS
            WebClient create = WebClient.create(baseApi + "/categorisation");
            // create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();

            InputStream responseExpected = SdmxRestExternalFacadeV10DataTest.class.getResourceAsStream("/responses/categorisation/categorisations.xml");
            assertEquals(200, findData.getStatus());
            assertInputStream(responseExpected, (InputStream) findData.getEntity(), false);
        }
    }

    @Test
    public void testCategorisations_ALL_Entity() throws Exception {
        {
            // DATAFLOW
            JAXBElement<CategorisationsType> findCategorisationsInternal = getSdmxDataRestExternalFacadeClientXml().findCategorisationsInternal();
            assertNotNull(findCategorisationsInternal.getValue());
        }
    }

    @Override
    protected void resetMocks() throws Exception {
        datasetRepositoriesServiceFacade = applicationContext.getBean(DatasetRepositoriesServiceFacade.class);
        reset(datasetRepositoriesServiceFacade);

        datasetService = applicationContext.getBean(DatasetService.class);
        reset(datasetService);

        apisLocator = applicationContext.getBean(ApisLocator.class);
        reset(apisLocator);

        // Dataset Repositories Service Facade
        mockFindObservationsExtendedByDimensions();
        mockFindAttributesInstancesWithDimensionAttachmentLevelDenormalized();
        mockRetrieveDatasetRepository();
        mockFindCodeDimensions();

        // Dataset
        mockFindDatasetVersionsByCondition();

        // Categorisation
        mockFindCategorisationsByCondition();

        // Apis Locator
        mockApisLocator();
    }

}
