package org.siemac.metamac.statistical.resources.core.invocation.utils;

import java.util.ArrayList;
import java.util.List;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CodeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ConceptType;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;

public class RestMapper {

    public static List<ExternalItem> buildExternalItemsFromSrmResources(List<ResourceInternal> resources, TypeExternalArtefactsEnum type) {
        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();
        for (ResourceInternal resource : resources) {
            externalItems.add(buildExternalItemFromSrmResource(resource, type));
        }
        return externalItems;
    }

    public static ExternalItem buildExternalItemFromSrmResource(ResourceInternal resource, TypeExternalArtefactsEnum type) {
        ExternalItem externalItem = new ExternalItem();
        externalItem.setCode(resource.getId());
        // target.setCodeNested(source.getNestedId()); // TODO nestedId
        externalItem.setUri(resource.getSelfLink().getHref());
        externalItem.setUrn(resource.getUrn());
        externalItem.setUrnInternal(resource.getUrnSiemac());
        externalItem.setType(type);
        externalItem.setManagementAppUrl(resource.getManagementAppLink()); // TODO no falta quitar endpoint??
        externalItem.setTitle(getInternationalStringFromInternationalStringResource(resource.getName()));
        return externalItem;
    }

    public static InternationalString getInternationalStringFromInternationalStringResource(org.siemac.metamac.rest.common.v1_0.domain.InternationalString intString) {
        InternationalString result = new InternationalString();
        for (org.siemac.metamac.rest.common.v1_0.domain.LocalisedString text : intString.getTexts()) {
            LocalisedString localised = new LocalisedString(text.getLang(), text.getValue());
            result.addText(localised);
        }

        return result;
    }

    public static ExternalItem buildExternalItemFromCode(CodeType code) {
        ExternalItem externalItem = new ExternalItem();
        externalItem.setCode(code.getId());
        // target.setCodeNested(source.getNestedId()); // TODO nestedId
        externalItem.setUri(code.getUri());
        externalItem.setUrn(code.getUrn());
        externalItem.setUrnInternal(code.getUrn()); // TODO puede no corresponder a ésta
        externalItem.setType(TypeExternalArtefactsEnum.CODE);
        externalItem.setTitle(getInternationalStringFromTextTypes(code.getNames()));
        return externalItem;
    }

    public static ExternalItem buildExternalItemFromConcept(ConceptType concept) {
        ExternalItem externalItem = new ExternalItem();
        externalItem.setCode(concept.getId());
        // target.setCodeNested(source.getNestedId()); // TODO nestedId
        externalItem.setUri(concept.getUri());
        externalItem.setUrn(concept.getUrn());
        externalItem.setUrnInternal(concept.getUrn()); // TODO puede no corresponder a ésta
        externalItem.setType(TypeExternalArtefactsEnum.CONCEPT);
        externalItem.setTitle(getInternationalStringFromTextTypes(concept.getNames()));
        return externalItem;
    }

    private static InternationalString getInternationalStringFromTextTypes(List<TextType> texts) {
        InternationalString intString = new InternationalString();
        for (TextType text : texts) {
            intString.addText(new LocalisedString(text.getLang(), text.getValue()));
        }
        return intString;
    }
}
