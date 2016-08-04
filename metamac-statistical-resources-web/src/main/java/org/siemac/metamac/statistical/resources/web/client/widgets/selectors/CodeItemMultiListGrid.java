package org.siemac.metamac.statistical.resources.web.client.widgets.selectors;

import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.model.ds.CodeItemDS;
import org.siemac.metamac.statistical.resources.web.client.model.record.CodeItemRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;
import org.siemac.metamac.web.common.client.widgets.selectors.ResourceMultiListGridSelector;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CodeItemMultiListGrid extends ResourceMultiListGridSelector<CodeItemDto> {

    public CodeItemMultiListGrid() {
        super();
    }

    @Override
    protected void setSelectorFields() {
        CustomListGridField codeField = new CustomListGridField(CodeItemDS.CODE, StatisticalResourcesWeb.getConstants().codeItemCode());
        codeField.setWidth("30%");
        CustomListGridField titleField = new CustomListGridField(CodeItemDS.TITLE, StatisticalResourcesWeb.getConstants().codeItemTitle());

        baseCheckListGrid.setFields(codeField, titleField);

        codeField = new CustomListGridField(CodeItemDS.CODE, StatisticalResourcesWeb.getConstants().codeItemCode());
        codeField.setWidth("30%");
        titleField = new CustomListGridField(CodeItemDS.TITLE, StatisticalResourcesWeb.getConstants().codeItemTitle());

        selectionListGrid.setFields(codeField, titleField);
    }

    @Override
    public ListGridRecord buildRecordForResource(CodeItemDto resource) {
        return StatisticalResourcesRecordUtils.getCodeItemRecord(resource);
    };

    @Override
    public CodeItemDto getResourceFromRecord(ListGridRecord record) {
        return ((CodeItemRecord) record).getCodeItemDto();
    }
}
