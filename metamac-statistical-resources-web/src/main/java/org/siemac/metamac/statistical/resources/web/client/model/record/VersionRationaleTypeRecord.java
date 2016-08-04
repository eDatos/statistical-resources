package org.siemac.metamac.statistical.resources.web.client.model.record;

import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.VersionRationaleTypeDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class VersionRationaleTypeRecord extends ListGridRecord {

    public VersionRationaleTypeRecord() {

    }

    public void setValue(String value) {
        setAttribute(VersionRationaleTypeDS.VALUE, value);
    }

    public String getValue() {
        return getAttributeAsString(VersionRationaleTypeDS.VALUE);
    }

    public void setVersionRationaleTypeDto(VersionRationaleTypeDto versionRationaleTypeDto) {
        setAttribute(VersionRationaleTypeDS.DTO, versionRationaleTypeDto);
    }

    public VersionRationaleTypeDto getVersionRationaleTypeDto() {
        return (VersionRationaleTypeDto) getAttributeAsObject(VersionRationaleTypeDS.DTO);
    }
}
