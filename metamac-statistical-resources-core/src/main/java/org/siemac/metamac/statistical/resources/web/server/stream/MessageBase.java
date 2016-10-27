package org.siemac.metamac.statistical.resources.web.server.stream;

public abstract class MessageBase<K, V> {

    protected K key;
    protected V content;

    public MessageBase(V messageContent) {
        this(null, messageContent);
    }

    public MessageBase(K key, V messageContent) {
        super();
        this.content = messageContent;
        this.key = key;
    }

    public V getContent() {
        return content;
    }

    public void setContent(V messageContent) {
        this.content = messageContent;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

}
