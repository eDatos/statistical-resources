package org.siemac.metamac.statistical.resources.core.utils;

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
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Representation;

public class SrmMockUtils {

    private static final String DEFAULT_LOCALE = "es";

    // -------------------------------------------------------------------------------------
    // COMMON
    // -------------------------------------------------------------------------------------

    private static ResourceLink buildResourceLink(TypeExternalArtefactsEnum type) {
        ResourceLink resourceLink = new ResourceLink();
        resourceLink.setHref(UUID.randomUUID().toString());
        resourceLink.setKind(type.getName());
        return resourceLink;
    }

    public static InternationalString buildInternationalStringResource(String label, String lang) {
        InternationalString internationalString = new InternationalString();
        internationalString.getTexts().add(buildLocalisedStringResource(label, lang));
        return internationalString;
    }

    public static LocalisedString buildLocalisedStringResource(String label, String lang) {
        LocalisedString text = new LocalisedString();
        text.setLang(lang);
        text.setValue(label);
        return text;
    }

    public static TextType buildTextType(String label, String lang) {
        TextType text = new TextType();
        text.setLang(lang);
        text.setValue(label);
        return text;
    }

    // -------------------------------------------------------------------------------------
    // CONCEPT SCHEME
    // -------------------------------------------------------------------------------------

    public static ConceptScheme buildConceptScheme(String id, String name, String lang, String urn) {
        ConceptScheme scheme = new ConceptScheme();
        scheme.setId(id);
        scheme.setUrn(urn);
        scheme.setName(buildInternationalStringResource(name, lang));
        return scheme;
    }

    public static ConceptSchemeReferenceType buildConceptSchemeRef(String conceptSchemeUrn) {
        ConceptSchemeReferenceType ref = new ConceptSchemeReferenceType();
        ref.setRef(buildConceptSchemeRefType(conceptSchemeUrn));
        ref.setURN(conceptSchemeUrn);
        return ref;
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

    protected static ConceptSchemeRefType buildConceptSchemeRefType(String codelistUrn) {
        ConceptSchemeRefType conceptSchemeRefType = new ConceptSchemeRefType();
        String[] params = UrnUtils.splitUrnItemScheme(codelistUrn);
        conceptSchemeRefType.setAgencyID(params[0]);
        conceptSchemeRefType.setId(params[1]);
        conceptSchemeRefType.setVersion(params[2]);
        return conceptSchemeRefType;
    }

    // -------------------------------------------------------------------------------------
    // CONCEPTS
    // -------------------------------------------------------------------------------------

    public static Concepts buildConcepts(int numConcepts) {
        Concepts concepts = new Concepts();
        for (int i = 1; i <= numConcepts; i++) {
            concepts.getConcepts().add(buildConcept("concept-0" + i, "Concept 0" + i));
        }
        return concepts;
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

    public static Concept buildConcept(String id, String urn, String name, Representation conceptRepresentation) {
        Concept concept = new Concept();
        concept.setId(id);
        concept.setUrn(urn);
        concept.setUri(UUID.randomUUID().toString());
        concept.setName(buildInternationalStringResource(name, DEFAULT_LOCALE));
        concept.setCoreRepresentation(conceptRepresentation);
        return concept;
    }

    public static ItemResourceInternal buildConcept(String id, String name) {
        ItemResourceInternal concept = new ItemResourceInternal();
        concept.setId(id);
        concept.setUrn("urn:uuid" + id);
        concept.setUrnProvider("urn:uuid:provider:" + id);
        concept.setSelfLink(buildResourceLink(TypeExternalArtefactsEnum.CONCEPT));
        concept.setManagementAppLink("http://srm/concepts/" + id);
        concept.setName(buildInternationalStringResource(name, DEFAULT_LOCALE));
        concept.setKind(TypeExternalArtefactsEnum.CONCEPT.getValue());
        return concept;
    }

    public static Representation buildConceptRepresentation(String codelistUrn) {
        Representation conceptRepresentation = new Representation();
        conceptRepresentation.setEnumerationCodelist(codelistUrn);
        return conceptRepresentation;
    }

    public static Representation buildConceptRepresentation(BasicComponentDataType textType) {
        Representation conceptRepresentation = new Representation();
        BasicComponentTextFormatType basicComponentTextFormatType = new BasicComponentTextFormatType();
        basicComponentTextFormatType.setTextType(textType);
        conceptRepresentation.setTextFormat(basicComponentTextFormatType);
        return conceptRepresentation;
    }

    // -------------------------------------------------------------------------------------
    // CODELIST
    // -------------------------------------------------------------------------------------

    public static Codelist buildCodelist(String id, String name, String lang, String urn) {
        Codelist scheme = new Codelist();
        scheme.setId(id);
        scheme.setUrn(urn);
        scheme.setName(buildInternationalStringResource(name, lang));
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

    // -------------------------------------------------------------------------------------
    // CODES
    // -------------------------------------------------------------------------------------

    public static Codes buildCodes(int numCodes) {
        Codes codes = new Codes();
        for (int i = 1; i <= numCodes; i++) {
            codes.getCodes().add(buildCode("code-0" + i, "Code 0" + i, DEFAULT_LOCALE));
        }
        return codes;
    }

    public static CodeResourceInternal buildCode(String id, String name, String lang) {
        CodeResourceInternal code = new CodeResourceInternal();
        code.setId(id);
        code.setUrn("urn:uuid:" + id);
        code.setUrnProvider("urn:uuid:provider:" + id);
        code.setSelfLink(buildResourceLink(TypeExternalArtefactsEnum.CODE));
        code.setName(buildInternationalStringResource(name, lang));
        code.setKind(TypeExternalArtefactsEnum.CODE.getValue());
        return code;
    }

    // -------------------------------------------------------------------------------------
    // DSD
    // -------------------------------------------------------------------------------------

    public static DataStructure mockDsdWithGeoTimeAndMeasureDimensions(String urn, String geoId, String timeId, String measureId, ConceptSchemeReferenceType measureConceptSchemeReference,
            CodelistReferenceType geoCodelistReference) {
        MeasureDimensionType measureDim = SrmMockUtils.buildMeasureDimension(measureId, measureConceptSchemeReference);
        TimeDimensionType timeDim = SrmMockUtils.buildTimeDimension(timeId, TimeDataType.REPORTING_YEAR);
        Dimension geoDim = SrmMockUtils.buildGeoDimension(geoId, geoCodelistReference);

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

    public static MeasureDimensionType buildMeasureDimension(String id, ConceptSchemeReferenceType conceptSchemeRepresentationReference) {
        MeasureDimensionType measureDim = new MeasureDimensionType();
        measureDim.setId(id);
        measureDim.setType(DimensionTypeType.MEASURE_DIMENSION);

        MeasureDimensionRepresentationType representationType = new MeasureDimensionRepresentationType();
        representationType.setEnumeration(conceptSchemeRepresentationReference);

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

    public static Dimension buildGeoDimension(String id, CodelistReferenceType codelistReference) {
        Dimension dim = new Dimension();
        dim.setId(id);
        dim.setIsSpatial(true);
        dim.setType(DimensionTypeType.DIMENSION);

        SimpleDataStructureRepresentationType representation = new SimpleDataStructureRepresentationType();
        representation.setEnumeration(codelistReference);

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

}
