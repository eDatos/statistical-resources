package org.siemac.metamac.statistical.resources.web.client.multidataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.multidataset.model.ds.MultidatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.SiemacMetadataContentDescriptorsForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;

public class MultidatasetContentDescriptorsForm extends SiemacMetadataContentDescriptorsForm {

    public MultidatasetContentDescriptorsForm() {
        super();

        ViewMultiLanguageTextItem filteringDimension = new ViewMultiLanguageTextItem(MultidatasetDS.FILTERING_DIMENSION, getConstants().multidatasetFilteringDimension());

        addFields(filteringDimension);
    }

    public void setMultidatasetVersionDto(MultidatasetVersionDto multidatasetVersionDto) {
        setSiemacMetadataStatisticalResourceDto(multidatasetVersionDto);

        setValue(MultidatasetDS.FILTERING_DIMENSION, multidatasetVersionDto.getFilteringDimension());
    }

}
