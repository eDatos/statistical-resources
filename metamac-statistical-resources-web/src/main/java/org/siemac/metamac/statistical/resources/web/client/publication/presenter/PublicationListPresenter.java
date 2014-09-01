package org.siemac.metamac.statistical.resources.web.client.publication.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.navigation.shared.NameTokens;
import org.siemac.metamac.statistical.resources.navigation.shared.PlaceRequestParams;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.StatisticalResourceBaseListPresenter;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.PublicationVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
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

public class PublicationListPresenter extends StatisticalResourceBaseListPresenter<PublicationListPresenter.PublicationListView, PublicationListPresenter.PublicationListProxy>
        implements
            PublicationListUiHandlers {

    private final PlaceManager placeManager;

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

        void setPublicationPaginatedList(List<PublicationVersionBaseDto> publicationVersionBaseDtos, int firstResult, int totalResults);

        // Search
        void clearSearchSection();
        PublicationVersionWebCriteria getPublicationVersionWebCriteria();
        void setStatisticalOperationsForSearchSection(GetStatisticalOperationsPaginatedListResult result);
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
    protected void onReset() {
        super.onReset();
        SetTitleEvent.fire(this, getConstants().publications());
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

        PublicationVersionWebCriteria publicationVersionWebCriteria = new PublicationVersionWebCriteria();
        publicationVersionWebCriteria.setStatisticalOperationUrn(StatisticalResourcesDefaults.getSelectedStatisticalOperation().getUrn());

        retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, publicationVersionWebCriteria);
    }

    @Override
    public void retrievePublications(int firstResult, int maxResults, PublicationVersionWebCriteria criteria) {
        dispatcher.execute(new GetPublicationVersionsAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetPublicationVersionsResult>(this) {

            @Override
            public void onWaitSuccess(GetPublicationVersionsResult result) {
                getView().setPublicationPaginatedList(result.getPublicationBaseDtos(), result.getFirstResultOut(), result.getTotalResults());
            }
        });
    }

    @Override
    public void createPublication(PublicationVersionDto publicationDto) {
        dispatcher.execute(new SavePublicationVersionAction(publicationDto, StatisticalResourcesDefaults.getSelectedStatisticalOperation()),
                new WaitingAsyncCallbackHandlingError<SavePublicationVersionResult>(this) {

                    @Override
                    public void onWaitSuccess(SavePublicationVersionResult result) {
                        retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getPublicationVersionWebCriteria());
                    }
                });
    }

    @Override
    public void deletePublication(List<String> urns) {
        dispatcher.execute(new DeletePublicationVersionsAction(urns), new WaitingAsyncCallbackHandlingError<DeletePublicationVersionsResult>(this) {

            @Override
            public void onWaitSuccess(DeletePublicationVersionsResult result) {
                fireSuccessMessage(getMessages().publicationsDeleted());
                retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getPublicationVersionWebCriteria());
            };

            @Override
            public void onWaitFailure(Throwable caught) {
                super.onWaitFailure(caught);
                retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getPublicationVersionWebCriteria());
            }
        });
    }

    //
    // LIFECYCLE
    //

    @Override
    public void sendToProductionValidation(List<PublicationVersionBaseDto> publicationVersionBaseDtos) {
        UpdatePublicationVersionsProcStatusAction action = new UpdatePublicationVersionsProcStatusAction(publicationVersionBaseDtos, LifeCycleActionEnum.SEND_TO_PRODUCTION_VALIDATION);
        updatePublicationVersionProcStatus(action, getMessages().lifeCycleResourcesSentToProductionValidation());
    }

    @Override
    public void sendToDiffusionValidation(List<PublicationVersionBaseDto> publicationVersionBaseDtos) {
        UpdatePublicationVersionsProcStatusAction action = new UpdatePublicationVersionsProcStatusAction(publicationVersionBaseDtos, LifeCycleActionEnum.SEND_TO_DIFFUSION_VALIDATION);
        updatePublicationVersionProcStatus(action, getMessages().lifeCycleResourcesSentToDiffusionValidation());
    }

    @Override
    public void rejectValidation(List<PublicationVersionBaseDto> publicationVersionBaseDtos, String reasonOfRejection) {
        UpdatePublicationVersionsProcStatusAction.Builder actionBuilder = new Builder(publicationVersionBaseDtos, LifeCycleActionEnum.REJECT_VALIDATION).reasonOfRejection(reasonOfRejection);
        updatePublicationVersionProcStatus(actionBuilder.build(), getMessages().lifeCycleResourcesRejectValidation());
    }

    @Override
    public void publish(List<PublicationVersionBaseDto> publicationVersionBaseDtos) {
        UpdatePublicationVersionsProcStatusAction action = new UpdatePublicationVersionsProcStatusAction(publicationVersionBaseDtos, LifeCycleActionEnum.PUBLISH);
        updatePublicationVersionProcStatus(action, getMessages().lifeCycleResourcesPublish());
    }

    @Override
    public void programPublication(List<PublicationVersionBaseDto> publicationVersionBaseDtos, Date validFrom) {
        UpdatePublicationVersionsProcStatusAction.Builder builder = new Builder(publicationVersionBaseDtos, LifeCycleActionEnum.PUBLISH);
        builder.validFrom(validFrom);
        updatePublicationVersionProcStatus(builder.build(), getMessages().lifeCycleResourcesProgramPublication());
    }

    @Override
    public void cancelProgrammedPublication(List<PublicationVersionBaseDto> publicationVersionBaseDtos) {
        UpdatePublicationVersionsProcStatusAction action = new UpdatePublicationVersionsProcStatusAction(publicationVersionBaseDtos, LifeCycleActionEnum.CANCEL_PROGRAMMED_PUBLICATION);
        updatePublicationVersionProcStatus(action, getMessages().lifeCycleResourcesCancelProgrammedPublication());
    }

    @Override
    public void version(List<PublicationVersionBaseDto> publicationVersionDtos, VersionTypeEnum versionType) {
        Builder builder = new UpdatePublicationVersionsProcStatusAction.Builder(publicationVersionDtos, LifeCycleActionEnum.VERSION);
        builder.versionType(versionType);
        updatePublicationVersionProcStatus(builder.build(), getMessages().lifeCycleResourcesVersion());
    }

    private void updatePublicationVersionProcStatus(UpdatePublicationVersionsProcStatusAction action, final String successMessage) {
        dispatcher.execute(action, new WaitingAsyncCallbackHandlingError<UpdatePublicationVersionsProcStatusResult>(this) {

            @Override
            public void onWaitSuccess(UpdatePublicationVersionsProcStatusResult result) {
                fireSuccessMessage(successMessage);
                retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getPublicationVersionWebCriteria());
            }
            @Override
            public void onWaitFailure(Throwable caught) {
                super.onWaitFailure(caught);
                retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getView().getPublicationVersionWebCriteria());
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
    public void goToPublication(String urn) {
        if (!StringUtils.isBlank(urn)) {
            placeManager.revealRelativePlace(new PlaceRequest(NameTokens.publicationPage).with(PlaceRequestParams.publicationParam, UrnUtils.removePrefix(urn)));
        }
    }
}
