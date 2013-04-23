package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceNextVersionEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;


public class LifecycleCommonMetadataCheckerTest extends StatisticalResourcesBaseTest {

    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;
    
    public LifecycleCommonMetadataCheckerTest() {
        lifecycleCommonMetadataChecker = new LifecycleCommonMetadataChecker();
    }
    
    @Test
    public void testCheckLifecycleCommonMetadata() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        expectedMetamacException(new MetamacException(
                Arrays.asList(
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION)),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS))
                )));
        
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }
    
    
    @Test
    public void testCheckLifecycleCommonMetadataVersionRationaleRequiredIfMinorErrata() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        
        for (StatisticalResourceVersionRationaleTypeEnum versionRationaleType2Test : StatisticalResourceVersionRationaleTypeEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            resource.addVersionRationaleType(new VersionRationaleType(versionRationaleType2Test));
            
            
            MetamacException expected = null;
            if (versionRationaleType2Test.equals(StatisticalResourceVersionRationaleTypeEnum.MINOR_ERRATA)) {
                expected = new MetamacException(
                        Arrays.asList(
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS))
                        ));
            } else {
                expected = new MetamacException(
                        Arrays.asList(
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS))
                        ));
            }
            
            List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
            lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(resource, baseMetadata, exceptionItems);
            MetamacException actual = new MetamacException(exceptionItems);
            MetamacAsserts.assertEqualsMetamacException(expected, actual);
        }
    }
    
    @Test
    public void testCheckLifecycleCommonMetadataNextVersionDateInvalidIfNotScheduledNextVersion() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        
        for (StatisticalResourceNextVersionEnum nextVersion2Test : StatisticalResourceNextVersionEnum.values()) {
            LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
            resource.setNextVersion(nextVersion2Test);
            resource.setNextVersionDate(new DateTime().plusDays(1));
            
            
            MetamacException expected = null;
            if (!nextVersion2Test.equals(StatisticalResourceNextVersionEnum.SCHEDULED_UPDATE)) {
                expected = new MetamacException(
                        Arrays.asList(
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_UNEXPECTED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION_DATE)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS))
                        ));
            } else {
                expected = new MetamacException(
                        Arrays.asList(
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)),
                                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS))
                        ));
            }
            
            List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
            lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(resource, baseMetadata, exceptionItems);
            MetamacException actual = new MetamacException(exceptionItems);
            MetamacAsserts.assertEqualsMetamacException(expected, actual);
        }
    }
    
    @Test
    public void testCheckSiemacResourceCommonMetadata() throws Exception {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        expectedMetamacException(new MetamacException(Arrays.asList(
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LANGUAGE)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LANGUAGES)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.STATISTICAL_OPERATION)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TYPE)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.MAINTAINER)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CREATOR)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LAST_UPDATE)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PUBLISHER)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.RIGHTS_HOLDER)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LICENSE))
        )));
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkSiemacCommonMetadata(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }
    
    @Test
    public void testDatasetVersionCommonMetadata() throws Exception {
        DatasetVersion resource = new DatasetVersion();
        
        String baseMetadata = ServiceExceptionSingleParameters.DATASET_VERSION;
        expectedMetamacException(new MetamacException ( 
                Arrays.asList(
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__GEOGRAPHIC_GRANULARITIES),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__TEMPORAL_GRANULARITIES),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__DATE_NEXT_UPDATE),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__UPDATE_FREQUENCY),
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__STATISTIC_OFFICIALITY)
                        )));
        
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }
}
