package org.siemac.metamac.statistical.resources.web.server.stream;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;

abstract public class ProducerBase<K, V> {

    protected ProducerBase() {
    }

    public void sendMessages(List<MessageBase<K, V>> messages) throws MetamacException {
        for (MessageBase<K, V> message : messages) {
            sendMessage(message);
        }
    }

    public abstract void sendMessage(MessageBase<K, V> m, String topic) throws MetamacException;

    public void sendMessage(MessageBase<K, V> m) throws MetamacException {
        sendMessage(m, null);
    }

    public abstract void close();
}
