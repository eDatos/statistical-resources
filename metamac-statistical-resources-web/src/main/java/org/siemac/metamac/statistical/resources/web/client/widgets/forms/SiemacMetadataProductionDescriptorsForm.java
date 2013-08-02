package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemsValue;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;

public class SiemacMetadataProductionDescriptorsForm extends LifeCycleResourceProductionDescriptorsForm {

    public SiemacMetadataProductionDescriptorsForm() {
        super();

        ExternalItemLinkItem creator = new ExternalItemLinkItem(SiemacMetadataDS.CREATOR, getConstants().siemacMetadataStatisticalResourceCreator());
        ExternalItemListItem contributor = new ExternalItemListItem(SiemacMetadataDS.CONTRIBUTOR, getConstants().siemacMetadataStatisticalResourceContributor(), false);

        ViewTextItem dateCreated = new ViewTextItem(SiemacMetadataDS.DATE_CREATED, getConstants().siemacMetadataStatisticalResourceDateCreated());
        ViewTextItem lastUpdate = new ViewTextItem(SiemacMetadataDS.LAST_UPDATE, getConstants().siemacMetadataStatisticalResourceLastUpdate());
        ViewMultiLanguageTextItem conformsTo = new ViewMultiLanguageTextItem(SiemacMetadataDS.CONFORMS_TO, getConstants().siemacMetadataStatisticalResourceConformsTo());
        ViewMultiLanguageTextItem conformsToInternal = new ViewMultiLanguageTextItem(SiemacMetadataDS.CONFORMS_TO_INTERNAL, getConstants().siemacMetadataStatisticalResourceConformsToInternal());

        addFields(dateCreated, lastUpdate, creator, contributor, conformsTo, conformsToInternal);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        setLifeCycleResourceDto(dto);

        setExternalItemValue(getItem(SiemacMetadataDS.CREATOR), dto.getCreator());
        setExternalItemsValue(getItem(SiemacMetadataDS.CONTRIBUTOR), dto.getContributor());

        setValue(SiemacMetadataDS.DATE_CREATED, dto.getResourceCreatedDate());
        setValue(SiemacMetadataDS.LAST_UPDATE, dto.getLastUpdate());
        setValue(SiemacMetadataDS.CONFORMS_TO, RecordUtils.getInternationalStringRecord(dto.getConformsTo()));
        setValue(SiemacMetadataDS.CONFORMS_TO_INTERNAL, RecordUtils.getInternationalStringRecord(dto.getConformsToInternal()));
    }
}
