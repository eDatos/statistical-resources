package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.DataSetDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.VersionDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.VersionDatasetResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.utils.UrnUtils;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
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

public class DatasetPresenter extends Presenter<DatasetPresenter.DatasetView, DatasetPresenter.DatasetProxy> implements DatasetUiHandlers {

    private PlaceManager  placeManager;
    private DispatchAsync dispatcher;

    public interface DatasetView extends View, HasUiHandlers<DatasetUiHandlers> {
        void setDataset(DataSetDto datasetDto);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.datasetPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface DatasetProxy extends Proxy<DatasetPresenter>, Place {
    }

    @Inject
    public DatasetPresenter(EventBus eventBus, DatasetView view, DatasetProxy proxy, PlaceManager placeManager, DispatchAsync dispatcher) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }
    
    @TitleFunction
    public String getTitle() {
        return getConstants().dataset();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String datasetIdentifier = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        if (!StringUtils.isBlank(datasetIdentifier)) {
            retrieveDataset(datasetIdentifier);
        }
    }

    private void setDataset(DataSetDto datasetDto) {
        getView().setDataset(datasetDto);
    }

    @Override
    public void retrieveDataset(String datasetIdentifier) {
        String urn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, datasetIdentifier);
        dispatcher.execute(new GetDatasetAction(urn), new WaitingAsyncCallback<GetDatasetResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().datasetErrorRetrieve()), MessageTypeEnum.ERROR);
            }

            @Override
            public void onWaitSuccess(GetDatasetResult result) {
                setDataset(result.getDatasetDto());
            }
        });
    }
    
    @Override
    public void saveDataset(DataSetDto datasetDto) {
        dispatcher.execute(new SaveDatasetAction(datasetDto), new WaitingAsyncCallback<SaveDatasetResult>() {
            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().datasetErrorSave()), MessageTypeEnum.ERROR);
            }
            
            @Override
            public void onWaitSuccess(SaveDatasetResult result) {
                getView().setDataset(result.getSavedDataset());
            }
        });
    }
    
    @Override
    public void sendToProductionValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION, currentProcStatus),
                new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorSendToProductionValidation()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceSentToProductionValidation()), MessageTypeEnum.SUCCESS);
                        getView().setDataset(result.getDatasetDto());
                    }
                });
    }

    @Override
    public void sendToDiffusionValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION, currentProcStatus),
                new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorSendToDiffusionValidation()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceSentToDiffusionValidation()), MessageTypeEnum.SUCCESS);
                        getView().setDataset(result.getDatasetDto());
                    }
                });
    }

    @Override
    public void rejectValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.VALIDATION_REJECTED, currentProcStatus),
                new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorRejectValidation()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceRejectValidation()), MessageTypeEnum.SUCCESS);
                        getView().setDataset(result.getDatasetDto());
                    }
                });
    }

    @Override
    public void sendToPendingPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLICATION_PENDING, currentProcStatus),
                new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorSendToPendingPublication()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceSentToPendingPublication()), MessageTypeEnum.SUCCESS);
                        getView().setDataset(result.getDatasetDto());
                    }
                });
    }

    @Override
    public void programPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED, currentProcStatus),
                new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorProgramPublication()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceProgramPublication()), MessageTypeEnum.SUCCESS);
                        getView().setDataset(result.getDatasetDto());
                    }
                });
    }

    @Override
    public void cancelProgrammedPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLICATION_PENDING, currentProcStatus),
                new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorCancelProgrammedPublication()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                        ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceCancelProgrammedPublication()), MessageTypeEnum.SUCCESS);
                        getView().setDataset(result.getDatasetDto());
                    }
                });
    }

    @Override
    public void publish(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLISHED, currentProcStatus), new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorPublish()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourcePublish()), MessageTypeEnum.SUCCESS);
                getView().setDataset(result.getDatasetDto());
            }
        });
    }

    @Override
    public void archive(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.ARCHIVED, currentProcStatus), new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorArchive()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceArchive()), MessageTypeEnum.SUCCESS);
                getView().setDataset(result.getDatasetDto());
            }
        });
    }

    @Override
    public void version(String urn, VersionTypeEnum versionType) {
        dispatcher.execute(new VersionDatasetAction(urn, versionType), new WaitingAsyncCallback<VersionDatasetResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorVersion()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(VersionDatasetResult result) {
                ShowMessageEvent.fire(DatasetPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceVersion()), MessageTypeEnum.SUCCESS);
                getView().setDataset(result.getDatasetDto());
            }
        });
    }

}
