package org.siemac.metamac.statistical.resources.core.invocation.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.common.mapper.CommonDto2DoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RestMapper {

    @Autowired
    @Qualifier("commonDto2DoMapper")
    CommonDto2DoMapper dto2DoMapper;

    public List<ExternalItem> buildExternalItemsFromResourcesInternal(List<ResourceInternal> resources) throws MetamacException {
        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();
        for (ResourceInternal resource : resources) {
            externalItems.add(buildExternalItemFromResourceInternal(resource));
        }
        return externalItems;
    }

    public ExternalItem buildExternalItemFromResourceInternal(ResourceInternal resource) throws MetamacException {
        ExternalItem externalItem = new ExternalItem();
        TypeExternalArtefactsEnum type = TypeExternalArtefactsEnum.fromValue(resource.getKind());

        externalItem.setType(type);
        externalItem.setCode(resource.getId());
        externalItem.setCodeNested(resource.getNestedId());
        externalItem.setUri(dto2DoMapper.externalItemApiUrlDtoToDo(type, resource.getSelfLink().getHref()));
        externalItem.setUrn(resource.getUrn());
        externalItem.setUrnProvider(resource.getUrnProvider());
        externalItem.setManagementAppUrl(dto2DoMapper.externalItemWebAppUrlDtoToDo(type, resource.getManagementAppLink()));
        externalItem.setTitle(getInternationalStringFromInternationalStringResource(resource.getName()));
        return externalItem;
    }

    public ExternalItem buildExternalItemFromSrmItemResourceInternal(ItemResourceInternal itemResourceInternal) throws MetamacException {
        return buildExternalItemFromResourceInternal(itemResourceInternal);
    }

    public ExternalItem buildExternalItemFromCode(CodeResourceInternal code) throws MetamacException {
        return buildExternalItemFromSrmItemResourceInternal(code);
    }

    public InternationalString getInternationalStringFromInternationalStringResource(org.siemac.metamac.rest.common.v1_0.domain.InternationalString intString) {
        InternationalString result = new InternationalString();
        for (org.siemac.metamac.rest.common.v1_0.domain.LocalisedString text : intString.getTexts()) {
            LocalisedString localised = new LocalisedString(text.getLang(), text.getValue());
            result.addText(localised);
        }

        return result;
    }
}
