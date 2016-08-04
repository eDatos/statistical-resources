package org.siemac.metamac.statistical.resources.web.client.model.record;

import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.CodeItemDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CodeItemRecord extends ListGridRecord {

    public CodeItemRecord() {
    }

    public void setCode(String value) {
        setAttribute(CodeItemDS.CODE, value);
    }

    public String getCode() {
        return getAttributeAsString(CodeItemDS.CODE);
    }

    public void setTitle(String value) {
        setAttribute(CodeItemDS.TITLE, value);
    }

    public String getTitle() {
        return getAttributeAsString(CodeItemDS.TITLE);
    }

    public void setDimensionId(String value) {
        setAttribute(CodeItemDS.DIMENSION_ID, value);
    }

    public void setCodeItemDto(CodeItemDto codeItemDto) {
        setAttribute(CodeItemDS.DTO, codeItemDto);
    }

    public CodeItemDto getCodeItemDto() {
        return (CodeItemDto) getAttributeAsObject(CodeItemDS.DTO);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof CodeItemRecord) {
            CodeItemRecord other = (CodeItemRecord) obj;
            if (other.getCode() != null) {
                return other.getCode().equals(getCode());
            } else if (getCode() != null) {
                return getCode().equals(other.getCode());
            }
        }
        return false;
    }
}
