package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_2;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_10_OBSERVATION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_11_OBSERVATION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_1_GLOBAL;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_2_GLOBAL;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_3_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_4_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_5_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_6_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_7_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_8_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_9_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.COLLECTION_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.COLLECTION_2_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.DATASET_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.DATASET_2_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.NOT_EXISTS;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_2_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.VERSION_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.VERSION_2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
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
import org.siemac.metamac.common.test.utils.ConditionalCriteriaUtils;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.constants.RestConstants;
import org.siemac.metamac.rest.structural_resources.v1_0.utils.CommonMetadataRestMocks;
import org.siemac.metamac.rest.structural_resources.v1_0.utils.RestDoMocks;
import org.siemac.metamac.rest.structural_resources.v1_0.utils.SrmRestMocks;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValue;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.publication.serviceapi.PublicationService;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.QueryService;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesUrnUtils;
import org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.invocation.CommonMetadataRestExternalFacade;
import org.siemac.metamac.statistical_resources.rest.external.invocation.SrmRestExternalFacade;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.service.StatisticalResourcesV1_0;
import org.springframework.context.ApplicationContext;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

public abstract class StatisticalResourcesRestExternalFacadeV10BaseTest extends MetamacRestBaseTest {

    private static String                     jaxrsServerAddress = "http://localhost:" + ServerResource.PORT + "/apis/statistical-resources";
    protected String                          baseApi            = jaxrsServerAddress + "/v1.0";
    protected static ApplicationContext       applicationContext = null;
    protected static StatisticalResourcesV1_0 statisticalResourcesRestExternalFacadeClientXml;
    private static String                     apiEndpointv10;

    protected static RestDoMocks              restDoMocks;

    private PublicationService                publicationService;
    private DatasetVersionRepository          datasetVersionRepository;
    private QueryVersionRepository            queryVersionRepository;
    private PublicationVersionRepository      publicationVersionRepository;
    private DatasetService                    datasetService;
    private QueryService                      queryService;

    // TODO habrá que cambiar el mock cuando se consuma a la api externa
    private SrmRestInternalService            srmRestInternalService;

    private SrmRestExternalFacade             srmRestExternalFacade;
    private CommonMetadataRestExternalFacade  commonMetadataRestExternalFacade;
    private DatasetRepositoriesServiceFacade  datasetRepositoriesServiceFacade;

    protected static List<String>             defaultLanguages   = Arrays.asList("es");

    @SuppressWarnings({"unchecked", "rawtypes"})
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Start server
        assertTrue("server did not launch correctly", launchServer(ServerResource.class, true));

        // Get application context from Jetty
        applicationContext = ApplicationContextProvider.getApplicationContext();
        StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks = StatisticalResourcesPersistedDoMocks.getInstance();
        restDoMocks = new RestDoMocks(statisticalResourcesPersistedDoMocks);

        // Rest clients
        // xml
        {
            List providers = new ArrayList();
            providers.add(applicationContext.getBean("jaxbProvider", JAXBElementProvider.class));
            statisticalResourcesRestExternalFacadeClientXml = JAXRSClientFactory.create(jaxrsServerAddress, StatisticalResourcesV1_0.class, providers, Boolean.TRUE);
        }
    }

    @Before
    public void setUp() throws Exception {
        ConfigurationService configurationService = applicationContext.getBean(ConfigurationService.class);
        apiEndpointv10 = configurationService.getProperty(ConfigurationConstants.ENDPOINT_STATISTICAL_RESOURCES_EXTERNAL_API) + "/v1.0";

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

    protected StatisticalResourcesV1_0 getStatisticalResourcesRestExternalFacadeClientXml() {
        WebClient.client(statisticalResourcesRestExternalFacadeClientXml).reset();
        WebClient.client(statisticalResourcesRestExternalFacadeClientXml).accept(APPLICATION_XML);
        return statisticalResourcesRestExternalFacadeClientXml;
    }

    protected String getRetrieveResourceUri(String resourcePath, String agencyID, String resourceID, String version, String fields, String langs) throws Exception {
        String uri = RestUtils.createLink(baseApi, resourcePath);
        if (agencyID != null) {
            uri = RestUtils.createLink(uri, agencyID);
            if (resourceID != null) {
                uri = RestUtils.createLink(uri, resourceID);
                if (version != null) {
                    uri = RestUtils.createLink(uri, version);
                }
            }
        }
        uri = RestUtils.createLinkWithQueryParam(uri, StatisticalResourcesRestExternalConstants.PARAMETER_FIELDS, RestUtils.encodeParameter(fields));
        uri = RestUtils.createLinkWithQueryParam(uri, StatisticalResourcesRestExternalConstants.PARAMETER_LANGS, RestUtils.encodeParameter(langs));
        return uri;
    }

    protected String getFindResourcesUri(String resourcePath, String agencyID, String resourceID, String query, String limit, String offset, String langs) throws Exception {
        String uri = RestUtils.createLink(baseApi, resourcePath);
        if (agencyID != null) {
            uri = RestUtils.createLink(uri, agencyID);
            if (resourceID != null) {
                uri = RestUtils.createLink(uri, resourceID);
            }
        }
        uri = RestUtils.createLinkWithQueryParam(uri, StatisticalResourcesRestExternalConstants.PARAMETER_LANGS, RestUtils.encodeParameter(langs));
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_QUERY, RestUtils.encodeParameter(query));
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_LIMIT, RestUtils.encodeParameter(limit));
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_OFFSET, RestUtils.encodeParameter(offset));
        return uri;
    }

    protected String getApiEndpoint() {
        return apiEndpointv10;
    }

    private void mockQueryVersionRepository() throws MetamacException {
        when(queryVersionRepository.retrieveLastVersion(any(String.class))).thenAnswer(new Answer<QueryVersion>() { // TODO retrieveLastPublishedVersion

                    @Override
                    public QueryVersion answer(InvocationOnMock invocation) throws Throwable {
                        String queryUrn = (String) invocation.getArguments()[0];
                        if (StringUtils.isBlank(queryUrn)) {
                            return null;
                        }
                        String[] queryUrnSplited = StatisticalResourcesUrnUtils.splitUrnQueryGlobal(queryUrn);
                        return restDoMocks.mockQueryVersion(queryUrnSplited[0], queryUrnSplited[1], VERSION_1);
                    };
                });

        when(queryVersionRepository.retrieveIsPartOf(any(QueryVersion.class))).thenAnswer(new Answer<List<RelatedResourceResult>>() {

            // TODO cambiar por retrieveIsPartOfOnlyPublishedVersion

            @Override
            public List<RelatedResourceResult> answer(InvocationOnMock invocation) throws Throwable {
                List<RelatedResourceResult> queries = new ArrayList<RelatedResourceResult>();
                queries.add(restDoMocks.mockPublicationRelatedResourceResult("agency01", "isPartOf01", "01.000"));
                queries.add(restDoMocks.mockPublicationRelatedResourceResult("agency01", "isPartOf02", "01.000"));
                return queries;
            };
        });

    }

    @SuppressWarnings("unchecked")
    private void mockFindCollectionsByCondition() throws MetamacException {
        when(publicationService.findPublicationVersionsByCondition(any(ServiceContext.class), any(List.class), any(PagingParameter.class))).thenAnswer(new Answer<PagedResult<PublicationVersion>>() {

            @Override
            public org.fornax.cartridges.sculptor.framework.domain.PagedResult<PublicationVersion> answer(InvocationOnMock invocation) throws Throwable {
                List<ConditionalCriteria> conditions = (List<ConditionalCriteria>) invocation.getArguments()[1];

                String agencyID = getAgencyIdFromConditionalCriteria(conditions);
                String resourceID = getResourceIdFromConditionalCriteria(conditions);
                String version = getVersionFromConditionalCriteria(conditions);

                if (agencyID != null && resourceID != null && version != null) {
                    // Retrieve one
                    PublicationVersion dataset = null;
                    if (NOT_EXISTS.equals(agencyID) || NOT_EXISTS.equals(resourceID) || NOT_EXISTS.equals(version)) {
                        dataset = null;
                    } else if (AGENCY_1.equals(agencyID) && COLLECTION_1_CODE.equals(resourceID) && VERSION_1.equals(version)) {
                        dataset = restDoMocks.mockPublicationVersion(agencyID, resourceID, version);
                    } else {
                        fail();
                    }
                    List<PublicationVersion> publications = new ArrayList<PublicationVersion>();
                    if (dataset != null) {
                        publications.add(dataset);
                    }
                    return new PagedResult<PublicationVersion>(publications, 0, publications.size(), publications.size());
                } else {
                    // any
                    List<PublicationVersion> publications = new ArrayList<PublicationVersion>();
                    publications.add(restDoMocks.mockPublicationVersion(AGENCY_1, COLLECTION_1_CODE, VERSION_1));
                    publications.add(restDoMocks.mockPublicationVersion(AGENCY_1, COLLECTION_1_CODE, VERSION_2));
                    publications.add(restDoMocks.mockPublicationVersion(AGENCY_2, COLLECTION_1_CODE, VERSION_1));
                    publications.add(restDoMocks.mockPublicationVersion(AGENCY_1, COLLECTION_2_CODE, VERSION_1));
                    return new PagedResult<PublicationVersion>(publications, publications.size(), publications.size(), publications.size(), publications.size() * 10, 0);
                }
            };
        });
    }

    private void mockDatasetVersionRepository() throws MetamacException {

        when(datasetVersionRepository.retrieveLastVersion(any(String.class))).thenAnswer(new Answer<DatasetVersion>() { // TODO retrieveLastPublishedVersion

                    @Override
                    public DatasetVersion answer(InvocationOnMock invocation) throws Throwable {
                        String datasetUrn = (String) invocation.getArguments()[0];
                        String[] datasetUrnSplited = StatisticalResourcesUrnUtils.splitUrnDatasetGlobal(datasetUrn);
                        return restDoMocks.mockDatasetVersion(datasetUrnSplited[0], datasetUrnSplited[1], VERSION_1);
                    };
                });
        when(datasetVersionRepository.retrieveIsRequiredBy(any(DatasetVersion.class))).thenAnswer(new Answer<List<RelatedResourceResult>>() {

            // TODO cambiar por retrieveIsRequiredByOnlyPublishedVersion

            @Override
            public List<RelatedResourceResult> answer(InvocationOnMock invocation) throws Throwable {
                List<RelatedResourceResult> queries = new ArrayList<RelatedResourceResult>();
                queries.add(restDoMocks.mockQueryRelatedResourceResult("agency01", "isRequiredBy01", "01.000"));
                queries.add(restDoMocks.mockQueryRelatedResourceResult("agency02", "isRequiredBy02", "01.000"));
                return queries;
            };
        });

        when(datasetVersionRepository.retrieveIsReplacedByVersion(any(DatasetVersion.class))).thenAnswer(new Answer<RelatedResourceResult>() {

            // TODO retrieveIsReplacedByVersionOnlyPublishedVersion

            @Override
            public RelatedResourceResult answer(InvocationOnMock invocation) throws Throwable {
                DatasetVersion datasetVersion = (DatasetVersion) invocation.getArguments()[0];
                return restDoMocks.mockDatasetRelatedResourceResult(datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested(), datasetVersion
                        .getSiemacMetadataStatisticalResource().getCode(), "02.000");
            };
        });

        when(datasetVersionRepository.retrieveIsReplacedBy(any(DatasetVersion.class))).thenAnswer(new Answer<RelatedResourceResult>() {

            // TODO retrieveIsReplacedByOnlyPublishedVersion

            @Override
            public RelatedResourceResult answer(InvocationOnMock invocation) throws Throwable {
                return restDoMocks.mockDatasetRelatedResourceResult("agency01", "dataset99", "01.000");
            };
        });

        when(datasetVersionRepository.retrieveIsPartOf(any(DatasetVersion.class))).thenAnswer(new Answer<List<RelatedResourceResult>>() {

            // TODO cambiar por retrieveIsPartOfOnlyPublishedVersion

            @Override
            public List<RelatedResourceResult> answer(InvocationOnMock invocation) throws Throwable {
                List<RelatedResourceResult> queries = new ArrayList<RelatedResourceResult>();
                queries.add(restDoMocks.mockPublicationRelatedResourceResult("agency01", "isPartOf01", "01.000"));
                queries.add(restDoMocks.mockPublicationRelatedResourceResult("agency01", "isPartOf02", "01.000"));
                return queries;
            };
        });

    }

    private void mockPublicationVersionRepository() throws MetamacException {
        when(publicationVersionRepository.retrieveLastVersion(any(String.class))).thenAnswer(new Answer<PublicationVersion>() { // TODO retrieveLastPublishedVersion

                    @Override
                    public PublicationVersion answer(InvocationOnMock invocation) throws Throwable {
                        String publicationUrn = (String) invocation.getArguments()[0];
                        if (StringUtils.isBlank(publicationUrn)) {
                            return null;
                        }
                        String[] publicationUrnSplited = StatisticalResourcesUrnUtils.splitUrnPublicationGlobal(publicationUrn);
                        return restDoMocks.mockPublicationVersion(publicationUrnSplited[0], publicationUrnSplited[1], VERSION_1);
                    };
                });

        when(publicationVersionRepository.retrieveIsReplacedBy(any(PublicationVersion.class))).thenAnswer(new Answer<RelatedResourceResult>() {

            // TODO retrieveIsReplacedByOnlyPublishedVersion

            @Override
            public RelatedResourceResult answer(InvocationOnMock invocation) throws Throwable {
                return restDoMocks.mockPublicationRelatedResourceResult("agency01", "collection99", "01.000");
            };
        });
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
                        dataset = restDoMocks.mockDatasetVersion(agencyID, resourceID, version);
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
                    datasets.add(restDoMocks.mockDatasetVersion(AGENCY_1, DATASET_1_CODE, VERSION_1));
                    datasets.add(restDoMocks.mockDatasetVersion(AGENCY_1, DATASET_1_CODE, VERSION_2));
                    datasets.add(restDoMocks.mockDatasetVersion(AGENCY_2, DATASET_1_CODE, VERSION_1));
                    datasets.add(restDoMocks.mockDatasetVersion(AGENCY_1, DATASET_2_CODE, VERSION_1));
                    return new PagedResult<DatasetVersion>(datasets, datasets.size(), datasets.size(), datasets.size(), datasets.size() * 10, 0);
                }
            };
        });
    }

    @SuppressWarnings("unchecked")
    private void mockFindQueriesByCondition() throws MetamacException {
        when(queryService.findQueryVersionsByCondition(any(ServiceContext.class), any(List.class), any(PagingParameter.class))).thenAnswer(new Answer<PagedResult<QueryVersion>>() {

            @Override
            public org.fornax.cartridges.sculptor.framework.domain.PagedResult<QueryVersion> answer(InvocationOnMock invocation) throws Throwable {
                List<ConditionalCriteria> conditions = (List<ConditionalCriteria>) invocation.getArguments()[1];

                String agencyID = getAgencyIdFromConditionalCriteria(conditions);
                String resourceID = getResourceIdFromConditionalCriteria(conditions);
                String version = getVersionFromConditionalCriteria(conditions);

                if (agencyID != null && resourceID != null && version != null) {
                    // Retrieve one
                    QueryVersion queryVersion = null;
                    if (NOT_EXISTS.equals(agencyID) || NOT_EXISTS.equals(resourceID) || NOT_EXISTS.equals(version)) {
                        queryVersion = null;
                    } else if (AGENCY_1.equals(agencyID) && QUERY_1_CODE.equals(resourceID) && VERSION_1.equals(version)) {
                        queryVersion = restDoMocks.mockQueryVersion(agencyID, resourceID, version);
                    } else {
                        fail();
                    }
                    List<QueryVersion> queries = new ArrayList<QueryVersion>();
                    if (queryVersion != null) {
                        queries.add(queryVersion);
                    }
                    return new PagedResult<QueryVersion>(queries, 0, queries.size(), queries.size());
                } else {
                    // any
                    List<QueryVersion> queries = new ArrayList<QueryVersion>();
                    queries.add(restDoMocks.mockQueryVersion(AGENCY_1, QUERY_1_CODE, VERSION_1));
                    queries.add(restDoMocks.mockQueryVersion(AGENCY_1, QUERY_1_CODE, VERSION_2));
                    queries.add(restDoMocks.mockQueryVersion(AGENCY_2, QUERY_1_CODE, VERSION_1));
                    queries.add(restDoMocks.mockQueryVersion(AGENCY_1, QUERY_2_CODE, VERSION_1));
                    return new PagedResult<QueryVersion>(queries, queries.size(), queries.size(), queries.size(), queries.size() * 10, 0);
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
                if ("GEO_DIM".equals(componentId)) {
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "santa-cruz-tenerife"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "tenerife"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "la-laguna"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "santa-cruz"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "la-palma"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "santa-cruz-la-palma"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "los-llanos-de-aridane"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "la-gomera"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "el-hierro"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "las-palmas-gran-canaria"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "gran-canaria"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "fuerteventura"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "lanzarote"));
                } else if ("measure01".equals(componentId)) {
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, componentId + "-conceptScheme01-concept01"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, componentId + "-conceptScheme01-concept02"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, componentId + "-conceptScheme01-concept05"));
                } else if ("TIME_PERIOD".equals(componentId)) {
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "2011"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "2012"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "2013"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, "2014"));
                } else if ("dim01".equals(componentId)) {
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, componentId + "-codelist01-code01"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, componentId + "-codelist01-code03"));
                    codeDimensions.add(restDoMocks.mockCodeDimension(componentId, componentId + "-codelist01-code04"));

                } else {
                    fail(componentId + " unexpected");
                }
                return codeDimensions;
            };
        });
    }

    private void mockRetrieveCoverageForDatasetVersionAttribute() throws MetamacException {
        when(datasetService.retrieveCoverageForDatasetVersionAttribute(any(ServiceContext.class), any(String.class), any(String.class))).thenAnswer(new Answer<List<AttributeValue>>() {

            @Override
            public List<AttributeValue> answer(InvocationOnMock invocation) throws Throwable {
                String componentId = (String) invocation.getArguments()[2];
                List<AttributeValue> attributeValues = new ArrayList<AttributeValue>();
                if ("at2".equals(componentId)) {
                    attributeValues.add(restDoMocks.mockAttributeValue(componentId, "A"));
                    attributeValues.add(restDoMocks.mockAttributeValue(componentId, "C"));
                } else if ("at6".equals(componentId)) {
                    attributeValues.add(restDoMocks.mockAttributeValue(componentId, "1"));
                    attributeValues.add(restDoMocks.mockAttributeValue(componentId, "2"));
                    attributeValues.add(restDoMocks.mockAttributeValue(componentId, "3"));
                } else {
                    // any. They must not be in response, because they are non-enumerated
                    attributeValues.add(restDoMocks.mockAttributeValue(componentId, "skip-1"));
                    attributeValues.add(restDoMocks.mockAttributeValue(componentId, "skip-2"));
                    attributeValues.add(restDoMocks.mockAttributeValue(componentId, "skip-3"));
                }
                return attributeValues;
            };
        });
    }

    @SuppressWarnings("unchecked")
    private void mockFindObservationsExtendedByDimensions() throws Exception {
        when(datasetRepositoriesServiceFacade.findObservationsExtendedByDimensions(any(String.class), any(List.class))).thenAnswer(new Answer<Map<String, ObservationExtendedDto>>() {

            @Override
            public Map<String, ObservationExtendedDto> answer(InvocationOnMock invocation) throws Throwable {

                List<String> geoDimensionValues = Arrays.asList("santa-cruz-tenerife", "tenerife", "la-laguna", "santa-cruz", "la-palma", "santa-cruz-la-palma", "los-llanos-de-aridane", "la-gomera",
                        "el-hierro", "las-palmas-gran-canaria", "gran-canaria", "fuerteventura", "lanzarote");
                List<String> timeDimensionValues = Arrays.asList("2011", "2012", "2013", "2014");
                List<String> measureDimensionValues = Arrays.asList("measure01-conceptScheme01-concept01", "measure01-conceptScheme01-concept02", "measure01-conceptScheme01-concept05");
                List<String> dimension1DimensionValues = Arrays.asList("dim01-codelist01-code01", "dim01-codelist01-code03", "dim01-codelist01-code04");
                Map<String, ObservationExtendedDto> observationsMap = new HashMap<String, ObservationExtendedDto>();
                for (String geoDimensionValue : geoDimensionValues) {
                    for (String timeDimensionValue : timeDimensionValues) {
                        for (String measureDimensionValue : measureDimensionValues) {
                            for (String dimension1DimensionValue : dimension1DimensionValues) {
                                ObservationExtendedDto observation = restDoMocks.mockObservation(geoDimensionValue, timeDimensionValue, measureDimensionValue, dimension1DimensionValue,
                                        observationsMap.size() + 1);
                                observationsMap.put(observation.getUniqueKey(), observation);
                            }
                        }
                    }
                }
                // Test one observation empty
                observationsMap.remove("santa-cruz-tenerife#2011#measure01-conceptScheme01-concept01#dim01-codelist01-code04");

                // Attributes
                int i = 1;
                observationsMap.get("santa-cruz-tenerife#2011#measure01-conceptScheme01-concept01#dim01-codelist01-code01").addAttribute(
                        restDoMocks.mockAttributeInstanceObservation(ATTRIBUTE_10_OBSERVATION, "Value " + i++));
                observationsMap.get("santa-cruz-tenerife#2011#measure01-conceptScheme01-concept01#dim01-codelist01-code03").addAttribute(
                        restDoMocks.mockAttributeInstanceObservation(ATTRIBUTE_10_OBSERVATION, "Value " + i++));
                observationsMap.get("tenerife#2011#measure01-conceptScheme01-concept01#dim01-codelist01-code01").addAttribute(
                        restDoMocks.mockAttributeInstanceObservation(ATTRIBUTE_10_OBSERVATION, "Value " + i++));
                observationsMap.get("lanzarote#2014#measure01-conceptScheme01-concept05#dim01-codelist01-code04").addAttribute(
                        restDoMocks.mockAttributeInstanceObservation(ATTRIBUTE_10_OBSERVATION, "Value " + i++));

                observationsMap.get("santa-cruz-tenerife#2011#measure01-conceptScheme01-concept01#dim01-codelist01-code01").addAttribute(
                        restDoMocks.mockAttributeInstanceObservation(ATTRIBUTE_11_OBSERVATION, "Value " + i++));
                observationsMap.get("tenerife#2011#measure01-conceptScheme01-concept01#dim01-codelist01-code01").addAttribute(
                        restDoMocks.mockAttributeInstanceObservation(ATTRIBUTE_11_OBSERVATION, "Value " + i++));
                observationsMap.get("la-laguna#2011#measure01-conceptScheme01-concept01#dim01-codelist01-code01").addAttribute(
                        restDoMocks.mockAttributeInstanceObservation(ATTRIBUTE_11_OBSERVATION, "Value " + i++));

                // internal attribute -> do not return
                observationsMap.get("la-laguna#2011#measure01-conceptScheme01-concept01#dim01-codelist01-code01").addAttribute(
                        restDoMocks.mockAttributeInstanceObservation("internalAttribute01", "Value " + i++));
                return observationsMap;
            };
        });
    }

    private void mockFindAttributesInstancesWithDatasetAttachmentLevel() throws Exception {
        when(datasetRepositoriesServiceFacade.findAttributesInstancesWithDatasetAttachmentLevel(any(String.class), any(String.class))).thenAnswer(new Answer<List<AttributeInstanceDto>>() {

            @Override
            public List<AttributeInstanceDto> answer(InvocationOnMock invocation) throws Throwable {
                String attributeId = (String) invocation.getArguments()[1];
                List<AttributeInstanceDto> attributes = new ArrayList<AttributeInstanceDto>();
                if (ATTRIBUTE_1_GLOBAL.equals(attributeId)) {
                    String value = "Value " + attributeId;
                    attributes.add(restDoMocks.mockAttributeInstanceWithDatasetAttachmentLevel(attributeId, value));
                } else if (ATTRIBUTE_2_GLOBAL.equals(attributeId)) {
                    String value = "Value | " + attributeId;
                    attributes.add(restDoMocks.mockAttributeInstanceWithDatasetAttachmentLevel(attributeId, value));
                } else {
                    fail("Attribute " + attributeId + " unsupported");
                }
                return attributes;
            };
        });
    }

    @SuppressWarnings("unchecked")
    private void mockFindAttributesInstancesWithDimensionAttachmentLevelDenormalized() throws Exception {
        when(datasetRepositoriesServiceFacade.findAttributesInstancesWithDimensionAttachmentLevelDenormalized(any(String.class), any(String.class), any(Map.class))).thenAnswer(
                new Answer<List<AttributeInstanceDto>>() {

                    @Override
                    public List<AttributeInstanceDto> answer(InvocationOnMock invocation) throws Throwable {
                        String attributeId = (String) invocation.getArguments()[1];
                        String value = "Value " + attributeId;
                        List<AttributeInstanceDto> attributes = new ArrayList<AttributeInstanceDto>();
                        int i = 1;
                        if (ATTRIBUTE_3_DIMENSION.equals(attributeId)) {
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", null, null,
                                    null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "la-laguna", null, null, null, null,
                                    null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "el-hierro", null, null, null, null,
                                    null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "lanzarote", null, null, null, null,
                                    null, null));
                        } else if (ATTRIBUTE_4_DIMENSION.equals(attributeId)) {
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "dim01",
                                    "dim01-codelist01-code01", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "la-laguna", "dim01",
                                    "dim01-codelist01-code01", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "el-hierro", "dim01",
                                    "dim01-codelist01-code01", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "dim01",
                                    "dim01-codelist01-code03", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "lanzarote", "dim01",
                                    "dim01-codelist01-code03", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "lanzarote", "dim01",
                                    "dim01-codelist01-code04", null, null, null, null));
                        } else if (ATTRIBUTE_5_DIMENSION.equals(attributeId)) {
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2011", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2012", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2013", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2014", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "tenerife", "TIME_PERIOD", "2012",
                                    null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "la-laguna", "TIME_PERIOD", "2012",
                                    null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "fuerteventura", "TIME_PERIOD", "2011",
                                    null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "fuerteventura", "TIME_PERIOD", "2012",
                                    null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "fuerteventura", "TIME_PERIOD", "2013",
                                    null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "fuerteventura", "TIME_PERIOD", "2014",
                                    null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "lanzarote", "TIME_PERIOD", "2011",
                                    null, null, null, null));
                        } else if (ATTRIBUTE_6_DIMENSION.equals(attributeId)) {
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2011", "measure01", "measure01-conceptScheme01-concept01", null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "la-laguna", "TIME_PERIOD", "2011",
                                    "measure01", "measure01-conceptScheme01-concept01", null, null));
                        } else if (ATTRIBUTE_7_DIMENSION.equals(attributeId)) {
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2011", "measure01", "measure01-conceptScheme01-concept01", "dim01", "dim01-codelist01-code01"));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2011", "measure01", "measure01-conceptScheme01-concept01", "dim01", "dim01-codelist01-code03"));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "lanzarote", "TIME_PERIOD", "2014",
                                    "measure01", "measure01-conceptScheme01-concept05", "dim01", "dim01-codelist01-code04"));
                        } else if (ATTRIBUTE_8_DIMENSION.equals(attributeId)) {
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2011", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2014", null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "tenerife", "TIME_PERIOD", "2012",
                                    null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "la-laguna", "TIME_PERIOD", "2012",
                                    null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "fuerteventura", "TIME_PERIOD", "2014",
                                    null, null, null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "lanzarote", "TIME_PERIOD", "2011",
                                    null, null, null, null));
                        } else if (ATTRIBUTE_9_DIMENSION.equals(attributeId)) {
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2011", "dim01", "dim01-codelist01-code01", null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "santa-cruz-tenerife", "TIME_PERIOD",
                                    "2011", "dim01", "dim01-codelist01-code03", null, null));
                            attributes.add(restDoMocks.mockAttributeInstanceWithDimensionAttachmentLevelDenormalized(attributeId, value + "-" + i++, "GEO_DIM", "lanzarote", "TIME_PERIOD", "2014",
                                    "dim01", "dim01-codelist01-code04", null, null));
                        } else {
                            fail("Attribute " + attributeId + " unsupported");
                        }
                        return attributes;
                    };
                });
    }
    private void mockRetrieveDataStructureByUrn() throws MetamacException {
        when(srmRestExternalFacade.retrieveDataStructureByUrn(any(String.class))).thenAnswer(new Answer<DataStructure>() {

            @Override
            public DataStructure answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[0];
                String[] urnSplited = UrnUtils.splitUrnStructure(urn);
                return SrmRestMocks.mockDataStructure(urnSplited[0], urnSplited[1], urnSplited[2]);
            };
        });
    }

    private void mockRetrieveCodelistByUrn() throws MetamacException {
        when(srmRestExternalFacade.retrieveCodelistByUrn(any(String.class))).thenAnswer(new Answer<Codelist>() {

            @Override
            public Codelist answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[0];
                String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
                return SrmRestMocks.mockCodelist(urnSplited[0], urnSplited[1], urnSplited[2]);
            };
        });
    }

    private void mockRetrieveCodesByCodelistUrn() throws MetamacException {
        when(srmRestExternalFacade.retrieveCodesByCodelistUrn(any(String.class), any(String.class), any(String.class))).thenAnswer(new Answer<Codes>() {

            @Override
            public Codes answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[0];
                String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
                return SrmRestMocks.mockCodesByCodelist(urnSplited[0], urnSplited[1], urnSplited[2]);
            };
        });
    }

    private void mockRetrieveConceptsByConceptSchemeUrn() throws MetamacException {
        when(srmRestExternalFacade.retrieveConceptsByConceptSchemeByUrn(any(String.class))).thenAnswer(new Answer<Concepts>() {

            @Override
            public Concepts answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[0];
                String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
                return SrmRestMocks.mockConceptsByConceptScheme(urnSplited[0], urnSplited[1], urnSplited[2]);
            };
        });
    }

    private void mockRetrieveConceptByUrn() throws MetamacException {
        when(srmRestExternalFacade.retrieveConceptByUrn(any(String.class))).thenAnswer(new Answer<Concept>() {

            @Override
            public Concept answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[0];
                String[] urnSplited = UrnUtils.splitUrnItem(urn);
                return SrmRestMocks.mockConcept(urnSplited[0], urnSplited[1], urnSplited[2], urnSplited[3]);
            };
        });
        when(srmRestInternalService.retrieveConceptByUrn(any(String.class))).thenAnswer(new Answer<Concept>() {

            @Override
            public Concept answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[0];
                String[] urnSplited = UrnUtils.splitUrnItem(urn);
                return SrmRestMocks.mockConcept(urnSplited[0], urnSplited[1], urnSplited[2], urnSplited[3]);
            };
        });
    }

    private void mockRetrieveConfigurationById() throws MetamacException {
        when(commonMetadataRestExternalFacade.retrieveConfiguration(any(String.class))).thenAnswer(new Answer<Configuration>() {

            @Override
            public Configuration answer(InvocationOnMock invocation) throws Throwable {
                return CommonMetadataRestMocks.mockConfiguration("configuration01");
            };
        });
    }

    private String getAgencyIdFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        // can use PublicationVersionProperties or DatasetVersionProperties...
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, PublicationVersionProperties
                .siemacMetadataStatisticalResource().maintainer().codeNested());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    private String getResourceIdFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        // can use PublicationVersionProperties or DatasetVersionProperties...
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, PublicationVersionProperties
                .siemacMetadataStatisticalResource().code());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    private String getVersionFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        // can use PublicationVersionProperties or DatasetVersionProperties...
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, PublicationVersionProperties
                .siemacMetadataStatisticalResource().versionLogic());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    private void resetMocks() throws Exception {
        datasetService = applicationContext.getBean(DatasetService.class);
        reset(datasetService);
        publicationService = applicationContext.getBean(PublicationService.class);
        reset(publicationService);
        queryService = applicationContext.getBean(QueryService.class);
        reset(queryService);

        queryVersionRepository = applicationContext.getBean(QueryVersionRepository.class);
        reset(queryVersionRepository);
        datasetVersionRepository = applicationContext.getBean(DatasetVersionRepository.class);
        reset(datasetVersionRepository);
        publicationVersionRepository = applicationContext.getBean(PublicationVersionRepository.class);
        reset(publicationVersionRepository);

        srmRestExternalFacade = applicationContext.getBean(SrmRestExternalFacade.class);
        reset(srmRestExternalFacade);
        srmRestInternalService = applicationContext.getBean(SrmRestInternalService.class);
        reset(srmRestInternalService);
        datasetRepositoriesServiceFacade = applicationContext.getBean(DatasetRepositoriesServiceFacade.class);
        reset(datasetRepositoriesServiceFacade);

        commonMetadataRestExternalFacade = applicationContext.getBean(CommonMetadataRestExternalFacade.class);
        reset(commonMetadataRestExternalFacade);

        mockFindDatasetsByCondition();
        mockRetrieveDatasetVersionDimensionsIds();
        mockRetrieveCoverageForDatasetVersionDimension();
        mockRetrieveCoverageForDatasetVersionAttribute();
        mockFindObservationsExtendedByDimensions();
        mockFindAttributesInstancesWithDatasetAttachmentLevel();
        mockFindAttributesInstancesWithDimensionAttachmentLevelDenormalized();
        mockDatasetVersionRepository();
        mockPublicationVersionRepository();

        mockRetrieveDataStructureByUrn();
        mockRetrieveCodelistByUrn();
        mockRetrieveCodesByCodelistUrn();
        mockRetrieveConceptsByConceptSchemeUrn();
        mockRetrieveConceptByUrn();

        mockFindCollectionsByCondition();

        mockFindQueriesByCondition();
        mockQueryVersionRepository();

        mockRetrieveConfigurationById();
    }
}