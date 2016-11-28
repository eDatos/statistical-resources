package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatetimeAvro;

public class DateTimeAvro2DoMapper {

    protected DateTimeAvro2DoMapper() {
    }

    public static DateTime avro2Do(DatetimeAvro source) {
        DateTime target = null;
        if (source != null) {
            target = new DateTime(source.getInstant());
        }
        return target;
    }

}
