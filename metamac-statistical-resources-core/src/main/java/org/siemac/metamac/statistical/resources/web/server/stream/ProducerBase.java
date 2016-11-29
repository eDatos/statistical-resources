package org.siemac.metamac.statistical.resources.web.server.stream;

import org.siemac.metamac.core.common.exception.MetamacException;

public interface ProducerBase<K, V> {

    public void sendMessage(MessageBase<K, V> m, String topic) throws MetamacException;

    public void close();
}
