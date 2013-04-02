package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceListItem;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchViewTextItem;

public class StatisticalResourceResourceRelationDescriptorsEditionForm extends GroupDynamicForm {

    public StatisticalResourceResourceRelationDescriptorsEditionForm() {
        super(getConstants().formResourceRelationDescriptors());

        // TODO SOURCE
        SearchViewTextItem replaces = new SearchViewTextItem(StatisticalResourceDS.REPLACES, getConstants().siemacMetadataStatisticalResourceReplaces()); // TODO editable!
        SearchViewTextItem isReplacedBy = new SearchViewTextItem(StatisticalResourceDS.IS_REPLACED_BY, getConstants().siemacMetadataStatisticalResourceIsReplacedBy()); // TODO editable!
        RelatedResourceListItem requires = new RelatedResourceListItem(StatisticalResourceDS.REQUIRES, getConstants().siemacMetadataStatisticalResourceRequires(), false);
        RelatedResourceListItem isRequiredBy = new RelatedResourceListItem(StatisticalResourceDS.IS_REQUIRED_BY, getConstants().siemacMetadataStatisticalResourceIsRequiredBy(), false);
        RelatedResourceListItem hasPart = new RelatedResourceListItem(StatisticalResourceDS.HAS_PART, getConstants().siemacMetadataStatisticalResourceHasPart(), false);
        RelatedResourceListItem isPartOf = new RelatedResourceListItem(StatisticalResourceDS.IS_PART_OF, getConstants().siemacMetadataStatisticalResourceIsPartOf(), false);
        // TODO IS_REFERENCE_BY
        // TODO REFERENCES
        // TODO IS_FORMAT_OF
        // TODO HAS_FORMAT

        setFields(replaces, isReplacedBy, requires, isRequiredBy, hasPart, isPartOf);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        // TODO SOURCE
        setValue(StatisticalResourceDS.REPLACES, RelatedResourceUtils.getRelatedResourceName(siemacMetadataStatisticalResourceDto.getReplaces()));
        setValue(StatisticalResourceDS.IS_REPLACED_BY, RelatedResourceUtils.getRelatedResourceName(siemacMetadataStatisticalResourceDto.getIsReplacedBy()));
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.REQUIRES)).setRelatedResources(siemacMetadataStatisticalResourceDto.getRequires());
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.IS_REQUIRED_BY)).setRelatedResources(siemacMetadataStatisticalResourceDto.getIsRequiredBy());
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.HAS_PART)).setRelatedResources(siemacMetadataStatisticalResourceDto.getHasPart());
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.IS_PART_OF)).setRelatedResources(siemacMetadataStatisticalResourceDto.getIsPartOf());
        // TODO IS_REFERENCE_BY
        // TODO REFERENCES
        // TODO IS_FORMAT_OF
        // TODO HAS_FORMAT
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        // TODO get modified values
        return siemacMetadataStatisticalResourceDto;
    }
}
