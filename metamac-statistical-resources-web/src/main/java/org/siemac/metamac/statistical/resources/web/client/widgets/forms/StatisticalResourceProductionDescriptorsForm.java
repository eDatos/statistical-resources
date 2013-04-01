package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class StatisticalResourceProductionDescriptorsForm extends GroupDynamicForm {

    public StatisticalResourceProductionDescriptorsForm() {
        super(getConstants().formProductionDescriptors());

        ViewTextItem maintainer = new ViewTextItem(StatisticalResourceDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer());
        ViewTextItem creator = new ViewTextItem(StatisticalResourceDS.CREATOR, getConstants().siemacMetadataStatisticalResourceCreator());
        ViewTextItem contributor = new ViewTextItem(StatisticalResourceDS.CONTRIBUTOR, getConstants().siemacMetadataStatisticalResourceContributor());
        ViewTextItem dateCreated = new ViewTextItem(StatisticalResourceDS.DATE_CREATED, getConstants().siemacMetadataStatisticalResourceDateCreated());
        ViewTextItem lastUpdate = new ViewTextItem(StatisticalResourceDS.LAST_UPDATE, getConstants().siemacMetadataStatisticalResourceLastUpdate());
        ViewMultiLanguageTextItem conformsTo = new ViewMultiLanguageTextItem(StatisticalResourceDS.CONFORMS_TO, getConstants().siemacMetadataStatisticalResourceConformsTo());
        ViewMultiLanguageTextItem conformsToInternal = new ViewMultiLanguageTextItem(StatisticalResourceDS.CONFORMS_TO_INTERNAL, getConstants().siemacMetadataStatisticalResourceConformsToInternal());

        setFields(maintainer, creator, contributor, dateCreated, lastUpdate, conformsTo, conformsToInternal);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setValue(StatisticalResourceDS.MAINTAINER, ExternalItemUtils.getExternalItemName(siemacMetadataStatisticalResourceDto.getMaintainer()));
        // TODO ExternalItemListItem setValue(StatisticalResourceDS.CREATOR, ExternalItemUtils.getExternalItemName(siemacMetadataStatisticalResourceDto.getContributor()));
        // TODO ExternalItemListItem setValue(StatisticalResourceDS.CONTRIBUTOR, ExternalItemUtils.getExternalItemName(siemacMetadataStatisticalResourceDto.getContributor()));
        setValue(StatisticalResourceDS.DATE_CREATED, siemacMetadataStatisticalResourceDto.getResourceCreatedDate());
        setValue(StatisticalResourceDS.LAST_UPDATE, siemacMetadataStatisticalResourceDto.getLastUpdate());
        setValue(StatisticalResourceDS.CONFORMS_TO, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getConformsTo()));
        setValue(StatisticalResourceDS.CONFORMS_TO_INTERNAL, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getConformsToInternal()));
    }
}
