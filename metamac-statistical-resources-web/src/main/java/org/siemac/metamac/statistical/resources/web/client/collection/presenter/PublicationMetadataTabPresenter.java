package org.siemac.metamac.statistical.resources.web.client.collection.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.collection.view.handlers.PublicationMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.ErrorUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.VersionPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.VersionPublicationResult;
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

public class PublicationMetadataTabPresenter extends Presenter<PublicationMetadataTabPresenter.PublicationMetadataTabView, PublicationMetadataTabPresenter.PublicationMetadataTabProxy>
        implements
            PublicationMetadataTabUiHandlers {

    private DispatchAsync   dispatcher;
    private PlaceManager    placeManager;

    private ExternalItemDto operation;    ;

    public interface PublicationMetadataTabView extends View, HasUiHandlers<PublicationMetadataTabUiHandlers> {

        void setAgenciesPaginatedList(GetAgenciesPaginatedListResult result);
        void setPublication(PublicationDto collectionDto);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.collectionMetadataPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface PublicationMetadataTabProxy extends Proxy<PublicationMetadataTabPresenter>, Place {
    }

    @Inject
    public PublicationMetadataTabPresenter(EventBus eventBus, PublicationMetadataTabView view, PublicationMetadataTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
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
        RevealContentEvent.fire(this, PublicationPresenter.TYPE_SetContextAreaMetadata, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String collectionCode = PlaceRequestUtils.getPublicationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(collectionCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            String collectionUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_COLLECTION_PREFIX, collectionCode);
            retrievePublication(collectionUrn);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().operationErrorRetrieve()), MessageTypeEnum.ERROR);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    PublicationMetadataTabPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(PublicationMetadataTabPresenter.this, result.getOperation());
                }
            });
        }
    }

    private void retrievePublication(String urn) {
        dispatcher.execute(new GetPublicationAction(urn), new WaitingAsyncCallback<GetPublicationResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorRetrieve()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetPublicationResult result) {
                getView().setPublication(result.getPublicationDto());
            }
        });
    }

    @Override
    public void retrieveAgencies(int firstResult, int maxResults, String queryText) {
        dispatcher.execute(new GetAgenciesPaginatedListAction(firstResult, maxResults, queryText), new WaitingAsyncCallback<GetAgenciesPaginatedListResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().agencyErrorRetrieveList()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(GetAgenciesPaginatedListResult result) {
                getView().setAgenciesPaginatedList(result);
            }
        });

    }

    @Override
    public void savePublication(PublicationDto collectionDto) {
        dispatcher.execute(new SavePublicationAction(collectionDto), new WaitingAsyncCallback<SavePublicationResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().collectionErrorSave()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(SavePublicationResult result) {
                ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().collectionSaved()), MessageTypeEnum.SUCCESS);
                getView().setPublication(result.getSavedPublication());
            }
        });
    }

    @Override
    public void sendToProductionValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdatePublicationProcStatusAction(urn, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION, currentProcStatus),
                new WaitingAsyncCallback<UpdatePublicationProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorSendToProductionValidation()),
                                MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdatePublicationProcStatusResult result) {
                        ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceSentToProductionValidation()), MessageTypeEnum.SUCCESS);
                        getView().setPublication(result.getPublicationDto());
                    }
                });
    }

    @Override
    public void sendToDiffusionValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdatePublicationProcStatusAction(urn, StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION, currentProcStatus),
                new WaitingAsyncCallback<UpdatePublicationProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorSendToDiffusionValidation()),
                                MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdatePublicationProcStatusResult result) {
                        ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceSentToDiffusionValidation()), MessageTypeEnum.SUCCESS);
                        getView().setPublication(result.getPublicationDto());
                    }
                });
    }

    @Override
    public void rejectValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdatePublicationProcStatusAction(urn, StatisticalResourceProcStatusEnum.VALIDATION_REJECTED, currentProcStatus),
                new WaitingAsyncCallback<UpdatePublicationProcStatusResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorRejectValidation()), MessageTypeEnum.ERROR);
                    }
                    @Override
                    public void onWaitSuccess(UpdatePublicationProcStatusResult result) {
                        ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceRejectValidation()), MessageTypeEnum.SUCCESS);
                        getView().setPublication(result.getPublicationDto());
                    }
                });
    }

    @Override
    public void publish(String urn, StatisticalResourceProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdatePublicationProcStatusAction(urn, StatisticalResourceProcStatusEnum.PUBLISHED, currentProcStatus), new WaitingAsyncCallback<UpdatePublicationProcStatusResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorPublish()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(UpdatePublicationProcStatusResult result) {
                ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourcePublish()), MessageTypeEnum.SUCCESS);
                getView().setPublication(result.getPublicationDto());
            }
        });
    }

    @Override
    public void version(String urn, VersionTypeEnum versionType) {
        dispatcher.execute(new VersionPublicationAction(urn, versionType), new WaitingAsyncCallback<VersionPublicationResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getErrorMessages(caught, getMessages().lifeCycleResourceErrorVersion()), MessageTypeEnum.ERROR);
            }
            @Override
            public void onWaitSuccess(VersionPublicationResult result) {
                ShowMessageEvent.fire(PublicationMetadataTabPresenter.this, ErrorUtils.getMessageList(getMessages().lifeCycleResourceVersion()), MessageTypeEnum.SUCCESS);
                getView().setPublication(result.getPublicationDto());
            }
        });
    }
}