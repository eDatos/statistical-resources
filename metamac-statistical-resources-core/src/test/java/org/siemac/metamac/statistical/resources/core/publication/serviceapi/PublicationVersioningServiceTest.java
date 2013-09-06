package org.siemac.metamac.statistical.resources.core.publication.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsVersionedElementLevelCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_12_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_16_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_26_WITH_COMPLEX_STRUCTURE_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_31_V2_PUBLISHED_NO_VISIBLE_FOR_PUBLICATION_06_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_39_PUBLISHED_WITH_NO_ROOT_MAINTAINER_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.enume.domain.VersionPatternEnum;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.validators.ProcStatusValidator;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.BaseEnumUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/include/rest-services-mockito.xml",
        "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class PublicationVersioningServiceTest extends StatisticalResourcesBaseTest {

    @Autowired
    private PublicationVersionMockFactory        publicationVersionMockFactory;

    @Autowired
    private PublicationService                   publicationService;

    @Autowired
    @Qualifier("publicationLifecycleService")
    private LifecycleService<PublicationVersion> publicationVersionLifecycleService;

    @Test
    @MetamacMock(PUBLICATION_VERSION_16_PUBLISHED_NAME)
    public void testVersioningPublicationVersion() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_16_PUBLISHED_NAME);

        PublicationVersion publicationNewVersion = publicationVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource()
                .getUrn(), VersionTypeEnum.MINOR);
        publicationVersion = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource().getUrn());
        assertNotNull(publicationNewVersion);
        assertFalse(publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic().equals(publicationNewVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        checkNewPublicationVersionCreated(publicationVersion, publicationNewVersion);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_26_WITH_COMPLEX_STRUCTURE_PUBLISHED_NAME)
    public void testVersioningPublicationVersionWithComplexStructure() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_26_WITH_COMPLEX_STRUCTURE_PUBLISHED_NAME);

        PublicationVersion publicationNewVersion = publicationVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource()
                .getUrn(), VersionTypeEnum.MINOR);
        publicationVersion = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource().getUrn());
        assertNotNull(publicationNewVersion);
        assertFalse(publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic().equals(publicationNewVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        checkNewPublicationVersionCreated(publicationVersion, publicationNewVersion);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_16_PUBLISHED_NAME)
    public void testVersioningPublicationVersionCheckUrnIsCorrectForMinorChange() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_16_PUBLISHED_NAME);

        // Necessary data for construct URN
        VersionTypeEnum versionType = VersionTypeEnum.MINOR;
        String publicationVersionCode = publicationVersion.getSiemacMetadataStatisticalResource().getCode();
        String[] maintainer = new String[]{publicationVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};
        String versionBefore = publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic();

        // New publication version
        PublicationVersion newPublicationVersion = publicationVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource()
                .getUrn(), versionType);
        publicationVersion = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Expected URN
        String versionAfter = VersionUtil.createNextVersion(versionBefore, VersionPatternEnum.XXX_YYY, versionType);

        // Compare URNS
        String expectedUrn = GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionVersionUrn(maintainer, publicationVersionCode, versionAfter);
        assertEquals(expectedUrn, newPublicationVersion.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_16_PUBLISHED_NAME)
    public void testVersioningPublicationVersionCheckUrnIsCorrectForMayorChange() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_16_PUBLISHED_NAME);

        // Necessary data for construct URN
        VersionTypeEnum versionType = VersionTypeEnum.MAJOR;
        String publicationVersionCode = publicationVersion.getSiemacMetadataStatisticalResource().getCode();
        String[] maintainer = new String[]{publicationVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};
        String versionBefore = publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic();

        // New publication version
        PublicationVersion newPublicationVersion = publicationVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource()
                .getUrn(), versionType);
        publicationVersion = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Expected URN
        String versionAfter = VersionUtil.createNextVersion(versionBefore, VersionPatternEnum.XXX_YYY, versionType);

        // Compare URNS
        String expectedUrn = GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionVersionUrn(maintainer, publicationVersionCode, versionAfter);
        assertEquals(expectedUrn, newPublicationVersion.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_39_PUBLISHED_WITH_NO_ROOT_MAINTAINER_NAME)
    public void testVersioningPublicationVersionCheckUrnIsCorrectForANonRootAgency() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_39_PUBLISHED_WITH_NO_ROOT_MAINTAINER_NAME);

        // Necessary data for construct URN
        VersionTypeEnum versionType = VersionTypeEnum.MINOR;
        String publicationVersionCode = publicationVersion.getSiemacMetadataStatisticalResource().getCode();
        String[] maintainer = new String[]{publicationVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};
        String versionBefore = publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic();

        // New publication version
        PublicationVersion newPublicationVersion = publicationVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource()
                .getUrn(), versionType);
        publicationVersion = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Expected URN
        String versionAfter = VersionUtil.createNextVersion(versionBefore, VersionPatternEnum.XXX_YYY, versionType);

        // Compare URNS
        String expectedUrn = GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionVersionUrn(maintainer, publicationVersionCode, versionAfter);
        assertEquals(expectedUrn, newPublicationVersion.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_31_V2_PUBLISHED_NO_VISIBLE_FOR_PUBLICATION_06_NAME)
    public void testVersioningPublicationVersionErrorPublicationVersionNotVisible() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_31_V2_PUBLISHED_NO_VISIBLE_FOR_PUBLICATION_06_NAME).getSiemacMetadataStatisticalResource()
                .getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, publicationVersionUrn,
                BaseEnumUtils.enumToString(ProcStatusValidator.procStatusForSendResourceToVersion)));
        publicationVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), publicationVersionUrn, VersionTypeEnum.MAJOR);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_12_DRAFT_NAME)
    public void testVersioningPublicationVersionErrorDraftProcStatus() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_12_DRAFT_NAME).getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, publicationVersionUrn, ProcStatusEnum.PUBLISHED.getName()));
        publicationVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), publicationVersionUrn, VersionTypeEnum.MAJOR);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME)
    public void testVersioningPublicationVersionErrorProductionValidationProcStatus() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, publicationVersionUrn, ProcStatusEnum.PUBLISHED.getName()));
        publicationVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), publicationVersionUrn, VersionTypeEnum.MAJOR);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME)
    public void testVersioningPublicationVersionErrorDiffusionValidationProcStatus() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, publicationVersionUrn, ProcStatusEnum.PUBLISHED.getName()));
        publicationVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), publicationVersionUrn, VersionTypeEnum.MAJOR);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME)
    public void testVersioningPublicationVersionErrorValidationRejectedProcStatus() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME).getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, publicationVersionUrn, ProcStatusEnum.PUBLISHED.getName()));
        publicationVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), publicationVersionUrn, VersionTypeEnum.MAJOR);
    }

    // -------------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // -------------------------------------------------------------------------------------------

    private static void checkNewPublicationVersionCreated(PublicationVersion previous, PublicationVersion next) throws MetamacException {
        BaseAsserts.assertEqualsVersioningSiemacMetadata(previous.getSiemacMetadataStatisticalResource(), next.getSiemacMetadataStatisticalResource());

        // Inherited fields
        assertEqualsVersionedElementLevelCollection(previous.getChildrenAllLevels(), next.getChildrenAllLevels());
        assertEqualsVersionedElementLevelCollection(previous.getChildrenFirstLevel(), next.getChildrenFirstLevel());
        assertEquals(previous.getFormatExtentResources(), next.getFormatExtentResources());
    }

}
