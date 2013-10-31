package org.siemac.metamac.statistical.resources.web.server.utils;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ListBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Agency;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Category;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Code;
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
        externalItemDto.setCode(resource.getId());
        externalItemDto.setCodeNested(resource.getNestedId());
        externalItemDto.setUri(resource.getSelfLink().getHref());
        externalItemDto.setUrn(resource.getUrn());
        externalItemDto.setUrnProvider(resource.getUrnProvider());
        externalItemDto.setType(type);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(resource.getName()));
        externalItemDto.setManagementAppUrl(resource.getManagementAppLink());
        return externalItemDto;
    }

    public static ExternalItemDto buildExternalItemDtoFromAgency(Agency agency) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(agency.getId());
        externalItemDto.setCodeNested(agency.getNestedId());
        externalItemDto.setUri(agency.getSelfLink().getHref());
        externalItemDto.setUrn(agency.getUrn());
        externalItemDto.setUrnProvider(agency.getUrnProvider());
        externalItemDto.setType(TypeExternalArtefactsEnum.AGENCY);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(agency.getName()));
        externalItemDto.setManagementAppUrl(agency.getManagementAppLink());
        return externalItemDto;
    }

    public static ExternalItemDto buildExternalItemDtoFromCode(Code code) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(code.getId());
        externalItemDto.setUri(code.getSelfLink().getHref());
        externalItemDto.setUrn(code.getUrn());
        externalItemDto.setUrnProvider(code.getUrnProvider());
        externalItemDto.setType(TypeExternalArtefactsEnum.CODE);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(code.getName()));
        externalItemDto.setManagementAppUrl(code.getManagementAppLink());
        return externalItemDto;
    }

    public static ExternalItemDto buildExternalItemDtoFromCategory(Category category) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(category.getId());
        externalItemDto.setUri(category.getSelfLink().getHref());
        externalItemDto.setUrn(category.getUrn());
        externalItemDto.setUrnProvider(category.getUrnProvider());
        externalItemDto.setType(TypeExternalArtefactsEnum.CATEGORY);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(category.getName()));
        externalItemDto.setManagementAppUrl(category.getManagementAppLink());
        return externalItemDto;
    }
}
