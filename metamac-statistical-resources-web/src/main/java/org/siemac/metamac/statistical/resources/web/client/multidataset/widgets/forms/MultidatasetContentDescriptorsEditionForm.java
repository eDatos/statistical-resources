package org.siemac.metamac.statistical.resources.web.client.multidataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.multidataset.model.ds.MultidatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.SiemacMetadataContentDescriptorsEditionForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;

public class MultidatasetContentDescriptorsEditionForm extends SiemacMetadataContentDescriptorsEditionForm {

    public MultidatasetContentDescriptorsEditionForm() {
        super();

        MultiLanguageTextItem filterDimension = new MultiLanguageTextItem(MultidatasetDS.FILTERING_DIMENSION, getConstants().multidatasetFilteringDimension());

        addFields(filterDimension);
    }

    public void setMultidatasetDto(MultidatasetVersionDto multidatasetVersionDto) {
        setSiemacMetadataStatisticalResourceDto(multidatasetVersionDto);

        setValue(MultidatasetDS.FILTERING_DIMENSION, multidatasetVersionDto.getFilteringDimension());
    }

    public MultidatasetVersionDto getMultidatasetDto(MultidatasetVersionDto multidatasetVersionDto) {
        multidatasetVersionDto = (MultidatasetVersionDto) getSiemacMetadataStatisticalResourceDto(multidatasetVersionDto);

        multidatasetVersionDto.setFilteringDimension(getValueAsInternationalStringDto(MultidatasetDS.FILTERING_DIMENSION));
        return multidatasetVersionDto;
    }

}
