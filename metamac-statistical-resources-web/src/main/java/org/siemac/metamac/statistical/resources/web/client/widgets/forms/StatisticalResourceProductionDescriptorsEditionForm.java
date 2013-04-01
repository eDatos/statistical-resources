package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAreaItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class StatisticalResourceProductionDescriptorsEditionForm extends GroupDynamicForm {

    public StatisticalResourceProductionDescriptorsEditionForm() {
        super(getConstants().formProductionDescriptors());

        ViewTextItem maintainer = new ViewTextItem(StatisticalResourceDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer()); // TODO Should be editable
        ViewTextItem creator = new ViewTextItem(StatisticalResourceDS.CREATOR, getConstants().siemacMetadataStatisticalResourceCreator()); // TODO Should be editable
        ViewTextItem contributor = new ViewTextItem(StatisticalResourceDS.CONTRIBUTOR, getConstants().siemacMetadataStatisticalResourceContributor()); // TODO Should be editable
        ViewTextItem dateCreated = new ViewTextItem(StatisticalResourceDS.DATE_CREATED, getConstants().siemacMetadataStatisticalResourceDateCreated());
        ViewTextItem lastUpdate = new ViewTextItem(StatisticalResourceDS.LAST_UPDATE, getConstants().siemacMetadataStatisticalResourceLastUpdate());
        MultiLanguageTextAreaItem conformsTo = new MultiLanguageTextAreaItem(StatisticalResourceDS.CONFORMS_TO, getConstants().siemacMetadataStatisticalResourceConformsTo());
        MultiLanguageTextAreaItem conformsToInternal = new MultiLanguageTextAreaItem(StatisticalResourceDS.CONFORMS_TO_INTERNAL, getConstants().siemacMetadataStatisticalResourceConformsToInternal());

        setFields(maintainer, creator, contributor, dateCreated, lastUpdate, conformsTo, conformsToInternal);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setValue(StatisticalResourceDS.MAINTAINER, ExternalItemUtils.getExternalItemName(siemacMetadataStatisticalResourceDto.getMaintainer())); // TODO editable
        // TODO ExternalItemListItem setValue(StatisticalResourceDS.CREATOR, ExternalItemUtils.getExternalItemName(siemacMetadataStatisticalResourceDto.getContributor()));
        // TODO ExternalItemListItem setValue(StatisticalResourceDS.CONTRIBUTOR, ExternalItemUtils.getExternalItemName(siemacMetadataStatisticalResourceDto.getContributor()));
        setValue(StatisticalResourceDS.DATE_CREATED, siemacMetadataStatisticalResourceDto.getResourceCreatedDate());
        setValue(StatisticalResourceDS.LAST_UPDATE, siemacMetadataStatisticalResourceDto.getLastUpdate());
        setValue(StatisticalResourceDS.CONFORMS_TO, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getConformsTo()));
        setValue(StatisticalResourceDS.CONFORMS_TO_INTERNAL, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getConformsToInternal()));
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        // TODO maintainer
        // TODO creator
        // TODO contributor
        siemacMetadataStatisticalResourceDto.setConformsTo((InternationalStringDto) getValue(StatisticalResourceDS.CONFORMS_TO));
        siemacMetadataStatisticalResourceDto.setConformsToInternal((InternationalStringDto) getValue(StatisticalResourceDS.CONFORMS_TO_INTERNAL));
        return siemacMetadataStatisticalResourceDto;
    }
}
