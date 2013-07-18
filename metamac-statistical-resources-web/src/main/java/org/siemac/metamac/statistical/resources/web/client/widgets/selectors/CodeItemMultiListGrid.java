package org.siemac.metamac.statistical.resources.web.client.widgets.selectors;

import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.CodeItemDS;
import org.siemac.metamac.statistical.resources.web.client.model.record.CodeItemRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;
import org.siemac.metamac.web.common.client.widgets.selectors.ResourceMultiListGridSelector;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CodeItemMultiListGrid extends ResourceMultiListGridSelector<CodeItemDto> {

    public CodeItemMultiListGrid() {
        super();
    }

    @Override
    protected void setSelectorFields() {
        // FIXME: change messages
        CustomListGridField codeField = new CustomListGridField(CodeItemDS.CODE, MetamacWebCommon.getConstants().relatedResourceCode());
        codeField.setWidth("30%");
        CustomListGridField titleField = new CustomListGridField(CodeItemDS.TITLE, MetamacWebCommon.getConstants().relatedResourceTitle());

        baseCheckListGrid.setFields(codeField, titleField);

        codeField = new CustomListGridField(CodeItemDS.CODE, MetamacWebCommon.getConstants().relatedResourceCode());
        codeField.setWidth("30%");
        titleField = new CustomListGridField(CodeItemDS.TITLE, MetamacWebCommon.getConstants().relatedResourceTitle());

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
