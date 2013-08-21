package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.util.shared.BooleanUtils;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.CustomTabSet;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DatasetVersionsSectionStack;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.InformationLabel;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class DatasetViewImpl extends ViewWithUiHandlers<DatasetUiHandlers> implements DatasetPresenter.DatasetView {

    private VLayout                     panel;

    private TitleLabel                  titleLabel;
    private InformationLabel            informationLabel;

    private DatasetVersionsSectionStack versionsSectionStack;

    private CustomTabSet                tabSet;
    private Tab                         datasetMetadataTab;
    private Tab                         datasetDatasourcesTab;
    private Tab                         datasetAttributesTab;

    private DatasetVersionDto           datasetVersionDto;

    public DatasetViewImpl() {
        panel = new VLayout();

        titleLabel = new TitleLabel(new String());
        titleLabel.setVisible(false);

        informationLabel = new InformationLabel(StringUtils.EMPTY);
        informationLabel.setVisible(false);

        //
        // DATASET VERSIONS
        //

        versionsSectionStack = new DatasetVersionsSectionStack(getConstants().datasetVersions());
        versionsSectionStack.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                String urn = ((DatasetRecord) event.getRecord()).getUrn();
                getUiHandlers().goToDatasetVersion(urn);
            }
        });

        // TABS

        tabSet = new CustomTabSet();
        datasetMetadataTab = new Tab(getConstants().datasetMetadata());
        datasetDatasourcesTab = new Tab(getConstants().datasetDatasources());
        datasetAttributesTab = new Tab(getConstants().datasetAttributes());
        tabSet.setTabs(datasetMetadataTab, datasetDatasourcesTab, datasetAttributesTab);

        //
        // PANEL LAYOUT
        //

        VLayout subPanel = new VLayout();
        subPanel.setOverflow(Overflow.SCROLL);
        subPanel.setMembersMargin(5);
        subPanel.addMember(versionsSectionStack);

        VLayout tabSubPanel = new VLayout();
        tabSubPanel.addMember(titleLabel);
        tabSubPanel.addMember(informationLabel);
        tabSubPanel.addMember(tabSet);
        tabSubPanel.setMargin(15);
        subPanel.addMember(tabSubPanel);

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

        datasetAttributesTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                getUiHandlers().goToDatasetAttributes();
            }
        });
    }

    @Override
    public void setDataset(DatasetVersionDto datasetVersionDto) {
        this.datasetVersionDto = datasetVersionDto;
        setTitleLabelContents(datasetVersionDto);
        setInformationLabelContents(datasetVersionDto);
    }

    @Override
    public void setDatasetVersions(List<DatasetVersionDto> datasetVersionDtos) {
        versionsSectionStack.setDatasetVersions(datasetVersionDtos);
        versionsSectionStack.selectDatasetVersion(datasetVersionDto);
    }

    private void setTitleLabelContents(DatasetVersionDto datasetVersionDto) {
        titleLabel.setContents(InternationalStringUtils.getLocalisedString(datasetVersionDto.getTitle()));
        titleLabel.show();
    }

    private void setInformationLabelContents(DatasetVersionDto datasetVersionDto) {
        if (BooleanUtils.isTrue(datasetVersionDto.getIsTaskInBackground())) {
            String message = getMessages().datasetVersionInProcessInBackground();
            informationLabel.setContents(message);
            informationLabel.show();
        } else {
            informationLabel.setContents(StringUtils.EMPTY);
            informationLabel.hide();
        }
    }

    @Override
    public void selectMetadataTab() {
        tabSet.selectTab(datasetMetadataTab);
    }

    @Override
    public void selectDatasourcesTab() {
        tabSet.selectTab(datasetDatasourcesTab);
    }

    @Override
    public void selectAttributesTab() {
        tabSet.selectTab(datasetAttributesTab);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
