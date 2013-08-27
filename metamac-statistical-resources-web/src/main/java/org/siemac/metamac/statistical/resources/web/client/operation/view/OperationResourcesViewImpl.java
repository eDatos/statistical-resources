package org.siemac.metamac.statistical.resources.web.client.operation.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DatasetListGrid;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationResourcesPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.view.handlers.OperationResourcesUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationListGrid;
import org.siemac.metamac.statistical.resources.web.client.query.model.record.QueryRecord;
import org.siemac.metamac.statistical.resources.web.client.query.view.widgets.QueryListGrid;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class OperationResourcesViewImpl extends ViewWithUiHandlers<OperationResourcesUiHandlers> implements OperationResourcesPresenter.OperationResourcesView {

    private static final String DATASETS_SECTION_ID     = "datasets";
    private static final String PUBLICATIONS_SECTION_ID = "publications";
    private static final String QUERIES_SECTION_ID      = "queries";

    private DatasetListGrid     datasetsListGrid;
    private PublicationListGrid publicationsListGrid;
    private QueryListGrid       queriesListGrid;

    private VLayout             panel;
    private SectionStack        sections;

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
                getUiHandlers().goToDataset(record.getDatasetVersionBaseDto());
            }
        });

        SectionStackSection lastModifiedDatasetsSection = new SectionStackSection();
        lastModifiedDatasetsSection.setID(DATASETS_SECTION_ID);
        lastModifiedDatasetsSection.setTitle(getConstants().datasetsLastModified());
        lastModifiedDatasetsSection.setExpanded(false);
        lastModifiedDatasetsSection.setItems(datasetsListGrid);
        sections.addSection(lastModifiedDatasetsSection);

        publicationsListGrid = new PublicationListGrid();
        publicationsListGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                PublicationRecord record = (PublicationRecord) event.getRecord();
                getUiHandlers().goToPublication(record.getPublicationVersionBaseDto());
            }
        });

        SectionStackSection lastModifiedPublicationsSection = new SectionStackSection();
        lastModifiedPublicationsSection.setID(PUBLICATIONS_SECTION_ID);
        lastModifiedPublicationsSection.setTitle(getConstants().publicationLastModified());
        lastModifiedPublicationsSection.setExpanded(false);
        lastModifiedPublicationsSection.setItems(publicationsListGrid);
        sections.addSection(lastModifiedPublicationsSection);

        queriesListGrid = new QueryListGrid();
        queriesListGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                QueryRecord record = (QueryRecord) event.getRecord();
                getUiHandlers().goToQuery(record.getQueryVersionBaseDto());
            }
        });

        SectionStackSection lastModifiedQueriesSection = new SectionStackSection();
        lastModifiedQueriesSection.setID(QUERIES_SECTION_ID);
        lastModifiedQueriesSection.setTitle(getConstants().queryLastModified());
        lastModifiedQueriesSection.setExpanded(false);
        lastModifiedQueriesSection.setItems(queriesListGrid);
        sections.addSection(lastModifiedQueriesSection);

        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);
        panel.addMember(sections);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setPublications(List<PublicationVersionBaseDto> publicationVersionBaseDtos) {
        publicationsListGrid.setPublications(publicationVersionBaseDtos);
        sections.expandSection(PUBLICATIONS_SECTION_ID);
    }

    @Override
    public void setDatasets(List<DatasetVersionBaseDto> datasetVersionBaseDtos) {
        datasetsListGrid.setDatasets(datasetVersionBaseDtos);
        sections.expandSection(DATASETS_SECTION_ID);
    }

    @Override
    public void setQueries(List<QueryVersionBaseDto> queryVersionBaseDtos) {
        queriesListGrid.setQueries(queryVersionBaseDtos);
        sections.expandSection(QUERIES_SECTION_ID);
    }
}
