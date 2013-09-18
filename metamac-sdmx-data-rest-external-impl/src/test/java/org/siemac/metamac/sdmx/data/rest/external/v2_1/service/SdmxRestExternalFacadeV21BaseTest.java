package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.ConnectionType;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria.Operator;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureType;
import org.siemac.metamac.common.test.utils.ConditionalCriteriaUtils;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.utils.SdmxDataCoreMocks;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.io.utils.ManipulateDataUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.context.ApplicationContext;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceDto;
import com.arte.statistic.dataset.repository.dto.ConditionDimensionDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.dataset.repository.util.DtoUtils;
import com.arte.statistic.parser.sdmx.v2_1.domain.IdValuePair;

public abstract class SdmxRestExternalFacadeV21BaseTest extends MetamacRestBaseTest {

    private static String                          jaxrsServerAddress = "http://localhost:" + ServerResource.PORT + "/apis/registry";
    protected String                               baseApi            = jaxrsServerAddress + "/v2.1";
    protected static ApplicationContext            applicationContext = null;
    protected static SdmxDataRestExternalFacadeV21 sdmxDataRestExternalFacadeClientXml;

    protected static SdmxDataCoreMocks             sdmxDataCoreMocks;

    protected DatasetRepositoriesServiceFacade     datasetRepositoriesServiceFacade;
    protected DatasetService                       datasetService;
    protected ApisLocator                          apisLocator;

    private static final String                    DATASET_ID         = "STR1";
    public static final String                     AGENCY_1           = "agency1";
    public static final String                     DATASET_1_CODE     = "dataset1";
    public static final String                     VERSION_1          = "01.000";

    private static final List<String>              DIMENSIONS         = Arrays.asList("FREQ", "CURRENCY", "CURRENCY_DENOM", "EXR_TYPE", "EXR_VAR", "TIME_PERIOD");

    @SuppressWarnings({"unchecked", "rawtypes"})
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Start server
        assertTrue("server did not launch correctly", launchServer(ServerResource.class, true));

        // Get application context from Jetty
        applicationContext = ApplicationContextProvider.getApplicationContext();

        StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks = StatisticalResourcesPersistedDoMocks.getInstance();
        sdmxDataCoreMocks = new SdmxDataCoreMocks(statisticalResourcesPersistedDoMocks);

        // Rest clients
        // xml
        {
            List providers = new ArrayList();
            providers.add(applicationContext.getBean("jaxbProvider", JAXBElementProvider.class));
            sdmxDataRestExternalFacadeClientXml = JAXRSClientFactory.create(jaxrsServerAddress, SdmxDataRestExternalFacadeV21.class, providers, Boolean.TRUE);
        }
    }

    @Before
    public void setUp() throws Exception {
        ConfigurationService configurationService = applicationContext.getBean(ConfigurationService.class);
        resetMocks();
    }

    @Test
    public void testErrorWithoutMatchError404() throws Exception {
        String requestUri = baseApi + "/nomatch";

        WebClient webClient = WebClient.create(requestUri).accept(APPLICATION_XML);
        Response response = webClient.get();

        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
        InputStream responseActual = (InputStream) response.getEntity();
        assertTrue(StringUtils.isBlank(IOUtils.toString(responseActual)));
    }

    protected SdmxDataRestExternalFacadeV21 getSdmxDataRestExternalFacadeClientXml() {
        WebClient.client(sdmxDataRestExternalFacadeClientXml).reset();
        WebClient.client(sdmxDataRestExternalFacadeClientXml).accept(APPLICATION_XML);
        return sdmxDataRestExternalFacadeClientXml;
    }

    protected void incrementRequestTimeOut(WebClient create) {
        ClientConfiguration config = WebClient.getConfig(create);
        HTTPConduit conduit = config.getHttpConduit();
        conduit.getClient().setConnectionTimeout(3000000);
        conduit.getClient().setReceiveTimeout(7000000);
        conduit.getClient().setConnection(ConnectionType.CLOSE);
    }

    protected abstract void resetMocks() throws Exception;

    @SuppressWarnings("unchecked")
    protected void mockFindObservationsExtendedByDimensions() throws Exception {
        when(datasetRepositoriesServiceFacade.findObservationsExtendedByDimensions(any(String.class), any(List.class))).thenAnswer(new Answer<Map<String, ObservationExtendedDto>>() {

            @Override
            public Map<String, ObservationExtendedDto> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                List<ConditionDimensionDto> conditionsDimensions = (List<ConditionDimensionDto>) args[1];

                Map<String, ObservationExtendedDto> observationsMap = new HashMap<String, ObservationExtendedDto>();

                List<ConditionDimensionDto> conditionsDimensionsSorted = new ArrayList<ConditionDimensionDto>(conditionsDimensions.size());
                for (String dim : DIMENSIONS) {
                    for (ConditionDimensionDto conditionDimensionDto : conditionsDimensions) {
                        if (conditionDimensionDto.getDimensionId().equals(dim)) {
                            conditionsDimensionsSorted.add(conditionDimensionDto);
                            break;
                        }
                    }
                }

                extractObservations(SdmxDataCoreMocks.mockObservations(), observationsMap, conditionsDimensionsSorted, 0, new LinkedList<String>());

                return observationsMap;
            };
        });
    }

    @SuppressWarnings("unchecked")
    protected void mockFindAttributesInstancesWithDimensionAttachmentLevelDenormalized() throws Exception {
        when(datasetRepositoriesServiceFacade.findAttributesInstancesWithDimensionAttachmentLevelDenormalized(any(String.class), any(String.class), any(Map.class))).thenAnswer(
                new Answer<List<AttributeInstanceDto>>() {

                    @Override
                    public List<AttributeInstanceDto> answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();

                        List<AttributeInstanceDto> result = new ArrayList<AttributeInstanceDto>();

                        // DimensionId, List<Codes>
                        Map<String, List<String>> conditionsMap = (Map<String, List<String>>) args[2];
                        String attributeId = (String) args[1];

                        List<ConditionDimensionDto> conditionsDimensionsSorted = new ArrayList<ConditionDimensionDto>(conditionsMap.size());
                        {
                            ConditionDimensionDto conditionDimensionDto = new ConditionDimensionDto();
                            conditionDimensionDto.setDimensionId("FREQ");
                            conditionDimensionDto.setCodesDimension(conditionsMap.get("FREQ"));
                            conditionsDimensionsSorted.add(conditionDimensionDto);
                        }
                        {
                            ConditionDimensionDto conditionDimensionDto = new ConditionDimensionDto();
                            conditionDimensionDto.setDimensionId("CURRENCY");
                            conditionDimensionDto.setCodesDimension(conditionsMap.get("CURRENCY"));
                            conditionsDimensionsSorted.add(conditionDimensionDto);
                        }
                        {
                            ConditionDimensionDto conditionDimensionDto = new ConditionDimensionDto();
                            conditionDimensionDto.setDimensionId("CURRENCY_DENOM");
                            conditionDimensionDto.setCodesDimension(conditionsMap.get("CURRENCY_DENOM"));
                            conditionsDimensionsSorted.add(conditionDimensionDto);
                        }
                        {
                            ConditionDimensionDto conditionDimensionDto = new ConditionDimensionDto();
                            conditionDimensionDto.setDimensionId("EXR_TYPE");
                            conditionDimensionDto.setCodesDimension(conditionsMap.get("EXR_TYPE"));
                            conditionsDimensionsSorted.add(conditionDimensionDto);
                        }
                        {
                            ConditionDimensionDto conditionDimensionDto = new ConditionDimensionDto();
                            conditionDimensionDto.setDimensionId("EXR_VAR");
                            conditionDimensionDto.setCodesDimension(conditionsMap.get("EXR_VAR"));
                            conditionsDimensionsSorted.add(conditionDimensionDto);
                        }
                        {
                            ConditionDimensionDto conditionDimensionDto = new ConditionDimensionDto();
                            conditionDimensionDto.setDimensionId("TIME_PERIOD");
                            conditionDimensionDto.setCodesDimension(conditionsMap.get("TIME_PERIOD"));
                            conditionsDimensionsSorted.add(conditionDimensionDto);
                        }

                        extractAttributes(SdmxDataCoreMocks.mockAttributes(), attributeId, result, conditionsDimensionsSorted, SdmxDataCoreMocks.mockAttributesIds(), 0, new LinkedList<IdValuePair>(),
                                new HashSet<String>());

                        return result;
                    };
                });
    }

    @SuppressWarnings("unchecked")
    protected void mockRetrieveDatasetRepository() throws Exception {
        when(datasetRepositoriesServiceFacade.retrieveDatasetRepository(any(String.class))).thenAnswer(new Answer<DatasetRepositoryDto>() {

            @Override
            public DatasetRepositoryDto answer(InvocationOnMock invocation) throws Throwable {
                DatasetRepositoryDto datasetRepositoryDto = new DatasetRepositoryDto();

                datasetRepositoryDto.setDatasetId(DATASET_ID);
                datasetRepositoryDto.setDimensions(DIMENSIONS);

                return datasetRepositoryDto;
            };
        });
    }

    @SuppressWarnings("unchecked")
    protected void mockFindCodeDimensions() throws Exception {
        when(datasetRepositoriesServiceFacade.findCodeDimensions(any(String.class))).thenAnswer(new Answer<Map<String, List<String>>>() {

            @Override
            public Map<String, List<String>> answer(InvocationOnMock invocation) throws Throwable {
                Map<String, List<String>> conditionObservationDtos = new HashMap<String, List<String>>();

                // FREQ
                {
                    conditionObservationDtos.put("FREQ", Arrays.asList("M"));
                }

                // CURRENCY
                {
                    conditionObservationDtos.put("CURRENCY", Arrays.asList("CHF", "JPY", "GBP", "USD"));
                }

                // CURRENCY_DENOM
                {
                    conditionObservationDtos.put("CURRENCY_DENOM", Arrays.asList("EUR"));
                }

                // EXR_TYPE
                {
                    conditionObservationDtos.put("EXR_TYPE", Arrays.asList("SP00"));
                }

                // EXR_VAR
                {
                    conditionObservationDtos.put("EXR_VAR", Arrays.asList("E"));
                }

                // TIME_PERIOD
                {
                    conditionObservationDtos.put("TIME_PERIOD", Arrays.asList("2010-08", "2010-09", "2010-10"));
                }
                return conditionObservationDtos;
            };
        });
    }

    @SuppressWarnings("unchecked")
    protected void mockFindDatasetVersionsByCondition() throws Exception {
        when(datasetService.findDatasetVersionsByCondition(any(ServiceContext.class), any(List.class), any(PagingParameter.class))).thenAnswer(new Answer<PagedResult<DatasetVersion>>() {

            @Override
            public PagedResult<DatasetVersion> answer(InvocationOnMock invocation) throws Throwable {
                List<ConditionalCriteria> conditions = (List<ConditionalCriteria>) invocation.getArguments()[1];

                String agencyID = getAgencyIdFromConditionalCriteria(conditions);
                String resourceID = getResourceIdFromConditionalCriteria(conditions);
                String version = getVersionFromConditionalCriteria(conditions);

                if (StringUtils.isEmpty(agencyID)) {
                    agencyID = "ECB";
                }

                if (StringUtils.isEmpty(version)) {
                    version = "1.0";
                }

                List<DatasetVersion> datasets = new ArrayList<DatasetVersion>();

                if (!StringUtils.isEmpty(resourceID)) {
                    DatasetVersion mockDatasetVersion = sdmxDataCoreMocks.mockDatasetVersion(agencyID, resourceID, version);
                    mockDatasetVersion.setDatasetRepositoryId(DATASET_ID);
                    mockDatasetVersion.getSiemacMetadataStatisticalResource().setCode(resourceID);
                    mockDatasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().setCodeNested(agencyID);
                    mockDatasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(version);
                    mockDatasetVersion.getRelatedDsd().setUrn(GeneratorUrnUtils.generateSdmxDatastructureUrn(new String[]{agencyID}, resourceID, version));
                    datasets.add(mockDatasetVersion);
                } else {
                    {
                        DatasetVersion mockDatasetVersion = sdmxDataCoreMocks.mockDatasetVersion(agencyID, resourceID, version);
                        mockDatasetVersion.setDatasetRepositoryId(DATASET_ID);
                        resourceID = "ECB_EXR_NG";
                        mockDatasetVersion.getSiemacMetadataStatisticalResource().setCode(resourceID);
                        mockDatasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().setCodeNested(agencyID);
                        mockDatasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(version);
                        mockDatasetVersion.getRelatedDsd().setUrn(GeneratorUrnUtils.generateSdmxDatastructureUrn(new String[]{agencyID}, resourceID, version));
                        datasets.add(mockDatasetVersion);
                    }
                    {
                        DatasetVersion mockDatasetVersion = sdmxDataCoreMocks.mockDatasetVersion(agencyID, resourceID, version);
                        mockDatasetVersion.setDatasetRepositoryId(DATASET_ID);
                        resourceID = "ECB_EXR_SG";
                        mockDatasetVersion.getSiemacMetadataStatisticalResource().setCode(resourceID);
                        mockDatasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().setCodeNested(agencyID);
                        mockDatasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(version);
                        mockDatasetVersion.getRelatedDsd().setUrn(GeneratorUrnUtils.generateSdmxDatastructureUrn(new String[]{agencyID}, resourceID, version));
                        datasets.add(mockDatasetVersion);
                    }
                    {
                        DatasetVersion mockDatasetVersion = sdmxDataCoreMocks.mockDatasetVersion(agencyID, resourceID, version);
                        mockDatasetVersion.setDatasetRepositoryId(DATASET_ID);
                        resourceID = "ECB_EXR_RG";
                        mockDatasetVersion.getSiemacMetadataStatisticalResource().setCode(resourceID);
                        mockDatasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().setCodeNested(agencyID);
                        mockDatasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(version);
                        mockDatasetVersion.getRelatedDsd().setUrn(GeneratorUrnUtils.generateSdmxDatastructureUrn(new String[]{agencyID}, resourceID, version));
                        datasets.add(mockDatasetVersion);
                    }
                }

                return new PagedResult<DatasetVersion>(datasets, datasets.size(), datasets.size(), datasets.size(), datasets.size() * 10, 0);
            }

        });
    }

    @SuppressWarnings("unchecked")
    protected void mockApisLocator() throws Exception {
        when(apisLocator.retrieveDsdByUrn(any(String.class))).thenAnswer(new Answer<DataStructureType>() {

            @Override
            public DataStructureType answer(InvocationOnMock invocation) throws Throwable {
                String dsdUrn = (String) invocation.getArguments()[0];

                DataStructureType result = null;
                if (dsdUrn.contains("ECB_EXR_RG")) {
                    result = sdmxDataCoreMocks.mockDsd_ECB_EXR_RG();
                } else if (dsdUrn.contains("ECB_EXR_NG")) {
                    result = sdmxDataCoreMocks.mockDsd_ECB_EXR_NG();
                } else if (dsdUrn.contains("ECB_EXR_SG")) {
                    result = sdmxDataCoreMocks.mockDsd_ECB_EXR_SG();
                }

                return result;
            }

        });
    }

    private void extractObservations(Map<String, ObservationExtendedDto> allObservationsMap, Map<String, ObservationExtendedDto> observationsMap, List<ConditionDimensionDto> conditionsDimensions,
            int currentDimension, List<String> codesDimension) {

        // FINAL
        if (conditionsDimensions.size() == currentDimension) {
            // ADD Observation
            String generateUniqueKeyWithCodes = DtoUtils.generateUniqueKeyWithCodes(codesDimension);
            observationsMap.put(generateUniqueKeyWithCodes, allObservationsMap.get(generateUniqueKeyWithCodes));
            return;
        }

        for (String code : conditionsDimensions.get(currentDimension).getCodesDimension()) {
            List<String> newCodes = new ArrayList<String>(codesDimension.size() + 1);
            newCodes.addAll(codesDimension);
            newCodes.add(code);

            extractObservations(allObservationsMap, observationsMap, conditionsDimensions, currentDimension + 1, newCodes);
        }
    }

    private void extractAttributes(List<Pair<List<IdValuePair>, AttributeInstanceDto>> allAttributesList, String attributeId, List<AttributeInstanceDto> attributes,
            List<ConditionDimensionDto> conditionsDimensions, List<String> attributesIds, int currentDimension, List<IdValuePair> codes, Set<String> currentAdded) {

        // FINAL
        if (conditionsDimensions.size() == currentDimension) {
            // ADD attribute
            String key = ManipulateDataUtils.generateKeyFromIdValuePairs(codes);
            for (Pair<List<IdValuePair>, AttributeInstanceDto> pair : allAttributesList) {
                if (attributeId != null && !attributeId.equals(pair.getRight().getAttributeId())) {
                    continue;
                }

                boolean add = true;
                for (IdValuePair idValuePair : pair.getLeft()) {
                    if (!key.contains(ManipulateDataUtils.generateKeyFromIdValuePairs(Arrays.asList(idValuePair)))) {
                        add = false;
                        break;
                    }
                }
                String attributeKey = ManipulateDataUtils.generateKeyForAttribute(attributeId, ManipulateDataUtils.generateKeyFromIdValuePairs(pair.getLeft()));
                if (add && !currentAdded.contains(attributeKey)) {
                    attributes.add(pair.getRight());
                    currentAdded.add(attributeKey);
                }
            }

            return;
        }

        for (String code : conditionsDimensions.get(currentDimension).getCodesDimension()) {
            List<IdValuePair> newCodes = new ArrayList<IdValuePair>(codes.size() + 1);
            newCodes.addAll(codes);
            newCodes.add(new IdValuePair(conditionsDimensions.get(currentDimension).getDimensionId(), code));

            extractAttributes(allAttributesList, attributeId, attributes, conditionsDimensions, attributesIds, currentDimension + 1, newCodes, currentAdded);
        }
    }

    private String getAgencyIdFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        // can use PublicationVersionProperties or DatasetVersionProperties...
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, DatasetVersionProperties
                .siemacMetadataStatisticalResource().maintainer().codeNested());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    private String getResourceIdFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        // can use PublicationVersionProperties or DatasetVersionProperties...
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, DatasetVersionProperties
                .siemacMetadataStatisticalResource().code());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    private String getVersionFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        // can use PublicationVersionProperties or DatasetVersionProperties...
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, DatasetVersionProperties
                .siemacMetadataStatisticalResource().versionLogic());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }
}