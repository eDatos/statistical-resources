package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.AvroMapperUtils;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.PublicationVersionAvro2DoMapper;

public class PublicationVersionAvroMapperTest {

    @Mock
    PublicationVersionRepository publicationVersionRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setPublicationVersionRepository(publicationVersionRepository);
    }

    @Test
    public void testDo2Avro() throws Exception {
        PublicationVersionAvro expected = MappersMockUtils.mockPublicationVersionAvro();
        PublicationVersion source = MappersMockUtils.mockPublicationVersion();

        when(publicationVersionRepository.retrieveByUrn(Mockito.any())).thenReturn(source);

        assertThat(source.getPublication().getIdentifiableStatisticalResource().getUrn(), is(equalTo(expected.getPublication().getIdentifiableStatisticalResource().getUrn())));

        PublicationVersionAvro actual = PublicationVersionDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() throws Exception {
        PublicationVersion expected = MappersMockUtils.mockPublicationVersion();
        PublicationVersionAvro source = MappersMockUtils.mockPublicationVersionAvro();

        when(publicationVersionRepository.retrieveByUrn(Mockito.any())).thenReturn(expected);

        PublicationVersion actual = PublicationVersionAvro2DoMapper.avro2Do(source);

        assertThat(actual.getHasPart().size(), is(equalTo(expected.getHasPart().size())));
        assertThat(actual.getPublication(), is(equalTo(expected.getPublication())));
    }

}
