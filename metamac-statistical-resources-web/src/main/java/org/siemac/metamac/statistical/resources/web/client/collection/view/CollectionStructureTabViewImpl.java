package org.siemac.metamac.statistical.resources.web.client.collection.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionStructureTabPresenter.CollectionStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.collection.widgets.CollectionStructurePanel;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.layout.VLayout;


public class CollectionStructureTabViewImpl extends ViewImpl implements CollectionStructureTabView {

    private VLayout panel;
    private CollectionStructurePanel structurePanel;

    private CollectionDto            collectionDto;
    
    @Inject
    public CollectionStructureTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(15);
        
        structurePanel = new CollectionStructurePanel();

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
