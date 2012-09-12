package org.siemac.metamac.statistical.resources.web.client.collection.widgets;

import org.siemac.metamac.statistical.resources.core.dto.CollectionStructureHierarchyDto;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;

import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderClickHandler;
import com.smartgwt.client.widgets.tree.events.FolderContextClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderContextClickHandler;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;
import com.smartgwt.client.widgets.tree.events.LeafContextClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafContextClickHandler;

public class CollectionStructurePanel extends HLayout {

    private CollectionTreeGrid          collectionStructureTreeGrid;
    private InternationalMainFormLayout collectionStructureMainFormLayout;

    public CollectionStructurePanel() {

        // TreeGrid

        collectionStructureTreeGrid = new CollectionTreeGrid();

        collectionStructureTreeGrid.getCreateElementMenuItem().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                collectionStructureMainFormLayout.show();
            }
        });

        collectionStructureTreeGrid.addFolderContextClickHandler(new FolderContextClickHandler() {

            @Override
            public void onFolderContextClick(final FolderContextClickEvent event) {
                collectionStructureTreeGrid.showContextMenu();
            }
        });

        collectionStructureTreeGrid.addLeafContextClickHandler(new LeafContextClickHandler() {

            @Override
            public void onLeafContextClick(LeafContextClickEvent event) {
                collectionStructureTreeGrid.showContextMenu();

            }
        });

        collectionStructureTreeGrid.addFolderClickHandler(new FolderClickHandler() {

            @Override
            public void onFolderClick(FolderClickEvent event) {
                collectionStructureMainFormLayout.show();
            }
        });

        collectionStructureTreeGrid.addLeafClickHandler(new LeafClickHandler() {

            @Override
            public void onLeafClick(LeafClickEvent event) {
                collectionStructureMainFormLayout.show();
            }
        });

        // MainFormLayout

        collectionStructureMainFormLayout = new InternationalMainFormLayout();
        collectionStructureMainFormLayout.setVisibility(Visibility.HIDDEN);

        addMember(collectionStructureTreeGrid);
        addMember(collectionStructureMainFormLayout);
    }

    public void setCollectionStructure(CollectionStructureHierarchyDto structureHierarchyDto) {
        collectionStructureTreeGrid.setCollectionStructure(structureHierarchyDto);
    }

}
