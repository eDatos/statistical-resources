package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.sdmx.resources.sdmxml.schemas.v2_1.message.Structure;
import org.siemac.metamac.sdmx.data.rest.external.conf.DataConfiguration;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.service.SdmxDataRestExternalFacadeV21;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("sdmxSrmRestExternalFacadeV21")
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
    private DataConfiguration     srmConfiguration;

    private final ServiceContext ctx             = new ServiceContext("restExternal", "restExternal", "restExternal");
    private final Logger         logger          = LoggerFactory.getLogger(SdmxDataRestExternalFacadeV21Impl.class);
    private static boolean       IS_INTERNAL_API = false;

    @Override
    public Structure findData(String detail, String references) {
        // TODO Auto-generated method stub
        return null;
    }

    /***************************************************************
     * DATA
     ***************************************************************/

    /***************************************************************
     * 
     ***************************************************************/

}