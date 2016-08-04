package org.siemac.metamac.statistical.resources.web.client.dataset.model.record;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasourceDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DatasourceRecord extends ListGridRecord {

    public DatasourceRecord(Long id, String code, String urn, DatasourceDto datasourceDto) {
        setId(id);
        setIdentifier(code);
        setUrn(urn);
        setDatasourceDto(datasourceDto);
    }

    public void setId(Long id) {
        setAttribute(DatasourceDS.ID, id);
    }

    public void setIdentifier(String identifier) {
        setAttribute(DatasourceDS.CODE, identifier);
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
        return getAttribute(DatasourceDS.CODE);
    }

    public String getUrn() {
        return getAttributeAsString(DatasourceDS.URN);
    }

}
