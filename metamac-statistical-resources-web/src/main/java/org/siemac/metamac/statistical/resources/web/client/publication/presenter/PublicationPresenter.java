package org.siemac.metamac.statistical.resources.web.client.publication.presenter;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetVersionsOfPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetVersionsOfPublicationResult;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class PublicationPresenter extends Presenter<PublicationPresenter.PublicationView, PublicationPresenter.PublicationProxy> implements PublicationUiHandlers {

    private final DispatchAsync                       dispatcher;
    private final PlaceManager                        placeManager;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaMetadata  = new Type<RevealContentHandler<?>>();

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaStructure = new Type<RevealContentHandler<?>>();

    @ProxyCodeSplit
    @NameToken(NameTokens.publicationPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface PublicationProxy extends Proxy<PublicationPresenter>, Place {
    }

    @TitleFunction
    public static String getTranslatedTitle(PlaceRequest placeRequest) {
        return PlaceRequestUtils.getPublicationBreadCrumbTitle(placeRequest);
    }

    public interface PublicationView extends View, HasUiHandlers<PublicationUiHandlers> {

        void setPublication(PublicationVersionDto publicationDto);
        void setPublicationVersions(List<PublicationVersionDto> publicationVersionDtos);
        void showMetadata();
    }

    @Inject
    public PublicationPresenter(EventBus eventBus, PublicationView publicationView, PublicationProxy publicationProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, publicationView, publicationProxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, OperationPresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String publicationCode = PlaceRequestUtils.getPublicationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(publicationCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);

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
        String publicationCode = PlaceRequestUtils.getPublicationParamFromUrl(placeManager);
        String publicationUrn = CommonUtils.generatePublicationUrn(publicationCode);
        retrievePublication(publicationUrn);
        getView().showMetadata();
    }

    private void retrievePublication(String urn) {
        dispatcher.execute(new GetPublicationVersionAction(urn), new WaitingAsyncCallback<GetPublicationVersionResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(PublicationPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetPublicationVersionResult result) {
                getView().setPublication(result.getPublicationVersionDto());

                // Load the publication versions
                retrievePublicationVersions(result.getPublicationVersionDto().getUrn());
            }
        });
    }

    private void retrievePublicationVersions(String urn) {
        dispatcher.execute(new GetVersionsOfPublicationAction(urn), new WaitingAsyncCallbackHandlingError<GetVersionsOfPublicationResult>(this) {

            @Override
            public void onWaitSuccess(GetVersionsOfPublicationResult result) {
                getView().setPublicationVersions(result.getPublicationVersionDtos());
            }
        });
    }

    //
    // NAVIGATION
    //

    @Override
    public void goToPublicationMetadata() {
        List<PlaceRequest> hierarchy = PlaceRequestUtils.getHierarchyUntilNameToken(placeManager, NameTokens.publicationPage);
        hierarchy.add(new PlaceRequest(NameTokens.publicationMetadataPage));
        placeManager.revealPlaceHierarchy(hierarchy);
    }

    @Override
    public void goToPublicationStructure() {
        List<PlaceRequest> hierarchy = PlaceRequestUtils.getHierarchyUntilNameToken(placeManager, NameTokens.publicationPage);
        hierarchy.add(new PlaceRequest(NameTokens.publicationStructurePage));
        placeManager.revealPlaceHierarchy(hierarchy);
    }

    @Override
    public void goToPublicationVersion(String urn) {
        List<PlaceRequest> hierarchy = PlaceRequestUtils.getHierarchyUntilNameToken(placeManager, NameTokens.publicationsListPage);
        hierarchy.add(PlaceRequestUtils.buildRelativePublicationPlaceRequest(urn));
        placeManager.revealPlaceHierarchy(hierarchy);
    }
}
