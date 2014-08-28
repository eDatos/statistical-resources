package org.siemac.metamac.statistical.resources.web.server.utils;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ListBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Agency;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Code;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Item;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Organisation;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;

public class ExternalItemWebUtils extends org.siemac.metamac.web.common.client.utils.ExternalItemUtils {

    public static ExternalItemsResult createExternalItemsResultFromListBase(ListBase listBase, List<ExternalItemDto> dtos) {
        ExternalItemsResult result = new ExternalItemsResult();
        if (listBase != null) {
            result.setFirstResult(listBase.getOffset() != null ? listBase.getOffset().intValue() : 0);
            result.setTotalResults(listBase.getOffset() != null ? listBase.getTotal().intValue() : 0);
            result.setExternalItemDtos(dtos);
        }
        return result;
    }

    public static ExternalItemDto buildExternalItemDtoFromResource(ResourceInternal resource, TypeExternalArtefactsEnum type) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        buildExternalItemDtoFromResource(externalItemDto, resource, type);
        return externalItemDto;
    }

    public static void buildExternalItemDtoFromResource(ExternalItemDto externalItemDto, ResourceInternal resource, TypeExternalArtefactsEnum type) {
        externalItemDto.setCode(resource.getId());
        externalItemDto.setCodeNested(resource.getNestedId());
        externalItemDto.setUri(resource.getSelfLink().getHref());
        externalItemDto.setUrn(resource.getUrn());
        externalItemDto.setUrnProvider(resource.getUrnProvider());
        externalItemDto.setType(type);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(resource.getName()));
        externalItemDto.setManagementAppUrl(resource.getManagementAppLink());
    }

    public static ExternalItemDto buildExternalItemDtoFromAgency(Agency agency) {
        return buildExternalItemDtoFromOrganisation(agency, TypeExternalArtefactsEnum.AGENCY);
    }

    public static ExternalItemDto buildExternalItemDtoFromCode(Code code) {
        return buildExternalItemDtoFromItem(code, TypeExternalArtefactsEnum.CODE);
    }

    public static ExternalItemDto buildExternalItemDtoFromCategory(Category category) {
        return buildExternalItemDtoFromItem(category, TypeExternalArtefactsEnum.CATEGORY);
    }

    public static ExternalItemDto buildExternalItemDtoFromCodelist(Codelist codelist) {
        return buildExternalItemDtoFromItemScheme(codelist, TypeExternalArtefactsEnum.CODELIST);
    }

    public static ExternalItemDto buildExternalItemDtoFromConceptScheme(ConceptScheme conceptScheme) {
        return buildExternalItemDtoFromItemScheme(conceptScheme, TypeExternalArtefactsEnum.CONCEPT_SCHEME);
    }

    private static ExternalItemDto buildExternalItemDtoFromItemScheme(ItemScheme itemScheme, TypeExternalArtefactsEnum type) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(itemScheme.getId());
        externalItemDto.setCodeNested(itemScheme.getNestedId());
        externalItemDto.setUri(itemScheme.getSelfLink().getHref());
        externalItemDto.setUrn(itemScheme.getUrn());
        externalItemDto.setUrnProvider(itemScheme.getUrnProvider());
        externalItemDto.setType(type);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(itemScheme.getName()));
        externalItemDto.setManagementAppUrl(itemScheme.getManagementAppLink());
        return externalItemDto;
    }

    private static ExternalItemDto buildExternalItemDtoFromItem(Item item, TypeExternalArtefactsEnum type) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(item.getId());
        externalItemDto.setCodeNested(item.getNestedId());
        externalItemDto.setUri(item.getSelfLink().getHref());
        externalItemDto.setUrn(item.getUrn());
        externalItemDto.setUrnProvider(item.getUrnProvider());
        externalItemDto.setType(type);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(item.getName()));
        externalItemDto.setManagementAppUrl(item.getManagementAppLink());
        return externalItemDto;
    }

    private static ExternalItemDto buildExternalItemDtoFromOrganisation(Organisation organisation, TypeExternalArtefactsEnum type) {
        return buildExternalItemDtoFromItem(organisation, type);
    }
}
