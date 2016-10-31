package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatetimeAvro;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


public class DatetimeAvroMapperTest {

    @Test
    public void testDo2Avro() {
        long expectedInstant = new DateTime(2016, 10, 26, 9, 0, 0, 0).getMillis();
        DatetimeAvro expected = DatetimeAvro.newBuilder().setInstant(expectedInstant).build();
        DateTime source = new DateTime(expectedInstant);

        DatetimeAvro actual = DatetimeAvroMapper.do2Avro(source);

        assertThat(actual.getInstant(), is(equalTo(expectedInstant)));

    }

    @Test
    public void testAvro2Do() {
        long expectedInstant = new DateTime(2016, 10, 26, 9, 0, 0, 0).getMillis();
        DateTime expected = new DateTime(expectedInstant);
        DatetimeAvro source = DatetimeAvro.newBuilder().setInstant(expectedInstant).build();

        DateTime actual = DatetimeAvroMapper.avro2Do(source);

        assertThat(actual.getMillis(), is(equalTo(expected.getMillis())));
    }

}
