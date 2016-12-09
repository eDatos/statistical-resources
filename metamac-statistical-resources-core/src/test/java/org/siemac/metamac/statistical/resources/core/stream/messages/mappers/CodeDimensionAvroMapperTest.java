package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.stream.messages.CodeDimensionAvro;

public class CodeDimensionAvroMapperTest {

    @Mock
    private static DatasetVersionRepository datasetVersionRepository;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.datasetVersionRepository = datasetVersionRepository;
    }

    @Test
    public void testDo2Avro() {
        CodeDimensionAvro expected = MappersMockUtils.mockCodeDimensionAvro();
        CodeDimension source = MappersMockUtils.mockCodeDimension();

        CodeDimensionAvro actual = CodeDimensionDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

}
