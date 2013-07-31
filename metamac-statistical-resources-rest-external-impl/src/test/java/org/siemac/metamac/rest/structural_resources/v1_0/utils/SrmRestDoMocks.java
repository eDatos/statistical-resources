package org.siemac.metamac.rest.structural_resources.v1_0.utils;

import java.math.BigInteger;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.DimensionTypeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TimeDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleDataStructureRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResource;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ShowDecimalPrecision;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ShowDecimalPrecisions;

public class SrmRestDoMocks {

    public static DataStructure mockDataStructure(String agencyID, String resourceID, String version) {
        DataStructure dataStructure = new DataStructure();
        dataStructure.setUrn("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=" + agencyID + ":" + resourceID + "(" + version + ")");
        dataStructure.setUrnProvider(dataStructure.getUrn());
        dataStructure.setAgencyID(agencyID);
        dataStructure.setId(resourceID);
        dataStructure.setVersion(version);
        dataStructure.setAutoOpen(Boolean.TRUE);

        dataStructure.setStub(new Dimensions());
        dataStructure.getStub().getDimensions().add(mockDimensionReference("GEO_DIM"));
        dataStructure.getStub().getDimensions().add(mockDimensionReference("TIME_PERIOD"));
        dataStructure.setHeading(new Dimensions());
        dataStructure.getHeading().getDimensions().add(mockDimensionReference("measure01"));
        dataStructure.getHeading().getDimensions().add(mockDimensionReference("dim01"));

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

    private static LocalDimensionReferenceType mockDimensionReference(String dimensionID) {
        LocalDimensionReferenceType dimension = new LocalDimensionReferenceType();
        dimension.setRef(new LocalDimensionRefType());
        dimension.getRef().setId(dimensionID);
        return dimension;
    }

    private static ShowDecimalPrecision mockShowDecimalPrecision(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID, int value) {
        ShowDecimalPrecision showDecimalPrecision = new ShowDecimalPrecision();
        showDecimalPrecision.setConcept(mockConceptReferenceType(agencyID, maintainableParentID, maintainableVersionID, resourceID));
        showDecimalPrecision.setShowDecimals(BigInteger.valueOf(value));
        return showDecimalPrecision;
    }

    public static Codes mockCodesByCodelist(String agencyID, String resourceID, String version) {
        Codes codes = new Codes();
        codes.getCodes().add(mockCodeResource(agencyID, resourceID, version, resourceID + "-code01", null));
        codes.getCodes().add(mockCodeResource(agencyID, resourceID, version, resourceID + "-code02", null));
        codes.getCodes().add(mockCodeResource(agencyID, resourceID, version, resourceID + "-code03", codes.getCodes().get(1).getUrn()));
        codes.getCodes().add(mockCodeResource(agencyID, resourceID, version, resourceID + "-code04", null));
        codes.getCodes().add(mockCodeResource(agencyID, resourceID, version, resourceID + "-code05", null));
        return codes;
    }

    public static Concepts mockConceptsByConceptScheme(String agencyID, String resourceID, String version) {
        Concepts concepts = new Concepts();
        concepts.getConcepts().add(mockConceptResource(agencyID, resourceID, version, resourceID + "-concept01", null));
        concepts.getConcepts().add(mockConceptResource(agencyID, resourceID, version, resourceID + "-concept02", concepts.getConcepts().get(0).getUrn()));
        concepts.getConcepts().add(mockConceptResource(agencyID, resourceID, version, resourceID + "-concept03", null));
        concepts.getConcepts().add(mockConceptResource(agencyID, resourceID, version, resourceID + "-concept04", null));
        concepts.getConcepts().add(mockConceptResource(agencyID, resourceID, version, resourceID + "-concept05", null));
        return concepts;
    }

    public static CodeResource mockCodeResource(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID, String parentUrn) {
        CodeResource code = new CodeResource();
        code.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=" + agencyID + ":" + maintainableParentID + "(" + maintainableVersionID + ")." + resourceID);
        code.setId(resourceID);
        code.setName(mockInternationalString(resourceID));
        code.setParent(parentUrn);
        code.setKind("structuralResources#code");
        code.setSelfLink(mockResourceLink("http://apis.metamac.org/metamac-srm-web/apis/structural-resources-internal/v1.0/codelists/" + agencyID + "/" + maintainableParentID + "/"
                + maintainableVersionID + "/codes/" + resourceID));
        return code;
    }

    public static ItemResourceInternal mockConceptResource(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID, String parentUrn) {
        ItemResourceInternal concept = new ItemResourceInternal();
        concept.setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=" + agencyID + ":" + maintainableParentID + "(" + maintainableVersionID + ")." + resourceID);
        concept.setId(resourceID);
        concept.setName(mockInternationalString(resourceID));
        concept.setParent(parentUrn);
        concept.setKind("structuralResources#concept");
        concept.setSelfLink(mockResourceLink("http://apis.metamac.org/metamac-srm-web/apis/structural-resources-internal/v1.0/conceptschemes/" + agencyID + "/" + maintainableParentID + "/"
                + maintainableVersionID + "/concepts/" + resourceID));
        return concept;
    }

    public static Concept mockConcept(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID) {
        Concept concept = new Concept();
        concept.setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=" + agencyID + ":" + maintainableParentID + "(" + maintainableVersionID + ")." + resourceID);
        concept.setUrnProvider(concept.getUrn());
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

    private static InternationalString mockInternationalString(String resourceID) {
        InternationalString internationalString = new InternationalString();
        internationalString.getTexts().add(mockLocalisedString("es", resourceID + " en Español"));
        internationalString.getTexts().add(mockLocalisedString("en", resourceID + " in English"));
        return internationalString;
    }

    private static LocalisedString mockLocalisedString(String lang, String value) {
        LocalisedString localisedString = new LocalisedString();
        localisedString.setLang(lang);
        localisedString.setValue(value);
        return localisedString;
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

    private static ResourceLink mockResourceLink(String link) {
        ResourceLink resourceLink = new ResourceLink();
        resourceLink.setHref(link);
        return resourceLink;
    }
}