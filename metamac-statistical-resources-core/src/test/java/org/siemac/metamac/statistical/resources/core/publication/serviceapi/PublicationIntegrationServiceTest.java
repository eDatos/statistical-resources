package org.siemac.metamac.statistical.resources.core.publication.serviceapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class PublicationIntegrationServiceTest extends StatisticalResourcesBaseTest {

    @Autowired
    private PublicationService publicationService;

    // ------------------------------------------------------------------------
    // PUBLICATIONS
    // ------------------------------------------------------------------------

    @Test
    public void testCreateAndUpdatePublicationMustHaveFilledStatisticalOperation() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        PublicationVersion publicationBeforeCreate = notPersistedDoMocks.mockPublicationVersion();
        assertNull(publicationBeforeCreate.getPublication());

        PublicationVersion publicationAfterCreate = publicationService.createPublicationVersion(getServiceContextAdministrador(), publicationBeforeCreate, statisticalOperation);
        assertNotNull(publicationAfterCreate.getPublication().getIdentifiableStatisticalResource().getStatisticalOperation());

        PublicationVersion publicationAfterUpdate = publicationService.updatePublicationVersion(getServiceContextAdministrador(), publicationAfterCreate);
        assertNotNull(publicationAfterUpdate.getPublication().getIdentifiableStatisticalResource().getStatisticalOperation());

        CommonAsserts.assertEqualsExternalItem(publicationAfterCreate.getPublication().getIdentifiableStatisticalResource().getStatisticalOperation(), publicationAfterUpdate.getPublication()
                .getIdentifiableStatisticalResource().getStatisticalOperation());
    }

    // ------------------------------------------------------------------------
    // PUBLICATIONS VERSIONS
    // ------------------------------------------------------------------------

    @Test
    public void testCreateAndUpdatePublicationVersionMustHaveFilledStatisticalOperation() throws Exception {
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        PublicationVersion publicationBeforeCreate = notPersistedDoMocks.mockPublicationVersion();
        assertNull(publicationBeforeCreate.getSiemacMetadataStatisticalResource().getStatisticalOperation());

        PublicationVersion publicationAfterCreate = publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), publicationBeforeCreate, statisticalOperation);
        assertNotNull(publicationAfterCreate.getSiemacMetadataStatisticalResource().getStatisticalOperation());

        PublicationVersion publicationAfterUpdate = publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), publicationAfterCreate);
        assertNotNull(publicationAfterUpdate.getSiemacMetadataStatisticalResource().getStatisticalOperation());

        CommonAsserts.assertEqualsExternalItem(publicationAfterCreate.getSiemacMetadataStatisticalResource().getStatisticalOperation(), publicationAfterUpdate.getSiemacMetadataStatisticalResource()
                .getStatisticalOperation());
    }

    // ------------------------------------------------------------------------
    // CHAPTERS
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME)
    public void testCreateAndUpdateChapterMustHaveFilledStatisticalOperation() throws Exception {
        String publicationUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME).getSiemacMetadataStatisticalResource().getUrn();

        Chapter chapterBeforeCreate = notPersistedDoMocks.mockChapter();
        assertNull(chapterBeforeCreate.getNameableStatisticalResource().getStatisticalOperation());

        Chapter chapterAfterCreate = publicationService.createChapter(getServiceContextWithoutPrincipal(), publicationUrn, chapterBeforeCreate);
        assertNotNull(chapterAfterCreate.getNameableStatisticalResource().getStatisticalOperation());

        Chapter chapterAfterUpdate = publicationService.updateChapter(getServiceContextWithoutPrincipal(), chapterAfterCreate);
        assertNotNull(chapterAfterUpdate.getNameableStatisticalResource().getStatisticalOperation());

        CommonAsserts.assertEqualsExternalItem(chapterAfterCreate.getNameableStatisticalResource().getStatisticalOperation(), chapterAfterUpdate.getNameableStatisticalResource()
                .getStatisticalOperation());
    }

    // ------------------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------------------

    @Test
    @MetamacMock({PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME, DATASET_01_BASIC_NAME})
    public void testCreateAndUpdateCubeMustHaveFilledStatisticalOperation() throws Exception {
        String publicationUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME).getSiemacMetadataStatisticalResource().getUrn();
        Dataset dataset = datasetMockFactory.retrieveMock(DATASET_01_BASIC_NAME);

        Cube cubeBeforeCreate = notPersistedDoMocks.mockDatasetCube(dataset);
        assertNull(cubeBeforeCreate.getNameableStatisticalResource().getStatisticalOperation());

        Cube cubeAfterCreate = publicationService.createCube(getServiceContextWithoutPrincipal(), publicationUrn, cubeBeforeCreate);
        assertNotNull(cubeAfterCreate.getNameableStatisticalResource().getStatisticalOperation());

        Cube cubeAfterUpdate = publicationService.updateCube(getServiceContextWithoutPrincipal(), cubeAfterCreate);
        assertNotNull(cubeAfterUpdate.getNameableStatisticalResource().getStatisticalOperation());

        CommonAsserts.assertEqualsExternalItem(cubeAfterCreate.getNameableStatisticalResource().getStatisticalOperation(), cubeAfterUpdate.getNameableStatisticalResource().getStatisticalOperation());
    }

}
