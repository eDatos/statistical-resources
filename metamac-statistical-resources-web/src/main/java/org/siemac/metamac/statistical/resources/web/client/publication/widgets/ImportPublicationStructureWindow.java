package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.shared.utils.StatisticalResourcesSharedTokens.UPLOAD_PARAM_LANGUAGE;
import static org.siemac.metamac.statistical.resources.web.shared.utils.StatisticalResourcesSharedTokens.UPLOAD_PARAM_PUBLICATION_VERSION_URN;
import static org.siemac.metamac.statistical.resources.web.shared.utils.StatisticalResourcesSharedTokens.UPLOAD_RESOURCE_TYPE;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationStructureTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.shared.utils.ImportableResourceTypeEnum;
import org.siemac.metamac.web.common.client.widgets.ImportResourceWindow;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;

import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class ImportPublicationStructureWindow extends ImportResourceWindow {

    private PublicationStructureTabUiHandlers uiHandlers;

    public ImportPublicationStructureWindow() {
        super(getConstants().actionImport());

        UploadStructureForm form = new UploadStructureForm();
        setForm(form);
    }

    public void setPublicationVersionUrn(String urn) {
        ((HiddenItem) form.getItem(UPLOAD_PARAM_PUBLICATION_VERSION_URN)).setDefaultValue(urn);
    }

    private class UploadStructureForm extends UploadForm {

        public UploadStructureForm() {
            super(getConstants().publicationStructure());

            HiddenItem resourceTypeItem = new HiddenItem(UPLOAD_RESOURCE_TYPE);
            resourceTypeItem.setDefaultValue(ImportableResourceTypeEnum.PUBLICATION_VERSION_STRUCTURE.name());

            HiddenItem publicationVersionUrnItem = new HiddenItem(UPLOAD_PARAM_PUBLICATION_VERSION_URN);

            CustomSelectItem languageItem = new CustomSelectItem(UPLOAD_PARAM_LANGUAGE, getConstants().publicationStructureImportationLanguage());
            languageItem.setValueMap(CommonUtils.getEditionLanguagesHashMap());
            languageItem.setWidth(50);

            addFieldsInThePenultimePosition(resourceTypeItem, publicationVersionUrnItem, languageItem);
        }
    }

    @Override
    public String getRelativeURL(String url) {
        return StatisticalResourcesWeb.getRelativeURL(url);
    }

    @Override
    public void showWaitPopup() {
        getUiHandlers().showWaitPopup();
    }

    public void setUiHandlers(PublicationStructureTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public PublicationStructureTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }
}
