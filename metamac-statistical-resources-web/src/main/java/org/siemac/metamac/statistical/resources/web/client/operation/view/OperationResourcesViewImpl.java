package org.siemac.metamac.statistical.resources.web.client.operation.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.collection.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.collection.widgets.PublicationListGrid;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
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
    private PublicationListGrid          publicationsListGrid;

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
        datasetsListGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                DatasetRecord record = (DatasetRecord) event.getRecord();
                uiHandlers.goToDataset(record.getUrn());
            }
        });

        SectionStackSection lastModifiedDatasetsSection = new SectionStackSection();
        lastModifiedDatasetsSection.setTitle(getConstants().datasetsLastModified());
        lastModifiedDatasetsSection.setExpanded(false);
        lastModifiedDatasetsSection.setItems(datasetsListGrid);
        sections.addSection(lastModifiedDatasetsSection);

        publicationsListGrid = new PublicationListGrid();
        publicationsListGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                PublicationRecord record = (PublicationRecord) event.getRecord();
                uiHandlers.goToPublication(record.getUrn());
            }
        });

        SectionStackSection lastModifiedPublicationsSection = new SectionStackSection();
        lastModifiedPublicationsSection.setTitle(getConstants().collectionLastModified());
        lastModifiedPublicationsSection.setExpanded(false);
        lastModifiedPublicationsSection.setItems(publicationsListGrid);
        sections.addSection(lastModifiedPublicationsSection);

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
    public void setPublications(List<PublicationDto> publicationDtos) {
        publicationsListGrid.setPublications(publicationDtos);
    }

    @Override
    public void setDatasets(List<DatasetDto> datasetsDtos) {
        datasetsListGrid.setDatasets(datasetsDtos);
    }

}
