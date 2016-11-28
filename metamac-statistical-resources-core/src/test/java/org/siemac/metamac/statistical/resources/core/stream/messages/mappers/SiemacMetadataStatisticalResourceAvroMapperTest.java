package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.SiemacMetadataStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.AvroMapperUtils;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.SiemacMetadataStatisticalResourceAvro2DoMapper;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;

public class SiemacMetadataStatisticalResourceAvroMapperTest {

    @Mock
    protected DatasetRepository datasetRepository;

    @Mock
    protected DatasetVersionRepository datasetVersionRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setDatasetRepository(datasetRepository);
        AvroMapperUtils.setDatasetVersionRepository(datasetVersionRepository);
    }

    @Test
    public void testAvro2Do() throws MetamacException {
        when(datasetVersionRepository.retrieveByUrn(any())).thenReturn(MappersMockUtils.mockDatasetVersion());

        SiemacMetadataStatisticalResource expected = MappersMockUtils.mockSiemacMetadataStatisticalResource(TypeRelatedResourceEnum.DATASET_VERSION);
        SiemacMetadataStatisticalResourceAvro source = MappersMockUtils.mockSiemacMetadataStatisticalResourceAvro(TypeRelatedResourceEnum.DATASET_VERSION);
        SiemacMetadataStatisticalResource actual = SiemacMetadataStatisticalResourceAvro2DoMapper.avro2Do(source);

        assertEqualsSiemacMetadataStatisticalResource(expected, actual);
    }

    @Test
    public void testDo2Avro() throws MetamacException {
        when(datasetVersionRepository.retrieveByUrn(MappersMockUtils.EXPECTED_URN)).thenReturn(MappersMockUtils.mockDatasetVersion());

        SiemacMetadataStatisticalResourceAvro expected = MappersMockUtils.mockSiemacMetadataStatisticalResourceAvro(TypeRelatedResourceEnum.DATASET);
        SiemacMetadataStatisticalResource source = MappersMockUtils.mockSiemacMetadataStatisticalResource(TypeRelatedResourceEnum.DATASET);

        SiemacMetadataStatisticalResourceAvro actual = SiemacMetadataStatisticalResourceDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));

    }

    private void assertEqualsSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource expected, SiemacMetadataStatisticalResource actual) throws MetamacException {
        CommonAsserts.assertEqualsInternationalString(expected.getAbstractLogic(), actual.getAbstractLogic());
        CommonAsserts.assertEqualsInternationalString(expected.getAccessRights(), actual.getAccessRights());
        CommonAsserts.assertEqualsExternalItem(expected.getCommonMetadata(), actual.getCommonMetadata());
        CommonAsserts.assertEqualsInternationalString(expected.getConformsTo(), actual.getConformsTo());
        CommonAsserts.assertEqualsInternationalString(expected.getConformsToInternal(), actual.getConformsToInternal());
        CommonAsserts.assertEqualsExternalItemCollection(expected.getContributor(), actual.getContributor());
        assertThat(actual.getCopyrightedDate(), is(equalTo(expected.getCopyrightedDate())));
        CommonAsserts.assertEqualsExternalItem(expected.getCreator(), actual.getCreator());
        CommonAsserts.assertEqualsInternationalString(expected.getKeywords(), actual.getKeywords());
        CommonAsserts.assertEqualsExternalItem(expected.getLanguage(), actual.getLanguage());
        assertThat(actual.getLastUpdate(), is(equalTo(expected.getLastUpdate())));
        CommonAsserts.assertEqualsExternalItemCollection(expected.getMediator(), actual.getMediator());
        assertThat(actual.getNewnessUntilDate(), is(equalTo(expected.getNewnessUntilDate())));
        CommonAsserts.assertEqualsExternalItemCollection(expected.getPublisherContributor(), actual.getPublisherContributor());
        CommonAsserts.assertEqualsExternalItemCollection(expected.getPublisher(), actual.getPublisher());
        CommonAsserts.assertEqualsRelatedResource(expected.getReplaces(), actual.getReplaces());
        assertThat(actual.getResourceCreatedDate(), is(equalTo(expected.getResourceCreatedDate())));
        CommonAsserts.assertEqualsExternalItemCollection(expected.getStatisticalOperationInstances(), actual.getStatisticalOperationInstances());
        CommonAsserts.assertEqualsInternationalString(expected.getSubtitle(), actual.getSubtitle());
        CommonAsserts.assertEqualsInternationalString(expected.getTitleAlternative(), actual.getTitleAlternative());
        assertThat(actual.getType(), is(equalTo(expected.getType())));
    }

}
