package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetUiHandlers;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class DatasetViewImpl extends ViewImpl implements DatasetPresenter.DatasetView {

    private DatasetUiHandlers uiHandlers;

    private VLayout           panel;

    private TitleLabel        titleLabel;
    private TabSet            tabSet;
    private Tab               datasetMetadataTab;
    private Tab               datasetDatasourcesTab;

    public DatasetViewImpl() {
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.AUTO);

        titleLabel = new TitleLabel(new String());
        titleLabel.setStyleName("sectionTitleLeftMargin");
        titleLabel.setVisibility(Visibility.HIDDEN);

        // TABS
        tabSet = new TabSet();
        tabSet.setMargin(10);

        datasetMetadataTab = new Tab(getConstants().datasetMetadata());
        datasetDatasourcesTab = new Tab(getConstants().datasetDatasources());

        tabSet.setTabs(datasetMetadataTab, datasetDatasourcesTab);

        panel.addMember(titleLabel);
        panel.addMember(tabSet);
        bindEvents();
    }

    private void bindEvents() {
        datasetMetadataTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                uiHandlers.goToDatasetMetadata();
            }
        });

        datasetDatasourcesTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                uiHandlers.goToDatasetDatasources();
            }
        });
    }

    @Override
    public void setDataset(DatasetDto datasetDto) {
        titleLabel.setContents(InternationalStringUtils.getLocalisedString(datasetDto.getTitle()));
        titleLabel.show();
    }

    @Override
    public void showMetadata() {
        tabSet.selectTab(datasetMetadataTab);
        uiHandlers.goToDatasetMetadata();
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

    @Override
    public void setUiHandlers(DatasetUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

}
