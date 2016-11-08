package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatetimeAvro;

public class DateTimeAvroMapper {

    protected DateTimeAvroMapper() {
    }

    public static DatetimeAvro do2Avro(DateTime source) {
        DatetimeAvro target = null;
        if (source != null) {
            target = DatetimeAvro.newBuilder()
                    .setInstant(source.getMillis())
                    .build();
        }
        return target;
    }

    public static DateTime avro2Do(DatetimeAvro source) {
        DateTime target = null;
        if (source != null) {
            target = new DateTime(source.getInstant());
        }
        return target;
    }

}
