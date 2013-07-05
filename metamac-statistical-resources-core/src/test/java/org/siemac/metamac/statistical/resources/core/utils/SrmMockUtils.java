package org.siemac.metamac.statistical.resources.core.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.DimensionTypeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TimeDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CodeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ConceptType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleDataStructureRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Code;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;

public class SrmMockUtils {

    public static MeasureDimensionType buildMeasureDimension(String id, ConceptScheme conceptSchemeRepresentation) {
        MeasureDimensionType measureDim = new MeasureDimensionType();
        measureDim.setId(id);
        measureDim.setType(DimensionTypeType.MEASURE_DIMENSION);
        
        MeasureDimensionRepresentationType representationType = new MeasureDimensionRepresentationType();
        representationType.setEnumeration(buildConceptSchemeRef(conceptSchemeRepresentation));
        
        measureDim.setLocalRepresentation(representationType);
        return measureDim;
    }
    
    public static TimeDimensionType buildTimeDimension(String id, TimeDataType type) {
        TimeDimensionType dim = new TimeDimensionType();
        dim.setId(id);
        dim.setType(DimensionTypeType.TIME_DIMENSION);
        
        TimeDimensionRepresentationType representationType = new TimeDimensionRepresentationType();
        TimeTextFormatType formatType = new TimeTextFormatType();
        formatType.setTextType(type);
        representationType.setTextFormat(formatType);
        
        dim.setLocalRepresentation(representationType);

        return dim;
    }
    
    public static Dimension buildGeoDimension(String id, Codelist codelist) {
        Dimension dim = new Dimension();
        dim.setId(id);
        dim.setIsSpatial(true);
        dim.setType(DimensionTypeType.DIMENSION);

        SimpleDataStructureRepresentationType representation = new SimpleDataStructureRepresentationType();
        representation.setEnumeration(buildCodelistRef(codelist));
        
        dim.setLocalRepresentation(representation);
        
        return dim;
    }
    
    public static ConceptScheme buildConceptScheme(String id, String name, String lang, String urn) {
        ConceptScheme scheme = new ConceptScheme();
        scheme.setId(id);
        scheme.setUrn(urn);
        scheme.getNames().add(buildTextType(name, lang));
        return scheme;
    }
    
    public static ConceptSchemeReferenceType buildConceptSchemeRef(ConceptScheme scheme) {
        if (scheme != null) {
            ConceptSchemeReferenceType ref = new ConceptSchemeReferenceType();
            ref.setURN(scheme.getUrn());
            return ref;
        }
        return null;
    }
    
    public static Concept buildConcept(String id, String name, String lang) {
        Concept concept = new Concept();
        concept.setId(id);
        concept.setUrn("urn:uuid"+id);
        concept.setUri(UUID.randomUUID().toString());
        concept.getNames().add(buildTextType(name, lang));
        return concept;
    }
    
    public static Codelist buildCodelist(String id, String name, String lang, String urn) {
        Codelist scheme = new Codelist();
        scheme.setId(id);
        scheme.setUrn(urn);
        scheme.getNames().add(buildTextType(name, lang));
        return scheme;
    }
    
    public static CodelistReferenceType buildCodelistRef(Codelist scheme) {
        if (scheme != null) {
            CodelistReferenceType ref = new CodelistReferenceType();
            ref.setURN(scheme.getUrn());
            return ref;
        }
        return null;
    }

    public static Code buildCode(String id, String name, String lang) {
        Code code = new Code();
        code.setId(id);
        code.setUrn("urn:uuid"+id);
        code.setUri(UUID.randomUUID().toString());
        code.getNames().add(buildTextType(name, lang));
        return code;
    }

    public static TextType buildTextType(String label, String lang) {
        TextType text = new TextType();
        text.setLang(lang);
        text.setValue(label);
        return text;
    }

    public static Codes mockCodesResult(List<CodeType> codes) {
        Codes codesList = new Codes();
        codesList.getCodes().addAll(buildResourcesInternalCodes(codes));
        codesList.setTotal(BigInteger.valueOf(codes.size()));
        codesList.setOffset(BigInteger.ZERO);
        return codesList;
    }
    
    public static Concepts mockConceptsResult(List<ConceptType> concepts) {
        Concepts conceptsList = new Concepts();
        conceptsList.getConcepts().addAll(buildResourcesInternalConcepts(concepts));
        conceptsList.setTotal(BigInteger.valueOf(concepts.size()));
        conceptsList.setOffset(BigInteger.ZERO);
        return conceptsList;
    }
    
    

    private static List<ResourceInternal> buildResourcesInternalCodes(List<CodeType> codes) {
        List<ResourceInternal> resources = new ArrayList<ResourceInternal>();
        for (CodeType code : codes) {
            resources.add(buildResourceInternalFromCode(code));
        }
        return resources;
    }
    
    private static  List<ResourceInternal> buildResourcesInternalConcepts(List<ConceptType> concepts) {
        List<ResourceInternal> resources = new ArrayList<ResourceInternal>();
        for (ConceptType concept : concepts) {
            resources.add(buildResourceInternalFromConcept(concept));
        }
        return resources;
    }
    
    private static  ResourceInternal buildResourceInternalFromCode(CodeType code) {
        ResourceInternal resource = new ResourceInternal();
        ResourceLink link = new ResourceLink();
        link.setHref(code.getUri());
        resource.setSelfLink(link);
        resource.setId(code.getId());
        resource.setUrn(code.getUrn());
        resource.setUrnInternal(code.getUrn());
        resource.setName(buildInternationalStringFromTextType(code.getNames()));
        resource.setManagementAppLink("http://"+code.getId());
        return resource;
    }
    
    private static  ResourceInternal buildResourceInternalFromConcept(ConceptType concept) {
        ResourceInternal resource = new ResourceInternal();
        ResourceLink link = new ResourceLink();
        link.setHref(concept.getUri());
        resource.setSelfLink(link);
        resource.setId(concept.getId());
        resource.setUrn(concept.getUrn());
        resource.setUrnInternal(concept.getUrn());
        resource.setName(buildInternationalStringFromTextType(concept.getNames()));
        resource.setManagementAppLink("http://"+concept.getId());
        return resource;
    }
    
    private static  InternationalString buildInternationalStringFromTextType(List<TextType> texts) {
        InternationalString intString = new InternationalString();
        for (TextType text : texts) {
            LocalisedString loc = new LocalisedString();
            loc.setLang(text.getLang());
            loc.setValue(text.getValue());
            intString.getTexts().add(loc);
        }
        return intString;
    }
}
