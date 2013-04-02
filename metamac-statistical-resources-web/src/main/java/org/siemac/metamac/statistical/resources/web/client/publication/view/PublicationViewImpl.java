package org.siemac.metamac.statistical.resources.web.client.publication.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationMetadataTabPresenter.PublicationMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationPresenter;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationStructureTabPresenter.PublicationStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationUiHandlers;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class PublicationViewImpl extends ViewWithUiHandlers<PublicationUiHandlers> implements PublicationPresenter.PublicationView {

    private VLayout    panel;
    private TitleLabel titleLabel;

    private TabSet     tabSet;
    private Tab        collectionMetadataTab;
    private Tab        collectionStructureTab;

    @Inject
    public PublicationViewImpl(PublicationMetadataTabView metadataView, PublicationStructureTabView structureView) {
        super();
        panel = new VLayout();

        titleLabel = new TitleLabel(new String());
        titleLabel.setStyleName("sectionTitleLeftMargin");
        titleLabel.setVisibility(Visibility.HIDDEN);

        // TABS
        tabSet = new TabSet();
        tabSet.setMargin(10);

        collectionMetadataTab = new Tab(getConstants().collectionMetadata());
        collectionStructureTab = new Tab(getConstants().publicationStructure());

        tabSet.setTabs(collectionMetadataTab, collectionStructureTab);

        panel.addMember(titleLabel);
        panel.addMember(tabSet);
        bindEvents();
    }

    private void bindEvents() {
        collectionMetadataTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                getUiHandlers().goToPublicationMetadata();
            }
        });
        collectionStructureTab.addTabSelectedHandler(new TabSelectedHandler() {

            @Override
            public void onTabSelected(TabSelectedEvent event) {
                getUiHandlers().goToPublicationStructure();
            }
        });
    }

    @Override
    public void setPublication(PublicationDto collectionDto) {
        titleLabel.setContents(InternationalStringUtils.getLocalisedString(collectionDto.getTitle()));
        titleLabel.show();
    }

    @Override
    public void showMetadata() {
        tabSet.selectTab(collectionMetadataTab);
        getUiHandlers().goToPublicationMetadata();
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == PublicationPresenter.TYPE_SetContextAreaMetadata) {
            collectionMetadataTab.setPane((Canvas) content);
        } else if (slot == PublicationPresenter.TYPE_SetContextAreaStructure) {
            collectionStructureTab.setPane((Canvas) content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
