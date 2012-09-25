package org.siemac.metamac.statistical.resources.web.client.collection.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.collection.view.handlers.CollectionMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetMetadataTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionResult;
import org.siemac.metamac.statistical.resources.web.shared.collection.SaveCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.SaveCollectionResult;
import org.siemac.metamac.statistical.resources.web.shared.collection.UpdateCollectionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.UpdateCollectionProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.collection.VersionCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.VersionCollectionResult;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationResult;
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

public class CollectionMetadataTabPresenter extends Presenter<CollectionMetadataTabPresenter.CollectionMetadataTabView, CollectionMetadataTabPresenter.CollectionMetadataTabProxy> implements CollectionMetadataTabUiHandlers {

    private DispatchAsync dispatcher;
    private PlaceManager placeManager;
    
    private ExternalItemDto operation;;
    
    public interface CollectionMetadataTabView extends View,HasUiHandlers<CollectionMetadataTabUiHandlers> {
        void setAgenciesPaginatedList(GetAgenciesPaginatedListResult result);
        void setCollection(CollectionDto collectionDto);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.collectionMetadataPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface CollectionMetadataTabProxy extends Proxy<CollectionMetadataTabPresenter>, Place {
    }

    @Inject
    public CollectionMetadataTabPresenter(EventBus eventBus, CollectionMetadataTabView view, CollectionMetadataTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
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
    protected void revealInParent() {
        RevealContentEvent.fire(this, CollectionPresenter.TYPE_SetContextAreaMetadata, this);
    }
    
    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String collectionCode = PlaceRequestUtils.getCollectionParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(collectionCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            String collectionUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_COLLECTION_PREFIX, collectionCode);
            retrieveCollection(collectionUrn);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }
    
    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().operationErrorRetrieve()), MessageTypeEnum.ERROR);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    CollectionMetadataTabPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(CollectionMetadataTabPresenter.this, result.getOperation());
                }
            });
        }
    }

    private void retrieveCollection(String urn) {
        dispatcher.execute(new GetCollectionAction(urn), new WaitingAsyncCallback<GetCollectionResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorRetrieve()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetCollectionResult result) {
                getView().setCollection(result.getCollectionDto());
            }
        });
    }
    
    @Override
    public void retrieveAgencies(int firstResult, int maxResults, String queryText) {
        dispatcher.execute(new GetAgenciesPaginatedListAction(firstResult,maxResults,queryText), new WaitingAsyncCallback<GetAgenciesPaginatedListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().agencyErrorRetrieveList()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetAgenciesPaginatedListResult result) {
                getView().setAgenciesPaginatedList(result);
            }
        });
        
    }
    
    @Override
    public void saveCollection(CollectionDto collectionDto) {
        dispatcher.execute(new SaveCollectionAction(collectionDto), new WaitingAsyncCallback<SaveCollectionResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorSave()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(SaveCollectionResult result) {
                ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().collectionSaved()), MessageTypeEnum.SUCCESS);
                getView().setCollection(result.getSavedCollection());
            }
        });
    }

    @Override
    public void sendToProductionValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateCollectionProcStatusAction(urn, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION, currentProcStatus),
                new WaitingAsyncCallback<UpdateCollectionProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorSendToProductionValidation()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateCollectionProcStatusResult result) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceSentToProductionValidation()), MessageTypeEnum.SUCCESS);
                        getView().setCollection(result.getCollectionDto());
                    }
                });
    }

    @Override
    public void sendToDiffusionValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateCollectionProcStatusAction(urn, StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION, currentProcStatus),
                new WaitingAsyncCallback<UpdateCollectionProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorSendToDiffusionValidation()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateCollectionProcStatusResult result) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceSentToDiffusionValidation()), MessageTypeEnum.SUCCESS);
                        getView().setCollection(result.getCollectionDto());
                    }
                });
    }

    @Override
    public void rejectValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateCollectionProcStatusAction(urn, StatisticalResourceProcStatusEnum.VALIDATION_REJECTED, currentProcStatus),
                new WaitingAsyncCallback<UpdateCollectionProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorRejectValidation()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateCollectionProcStatusResult result) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceRejectValidation()), MessageTypeEnum.SUCCESS);
                        getView().setCollection(result.getCollectionDto());
                    }
                });
    }

    @Override
    public void sendToPendingPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateCollectionProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLICATION_PENDING, currentProcStatus),
                new WaitingAsyncCallback<UpdateCollectionProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorSendToPendingPublication()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateCollectionProcStatusResult result) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceSentToPendingPublication()), MessageTypeEnum.SUCCESS);
                        getView().setCollection(result.getCollectionDto());
                    }
                });
    }

    @Override
    public void programPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateCollectionProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED, currentProcStatus),
                new WaitingAsyncCallback<UpdateCollectionProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorProgramPublication()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateCollectionProcStatusResult result) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceProgramPublication()), MessageTypeEnum.SUCCESS);
                        getView().setCollection(result.getCollectionDto());
                    }
                });
    }

    @Override
    public void cancelProgrammedPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateCollectionProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLICATION_PENDING, currentProcStatus),
                new WaitingAsyncCallback<UpdateCollectionProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorCancelProgrammedPublication()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdateCollectionProcStatusResult result) {
                        ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceCancelProgrammedPublication()), MessageTypeEnum.SUCCESS);
                        getView().setCollection(result.getCollectionDto());
                    }
                });
    }

    @Override
    public void publish(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateCollectionProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLISHED, currentProcStatus), new WaitingAsyncCallback<UpdateCollectionProcStatusResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorPublish()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(UpdateCollectionProcStatusResult result) {
                ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourcePublish()), MessageTypeEnum.SUCCESS);
                getView().setCollection(result.getCollectionDto());
            }
        });
    }

    @Override
    public void archive(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdateCollectionProcStatusAction(urn, StatisticalResourceProcStatusEnum.ARCHIVED, currentProcStatus), new WaitingAsyncCallback<UpdateCollectionProcStatusResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorArchive()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(UpdateCollectionProcStatusResult result) {
                ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceArchive()), MessageTypeEnum.SUCCESS);
                getView().setCollection(result.getCollectionDto());
            }
        });
    }

    @Override
    public void version(String urn, VersionTypeEnum versionType) {
        dispatcher.execute(new VersionCollectionAction(urn, versionType), new WaitingAsyncCallback<VersionCollectionResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorVersion()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(VersionCollectionResult result) {
                ShowMessageEvent.fire(CollectionMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceVersion()), MessageTypeEnum.SUCCESS);
                getView().setCollection(result.getCollectionDto());
            }
        });
    }
}