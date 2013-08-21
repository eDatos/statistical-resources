package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetAttributesTabPresenter.DatasetAttributesTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.model.ds.ExternalItemDS;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.widgets.layout.VLayout;

public class DatasetAttributesTabViewImpl extends ViewWithUiHandlers<DatasetAttributesTabUiHandlers> implements DatasetAttributesTabView {

    private VLayout            panel;
    private BaseCustomListGrid listGrid;

    public DatasetAttributesTabViewImpl() {

        // LIST

        listGrid = new BaseCustomListGrid();
        listGrid.setAutoFitMaxRecords(StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS);
        listGrid.setAutoFitData(Autofit.VERTICAL);
        listGrid.setMargin(15);
        CustomListGridField codeField = new CustomListGridField(ExternalItemDS.CODE, MetamacWebCommon.getConstants().externalItemCode());
        listGrid.setFields(codeField);

        // PANEL LAYOUT

        panel = new VLayout();
        panel.setAutoHeight();

        // TODO
        // VLayout subpanel = new VLayout();
        // subpanel.setOverflow(Overflow.SCROLL);
        // subpanel.addMember(listGrid);

        panel.addMember(listGrid);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setAttributes(List<ExternalItemDto> attributes) {
        listGrid.setData(RecordUtils.getExternalItemRecords(attributes));
    }

    @Override
    public void setUiHandlers(DatasetAttributesTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
    }
}
