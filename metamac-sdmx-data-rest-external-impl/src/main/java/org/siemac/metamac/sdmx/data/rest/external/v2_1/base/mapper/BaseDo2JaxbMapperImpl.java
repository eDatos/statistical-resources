package org.siemac.metamac.sdmx.data.rest.external.v2_1.base.mapper;

@org.springframework.stereotype.Component("baseDo2JaxbMapperSdmxSrm")
public class BaseDo2JaxbMapperImpl implements BaseDo2JaxbMapper {

    private org.sdmx.resources.sdmxml.schemas.v2_1.structure.ObjectFactory      structureObjectFactory     = new org.sdmx.resources.sdmxml.schemas.v2_1.structure.ObjectFactory();

    private org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectFactory         commonObjectFactory        = new org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectFactory();

    private org.sdmx.resources.sdmxml.schemas.v2_1.message.ObjectFactory        messageObjectFactory       = new org.sdmx.resources.sdmxml.schemas.v2_1.message.ObjectFactory();

    private org.sdmx.resources.sdmxml.schemas.v2_1.message.footer.ObjectFactory messageFooterObjectFactory = new org.sdmx.resources.sdmxml.schemas.v2_1.message.footer.ObjectFactory();

    @Override
    public org.sdmx.resources.sdmxml.schemas.v2_1.structure.ObjectFactory getStructureObjectFactory() {
        return structureObjectFactory;
    }

    @Override
    public org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectFactory getCommonObjectFactory() {
        return commonObjectFactory;
    }

    @Override
    public org.sdmx.resources.sdmxml.schemas.v2_1.message.ObjectFactory getMessageObjectFactory() {
        return messageObjectFactory;
    }

    @Override
    public org.sdmx.resources.sdmxml.schemas.v2_1.message.footer.ObjectFactory getMessageFooterObjectFactory() {
        return messageFooterObjectFactory;
    }

}