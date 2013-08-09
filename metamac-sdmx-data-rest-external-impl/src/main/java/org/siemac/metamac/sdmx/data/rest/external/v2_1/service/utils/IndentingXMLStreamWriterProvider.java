package org.siemac.metamac.sdmx.data.rest.external.v2_1.service.utils;

import java.io.OutputStream;

import javax.ws.rs.core.Response;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.cxf.jaxrs.ext.ResponseHandler;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.message.Message;
import org.apache.cxf.staxutils.StaxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

public class IndentingXMLStreamWriterProvider implements ResponseHandler {

    private static Logger       logger  = LoggerFactory.getLogger(IndentingXMLStreamWriterProvider.class);
    private static final String NEWLINE = "\n";

    @Override
    public Response handleResponse(Message m, OperationResourceInfo ori, Response response) {
        // Create a XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        outputFactory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, Boolean.TRUE);

        // Create XMLStreamWriter
        XMLStreamWriter writer = StaxUtils.createXMLStreamWriter(m.getContent(OutputStream.class));
        try {
            writer.writeStartDocument();
            writer.writeCharacters(NEWLINE);
        } catch (XMLStreamException e) {
            logger.error("Impossible to write XML startDocument", e);
        }
        m.setContent(XMLStreamWriter.class, new IndentingXMLStreamWriter(writer));

        return null;
    }
}
