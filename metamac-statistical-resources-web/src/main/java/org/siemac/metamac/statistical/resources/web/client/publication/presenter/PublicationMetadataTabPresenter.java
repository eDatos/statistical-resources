package org.siemac.metamac.statistical.resources.web.client.publication.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.StatisticalResourceMetadataBasePresenter;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.MetamacPortalWebUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusAction.Builder;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusResult;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.google.gwt.user.client.Window;
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

        void setPublication(PublicationVersionDto publicationDto);

        void setPublicationsForReplaces(GetPublicationVersionsResult result);
        void setPublicationsForIsReplacedBy(GetPublicationVersionsResult result);
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
        dispatcher.execute(new GetPublicationVersionAction(urn), new WaitingAsyncCallbackHandlingError<GetPublicationVersionResult>(this) {

            @Override
            public void onWaitSuccess(GetPublicationVersionResult result) {
                getView().setPublication(result.getPublicationVersionDto());
            }
        });
    }

    @Override
    public void savePublication(PublicationVersionDto publicationDto) {
        dispatcher.execute(new SavePublicationVersionAction(publicationDto, operation), new WaitingAsyncCallbackHandlingError<SavePublicationVersionResult>(this) {

            @Override
            public void onWaitSuccess(SavePublicationVersionResult result) {
                ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().publicationSaved());
                getView().setPublication(result.getSavedPublicationVersion());
            }
        });
    }

    //
    // LIFE CYCLE
    //

    @Override
    public void sendToProductionValidation(PublicationVersionDto publicationVersionDto) {
        dispatcher.execute(new UpdatePublicationVersionProcStatusAction(publicationVersionDto, LifeCycleActionEnum.SEND_TO_PRODUCTION_VALIDATION),
                new WaitingAsyncCallbackHandlingError<UpdatePublicationVersionProcStatusResult>(this) {

                    @Override
                    public void onWaitSuccess(UpdatePublicationVersionProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourceSentToProductionValidation());
                        getView().setPublication(result.getPublicationVersionDto());
                    }
                });
    }

    @Override
    public void sendToDiffusionValidation(PublicationVersionDto publicationVersionDto) {
        dispatcher.execute(new UpdatePublicationVersionProcStatusAction(publicationVersionDto, LifeCycleActionEnum.SEND_TO_DIFFUSION_VALIDATION),
                new WaitingAsyncCallbackHandlingError<UpdatePublicationVersionProcStatusResult>(this) {

                    @Override
                    public void onWaitSuccess(UpdatePublicationVersionProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourceSentToDiffusionValidation());
                        getView().setPublication(result.getPublicationVersionDto());
                    }
                });
    }

    @Override
    public void rejectValidation(PublicationVersionDto publicationVersionDto) {
        dispatcher.execute(new UpdatePublicationVersionProcStatusAction(publicationVersionDto, LifeCycleActionEnum.REJECT_VALIDATION),
                new WaitingAsyncCallbackHandlingError<UpdatePublicationVersionProcStatusResult>(this) {

                    @Override
                    public void onWaitSuccess(UpdatePublicationVersionProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourceRejectValidation());
                        getView().setPublication(result.getPublicationVersionDto());
                    }
                });
    }

    @Override
    public void publish(PublicationVersionDto publicationVersionDto) {
        dispatcher.execute(new UpdatePublicationVersionProcStatusAction(publicationVersionDto, LifeCycleActionEnum.PUBLISH),
                new WaitingAsyncCallbackHandlingError<UpdatePublicationVersionProcStatusResult>(this) {

                    @Override
                    public void onWaitSuccess(UpdatePublicationVersionProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourcePublish());
                        getView().setPublication(result.getPublicationVersionDto());
                    }
                });
    }

    @Override
    public void programPublication(PublicationVersionDto publication) {
        dispatcher.execute(new UpdatePublicationVersionProcStatusAction(publication, LifeCycleActionEnum.PUBLISH),
                new WaitingAsyncCallbackHandlingError<UpdatePublicationVersionProcStatusResult>(this) {

                    @Override
                    public void onWaitSuccess(UpdatePublicationVersionProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourceRejectValidation());
                        getView().setPublication(result.getPublicationVersionDto());
                    }
                });
    }

    @Override
    public void cancelProgrammedPublication(PublicationVersionDto publication) {
        // TODO Auto-generated method stub

    }

    @Override
    public void version(PublicationVersionDto publication, VersionTypeEnum versionType) {
        Builder builder = new Builder(publication, LifeCycleActionEnum.VERSION);
        builder.versionType(versionType);
        dispatcher.execute(builder.build(), new WaitingAsyncCallbackHandlingError<UpdatePublicationVersionProcStatusResult>(this) {

            @Override
            public void onWaitSuccess(UpdatePublicationVersionProcStatusResult result) {
                ShowMessageEvent.fireSuccessMessage(PublicationMetadataTabPresenter.this, getMessages().lifeCycleResourceVersion());
                getView().setPublication(result.getPublicationVersionDto());
            }
        });
    }

    @Override
    public void previewData(PublicationVersionDto publicationVersionDto) {
        String url = MetamacPortalWebUtils.buildPublicationVersionUrl(publicationVersionDto);
        Window.open(url, "_blank", "");
    }

    //
    // RELATED RESOURCES
    //

    @Override
    public void retrievePublicationsForReplaces(int firstResult, int maxResults, MetamacWebCriteria criteria) {

        VersionableStatisticalResourceWebCriteria publicationWebCriteria = new VersionableStatisticalResourceWebCriteria(criteria.getCriteria());
        publicationWebCriteria.setOnlyLastVersion(false);

        dispatcher.execute(new GetPublicationVersionsAction(firstResult, maxResults, publicationWebCriteria), new WaitingAsyncCallbackHandlingError<GetPublicationVersionsResult>(this) {

            @Override
            public void onWaitSuccess(GetPublicationVersionsResult result) {
                getView().setPublicationsForReplaces(result);
            }
        });
    }
}
