package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.shared.utils.StatisticalResourcesSharedTokens;
import org.siemac.metamac.web.common.client.widgets.ImportResourceWindow;
import org.siemac.metamac.web.common.client.widgets.InformationLabel;

import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class ImportDatasourcesWindow extends ImportResourceWindow {

    public ImportDatasourcesWindow() {
        super(getConstants().actionLoadDatasources());

        InformationLabel informationLabel = new InformationLabel(getMessages().datasourceImportationInfoMessage());
        informationLabel.setWidth(300);
        informationLabel.setMargin(10);
        body.addMember(informationLabel);

        UploadDatasourceForm form = new UploadDatasourceForm();
        setForm(form);
    }

    public void setDatasetVersion(String datasetVersionUrn) {
        ((HiddenItem) form.getItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DATASET_VERSION_URN)).setDefaultValue(datasetVersionUrn);
        ((HiddenItem) form.getItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_OPERATION_URN)).setDefaultValue(StatisticalResourcesDefaults.getSelectedStatisticalOperation().getUrn());
    }

    private class UploadDatasourceForm extends UploadForm {

        public UploadDatasourceForm() {
            super(getConstants().datasources());

            HiddenItem datasetVersionUrnItem = new HiddenItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_DATASET_VERSION_URN);
            HiddenItem operationUrnItem = new HiddenItem(StatisticalResourcesSharedTokens.UPLOAD_PARAM_OPERATION_URN);

            addFieldsInThePenultimePosition(datasetVersionUrnItem, operationUrnItem);
        }
    }

    @Override
    public String getRelativeURL(String url) {
        return StatisticalResourcesWeb.getRelativeURL(url);
    }
}
