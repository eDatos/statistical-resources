package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatetimeAvro;

public class DateTimeDo2AvroMapper {

    protected DateTimeDo2AvroMapper() {
    }

    public static DatetimeAvro do2Avro(DateTime source) {
        DatetimeAvro target = null;
        if (source != null) {
            target = DatetimeAvro.newBuilder().setInstant(source.getMillis()).build();
        }
        return target;
    }

}
