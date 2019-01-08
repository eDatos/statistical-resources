package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

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
        return form.getValueAsString("Name RequiredTextItem"); // METAMAC-2866 Components name, where they should be defined?
    }

    private class ImportDbDataSourceForm extends CustomDynamicForm {

        public ImportDbDataSourceForm() {
            super();
            setMargin(5);

            // METAMAC-2866 Components name, where they should be defined?
            RequiredTextItem tableNameTextItem = new RequiredTextItem("Name RequiredTextItem", getConstants().datasetTableName());
            tableNameTextItem.setWidth("*");

            // METAMAC-2866 Components name, where they should be defined?
            CustomButtonItem saveButtonItem = new CustomButtonItem("Name CustomButtonItem", MetamacWebCommon.getConstants().accept());

            setFields(tableNameTextItem, saveButtonItem);
        }

        public HasClickHandlers getSaveButtonHandlers() {
            return getItem("Name CustomButtonItem");
        }
    }
}
