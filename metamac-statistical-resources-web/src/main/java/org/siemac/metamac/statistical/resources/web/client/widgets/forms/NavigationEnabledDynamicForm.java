package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.handlers.CustomLinkItemNavigationClickHandler;
import org.siemac.metamac.web.common.client.widgets.handlers.ListRecordNavigationClickHandler;

public abstract class NavigationEnabledDynamicForm extends GroupDynamicForm {

    public NavigationEnabledDynamicForm(String groupTitle) {
        super(groupTitle);
    }

    protected ListRecordNavigationClickHandler getRecordNavigationHandler() {
        return new ListRecordNavigationClickHandler() {

            @Override
            public BaseUiHandlers getBaseUiHandlers() {
                return NavigationEnabledDynamicForm.this.getBaseUiHandlers();
            }
        };
    }

    protected CustomLinkItemNavigationClickHandler getCustomLinkItemNavigationClickHandler() {
        return new CustomLinkItemNavigationClickHandler() {

            @Override
            public BaseUiHandlers getBaseUiHandlers() {
                return NavigationEnabledDynamicForm.this.getBaseUiHandlers();
            }
        };
    }

    public abstract BaseUiHandlers getBaseUiHandlers();
}
