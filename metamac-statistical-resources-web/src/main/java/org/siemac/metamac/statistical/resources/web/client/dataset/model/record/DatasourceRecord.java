package org.siemac.metamac.statistical.resources.web.client.dataset.model.record;

import org.siemac.metamac.statistical.resources.core.dto.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasourceDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;


public class DatasourceRecord extends ListGridRecord {
    
    public DatasourceRecord(Long id, String identifier, String title, String urn, DatasourceDto datasourceDto) {
        setId(id);
        setIdentifier(identifier);
        setTitle(title);
        setUrn(urn);
        setDatasourceDto(datasourceDto);
    }

    public void setId(Long id) {
        setAttribute(DatasourceDS.ID, id);
    }

    public void setTitle(String title) {
        setAttribute(DatasourceDS.TITLE, title);
    }

    public void setIdentifier(String identifier) {
        setAttribute(DatasourceDS.IDENTIFIER, identifier);
    }

    public void setUrn(String value) {
        setAttribute(DatasourceDS.URN, value);
    }

    public void setDatasourceDto(DatasourceDto datasourceDto) {
        setAttribute(DatasourceDS.DTO, datasourceDto);
    }

    public Long getId() {
        return getAttributeAsLong(DatasourceDS.ID);
    }

    public String getIdentifier() {
        return getAttribute(DatasourceDS.IDENTIFIER);
    }

    public String getTitle() {
        return getAttribute(DatasourceDS.TITLE);
    }

    public String getUrn() {
        return getAttributeAsString(DatasourceDS.URN);
    }

}
