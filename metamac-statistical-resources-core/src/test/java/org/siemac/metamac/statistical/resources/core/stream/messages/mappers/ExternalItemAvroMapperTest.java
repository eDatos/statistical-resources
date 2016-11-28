package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.AvroMapperUtils;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.ExternalItemAvro2DoMapper;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;

public class ExternalItemAvroMapperTest {

    @Mock
    private static ConfigurationService configurationService;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setConfiguratinService(configurationService);
        try {
            when(configurationService.retrieveStatisticalResourcesInternalApiUrlBase()).thenReturn(MappersMockUtils.EXPECTED_API_BASE);
        } catch (MetamacException e) {
        }
    }

    @Test
    public void testExternalItemDo2Avro() {
        ExternalItem source = MappersMockUtils.mockExternalItem();
        ExternalItemAvro expected = MappersMockUtils.mockExternalItemAvro();
        ExternalItemAvro actual = ExternalItemDo2AvroMapper.do2Avro(source);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testExternalItemAvro2Do() throws MetamacException {
        ExternalItem expected = MappersMockUtils.mockExternalItem();
        ExternalItemAvro source = MappersMockUtils.mockExternalItemAvro();

        ExternalItem actual = ExternalItemAvro2DoMapper.avro2Do(source);

        CommonAsserts.assertEqualsExternalItem(expected, actual);;

    }

}
