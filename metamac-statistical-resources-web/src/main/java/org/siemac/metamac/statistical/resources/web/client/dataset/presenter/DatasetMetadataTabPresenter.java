package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.VersionDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.VersionDatasetResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCommonMetadataConfigurationsListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCommonMetadataConfigurationsListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
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

public class DatasetMetadataTabPresenter extends Presenter<DatasetMetadataTabPresenter.DatasetMetadataTabView, DatasetMetadataTabPresenter.DatasetMetadataTabProxy>
        implements
            DatasetMetadataTabUiHandlers {

    private DispatchAsync   dispatcher;
    private PlaceManager    placeManager;

    private ExternalItemDto operation;

    public interface DatasetMetadataTabView extends View, HasUiHandlers<DatasetMetadataTabUiHandlers> {

        void setDataset(DatasetDto datasetDto);
        void setAgenciesPaginatedList(GetAgenciesPaginatedListResult datasetsPaginatedList);

        void setDatasetsForReplaces(GetDatasetsResult result);
        void setDatasetsForIsReplacedBy(GetDatasetsResult result);
        void setDsdsForRelatedDsd(GetDsdsPaginatedListResult result);
        void setStatisticalOperationsForDsdSelection(List<ExternalItemDto> results, ExternalItemDto defaultSelected);
        void setCommonConfigurations(GetCommonMetadataConfigurationsListResult result);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.datasetMetadataPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface DatasetMetadataTabProxy extends Proxy<DatasetMetadataTabPresenter>, Place {
    }

    @Inject
    public DatasetMetadataTabPresenter(EventBus eventBus, DatasetMetadataTabView view, DatasetMetadataTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @TitleFunction
    public String title() {
        return getConstants().breadcrumbMetadata();
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(datasetCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            retrieveDataset(datasetCode);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, DatasetPresenter.TYPE_SetContextAreaMetadata, this);
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    DatasetMetadataTabPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(DatasetMetadataTabPresenter.this, result.getOperation());
                }
            });
        }
    }

    @Override
    public void retrieveAgencies(int firstResult, int maxResults, String queryText) {
        dispatcher.execute(new GetAgenciesPaginatedListAction(firstResult, maxResults, queryText), new WaitingAsyncCallback<GetAgenciesPaginatedListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetAgenciesPaginatedListResult result) {
                getView().setAgenciesPaginatedList(result);
            }
        });
    }

    @Override
    public void retrieveDataset(String datasetIdentifier) {
        String urn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, datasetIdentifier);
        dispatcher.execute(new GetDatasetAction(urn), new WaitingAsyncCallback<GetDatasetResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
            }

            @Override
            public void onWaitSuccess(GetDatasetResult result) {
                getView().setDataset(result.getDatasetDto());
            }
        });
    }

    @Override
    public void saveDataset(DatasetDto datasetDto) {
        dispatcher.execute(new SaveDatasetAction(datasetDto, datasetDto.getStatisticalOperation().getCode()), new WaitingAsyncCallback<SaveDatasetResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
            }

            @Override
            public void onWaitSuccess(SaveDatasetResult result) {
                getView().setDataset(result.getSavedDataset());
            }
        });
    }

    @Override
    public void sendToProductionValidation(String urn, ProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, ProcStatusEnum.PRODUCTION_VALIDATION, currentProcStatus),
                new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
                    }
                    @Override
                    public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourceSentToProductionValidation());
                        getView().setDataset(result.getDatasetDto());
                    }
                });
    }

    @Override
    public void sendToDiffusionValidation(String urn, ProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, ProcStatusEnum.DIFFUSION_VALIDATION, currentProcStatus),
                new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
                    }
                    @Override
                    public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourceSentToDiffusionValidation());
                        getView().setDataset(result.getDatasetDto());
                    }
                });
    }

    @Override
    public void rejectValidation(String urn, ProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, ProcStatusEnum.VALIDATION_REJECTED, currentProcStatus), new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourceRejectValidation());
                getView().setDataset(result.getDatasetDto());
            }
        });
    }
    // FIXME ADD LIFECYCLE OPERATIONS
    // @Override
    // public void sendToPendingPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
    // dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLICATION_PENDING, currentProcStatus),
    // new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {
    //
    // @Override
    // public void onWaitFailure(Throwable caught) {
    // ShowMessageEvent.fire(DatasetMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorSendToPendingPublication()), MessageTypeEnum.ERROR);
    // }
    // @Override
    // public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
    // ShowMessageEvent.fire(DatasetMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceSentToPendingPublication()), MessageTypeEnum.SUCCESS);
    // getView().setDataset(result.getDatasetDto());
    // }
    // });
    // }
    //
    // @Override
    // public void programPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
    // dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED, currentProcStatus),
    // new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {
    //
    // @Override
    // public void onWaitFailure(Throwable caught) {
    // ShowMessageEvent.fire(DatasetMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorProgramPublication()), MessageTypeEnum.ERROR);
    // }
    // @Override
    // public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
    // ShowMessageEvent.fire(DatasetMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceProgramPublication()), MessageTypeEnum.SUCCESS);
    // getView().setDataset(result.getDatasetDto());
    // }
    // });
    // }
    //
    // @Override
    // public void cancelProgrammedPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
    // dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLICATION_PENDING, currentProcStatus),
    // new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {
    //
    // @Override
    // public void onWaitFailure(Throwable caught) {
    // ShowMessageEvent.fire(DatasetMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorCancelProgrammedPublication()), MessageTypeEnum.ERROR);
    // }
    // @Override
    // public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
    // ShowMessageEvent.fire(DatasetMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceCancelProgrammedPublication()), MessageTypeEnum.SUCCESS);
    // getView().setDataset(result.getDatasetDto());
    // }
    // });
    // }

    @Override
    public void publish(String urn, ProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateDatasetProcStatusAction(urn, ProcStatusEnum.PUBLISHED, currentProcStatus), new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourcePublish());
                getView().setDataset(result.getDatasetDto());
            }
        });
    }

    // @Override
    // public void archive(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
    // dispatcher.execute(new UpdateDatasetProcStatusAction(urn, StatisticalResourceProcStatusEnum.ARCHIVED, currentProcStatus), new WaitingAsyncCallback<UpdateDatasetProcStatusResult>() {
    //
    // @Override
    // public void onWaitFailure(Throwable caught) {
    // ShowMessageEvent.fire(DatasetMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorArchive()), MessageTypeEnum.ERROR);
    // }
    // @Override
    // public void onWaitSuccess(UpdateDatasetProcStatusResult result) {
    // ShowMessageEvent.fire(DatasetMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceArchive()), MessageTypeEnum.SUCCESS);
    // getView().setDataset(result.getDatasetDto());
    // }
    // });
    // }

    @Override
    public void version(String urn, VersionTypeEnum versionType) {
        dispatcher.execute(new VersionDatasetAction(urn, versionType), new WaitingAsyncCallback<VersionDatasetResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(VersionDatasetResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourceVersion());
                getView().setDataset(result.getDatasetDto());
            }
        });
    }

    @Override
    public void retrieveDatasetsForReplaces(int firstResult, int maxResults, String criteria) {

        VersionableStatisticalResourceWebCriteria datasetWebCriteria = new VersionableStatisticalResourceWebCriteria(criteria);
        // TODO Which is the condition to find the datasets to fill REPLACES?

        dispatcher.execute(new GetDatasetsAction(firstResult, maxResults, datasetWebCriteria), new WaitingAsyncCallback<GetDatasetsResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetDatasetsResult result) {
                getView().setDatasetsForReplaces(result);
            }
        });
    }
    
    @Override
    public void retrieveDsdsForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria) {
        dispatcher.execute(new GetDsdsPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallback<GetDsdsPaginatedListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetDsdsPaginatedListResult result) {
                getView().setDsdsForRelatedDsd(result);
            }
        });
    }
    
    @Override
    public void retrieveStatisticalOperationsForDsdSelection() {
        dispatcher.execute(new GetStatisticalOperationsPaginatedListAction(0, Integer.MAX_VALUE, null), new WaitingAsyncCallback<GetStatisticalOperationsPaginatedListResult>() {
            
            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetStatisticalOperationsPaginatedListResult result) {
                getView().setStatisticalOperationsForDsdSelection(result.getOperationsList(), operation);
            }
        });
    }

    @Override
    public void retrieveDatasetsForIsReplacedBy(int firstResult, int maxResults, String criteria) {

        VersionableStatisticalResourceWebCriteria datasetWebCriteria = new VersionableStatisticalResourceWebCriteria(criteria);
        // TODO Which is the condition to find the datasets to fill IS_REPLACED_BY?

        dispatcher.execute(new GetDatasetsAction(firstResult, maxResults, datasetWebCriteria), new WaitingAsyncCallback<GetDatasetsResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetDatasetsResult result) {
                getView().setDatasetsForIsReplacedBy(result);
            }
        });
    }
    
    @Override
    public void retrieveCommonConfigurations(CommonConfigurationWebCriteria criteria) {
        dispatcher.execute(new GetCommonMetadataConfigurationsListAction(criteria), new WaitingAsyncCallback<GetCommonMetadataConfigurationsListResult>() {
           
            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(DatasetMetadataTabPresenter.this, caught);            }
            
            @Override
            public void onWaitSuccess(GetCommonMetadataConfigurationsListResult result) {
                getView().setCommonConfigurations(result);
            }
        });
    }
    
    @Override
    public void goTo(List<PlaceRequest> location) {
        if (location != null && !location.isEmpty()) {
            placeManager.revealPlaceHierarchy(location);
        }
    }
}