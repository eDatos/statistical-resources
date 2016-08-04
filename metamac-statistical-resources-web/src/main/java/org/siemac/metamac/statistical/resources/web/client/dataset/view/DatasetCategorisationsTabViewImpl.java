package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.CategorisationDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetCategorisationsTabPresenter.DatasetCategorisationsTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetCategorisationsTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.model.record.CategorisationRecord;
import org.siemac.metamac.statistical.resources.web.client.widgets.CategorisationsPanel;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

public class DatasetCategorisationsTabViewImpl extends ViewWithUiHandlers<DatasetCategorisationsTabUiHandlers> implements DatasetCategorisationsTabView {

    private VLayout                     panel;

    private DatasetCategorisationsPanel categorisationsPanel;

    private DatasetVersionDto           datasetVersionDto;

    public DatasetCategorisationsTabViewImpl() {
        panel = new VLayout();
        panel.setHeight100();

        categorisationsPanel = new DatasetCategorisationsPanel();

        panel.addMember(categorisationsPanel);
    }

    @Override
    public void setCategorisations(DatasetVersionDto datasetVersionDto, List<CategorisationDto> categorisationDtos) {
        this.datasetVersionDto = datasetVersionDto;
        categorisationsPanel.setCategorisations(categorisationDtos);
        categorisationsPanel.updateVisibility(datasetVersionDto);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setCategorySchemesForCategorisations(List<ExternalItemDto> categorySchemes, Integer firstResultOut, Integer totalResults) {
        categorisationsPanel.setCategorySchemesForCategorisations(categorySchemes, firstResultOut, totalResults);
    }

    @Override
    public void setCategoriesForCategorisations(List<ExternalItemDto> categories, Integer firstResultOut, Integer totalResults) {
        categorisationsPanel.setCategoriesForCategorisations(categories, firstResultOut, totalResults);
    }

    private class DatasetCategorisationsPanel extends CategorisationsPanel {

        public void updateVisibility(DatasetVersionDto datasetVersionDto) {
            super.setCategorisedArtefactProcStatus(datasetVersionDto.getProcStatus());
            updateNewButtonVisibility();
        }

        @Override
        public void updateNewButtonVisibility() {
            if (DatasetClientSecurityUtils.canCreateCategorisation(datasetVersionDto)) {
                newCategorisationButton.show();
            } else {
                newCategorisationButton.hide();
            }
        }

        @Override
        public boolean canAllCategorisationsBeDeleted(ListGridRecord[] records) {
            for (ListGridRecord record : records) {
                if (record instanceof CategorisationRecord) {
                    CategorisationRecord categorisationRecord = (CategorisationRecord) record;
                    if (!DatasetClientSecurityUtils.canDeleteDatasetCategorisation(datasetVersionDto, categorisationRecord.getCategorisationDto())) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public boolean canCancelAllCategorisationsValidity(ListGridRecord[] records) {
            for (ListGridRecord record : records) {
                if (record instanceof CategorisationRecord) {
                    CategorisationRecord categorisationRecord = (CategorisationRecord) record;
                    if (!DatasetClientSecurityUtils.canEndCategorisationValidity(datasetVersionDto, categorisationRecord.getCategorisationDto())) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        protected void createCategorisations(List<String> categoriesUrns) {
            getUiHandlers().createCategorisations(datasetVersionDto.getUrn(), categoriesUrns);
        }

        @Override
        protected void deleteCategorisations(List<String> categorisationsUrns) {
            getUiHandlers().deleteCategorisations(datasetVersionDto.getUrn(), categorisationsUrns);
        }

        @Override
        protected void endCategorisationsValidity(List<String> selectedCategorisationUrns, Date endValidityDate) {
            getUiHandlers().endCategorisationsValidity(datasetVersionDto.getUrn(), selectedCategorisationUrns, endValidityDate);
        }

        @Override
        protected void retrieveCategoriesForCategorisations(int firstResult, int maxResults, SrmItemRestCriteria categoryWebCriteria) {
            getUiHandlers().retrieveCategoriesForCategorisations(firstResult, maxResults, categoryWebCriteria);
        }

        @Override
        protected void retrieveCategorySchemesForCategorisations(int firstResult, int maxResults, SrmExternalResourceRestCriteria categorySchemeWebCriteria) {
            getUiHandlers().retrieveCategorySchemesForCategorisations(firstResult, maxResults, categorySchemeWebCriteria);
        }
    }
}
