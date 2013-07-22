package org.siemac.metamac.statistical.resources.web.client.publication.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
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
    private Tab        publicationMetadataTab;
    private Tab        publicationStructureTab;

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

        publicationMetadataTab = new Tab(getConstants().collectionMetadata());
        publicationStructureTab = new Tab(getConstants().publicationStructure());

        tabSet.setTabs(publicationMetadataTab, publicationStructureTab);

        panel.addMember(titleLabel);
        panel.addMember(tabSet);
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
        // TODO Update the title label when the title is changed!!
        titleLabel.setContents(InternationalStringUtils.getLocalisedString(publicationVersionDto.getTitle()));
        titleLabel.show();
    }

    @Override
    public void showMetadata() {
        tabSet.selectTab(publicationMetadataTab);
        getUiHandlers().goToPublicationMetadata();
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == PublicationPresenter.TYPE_SetContextAreaMetadata) {
            publicationMetadataTab.setPane((Canvas) content);
        } else if (slot == PublicationPresenter.TYPE_SetContextAreaStructure) {
            publicationStructureTab.setPane((Canvas) content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
