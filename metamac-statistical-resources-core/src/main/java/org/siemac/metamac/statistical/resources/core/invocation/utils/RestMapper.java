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
        ExternalItem externalItem = new ExternalItem(
                resource.getId(),
                resource.getSelfLink().getHref(),
                resource.getUrn(),
                resource.getUrnInternal(),
                type,
                getInternationalStringFromInternationalStringResource(resource.getName()),
                resource.getManagementAppLink());
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
        ExternalItem externalItem = new ExternalItem(
                code.getId(),
                code.getUri(),
                code.getUrn(),
                code.getUrn(),
                TypeExternalArtefactsEnum.CODE,
                getInternationalStringFromTextTypes(code.getNames()),
                null);
        return externalItem;
    }
    
    public static ExternalItem buildExternalItemFromConcept(ConceptType concept) {
        ExternalItem externalItem = new ExternalItem(
                concept.getId(),
                concept.getUri(),
                concept.getUrn(),
                concept.getUrn(),
                TypeExternalArtefactsEnum.CODE,
                getInternationalStringFromTextTypes(concept.getNames()),
                null);
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
