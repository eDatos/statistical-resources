package org.siemac.metamac.statistical.resources.web.client.publication.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.StatisticalResourceMetadataBasePresenter;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationsAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationsResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.VersionPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.VersionPublicationResult;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
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

public class PublicationMetadataTabPresenter
        extends
            StatisticalResourceMetadataBasePresenter<PublicationMetadataTabPresenter.PublicationMetadataTabView, PublicationMetadataTabPresenter.PublicationMetadataTabProxy>
        implements
            PublicationMetadataTabUiHandlers {

    private ExternalItemDto operation;

    public interface PublicationMetadataTabView extends StatisticalResourceMetadataBasePresenter.StatisticalResourceMetadataBaseView, HasUiHandlers<PublicationMetadataTabUiHandlers> {

        void setPublication(PublicationDto collectionDto);

        void setPublicationsForReplaces(GetPublicationsResult result);
        void setPublicationsForIsReplacedBy(GetPublicationsResult result);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.publicationMetadataPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface PublicationMetadataTabProxy extends Proxy<PublicationMetadataTabPresenter>, Place {
    }

    @Inject
    public PublicationMetadataTabPresenter(EventBus eventBus, PublicationMetadataTabView view, PublicationMetadataTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy, dispatcher, placeManager);
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
        String publicationCode = PlaceRequestUtils.getPublicationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(publicationCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            String publicationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_COLLECTION_PREFIX, publicationCode);
            retrievePublication(publicationUrn);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationResult>(this) {

                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    PublicationMetadataTabPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(PublicationMetadataTabPresenter.this, result.getOperation());
                }
            });
        }
    }

    private void retrievePublication(String urn) {
        dispatcher.execute(new GetPublicationAction(urn), new WaitingAsyncCallbackHandlingError<GetPublicationResult>(this) {

            @Override
            public void onWaitSuccess(GetPublicationResult result) {
                getView().setPublication(result.getPublicationDto());
            }
        });
    }

    @Override
    public void savePublication(PublicationDto publicationDto) {
        dispatcher.execute(new SavePublicationAction(publicationDto, operation), new WaitingAsyncCallbackHandlingError<SavePublicationResult>(this) {

            @Override
            public void onWaitSuccess(SavePublicationResult result) {
                ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().collectionSaved());
                getView().setPublication(result.getSavedPublication());
            }
        });
    }

    @Override
    public void sendToProductionValidation(String urn, ProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdatePublicationProcStatusAction(urn, ProcStatusEnum.PRODUCTION_VALIDATION, currentProcStatus),
                new WaitingAsyncCallbackHandlingError<UpdatePublicationProcStatusResult>(this) {

                    @Override
                    public void onWaitSuccess(UpdatePublicationProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourceSentToProductionValidation());
                        getView().setPublication(result.getPublicationDto());
                    }
                });
    }

    @Override
    public void sendToDiffusionValidation(String urn, ProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdatePublicationProcStatusAction(urn, ProcStatusEnum.DIFFUSION_VALIDATION, currentProcStatus),
                new WaitingAsyncCallbackHandlingError<UpdatePublicationProcStatusResult>(this) {

                    @Override
                    public void onWaitSuccess(UpdatePublicationProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourceSentToDiffusionValidation());
                        getView().setPublication(result.getPublicationDto());
                    }
                });
    }

    @Override
    public void rejectValidation(String urn, ProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdatePublicationProcStatusAction(urn, ProcStatusEnum.VALIDATION_REJECTED, currentProcStatus), new WaitingAsyncCallbackHandlingError<UpdatePublicationProcStatusResult>(
                this) {

            @Override
            public void onWaitSuccess(UpdatePublicationProcStatusResult result) {
                ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourceRejectValidation());
                getView().setPublication(result.getPublicationDto());
            }
        });
    }

    @Override
    public void publish(String urn, ProcStatusEnum currentProcStatus) {
        dispatcher.execute(new UpdatePublicationProcStatusAction(urn, ProcStatusEnum.PUBLISHED, currentProcStatus), new WaitingAsyncCallbackHandlingError<UpdatePublicationProcStatusResult>(this) {

            @Override
            public void onWaitSuccess(UpdatePublicationProcStatusResult result) {
                ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourcePublish());
                getView().setPublication(result.getPublicationDto());
            }
        });
    }

    @Override
    public void version(String urn, VersionTypeEnum versionType) {
        dispatcher.execute(new VersionPublicationAction(urn, versionType), new WaitingAsyncCallbackHandlingError<VersionPublicationResult>(this) {

            @Override
            public void onWaitSuccess(VersionPublicationResult result) {
                ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourceVersion());
                getView().setPublication(result.getPublicationDto());
            }
        });
    }

    @Override
    public void retrievePublicationsForReplaces(int firstResult, int maxResults, MetamacWebCriteria criteria) {

        VersionableStatisticalResourceWebCriteria publicationWebCriteria = new VersionableStatisticalResourceWebCriteria(criteria.getCriteria());
        publicationWebCriteria.setOnlyLastVersion(false);

        dispatcher.execute(new GetPublicationsAction(firstResult, maxResults, publicationWebCriteria), new WaitingAsyncCallbackHandlingError<GetPublicationsResult>(this) {

            @Override
            public void onWaitSuccess(GetPublicationsResult result) {
                getView().setPublicationsForReplaces(result);
            }
        });
    }

}