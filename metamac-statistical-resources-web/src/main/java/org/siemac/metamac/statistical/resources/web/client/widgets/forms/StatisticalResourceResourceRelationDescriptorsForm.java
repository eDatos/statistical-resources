package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceListItem;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.shared.RelatedResourceBaseUtils;

public class StatisticalResourceResourceRelationDescriptorsForm extends NavigationEnabledDynamicForm {

    private BaseUiHandlers uiHandlers;

    public StatisticalResourceResourceRelationDescriptorsForm() {
        super(getConstants().formResourceRelationDescriptors());

        ViewTextItem replaces = new ViewTextItem(StatisticalResourceDS.REPLACES, getConstants().siemacMetadataStatisticalResourceReplaces());
        ViewTextItem isReplacedBy = new ViewTextItem(StatisticalResourceDS.IS_REPLACED_BY, getConstants().siemacMetadataStatisticalResourceIsReplacedBy());
        RelatedResourceListItem requires = new RelatedResourceListItem(StatisticalResourceDS.REQUIRES, getConstants().siemacMetadataStatisticalResourceRequires(), false, getRecordNavigationHandler());
        RelatedResourceListItem isRequiredBy = new RelatedResourceListItem(StatisticalResourceDS.IS_REQUIRED_BY, getConstants().siemacMetadataStatisticalResourceIsRequiredBy(), false,
                getRecordNavigationHandler());
        RelatedResourceListItem hasPart = new RelatedResourceListItem(StatisticalResourceDS.HAS_PART, getConstants().siemacMetadataStatisticalResourceHasPart(), false, getRecordNavigationHandler());
        RelatedResourceListItem isPartOf = new RelatedResourceListItem(StatisticalResourceDS.IS_PART_OF, getConstants().siemacMetadataStatisticalResourceIsPartOf(), false,
                getRecordNavigationHandler());

        setFields(replaces, isReplacedBy, requires, isRequiredBy, hasPart, isPartOf);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setValue(StatisticalResourceDS.REPLACES, RelatedResourceBaseUtils.getRelatedResourceName(siemacMetadataStatisticalResourceDto.getReplaces()));
        setValue(StatisticalResourceDS.IS_REPLACED_BY, RelatedResourceBaseUtils.getRelatedResourceName(siemacMetadataStatisticalResourceDto.getIsReplacedBy()));
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.REQUIRES)).setRelatedResources(siemacMetadataStatisticalResourceDto.getRequires());
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.IS_REQUIRED_BY)).setRelatedResources(siemacMetadataStatisticalResourceDto.getIsRequiredBy());
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.HAS_PART)).setRelatedResources(siemacMetadataStatisticalResourceDto.getHasPart());
        ((RelatedResourceListItem) getItem(StatisticalResourceDS.IS_PART_OF)).setRelatedResources(siemacMetadataStatisticalResourceDto.getIsPartOf());
    }

    public void setUiHandlers(BaseUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
