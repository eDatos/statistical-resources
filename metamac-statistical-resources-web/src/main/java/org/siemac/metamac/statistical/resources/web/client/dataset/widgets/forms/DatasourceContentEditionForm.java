package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.shared.utils.SharedTokens;

import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.FormErrorOrientation;
import com.smartgwt.client.types.FormMethod;
import com.smartgwt.client.widgets.form.fields.UploadItem;

public class DatasourceContentEditionForm extends GroupDynamicForm {

    private static final String TARGET = "uploadTarget";

    private CustomButtonItem    uploadButton;
    private UploadItem          uploadItem;

    public DatasourceContentEditionForm() {
        super(getConstants().formContent());
        setErrorOrientation(FormErrorOrientation.LEFT);
        setValidateOnChange(true);
        setAutoHeight();
        setCanSubmit(true);
        setWidth100();
        setMargin(8);
        setNumCols(2);
        setCellPadding(2);
        setWrapItemTitles(false);
        setTitleSuffix(" ");
        setRequiredTitleSuffix(" ");

        // // Initialize the hidden frame
        // NamedFrame frame = new NamedFrame(TARGET);
        // frame.setWidth("1px");
        // frame.setHeight("1px");
        // frame.setVisible(false);

        setEncoding(Encoding.MULTIPART);
        setMethod(FormMethod.POST);
        // setTarget(TARGET);

        StringBuilder url = new StringBuilder();
        url.append(SharedTokens.FILE_UPLOAD_DIR_PATH);
        setAction(StatisticalResourcesWeb.getRelativeURL(url.toString()));

        uploadItem = new UploadItem("filename");
        uploadItem.setTitle(getConstants().filename());
        uploadItem.setWidth(300);
        uploadItem.setRequired(true);

        uploadButton = new CustomButtonItem("button-import", getConstants().actionImport());
        uploadButton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                Object obj = uploadItem.getDisplayValue();
                if (validate() && obj != null) {
                    submitForm();
                }
            }
        });

        setFields(uploadItem, uploadButton);
    }

    public void uploadComplete(String fileName) {
        System.out.println(fileName);
    }

    public void uploadFailed(String fileName) {
        System.out.println(fileName);
    }

    private native void initComplete(DatasourceContentEditionForm upload) /*-{
                                                                          $wnd.uploadComplete = function(fileName) {
                                                                          upload.@org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasourceContentEditionForm::uploadComplete(Ljava/lang/String;)(fileName);
                                                                          };
                                                                          }-*/;

    private native void initUploadFailed(DatasourceContentEditionForm upload) /*-{
                                                                              $wnd.uploadFailed = function(fileName) {
                                                                              upload.@org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasourceContentEditionForm::uploadFailed(Ljava/lang/String;)(fileName);
                                                                              }
                                                                              }-*/;

}
