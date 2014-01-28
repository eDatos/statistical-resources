package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.CategorisationDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetCategorisationsTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.enums.DatasetTabTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.events.SelectDatasetTabEvent;
import org.siemac.metamac.statistical.resources.web.client.events.SetDatasetEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.CreateDatasetCategorisationsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.CreateDatasetCategorisationsResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteCategorisationsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteCategorisationsResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.EndCategorisationsValidityAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.EndCategorisationsValidityResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetCategorisationsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetCategorisationsResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCategoriesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCategoriesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCategorySchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCategorySchemesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class DatasetCategorisationsTabPresenter extends Presenter<DatasetCategorisationsTabPresenter.DatasetCategorisationsTabView, DatasetCategorisationsTabPresenter.DatasetCategorisationsTabProxy>
        implements
            DatasetCategorisationsTabUiHandlers {

    private DispatchAsync dispatcher;
    private PlaceManager  placeManager;

    private String        datasetVersionUrn;

    public interface DatasetCategorisationsTabView extends View, HasUiHandlers<DatasetCategorisationsTabUiHandlers> {

        void setCategorisations(DatasetVersionDto datasetVersionDto, List<CategorisationDto> categorisations);

        void setCategoriesForCategorisations(List<ExternalItemDto> categories, Integer firstResultOut, Integer totalResults);

        void setCategorySchemesForCategorisations(List<ExternalItemDto> categorySchemes, Integer firstResultOut, Integer totalResults);

    }

    @ProxyCodeSplit
    @NameToken(NameTokens.datasetCategorisationsPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface DatasetCategorisationsTabProxy extends Proxy<DatasetCategorisationsTabPresenter>, Place {
    }

    @Inject
    public DatasetCategorisationsTabPresenter(EventBus eventBus, DatasetCategorisationsTabView view, DatasetCategorisationsTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @TitleFunction
    public String title() {
        return getConstants().breadcrumbDatasetCategorisations();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, DatasetPresenter.TYPE_SetContextAreaDataset, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        SelectDatasetTabEvent.fire(this, DatasetTabTypeEnum.CATEGORISATIONS);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(datasetCode)) {
            String operationUrn = CommonUtils.generateStatisticalOperationUrn(operationCode);

            if (!CommonUtils.isUrnFromSelectedStatisticalOperation(operationUrn)) {
                retrieveOperation(operationUrn);
            } else {
                loadInitialData();
            }

        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    private void loadInitialData() {
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        datasetVersionUrn = CommonUtils.generateDatasetUrn(datasetCode);
        retrieveDatasetAndCategorisations(datasetVersionUrn);
    }

    private void retrieveOperation(String urn) {
        dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationResult>(this) {

            @Override
            public void onWaitSuccess(GetStatisticalOperationResult result) {
                StatisticalResourcesDefaults.setSelectedStatisticalOperation(result.getOperation());
                loadInitialData();
            }
        });
    }

    private void retrieveDatasetAndCategorisations(String datasetUrn) {
        dispatcher.execute(new GetDatasetVersionAction(datasetUrn), new WaitingAsyncCallbackHandlingError<GetDatasetVersionResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetVersionResult result) {
                SetDatasetEvent.fire(DatasetCategorisationsTabPresenter.this, result.getDatasetVersionDto());
                retrieveCategorisations(result.getDatasetVersionDto());
            }
        });
    }

    private void retrieveCategorisations(final DatasetVersionDto datasetVersionDto) {
        dispatcher.execute(new GetDatasetCategorisationsAction(datasetVersionDto.getUrn()), new WaitingAsyncCallbackHandlingError<GetDatasetCategorisationsResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetCategorisationsResult result) {
                getView().setCategorisations(datasetVersionDto, result.getCategorisations());
            }
        });
    }

    @Override
    public void createCategorisations(final String datasetVersionUrn, List<String> categoryUrns) {
        dispatcher.execute(new CreateDatasetCategorisationsAction(datasetVersionUrn, categoryUrns), new WaitingAsyncCallbackHandlingError<CreateDatasetCategorisationsResult>(this) {

            @Override
            public void onWaitSuccess(CreateDatasetCategorisationsResult result) {
                retrieveDatasetAndCategorisations(datasetVersionUrn);
            }

            @Override
            public void onWaitFailure(Throwable caught) {
                super.onWaitFailure(caught);
                retrieveDatasetAndCategorisations(datasetVersionUrn);
            }
        });
    }

    @Override
    public void deleteCategorisations(final String datasetVersionUrn, List<String> urns) {
        dispatcher.execute(new DeleteCategorisationsAction(urns), new WaitingAsyncCallbackHandlingError<DeleteCategorisationsResult>(this) {

            @Override
            public void onWaitSuccess(DeleteCategorisationsResult result) {
                retrieveDatasetAndCategorisations(datasetVersionUrn);
            }

            @Override
            public void onWaitFailure(Throwable caught) {
                super.onWaitFailure(caught);
                retrieveDatasetAndCategorisations(datasetVersionUrn);
            }
        });
    }

    @Override
    public void endCategorisationsValidity(final String datasetVersionUrn, List<String> urns, Date validTo) {
        dispatcher.execute(new EndCategorisationsValidityAction(urns, validTo), new WaitingAsyncCallbackHandlingError<EndCategorisationsValidityResult>(this) {

            @Override
            public void onWaitSuccess(EndCategorisationsValidityResult result) {
                retrieveDatasetAndCategorisations(datasetVersionUrn);
            }

            @Override
            public void onWaitFailure(Throwable caught) {
                super.onWaitFailure(caught);
                retrieveDatasetAndCategorisations(datasetVersionUrn);
            }
        });

    }
    @Override
    public void retrieveCategoriesForCategorisations(int firstResult, int maxResults, ItemSchemeWebCriteria criteria) {
        dispatcher.execute(new GetCategoriesPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetCategoriesPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetCategoriesPaginatedListResult result) {
                getView().setCategoriesForCategorisations(result.getCategories(), result.getFirstResultOut(), result.getTotalResults());

            }
        });
    }

    @Override
    public void retrieveCategorySchemesForCategorisations(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        dispatcher.execute(new GetCategorySchemesPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetCategorySchemesPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetCategorySchemesPaginatedListResult result) {
                getView().setCategorySchemesForCategorisations(result.getCategorySchemes(), result.getFirstResultOut(), result.getTotalResults());

            }
        });
    }

    //
    // NAVIGATION
    //

    @Override
    public void goTo(List<PlaceRequest> location) {
        if (location != null && !location.isEmpty()) {
            placeManager.revealPlaceHierarchy(location);
        }
    }
}
