package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.tab.TabSet;

public class CustomTabSet extends TabSet {

    public CustomTabSet() {
        setHeight(50);
        setStyleName("marginTop15");
        setOverflow(Overflow.VISIBLE);
        setPaneContainerOverflow(Overflow.VISIBLE);
    }
}
