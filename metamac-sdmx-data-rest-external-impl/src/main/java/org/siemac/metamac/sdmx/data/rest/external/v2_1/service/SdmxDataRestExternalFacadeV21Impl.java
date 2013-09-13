package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.sdmx.resources.sdmxml.rest.schemas.v2_1.types.DataParameterDetailEnum;
import org.sdmx.resources.sdmxml.rest.schemas.v2_1.types.StructureParameterDetailEnum;
import org.sdmx.resources.sdmxml.rest.schemas.v2_1.types.StructureParameterReferencesEnum;
import org.sdmx.resources.sdmxml.schemas.v2_1.message.SenderType;
import org.sdmx.resources.sdmxml.schemas.v2_1.message.Structure;
import org.sdmx.resources.sdmxml.schemas.v2_1.message.StructureHeaderType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.Structures;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.core.common.util.SdmxTimeUtils;
import org.siemac.metamac.sdmx.data.rest.external.conf.DataConfiguration;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.RestExternalConstants;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.dataflow.mapper.DataFlow2JaxbMapper;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.RestException;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.RestServiceExceptionType;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.utils.RestExceptionUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.io.domain.DsdSdmxInfo.DsdSdmxExtractor;
import org.siemac.metamac.statistical.resources.core.io.domain.RequestParameter;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacSdmx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.WriterDataCallbackImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.Sdmx21Writer;
import com.arte.statistic.parser.sdmx.v2_1.WriterDataCallback;
import com.arte.statistic.parser.sdmx.v2_1.domain.WriterResult;

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

    private final ServiceContext             ctx                 = new ServiceContext("restExternal", "restExternal", "restExternal");
    private final Logger                     logger              = LoggerFactory.getLogger(SdmxDataRestExternalFacadeV21Impl.class);
    private final String                     HEADER_PARAM_ACCEPT = "accept";

    @Override
    public Response findData(HttpHeaders headers, String flowRef, String detail, String dimensionAtObservation, String startPeriod, String endPeriod) {
        try {
            WriterResult writerResult = processDataRequest(flowRef, null, null, createRequestParameters(startPeriod, endPeriod, null, null, null, dimensionAtObservation, detail),
                    getAcceptHeaderParameter(headers));

            return Response.ok(new FileInputStream(writerResult.getFile())).type(writerResult.getTypeSDMXDataMessageEnum().getValue()).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Response findData(HttpHeaders headers, String flowRef, String key, String detail, String dimensionAtObservation, String startPeriod, String endPeriod) {
        try {
            WriterResult writerResult = processDataRequest(flowRef, key, null, createRequestParameters(startPeriod, endPeriod, null, null, null, dimensionAtObservation, detail),
                    getAcceptHeaderParameter(headers));

            return Response.ok(new FileInputStream(writerResult.getFile())).type(writerResult.getTypeSDMXDataMessageEnum().getValue()).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Response findData(HttpHeaders headers, String flowRef, String key, String providerRef, String detail, String dimensionAtObservation, String startPeriod, String endPeriod) {
        try {
            WriterResult writerResult = processDataRequest(flowRef, key, providerRef, createRequestParameters(startPeriod, endPeriod, null, null, null, dimensionAtObservation, detail),
                    getAcceptHeaderParameter(headers));

            return Response.ok(new FileInputStream(writerResult.getFile())).type(writerResult.getTypeSDMXDataMessageEnum().getValue()).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Structure findDataFlows(String detail, String references) {
        try {
            // Retrieve
            List<DatasetVersion> findDataFlows = findDataFlowsCore(null, null, null);

            return transformDataflowQueryToMessageReponse(detail, references, findDataFlows);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    /***************************************************************
     * DATA PRIVATE
     ***************************************************************/
    private WriterResult processDataRequest(String flowRef, String key, String providerRef, RequestParameter requestParameter, String proposeContentType) throws Exception {
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

        return Sdmx21Writer.writerData(writerDataCallback, proposeContentType);
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
            manageException(new RestException(RestExceptionUtils.getError(RestServiceExceptionType.UNKNOWN), Status.NOT_FOUND)); // TODO establecer el mesnaje de error correcto si no se encuentran las
            throw MetamacExceptionBuilder.builder().withExceptionItems(CommonServiceExceptionType.UNKNOWN).build();
        }

        return datasetVersions;
    }

    protected List<ConditionalCriteria> createDatasetVersionsConditions(String agencyId, String flowID, String version) {
        List<ConditionalCriteria> conditions = new ArrayList<ConditionalCriteria>();
        // conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).buildSingle());

        // Add flow ID
        if (!StringUtils.isEmpty(flowID)) {
            conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().code()).eq(flowID).buildSingle());
        }

        // Add agency query
        if (!StringUtils.isEmpty(agencyId)) {
            conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().maintainer().codeNested())
                    .eq(agencyId).buildSingle());
        }

        // Add version query
        if (!StringUtils.isEmpty(version)) {
            conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().versionLogic()).eq(version)
                    .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus()).eq(ProcStatusEnum.PUBLISHED).and()
                    .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().validFrom()).lessThanOrEqual(new DateTime()).buildSingle());
        } else {
            DateTime now = new DateTime();
            conditions.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus())
                    .eq(ProcStatusEnum.PUBLISHED).and().withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().validFrom()).lessThanOrEqual(now).and().lbrace()
                    .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().validTo()).greaterThan(now).or()
                    .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().validTo()).isNull().rbrace().buildSingle());
        }
        return conditions;
    }

    private List<DatasetVersion> findDataFlowsCore(String agencyID, String resourceID, String version) throws Exception {
        // Find
        List<ConditionalCriteria> conditions = createDatasetVersionsConditions(agencyID, null, version);
        PagedResult<DatasetVersion> datasetsVersionsResult = datasetService.findDatasetVersionsByCondition(ctx, conditions, PagingParameter.noLimits());
        return datasetsVersionsResult.getValues();
    }

    protected Structure transformDataflowQueryToMessageReponse(String detail, String references, List<DatasetVersion> datasetVersions) throws MetamacException {
        // Transform
        StructureParameterDetailEnum detailEnum = checkParameterDetail(detail);
        StructureParameterReferencesEnum referencesEnum = checkParameterReferences(references);

        // Msg : Header | Structures (0..1) | Footer (0..1)
        Structure structure = new Structure();

        // Header *************************************
        StructureHeaderType structureHeaderType = new StructureHeaderType();

        // ID (Required): identifies an identification for the message, assigned by the sender.
        structureHeaderType.setID("Structure_Message");

        // Prepared (Required): is the date the message was prepared.
        structureHeaderType.setPrepared(CoreCommonUtil.jodaDateTime2xsDateTime(new DateTime()));

        // Sender (Required): is information about the party that is transmitting the message.
        SenderType senderType = new SenderType();
        senderType.setId("TODO");
        structureHeaderType.setSender(senderType);

        // Test (Optional, default is false): Test indicates whether the message is for test purposes or not.
        structureHeaderType.setTest(Boolean.FALSE);

        // Receiver (Optional): Receiver is information about the party that is the intended recipient of the message.
        // Name (Optional: Name provides a name for the transmission. Multiple instances allow for parallel language values.
        // Source (Optional): provides human-readable information about the source of the data.

        structure.setHeader(structureHeaderType);

        // Pair<Map<String, RelatedResourceTypeEnum>, Map<String, RelatedResourceTypeEnum>> calculateReferences = MapperUtils.initializeReferenceStructure();
        // messageDo2JaxbMapper.addConceptSchemes(structure, conceptSchemeVersions, null, isRetrieveAsStub(detailEnum, referencesEnum, true), calculateReferences);
        structure.setStructures(new Structures());
        structure.getStructures().setDataflows(dataFlow2JaxbMapper.dataflowsDo2Jaxb(datasetVersions, isRetrieveAsStub(detailEnum, referencesEnum, true)));
        // addOtherArtefactsInMessage(structure, RelatedResourceTypeEnum.CONCEPT_SCHEME, calculateReferences, detailEnum, referencesEnum);
        return structure;
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
            String detail) {
        RequestParameter requestParameter = new RequestParameter();

        // START_PERIOD
        if (StringUtils.isNotEmpty(startPeriod)) {
            if (SdmxTimeUtils.isTimeRange(startPeriod)) {
                // TODO mensaje de error: SDMX no permite el tipo temporal TimeRange en este parámetro
                org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error = RestExceptionUtils.getError(RestServiceExceptionType.PARAMETER_UNKNOWN, RestExternalConstants.START_PERIOD, startPeriod);
                throw new RestException(error, Status.INTERNAL_SERVER_ERROR);
            }
            requestParameter.setStartPeriod(startPeriod);
        }

        // END_PERIOD
        if (StringUtils.isNotEmpty(endPeriod)) {
            if (SdmxTimeUtils.isTimeRange(endPeriod)) {
                // TODO mensaje de error: SDMX no permite el tipo temporal TimeRange en este parámetro
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

        return requestParameter;
    }

    private String getAcceptHeaderParameter(HttpHeaders headers) {
        if (headers.getRequestHeader(HEADER_PARAM_ACCEPT) != null && !headers.getRequestHeader(HEADER_PARAM_ACCEPT).isEmpty()) {
            return headers.getRequestHeader(HEADER_PARAM_ACCEPT).get(0);
        }
        return null;
    }

    private StructureParameterDetailEnum checkParameterDetail(String detail) {
        // Detail
        StructureParameterDetailEnum parameterDetail = StructureParameterDetailEnum.fromCaseInsensitiveString(detail);
        if (StructureParameterDetailEnum.UNKNOWN.equals(parameterDetail)) {
            org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error = RestExceptionUtils.getError(RestServiceExceptionType.PARAMETER_UNKNOWN, RestExternalConstants.DETAIL, detail);
            throw new RestException(error, Status.INTERNAL_SERVER_ERROR);
        }
        return parameterDetail;
    }

    private StructureParameterReferencesEnum checkParameterReferences(String references) {
        // References
        StructureParameterReferencesEnum parameterRefereceEnum = StructureParameterReferencesEnum.fromCaseInsensitiveString(references);
        if (StructureParameterReferencesEnum.UNKNOWN.equals(parameterRefereceEnum)) {
            org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error = RestExceptionUtils.getError(RestServiceExceptionType.UNKNOWN, RestExternalConstants.REFERENCES, references);
            throw new RestException(error, Status.INTERNAL_SERVER_ERROR);
        } else if (StructureParameterReferencesEnum.UNSUPPORTED.equals(parameterRefereceEnum)) {
            org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error = RestExceptionUtils.getError(RestServiceExceptionType.UNKNOWN, RestExternalConstants.REFERENCES, references);
            throw new RestException(error, Status.INTERNAL_SERVER_ERROR);
        }
        return parameterRefereceEnum;
    }

    protected boolean isRetrieveAsStub(StructureParameterDetailEnum detailEnum, StructureParameterReferencesEnum referencesEnum, boolean isFistInvokation) {
        if (StructureParameterReferencesEnum.NONE.equals(referencesEnum)) {
            if (StructureParameterDetailEnum.FULL.equals(detailEnum)) {
                return false;
            } else if (StructureParameterDetailEnum.ALLSTUBS.equals(detailEnum)) {
                return true;
            } else if (StructureParameterDetailEnum.REFERENCESTUBS.equals(detailEnum)) {
                return false; // No references will be returned, the query artifact is full
            }
        } else if (StructureParameterReferencesEnum.CHILDREN.equals(referencesEnum)) {
            if (StructureParameterDetailEnum.FULL.equals(detailEnum)) {
                return false;
            } else if (StructureParameterDetailEnum.ALLSTUBS.equals(detailEnum)) {
                return true;
            } else if (StructureParameterDetailEnum.REFERENCESTUBS.equals(detailEnum)) {
                return !isFistInvokation;
            }
        }
        return false;
    }

}