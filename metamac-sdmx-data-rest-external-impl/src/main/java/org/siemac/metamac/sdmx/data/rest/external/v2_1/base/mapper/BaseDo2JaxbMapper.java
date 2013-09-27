package org.siemac.metamac.sdmx.data.rest.external.v2_1.base.mapper;

public interface BaseDo2JaxbMapper {

    public org.sdmx.resources.sdmxml.schemas.v2_1.structure.ObjectFactory getStructureObjectFactory();

    public org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectFactory getCommonObjectFactory();

    public org.sdmx.resources.sdmxml.schemas.v2_1.message.ObjectFactory getMessageObjectFactory();

    public org.sdmx.resources.sdmxml.schemas.v2_1.message.footer.ObjectFactory getMessageFooterObjectFactory();
}