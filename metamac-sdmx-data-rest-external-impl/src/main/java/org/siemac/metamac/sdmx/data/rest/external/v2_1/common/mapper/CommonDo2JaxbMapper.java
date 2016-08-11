package org.siemac.metamac.sdmx.data.rest.external.v2_1.common.mapper;

import java.util.List;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.CategoryReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.base.mapper.BaseDo2JaxbMapper;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public interface CommonDo2JaxbMapper extends BaseDo2JaxbMapper {

    public List<TextType> internationalStringDoToJaxb(InternationalString source);

    // *************************************************************************
    // References
    // *************************************************************************
    public CategoryReferenceType categoryReferenceTypeDoToJaxb(ExternalItem source);
    public ObjectReferenceType datasetVersionReferenceToJaxb(DatasetVersion source);
}