package org.siemac.metamac.statistical.resources.core.utils.shared;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Code;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Item;

public class ExternalItemUtils {

    public static ExternalItemDto buildExternalItemDtoFromCode(Code code) {
        return buildExternalItemDtoFromItem(code, TypeExternalArtefactsEnum.CODE);
    }

    public static ExternalItemDto buildExternalItemDtoFromConcept(Concept concept) {
        return buildExternalItemDtoFromItem(concept, TypeExternalArtefactsEnum.CONCEPT);
    }

    private static ExternalItemDto buildExternalItemDtoFromItem(Item item, TypeExternalArtefactsEnum type) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(item.getId());
        externalItemDto.setUri(item.getSelfLink().getHref());
        externalItemDto.setUrn(item.getUrn());
        externalItemDto.setUrnProvider(item.getUrnProvider());
        externalItemDto.setType(type);
        externalItemDto.setTitle(getInternationalStringDtoFromInternationalString(item.getName()));
        externalItemDto.setManagementAppUrl(item.getManagementAppLink());
        return externalItemDto;
    }

    private static InternationalStringDto getInternationalStringDtoFromInternationalString(InternationalString internationalString) {
        if (internationalString != null) {
            InternationalStringDto internationalStringDto = new InternationalStringDto();
            List<LocalisedString> localisedStringList = internationalString.getTexts();
            for (LocalisedString localisedString : localisedStringList) {
                LocalisedStringDto localisedStringDto = new LocalisedStringDto();
                localisedStringDto.setLocale(localisedString.getLang());
                localisedStringDto.setLabel(localisedString.getValue());
                internationalStringDto.addText(localisedStringDto);
            }
            return internationalStringDto;
        }
        return null;
    }
}
