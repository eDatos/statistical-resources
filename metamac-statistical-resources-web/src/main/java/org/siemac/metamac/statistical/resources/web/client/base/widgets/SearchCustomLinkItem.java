package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.FormItemUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomLinkItem;
import org.siemac.metamac.web.common.client.widgets.handlers.CustomLinkItemNavigationClickHandler;

import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class SearchCustomLinkItem extends CustomLinkItem {

    protected FormItemIcon searchIcon;
    protected FormItemIcon clearIcon;

    public SearchCustomLinkItem(String name, String title) {
        super(name, title);
        create();
    }

    public SearchCustomLinkItem(String name, String title, CustomLinkItemNavigationClickHandler clickHandler) {
        super(name, title, clickHandler);
        create();
    }

    private void create() {

        setTitleStyle("formTitle"); // no bold title (unless it is required)

        setValidateOnChange(true);
        setValidateOnExit(true);

        searchIcon = new FormItemIcon();
        searchIcon.setSrc(GlobalResources.RESOURCE.search().getURL());

        clearIcon = new FormItemIcon();
        clearIcon.setSrc(GlobalResources.RESOURCE.deleteListGrid().getURL());
        clearIcon.setHeight(14);
        clearIcon.setWidth(14);
        clearIcon.addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                clearValue();
                event.getForm().markForRedraw();
            }
        });

        setIcons(searchIcon, clearIcon);
    }

    public FormItemIcon getSearchIcon() {
        return searchIcon;
    }

    public FormItemIcon getClearIcon() {
        return clearIcon;
    }

    public void setRequired(boolean required) {
        if (required) {
            setTitleStyle("requiredFormLabel");
            setValidators(FormItemUtils.getCustomRequiredValidatorForSearchItem()); // Set requited with a custom validator
        }
    }

    @Override
    public void clearValue() {
        super.clearValue();
    }
}
