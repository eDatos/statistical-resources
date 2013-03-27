package org.siemac.metamac.statistical.resources.web.client.collection.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.PublicationMetadataTabPresenter.CollectionMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.PublicationPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.PublicationStructureTabPresenter.CollectionStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.collection.view.handlers.PublicationUiHandlers;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class PublicationViewImpl extends ViewImpl implements PublicationPresenter.CollectionView {

    private PublicationUiHandlers uiHandlers;
    
    private VLayout                  panel;
    private TitleLabel               titleLabel;

    private TabSet tabSet; 
    private Tab collectionMetadataTab;
    private Tab collectionStructureTab;
    
    @Inject
    public PublicationViewImpl(CollectionMetadataTabView metadataView, CollectionStructureTabView structureView) {
        super();
        panel = new VLayout();
        
        titleLabel = new TitleLabel(new String());
        titleLabel.setStyleName("sectionTitleLeftMargin");
        titleLabel.setVisibility(Visibility.HIDDEN);

        // TABS
        tabSet = new TabSet();
        tabSet.setMargin(10);

        collectionMetadataTab = new Tab(getConstants().collectionMetadata());
        collectionStructureTab = new Tab(getConstants().collectionStructure());

        tabSet.setTabs(collectionMetadataTab, collectionStructureTab);

        panel.addMember(titleLabel);
        panel.addMember(tabSet);
        bindEvents();
    }
    
    
    private void bindEvents() {
        collectionMetadataTab.addTabSelectedHandler(new TabSelectedHandler() {
            @Override
            public void onTabSelected(TabSelectedEvent event) {
                uiHandlers.goToCollectionMetadata();
            }
        });
        collectionStructureTab.addTabSelectedHandler(new TabSelectedHandler() {
            @Override
            public void onTabSelected(TabSelectedEvent event) {
                uiHandlers.goToCollectionStructure();
            }
        });
    }

    @Override
    public void setCollection(CollectionDto collectionDto) {
        titleLabel.setContents(InternationalStringUtils.getLocalisedString(collectionDto.getTitle()));
        titleLabel.show();
    }
    
    @Override
    public void showMetadata() {
        tabSet.selectTab(collectionMetadataTab);
        uiHandlers.goToCollectionMetadata();
    }        
    
    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == PublicationPresenter.TYPE_SetContextAreaMetadata) {
            collectionMetadataTab.setPane((Canvas)content);
        } else if (slot == PublicationPresenter.TYPE_SetContextAreaStructure) {
            collectionStructureTab.setPane((Canvas)content);
        } else {
            super.setInSlot(slot, content);
        }
    }
    
    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(PublicationPresenter uiHandlers) {
        this.uiHandlers = uiHandlers;
    }


}
