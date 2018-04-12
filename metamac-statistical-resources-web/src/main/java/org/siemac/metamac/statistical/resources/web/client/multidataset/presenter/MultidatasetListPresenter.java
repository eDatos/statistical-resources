package org.siemac.metamac.statistical.resources.web.client.multidataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;
import static org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils.showMessageAfterMultipleResourcesLifeCycleUpdate;

import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.navigation.shared.NameTokens;
import org.siemac.metamac.statistical.resources.navigation.shared.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.StatisticalResourceBaseListPresenter;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.client.multidataset.view.handlers.MultidatasetListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.MultidatasetVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.DeleteMultidatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.DeleteMultidatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetMultidatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetMultidatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.SaveMultidatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.SaveMultidatasetVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.UpdateMultidatasetVersionsProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.UpdateMultidatasetVersionsProcStatusAction.Builder;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.UpdateMultidatasetVersionsProcStatusResult;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class MultidatasetListPresenter extends StatisticalResourceBaseListPresenter<MultidatasetListPresenter.MultidatasetListView, MultidatasetListPresenter.MultidatasetListProxy>
        implements
            MultidatasetListUiHandlers {

    private final PlaceManager placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.multidatasetsListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface MultidatasetListProxy extends Proxy<MultidatasetListPresenter>, Place {
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return StatisticalResourcesWeb.getConstants().breadcrumbMultidatasets();
    }

    public interface MultidatasetListView extends StatisticalResourceBaseListPresenter.StatisticalResourceBaseListView, HasUiHandlers<MultidatasetListUiHandlers> {

        void setMultidatasetPaginatedList(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos, int firstResult, int totalResults);

        // Search
        void clearSearchSection();
        MultidatasetVersionWebCriteria getMultidatasetVersionWebCriteria();
        void setStatisticalOperationsForSearchSection(GetStatisticalOperationsPaginatedListResult result);
    }

    @Inject
    public MultidatasetListPresenter(EventBus eventBus, MultidatasetListView multidatasetListView, MultidatasetListProxy multidatasetListProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, multidatasetListView, multidatasetListProxy, dispatcher);
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    protected void onReset() {
        super.onReset();
        SetTitleEvent.fire(this, getConstants().multidatasets());
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode)) {
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
        getView().clearSearchSection();

        MultidatasetVersionWebCriteria multidatasetVersionWebCriteria = new MultidatasetVersionWebCriteria();
        multidatasetVersionWebCriteria.setStatisticalOperationUrn(StatisticalResourcesDefaults.getSelectedStatisticalOperation().getUrn());

        retrieveMultidatasets(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, multidatasetVersionWebCriteria);
    }

    @Override
    public void retrieveMultidatasets(int firstResult, int maxResults, MultidatasetVersionWebCriteria criteria) {
        dispatcher.execute(new GetMultidatasetVersionsAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetMultidatasetVersionsResult>(this) {

            @Override
            public void onWaitSuccess(GetMultidatasetVersionsResult result) {
                getView().setMultidatasetPaginatedList(result.getMultidatasetBaseDtos(), result.getFirstResultOut(), result.getTotalResults());
            }
        });
    }

    @Override
    public void createMultidataset(MultidatasetVersionDto multidatasetDto) {
        dispatcher.execute(new SaveMultidatasetVersionAction(multidatasetDto, StatisticalResourcesDefaults.getSelectedStatisticalOperation()),
                new WaitingAsyncCallbackHandlingError<SaveMultidatasetVersionResult>(this) {

                    @Override
                    public void onWaitSuccess(SaveMultidatasetVersionResult result) {
                        retrieveMultidatasets(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getMultidatasetVersionWebCriteria());
                    }
                });
    }

    @Override
    public void deleteMultidataset(List<String> urns) {
        dispatcher.execute(new DeleteMultidatasetVersionsAction(urns), new WaitingAsyncCallbackHandlingError<DeleteMultidatasetVersionsResult>(this) {

            @Override
            public void onWaitSuccess(DeleteMultidatasetVersionsResult result) {
                fireSuccessMessage(getMessages().multidatasetsDeleted());
                retrieveMultidatasets(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getMultidatasetVersionWebCriteria());
            };

            @Override
            public void onWaitFailure(Throwable caught) {
                super.onWaitFailure(caught);
                retrieveMultidatasets(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getMultidatasetVersionWebCriteria());
            }
        });
    }

    //
    // LIFECYCLE
    //

    @Override
    public void sendToProductionValidation(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos) {
        UpdateMultidatasetVersionsProcStatusAction action = new UpdateMultidatasetVersionsProcStatusAction(multidatasetVersionBaseDtos, LifeCycleActionEnum.SEND_TO_PRODUCTION_VALIDATION);
        updateMultidatasetVersionProcStatus(action, getMessages().lifeCycleResourcesSentToProductionValidation());
    }

    @Override
    public void sendToDiffusionValidation(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos) {
        UpdateMultidatasetVersionsProcStatusAction action = new UpdateMultidatasetVersionsProcStatusAction(multidatasetVersionBaseDtos, LifeCycleActionEnum.SEND_TO_DIFFUSION_VALIDATION);
        updateMultidatasetVersionProcStatus(action, getMessages().lifeCycleResourcesSentToDiffusionValidation());
    }

    @Override
    public void rejectValidation(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos, String reasonOfRejection) {
        UpdateMultidatasetVersionsProcStatusAction.Builder actionBuilder = new Builder(multidatasetVersionBaseDtos, LifeCycleActionEnum.REJECT_VALIDATION).reasonOfRejection(reasonOfRejection);
        updateMultidatasetVersionProcStatus(actionBuilder.build(), getMessages().lifeCycleResourcesRejectValidation());
    }

    @Override
    public void publish(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos) {
        UpdateMultidatasetVersionsProcStatusAction action = new UpdateMultidatasetVersionsProcStatusAction(multidatasetVersionBaseDtos, LifeCycleActionEnum.PUBLISH);
        updateMultidatasetVersionProcStatus(action, getMessages().lifeCycleResourcesPublish());
    }

    @Override
    public void version(List<MultidatasetVersionBaseDto> multidatasetVersionDtos, VersionTypeEnum versionType) {
        Builder builder = new UpdateMultidatasetVersionsProcStatusAction.Builder(multidatasetVersionDtos, LifeCycleActionEnum.VERSION);
        builder.versionType(versionType);
        updateMultidatasetVersionProcStatus(builder.build(), getMessages().lifeCycleResourcesVersion());
    }

    private void updateMultidatasetVersionProcStatus(UpdateMultidatasetVersionsProcStatusAction action, final String successMessage) {
        dispatcher.execute(action, new WaitingAsyncCallbackHandlingError<UpdateMultidatasetVersionsProcStatusResult>(this) {

            @Override
            public void onWaitSuccess(UpdateMultidatasetVersionsProcStatusResult result) {
                showMessageAfterMultipleResourcesLifeCycleUpdate(MultidatasetListPresenter.this, result.getNotificationException(), successMessage);
                retrieveMultidatasets(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getMultidatasetVersionWebCriteria());
            }
            @Override
            public void onWaitFailure(Throwable caught) {
                super.onWaitFailure(caught);
                retrieveMultidatasets(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getMultidatasetVersionWebCriteria());
            }
        });
    }

    //
    // RELATED RESOURCES
    //

    @Override
    public void retrieveStatisticalOperationsForSearchSection(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        dispatcher.execute(new GetStatisticalOperationsPaginatedListAction(firstResult, maxResults, criteria),
                new WaitingAsyncCallbackHandlingError<GetStatisticalOperationsPaginatedListResult>(this) {

                    @Override
                    public void onWaitSuccess(GetStatisticalOperationsPaginatedListResult result) {
                        getView().setStatisticalOperationsForSearchSection(result);
                    }
                });
    }

    //
    // NAVIGATION
    //

    @Override
    public void goToMultidataset(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.multidatasetPage).with(PlaceRequestParams.multidatasetParam, UrnUtils.removePrefix(urn)));
        }
    }
}
