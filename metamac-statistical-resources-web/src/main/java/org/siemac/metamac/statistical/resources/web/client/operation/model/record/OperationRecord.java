package org.siemac.metamac.statistical.resources.web.client.operation.model.record;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.operation.model.ds.OperationDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class OperationRecord extends ListGridRecord {

    public OperationRecord(String identifier, String title, String uri, String urn, ExternalItemDto operationExternalItemDto) {
        setIdentifier(identifier);
        setTitle(title);
        setUrn(uri);
        setUrn(urn);
        setOperationExternalItemDto(operationExternalItemDto);
    }

    public void setIdentifier(String identifier) {
        setAttribute(OperationDS.IDENTIFIER, identifier);
    }

    public void setTitle(String title) {
        setAttribute(OperationDS.TITLE, title);
    }

    public void setUrn(String value) {
        setAttribute(OperationDS.URN, value);
    }

    public void setUri(String value) {
        setAttribute(OperationDS.URI, value);
    }

    public void setOperationExternalItemDto(ExternalItemDto operationExternalItemDto) {
        setAttribute(OperationDS.DTO, operationExternalItemDto);
    }

    public String getIdentifier() {
        return getAttribute(OperationDS.IDENTIFIER);
    }

    public String getTitle() {
        return getAttribute(OperationDS.TITLE);
    }

    public String getUrn() {
        return getAttributeAsString(OperationDS.URN);
    }

    public String getUri() {
        return getAttributeAsString(OperationDS.URI);
    }

}
