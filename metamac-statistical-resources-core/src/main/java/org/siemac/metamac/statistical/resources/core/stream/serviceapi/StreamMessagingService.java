package org.siemac.metamac.statistical.resources.core.stream.serviceapi;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.web.server.stream.ProducerBase;

public abstract class StreamMessagingService<K, V> {

    public static final String BEAN_ID = "StreamMessagingService";

    public ProducerBase<K, V>  producer;

    protected StreamMessagingService() {
    }

    public abstract void sendMessage(V message, String topic) throws MetamacException;

}
