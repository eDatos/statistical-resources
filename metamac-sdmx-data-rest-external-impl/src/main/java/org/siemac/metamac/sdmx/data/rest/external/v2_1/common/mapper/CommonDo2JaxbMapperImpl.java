package org.siemac.metamac.sdmx.data.rest.external.v2_1.common.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CategoryRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CategoryReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.base.mapper.BaseDo2JaxbMapperImpl;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

@org.springframework.stereotype.Component("commonDo2JaxbMapperSdmxSrm")
public class CommonDo2JaxbMapperImpl extends BaseDo2JaxbMapperImpl implements CommonDo2JaxbMapper {

    @Override
    public List<TextType> internationalStringDoToJaxb(InternationalString source) {
        List<TextType> target = new ArrayList<TextType>();
        if (source == null) {
            return target;
        }
        for (LocalisedString localisedStringDo : source.getTexts()) {
            // LocalisedStringDo to TextType
            TextType textType = getCommonObjectFactory().createTextType();
            textType.setLang(localisedStringDo.getLocale());
            textType.setValue(localisedStringDo.getLabel());

            // Add to result
            target.add(textType);
        }
        return target;
    }

    @Override
    public CategoryReferenceType categoryReferenceTypeDoToJaxb(ExternalItem source) {
        if (source == null) {
            return null;
        }

        CategoryReferenceType categoryReferenceType = getCommonObjectFactory().createCategoryReferenceType();

        if (StringUtils.isNotEmpty(source.getUrnProvider())) {
            // By Urn
            categoryReferenceType.setURN(source.getUrnProvider());
        } else {
            // For adding references in details parameter in API
            categoryReferenceType.setURN(source.getUrn());
        }

        // By CategoryRefType
        String[] urnParts = UrnUtils.splitUrnItem(source.getUrn());
        CategoryRefType categoryRefType = new CategoryRefType();
        categoryRefType.setAgencyID(urnParts[0]);
        categoryRefType.setMaintainableParentID(urnParts[1]);
        categoryRefType.setMaintainableParentVersion(urnParts[2]);
        categoryRefType.setId(urnParts[3]);
        categoryReferenceType.setRef(categoryRefType);

        return categoryReferenceType;
    }

    @Override
    public ObjectReferenceType datasetVersionReferenceToJaxb(DatasetVersion source) {
        if (source == null) {
            return null;
        }

        ObjectReferenceType objectReferenceType = getCommonObjectFactory().createObjectReferenceType();

        // By Urn
        objectReferenceType.setURN(source.getSiemacMetadataStatisticalResource().getUrn());

        ObjectRefType objectRefType = getCommonObjectFactory().createObjectRefType();
        objectRefType.setAgencyID(source.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested());
        objectRefType.setId(source.getSiemacMetadataStatisticalResource().getCode());
        objectRefType.setVersion(source.getSiemacMetadataStatisticalResource().getVersionLogic());
        objectReferenceType.setRef(objectRefType);

        return objectReferenceType;
    }
}