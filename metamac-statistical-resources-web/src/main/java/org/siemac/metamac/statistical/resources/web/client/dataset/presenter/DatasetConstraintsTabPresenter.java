package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.navigation.shared.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetConstraintsTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.enums.DatasetTabTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.events.SelectDatasetTabEvent;
import org.siemac.metamac.statistical.resources.web.client.events.SetDatasetEvent;
import org.siemac.metamac.statistical.resources.web.client.events.ShowUnauthorizedDatasetWarningMessageEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.CreateDatasetConstraintAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.CreateDatasetConstraintResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionConstraintAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionConstraintResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.client.utils.CommonErrorUtils;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;

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

public class DatasetConstraintsTabPresenter extends Presenter<DatasetConstraintsTabPresenter.DatasetConstraintsTabView, DatasetConstraintsTabPresenter.DatasetConstraintsTabProxy>
        implements
            DatasetConstraintsTabUiHandlers {

    private DispatchAsync dispatcher;
    private PlaceManager  placeManager;

    private String        datasetVersionUrn;

    public interface DatasetConstraintsTabView extends View, HasUiHandlers<DatasetConstraintsTabUiHandlers> {

        void setDatasetAndDimensions(DatasetVersionDto datasetVersion, List<DsdDimensionDto> dimensions);
        void setConstraint(ContentConstraintDto contentConstraintDto);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.datasetConstraintsPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface DatasetConstraintsTabProxy extends Proxy<DatasetConstraintsTabPresenter>, Place {
    }

    @Inject
    public DatasetConstraintsTabPresenter(EventBus eventBus, DatasetConstraintsTabView view, DatasetConstraintsTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, DatasetPresenter.TYPE_SetContextAreaDataset, this);
    }

    @TitleFunction
    public String title() {
        return getConstants().breadcrumbDatasetConstraints();
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        SelectDatasetTabEvent.fire(this, DatasetTabTypeEnum.CONSTRAINTS);
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

    private void retrieveOperation(String urn) {
        dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationResult>(this) {

            @Override
            public void onWaitSuccess(GetStatisticalOperationResult result) {
                StatisticalResourcesDefaults.setSelectedStatisticalOperation(result.getOperation());
                loadInitialData();
            }
        });
    }

    private void loadInitialData() {
        String datasetVersionCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        datasetVersionUrn = CommonUtils.generateDatasetUrn(datasetVersionCode);

        retrieveConstraint(datasetVersionUrn);
    }

    private void retrieveConstraint(final String datasetVersionUrn) {
        dispatcher.execute(new GetDatasetVersionConstraintAction(datasetVersionUrn), new WaitingAsyncCallbackHandlingError<GetDatasetVersionConstraintResult>(this) {

            @Override
            public void onWaitFailure(Throwable caught) {
                if (CommonErrorUtils.isOperationNotAllowedException(caught)) {
                    ShowUnauthorizedDatasetWarningMessageEvent.fire(DatasetConstraintsTabPresenter.this, datasetVersionUrn);
                } else {
                    super.onWaitFailure(caught);
                }
            }
            @Override
            public void onWaitSuccess(GetDatasetVersionConstraintResult result) {
                if (result.getContentConstraint() != null) {
                    // Load dimensions only if the constraint is enable
                    retrieveDimensions(datasetVersionUrn);
                }
                getView().setConstraint(result.getContentConstraint());
            }
        });
    }

    private void retrieveDimensions(final String datasetUrn) {
        dispatcher.execute(new GetDatasetDimensionsAction(datasetUrn), new WaitingAsyncCallbackHandlingError<GetDatasetDimensionsResult>(this) {

            @Override
            public void onWaitFailure(Throwable caught) {
                if (CommonErrorUtils.isOperationNotAllowedException(caught)) {
                    ShowUnauthorizedDatasetWarningMessageEvent.fire(DatasetConstraintsTabPresenter.this, datasetUrn);
                } else {
                    super.onWaitFailure(caught);
                }
            }
            @Override
            public void onWaitSuccess(GetDatasetDimensionsResult result) {
                SetDatasetEvent.fire(DatasetConstraintsTabPresenter.this, result.getDatasetVersion());
            }
        });
    }

    @Override
    public void createConstraint() {
        dispatcher.execute(new CreateDatasetConstraintAction(datasetVersionUrn), new WaitingAsyncCallbackHandlingError<CreateDatasetConstraintResult>(this) {

            @Override
            public void onWaitSuccess(CreateDatasetConstraintResult result) {
                // TODO METAMAC-1985 Auto-generated method stub
            }
        });
    }
}
