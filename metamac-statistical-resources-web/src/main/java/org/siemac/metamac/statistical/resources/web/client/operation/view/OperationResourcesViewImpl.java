package org.siemac.metamac.statistical.resources.web.client.operation.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.collection.model.record.CollectionRecord;
import org.siemac.metamac.statistical.resources.web.client.collection.widgets.CollectionListGrid;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DatasetListGrid;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationResourcesPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.view.handlers.OperationResourcesUiHandlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class OperationResourcesViewImpl extends ViewImpl implements OperationResourcesPresenter.OperationResourcesView {

    private OperationResourcesUiHandlers uiHandlers;

    private DatasetListGrid              datasetsListGrid;
    private CollectionListGrid           collectionsListGrid;

    private VLayout                      panel;
    private SectionStack                 sections;

    @Inject
    public OperationResourcesViewImpl() {
        sections = new SectionStack();
        sections.setWidth100();
        sections.setVisibilityMode(VisibilityMode.MULTIPLE);
        sections.setAnimateSections(true);
        sections.setOverflow(Overflow.HIDDEN);

        datasetsListGrid = new DatasetListGrid();

        SectionStackSection lastModifiedDatasetsSection = new SectionStackSection();
        lastModifiedDatasetsSection.setTitle(getConstants().datasetsLastModified());
        lastModifiedDatasetsSection.setExpanded(false);
        lastModifiedDatasetsSection.setItems(datasetsListGrid);
        sections.addSection(lastModifiedDatasetsSection);

        collectionsListGrid = new CollectionListGrid();
        collectionsListGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                CollectionRecord record = (CollectionRecord) event.getRecord();
                uiHandlers.goToCollection(record.getUrn());
            }
        });

        SectionStackSection lastModifiedCollectionsSection = new SectionStackSection();
        lastModifiedCollectionsSection.setTitle(getConstants().collectionLastModified());
        lastModifiedCollectionsSection.setExpanded(false);
        lastModifiedCollectionsSection.setItems(collectionsListGrid);
        sections.addSection(lastModifiedCollectionsSection);

        SectionStackSection lastModifiedQueriesSection = new SectionStackSection();
        lastModifiedQueriesSection.setTitle(getConstants().queryLastModified());
        lastModifiedQueriesSection.setExpanded(false);
        // TODO lastModifiedQueriesSection.setItems(queriesListGrid);
        sections.addSection(lastModifiedQueriesSection);

        panel = new VLayout();
        panel.addMember(sections);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(OperationResourcesUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public void setCollections(List<CollectionDto> collectionDtos) {
        collectionsListGrid.setCollections(collectionDtos);
    }

}
