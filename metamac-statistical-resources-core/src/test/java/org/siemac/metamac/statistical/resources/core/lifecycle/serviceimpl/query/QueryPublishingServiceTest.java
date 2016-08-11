package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesMockRestBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_97_NOT_VISIBLE_HAS_PART_NOT_VISIBLE_QUERY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_37_PREPARED_TO_PUBLISH_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_38_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_VERSION_VISIBLE_BEFORE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_39_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_VERSION_VISIBLE_AFTER_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_40_TO_PUBLISH_WITH_DATASET_VERSION_NOT_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_41_TO_PUBLISH_WITH_DATASET_WITH_NO_PUBLISHED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_42_TO_PUBLISH_WITH_DATASET_WITH_LAST_VERSION_NOT_PUBLISHED_PREVIOUS_COMPATIBLE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_43_TO_PUBLISH_WITH_DATASET_WITH_LAST_VERSION_NOT_PUBLISHED_PREVIOUS_INCOMPATIBLE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_44_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_BEFORE_QUERY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_45_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_AFTER_QUERY_NO_PREVIOUS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_46_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_AFTER_QUERY_PREVIOUS_COMPATIBLE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_47_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_AFTER_QUERY_PREVIOUS_INCOMPATIBLE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_52_NOT_VISIBLE_IS_PART_OF_NOT_VISIBLE_PUBLICATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_53_NOT_VISIBLE_IS_PART_OF_EMPTY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_55_PREPARED_TO_PUBLISH_STATUS_ACTIVE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_56_PREPARED_TO_PUBLISH_STATUS_DISCONTINUED_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/include/task-mockito.xml",
        "classpath:spring/statistical-resources/include/apis-locator-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class QueryPublishingServiceTest extends StatisticalResourcesMockRestBaseTest {

    @Autowired
    private QueryVersionRepository queryVersionRepository;

    @Autowired
    @Qualifier("queryLifecycleService")
    private LifecycleService<QueryVersion> queryLifecycleService;

    @Test
    @MetamacMock(QUERY_VERSION_37_PREPARED_TO_PUBLISH_NAME)
    public void testPublishQueryVersionLinkedPublishedDatasetVersion() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_37_PREPARED_TO_PUBLISH_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);

        queryVersion = this.queryVersionRepository.retrieveByUrn(queryVersionUrn);

        this.assertPublishingQueryVersion(queryVersion, null);
    }

    @Test
    @MetamacMock(QUERY_VERSION_55_PREPARED_TO_PUBLISH_STATUS_ACTIVE_NAME)
    public void testPublishQueryVersionStatusActive() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_55_PREPARED_TO_PUBLISH_STATUS_ACTIVE_NAME);

        String queryVersionUrn = queryVersion.getLifeCycleStatisticalResource().getUrn();
        this.mockLifecycleExternalItemsPublished(queryVersion.getLifeCycleStatisticalResource());

        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);
        queryVersion = this.queryVersionRepository.retrieveByUrn(queryVersionUrn);

        this.assertPublishingQueryVersion(queryVersion, null);
    }

    @Test
    @MetamacMock(QUERY_VERSION_56_PREPARED_TO_PUBLISH_STATUS_DISCONTINUED_NAME)
    public void testPublishQueryVersionStatusDiscontinued() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_56_PREPARED_TO_PUBLISH_STATUS_DISCONTINUED_NAME);

        String queryVersionUrn = queryVersion.getLifeCycleStatisticalResource().getUrn();
        this.mockLifecycleExternalItemsPublished(queryVersion.getLifeCycleStatisticalResource());

        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);
        queryVersion = this.queryVersionRepository.retrieveByUrn(queryVersionUrn);

        this.assertPublishingQueryVersion(queryVersion, null);
    }

    @Test
    @MetamacMock(QUERY_VERSION_38_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_VERSION_VISIBLE_BEFORE_NAME)
    public void testPublishQueryVersionLinkedPublishedDatasetVersionVisibleBeforeQuery() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_38_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_VERSION_VISIBLE_BEFORE_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);

        queryVersion = this.queryVersionRepository.retrieveByUrn(queryVersionUrn);

        this.assertPublishingQueryVersion(queryVersion, null);
    }

    @Test
    @MetamacMock(QUERY_VERSION_39_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_VERSION_VISIBLE_AFTER_NAME)
    public void testPublishQueryVersionLinkedPublishedDatasetVersionVisibleAfterQuery() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_39_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_VERSION_VISIBLE_AFTER_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();
        String datasetVersionUrn = queryVersion.getFixedDatasetVersion().getSiemacMetadataStatisticalResource().getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_VERSION_DATASET_VERSION_MUST_BE_PUBLISHED, datasetVersionUrn));
        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);
    }

    @Test
    @MetamacMock(QUERY_VERSION_40_TO_PUBLISH_WITH_DATASET_VERSION_NOT_PUBLISHED_NAME)
    public void testPublishQueryVersionLinkedNotPublishedDatasetVersion() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_40_TO_PUBLISH_WITH_DATASET_VERSION_NOT_PUBLISHED_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();
        String datasetVersionUrn = queryVersion.getFixedDatasetVersion().getSiemacMetadataStatisticalResource().getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_VERSION_DATASET_VERSION_MUST_BE_PUBLISHED, datasetVersionUrn));
        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);
    }

    @Test
    @MetamacMock(QUERY_VERSION_41_TO_PUBLISH_WITH_DATASET_WITH_NO_PUBLISHED_VERSION_NAME)
    public void testPublishQueryVersionLinkedDatasetWithNoPublishedVersion() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_41_TO_PUBLISH_WITH_DATASET_WITH_NO_PUBLISHED_VERSION_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();
        String datasetUrn = queryVersion.getDataset().getIdentifiableStatisticalResource().getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_VERSION_DATASET_WITH_NO_PUBLISHED_VERSION, datasetUrn));
        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);
    }

    @Test
    @MetamacMock(QUERY_VERSION_42_TO_PUBLISH_WITH_DATASET_WITH_LAST_VERSION_NOT_PUBLISHED_PREVIOUS_COMPATIBLE_NAME)
    public void testPublishQueryVersionLinkedDatasetWithLastVersionNotPublishedPreviousVersionCompatible() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_42_TO_PUBLISH_WITH_DATASET_WITH_LAST_VERSION_NOT_PUBLISHED_PREVIOUS_COMPATIBLE_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);

        queryVersion = this.queryVersionRepository.retrieveByUrn(queryVersionUrn);

        this.assertPublishingQueryVersion(queryVersion, null);
    }

    @Test
    @MetamacMock(QUERY_VERSION_43_TO_PUBLISH_WITH_DATASET_WITH_LAST_VERSION_NOT_PUBLISHED_PREVIOUS_INCOMPATIBLE_NAME)
    public void testPublishQueryVersionLinkedDatasetWithLastVersionNotPublishedPreviousVersionInCompatible() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_43_TO_PUBLISH_WITH_DATASET_WITH_LAST_VERSION_NOT_PUBLISHED_PREVIOUS_INCOMPATIBLE_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();
        String datasetUrn = queryVersion.getDataset().getIdentifiableStatisticalResource().getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_VERSION_NOT_COMPATIBLE_WITH_LAST_PUBLISHED_DATASET_VERSION, datasetUrn));
        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);
    }

    @Test
    @MetamacMock(QUERY_VERSION_44_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_BEFORE_QUERY_NAME)
    public void testPublishQueryVersionLinkedDatasetWithLastVersionPublishedVisibleBeforeQuery() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_44_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_BEFORE_QUERY_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);

        queryVersion = this.queryVersionRepository.retrieveByUrn(queryVersionUrn);

        this.assertPublishingQueryVersion(queryVersion, null);
    }

    @Test
    @MetamacMock(QUERY_VERSION_45_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_AFTER_QUERY_NO_PREVIOUS_NAME)
    public void testPublishQueryVersionLinkedDatasetWithLastVersionPublishedVisibleAfterQueryNoPreviousVersion() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_45_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_AFTER_QUERY_NO_PREVIOUS_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();
        String datasetUrn = queryVersion.getDataset().getIdentifiableStatisticalResource().getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_VERSION_DATASET_WITH_NO_PUBLISHED_VERSION, datasetUrn));
        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);
    }

    @Test
    @MetamacMock(QUERY_VERSION_46_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_AFTER_QUERY_PREVIOUS_COMPATIBLE_NAME)
    public void testPublishQueryVersionLinkedDatasetWithLastVersionPublishedVisibleAfterQueryPreviousVersionCompatible() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_46_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_AFTER_QUERY_PREVIOUS_COMPATIBLE_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);

        queryVersion = this.queryVersionRepository.retrieveByUrn(queryVersionUrn);

        this.assertPublishingQueryVersion(queryVersion, null);
    }

    @Test
    @MetamacMock(QUERY_VERSION_47_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_AFTER_QUERY_PREVIOUS_INCOMPATIBLE_NAME)
    public void testPublishQueryVersionLinkedDatasetWithLastVersionPublishedVisibleAfterQueryPreviousVersionIncompatible() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory
                .retrieveMock(QUERY_VERSION_47_TO_PUBLISH_WITH_FUTURE_DATE_WITH_DATASET_WITH_LAST_VERSION_VISIBLE_AFTER_QUERY_PREVIOUS_INCOMPATIBLE_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();
        String datasetUrn = queryVersion.getDataset().getIdentifiableStatisticalResource().getUrn();

        this.mockLifecycleExternalItemsPublished(lifeCycleStatisticalResource);

        this.expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_VERSION_NOT_COMPATIBLE_WITH_LAST_PUBLISHED_DATASET_VERSION, datasetUrn));
        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);
    }

    public void testPublishQueryVersionLinkedDatasetWithLastVersionPublished() throws Exception {

    }

    @Test
    @MetamacMock(QUERY_VERSION_37_PREPARED_TO_PUBLISH_NAME)
    public void testPublishQueryVersionExternalItemNotPublished() throws Exception {
        QueryVersion queryVersion = this.queryVersionMockFactory.retrieveMock(QUERY_VERSION_37_PREPARED_TO_PUBLISH_NAME);

        LifeCycleStatisticalResource lifeCycleStatisticalResource = queryVersion.getLifeCycleStatisticalResource();
        String queryVersionUrn = lifeCycleStatisticalResource.getUrn();

        this.mockLifecycleExternalItemsNotPublished(lifeCycleStatisticalResource);

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        exceptionItems.addAll(this.getExceptionItemsForExternalItemNotPublishedLifecycle(lifeCycleStatisticalResource, ServiceExceptionParameters.QUERY_VERSION, false));

        this.expectedMetamacException(new MetamacException(exceptionItems));

        this.queryLifecycleService.sendToPublished(this.getServiceContextAdministrador(), queryVersionUrn);
    }

    @Test
    @MetamacMock(QUERY_VERSION_52_NOT_VISIBLE_IS_PART_OF_NOT_VISIBLE_PUBLICATION_NAME)
    public void testCancelPublicationQueryVersionLinkedToNotVisiblePublication() throws Exception {
        String queryVersionUrn = this.getQueryVersionMockUrn(QUERY_VERSION_52_NOT_VISIBLE_IS_PART_OF_NOT_VISIBLE_PUBLICATION_NAME);
        String publicationVersionUrn = this.getPublicationVersionMockUrn(PUBLICATION_VERSION_97_NOT_VISIBLE_HAS_PART_NOT_VISIBLE_QUERY_NAME);

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_IS_PART_OF_NOT_VISIBLE_PUBLICATION, publicationVersionUrn));
        this.expectedMetamacException(new MetamacException(exceptionItems));

        this.queryLifecycleService.cancelPublication(this.getServiceContextAdministrador(), queryVersionUrn);
    }

    @Test
    @MetamacMock(QUERY_VERSION_53_NOT_VISIBLE_IS_PART_OF_EMPTY_NAME)
    public void testCancelPublicationQueryVersion() throws Exception {
        String queryVersionUrn = this.getQueryVersionMockUrn(QUERY_VERSION_53_NOT_VISIBLE_IS_PART_OF_EMPTY_NAME);

        this.queryLifecycleService.cancelPublication(this.getServiceContextAdministrador(), queryVersionUrn);

        QueryVersion queryVersion = this.queryVersionRepository.retrieveByUrn(queryVersionUrn);

        this.assertCancelPublicationQueryVersion(queryVersion, null);
    }

    // -------------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // -------------------------------------------------------------------------------------------
    private void assertCancelPublicationQueryVersion(QueryVersion current, QueryVersion previous) throws MetamacException {
        LifecycleAsserts.assertAutomaticallyFilledMetadataLifecycleCancelPublication(current, previous);
    }

    private void assertPublishingQueryVersion(QueryVersion current, QueryVersion previous) throws MetamacException {
        LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataLifecycleSendToPublished(current, previous);

        assertTrue(current.getFixedDatasetVersion() != null || current.getDataset() != null);
        assertNotNull(current.getStatus());
        assertTrue(current.getStatus().equals(QueryStatusEnum.ACTIVE) || current.getStatus().equals(QueryStatusEnum.DISCONTINUED));
    }
}
