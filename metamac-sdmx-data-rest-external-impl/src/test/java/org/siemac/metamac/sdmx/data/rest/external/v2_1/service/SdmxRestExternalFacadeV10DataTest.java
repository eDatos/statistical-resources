package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.cxf.jaxrs.client.WebClient;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria.Operator;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureType;
import org.siemac.metamac.common.test.utils.ConditionalCriteriaUtils;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.utils.SdmxDataCoreMocks;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.io.utils.ManipulateDataUtils;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceDto;
import com.arte.statistic.dataset.repository.dto.ConditionDimensionDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.dataset.repository.util.DtoUtils;
import com.arte.statistic.parser.sdmx.v2_1.domain.IdValuePair;
import com.arte.statistic.parser.sdmx.v2_1.domain.TypeSDMXDataMessageEnum;

public class SdmxRestExternalFacadeV10DataTest extends SdmxRestExternalFacadeV21BaseTest {

    private static final String       DATASET_ID     = "TEST_DATA_STR_ECB_EXR_RG";
    public static final String        AGENCY_1       = "agency1";
    public static final String        DATASET_1_CODE = "dataset1";
    public static final String        VERSION_1      = "01.000";

    private static final List<String> DIMENSIONS     = Arrays.asList("FREQ", "CURRENCY", "CURRENCY_DENOM", "EXR_TYPE", "EXR_VAR", "TIME_PERIOD");

    @Test
    public void testDataInSeriesFrom_ECB_EXR_RG() throws Exception {

        { // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=CURRENCY");
            create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();
            System.out.println("_____________");
            System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));
        }

        {
            // All data: specific time series with general format (StructureSpecificTimeSeriesData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=TIME_PERIOD");
            // create.accept(TypeSDMXDataMessageEnum.SPECIFIC_2_1.getValue());
            incrementRequestTimeOut(create); // Timeout
            Response findData = create.get();
            System.out.println("_____________");
            System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));
        }

        {
            // All data: generic time series with time series format (GenericTimeSeriesData)
            WebClient create = WebClient.create(baseApi + "/data/ECB_EXR_RG?dimensionAtObservation=TIME_PERIOD");
            create.accept(TypeSDMXDataMessageEnum.GENERIC_TIME_SERIES_2_1.getValue());
            Response findData = create.get();

            System.out.println("_____________");
            System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));
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

            System.out.println("_____________");
            System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));

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

            System.out.println("_____________");
            System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));

        }
    }

    @Test
    public void testDataKey() throws Exception {

        String key = "M.CHF.EUR.SP00.E.2010-08";

        {
            // All data: generic time series with general format
            WebClient create = WebClient.create(baseApi + "/data/TEST_DATA_STR_ECB_EXR_RG/" + key + "?dimensionAtObservation=TIME_PERIOD");
            create.accept(TypeSDMXDataMessageEnum.GENERIC_2_1.getValue());
            Response findData = create.get();

            System.out.println("_____________");
            System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));

        }

    }

    @Test
    public void testDataKeyWildcards() throws Exception {

        String key = "M..EUR.SP00.E.2010-08";

        {
            // All data: generic time series with general format
            WebClient create = WebClient.create(baseApi + "/data/TEST_DATA_STR_ECB_EXR_RG/" + key + "?dimensionAtObservation=TIME_PERIOD");
            create.accept(TypeSDMXDataMessageEnum.GENERIC_2_1.getValue());
            Response findData = create.get();

            System.out.println("_____________");
            System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));

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

        // Dataset Service
        mockFindDatasetVersionsByCondition();

        // Apis Locator
        mockApisLocator();
    }

    @SuppressWarnings("unchecked")
    private void mockFindObservationsExtendedByDimensions() throws Exception {
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
    private void mockFindAttributesInstancesWithDimensionAttachmentLevelDenormalized() throws Exception {
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
    private void mockRetrieveDatasetRepository() throws Exception {
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
    private void mockFindCodeDimensions() throws Exception {
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
    private void mockFindDatasetVersionsByCondition() throws Exception {
        when(datasetService.findDatasetVersionsByCondition(any(ServiceContext.class), any(List.class), any(PagingParameter.class))).thenAnswer(new Answer<PagedResult<DatasetVersion>>() {

            @Override
            public PagedResult<DatasetVersion> answer(InvocationOnMock invocation) throws Throwable {
                List<ConditionalCriteria> conditions = (List<ConditionalCriteria>) invocation.getArguments()[1];

                String agencyID = getAgencyIdFromConditionalCriteria(conditions);
                String resourceID = getResourceIdFromConditionalCriteria(conditions);
                String version = getVersionFromConditionalCriteria(conditions);

                List<DatasetVersion> datasets = new ArrayList<DatasetVersion>();

                DatasetVersion mockDatasetVersion = sdmxDataCoreMocks.mockDatasetVersion(agencyID, resourceID, version);
                mockDatasetVersion.setDatasetRepositoryId(DATASET_ID);
                mockDatasetVersion.getRelatedDsd().setUrn(GeneratorUrnUtils.generateSdmxDatastructureUrn(new String[]{"ECB"}, resourceID, "1.0"));

                datasets.add(mockDatasetVersion);
                return new PagedResult<DatasetVersion>(datasets, datasets.size(), datasets.size(), datasets.size(), datasets.size() * 10, 0);
            }

        });
    }

    @SuppressWarnings("unchecked")
    private void mockApisLocator() throws Exception {
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
