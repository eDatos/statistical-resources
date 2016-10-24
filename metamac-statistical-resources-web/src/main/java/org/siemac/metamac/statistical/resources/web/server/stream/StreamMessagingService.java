package org.siemac.metamac.statistical.resources.web.server.stream;

import org.siemac.metamac.core.common.exception.MetamacException;

public abstract class StreamMessagingService<K, V> {

    public static final String BEAN_ID = "StreamMessagingService";

    ProducerBase<K, V>         producer;

    protected StreamMessagingService() {
    }

    public abstract void sendMessage(V message, String topic) throws MetamacException;

}
