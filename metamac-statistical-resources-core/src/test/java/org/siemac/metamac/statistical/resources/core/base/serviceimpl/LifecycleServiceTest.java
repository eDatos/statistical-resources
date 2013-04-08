package org.siemac.metamac.statistical.resources.core.base.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.base.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceNextVersionEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class LifecycleServiceTest extends StatisticalResourcesBaseTest {

    @Autowired
    private LifecycleService lifecycleServiceBaseImpl;

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationRequiredFields() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        resource.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        expectedMetamacException(new MetamacException(Arrays.asList(
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION)))));

        lifecycleServiceBaseImpl.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }

    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationProcStatus() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        String validStatus = StatisticalResourceProcStatusEnum.DRAFT.name() + ", " + StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.name();

        for (StatisticalResourceProcStatusEnum procStatus : StatisticalResourceProcStatusEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            prepareToProductionValidation(resource);
            resource.setProcStatus(procStatus);

            if (StatisticalResourceProcStatusEnum.DRAFT.equals(procStatus) || StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleServiceBaseImpl.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleServiceBaseImpl.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    MetamacAsserts.assertEqualsMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, resource.getUrn(), validStatus), e);
                }
            }
        }
    }

    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationVersionRationale() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        prepareToProductionValidation(resource);
        resource.setVersionLogic("01.001");
        resource.getVersionRationaleTypes().clear();
        resource.getVersionRationaleTypes().add(new VersionRationaleType(StatisticalResourceVersionRationaleTypeEnum.MINOR_ERRATA));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        lifecycleServiceBaseImpl.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);

        MetamacException expected = new MetamacException(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE));
        MetamacException actual = new MetamacException(exceptionItems);

        MetamacAsserts.assertEqualsMetamacException(expected, actual);
    }

    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationWrongVersionRationaleType() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        for (StatisticalResourceVersionRationaleTypeEnum versionRationaleType : StatisticalResourceVersionRationaleTypeEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            prepareToProductionValidation(resource);
            resource.setVersionLogic("01.000");
            resource.getVersionRationaleTypes().clear();
            resource.getVersionRationaleTypes().add(new VersionRationaleType(versionRationaleType));
            resource.setVersionRationale(StatisticalResourcesPersistedDoMocks.mockInternationalString());

            List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
            lifecycleServiceBaseImpl.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);

            if (StatisticalResourceVersionRationaleTypeEnum.MAJOR_NEW_RESOURCE.equals(versionRationaleType)) {
                assertEquals(0, exceptionItems.size());
            } else {
                MetamacAsserts.assertEqualsMetamacException(
                        new MetamacException(ServiceExceptionType.METADATA_INCORRECT, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)), new MetamacException(
                                exceptionItems));
            }
        }
    }

    @Test
    public void testLifeCycleResourceCheckSendToProductionValidationNextVersionDate() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        prepareToProductionValidation(resource);
        resource.setNextVersion(StatisticalResourceNextVersionEnum.NON_SCHEDULED_UPDATE);
        resource.setNextVersionDate(new DateTime().plusDays(10));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        lifecycleServiceBaseImpl.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);

        MetamacException expected = new MetamacException(ServiceExceptionType.METADATA_UNEXPECTED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION_DATE));
        MetamacException actual = new MetamacException(exceptionItems);
        MetamacAsserts.assertEqualsMetamacException(expected, actual);
    }

    @Test
    public void testLifeCycleResourceApplySendToProductionValidationActions() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        prepareToProductionValidation(resource);

        lifecycleServiceBaseImpl.applySendToProductionValidationActions(getServiceContextAdministrador(), resource);

        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertEquals(StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION, resource.getProcStatus());
    }

    private void assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(LifeCycleStatisticalResource resource) {
        assertNotNull(resource.getProductionValidationDate());
        assertNotNull(resource.getProductionValidationUser());
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToDiffusionValidationProcStatus() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        for (StatisticalResourceProcStatusEnum procStatus : StatisticalResourceProcStatusEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            prepareToDiffusionValidation(resource);
            resource.setProcStatus(procStatus);

            if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleServiceBaseImpl.checkSendToDiffusionValidation(resource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleServiceBaseImpl.checkSendToDiffusionValidation(resource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    assertEquals("Error with procstatus " + procStatus, ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS.getCode(), e.getExceptionItems().get(0).getCode());
                }
            }
        }
    }
    
    @Test
    public void testLifeCycleResourceApplySendToDiffusionValidationActions() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        prepareToDiffusionValidation(resource);

        lifecycleServiceBaseImpl.applySendToDiffusionValidationActions(getServiceContextAdministrador(), resource);

        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(resource);
        assertEquals(StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION,resource.getProcStatus());
        
    }

    private void assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(LifeCycleStatisticalResource resource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertNotNull(resource.getDiffusionValidationDate());
        assertNotNull(resource.getDiffusionValidationUser());
        
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceCheckSendToValidationRejectedProcStatus() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        for (StatisticalResourceProcStatusEnum procStatus : StatisticalResourceProcStatusEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            prepareToValidationRejected(resource);
            resource.setProcStatus(procStatus);

            if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus) || StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus)) {
                List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                lifecycleServiceBaseImpl.checkSendToValidationRejected(resource, baseMetadata, exceptionItems);
                assertEquals(0, exceptionItems.size());
            } else {
                try {
                    List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
                    lifecycleServiceBaseImpl.checkSendToValidationRejected(resource, baseMetadata, exceptionItems);
                } catch (MetamacException e) {
                    assertEquals(1, e.getExceptionItems().size());
                    assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, 2, new String[]{resource.getUrn(), "PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"}, e.getExceptionItems().get(0));
                }
            }
        }
    }
    
    
    @Test
    public void testLifeCycleResourceApplySendToValidationRejectedActions() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        prepareToValidationRejected(resource);

        lifecycleServiceBaseImpl.applySendToValidationRejectedActions(getServiceContextAdministrador(), resource);

        assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(resource);
        assertEquals(StatisticalResourceProcStatusEnum.VALIDATION_REJECTED,resource.getProcStatus());
        
    }

    private void assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(LifeCycleStatisticalResource resource) {
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(resource);
        assertNotNull(resource.getRejectValidationDate());
        assertNotNull(resource.getRejectValidationUser());
    }

    // ------------------------------------------------------------------------------------------------------
    // UTILS
    // ------------------------------------------------------------------------------------------------------

    private void prepareToProductionValidation(LifeCycleStatisticalResource resource) {
        StatisticalResourcesPersistedDoMocks.prepareToProductionValidationLifecycleResource(resource);
        resource.setCode("CODE");
        resource.setUrn("URN");
    }
    
    private void prepareToDiffusionValidation(LifeCycleStatisticalResource resource) {
        prepareToProductionValidation(resource);
        resource.setProductionValidationDate(new DateTime());
        resource.setProductionValidationUser("PRODUCTION_VALIDATION_USER");
    }
    
    private void prepareToValidationRejected(LifeCycleStatisticalResource resource) {
        prepareToProductionValidation(resource);
        resource.setProductionValidationDate(new DateTime());
        resource.setProductionValidationUser("PRODUCTION_VALIDATION_USER");
    }
}
