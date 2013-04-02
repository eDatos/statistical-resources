package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceListItem;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.shared.RelatedResourceBaseUtils;

public class StatisticalResourceResourceRelationDescriptorsForm extends GroupDynamicForm {

    public StatisticalResourceResourceRelationDescriptorsForm() {
        super(getConstants().formResourceRelationDescriptors());

        // TODO SOURCE
        ViewTextItem replaces = new ViewTextItem(StatisticalResourceDS.REPLACES, getConstants().siemacMetadataStatisticalResourceReplaces());
        ViewTextItem isReplacedBy = new ViewTextItem(StatisticalResourceDS.IS_REPLACED_BY, getConstants().siemacMetadataStatisticalResourceIsReplacedBy());
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
        setValue(StatisticalResourceDS.REPLACES, RelatedResourceBaseUtils.getRelatedResourceName(siemacMetadataStatisticalResourceDto.getReplaces()));
        setValue(StatisticalResourceDS.IS_REPLACED_BY, RelatedResourceBaseUtils.getRelatedResourceName(siemacMetadataStatisticalResourceDto.getIsReplacedBy()));
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.REQUIRES)).setRelatedResources(siemacMetadataStatisticalResourceDto.getRequires());
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.IS_REQUIRED_BY)).setRelatedResources(siemacMetadataStatisticalResourceDto.getIsRequiredBy());
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.HAS_PART)).setRelatedResources(siemacMetadataStatisticalResourceDto.getHasPart());
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.IS_PART_OF)).setRelatedResources(siemacMetadataStatisticalResourceDto.getIsPartOf());
        // TODO IS_REFERENCE_BY
        // TODO REFERENCES
        // TODO IS_FORMAT_OF
        // TODO HAS_FORMAT
    }
}
