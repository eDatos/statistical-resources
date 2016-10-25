package org.siemac.metamac.statistical.resources.web.server.stream;

import org.apache.avro.specific.SpecificRecordBase;

public class AvroMessage<T extends SpecificRecordBase> extends MessageBase<T> {

    public AvroMessage(T messageContent) {
        super(messageContent);
    }

}
