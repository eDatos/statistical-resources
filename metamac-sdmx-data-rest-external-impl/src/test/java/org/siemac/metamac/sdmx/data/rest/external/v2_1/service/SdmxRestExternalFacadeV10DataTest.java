package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.utils.SdmxDataCoreMocks;

import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ConditionDimensionDto;
import com.arte.statistic.dataset.repository.dto.ConditionObservationDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.dataset.repository.util.DtoUtils;

public class SdmxRestExternalFacadeV10DataTest extends SdmxRestExternalFacadeV21BaseTest {

    private static final String       DATASET_ID = "TEST_DATA_STR_ECB_EXR_RG";

    private static final List<String> DIMENSIONS = Arrays.asList("FREQ", "CURRENCY", "CURRENCY_DENOM", "EXR_TYPE", "EXR_VAR", "TIME_PERIOD");

    @Test
    public void testData() throws Exception {

        {
            // All data
            // Response findData = getSdmxDataRestExternalFacadeClientXml().findData(DATASET_ID, null, null);
            WebClient create = WebClient.create("http://localhost:9001/apis/sdmx/data-resources/v2.1/data/TEST_DATA_STR_ECB_EXR_RG?dimensionAtObservation=TIME_PERIOD");
            create.accept("application/vnd.sdmx.genericdata+xml;version=2.1");
            Response findData = create.get();

            System.out.println("_____________");
            System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));

        }

        {
            // All data, with FREQ as dimensionAtObservation
            // Response findData = getSdmxDataRestExternalFacadeClientXml().findData(RestExternalConstants.MEDIATYPE_MESSAGE_GENERICDATA_2_1, DATASET_ID, null, "FREQ");
            //
            // System.out.println("_____________");
            // System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));
        }
    }

    @Test
    public void testDataKey() throws Exception {

        String key = "M.CHF.EUR.SP00.E.2010-08";
        // Response findData = getSdmxDataRestExternalFacadeClientXml().findData(RestExternalConstants.MEDIATYPE_MESSAGE_GENERICDATA_2_1, DATASET_ID, key, null, null);
        //
        // System.out.println("_____________");
        // System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));
    }

    @Test
    public void testDataKeyWildcards() throws Exception {

        String key = "M..EUR.SP00.E.2010-08";
        // Response findData = getSdmxDataRestExternalFacadeClientXml().findData(RestExternalConstants.MEDIATYPE_MESSAGE_GENERICDATA_2_1, DATASET_ID, key, null, null);
        //
        // System.out.println("_____________");
        // System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));
    }

    @Override
    protected void resetMocks() throws Exception {
        datasetRepositoriesServiceFacade = applicationContext.getBean(DatasetRepositoriesServiceFacade.class);
        reset(datasetRepositoriesServiceFacade);

        mockFindObservationsExtendedByDimensions();
        mockRetrieveDatasetRepository();
        mockFindCodeDimensions();
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
        when(datasetRepositoriesServiceFacade.findCodeDimensions(any(String.class))).thenAnswer(new Answer<List<ConditionObservationDto>>() {

            @Override
            public List<ConditionObservationDto> answer(InvocationOnMock invocation) throws Throwable {
                List<ConditionObservationDto> conditionObservationDtos = new ArrayList<ConditionObservationDto>(6);

                // FREQ
                {
                    ConditionObservationDto conditionObservationDto = new ConditionObservationDto();
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("FREQ", "M"));
                    conditionObservationDtos.add(conditionObservationDto);
                }

                // CURRENCY
                {
                    ConditionObservationDto conditionObservationDto = new ConditionObservationDto();
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("CURRENCY", "CHF"));
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("CURRENCY", "JPY"));
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("CURRENCY", "GBP"));
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("CURRENCY", "USD"));
                    conditionObservationDtos.add(conditionObservationDto);
                }

                // CURRENCY_DENOM
                {
                    ConditionObservationDto conditionObservationDto = new ConditionObservationDto();
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
                    conditionObservationDtos.add(conditionObservationDto);
                }

                // EXR_TYPE
                {
                    ConditionObservationDto conditionObservationDto = new ConditionObservationDto();
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("EXR_TYPE", "SP00"));
                    conditionObservationDtos.add(conditionObservationDto);
                }

                // EXR_VAR
                {
                    ConditionObservationDto conditionObservationDto = new ConditionObservationDto();
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("EXR_VAR", "E"));
                    conditionObservationDtos.add(conditionObservationDto);
                }

                // TIME_PERIOD
                {
                    ConditionObservationDto conditionObservationDto = new ConditionObservationDto();
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("TIME_PERIOD", "2010-08"));
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("TIME_PERIOD", "2010-09"));
                    conditionObservationDto.addCodesDimension(new CodeDimensionDto("TIME_PERIOD", "2010-10"));
                    conditionObservationDtos.add(conditionObservationDto);
                }
                return conditionObservationDtos;
            };
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

}
