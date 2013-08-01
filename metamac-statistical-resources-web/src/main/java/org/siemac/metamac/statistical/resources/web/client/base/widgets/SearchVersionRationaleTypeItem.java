package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.web.client.base.model.ds.VersionRationaleTypeDS;
import org.siemac.metamac.statistical.resources.web.client.base.model.record.VersionRationaleTypeRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.BaseSearchWindow;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;
import org.siemac.metamac.web.common.client.widgets.form.fields.DragAndDropItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SearchVersionRationaleTypeItem extends ExternalItemListItem {

    protected static final int                 FORM_ITEM_CUSTOM_WIDTH   = 500;

    protected SearchVersionRationaleTypeWindow searchVersionRationaleTypeWindow;
    protected List<VersionRationaleTypeDto>    versionRationaleTypeDtos = new ArrayList<VersionRationaleTypeDto>();

    public SearchVersionRationaleTypeItem(final String name, String title) {
        super(name, title, true);

        CustomListGridField valueField = new CustomListGridField(VersionRationaleTypeDS.VALUE, getConstants().versionableStatisticalResourceVersionRationaleTypeValue());
        listGrid.setFields(valueField);

        getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                searchVersionRationaleTypeWindow = new SearchVersionRationaleTypeWindow(name);
                searchVersionRationaleTypeWindow.resetValues();
                searchVersionRationaleTypeWindow.setSourceVersionRationaleTypeDtos(getSourceVersionRationaleTypeDtosWihtoutSelectedTypes());
                searchVersionRationaleTypeWindow.setTargetVersionRationaleTypeDtos(getSelectedVersionRationaleTypeDtos());
                searchVersionRationaleTypeWindow.getSaveButton().addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        List<VersionRationaleTypeDto> selectedTypeDtos = searchVersionRationaleTypeWindow.getSelectedVersionRationaleTypeDtos();
                        searchVersionRationaleTypeWindow.markForDestroy();

                        setVersionRationaleTypes(selectedTypeDtos);
                        SearchVersionRationaleTypeItem.this.validate();
                    }
                });
            }
        });
    }

    private List<VersionRationaleTypeDto> getSourceVersionRationaleTypeDtosWihtoutSelectedTypes() {
        List<VersionRationaleTypeDto> sourceTypeDtosWihtoutSelectedTypes = new ArrayList<VersionRationaleTypeDto>();
        List<VersionRationaleTypeDto> selectedTypeDtos = getSelectedVersionRationaleTypeDtos();
        for (VersionRationaleTypeDto typeDto : versionRationaleTypeDtos) {
            if (!isTypeInList(typeDto, selectedTypeDtos)) {
                sourceTypeDtosWihtoutSelectedTypes.add(typeDto);
            }
        }
        return sourceTypeDtosWihtoutSelectedTypes;
    }

    private boolean isTypeInList(VersionRationaleTypeDto versionRationaleTypeDto, List<VersionRationaleTypeDto> versionRationaleTypeDtos) {
        for (VersionRationaleTypeDto type : versionRationaleTypeDtos) {
            if (versionRationaleTypeDto.getValue() != null && type.getValue() != null) {
                if (StringUtils.equals(versionRationaleTypeDto.getValue().name(), type.getValue().name())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setVersionRationaleTypes(List<VersionRationaleTypeDto> versionRationaleTypeDtos) {
        VersionRationaleTypeRecord[] records = StatisticalResourcesRecordUtils.getVersionRationaleTypeRecords(versionRationaleTypeDtos);
        getListGrid().setData(records);
    }

    public List<VersionRationaleTypeDto> getSelectedVersionRationaleTypeDtos() {
        List<VersionRationaleTypeDto> versionRationaleTypeDtos = new ArrayList<VersionRationaleTypeDto>();
        ListGridRecord[] records = getListGrid().getRecords();
        for (ListGridRecord record : records) {
            versionRationaleTypeDtos.add(((VersionRationaleTypeRecord) record).getVersionRationaleTypeDto());
        }
        return versionRationaleTypeDtos;
    }

    public void setSourceVersionRationaleTypeDtos(List<VersionRationaleTypeDto> versionRationaleTypeDtos) {
        this.versionRationaleTypeDtos = versionRationaleTypeDtos;
    }

    private class SearchVersionRationaleTypeWindow extends BaseSearchWindow {

        protected VersionRationaleTypeDragAndDropItem VersionRationaleTypeDragAndDropItem;
        protected ButtonItem                          saveItem;

        public SearchVersionRationaleTypeWindow(String name) {
            super(getConstants().versionableStatisticalResourceVersionRationaleTypesSelection());
            setWidth(FORM_ITEM_CUSTOM_WIDTH);

            VersionRationaleTypeDragAndDropItem = new VersionRationaleTypeDragAndDropItem(name, "", name);
            VersionRationaleTypeDragAndDropItem.setShowTitle(false);

            saveItem = new ButtonItem("save-button", MetamacWebCommon.getConstants().actionSave());
            saveItem.setAlign(Alignment.CENTER);

            getForm().setFields(VersionRationaleTypeDragAndDropItem, saveItem);
        }

        public void setSourceVersionRationaleTypeDtos(List<VersionRationaleTypeDto> versionRationaleTypeDtos) {
            VersionRationaleTypeDragAndDropItem.setSourceVersionRationaleTypeDtos(versionRationaleTypeDtos);
        }

        public void setTargetVersionRationaleTypeDtos(List<VersionRationaleTypeDto> versionRationableTypeDtos) {
            VersionRationaleTypeDragAndDropItem.setTargetVersionRationaleTypeDtos(versionRationableTypeDtos);
        }

        public void resetValues() {
            VersionRationaleTypeDragAndDropItem.resetValues();
        }

        public ButtonItem getSaveButton() {
            return saveItem;
        }

        public List<VersionRationaleTypeDto> getSelectedVersionRationaleTypeDtos() {
            return VersionRationaleTypeDragAndDropItem.getSelectedVersionRationaleTypeDtos();
        }
    }

    private class VersionRationaleTypeDragAndDropItem extends DragAndDropItem {

        public VersionRationaleTypeDragAndDropItem(String name, String title, String dragDropType) {
            super(name, title);

            CustomListGridField titleField = new CustomListGridField(VersionRationaleTypeDS.VALUE, getConstants().versionableStatisticalResourceVersionRationaleTypeValue());

            sourceList.setHeight(250);
            sourceList.setWidth(FORM_ITEM_CUSTOM_WIDTH / 2);
            sourceList.setDataSource(new VersionRationaleTypeDS());
            sourceList.setUseAllDataSourceFields(false);
            sourceList.setFields(titleField);

            targetList.setHeight(250);
            targetList.setWidth(FORM_ITEM_CUSTOM_WIDTH / 2);
            targetList.setDataSource(new VersionRationaleTypeDS());
            targetList.setUseAllDataSourceFields(false);
            targetList.setFields(titleField);
        }

        public void setSourceVersionRationaleTypeDtos(List<VersionRationaleTypeDto> versionRationaleTypeDtos) {
            VersionRationaleTypeRecord[] records = StatisticalResourcesRecordUtils.getVersionRationaleTypeRecords(versionRationaleTypeDtos);
            sourceList.setData(records);
        }

        public void setTargetVersionRationaleTypeDtos(List<VersionRationaleTypeDto> versionRationaleTypeDtos) {
            VersionRationaleTypeRecord[] records = StatisticalResourcesRecordUtils.getVersionRationaleTypeRecords(versionRationaleTypeDtos);
            targetList.setData(records);
        }

        public List<VersionRationaleTypeDto> getSelectedVersionRationaleTypeDtos() {
            List<VersionRationaleTypeDto> selectedItems = new ArrayList<VersionRationaleTypeDto>();
            ListGridRecord[] records = targetList.getRecords();
            for (int i = 0; i < records.length; i++) {
                VersionRationaleTypeRecord record = (VersionRationaleTypeRecord) records[i];
                selectedItems.add(record.getVersionRationaleTypeDto());
            }
            return selectedItems;
        }

        public void resetValues() {
            clearValue();
        }
    }
}
