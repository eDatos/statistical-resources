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
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;

public class LifecycleCommonMetadataCheckerTest extends StatisticalResourcesBaseTest {

    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    public LifecycleCommonMetadataCheckerTest() {
        lifecycleCommonMetadataChecker = new LifecycleCommonMetadataChecker();
    }

    @Test
    public void testCheckLifecycleCommonMetadata() throws Exception {
        HasLifecycle mockedResource = mock(HasLifecycle.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        mockedResource.getLifeCycleStatisticalResource().setIsReplacedByVersion(new RelatedResource());

        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        expectedMetamacException(new MetamacException(Arrays.asList(
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.STATISTICAL_OPERATION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_UNEXPECTED, addParameter(baseMetadata, ServiceExceptionSingleParameters.IS_REPLACED_BY_VERSION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.MAINTAINER)))));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(mockedResource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }

    @Test
    public void testCheckLifecycleCommonMetadataReplacesVersionNotRequired() throws Exception {
        HasLifecycle mockedResource = mock(HasLifecycle.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        mockedResource.getLifeCycleStatisticalResource().setIsReplacedByVersion(new RelatedResource());
        mockedResource.getLifeCycleStatisticalResource().setVersionLogic(StatisticalResourcesVersionUtils.INITIAL_VERSION);

        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        expectedMetamacException(new MetamacException(Arrays.asList(
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.STATISTICAL_OPERATION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_UNEXPECTED, addParameter(baseMetadata, ServiceExceptionSingleParameters.IS_REPLACED_BY_VERSION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.MAINTAINER)))));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(mockedResource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }

    @Test
    public void testCheckLifecycleCommonMetadataReplacesVersionRequired() throws Exception {
        HasLifecycle mockedResource = mock(HasLifecycle.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        mockedResource.getLifeCycleStatisticalResource().setIsReplacedByVersion(new RelatedResource());
        mockedResource.getLifeCycleStatisticalResource().setVersionLogic("0002.000");

        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;
        expectedMetamacException(new MetamacException(Arrays.asList(
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.STATISTICAL_OPERATION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_UNEXPECTED, addParameter(baseMetadata, ServiceExceptionSingleParameters.IS_REPLACED_BY_VERSION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.REPLACES_VERSION)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.MAINTAINER)))));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(mockedResource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }

    @Test
    public void testCheckLifecycleCommonMetadataVersionRationaleRequiredIfMinorErrata() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.LIFE_CYCLE_STATISTICAL_RESOURCE;

        for (VersionRationaleTypeEnum versionRationaleType2Test : VersionRationaleTypeEnum.values()) {
            HasLifecycle mockedResource = mock(HasLifecycle.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().addVersionRationaleType(new VersionRationaleType(versionRationaleType2Test));

            MetamacException expected = null;
            if (versionRationaleType2Test.equals(VersionRationaleTypeEnum.MINOR_ERRATA)) {
                expected = new MetamacException(Arrays.asList(
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.STATISTICAL_OPERATION)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.MAINTAINER))));
            } else {
                expected = new MetamacException(Arrays.asList(
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.STATISTICAL_OPERATION)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.MAINTAINER))));
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
            HasLifecycle mockedResource = mock(HasLifecycle.class);
            when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

            mockedResource.getLifeCycleStatisticalResource().setNextVersion(nextVersion2Test);
            mockedResource.getLifeCycleStatisticalResource().setNextVersionDate(new DateTime().plusDays(1));

            MetamacException expected = null;
            if (!nextVersion2Test.equals(NextVersionTypeEnum.SCHEDULED_UPDATE)) {
                expected = new MetamacException(Arrays.asList(
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.STATISTICAL_OPERATION)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_UNEXPECTED, addParameter(baseMetadata, ServiceExceptionSingleParameters.NEXT_VERSION_DATE)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.MAINTAINER))));
            } else {
                expected = new MetamacException(Arrays.asList(
                        new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.STATISTICAL_OPERATION)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CODE)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.URN)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TITLE)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.DESCRIPTION)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_LOGIC)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PROC_STATUS)), new MetamacExceptionItem(
                                ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.MAINTAINER))));
            }

            List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
            lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(mockedResource, baseMetadata, exceptionItems);
            MetamacException actual = new MetamacException(exceptionItems);
            MetamacAsserts.assertEqualsMetamacException(expected, actual);
        }
    }

    @Test
    public void testCheckSiemacResourceCommonMetadata() throws Exception {
        HasSiemacMetadata mockedResource = mock(HasSiemacMetadata.class);
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        expectedMetamacException(new MetamacException(Arrays.asList(
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LANGUAGE)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LANGUAGES)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TYPE)), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        addParameter(baseMetadata, ServiceExceptionSingleParameters.CREATOR)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LAST_UPDATE)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PUBLISHER)), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.COMMON_METADATA)))));
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkSiemacCommonMetadata(mockedResource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }

    @Test
    public void testDatasetVersionCommonMetadata() throws Exception {
        DatasetVersion resource = new DatasetVersion();
        resource.setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        resource.getSiemacMetadataStatisticalResource().setUrn("dataset-urn");

        String baseMetadata = ServiceExceptionSingleParameters.DATASET_VERSION;
        expectedMetamacException(new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__GEOGRAPHIC_GRANULARITIES), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__TEMPORAL_GRANULARITIES), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.DATASET_VERSION__UPDATE_FREQUENCY), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.DATASET_VERSION__STATISTIC_OFFICIALITY), new MetamacExceptionItem(ServiceExceptionType.DATASET_EMPTY_DATASOURCES, resource
                        .getSiemacMetadataStatisticalResource().getUrn()))));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }

    @Test
    public void testDatasetVersionCommonMetadataWithDatasourceWithNoDateNextUpdate() throws Exception {
        DatasetVersion resource = new DatasetVersion();
        resource.setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        resource.addDatasource(new Datasource());

        String baseMetadata = ServiceExceptionSingleParameters.DATASET_VERSION;
        expectedMetamacException(new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__GEOGRAPHIC_GRANULARITIES), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__TEMPORAL_GRANULARITIES), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.DATASET_VERSION__UPDATE_FREQUENCY), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.DATASET_VERSION__STATISTIC_OFFICIALITY), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.DATASET_VERSION__DATE_NEXT_UPDATE))));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }

    @Test
    public void testDatasetVersionCommonMetadataWithDatasourceWithUserDateNextUpdate() throws Exception {
        DatasetVersion resource = new DatasetVersion();
        resource.setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        resource.addDatasource(new Datasource());
        resource.setUserModifiedDateNextUpdate(true);
        resource.setDateNextUpdate(new DateTime());

        String baseMetadata = ServiceExceptionSingleParameters.DATASET_VERSION;
        expectedMetamacException(new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__GEOGRAPHIC_GRANULARITIES), new MetamacExceptionItem(
                        ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__TEMPORAL_GRANULARITIES), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.DATASET_VERSION__UPDATE_FREQUENCY), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.DATASET_VERSION__STATISTIC_OFFICIALITY))));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }

    @Test
    public void testPublicationVersionCommonMetadata() throws Exception {
        PublicationVersion resource = new PublicationVersion();
        resource.setFormatExtentResources(1);

        String baseMetadata = ServiceExceptionSingleParameters.PUBLICATION_VERSION;
        expectedMetamacException(new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.METADATA_UNEXPECTED,
                ServiceExceptionParameters.PUBLICATION_VERSION__FORMAT_EXTENT_RESOURCES))));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkPublicationVersionCommonMetadata(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }

    @Test
    public void testQueryVersionCommonMetadata() throws Exception {
        QueryVersion resource = new QueryVersion();

        String baseMetadata = ServiceExceptionSingleParameters.QUERY_VERSION;
        expectedMetamacException(new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__DATASET_VERSION),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__STATUS), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.QUERY_VERSION__TYPE), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__SELECTION))));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkQueryVersionCommonMetadata(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }

    @Test
    public void testQueryVersionCommonMetadataTypeLatestData() throws Exception {
        QueryVersion resource = new QueryVersion();
        resource.setType(QueryTypeEnum.LATEST_DATA);

        String baseMetadata = ServiceExceptionSingleParameters.QUERY_VERSION;
        expectedMetamacException(new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__DATASET_VERSION),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.QUERY_VERSION__STATUS), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.QUERY_VERSION__SELECTION), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.QUERY_VERSION__LATEST_DATA_NUMBER), new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED,
                        ServiceExceptionParameters.QUERY_VERSION__LATEST_TEMPORAL_CODE_IN_CREATION))));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        lifecycleCommonMetadataChecker.checkQueryVersionCommonMetadata(resource, baseMetadata, exceptionItems);
        throw new MetamacException(exceptionItems);
    }
}
