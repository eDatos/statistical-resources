package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValue;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.stream.messages.AttributeValueAvro;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;


public class AttributeValueAvroMapperTest {

    @Mock
    private static DatasetVersionRepository datasetVersionRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setDatasetVersionRepository(datasetVersionRepository);
    }


    @Test
    public void testDo2Avro() {
        AttributeValueAvro expected = MappersMockUtils.mockAttributeValueAvro();
        AttributeValue source = MappersMockUtils.mockAttributeValue();

        AttributeValueAvro actual = AttributeValueAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() throws MetamacException {
        AttributeValue expected = MappersMockUtils.mockAttributeValue();
        AttributeValueAvro source = MappersMockUtils.mockAttributeValueAvro();

        when(datasetVersionRepository.retrieveByUrn(MappersMockUtils.EXPECTED_URN)).thenReturn(expected.getDatasetVersion());

        AttributeValue actual = AttributeValueAvroMapper.avro2Do(source);


        DatasetsAsserts.assertEqualsDatasetVersion(expected.getDatasetVersion(), actual.getDatasetVersion());    }

}




