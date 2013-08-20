package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.sdmx.data.rest.external.conf.DataConfiguration;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.RestExternalConstants;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.RestException;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.RestServiceExceptionType;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.utils.RestExceptionUtils;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacSdmx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.WriterDataCallbackImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.Sdmx21Writer;
import com.arte.statistic.parser.sdmx.v2_1.WriterDataCallback;

@Service("sdmxDataRestExternalFacadeV21")
public class SdmxDataRestExternalFacadeV21Impl implements SdmxDataRestExternalFacadeV21 {

    @Autowired
    private MetamacSdmx2StatRepoMapper       metamac2StatRepoMapper;

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    // @Autowired
    // private CodesService codesService;
    //
    // @Autowired
    // private ConceptsService conceptsService;
    //
    // @Autowired
    // private OrganisationsService organisationsService;
    //
    // @Autowired
    // private CategoriesService categoriesService;
    //
    // @Autowired
    // private DataStructureDefinitionService dataStructureDefinitionService;
    //
    // @Autowired
    // @Qualifier("messageDo2JaxbMapperSdmxSrm")
    // private MessageDo2JaxbMapper messageDo2JaxbMapper;
    //
    // @Autowired
    // @Qualifier("codesDo2JaxbRestExternalCallback")
    // private CodesDo2JaxbCallback codesDo2JaxbCallback;

    @Autowired
    private DataConfiguration                dataConfiguration;

    private final ServiceContext             ctx    = new ServiceContext("restExternal", "restExternal", "restExternal");
    private final Logger                     logger = LoggerFactory.getLogger(SdmxDataRestExternalFacadeV21Impl.class);

    @Override
    public Response findData(String flowRef, String detail) {
        try {
            InputStream is = processrequest(flowRef, null, null);

            return Response.ok(is).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Response findData(String flowRef, String key, String detail) {
        try {
            InputStream is = processrequest(flowRef, key, null);

            return Response.ok(is).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Response findData(String flowRef, String key, String providerRef, String detail) {
        try {
            InputStream is = processrequest(flowRef, key, providerRef);

            return Response.ok(is).build();
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    // TODO Añdir el media Type en las respuestas, ver tb el otro API de SDMX para ponerlo

    // <response status="200">
    // <representation mediaType="application/vnd.sdmx.genericdata+xml;version=2.1" element="message:GenericData"/>
    // <representation mediaType="application/vnd.sdmx.structurespecificdata+xml;version=2.1" element="message:StructureSpecificData"/>
    // <representation mediaType="application/vnd.sdmx.generictimeseriesdata+xml;version=2.1" element="message:GenericTimeSeriesData"/>
    // <representation mediaType="application/vnd.sdmx.structurespecifictimeseriesdata+xml;version=2.1" element="message:StructureSpecificTimeSeriesData"/>
    // </response>
    // <response status="400 401 404 413 500 501 503">
    // <representation mediaType="application/vnd.sdmx.error+xml;version=2.1" element="message:Error"/>
    // </response>

    /***************************************************************
     * DATA PRIVATE
     ***************************************************************/
    private InputStream processrequest(String flowRef, String key, String providerRef) throws Exception {
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

        WriterDataCallback writerDataCallback = new WriterDataCallbackImpl(datasetRepositoriesServiceFacade, metamac2StatRepoMapper, findDataSetFromDataFlow(flowRef, providerRef), key); // TODO
                                                                                                                                                                                          // manejar
                                                                                                                                                                                          // dataflow
        File writerData = Sdmx21Writer.writerData(writerDataCallback, true);

        return new FileInputStream(writerData);
    }

    private String findDataSetFromDataFlow(String flowRef, String providerRef) {
        if (providerRef == null) {
            // Any maintainer
        }

        // TODO ver como vamos a hacer la correspondencia entre Dataflow y Dataset

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
}