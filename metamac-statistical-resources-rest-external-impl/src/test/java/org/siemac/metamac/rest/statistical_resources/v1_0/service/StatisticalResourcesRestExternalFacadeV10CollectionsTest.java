package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_2;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.COLLECTION_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.COLLECTION_2_CODE;
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
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.siemac.metamac.common.test.utils.ConditionalCriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.constants.RestConstants;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.publication.serviceapi.PublicationService;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesUrnUtils;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;

public class StatisticalResourcesRestExternalFacadeV10CollectionsTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    private PublicationService       publicationService;
    private DatasetVersionRepository datasetVersionRepository;
    private QueryVersionRepository   queryVersionRepository;

    @Test
    public void testRetrieveCollectionXml() throws Exception {
        String requestBase = getCollectionUri(AGENCY_1, COLLECTION_1_CODE, VERSION_1, null, null, null);
        String[] requestUris = new String[]{requestBase, requestBase + ".xml", requestBase + "?_type=xml"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10CollectionsTest.class.getResourceAsStream("/responses/collections/retrieveCollection.id1.xml");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Ignore
    // TODO testRetrievePublicationJson
    @Test
    public void testRetrieveCollectionJson() throws Exception {

    }

    @Ignore
    // TODO testRetrieveCollectionErrorNotExists
    @Test
    public void testRetrieveCollectionErrorNotExists() throws Exception {

    }

    @Ignore
    // TODO testRetrieveCollectionErrorNotExistsXml
    @Test
    public void testRetrieveCollectionErrorNotExistsXml() throws Exception {
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

    private void mockRetrieveDatasetLastPublishedVersion() throws MetamacException {
        when(datasetVersionRepository.retrieveLastVersion(any(String.class))).thenAnswer(new Answer<DatasetVersion>() { // TODO retrieveLastPublishedVersion

                    @Override
                    public DatasetVersion answer(InvocationOnMock invocation) throws Throwable {
                        String datasetUrn = (String) invocation.getArguments()[0];
                        String[] datasetUrnSplited = StatisticalResourcesUrnUtils.splitUrnDatasetGlobal(datasetUrn);
                        return restDoMocks.mockDatasetVersion(datasetUrnSplited[0], datasetUrnSplited[1], VERSION_1);
                    };
                });
    }

    private void mockRetrieveQueryLastPublishedVersion() throws MetamacException {
        when(queryVersionRepository.retrieveLastVersion(any(String.class))).thenAnswer(new Answer<QueryVersion>() { // TODO retrieveLastPublishedVersion

                    @Override
                    public QueryVersion answer(InvocationOnMock invocation) throws Throwable {
                        String queryUrn = (String) invocation.getArguments()[0];
                        String[] queryUrnSplited = StatisticalResourcesUrnUtils.splitUrnQueryGlobal(queryUrn);
                        return restDoMocks.mockQueryVersion(queryUrnSplited[0], queryUrnSplited[1], VERSION_1);
                    };
                });
    }

    private String getAgencyIdFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, PublicationVersionProperties
                .siemacMetadataStatisticalResource().maintainer().codeNested());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    private String getResourceIdFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, PublicationVersionProperties
                .siemacMetadataStatisticalResource().code());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    private String getVersionFromConditionalCriteria(List<ConditionalCriteria> conditions) {
        ConditionalCriteria conditionalCriteria = ConditionalCriteriaUtils.getConditionalCriteriaByPropertyName(conditions, Operator.Equal, PublicationVersionProperties
                .siemacMetadataStatisticalResource().versionLogic());
        return conditionalCriteria != null ? (String) conditionalCriteria.getFirstOperant() : null;
    }

    @Override
    protected void resetMocks() throws Exception {
        publicationService = applicationContext.getBean(PublicationService.class);
        reset(publicationService);
        datasetVersionRepository = applicationContext.getBean(DatasetVersionRepository.class);
        reset(datasetVersionRepository);
        queryVersionRepository = applicationContext.getBean(QueryVersionRepository.class);
        reset(queryVersionRepository);

        mockFindCollectionsByCondition();
        mockRetrieveDatasetLastPublishedVersion();
        mockRetrieveQueryLastPublishedVersion();
    }

    public String getCollectionUri(String agencyID, String resourceID, String version, String query, String limit, String offset) throws Exception {
        String uri = getResourceUri(RestExternalConstants.LINK_SUBPATH_COLLECTIONS, agencyID, resourceID, version);
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_QUERY, RestUtils.encodeParameter(query));
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_LIMIT, RestUtils.encodeParameter(limit));
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_OFFSET, RestUtils.encodeParameter(offset));
        return uri.toString();
    }

}