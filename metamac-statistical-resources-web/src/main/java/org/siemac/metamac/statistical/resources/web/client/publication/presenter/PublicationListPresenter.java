package org.siemac.metamac.statistical.resources.web.client.publication.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.StatisticalResourceBaseListPresenter;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent.SetOperationHandler;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.criteria.PublicationVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionsProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionsProcStatusAction.Builder;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionsProcStatusResult;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class PublicationListPresenter extends StatisticalResourceBaseListPresenter<PublicationListPresenter.PublicationListView, PublicationListPresenter.PublicationListProxy>
        implements
            PublicationListUiHandlers,
            SetOperationHandler {

    private final PlaceManager                        placeManager;

    private ExternalItemDto                           operation;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetOperationResourcesToolBar                   = new Type<RevealContentHandler<?>>();

    public static final Object                        TYPE_SetContextAreaContentOperationResourcesToolBar = new Object();

    @ProxyCodeSplit
    @NameToken(NameTokens.publicationsListPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface PublicationListProxy extends Proxy<PublicationListPresenter>, Place {
    }

    @TitleFunction
    public static String getTranslatedTitle() {
        return StatisticalResourcesWeb.getConstants().breadcrumbPublications();
    }

    public interface PublicationListView extends StatisticalResourceBaseListPresenter.StatisticalResourceBaseListView, HasUiHandlers<PublicationListUiHandlers> {

        void setPublicationPaginatedList(List<PublicationVersionDto> PublicationDtos, int firstResult, int totalResults);

        // Search
        void clearSearchSection();
    }

    @Inject
    public PublicationListPresenter(EventBus eventBus, PublicationListView publicationListView, PublicationListProxy publicationListProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, publicationListView, publicationListProxy, dispatcher);
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            retrievePublications(operationUrn, 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, new PublicationVersionWebCriteria());
            getView().clearSearchSection();
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        SetTitleEvent.fire(this, getConstants().publications());
    }

    @ProxyEvent
    @Override
    public void onSetOperation(SetOperationEvent event) {
        this.operation = event.getOperation();
    }

    @Override
    public void retrievePublications(int firstResult, int maxResults, PublicationVersionWebCriteria publicationVersionWebCriteria) {
        retrievePublications(operation != null ? operation.getUrn() : null, firstResult, maxResults, publicationVersionWebCriteria);
    }

    private void retrievePublications(String operationUrn, int firstResult, int maxResults, PublicationVersionWebCriteria publicationVersionWebCriteria) {
        publicationVersionWebCriteria.setStatisticalOperationUrn(operationUrn);
        dispatcher.execute(new GetPublicationVersionsAction(firstResult, maxResults, publicationVersionWebCriteria), new WaitingAsyncCallbackHandlingError<GetPublicationVersionsResult>(this) {

            @Override
            public void onWaitSuccess(GetPublicationVersionsResult result) {
                getView().setPublicationPaginatedList(result.getPublicationDtos(), result.getFirstResultOut(), result.getTotalResults());
            }
        });
    }

    @Override
    public void createPublication(PublicationVersionDto publicationDto) {
        dispatcher.execute(new SavePublicationVersionAction(publicationDto, operation), new WaitingAsyncCallbackHandlingError<SavePublicationVersionResult>(this) {

            @Override
            public void onWaitSuccess(SavePublicationVersionResult result) {
                retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, null);
            }
        });
    }

    @Override
    public void deletePublication(List<String> urns) {
        dispatcher.execute(new DeletePublicationVersionsAction(urns), new WaitingAsyncCallbackHandlingError<DeletePublicationVersionsResult>(this) {

            @Override
            public void onWaitSuccess(DeletePublicationVersionsResult result) {
                ShowMessageEvent.fireSuccessMessage(PublicationListPresenter.this, getMessages().publicationDeleted());
                retrievePublications(PublicationListPresenter.this.operation.getUrn(), 0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, null);
            };
        });
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationResult>(this) {

                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    PublicationListPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(PublicationListPresenter.this, result.getOperation());
                }
            });
        }
    }

    //
    // LIFECYCLE
    //

    @Override
    public void sendToProductionValidation(List<PublicationVersionDto> publicationVersionDtos) {
        UpdatePublicationVersionsProcStatusAction action = new UpdatePublicationVersionsProcStatusAction(publicationVersionDtos, LifeCycleActionEnum.SEND_TO_PRODUCTION_VALIDATION);
        updatePublicationVersionProcStatus(action, getMessages().lifeCycleResourcesSentToProductionValidation());
    }

    @Override
    public void sendToDiffusionValidation(List<PublicationVersionDto> publicationVersionDtos) {
        UpdatePublicationVersionsProcStatusAction action = new UpdatePublicationVersionsProcStatusAction(publicationVersionDtos, LifeCycleActionEnum.SEND_TO_DIFFUSION_VALIDATION);
        updatePublicationVersionProcStatus(action, getMessages().lifeCycleResourcesSentToDiffusionValidation());
    }

    @Override
    public void rejectValidation(List<PublicationVersionDto> publicationVersionDtos) {
        UpdatePublicationVersionsProcStatusAction action = new UpdatePublicationVersionsProcStatusAction(publicationVersionDtos, LifeCycleActionEnum.REJECT_VALIDATION);
        updatePublicationVersionProcStatus(action, getMessages().lifeCycleResourcesRejectValidation());
    }

    @Override
    public void publish(List<PublicationVersionDto> publicationVersionDtos) {
        UpdatePublicationVersionsProcStatusAction action = new UpdatePublicationVersionsProcStatusAction(publicationVersionDtos, LifeCycleActionEnum.PUBLISH);
        updatePublicationVersionProcStatus(action, getMessages().lifeCycleResourcesPublish());
    }

    @Override
    public void programPublication(List<PublicationVersionDto> publicationVersionDtos) {
        UpdatePublicationVersionsProcStatusAction action = new UpdatePublicationVersionsProcStatusAction(publicationVersionDtos, LifeCycleActionEnum.PUBLISH);
        updatePublicationVersionProcStatus(action, getMessages().lifeCycleResourcesProgramPublication());
    }

    @Override
    public void version(List<PublicationVersionDto> publicationVersionDtos, VersionTypeEnum versionType) {
        Builder builder = new UpdatePublicationVersionsProcStatusAction.Builder(publicationVersionDtos, LifeCycleActionEnum.VERSION);
        builder.versionType(versionType);
        updatePublicationVersionProcStatus(builder.build(), getMessages().lifeCycleResourcesVersion());
    }

    private void updatePublicationVersionProcStatus(UpdatePublicationVersionsProcStatusAction action, final String successMessage) {
        dispatcher.execute(action, new WaitingAsyncCallbackHandlingError<UpdatePublicationVersionsProcStatusResult>(this) {

            @Override
            public void onWaitFailure(Throwable caught) {
                super.onWaitFailure(caught);
                retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, null);
            }
            @Override
            public void onWaitSuccess(UpdatePublicationVersionsProcStatusResult result) {
                ShowMessageEvent.fireSuccessMessage(PublicationListPresenter.this, successMessage);
                retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, null);
            }
        });
    }

    //
    // NAVIGATION
    //

    @Override
    public void goToPublication(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.publicationPage).with(PlaceRequestParams.publicationParam, UrnUtils.removePrefix(urn)));
        }
    }
}
