package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemsValue;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;

public class StatisticalResourceProductionDescriptorsForm extends GroupDynamicForm {

    public StatisticalResourceProductionDescriptorsForm() {
        super(getConstants().formProductionDescriptors());

        ExternalItemLinkItem maintainer = new ExternalItemLinkItem(StatisticalResourceDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer());
        ExternalItemLinkItem creator = new ExternalItemLinkItem(StatisticalResourceDS.CREATOR, getConstants().siemacMetadataStatisticalResourceCreator());
        ExternalItemListItem contributor = new ExternalItemListItem(StatisticalResourceDS.CONTRIBUTOR, getConstants().siemacMetadataStatisticalResourceContributor(), false);

        ViewTextItem dateCreated = new ViewTextItem(StatisticalResourceDS.DATE_CREATED, getConstants().siemacMetadataStatisticalResourceDateCreated());
        ViewTextItem lastUpdate = new ViewTextItem(StatisticalResourceDS.LAST_UPDATE, getConstants().siemacMetadataStatisticalResourceLastUpdate());
        ViewMultiLanguageTextItem conformsTo = new ViewMultiLanguageTextItem(StatisticalResourceDS.CONFORMS_TO, getConstants().siemacMetadataStatisticalResourceConformsTo());
        ViewMultiLanguageTextItem conformsToInternal = new ViewMultiLanguageTextItem(StatisticalResourceDS.CONFORMS_TO_INTERNAL, getConstants().siemacMetadataStatisticalResourceConformsToInternal());

        setFields(dateCreated, lastUpdate, maintainer, creator, contributor, conformsTo, conformsToInternal);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        setExternalItemValue(getItem(StatisticalResourceDS.MAINTAINER), dto.getMaintainer());
        setExternalItemValue(getItem(StatisticalResourceDS.CREATOR), dto.getCreator());
        setExternalItemsValue(getItem(StatisticalResourceDS.CONTRIBUTOR), dto.getContributor());

        setValue(StatisticalResourceDS.DATE_CREATED, dto.getResourceCreatedDate());
        setValue(StatisticalResourceDS.LAST_UPDATE, dto.getLastUpdate());
        setValue(StatisticalResourceDS.CONFORMS_TO, RecordUtils.getInternationalStringRecord(dto.getConformsTo()));
        setValue(StatisticalResourceDS.CONFORMS_TO_INTERNAL, RecordUtils.getInternationalStringRecord(dto.getConformsToInternal()));
    }
}
