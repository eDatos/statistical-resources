package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import java.io.FileInputStream;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.sdmx.data.rest.external.conf.DataConfiguration;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.RestExternalConstants;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.RestException;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.RestServiceExceptionType;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.utils.RestExceptionUtils;
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
    private DataConfiguration                dataConfiguration;

    private final ServiceContext             ctx                 = new ServiceContext("restExternal", "restExternal", "restExternal");
    private final Logger                     logger              = LoggerFactory.getLogger(SdmxDataRestExternalFacadeV21Impl.class);
    private final String                     HEADER_PARAM_ACCEPT = "accept";

    @Override
    public Response findData(HttpHeaders headers, String flowRef, String detail, String dimensionAtObservation) {
        try {
            WriterResult writerResult = processrequest(flowRef, null, null, createRequestParameters(null, null, null, null, null, dimensionAtObservation, detail), getAcceptHeaderParameter(headers));

            return Response.ok(new FileInputStream(writerResult.getFile())).type(writerResult.getTypeSDMXDataMessageEnum().getValue()).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    private String getAcceptHeaderParameter(HttpHeaders headers) {
        if (headers.getRequestHeader(HEADER_PARAM_ACCEPT) != null && !headers.getRequestHeader(HEADER_PARAM_ACCEPT).isEmpty()) {
            return headers.getRequestHeader(HEADER_PARAM_ACCEPT).get(0);
        }
        return null;
    }

    @Override
    public Response findData(HttpHeaders headers, String flowRef, String key, String detail, String dimensionAtObservation) {
        try {
            WriterResult writerResult = processrequest(flowRef, key, null, createRequestParameters(null, null, null, null, null, dimensionAtObservation, detail), getAcceptHeaderParameter(headers));

            return Response.ok(new FileInputStream(writerResult.getFile())).type(writerResult.getTypeSDMXDataMessageEnum().getValue()).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Response findData(HttpHeaders headers, String flowRef, String key, String providerRef, String detail, String dimensionAtObservation) {
        try {
            WriterResult writerResult = processrequest(flowRef, key, providerRef, createRequestParameters(null, null, null, null, null, dimensionAtObservation, detail),
                    getAcceptHeaderParameter(headers));

            return Response.ok(new FileInputStream(writerResult.getFile())).type(writerResult.getTypeSDMXDataMessageEnum().getValue()).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    /***************************************************************
     * DATA PRIVATE
     ***************************************************************/
    private WriterResult processrequest(String flowRef, String key, String providerRef, RequestParameter requestParameter, String proposeContentType) throws Exception {
        // flowRef: Always required
        // key: nullable, then is the same that SAME wildcard
        // key: providerRef, then is the same that SAME wildcard

        if (key != null) {
            if (RestExternalConstants.WildcardIdentifyingEnum.ALL.equals(RestExternalConstants.WildcardIdentifyingEnum.fromCaseInsensitiveString(key))) {
                key = null;
            }
        }

        if (providerRef != null) {
            if (RestExternalConstants.WildcardIdentifyingEnum.UNKNOWN.equals(RestExternalConstants.WildcardIdentifyingEnum.fromCaseInsensitiveString(providerRef))) {
                providerRef = null;
            }
        }

        // TODO ver como vamos a hacer la correspondencia entre Dataflow y Dataset
        ExternalItem externalItem = new ExternalItem();
        externalItem.setType(TypeExternalArtefactsEnum.AGENCY);
        externalItem.setCode("SDMX01");
        externalItem.setUrn("urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0).SDMX01");

        WriterDataCallback writerDataCallback = new WriterDataCallbackImpl(datasetRepositoriesServiceFacade, metamac2StatRepoMapper, findDataSetFromDataFlow(flowRef, providerRef), key,
                requestParameter, externalItem, dataConfiguration.retrieveOrganisationIDDefault()); // TODO
        // manejar
        // dataflow
        return Sdmx21Writer.writerData(writerDataCallback, proposeContentType);
    }

    private String findDataSetFromDataFlow(String flowRef, String providerRef) {
        if (providerRef == null) {
            // Any maintainer
        }

        return flowRef;
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
            requestParameter.setStartPeriod(startPeriod);
        }

        // END_PERIOD
        if (StringUtils.isNotEmpty(endPeriod)) {
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
        if (StringUtils.isNotEmpty(detail) && RestExternalConstants.DETAIL.equals(detail)) {
            requestParameter.setDetail(detail);
        }

        return requestParameter;
    }

}