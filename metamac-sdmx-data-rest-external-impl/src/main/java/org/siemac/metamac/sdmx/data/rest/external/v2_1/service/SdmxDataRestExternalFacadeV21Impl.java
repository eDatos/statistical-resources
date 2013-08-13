package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.sdmx.data.rest.external.conf.DataConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sdmxDataRestExternalFacadeV21")
public class SdmxDataRestExternalFacadeV21Impl implements SdmxDataRestExternalFacadeV21 {

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
    private DataConfiguration    srmConfiguration;

    private final ServiceContext ctx             = new ServiceContext("restExternal", "restExternal", "restExternal");
    private final Logger         logger          = LoggerFactory.getLogger(SdmxDataRestExternalFacadeV21Impl.class);
    private static boolean       IS_INTERNAL_API = false;

    @Override
    public Response findData(String flowRef, String detail) {

        InputStream is = SdmxDataRestExternalFacadeV21Impl.class.getResourceAsStream("/responses/00-data.xml");

        return Response.ok(is).build();
    }

    /***************************************************************
     * DATA
     ***************************************************************/

    /***************************************************************
     * 
     ***************************************************************/

}