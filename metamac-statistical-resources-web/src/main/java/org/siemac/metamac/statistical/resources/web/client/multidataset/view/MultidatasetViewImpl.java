package org.siemac.metamac.statistical.resources.web.client.multidataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.CustomTabSet;
import org.siemac.metamac.statistical.resources.web.client.multidataset.model.record.MultidatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.multidataset.presenter.MultidatasetMetadataTabPresenter.MultidatasetMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.multidataset.presenter.MultidatasetPresenter;
import org.siemac.metamac.statistical.resources.web.client.multidataset.presenter.MultidatasetStructureTabPresenter.MultidatasetStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.multidataset.view.handlers.MultidatasetUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.multidataset.widgets.MultidatasetVersionsSectionStack;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;
import org.siemac.metamac.web.common.client.widgets.WarningLabel;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class MultidatasetViewImpl extends ViewWithUiHandlers<MultidatasetUiHandlers> implements MultidatasetPresenter.MultidatasetView {

    private VLayout                          panel;
    private TitleLabel                       titleLabel;
    private WarningLabel                     warningLabel;

    private MultidatasetVersionsSectionStack versionsSectionStack;

    private CustomTabSet                     tabSet;
    private Tab                              multidatasetMetadataTab;
    private Tab                              multidatasetStructureTab;

    @Inject
    public MultidatasetViewImpl(MultidatasetMetadataTabView metadataView, MultidatasetStructureTabView structureView) {
        super();
        panel = new VLayout();

        titleLabel = new TitleLabel(new String());
        titleLabel.setVisible(false);

        warningLabel = new WarningLabel();
        warningLabel.setVisible(false);
        warningLabel.setAlign(Alignment.CENTER);
        warningLabel.setMargin(50);
        warningLabel.setIconSize(24);

        //
        // MULTIDATASET VERSIONS
        //

        versionsSectionStack = new MultidatasetVersionsSectionStack(getConstants().multidatasetVersions());
        versionsSectionStack.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                String urn = ((MultidatasetRecord) event.getRecord()).getUrn();
                getUiHandlers().goToMultidatasetVersion(urn);
            }
        });

        // TABS

        tabSet = new CustomTabSet();

        multidatasetMetadataTab = new Tab(getConstants().multidatasetMetadata());
        multidatasetMetadataTab.setPane((Canvas) metadataView.asWidget());

        multidatasetStructureTab = new Tab(getConstants().multidatasetStructure());
        multidatasetStructureTab.setPane((Canvas) structureView.asWidget());

        tabSet.setTabs(multidatasetMetadataTab, multidatasetStructureTab);

        //
        // PANEL LAYOUT
        //

        VLayout subPanel = new VLayout();
        subPanel.setOverflow(Overflow.SCROLL);
        subPanel.setMembersMargin(5);
        subPanel.addMember(versionsSectionStack);

        VLayout tabSubPanel = new VLayout();
        tabSubPanel.addMember(titleLabel);
        tabSubPanel.addMember(warningLabel);
        tabSubPanel.addMember(tabSet);
        tabSubPanel.setMargin(15);
        subPanel.addMember(tabSubPanel);

        panel.addMember(subPanel);

        bindEvents();
    }

    private void bindEvents() {
        multidatasetMetadataTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                getUiHandlers().goToMultidatasetMetadata();
            }
        });
        multidatasetStructureTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                getUiHandlers().goToMultidatasetStructure();
            }
        });
    }

    @Override
    public void setMultidataset(MultidatasetVersionDto multidatasetVersionDto) {
        clearWarningLabel();
        setTitleLabelContents(multidatasetVersionDto);
        tabSet.show();
    }

    @Override
    public void showUnauthorizedResourceWarningMessage() {
        clearTitleLabel();
        tabSet.hide();
        setWarningLabelContents(getMessages().lifeCycleResourceRetrieveOperationNotAllowed(StatisticalResourcesWeb.getCurrentUser().getUserId()));
    }

    @Override
    public void setMultidatasetVersionsAndSelectCurrent(String currentUrn, List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos) {
        versionsSectionStack.setMultidatasetVersions(multidatasetVersionBaseDtos);
        versionsSectionStack.selectMultidatasetVersion(currentUrn);
    }

    @Override
    public void selectMetadataTab() {
        tabSet.selectTab(multidatasetMetadataTab);
    }

    @Override
    public void selectStructureTab() {
        tabSet.selectTab(multidatasetStructureTab);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    private void setTitleLabelContents(MultidatasetVersionDto multidatasetVersionDto) {
        titleLabel.setContents(InternationalStringUtils.getLocalisedString(multidatasetVersionDto.getTitle()));
        titleLabel.show();
    }

    private void clearWarningLabel() {
        warningLabel.setContents(StringUtils.EMPTY);
        warningLabel.hide();
    }

    private void clearTitleLabel() {
        titleLabel.setContents(StringUtils.EMPTY);
        titleLabel.hide();
    }

    private void setWarningLabelContents(String message) {
        warningLabel.setContents(message);
        warningLabel.show();
    }
}
