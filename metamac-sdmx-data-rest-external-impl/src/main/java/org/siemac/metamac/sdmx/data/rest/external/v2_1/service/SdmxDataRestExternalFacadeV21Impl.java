package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.sdmx.resources.sdmxml.rest.schemas.v2_1.types.DataParameterDetailEnum;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CategorisationsType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataflowsType;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.io.DeleteOnCloseFileInputStream;
import org.siemac.metamac.core.common.util.SdmxTimeUtils;
import org.siemac.metamac.sdmx.data.rest.external.conf.DataConfiguration;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.RestExternalConstants;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.categorisation.mapper.CategorisationsDo2JaxbMapper;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.dataflow.mapper.DataFlow2JaxbMapper;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.RestException;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.RestServiceExceptionType;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.utils.RestExceptionUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CategorisationProperties;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.io.domain.DsdSdmxInfo.DsdSdmxExtractor;
import org.siemac.metamac.statistical.resources.core.io.domain.RequestParameter;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacSdmx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.WriterDataCallbackImpl;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arte.statistic.parser.sdmx.v2_1.Sdmx21Writer;
import com.arte.statistic.parser.sdmx.v2_1.WriterDataCallback;
import com.arte.statistic.parser.sdmx.v2_1.domain.WriterResult;

import es.gobcan.istac.edatos.dataset.repository.service.DatasetRepositoriesServiceFacade;

@Service("sdmxDataRestExternalFacadeV21")
public class SdmxDataRestExternalFacadeV21Impl implements SdmxDataRestExternalFacadeV21 {

    @Autowired
    private MetamacSdmx2StatRepoMapper       metamac2StatRepoMapper;

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    @Autowired
    private DatasetService                   datasetService;

    @Autowired
    private DataConfiguration                dataConfiguration;

    @Autowired
    private DsdSdmxExtractor                 dsdSdmxExtractor;

    @Autowired
    private DataFlow2JaxbMapper              dataFlow2JaxbMapper;

    @Autowired
    private CategorisationsDo2JaxbMapper     categorisationsDo2JaxbMapper;

    private final ServiceContext             ctx                 = new ServiceContext("restExternal", "restExternal", "restExternal");
    private final Logger                     logger              = LoggerFactory.getLogger(SdmxDataRestExternalFacadeV21Impl.class);
    private final String                     HEADER_PARAM_ACCEPT = "accept";
    private final String                     NAME_SUFIX          = ".xml";
    private final String                     NAME_PREFIX         = "data";

    @Override
    public Response findData(HttpHeaders headers, String flowRef, String detail, String dimensionAtObservation, String startPeriod, String endPeriod) {
        try {
            WriterResult writerResult = processDataRequest(flowRef, null, null,
                    createRequestParameters(startPeriod, endPeriod, null, null, null, dimensionAtObservation, detail, getAcceptHeaderParameter(headers)));

            return Response.ok(new DeleteOnCloseFileInputStream(writerResult.getFile())).type(writerResult.getTypeSDMXDataMessageEnum().getValue())
                    .header("Content-Disposition", "attachment; filename=" + generateRandomAttachName()).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Response findData(HttpHeaders headers, String flowRef, String key, String detail, String dimensionAtObservation, String startPeriod, String endPeriod) {
        try {
            WriterResult writerResult = processDataRequest(flowRef, key, null,
                    createRequestParameters(startPeriod, endPeriod, null, null, null, dimensionAtObservation, detail, getAcceptHeaderParameter(headers)));

            return Response.ok(new DeleteOnCloseFileInputStream(writerResult.getFile())).type(writerResult.getTypeSDMXDataMessageEnum().getValue())
                    .header("Content-Disposition", "attachment; filename=" + generateRandomAttachName()).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Response findData(HttpHeaders headers, String flowRef, String key, String providerRef, String detail, String dimensionAtObservation, String startPeriod, String endPeriod) {
        try {
            WriterResult writerResult = processDataRequest(flowRef, key, providerRef,
                    createRequestParameters(startPeriod, endPeriod, null, null, null, dimensionAtObservation, detail, getAcceptHeaderParameter(headers)));

            return Response.ok(new DeleteOnCloseFileInputStream(writerResult.getFile())).type(writerResult.getTypeSDMXDataMessageEnum().getValue())
                    .header("Content-Disposition", "attachment; filename=" + generateRandomAttachName()).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public JAXBElement<DataflowsType> findDataFlowsInternal() {
        try {
            // Retrieve
            List<DatasetVersion> findDataFlows = findDataFlowsCore(null, null, null);

            return transformDataflowQueryToMessageReponse(findDataFlows);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public JAXBElement<DataflowsType> findDataFlowsInternal(String agencyID) {
        try {
            // Retrieve
            List<DatasetVersion> findDataFlows = findDataFlowsCore(agencyID, null, null);

            return transformDataflowQueryToMessageReponse(findDataFlows);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public JAXBElement<DataflowsType> findDataFlowsInternal(String agencyID, String resourceID) {
        try {
            // Retrieve
            List<DatasetVersion> findDataFlows = findDataFlowsCore(agencyID, resourceID, null);

            return transformDataflowQueryToMessageReponse(findDataFlows);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public JAXBElement<DataflowsType> findDataFlowsInternal(String agencyID, String resourceID, String version) {
        try {
            // Retrieve
            List<DatasetVersion> findDataFlows = findDataFlowsCore(agencyID, resourceID, version);

            return transformDataflowQueryToMessageReponse(findDataFlows);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public JAXBElement<CategorisationsType> findCategorisationsInternal() {
        try {
            // Retrieve
            List<Categorisation> findCategorisations = findCategorisationsCore(null, null, null);

            return transformCategorisationQueryToMessageReponse(findCategorisations);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public JAXBElement<CategorisationsType> findCategorisationsInternal(String agencyID) {
        try {
            // Retrieve
            List<Categorisation> findCategorisations = findCategorisationsCore(agencyID, null, null);

            return transformCategorisationQueryToMessageReponse(findCategorisations);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public JAXBElement<CategorisationsType> findCategorisationsInternal(String agencyID, String resourceID) {
        try {
            // Retrieve
            List<Categorisation> findCategorisations = findCategorisationsCore(agencyID, resourceID, null);

            return transformCategorisationQueryToMessageReponse(findCategorisations);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public JAXBElement<CategorisationsType> findCategorisationsInternal(String agencyID, String resourceID, String version) {
        try {
            // Retrieve
            List<Categorisation> findCategorisations = findCategorisationsCore(agencyID, resourceID, version);

            return transformCategorisationQueryToMessageReponse(findCategorisations);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    /***************************************************************
     * DATA PRIVATE
     ***************************************************************/
    private WriterResult processDataRequest(String flowRef, String key, String providerRef, RequestParameter requestParameter) throws Exception {
        // flowRef: Always required
        // key: nullable, then is the same that SAME wildcard
        // key: providerRef, then is the same that SAME wildcard

        if (key != null) {
            if (RestExternalConstants.WildcardIdentifyingEnum.ALL.equals(RestExternalConstants.WildcardIdentifyingEnum.fromCaseInsensitiveString(key))) {
                key = null;
            }
        }

        if (providerRef != null) {
            if (RestExternalConstants.WildcardIdentifyingEnum.ALL.equals(RestExternalConstants.WildcardIdentifyingEnum.fromCaseInsensitiveString(providerRef))) {
                providerRef = null;
            }
        }

        WriterDataCallback writerDataCallback = new WriterDataCallbackImpl(datasetRepositoriesServiceFacade, metamac2StatRepoMapper, dsdSdmxExtractor, findDataSetFromDataFlow(flowRef, providerRef),
                key, requestParameter, dataConfiguration.retrieveOrganisationIDDefault());

        return Sdmx21Writer.writerData(writerDataCallback);
    }

    /**
     * FLOWREF = "AGENCY_ID,FLOW_ID,VERSION" with AGENCY_ID or VERSION optional
     * NOTE: Nothing to do with providerRef in this implementation of the API
     *
     * @param flowRef
     * @param providerRef
     * @return
     * @throws MetamacException
     */
    private PagedResult<DatasetVersion> findDataSetFromDataFlow(String flowRef, String providerRef) throws MetamacException {

        String[] flowRefParts = flowRef.split(",");

        String agencyId = null; // "ALL";
        String flowID = null;
        String version = null; // "LATEST";

        if (flowRefParts.length == 3) {
            agencyId = flowRefParts[0];
            flowID = flowRefParts[1];
            version = flowRefParts[2];
        } else if (flowRefParts.length == 2) {
            agencyId = flowRefParts[0];
            flowID = flowRefParts[1];
        } else if (flowRefParts.length == 1) {
            flowID = flowRefParts[0];
        }

        List<ConditionalCriteria> conditions = createDatasetVersionsConditions(agencyId, flowID, version);
        PagedResult<DatasetVersion> datasetVersions = datasetService.findDatasetVersionsByCondition(ctx, conditions, PagingParameter.noLimits());

        if (datasetVersions.getValues().isEmpty()) {
            throw manageException(new RestException(RestExceptionUtils.getError(RestServiceExceptionType.NOT_FOUND), Status.NOT_FOUND));
        }

        return datasetVersions;
    }

    protected List<ConditionalCriteria> createCategorisationsConditions(String agencyId, String resourceId, String version) {
        List<ConditionalCriteria> conditions = new ArrayList<ConditionalCriteria>();

        // Add flow ID
        if (!StringUtils.isEmpty(resourceId) && RestExternalConstants.WildcardIdentifyingEnum.UNKNOWN.equals(RestExternalConstants.WildcardIdentifyingEnum.fromCaseInsensitiveString(resourceId))) {
            conditions.add(ConditionalCriteriaBuilder.criteriaFor(Categorisation.class).withProperty(CategorisationProperties.versionableStatisticalResource().code()).eq(resourceId).buildSingle());
        }

        // Add agency query
        if (!StringUtils.isEmpty(agencyId) && RestExternalConstants.WildcardIdentifyingEnum.UNKNOWN.equals(RestExternalConstants.WildcardIdentifyingEnum.fromCaseInsensitiveString(agencyId))) {
            conditions.add(ConditionalCriteriaBuilder.criteriaFor(Categorisation.class).withProperty(CategorisationProperties.maintainer().codeNested()).eq(agencyId).buildSingle());
        }

        // Add version query
        if (version == null) {
            version = StatisticalResourcesVersionUtils.INITIAL_VERSION;
        }

        //@formatter:off
        conditions.add(ConditionalCriteriaBuilder.criteriaFor(Categorisation.class).
                withProperty(CategorisationProperties.versionableStatisticalResource().versionLogic()).eq(version).
                and().
                withProperty(CategorisationProperties.datasetVersion().siemacMetadataStatisticalResource().procStatus()).eq(ProcStatusEnum.PUBLISHED).
                buildSingle());

       conditions.add(ConditionalCriteriaBuilder.criteriaFor(Categorisation.class).
                withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(CategorisationProperties.datasetVersion().siemacMetadataStatisticalResource().validFrom(), Categorisation.class)).lessThanOrEqual(new DateTime().toDate()).
                buildSingle());

        conditions.add(ConditionalCriteriaBuilder.criteriaFor(Categorisation.class).
                withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(CategorisationProperties.datasetVersion().siemacMetadataStatisticalResource().validTo(), Categorisation.class)).isNull().
                or().
                withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(CategorisationProperties.datasetVersion().siemacMetadataStatisticalResource().validTo(), Categorisation.class)).greaterThan(new DateTime().toDate())
        .buildSingle());
        //@formatter:on

        conditions.add(ConditionalCriteriaBuilder.criteriaFor(Categorisation.class).distinctRoot().buildSingle());
        return conditions;
    }

    protected List<ConditionalCriteria> createDatasetVersionsConditions(String agencyId, String flowID, String version) {
        List<ConditionalCriteria> conditions = new ArrayList<ConditionalCriteria>();

        // Add flow ID
        if (!StringUtils.isEmpty(flowID) && RestExternalConstants.WildcardIdentifyingEnum.UNKNOWN.equals(RestExternalConstants.WildcardIdentifyingEnum.fromCaseInsensitiveString(flowID))) {
            conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().code()).eq(flowID).buildSingle());
        }

        // Add agency query
        if (!StringUtils.isEmpty(agencyId) && RestExternalConstants.WildcardIdentifyingEnum.UNKNOWN.equals(RestExternalConstants.WildcardIdentifyingEnum.fromCaseInsensitiveString(agencyId))) {
            conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().maintainer().codeNested())
                    .eq(agencyId).buildSingle());
        }

        // Add version query
        RestExternalConstants.WildcardIdentifyingEnum versionWildcard = RestExternalConstants.WildcardIdentifyingEnum.UNKNOWN;
        if (version != null) {
            versionWildcard = RestExternalConstants.WildcardIdentifyingEnum.fromCaseInsensitiveString(version);
        } else {
            versionWildcard = RestExternalConstants.WildcardIdentifyingEnum.LATEST;
        }

        if (RestExternalConstants.WildcardIdentifyingEnum.UNKNOWN.equals(versionWildcard)) {
            conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().versionLogic()).eq(version)
                    .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus()).eq(ProcStatusEnum.PUBLISHED).and()
                    .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().validFrom()).lessThanOrEqual(new DateTime()).buildSingle());
        } else if (RestExternalConstants.WildcardIdentifyingEnum.LATEST.equals(versionWildcard)) {
            //@formatter:off
            conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).
                withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus()).eq(ProcStatusEnum.PUBLISHED).
                and().
                withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.siemacMetadataStatisticalResource().validFrom(), DatasetVersion.class)).lessThanOrEqual(new DateTime().toDate())
            .buildSingle());

            conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).
                    withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.siemacMetadataStatisticalResource().validTo(), DatasetVersion.class)).isNull().
                    or().
                    withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.siemacMetadataStatisticalResource().validTo(), DatasetVersion.class)).greaterThan(new DateTime().toDate())
            .buildSingle());

            //@formatter:on
        }
        conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).distinctRoot().buildSingle());
        return conditions;
    }

    private List<DatasetVersion> findDataFlowsCore(String agencyID, String resourceID, String version) throws Exception {
        // Find
        List<ConditionalCriteria> conditions = createDatasetVersionsConditions(agencyID, null, version);
        PagedResult<DatasetVersion> datasetsVersionsResult = datasetService.findDatasetVersionsByCondition(ctx, conditions, PagingParameter.noLimits());
        return datasetsVersionsResult.getValues();
    }

    private List<Categorisation> findCategorisationsCore(String agencyID, String resourceID, String version) throws Exception {
        // Find
        List<ConditionalCriteria> conditions = createCategorisationsConditions(agencyID, resourceID, version);

        PagedResult<Categorisation> categorisations = datasetService.findCategorisationsByCondition(ctx, conditions, PagingParameter.noLimits());
        return categorisations.getValues();
    }

    protected JAXBElement<DataflowsType> transformDataflowQueryToMessageReponse(List<DatasetVersion> datasetVersions) throws MetamacException {
        DataflowsType dataflowsDo2Jaxb = dataFlow2JaxbMapper.dataflowsDo2Jaxb(datasetVersions, false);
        return new JAXBElement<DataflowsType>(new QName("structure:Dataflows"), DataflowsType.class, dataflowsDo2Jaxb);
    }

    protected JAXBElement<CategorisationsType> transformCategorisationQueryToMessageReponse(List<Categorisation> categorisations) throws MetamacException {

        CategorisationsType categorisationDoToJaxb = categorisationsDo2JaxbMapper.categorisationDoToJaxb(categorisations, false);
        return new JAXBElement<CategorisationsType>(new QName("structure:Categorisations"), CategorisationsType.class, categorisationDoToJaxb);
    }

    /**
     * Throws response error, logging exception
     */
    private RestException manageException(Exception e) {
        logger.error("Error", e);
        if (e instanceof RestException) {
            return (RestException) e;
        } else {
            // do not show information details about exception to user
            org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error = RestExceptionUtils.getError(RestServiceExceptionType.UNKNOWN);
            return new RestException(error, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private RequestParameter createRequestParameters(String startPeriod, String endPeriod, String updatedAfter, String firstNObservations, String lastNObservations, String dimensionAtObservation,
            String detail, String proposeContentType) {
        RequestParameter requestParameter = new RequestParameter();

        // START_PERIOD
        if (StringUtils.isNotEmpty(startPeriod)) {
            if (SdmxTimeUtils.isTimeRange(startPeriod)) {
                org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error = RestExceptionUtils.getError(RestServiceExceptionType.PARAMETER_UNKNOWN, RestExternalConstants.START_PERIOD, startPeriod);
                throw new RestException(error, Status.INTERNAL_SERVER_ERROR);
            }
            requestParameter.setStartPeriod(startPeriod);
        }

        // END_PERIOD
        if (StringUtils.isNotEmpty(endPeriod)) {
            if (SdmxTimeUtils.isTimeRange(endPeriod)) {
                org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error = RestExceptionUtils.getError(RestServiceExceptionType.PARAMETER_UNKNOWN, RestExternalConstants.START_PERIOD, startPeriod);
                throw new RestException(error, Status.INTERNAL_SERVER_ERROR);
            }
            requestParameter.setEndPeriod(endPeriod);
        }

        // UPDATED_AFTER
        if (StringUtils.isNotEmpty(updatedAfter)) {
            requestParameter.setUpdatedAfter(updatedAfter);
        }

        // FIRST_N_OBSERVATIONS
        if (StringUtils.isNotEmpty(firstNObservations)) {
            requestParameter.setFirstNObservations(firstNObservations);
        }

        // LAST_N_OBSERVATIONS
        if (StringUtils.isNotEmpty(lastNObservations)) {
            requestParameter.setLastNObservations(lastNObservations);
        }

        // DIMENSION_AT_OBSERVATION
        if (StringUtils.isNotEmpty(dimensionAtObservation)) {
            requestParameter.setDimensionAtObservation(dimensionAtObservation);
        }

        // DETAIL
        if (StringUtils.isNotEmpty(detail)) {
            DataParameterDetailEnum dataParameterDetail = DataParameterDetailEnum.fromCaseInsensitiveString(detail);
            if (DataParameterDetailEnum.UNKNOWN.equals(dataParameterDetail)) {
                org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error = RestExceptionUtils.getError(RestServiceExceptionType.PARAMETER_UNKNOWN, RestExternalConstants.DETAIL, detail);
                throw new RestException(error, Status.INTERNAL_SERVER_ERROR);
            }
            requestParameter.setDetail(dataParameterDetail);
        } else {
            requestParameter.setDetail(DataParameterDetailEnum.FULL); // Default
        }

        // Content type
        if (StringUtils.isNotEmpty(proposeContentType)) {
            requestParameter.setProposeContentTypeString(proposeContentType);
        }

        return requestParameter;
    }

    private String getAcceptHeaderParameter(HttpHeaders headers) {
        if (headers.getRequestHeader(HEADER_PARAM_ACCEPT) != null && !headers.getRequestHeader(HEADER_PARAM_ACCEPT).isEmpty()) {
            return headers.getRequestHeader(HEADER_PARAM_ACCEPT).get(0);
        }
        return null;
    }

    private String generateRandomAttachName() {
        return NAME_PREFIX + RandomStringUtils.randomAlphanumeric(10) + NAME_SUFIX;
    }
}