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
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.AvroMapperUtils;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.PublicationAvroDo2Mapper;
import org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts;

public class PublicationAvroMapperTest {

    @Mock
    PublicationVersionRepository publicationVersionRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setPublicationVersionRepository(publicationVersionRepository);
    }

    @Test
    public void testDo2Avro() throws Exception {
        PublicationAvro expected = MappersMockUtils.mockPublicationAvro();
        Publication source = MappersMockUtils.mockPublication();

        PublicationAvro actual = PublicationDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() throws Exception {
        Publication expected = MappersMockUtils.mockPublication();
        PublicationAvro source = MappersMockUtils.mockPublicationAvro();

        when(publicationVersionRepository.retrieveByUrn(Mockito.startsWith(MappersMockUtils.EXPECTED_URN))).thenReturn(expected.getVersions().get(0)).thenReturn(expected.getVersions().get(1));

        Publication actual = PublicationAvroDo2Mapper.avro2Do(source);

        PublicationsAsserts.assertEqualsPublication(expected, actual);
    }

}
