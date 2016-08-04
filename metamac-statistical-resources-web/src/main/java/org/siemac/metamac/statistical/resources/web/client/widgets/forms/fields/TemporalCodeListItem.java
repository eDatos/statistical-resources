package org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.TemporalCodeDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.model.ds.TemporalCodeDS;
import org.siemac.metamac.statistical.resources.web.client.model.record.TemporalCodeRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;
import org.siemac.metamac.web.common.client.widgets.form.fields.BaseListItem;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TemporalCodeListItem extends BaseListItem {

    protected HandlerRegistration recordClickHandlerRegistration;

    public TemporalCodeListItem(String name, String title, boolean editionMode) {
        super(name, title, editionMode);
        createItem();
    }

    private void createItem() {

        CustomListGridField codeField = new CustomListGridField(TemporalCodeDS.CODE, StatisticalResourcesWeb.getConstants().codeItemCode());
        CustomListGridField nameField = new CustomListGridField(TemporalCodeDS.TITLE, StatisticalResourcesWeb.getConstants().codeItemTitle());

        listGrid.setFields(codeField, nameField);
    }

    public void setTemporalCodes(List<TemporalCodeDto> dtos) {
        listGrid.removeAllData();
        if (dtos != null) {
            for (TemporalCodeDto dto : dtos) {
                TemporalCodeRecord record = StatisticalResourcesRecordUtils.getTemporalCodeRecord(dto);
                listGrid.addData(record);
            }
        }
    }

    public List<TemporalCodeDto> getTemporalCodes() {
        List<TemporalCodeDto> selectedResourceDtos = new ArrayList<TemporalCodeDto>();
        ListGridRecord records[] = listGrid.getRecords();
        if (records != null) {
            for (ListGridRecord record : records) {
                TemporalCodeRecord codeRecord = (TemporalCodeRecord) record;
                selectedResourceDtos.add(codeRecord.getTemporalCodeDto());
            }
        }
        return selectedResourceDtos;
    }

    public void clearRelatedResourceList() {
        listGrid.removeAllData();
    }

    public void setRequired(boolean required) {
        if (required) {
            setTitleStyle("requiredFormLabel");
            CustomValidator customValidator = new CustomValidator() {

                @Override
                protected boolean condition(Object value) {
                    return isVisible() ? (getTemporalCodes() != null && !getTemporalCodes().isEmpty()) : true;
                }
            };
            setValidators(customValidator);
        }
    }

}