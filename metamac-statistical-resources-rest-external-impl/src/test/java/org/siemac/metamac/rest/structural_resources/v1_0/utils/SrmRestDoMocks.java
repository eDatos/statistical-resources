package org.siemac.metamac.rest.structural_resources.v1_0.utils;

import java.math.BigInteger;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.DimensionTypeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalCodeRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalCodeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalConceptRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TimeDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CodeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ConceptType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleDataStructureRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ShowDecimalPrecision;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ShowDecimalPrecisions;

public class SrmRestDoMocks {

    public static DataStructure mockDataStructure(String agencyID, String resourceID, String version) {
        DataStructure dataStructure = new DataStructure();
        dataStructure.setUrn("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=" + agencyID + ":" + resourceID + "(" + version + ")");
        dataStructure.setUrnInternal(dataStructure.getUrn());
        dataStructure.setAgencyID(agencyID);
        dataStructure.setId(resourceID);
        dataStructure.setVersion(version);
        dataStructure.setAutoOpen(Boolean.TRUE);

        dataStructure.setDataStructureComponents(new DataStructureComponentsType());
        dataStructure.getDataStructureComponents().setDimensionList(new DimensionListType());
        dataStructure.getDataStructureComponents().getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions().add(mockDimension("GEO_DIM", Boolean.TRUE));
        dataStructure.getDataStructureComponents().getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions().add(mockTimeDimension("TIME_PERIOD"));
        dataStructure.getDataStructureComponents().getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions().add(mockMeasureDimension("measure01"));
        dataStructure.getDataStructureComponents().getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions().add(mockDimension("dim01", Boolean.FALSE));

        dataStructure.setShowDecimals(BigInteger.valueOf(2));
        dataStructure.setShowDecimalsPrecisions(new ShowDecimalPrecisions());
        dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions().add(mockShowDecimalPrecision("agency01", "conceptScheme01", "01.000", "measure01-conceptScheme01-concept01", 4));
        dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions().add(mockShowDecimalPrecision("agency01", "conceptScheme01", "01.000", "measure01-conceptScheme01-concept05", 1));
        return dataStructure;
    }

    private static ShowDecimalPrecision mockShowDecimalPrecision(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID, int value) {
        ShowDecimalPrecision showDecimalPrecision = new ShowDecimalPrecision();
        showDecimalPrecision.setConcept(mockConceptReferenceType(agencyID, maintainableParentID, maintainableVersionID, resourceID));
        showDecimalPrecision.setShowDecimals(BigInteger.valueOf(value));
        return showDecimalPrecision;
    }

    public static Codelist mockCodelist(String agencyID, String resourceID, String version) {
        Codelist codelist = new Codelist();
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=" + agencyID + ":" + resourceID + "(" + version + ")");
        codelist.setUrnInternal(codelist.getUrn());
        codelist.setAgencyID(agencyID);
        codelist.setId(resourceID);
        codelist.setVersion(version);
        codelist.getCodes().add(mockCodeType(agencyID, resourceID, version, resourceID + "-code01", null));
        codelist.getCodes().add(mockCodeType(agencyID, resourceID, version, resourceID + "-code02", null));
        codelist.getCodes().add(mockCodeType(agencyID, resourceID, version, resourceID + "-code03", codelist.getCodes().get(1).getId()));
        codelist.getCodes().add(mockCodeType(agencyID, resourceID, version, resourceID + "-code04", null));
        codelist.getCodes().add(mockCodeType(agencyID, resourceID, version, resourceID + "-code05", null));
        return codelist;
    }

    public static ConceptScheme mockConceptScheme(String agencyID, String resourceID, String version) {
        ConceptScheme conceptScheme = new ConceptScheme();
        conceptScheme.setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=" + agencyID + ":" + resourceID + "(" + version + ")");
        conceptScheme.setUrnInternal(conceptScheme.getUrn());
        conceptScheme.setAgencyID(agencyID);
        conceptScheme.setId(resourceID);
        conceptScheme.setVersion(version);
        conceptScheme.getConcepts().add(mockConceptType(agencyID, resourceID, version, resourceID + "-concept01", null));
        conceptScheme.getConcepts().add(mockConceptType(agencyID, resourceID, version, resourceID + "-concept02", conceptScheme.getConcepts().get(0).getId()));
        conceptScheme.getConcepts().add(mockConceptType(agencyID, resourceID, version, resourceID + "-concept03", null));
        conceptScheme.getConcepts().add(mockConceptType(agencyID, resourceID, version, resourceID + "-concept04", null));
        conceptScheme.getConcepts().add(mockConceptType(agencyID, resourceID, version, resourceID + "-concept05", null));
        return conceptScheme;
    }

    public static CodeType mockCodeType(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID, String parentID) {
        CodeType codeType = new CodeType();
        codeType.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=" + agencyID + ":" + maintainableParentID + "(" + maintainableVersionID + ")." + resourceID);
        codeType.setId(resourceID);
        codeType.getNames().add(mockTextType("es", resourceID + " en Español"));
        codeType.getNames().add(mockTextType("en", resourceID + " in English"));
        if (parentID != null) {
            codeType.setParent(mockLocalCodeReferenceType(parentID));
        }
        return codeType;
    }
    public static ConceptType mockConceptType(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID, String parentID) {
        ConceptType conceptType = new ConceptType();
        conceptType.setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=" + agencyID + ":" + maintainableParentID + "(" + maintainableVersionID + ")." + resourceID);
        conceptType.setId(resourceID);
        conceptType.getNames().add(mockTextType("es", resourceID + " en Español"));
        conceptType.getNames().add(mockTextType("en", resourceID + " in English"));
        if (parentID != null) {
            conceptType.setParent(mockLocalConceptReferenceType(parentID));
        }
        return conceptType;
    }

    public static Concept mockConcept(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID) {
        Concept concept = new Concept();
        concept.setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=" + agencyID + ":" + maintainableParentID + "(" + maintainableVersionID + ")." + resourceID);
        concept.setUrnInternal(concept.getUrn());
        concept.setId(resourceID);
        concept.getNames().add(mockTextType("es", resourceID + " en Español"));
        concept.getNames().add(mockTextType("en", resourceID + " in English"));
        return concept;
    }

    private static Dimension mockDimension(String id, Boolean isSpatial) {
        Dimension dimension = new Dimension();
        dimension.setType(DimensionTypeType.DIMENSION);
        dimension.setId(id);
        dimension.setIsSpatial(isSpatial);
        dimension.setLocalRepresentation(new SimpleDataStructureRepresentationType());
        dimension.getLocalRepresentation().setEnumeration(mockCodelistReferenceType("agency01", id + "-codelist01", "01.000"));
        dimension.setConceptIdentity(mockConceptReferenceType("agency01", "conceptScheme01", "01.000", id + "-concept01"));
        return dimension;
    }

    private static MeasureDimensionType mockMeasureDimension(String id) {
        MeasureDimensionType dimension = new MeasureDimensionType();
        dimension.setType(DimensionTypeType.MEASURE_DIMENSION);
        dimension.setId(id);
        dimension.setLocalRepresentation(new MeasureDimensionRepresentationType());
        dimension.getLocalRepresentation().setEnumeration(mockConceptSchemeReferenceType("agency01", id + "-conceptScheme01", "01.000"));
        dimension.setConceptIdentity(mockConceptReferenceType("agency01", "conceptScheme01", "01.000", id + "-conceptMeasureDimension01"));
        return dimension;
    }

    private static TimeDimensionType mockTimeDimension(String id) {
        TimeDimensionType dimension = new TimeDimensionType();
        dimension.setType(DimensionTypeType.TIME_DIMENSION);
        dimension.setId(id);
        dimension.setLocalRepresentation(new TimeDimensionRepresentationType());
        dimension.getLocalRepresentation().setTextFormat(mockTimeTextFormatType());
        dimension.setConceptIdentity(mockConceptReferenceType("agency01", "conceptScheme01", "01.000", id + "-conceptTimeDimension01"));
        return dimension;
    }

    private static CodelistReferenceType mockCodelistReferenceType(String agencyID, String id, String version) {
        CodelistReferenceType reference = new CodelistReferenceType();
        reference.setRef(new CodelistRefType());
        reference.getRef().setAgencyID(agencyID);
        reference.getRef().setId(id);
        reference.getRef().setVersion(version);
        return reference;
    }

    private static ConceptSchemeReferenceType mockConceptSchemeReferenceType(String agencyID, String id, String version) {
        ConceptSchemeReferenceType reference = new ConceptSchemeReferenceType();
        reference.setRef(new ConceptSchemeRefType());
        reference.getRef().setAgencyID(agencyID);
        reference.getRef().setId(id);
        reference.getRef().setVersion(version);
        return reference;
    }

    private static ConceptReferenceType mockConceptReferenceType(String agencyID, String maintainableParentID, String maintainableVersionID, String id) {
        ConceptReferenceType reference = new ConceptReferenceType();
        reference.setRef(new ConceptRefType());
        reference.getRef().setAgencyID(agencyID);
        reference.getRef().setId(id);
        reference.getRef().setMaintainableParentID(maintainableParentID);
        reference.getRef().setMaintainableParentVersion(maintainableVersionID);
        return reference;
    }

    private static LocalCodeReferenceType mockLocalCodeReferenceType(String id) {
        LocalCodeReferenceType reference = new LocalCodeReferenceType();
        reference.setRef(new LocalCodeRefType());
        reference.getRef().setId(id);
        return reference;
    }

    private static LocalConceptReferenceType mockLocalConceptReferenceType(String id) {
        LocalConceptReferenceType reference = new LocalConceptReferenceType();
        reference.setRef(new LocalConceptRefType());
        reference.getRef().setId(id);
        return reference;
    }

    private static TextType mockTextType(String lang, String value) {
        TextType textType = new TextType();
        textType.setLang(lang);
        textType.setValue(value);
        return textType;
    }

    private static TimeTextFormatType mockTimeTextFormatType() {
        TimeTextFormatType timeTextFormatType = new TimeTextFormatType();
        timeTextFormatType.setTextType(TimeDataType.BASIC_TIME_PERIOD);
        timeTextFormatType.getEndTimes().add("2013");
        return timeTextFormatType;
    }

}