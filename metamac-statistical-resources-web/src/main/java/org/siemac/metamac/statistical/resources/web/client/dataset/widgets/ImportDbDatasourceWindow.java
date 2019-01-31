package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

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
import com.smartgwt.client.widgets.form.validator.LengthRangeValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;

public class ImportDbDatasourceWindow extends Window {

    private ImportDbDataSourceForm form;

    public ImportDbDatasourceWindow() {
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

        form = new ImportDbDataSourceForm();
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

    private class ImportDbDataSourceForm extends CustomDynamicForm {

        private static final String TABLE_NAME_REGULAR_EXPRESSION = "^[a-zA-Z_][a-zA-Z0-9_]*$";

        public ImportDbDataSourceForm() {
            super();
            setMargin(5);

            RequiredTextItem tableNameTextItem = getTableNameTextItem();

            // TODO METAMAC-2866 Components name, where they should be defined?
            CustomButtonItem saveButtonItem = new CustomButtonItem("button-dbimport", MetamacWebCommon.getConstants().accept());

            setFields(tableNameTextItem, saveButtonItem);
        }

        public HasClickHandlers getSaveButtonHandlers() {
            return getItem("button-dbimport");
        }

        private RequiredTextItem getTableNameTextItem() {

            LengthRangeValidator lengthRangeValidator = getTableNameLengthValidator();

            RegExpValidator regExpValidator = getTableNameFormatValidator();

            RequiredTextItem tableNameTextItem = new RequiredTextItem(DatasourceDS.TABLE_NAME, getConstants().datasetTableName());
            tableNameTextItem.setWidth("*");
            tableNameTextItem.setValidators(lengthRangeValidator, regExpValidator);

            return tableNameTextItem;

        }

        /**
         * Create a validator to check the table name format: in sql standard must begin with a letter or an underscore, subsequent characters can be letters, underscores or digits (0-9)
         * 
         * @see https://www.postgresql.org/docs/8.0/sql-syntax.html#SQL-SYNTAX-IDENTIFIERS
         * @return validator that checks the table name format and shows a custom error message in case the table name format is not valid
         */
        private RegExpValidator getTableNameFormatValidator() {
            RegExpValidator regExpValidator = new RegExpValidator(ImportDbDataSourceForm.TABLE_NAME_REGULAR_EXPRESSION);
            regExpValidator.setErrorMessage(getMessages().errorTableNameFormat());
            return regExpValidator;
        }

        /**
         * Creates a validator to check table name length: in postgresql, by default, the maximum identifier length is 63, longer names will be truncated
         * 
         * @see https://www.postgresql.org/docs/8.0/sql-syntax.html#SQL-SYNTAX-IDENTIFIERS
         * @return validator that checks that the table name length is between 1 and 63 and shows a custom error message in case the table name length is not valid
         */
        private LengthRangeValidator getTableNameLengthValidator() {
            LengthRangeValidator lengthRangeValidator = new LengthRangeValidator();
            lengthRangeValidator.setMin(1);
            lengthRangeValidator.setMax(63);
            lengthRangeValidator.setErrorMessage(getMessages().errorTableNameLength());
            return lengthRangeValidator;
        }
    }
}
