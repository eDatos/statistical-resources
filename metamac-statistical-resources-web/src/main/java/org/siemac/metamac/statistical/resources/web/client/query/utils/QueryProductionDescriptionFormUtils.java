package org.siemac.metamac.statistical.resources.web.client.query.utils;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.VersionableRelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils;

import com.smartgwt.client.widgets.form.fields.FormItem;

public class QueryProductionDescriptionFormUtils { // TODO EDATOS-3047 rename this class! or not!

    private QueryProductionDescriptionFormUtils() {
        // NOTHING TO DO HERE!
    }

    public static void setRelatedDataset(QueryVersionDto queryVersionDto, FormItem formItem) {
        if (queryVersionDto != null && queryVersionDto.getRelatedDatasetVersion() != null) {
            setRelatedDataset(queryVersionDto.getRelatedDatasetVersion(), formItem);
        }
    }

    public static void setRelatedDataset(VersionableRelatedResourceDto versionableRelatedResourceDto, FormItem formItem) {
        if (versionableRelatedResourceDto != null && versionableRelatedResourceDto.getLastVersion() != null) {
            if (versionableRelatedResourceDto.getLastVersion()) {
                formItem.setTitle(getConstants().queryDataset());
                StatisticalResourcesFormUtils.setRelatedResourceValue(formItem, versionableRelatedResourceDto);
            } else {
                formItem.setTitle(getConstants().queryDatasetVersion());
                StatisticalResourcesFormUtils.setVersionableRelatedResourceValue(formItem, versionableRelatedResourceDto);
            }
        } else {
            formItem.setTitle(getConstants().queryDataset());
            StatisticalResourcesFormUtils.setRelatedResourceValue(formItem, null);
        }
    }
}
