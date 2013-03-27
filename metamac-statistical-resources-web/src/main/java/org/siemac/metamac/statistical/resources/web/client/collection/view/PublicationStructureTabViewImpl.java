package org.siemac.metamac.statistical.resources.web.client.collection.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.PublicationStructureTabPresenter.CollectionStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.collection.widgets.PublicationStructurePanel;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.layout.VLayout;


public class PublicationStructureTabViewImpl extends ViewImpl implements CollectionStructureTabView {

    private VLayout panel;
    private PublicationStructurePanel structurePanel;

    private CollectionDto            collectionDto;
    
    @Inject
    public PublicationStructureTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(15);
        
        structurePanel = new PublicationStructurePanel();

        VLayout collectionStructureLayout = new VLayout();
        collectionStructureLayout.setMargin(15);
        collectionStructureLayout.addMember(new TitleLabel(getConstants().collectionStructure()));
        collectionStructureLayout.addMember(structurePanel);
        
        panel.addMember(structurePanel);
    }
    
    @Override
    public void setCollection(CollectionDto collectionDto) {
        this.collectionDto = collectionDto;

        structurePanel.setCollectionStructure(collectionDto.getStructure());
    }
    
    @Override
    public Widget asWidget() {
        return panel;
    }

}
