package org.siemac.metamac.statistical.resources.core.invocation.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.common.mapper.CommonDto2DoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RestMapper {

    @Autowired
    @Qualifier("commonDto2DoMapper")
    CommonDto2DoMapper dto2DoMapper;

    public List<ExternalItem> buildExternalItemsFromSrmResources(List<ResourceInternal> resources) throws MetamacException {
        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();
        for (ResourceInternal resource : resources) {
            externalItems.add(buildExternalItemFromSrmResource(resource));
        }
        return externalItems;
    }

    public ExternalItem buildExternalItemFromSrmResource(ResourceInternal resource) throws MetamacException {
        ExternalItem externalItem = new ExternalItem();
        externalItem.setCode(resource.getId());
        externalItem.setCodeNested(resource.getNestedId());
        externalItem.setUri(dto2DoMapper.srmInternalApiUrlDtoToDo(resource.getSelfLink().getHref()));
        externalItem.setUrn(resource.getUrn());
        externalItem.setUrnProvider(resource.getUrnProvider());
        externalItem.setType(TypeExternalArtefactsEnum.fromValue(resource.getKind()));
        externalItem.setManagementAppUrl(dto2DoMapper.srmInternalWebAppUrlDtoToDo(resource.getManagementAppLink()));
        externalItem.setTitle(getInternationalStringFromInternationalStringResource(resource.getName()));
        return externalItem;
    }

    public ExternalItem buildExternalItemFromCode(CodeResourceInternal code) throws MetamacException {
        ExternalItem externalItem = new ExternalItem();
        externalItem.setCode(code.getId());
        externalItem.setCodeNested(code.getNestedId());
        externalItem.setUri(dto2DoMapper.srmInternalApiUrlDtoToDo(code.getSelfLink().getHref()));
        externalItem.setUrn(code.getUrn());
        externalItem.setUrnProvider(code.getUrnProvider());
        externalItem.setType(TypeExternalArtefactsEnum.fromValue(code.getKind()));
        externalItem.setManagementAppUrl(dto2DoMapper.srmInternalWebAppUrlDtoToDo(code.getManagementAppLink()));
        externalItem.setTitle(getInternationalStringFromInternationalStringResource(code.getName()));
        return externalItem;
    }

    public ExternalItem buildExternalItemFromSrmItemResourceInternal(ItemResourceInternal itemResourceInternal) throws MetamacException {
        ExternalItem externalItem = new ExternalItem();
        externalItem.setCode(itemResourceInternal.getId());
        externalItem.setCodeNested(itemResourceInternal.getNestedId());
        externalItem.setUri(dto2DoMapper.srmInternalApiUrlDtoToDo(itemResourceInternal.getSelfLink().getHref()));
        externalItem.setUrn(itemResourceInternal.getUrn());
        externalItem.setUrnProvider(itemResourceInternal.getUrnProvider());
        externalItem.setType(TypeExternalArtefactsEnum.fromValue(itemResourceInternal.getKind()));
        externalItem.setManagementAppUrl(dto2DoMapper.srmInternalWebAppUrlDtoToDo(itemResourceInternal.getManagementAppLink()));
        externalItem.setTitle(getInternationalStringFromInternationalStringResource(itemResourceInternal.getName()));
        return externalItem;
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
