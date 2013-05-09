package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;


public class LifecycleCommonMetadataCheckerTest extends StatisticalResourcesBaseTest {

    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;
    
    public LifecycleCommonMetadataCheckerTest() {
        lifecycleCommonMetadataChecker = new LifecycleCommonMetadataChecker();
    }
    
    @Test
    public void testCheckLifecycleCommonMetadata() throws Exception {
        HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        
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
        lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(mockedResource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }
    
    
    @Test
    public void testCheckLifecycleCommonMetadataVersionRationaleRequiredIfMinorErrata() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        
        for (VersionRationaleTypeEnum versionRationaleType2Test : VersionRationaleTypeEnum.values()) {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
            
            mockedResource.getLifeCycleStatisticalResource().addVersionRationaleType(new VersionRationaleType(versionRationaleType2Test));
            
            
            MetamacException expected = null;
            if (versionRationaleType2Test.equals(VersionRationaleTypeEnum.MINOR_ERRATA)) {
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
            lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(mockedResource, baseMetadata, exceptionItems);
            MetamacException actual = new MetamacException(exceptionItems);
            MetamacAsserts.assertEqualsMetamacException(expected, actual);
        }
    }
    
    @Test
    public void testCheckLifecycleCommonMetadataNextVersionDateInvalidIfNotScheduledNextVersion() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        
        for (NextVersionTypeEnum nextVersion2Test : NextVersionTypeEnum.values()) {
            HasLifecycleStatisticalResource mockedResource = mock(HasLifecycleStatisticalResource.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
            
            mockedResource.getLifeCycleStatisticalResource().setNextVersion(nextVersion2Test);
            mockedResource.getLifeCycleStatisticalResource().setNextVersionDate(new DateTime().plusDays(1));
            
            
            MetamacException expected = null;
            if (!nextVersion2Test.equals(NextVersionTypeEnum.SCHEDULED_UPDATE)) {
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
            lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(mockedResource, baseMetadata, exceptionItems);
            MetamacException actual = new MetamacException(exceptionItems);
            MetamacAsserts.assertEqualsMetamacException(expected, actual);
        }
    }
    
    @Test
    public void testCheckSiemacResourceCommonMetadata() throws Exception {
        HasSiemacMetadataStatisticalResource mockedResource = mock(HasSiemacMetadataStatisticalResource.class);
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        
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
        lifecycleCommonMetadataChecker.checkSiemacCommonMetadata(mockedResource, baseMetadata, exceptionItems);
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
    
    @Test
    public void testPublicationVersionCommonMetadata() throws Exception {
        PublicationVersion resource = new PublicationVersion();
        
        String baseMetadata = ServiceExceptionSingleParameters.PUBLICATION_VERSION;
        expectedMetamacException(new MetamacException ( 
                Arrays.asList(
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.PUBLICATION_VERSION__FORMAT_EXTENT_RESOURCES)
                        )));
        
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkPublicationVersionCommonMetadata(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }
}
