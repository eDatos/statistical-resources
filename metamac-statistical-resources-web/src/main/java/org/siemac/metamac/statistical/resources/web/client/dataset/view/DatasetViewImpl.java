package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.CustomTabSet;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetUiHandlers;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class DatasetViewImpl extends ViewWithUiHandlers<DatasetUiHandlers> implements DatasetPresenter.DatasetView {

    private VLayout      panel;

    private TitleLabel   titleLabel;
    private CustomTabSet tabSet;
    private Tab          datasetMetadataTab;
    private Tab          datasetDatasourcesTab;

    public DatasetViewImpl() {
        panel = new VLayout();

        titleLabel = new TitleLabel(new String());
        titleLabel.setVisibility(Visibility.HIDDEN);

        // TABS
        tabSet = new CustomTabSet();
        datasetMetadataTab = new Tab(getConstants().datasetMetadata());
        datasetDatasourcesTab = new Tab(getConstants().datasetDatasources());
        tabSet.setTabs(datasetMetadataTab, datasetDatasourcesTab);

        VLayout subPanel = new VLayout();
        subPanel.setOverflow(Overflow.SCROLL);
        subPanel.setMargin(15);
        subPanel.addMember(titleLabel);
        subPanel.addMember(tabSet);

        panel.addMember(subPanel);

        bindEvents();
    }

    private void bindEvents() {
        datasetMetadataTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                getUiHandlers().goToDatasetMetadata();
            }
        });

        datasetDatasourcesTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                getUiHandlers().goToDatasetDatasources();
            }
        });
    }

    @Override
    public void setDataset(DatasetVersionDto datasetDto) {
        titleLabel.setContents(InternationalStringUtils.getLocalisedString(datasetDto.getTitle()));
        titleLabel.show();
    }

    @Override
    public void showMetadata() {
        tabSet.selectTab(datasetMetadataTab);
        getUiHandlers().goToDatasetMetadata();
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == DatasetPresenter.TYPE_SetContextAreaMetadata) {
            datasetMetadataTab.setPane((Canvas) content);
        } else if (slot == DatasetPresenter.TYPE_SetContextAreaDatasources) {
            datasetDatasourcesTab.setPane((Canvas) content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
