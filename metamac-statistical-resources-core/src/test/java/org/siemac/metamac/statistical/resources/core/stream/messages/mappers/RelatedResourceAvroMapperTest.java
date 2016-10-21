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
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;

public class RelatedResourceAvroMapperTest {

    @Mock
    protected DatasetRepository        datasetRepository;

    @Mock
    protected DatasetVersionRepository datasetVersionRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setDatasetRepository(datasetRepository);
        AvroMapperUtils.setDatasetVersionRepository(datasetVersionRepository);
    }

    @Test
    public void testRelatedResourceDo2Avro() throws MetamacException {
        when(datasetVersionRepository.retrieveByUrn(MappersMockUtils.EXPECTED_URN)).thenReturn(MappersMockUtils.mockDatasetVersion());

        RelatedResourceAvro expected = MappersMockUtils.mockRelatedResourceAvro(TypeRelatedResourceEnum.DATASET);
        RelatedResource source = MappersMockUtils.mockRelatedResource(TypeRelatedResourceEnum.DATASET);

        RelatedResourceAvro actual = RelatedResourceAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testRelatedResourceAvro2Do() throws MetamacException {
        when(datasetRepository.retrieveByUrn(any())).thenReturn(MappersMockUtils.mockDataset());

        RelatedResource expected = MappersMockUtils.mockRelatedResource(TypeRelatedResourceEnum.DATASET);
        RelatedResourceAvro source = MappersMockUtils.mockRelatedResourceAvro(TypeRelatedResourceEnum.DATASET);
        RelatedResource actual = RelatedResourceAvroMapper.avro2Do(source);

        assertThat(actual.getDataset().getIdentifiableStatisticalResource().getCode(), is(equalTo(expected.getDataset().getIdentifiableStatisticalResource().getCode())));
        assertThat(actual.getDataset().getIdentifiableStatisticalResource().getUrn(), is(equalTo(expected.getDataset().getIdentifiableStatisticalResource().getUrn())));
        assertThat(actual.getType(), is(equalTo(expected.getType())));
        assertThat(actual.getDataset().getIdentifiableStatisticalResource().getStatisticalOperation().getUrn(),
                is(expected.getDataset().getIdentifiableStatisticalResource().getStatisticalOperation().getUrn()));
    }

}
