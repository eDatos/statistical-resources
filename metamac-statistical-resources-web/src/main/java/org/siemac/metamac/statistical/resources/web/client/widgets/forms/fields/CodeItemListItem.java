package org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.CodeItemDS;
import org.siemac.metamac.statistical.resources.web.client.model.record.CodeItemRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;
import org.siemac.metamac.web.common.client.widgets.form.fields.BaseListItem;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CodeItemListItem extends BaseListItem {

    protected HandlerRegistration recordClickHandlerRegistration;

    public CodeItemListItem(String name, String title, boolean editionMode) {
        super(name, title, editionMode);
        createItem();
    }

    // FIXME: messages
    private void createItem() {

        CustomListGridField codeField = new CustomListGridField(CodeItemDS.CODE, MetamacWebCommon.getConstants().relatedResourceCode());
        CustomListGridField nameField = new CustomListGridField(CodeItemDS.TITLE, MetamacWebCommon.getConstants().relatedResourceTitle());

        listGrid.setFields(codeField, nameField);
    }

    public void setCodeItems(List<CodeItemDto> dtos) {
        listGrid.removeAllData();
        for (CodeItemDto dto : dtos) {
            CodeItemRecord record = StatisticalResourcesRecordUtils.getCodeItemRecord(dto);
            listGrid.addData(record);
        }
    }

    public List<CodeItemDto> getCodeItemsDtos() {
        List<CodeItemDto> selectedResourceDtos = new ArrayList<CodeItemDto>();
        ListGridRecord records[] = listGrid.getRecords();
        if (records != null) {
            for (ListGridRecord record : records) {
                CodeItemRecord codeItemRecord = (CodeItemRecord) record;
                selectedResourceDtos.add(codeItemRecord.getCodeItemDto());
            }
        }
        return selectedResourceDtos;
    }

    public void clearRelatedResourceList() {
        listGrid.removeAllData();
    }

}