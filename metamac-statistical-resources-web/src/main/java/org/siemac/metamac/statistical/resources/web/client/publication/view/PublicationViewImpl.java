package org.siemac.metamac.statistical.resources.web.client.publication.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.CustomTabSet;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationMetadataTabPresenter.PublicationMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationPresenter;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationStructureTabPresenter.PublicationStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationVersionsSectionStack;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class PublicationViewImpl extends ViewWithUiHandlers<PublicationUiHandlers> implements PublicationPresenter.PublicationView {

    private VLayout                         panel;
    private TitleLabel                      titleLabel;

    private PublicationVersionsSectionStack versionsSectionStack;

    private CustomTabSet                    tabSet;
    private Tab                             publicationMetadataTab;
    private Tab                             publicationStructureTab;

    private PublicationVersionDto           publicationVersionDto;

    @Inject
    public PublicationViewImpl(PublicationMetadataTabView metadataView, PublicationStructureTabView structureView) {
        super();
        panel = new VLayout();

        titleLabel = new TitleLabel(new String());
        titleLabel.setVisible(false);

        //
        // PUBLICATION VERSIONS
        //

        versionsSectionStack = new PublicationVersionsSectionStack(getConstants().publicationVersions());
        versionsSectionStack.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                String urn = ((PublicationRecord) event.getRecord()).getUrn();
                getUiHandlers().goToPublicationVersion(urn);
            }
        });

        // TABS

        tabSet = new CustomTabSet();

        publicationMetadataTab = new Tab(getConstants().publicationMetadata());
        publicationMetadataTab.setPane((Canvas) metadataView.asWidget());

        publicationStructureTab = new Tab(getConstants().publicationStructure());
        publicationStructureTab.setPane((Canvas) structureView.asWidget());

        tabSet.setTabs(publicationMetadataTab, publicationStructureTab);

        //
        // PANEL LAYOUT
        //

        VLayout subPanel = new VLayout();
        subPanel.setOverflow(Overflow.SCROLL);
        subPanel.setMembersMargin(5);
        subPanel.addMember(versionsSectionStack);

        VLayout tabSubPanel = new VLayout();
        tabSubPanel.addMember(titleLabel);
        tabSubPanel.addMember(tabSet);
        tabSubPanel.setMargin(15);
        subPanel.addMember(tabSubPanel);

        panel.addMember(subPanel);

        bindEvents();
    }

    private void bindEvents() {
        publicationMetadataTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                getUiHandlers().goToPublicationMetadata();
            }
        });
        publicationStructureTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                getUiHandlers().goToPublicationStructure();
            }
        });
    }

    @Override
    public void setPublication(PublicationVersionDto publicationVersionDto) {
        this.publicationVersionDto = publicationVersionDto;
        titleLabel.setContents(InternationalStringUtils.getLocalisedString(publicationVersionDto.getTitle()));
        titleLabel.show();
    }

    @Override
    public void setPublicationVersionsAndSelectCurrent(String currentUrn, List<PublicationVersionBaseDto> publicationVersionBaseDtos) {
        versionsSectionStack.setPublicationVersions(publicationVersionBaseDtos);
        versionsSectionStack.selectPublicationVersion(currentUrn);
    }

    @Override
    public void selectMetadataTab() {
        tabSet.selectTab(publicationMetadataTab);
    }

    @Override
    public void selectStructureTab() {
        tabSet.selectTab(publicationStructureTab);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
