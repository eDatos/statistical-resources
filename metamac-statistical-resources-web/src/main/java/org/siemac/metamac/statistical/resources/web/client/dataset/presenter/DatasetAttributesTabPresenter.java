package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.RepresentationDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.enums.DatasetTabTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.events.SelectDatasetTabEvent;
import org.siemac.metamac.statistical.resources.web.client.events.SetDatasetEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAttributeInstancesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAttributeInstancesResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAttributesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAttributesResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionCoverageAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionCoverageResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCodesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCodesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

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

public class DatasetAttributesTabPresenter extends Presenter<DatasetAttributesTabPresenter.DatasetAttributesTabView, DatasetAttributesTabPresenter.DatasetAttributesTabProxy>
        implements
            DatasetAttributesTabUiHandlers {

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    private String              datasetVersionUrn;

    public interface DatasetAttributesTabView extends View, HasUiHandlers<DatasetAttributesTabUiHandlers> {

        void setAttributes(List<DsdAttributeDto> attributes);
        void setAttributeInstances(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos);
        void setDimensionCoverageValues(String dimensionId, List<CodeItemDto> codeItemDtos);
        void setItemsForDatasetLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults);
        void setItemsForDimensionOrGroupLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.datasetAttributesPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface DatasetAttributesTabProxy extends Proxy<DatasetAttributesTabPresenter>, Place {
    }

    @Inject
    public DatasetAttributesTabPresenter(EventBus eventBus, DatasetAttributesTabView view, DatasetAttributesTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }

    @TitleFunction
    public String title() {
        return getConstants().breadcrumbDatasetAttributes();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, DatasetPresenter.TYPE_SetContextAreaDataset, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        SelectDatasetTabEvent.fire(this, DatasetTabTypeEnum.ATTRIBUTES);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(datasetCode)) {
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
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        datasetVersionUrn = CommonUtils.generateDatasetUrn(datasetCode);
        retrieveDataset(datasetVersionUrn);
        retrieveAttributes(datasetVersionUrn);
    }

    private void retrieveDataset(String datasetUrn) {
        dispatcher.execute(new GetDatasetVersionAction(datasetUrn), new WaitingAsyncCallbackHandlingError<GetDatasetVersionResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetVersionResult result) {
                SetDatasetEvent.fire(DatasetAttributesTabPresenter.this, result.getDatasetVersionDto());
            }
        });
    }

    private void retrieveAttributes(String datasetUrn) {
        dispatcher.execute(new GetDatasetAttributesAction(datasetUrn), new WaitingAsyncCallbackHandlingError<GetDatasetAttributesResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetAttributesResult result) {
                getView().setAttributes(result.getDatasetVersionAttributes());
            }
        });
    }

    @Override
    public void retrieveAttributeInstances(final DsdAttributeDto dsdAttributeDto) {
        dispatcher.execute(new GetDatasetAttributeInstancesAction(datasetVersionUrn, dsdAttributeDto.getIdentifier()), new WaitingAsyncCallbackHandlingError<GetDatasetAttributeInstancesResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetAttributeInstancesResult result) {
                getView().setAttributeInstances(dsdAttributeDto, result.getDsdAttributeInstanceDtos());
            }
        });
    }

    @Override
    public void retrieveDimensionCoverage(final String dimensionId, MetamacWebCriteria metamacWebCriteria) {
        dispatcher.execute(new GetDatasetDimensionCoverageAction(datasetVersionUrn, dimensionId, metamacWebCriteria), new WaitingAsyncCallbackHandlingError<GetDatasetDimensionCoverageResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetDimensionCoverageResult result) {
                getView().setDimensionCoverageValues(dimensionId, result.getCodesDimension());
            }
        });
    }

    //
    // RELATED RESOURCES
    //

    @Override
    public void retrieveItemsFromItemSchemeForDatasetLevelAttribute(RepresentationDto representationDto, int firstResult, int maxResults, MetamacWebCriteria criteria) {

        ItemSchemeWebCriteria itemSchemeWebCriteria = new ItemSchemeWebCriteria(criteria);

        if (!StringUtils.isBlank(representationDto.getCodelistRepresentationUrn())) {
            itemSchemeWebCriteria.setSchemeUrn(representationDto.getCodelistRepresentationUrn());
            retrieveCodesFromItemSchemeForDatasetLevelAttribute(firstResult, maxResults, itemSchemeWebCriteria);

        } else if (!StringUtils.isBlank(representationDto.getConceptSchemeRepresentationUrn())) {
            itemSchemeWebCriteria.setSchemeUrn(representationDto.getConceptSchemeRepresentationUrn());
            retrieveConceptsFromItemSchemeForDatasetLevelAttribute(firstResult, maxResults, itemSchemeWebCriteria);
        }
    }

    private void retrieveCodesFromItemSchemeForDatasetLevelAttribute(int firstResult, int maxResults, ItemSchemeWebCriteria criteria) {
        dispatcher.execute(new GetCodesPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetCodesPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetCodesPaginatedListResult result) {
                getView().setItemsForDatasetLevelAttributeValueSelection(result.getCodes(), result.getFirstResultOut(), result.getTotalResults());
            }
        });
    }

    private void retrieveConceptsFromItemSchemeForDatasetLevelAttribute(int firstResult, int maxResults, ItemSchemeWebCriteria criteria) {
        dispatcher.execute(new GetConceptsPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetConceptsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetConceptsPaginatedListResult result) {
                getView().setItemsForDatasetLevelAttributeValueSelection(result.getConcepts(), result.getFirstResultOut(), result.getTotalResults());
            }
        });
    }

    @Override
    public void retrieveItemsFromItemSchemeForDimensionOrGroupLevelAttribute(RepresentationDto representationDto, int firstResult, int maxResults, MetamacWebCriteria criteria) {

        ItemSchemeWebCriteria itemSchemeWebCriteria = new ItemSchemeWebCriteria(criteria);

        if (!StringUtils.isBlank(representationDto.getCodelistRepresentationUrn())) {
            itemSchemeWebCriteria.setSchemeUrn(representationDto.getCodelistRepresentationUrn());
            retrieveCodesFromItemSchemeForDimensionOrGroupLevelAttribute(firstResult, maxResults, itemSchemeWebCriteria);

        } else if (!StringUtils.isBlank(representationDto.getConceptSchemeRepresentationUrn())) {
            itemSchemeWebCriteria.setSchemeUrn(representationDto.getConceptSchemeRepresentationUrn());
            retrieveConceptsFromItemSchemeForDimensionOrGroupLevelAttribute(firstResult, maxResults, itemSchemeWebCriteria);
        }
    }

    private void retrieveCodesFromItemSchemeForDimensionOrGroupLevelAttribute(int firstResult, int maxResults, ItemSchemeWebCriteria criteria) {
        dispatcher.execute(new GetCodesPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetCodesPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetCodesPaginatedListResult result) {
                getView().setItemsForDimensionOrGroupLevelAttributeValueSelection(result.getCodes(), result.getFirstResultOut(), result.getTotalResults());
            }
        });
    }

    private void retrieveConceptsFromItemSchemeForDimensionOrGroupLevelAttribute(int firstResult, int maxResults, ItemSchemeWebCriteria criteria) {
        dispatcher.execute(new GetConceptsPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetConceptsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetConceptsPaginatedListResult result) {
                getView().setItemsForDimensionOrGroupLevelAttributeValueSelection(result.getConcepts(), result.getFirstResultOut(), result.getTotalResults());
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
}
