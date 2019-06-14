package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.statistical.resources.core.utils.shared.DatabaseDatasetImportSharedUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasourceDS;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

public class CreateDatabaseDatasourceWindow extends Window {

    private CreateDatabaseDataSourceForm form;

    public CreateDatabaseDatasourceWindow() {
        super();

        setWidth(450);
        setHeight(130);
        setTitle(getConstants().actionLoadDatasources());
        setShowMinimizeButton(false);
        setIsModal(true);
        setShowModalMask(true);
        setAutoCenter(true);
        addCloseClickHandler(new CloseClickHandler() {

            @Override
            public void onCloseClick(CloseClickEvent event) {
                hide();
            }
        });
        addVisibilityChangedHandler(new VisibilityChangedHandler() {

            @Override
            public void onVisibilityChanged(VisibilityChangedEvent event) {
                if (event.getIsVisible()) {
                    form.clearValues();
                }
            }
        });

        form = new CreateDatabaseDataSourceForm();
        addItem(form);
    }

    public boolean validate() {
        return form.validate();
    }

    public HasClickHandlers getSaveButtonHandlers() {
        return form.getSaveButtonHandlers();
    }

    public String getTableNameItemValue() {
        return form.getValueAsString(DatasourceDS.TABLE_NAME);
    }

    private class CreateDatabaseDataSourceForm extends CustomDynamicForm {

        private static final String FIELD_SAVE = "button-database-import";

        public CreateDatabaseDataSourceForm() {
            super();
            setMargin(5);

            RequiredTextItem tableNameTextItem = getTableNameTextItem();

            CustomButtonItem saveButtonItem = new CustomButtonItem(FIELD_SAVE, MetamacWebCommon.getConstants().accept());

            setFields(tableNameTextItem, saveButtonItem);
        }

        public HasClickHandlers getSaveButtonHandlers() {
            return getItem(FIELD_SAVE);
        }

        private RequiredTextItem getTableNameTextItem() {

            CustomValidator lengthRangeValidator = getTableNameLengthValidator();

            CustomValidator tableNameFormatValidator = getTableNameFormatValidator();

            RequiredTextItem tableNameTextItem = new RequiredTextItem(DatasourceDS.TABLE_NAME, getConstants().datasetTableName());
            tableNameTextItem.setWidth("*");
            tableNameTextItem.setValidators(lengthRangeValidator, tableNameFormatValidator);

            return tableNameTextItem;

        }

        private CustomValidator getTableNameFormatValidator() {
            CustomValidator customValidator = new CustomValidator() {

                @Override
                protected boolean condition(Object value) {
                    return DatabaseDatasetImportSharedUtils.checkTableNameFormat((String) value);
                }
            };
            customValidator.setErrorMessage(getMessages().errorTableNameFormat());
            return customValidator;
        }

        private CustomValidator getTableNameLengthValidator() {
            CustomValidator customValidator = new CustomValidator() {

                @Override
                protected boolean condition(Object value) {
                    return DatabaseDatasetImportSharedUtils.checkTableNameLength((String) value);
                }
            };
            customValidator.setErrorMessage(getMessages().errorTableNameLength());
            return customValidator;
        }
    }
}
