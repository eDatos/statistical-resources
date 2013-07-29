package org.siemac.metamac.statistical.resources.web.client.publication.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationStructureTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.base.GetLatestResourceVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.base.GetLatestResourceVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationStructureElementAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationStructureElementResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationStructureAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationStructureResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationStructureElementAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationStructureElementResult;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationStructureElementLocationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationStructureElementLocationResult;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueriesAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueriesResult;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
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

public class PublicationStructureTabPresenter extends Presenter<PublicationStructureTabPresenter.PublicationStructureTabView, PublicationStructureTabPresenter.PublicationStructureTabProxy>
        implements
            PublicationStructureTabUiHandlers {

    private DispatchAsync   dispatcher;
    private PlaceManager    placeManager;

    private ExternalItemDto operation;

    public interface PublicationStructureTabView extends View, HasUiHandlers<PublicationStructureTabUiHandlers> {

        void setPublicationStructure(PublicationStructureDto publicationStructureDto);
        void setPublicationStructure(PublicationStructureDto publicationStructureDto, NameableStatisticalResourceDto selectedElement);

        // Related resources
        void setDatasetsForCubes(GetDatasetsResult result);
        void setStatisticalOperationsForDatasetSelection(GetStatisticalOperationsPaginatedListResult result);
        void setQueriesForCubes(GetQueriesResult result);
        void setStatisticalOperationsForQuerySelection(GetStatisticalOperationsPaginatedListResult result);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.publicationStructurePage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface PublicationStructureTabProxy extends Proxy<PublicationStructureTabPresenter>, Place {
    }

    @Inject
    public PublicationStructureTabPresenter(EventBus eventBus, PublicationStructureTabView view, PublicationStructureTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @TitleFunction
    public String title() {
        return getConstants().breadcrumbStructure();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, PublicationPresenter.TYPE_SetContextAreaStructure, this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String publicationCode = PlaceRequestUtils.getPublicationParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(publicationCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            String publicationVersionUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_COLLECTION_PREFIX, publicationCode);
            retrievePublicationStructure(publicationVersionUrn);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallback<GetStatisticalOperationResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fireErrorMessage(PublicationStructureTabPresenter.this, caught);
                }
                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    PublicationStructureTabPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(PublicationStructureTabPresenter.this, result.getOperation());
                }
            });
        }
    }

    private void retrievePublicationStructure(String publicationVersionUrn) {
        dispatcher.execute(new GetPublicationStructureAction(publicationVersionUrn), new WaitingAsyncCallback<GetPublicationStructureResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(PublicationStructureTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetPublicationStructureResult result) {
                getView().setPublicationStructure(result.getPublicationStructureDto());
            }
        });
    }

    @Override
    public void saveElement(String publicationVersionUrn, NameableStatisticalResourceDto element) {
        dispatcher.execute(new SavePublicationStructureElementAction(publicationVersionUrn, element), new WaitingAsyncCallback<SavePublicationStructureElementResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(PublicationStructureTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(SavePublicationStructureElementResult result) {
                getView().setPublicationStructure(result.getPublicationStructureDto(), result.getSavedElement());
            }
        });
    }

    @Override
    public void deleteElement(String publicationVersionUrn, String elementUrn) {
        dispatcher.execute(new DeletePublicationStructureElementAction(publicationVersionUrn, elementUrn), new WaitingAsyncCallback<DeletePublicationStructureElementResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(PublicationStructureTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(DeletePublicationStructureElementResult result) {
                getView().setPublicationStructure(result.getPublicationStructureDto());
            }
        });
    }

    @Override
    public void updateElementLocation(String publicationVersionUrn, String elementUrn, String parentTargetUrn, Long orderInLevel) {
        dispatcher.execute(new UpdatePublicationStructureElementLocationAction(publicationVersionUrn, elementUrn, parentTargetUrn, orderInLevel),
                new WaitingAsyncCallback<UpdatePublicationStructureElementLocationResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        ShowMessageEvent.fireErrorMessage(PublicationStructureTabPresenter.this, caught);
                    }
                    @Override
                    public void onWaitSuccess(UpdatePublicationStructureElementLocationResult result) {
                        getView().setPublicationStructure(result.getPublicationStructureDto());
                    }
                });
    }

    //
    // RELATED RESOURCES
    //

    @Override
    public void retrieveDatasetsForCubes(int firstResult, int maxResults, StatisticalResourceWebCriteria criteria) {
        dispatcher.execute(new GetDatasetsAction(firstResult, maxResults, criteria), new WaitingAsyncCallback<GetDatasetsResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(PublicationStructureTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetDatasetsResult result) {
                getView().setDatasetsForCubes(result);
            }
        });
    }

    @Override
    public void retrieveStatisticalOperationsForDatasetSelection() {
        dispatcher.execute(new GetStatisticalOperationsPaginatedListAction(0, Integer.MAX_VALUE, null), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetStatisticalOperationsPaginatedListResult result) {
                getView().setStatisticalOperationsForDatasetSelection(result);
            }
        });
    }

    @Override
    public void retrieveQueriesForCubes(int firstResult, int maxResults, StatisticalResourceWebCriteria criteria) {
        dispatcher.execute(new GetQueriesAction(firstResult, maxResults, criteria), new WaitingAsyncCallback<GetQueriesResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                ShowMessageEvent.fireErrorMessage(PublicationStructureTabPresenter.this, caught);
            }
            @Override
            public void onWaitSuccess(GetQueriesResult result) {
                getView().setQueriesForCubes(result);
            }
        });
    }

    @Override
    public void retrieveStatisticalOperationsForQuerySelection() {
        dispatcher.execute(new GetStatisticalOperationsPaginatedListAction(0, Integer.MAX_VALUE, null), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetStatisticalOperationsPaginatedListResult result) {
                getView().setStatisticalOperationsForQuerySelection(result);
            }
        });
    }

    //
    // NAVIGATION
    //

    @Override
    public void goTo(List<PlaceRequest> location) {
        if (location != null && !location.isEmpty()) {
            placeManager.revealPlaceHierarchy(location);
        }
    }

    @Override
    public void goToLastVersion(final String urn) {
        if (!StringUtils.isBlank(urn)) {
            dispatcher.execute(new GetLatestResourceVersionAction(urn), new WaitingAsyncCallback<GetLatestResourceVersionResult>() {

                @Override
                public void onWaitFailure(Throwable caught) {
                    ShowMessageEvent.fireErrorMessage(PublicationStructureTabPresenter.this, caught);
                }
                @Override
                public void onWaitSuccess(GetLatestResourceVersionResult result) {
                    LifeCycleStatisticalResourceDto resourceVersion = result.getResourceVersion();
                    String operationUrn = resourceVersion.getStatisticalOperation().getUrn();
                    String resourceUrn = resourceVersion.getUrn();
                    if (resourceVersion instanceof DatasetVersionDto) {
                        placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteDatasetPlaceRequest(operationUrn, resourceUrn));
                    } else if (resourceVersion instanceof QueryVersionDto) {
                        placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteQueryPlaceRequest(operationUrn, resourceUrn));
                    }
                }
            });
        }
    }
}
