package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatetimeAvro;

public class DateTimeAvroMapperTest {

    @Test
    public void testDo2Avro() {
        long expectedInstant = new DateTime(2016, 10, 26, 9, 0, 0, 0).getMillis();
        DatetimeAvro expected = DatetimeAvro.newBuilder().setInstant(expectedInstant).build();
        DateTime source = new DateTime(expectedInstant);

        DatetimeAvro actual = DateTimeDo2AvroMapper.do2Avro(source);

        assertThat(actual.getInstant(), is(equalTo(expectedInstant)));

    }

}
