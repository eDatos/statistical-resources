package org.siemac.metamac.statistical.resources.web.server.stream;

public abstract class MessageBase<T> {

    protected T content;

    public MessageBase(T messageContent) {
        super();
        this.content = messageContent;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T messageContent) {
        this.content = messageContent;
    }

}
