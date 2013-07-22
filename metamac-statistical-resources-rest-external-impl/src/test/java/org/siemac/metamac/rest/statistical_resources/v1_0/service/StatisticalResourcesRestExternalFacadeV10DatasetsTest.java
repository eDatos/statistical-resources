package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_2;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.DATASET_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.DATASET_2_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.NOT_EXISTS;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.VERSION_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.VERSION_2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria.Operator;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.siemac.metamac.common.test.utils.ConditionalCriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.constants.RestConstants;
import org.siemac.metamac.rest.structural_resources.v1_0.utils.SrmRestDoMocks;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.invocation.SrmRestExternalFacade;

public class StatisticalResourcesRestExternalFacadeV10DatasetsTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    private DatasetService        datasetService;
    private SrmRestExternalFacade srmRestExternalFacade;

    @Test
    public void testFindDatasetsXml() throws Exception {
        // TODO testFindDatasetsXml
    }

    @Test
    public void testRetrieveDataset() throws Exception {
        // TODO testRetrieveDataset
    }

    @Test
    public void testRetrieveDatasetXml() throws Exception {
        String requestBase = getDatasetUri(AGENCY_1, DATASET_1_CODE, VERSION_1, null, null, null);
        String[] requestUris = new String[]{requestBase, requestBase + ".xml", requestBase + "?_type=xml"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10DatasetsTest.class.getResourceAsStream("/responses/datasets/retrieveDataset.id1.xml");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Test
    public void testRetrieveDatasetErrorNotExists() throws Exception {
        // TODO testRetrieveDatasetErrorNotExists
    }

    @Test
    public void testRetrieveDatasetErrorNotExistsXml() throws Exception {
        // TODO testRetrieveDatasetErrorNotExistsXml
    }

    @SuppressWarnings("unchecked")
    private void mockFindDatasetsByCondition() throws MetamacException {
        when(datasetService.findDatasetVersionsByCondition(any(ServiceContext.class), any(List.class), any(PagingParameter.class))).thenAnswer(new Answer<PagedResult<DatasetVersion>>() {

            @Override
            public org.fornax.cartridges.sculptor.framework.domain.PagedResult<DatasetVersion> answer(InvocationOnMock invocation) throws Throwable {
                List<ConditionalCriteria> conditions = (List<ConditionalCriteria>) invocation.getArguments()[1];

                String agencyID = getAgencyIdFromConditionalCriteria(conditions);
                String resourceID = getResourceIdFromConditionalCriteria(conditions);
                String version = getVersionFromConditionalCriteria(conditions);

                if (agencyID != null && resourceID != null && version != null) {
                    // Retrieve one
                    DatasetVersion dataset = null;
                    if (NOT_EXISTS.equals(agencyID) || NOT_EXISTS.equals(resourceID) || NOT_EXISTS.equals(version)) {
                        dataset = null;
                    } else if (AGENCY_1.equals(agencyID) && DATASET_1_CODE.equals(resourceID) && VERSION_1.equals(version)) {
                        dataset = datasetDoMocks.mockDatasetVersion(agencyID, resourceID, version);
                    } else {
                        fail();
                    }
                    List<DatasetVersion> datasets = new ArrayList<DatasetVersion>();
                    if (dataset != null) {
                        datasets.add(dataset);
                    }
                    return new PagedResult<DatasetVersion>(datasets, 0, datasets.size(), datasets.size());
                } else {
                    // any
                    List<DatasetVersion> datasets = new ArrayList<DatasetVersion>();
                    datasets.add(datasetDoMocks.mockDatasetVersion(AGENCY_1, DATASET_1_CODE, VERSION_1));
                    datasets.add(datasetDoMocks.mockDatasetVersion(AGENCY_1, DATASET_1_CODE, VERSION_2));
                    datasets.add(datasetDoMocks.mockDatasetVersion(AGENCY_2, DATASET_1_CODE, VERSION_1));
                    datasets.add(datasetDoMocks.mockDatasetVersion(AGENCY_1, DATASET_2_CODE, VERSION_1));
                    return new PagedResult<DatasetVersion>(datasets, datasets.size(), datasets.size(), datasets.size(), datasets.size() * 10, 0);
                }
            };
        });
    }

    private void mockRetrieveDatasetVersionDimensionsIds() throws MetamacException {
        when(datasetService.retrieveDatasetVersionDimensionsIds(any(ServiceContext.class), any(String.class))).thenAnswer(new Answer<List<String>>() {

            @Override
            public List<String> answer(InvocationOnMock invocation) throws Throwable {
                List<String> dimensions = new ArrayList<String>();
                dimensions.add("GEO_DIM");
                dimensions.add("TIME_PERIOD");
                dimensions.add("measure01");
                dimensions.add("dim01");
                return dimensions;
            };
        });
    }

    private void mockRetrieveCoverageForDatasetVersionDimension() throws MetamacException {
        when(datasetService.retrieveCoverageForDatasetVersionDimension(any(ServiceContext.class), any(String.class), any(String.class))).thenAnswer(new Answer<List<CodeDimension>>() {

            @Override
            public List<CodeDimension> answer(InvocationOnMock invocation) throws Throwable {
                String componentId = (String) invocation.getArguments()[2];
                List<CodeDimension> codeDimensions = new ArrayList<CodeDimension>();
                if ("GEO_DIM".equals(componentId) || "dim01".equals(componentId)) {
                    codeDimensions.add(datasetDoMocks.mockCodeDimension(componentId, componentId + "-codelist01-code01"));
                    codeDimensions.add(datasetDoMocks.mockCodeDimension(componentId, componentId + "-codelist01-code03"));
                    codeDimensions.add(datasetDoMocks.mockCodeDimension(componentId, componentId + "-codelist01-code04"));
                } else if ("measure01".equals(componentId)) {
                    codeDimensions.add(datasetDoMocks.mockCodeDimension(componentId, componentId + "-conceptScheme01-concept01"));
                    codeDimensions.add(datasetDoMocks.mockCodeDimension(componentId, componentId + "-conceptScheme01-concept02"));
                    codeDimensions.add(datasetDoMocks.mockCodeDimension(componentId, componentId + "-conceptScheme01-concept05"));
                } else if ("TIME_PERIOD".equals(componentId)) {
                    codeDimensions.add(datasetDoMocks.mockCodeDimension(componentId, "2011"));
                    codeDimensions.add(datasetDoMocks.mockCodeDimension(componentId, "2012"));
                    codeDimensions.add(datasetDoMocks.mockCodeDimension(componentId, "2013"));
                } else {
                    fail(componentId + " unexpected");
                }
                return codeDimensions;
            };
        });
    }

    private void mockRetrieveDataStructureByUrn() throws MetamacException {
        when(srmRestExternalFacade.retrieveDataStructureByUrn(any(String.class))).thenAnswer(new Answer<DataStructure>() {

            @Override
            public DataStructure answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[0];
                String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
                return SrmRestDoMocks.mockDataStructure(urnSplited[0], urnSplited[1], urnSplited[2]);
            };
        });
    }

    private void mockRetrieveCodelistByUrn() throws MetamacException {
        when(srmRestExternalFacade.retrieveCodelistByUrn(any(String.class))).thenAnswer(new Answer<Codelist>() {

            @Override
            public Codelist answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[0];
                String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
                return SrmRestDoMocks.mockCodelist(urnSplited[0], urnSplited[1], urnSplited[2]);
            };
        });
    }

    private void mockRetrieveConceptSchemeByUrn() throws MetamacException {
        when(srmRestExternalFacade.retrieveConceptSchemeByUrn(any(String.class))).thenAnswer(new Answer<ConceptScheme>() {

            @Override
            public ConceptScheme answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[0];
                String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
                return SrmRestDoMocks.mockConceptScheme(urnSplited[0], urnSplited[1], urnSplited[2]);
            };
        });
    }

    private void mockRetrieveConceptByUrn() throws MetamacException {
        when(srmRestExternalFacade.retrieveConceptByUrn(any(String.class))).thenAnswer(new Answer<Concept>() {

            @Override
            public Concept answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[0];
                String[] urnSplited = UrnUtils.splitUrnItem(urn);
                return SrmRestDoMocks.mockConcept(urnSplited[0], urnSplited[1], urnSplited[2], urnSplited[3]);
            };
        });
    }

    private String getAgencyIdFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, DatasetVersionProperties
                .siemacMetadataStatisticalResource().maintainer().codeNested());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    private String getResourceIdFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, DatasetVersionProperties
                .siemacMetadataStatisticalResource().code());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    private String getVersionFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, DatasetVersionProperties
                .siemacMetadataStatisticalResource().versionLogic());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    @Override
    protected void resetMocks() throws MetamacException {
        datasetService = applicationContext.getBean(DatasetService.class);
        reset(datasetService);
        srmRestExternalFacade = applicationContext.getBean(SrmRestExternalFacade.class);
        reset(srmRestExternalFacade);

        mockFindDatasetsByCondition();
        mockRetrieveDatasetVersionDimensionsIds();
        mockRetrieveCoverageForDatasetVersionDimension();
        mockRetrieveDataStructureByUrn();
        mockRetrieveCodelistByUrn();
        mockRetrieveConceptSchemeByUrn();
        mockRetrieveConceptByUrn();
    }

    public String getDatasetUri(String agencyID, String resourceID, String version, String query, String limit, String offset) throws Exception {
        String uri = getResourceUri(RestExternalConstants.LINK_SUBPATH_DATASETS, agencyID, resourceID, version);
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_QUERY, RestUtils.encodeParameter(query));
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_LIMIT, RestUtils.encodeParameter(limit));
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_OFFSET, RestUtils.encodeParameter(offset));
        return uri.toString();
    }

}