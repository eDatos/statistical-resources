package org.siemac.metamac.statistical.resources.core.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.BasicComponentDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.DimensionTypeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalGroupKeyDescriptorRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalGroupKeyDescriptorReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalPrimaryMeasureRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalPrimaryMeasureReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TimeDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeRelationshipType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.BasicComponentTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CodeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ConceptRepresentation;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ConceptType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.PrimaryMeasureType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleDataStructureRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.UsageStatusType;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Code;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;

public class SrmMockUtils {

    public static ConceptScheme buildConceptSchemeWithConcepts(String id, String urn, String lang, int numConcepts) {
        ConceptScheme conceptScheme = SrmMockUtils.buildConceptScheme(id, "title", lang, urn);
        for (int i = 1; i <= numConcepts; i++) {
            conceptScheme.getConcepts().add(SrmMockUtils.buildConcept("concept-0" + i, "Concept 0" + i, lang));
        }
        return conceptScheme;
    }

    public static Codelist buildCodelistWithCodes(String id, String urn, String lang, int numCodes) {
        Codelist codelist = SrmMockUtils.buildCodelist(id, "title", lang, urn);
        for (int i = 1; i <= numCodes; i++) {
            codelist.getCodes().add(SrmMockUtils.buildCode("code-0" + i, "Code 0" + i, lang));
        }
        return codelist;
    }

    public static DataStructure mockDsdWithGeoTimeAndMeasureDimensions(String urn, String geoId, String timeId, String measureId, ConceptScheme measureConceptScheme, Codelist geoCodelist) {
        MeasureDimensionType measureDim = SrmMockUtils.buildMeasureDimension(measureId, measureConceptScheme);
        TimeDimensionType timeDim = SrmMockUtils.buildTimeDimension(timeId, TimeDataType.REPORTING_YEAR);
        Dimension geoDim = SrmMockUtils.buildGeoDimension(geoId, geoCodelist);

        DataStructure dsd = new DataStructure();
        dsd.setUrn(urn);
        DataStructureComponentsType components = new DataStructureComponentsType();
        components.setDimensionList(new DimensionListType());
        components.getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions().add(measureDim);
        components.getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions().add(timeDim);
        components.getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions().add(geoDim);
        dsd.setDataStructureComponents(components);

        return dsd;
    }

    public static MeasureDimensionType buildMeasureDimension(String id, ConceptScheme conceptSchemeRepresentation) {
        MeasureDimensionType measureDim = new MeasureDimensionType();
        measureDim.setId(id);
        measureDim.setType(DimensionTypeType.MEASURE_DIMENSION);

        MeasureDimensionRepresentationType representationType = new MeasureDimensionRepresentationType();
        representationType.setEnumeration(buildConceptSchemeRef(conceptSchemeRepresentation));

        measureDim.setLocalRepresentation(representationType);

        measureDim.setConceptIdentity(buildConceptReferenceTypeWithURN("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_TYPE"));
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
        dim.setConceptIdentity(buildConceptReferenceTypeWithURN("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_TYPE"));
        return dim;
    }

    public static TimeDimensionType buildTimeDimension(String id, String conceptIdentityURN, TimeDataType type) {
        TimeDimensionType dim = new TimeDimensionType();
        dim.setId(id);
        dim.setType(DimensionTypeType.TIME_DIMENSION);

        TimeDimensionRepresentationType representationType = new TimeDimensionRepresentationType();
        TimeTextFormatType formatType = new TimeTextFormatType();
        formatType.setTextType(type);
        representationType.setTextFormat(formatType);

        dim.setLocalRepresentation(representationType);

        dim.setConceptIdentity(buildConceptReferenceTypeWithURN(conceptIdentityURN));
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

        dim.setConceptIdentity(buildConceptReferenceTypeWithURN("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_TYPE"));
        return dim;
    }

    public static Dimension buildDimension(String id, String conceptIdentityURN, String representationURN) {
        Dimension dimension = new Dimension();
        dimension.setId(id);
        dimension.setIsSpatial(false);
        dimension.setType(DimensionTypeType.DIMENSION);

        dimension.setConceptIdentity(buildConceptReferenceTypeWithURN(conceptIdentityURN));

        if (StringUtils.isNotEmpty(representationURN)) {
            dimension.setLocalRepresentation(buildSimpleDataStructureRepresentationTypeWithURN(representationURN));
        }
        return dimension;
    }

    public static GroupType buildGroupType(String groupId, String... dimensions) {
        GroupType groupType = new GroupType();
        groupType.setId(groupId);

        for (int i = 0; i < dimensions.length; i++) {
            GroupDimensionType groupDimensionType = new GroupDimensionType();
            LocalDimensionReferenceType localDimensionReferenceType = buildLocalDimensionReferenceType(dimensions[i]);
            groupDimensionType.setDimensionReference(localDimensionReferenceType);

            groupType.getGroupDimensions().add(groupDimensionType);
        }

        return groupType;
    }

    protected static LocalDimensionReferenceType buildLocalDimensionReferenceType(String dimensionId) {
        LocalDimensionReferenceType localDimensionReferenceType = new LocalDimensionReferenceType();
        LocalDimensionRefType localDimensionRefType = new LocalDimensionRefType();
        localDimensionRefType.setId(dimensionId);
        localDimensionReferenceType.setRef(localDimensionRefType);
        return localDimensionReferenceType;
    }

    private static Attribute buildAttributeType(String attributeId, String conceptIdentityURN, UsageStatusType usageStatusType, String representationURN) {
        Attribute attribute = new Attribute();
        attribute.setId(attributeId);
        attribute.setAssignmentStatus(usageStatusType);

        attribute.setConceptIdentity(buildConceptReferenceTypeWithURN(conceptIdentityURN));

        if (StringUtils.isNotEmpty(representationURN)) {
            attribute.setLocalRepresentation(buildSimpleDataStructureRepresentationTypeWithURN(representationURN));
        }

        return attribute;
    }

    public static Attribute buildAttributeTypeWithGroupRelationship(String attributeId, String conceptIdentityURN, String representationURN, UsageStatusType usageStatusType, String groupId) {
        Attribute attribute = buildAttributeType(attributeId, conceptIdentityURN, usageStatusType, representationURN);

        AttributeRelationshipType attributeRelationshipType = new AttributeRelationshipType();
        LocalGroupKeyDescriptorReferenceType localGroupKeyDescriptorReferenceType = buildLocalGroupKeyDescriptorReferenceType(groupId);
        attributeRelationshipType.setGroup(localGroupKeyDescriptorReferenceType);

        attribute.setAttributeRelationship(attributeRelationshipType);

        return attribute;
    }

    protected static LocalGroupKeyDescriptorReferenceType buildLocalGroupKeyDescriptorReferenceType(String groupId) {
        LocalGroupKeyDescriptorReferenceType localGroupKeyDescriptorReferenceType = new LocalGroupKeyDescriptorReferenceType();
        LocalGroupKeyDescriptorRefType localGroupKeyDescriptorRefType = new LocalGroupKeyDescriptorRefType();
        localGroupKeyDescriptorRefType.setId(groupId);
        localGroupKeyDescriptorReferenceType.setRef(localGroupKeyDescriptorRefType);
        return localGroupKeyDescriptorReferenceType;
    }

    public static Attribute buildAttributeTypeWithDimensionRelationship(String attributeId, String conceptIdentityURN, String representationURN, UsageStatusType usageStatusType,
            List<String> dimensions, List<String> groups) {
        Attribute attribute = buildAttributeType(attributeId, conceptIdentityURN, usageStatusType, representationURN);

        AttributeRelationshipType attributeRelationshipType = new AttributeRelationshipType();

        for (String dimension : dimensions) {
            attributeRelationshipType.getDimensions().add(buildLocalDimensionReferenceType(dimension));
        }

        for (String group : groups) {
            attributeRelationshipType.getAttachmentGroups().add(buildLocalGroupKeyDescriptorReferenceType(group));
        }

        attribute.setAttributeRelationship(attributeRelationshipType);

        return attribute;
    }

    public static Attribute buildAttributeTypeWithPrimaryMeasureRelationship(String attributeId, String conceptIdentityURN, String representationURN, UsageStatusType usageStatusType) {
        Attribute attribute = buildAttributeType(attributeId, conceptIdentityURN, usageStatusType, representationURN);

        AttributeRelationshipType attributeRelationshipType = new AttributeRelationshipType();

        LocalPrimaryMeasureReferenceType localPrimaryMeasureReferenceType = new LocalPrimaryMeasureReferenceType();
        LocalPrimaryMeasureRefType localPrimaryMeasureRefType = new LocalPrimaryMeasureRefType();
        localPrimaryMeasureRefType.setId("OBS_VALUE");
        localPrimaryMeasureReferenceType.setRef(localPrimaryMeasureRefType);
        attributeRelationshipType.setPrimaryMeasure(localPrimaryMeasureReferenceType);

        attribute.setAttributeRelationship(attributeRelationshipType);

        return attribute;
    }

    private static SimpleDataStructureRepresentationType buildSimpleDataStructureRepresentationTypeWithURN(String representationURN) {
        SimpleDataStructureRepresentationType simpleDataStructureRepresentationType = new SimpleDataStructureRepresentationType();
        simpleDataStructureRepresentationType.setEnumeration(buildCodelistRef(representationURN));
        return simpleDataStructureRepresentationType;
    }

    public static PrimaryMeasureType buildPrimaryMeasure(String conceptIdentityURN, String representationURN) {
        PrimaryMeasureType primaryMeasureType = new PrimaryMeasureType();
        primaryMeasureType.setId("OBS_VALUE");
        primaryMeasureType.setConceptIdentity(buildConceptReferenceTypeWithURN(conceptIdentityURN));

        if (StringUtils.isNotEmpty(representationURN)) {
            primaryMeasureType.setLocalRepresentation(buildSimpleDataStructureRepresentationTypeWithURN(representationURN));
        }

        return primaryMeasureType;
    }

    public static ConceptReferenceType buildConceptReferenceTypeWithURN(String conceptIdentityURN) {
        ConceptReferenceType conceptReferenceType = new ConceptReferenceType();
        conceptReferenceType.setURN(conceptIdentityURN);
        conceptReferenceType.setRef(buildConceptRefType(conceptIdentityURN));
        return conceptReferenceType;
    }

    protected static ConceptRefType buildConceptRefType(String codelistUrn) {
        ConceptRefType conceptRefType = new ConceptRefType();
        String[] params = UrnUtils.splitUrnItem(codelistUrn);
        conceptRefType.setAgencyID(params[0]);
        conceptRefType.setMaintainableParentID(params[1]);
        conceptRefType.setMaintainableParentVersion(params[2]);
        conceptRefType.setId(params[3]);
        return conceptRefType;
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
            ref.setRef(buildConceptSchemeRefType(scheme.getUrn()));
            ref.setURN(scheme.getUrn());
            return ref;
        }
        return null;
    }
    public static Concept buildConcept(String id, String name, String lang) {
        Concept concept = new Concept();
        concept.setId(id);
        concept.setUrn("urn:uuid" + id);
        concept.setUri(UUID.randomUUID().toString());
        concept.getNames().add(buildTextType(name, lang));
        return concept;
    }

    public static Concept buildConcept(String id, String urn, String name, String lang, ConceptRepresentation conceptRepresentation) {
        Concept concept = new Concept();
        concept.setId(id);
        concept.setUrn(urn);
        concept.setUri(UUID.randomUUID().toString());
        concept.getNames().add(buildTextType(name, lang));
        concept.setCoreRepresentation(conceptRepresentation);
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
            ref.setRef(buildCodelistRefType(scheme.getUrn()));
            ref.setURN(scheme.getUrn());

            return ref;
        }
        return null;
    }

    public static CodelistReferenceType buildCodelistRef(String codelistUrn) {
        CodelistReferenceType ref = new CodelistReferenceType();

        ref.setRef(buildCodelistRefType(codelistUrn));

        ref.setURN(codelistUrn);

        return ref;
    }

    protected static CodelistRefType buildCodelistRefType(String codelistUrn) {
        CodelistRefType codelistRefType = new CodelistRefType();
        String[] params = UrnUtils.splitUrnItemScheme(codelistUrn);
        codelistRefType.setAgencyID(params[0]);
        codelistRefType.setId(params[1]);
        codelistRefType.setVersion(params[2]);
        return codelistRefType;
    }

    protected static ConceptSchemeRefType buildConceptSchemeRefType(String codelistUrn) {
        ConceptSchemeRefType conceptSchemeRefType = new ConceptSchemeRefType();
        String[] params = UrnUtils.splitUrnItemScheme(codelistUrn);
        conceptSchemeRefType.setAgencyID(params[0]);
        conceptSchemeRefType.setId(params[1]);
        conceptSchemeRefType.setVersion(params[2]);
        return conceptSchemeRefType;
    }

    public static Code buildCode(String id, String name, String lang) {
        Code code = new Code();
        code.setId(id);
        code.setUrn("urn:uuid" + id);
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

    public static ConceptRepresentation buildConceptRepresentation(String codelistUrn) {
        ConceptRepresentation conceptRepresentation = new ConceptRepresentation();
        conceptRepresentation.setEnumeration(buildCodelistRef(codelistUrn));
        return conceptRepresentation;
    }

    public static ConceptRepresentation buildConceptRepresentation(BasicComponentDataType textType) {
        ConceptRepresentation conceptRepresentation = new ConceptRepresentation();
        BasicComponentTextFormatType basicComponentTextFormatType = new BasicComponentTextFormatType();
        basicComponentTextFormatType.setTextType(textType);
        conceptRepresentation.setTextFormat(basicComponentTextFormatType);
        return conceptRepresentation;
    }

    private static List<ItemResourceInternal> buildResourcesInternalCodes(List<CodeType> codes) {
        List<ItemResourceInternal> resources = new ArrayList<ItemResourceInternal>();
        for (CodeType code : codes) {
            resources.add(buildResourceInternalFromCode(code));
        }
        return resources;
    }

    private static List<ItemResourceInternal> buildResourcesInternalConcepts(List<ConceptType> concepts) {
        List<ItemResourceInternal> resources = new ArrayList<ItemResourceInternal>();
        for (ConceptType concept : concepts) {
            resources.add(buildResourceInternalFromConcept(concept));
        }
        return resources;
    }

    private static ItemResourceInternal buildResourceInternalFromCode(CodeType code) {
        ItemResourceInternal resource = new ItemResourceInternal();
        ResourceLink link = new ResourceLink();
        link.setHref(code.getUri());
        resource.setSelfLink(link);
        resource.setId(code.getId());
        resource.setUrn(code.getUrn());
        resource.setUrnSiemac(code.getUrn());
        resource.setName(buildInternationalStringFromTextType(code.getNames()));
        resource.setManagementAppLink("http://" + code.getId());
        return resource;
    }

    private static ItemResourceInternal buildResourceInternalFromConcept(ConceptType concept) {
        ItemResourceInternal resource = new ItemResourceInternal();
        ResourceLink link = new ResourceLink();
        link.setHref(concept.getUri());
        resource.setSelfLink(link);
        resource.setId(concept.getId());
        resource.setUrn(concept.getUrn());
        resource.setUrnSiemac(concept.getUrn());
        resource.setName(buildInternationalStringFromTextType(concept.getNames()));
        resource.setManagementAppLink("http://" + concept.getId());
        return resource;
    }

    private static InternationalString buildInternationalStringFromTextType(List<TextType> texts) {
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
