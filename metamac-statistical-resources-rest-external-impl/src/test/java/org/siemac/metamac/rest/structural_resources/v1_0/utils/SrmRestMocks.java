package org.siemac.metamac.rest.structural_resources.v1_0.utils;

import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructureComponents;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionReferences;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.MeasureDimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Representation;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ShowDecimalPrecision;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ShowDecimalPrecisions;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.TextFormat;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.TimeDimension;

public class SrmRestMocks {

    public static DataStructure mockDataStructure(String agencyID, String resourceID, String version) {
        DataStructure dataStructure = new DataStructure();
        dataStructure.setUrn("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=" + agencyID + ":" + resourceID + "(" + version + ")");
        dataStructure.setUrnProvider(dataStructure.getUrn());
        dataStructure.setAgencyID(agencyID);
        dataStructure.setId(resourceID);
        dataStructure.setVersion(version);
        dataStructure.setAutoOpen(Boolean.TRUE);

        dataStructure.setStub(new DimensionReferences());
        dataStructure.getStub().getDimensions().add("GEO_DIM");
        dataStructure.getStub().getDimensions().add("TIME_PERIOD");
        dataStructure.setHeading(new DimensionReferences());
        dataStructure.getHeading().getDimensions().add("measure01");
        dataStructure.getHeading().getDimensions().add("dim01");

        dataStructure.setDataStructureComponents(new DataStructureComponents());
        dataStructure.getDataStructureComponents().setDimensions(new Dimensions());
        dataStructure.getDataStructureComponents().getDimensions().getDimensions().add(mockDimension("GEO_DIM", Boolean.TRUE));
        dataStructure.getDataStructureComponents().getDimensions().getDimensions().add(mockTimeDimension("TIME_PERIOD"));
        dataStructure.getDataStructureComponents().getDimensions().getDimensions().add(mockMeasureDimension("measure01"));
        dataStructure.getDataStructureComponents().getDimensions().getDimensions().add(mockDimension("dim01", Boolean.FALSE));

        dataStructure.setShowDecimals(Integer.valueOf(2));
        dataStructure.setShowDecimalsPrecisions(new ShowDecimalPrecisions());
        dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions().add(mockShowDecimalPrecision("agency01", "measure01-conceptScheme01", "01.000", "measure01-conceptScheme01-concept01", 4));
        dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions().add(mockShowDecimalPrecision("agency01", "measure01-conceptScheme01", "01.000", "measure01-conceptScheme01-concept05", 1));
        return dataStructure;
    }

    private static ShowDecimalPrecision mockShowDecimalPrecision(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID, int value) {
        ShowDecimalPrecision showDecimalPrecision = new ShowDecimalPrecision();
        showDecimalPrecision.setConcept(mockConceptResource(agencyID, maintainableParentID, maintainableVersionID, resourceID, null));
        showDecimalPrecision.setShowDecimals(value);
        return showDecimalPrecision;
    }

    public static Codes mockCodesByCodelist(String agencyID, String resourceID, String version) {
        if ("GEO_DIM-codelist01".equals(resourceID)) {
            return mockCodesByCodelistGeographicalDimension(agencyID, resourceID, version);
        } else {
            return mockCodesByCodelistOtherDimension(agencyID, resourceID, version);
        }
    }

    public static Codes mockCodesByCodelistGeographicalDimension(String agencyID, String resourceID, String version) {

        Codes codes = new Codes();
        {
            CodeResourceInternal parent = mockCodeResource(agencyID, resourceID, version, "santa-cruz-tenerife", null, 1, true);
            codes.getCodes().add(parent);
            {
                CodeResourceInternal child = mockCodeResource(agencyID, resourceID, version, "tenerife", parent.getUrn(), 1, false);
                codes.getCodes().add(child);
                {
                    CodeResourceInternal child2 = mockCodeResource(agencyID, resourceID, version, "la-laguna", child.getUrn(), 1, true);
                    codes.getCodes().add(child2);
                }
                {
                    CodeResourceInternal child2 = mockCodeResource(agencyID, resourceID, version, "santa-cruz", child.getUrn(), 2, true);
                    codes.getCodes().add(child2);
                }
            }
            {
                CodeResourceInternal child = mockCodeResource(agencyID, resourceID, version, "la-palma", parent.getUrn(), 2, true);
                codes.getCodes().add(child);
                {
                    CodeResourceInternal child2 = mockCodeResource(agencyID, resourceID, version, "los-llanos-de-aridane", child.getUrn(), 1, true);
                    codes.getCodes().add(child2);
                }
                {
                    CodeResourceInternal child2 = mockCodeResource(agencyID, resourceID, version, "santa-cruz-la-palma", child.getUrn(), 2, true);
                    codes.getCodes().add(child2);
                }
            }
            {
                CodeResourceInternal child = mockCodeResource(agencyID, resourceID, version, "la-gomera", parent.getUrn(), 3, true);
                codes.getCodes().add(child);
            }
            {
                CodeResourceInternal child = mockCodeResource(agencyID, resourceID, version, "el-hierro", parent.getUrn(), 4, true);
                codes.getCodes().add(child);
            }
        }
        {
            CodeResourceInternal parent = mockCodeResource(agencyID, resourceID, version, "las-palmas-gran-canaria", null, 1, false);
            codes.getCodes().add(parent);
            {
                CodeResourceInternal child = mockCodeResource(agencyID, resourceID, version, "gran-canaria", parent.getUrn(), 1, true);
                codes.getCodes().add(child);
            }
            {
                CodeResourceInternal child = mockCodeResource(agencyID, resourceID, version, "fuerteventura", parent.getUrn(), 2, true);
                codes.getCodes().add(child);
            }
            {
                CodeResourceInternal child = mockCodeResource(agencyID, resourceID, version, "lanzarote", parent.getUrn(), 3, true);
                codes.getCodes().add(child);
            }
        }
        return codes;
    }

    public static Codes mockCodesByCodelistOtherDimension(String agencyID, String resourceID, String version) {
        Codes codes = new Codes();
        {
            CodeResourceInternal parent = mockCodeResource(agencyID, resourceID, version, resourceID + "-code01", null, 1, true);
            codes.getCodes().add(parent);
        }
        {
            CodeResourceInternal parent = mockCodeResource(agencyID, resourceID, version, resourceID + "-code02", null, 2, false);
            codes.getCodes().add(parent);
            {
                CodeResourceInternal child = mockCodeResource(agencyID, resourceID, version, resourceID + "-code03", parent.getUrn(), 1, true);
                codes.getCodes().add(child);
            }
        }
        {
            CodeResourceInternal parent = mockCodeResource(agencyID, resourceID, version, resourceID + "-code04", null, 3, true);
            codes.getCodes().add(parent);
        }
        {
            CodeResourceInternal parent = mockCodeResource(agencyID, resourceID, version, resourceID + "-code05", null, 4, false);
            codes.getCodes().add(parent);
        }
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

    public static CodeResourceInternal mockCodeResource(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID, String parentUrn, Integer order, Boolean open) {
        CodeResourceInternal code = new CodeResourceInternal();
        code.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=" + agencyID + ":" + maintainableParentID + "(" + maintainableVersionID + ")." + resourceID);
        code.setId(resourceID);
        code.setName(mockInternationalString(resourceID));
        code.setParent(parentUrn);
        code.setKind("structuralResources#code");
        code.setSelfLink(mockResourceLink("http://apis.metamac.org/metamac-srm-web/apis/structural-resources-internal/v1.0/codelists/" + agencyID + "/" + maintainableParentID + "/"
                + maintainableVersionID + "/codes/" + resourceID));
        code.setOrder(Integer.valueOf(order));
        code.setOpen(open);
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
        concept.setName(mockInternationalString(resourceID));
        return concept;
    }

    private static Dimension mockDimension(String id, Boolean isSpatial) {
        Dimension dimension = new Dimension();
        dimension.setType(DimensionType.DIMENSION);
        dimension.setId(id);
        dimension.setIsSpatial(isSpatial);
        dimension.setLocalRepresentation(new Representation());
        dimension.getLocalRepresentation().setEnumerationCodelist(mockCodelistResource("agency01", id + "-codelist01", "01.000"));
        dimension.setConceptIdentity(mockConceptResource("agency01", "conceptScheme01", "01.000", id + "-concept01", null));
        return dimension;
    }

    private static MeasureDimension mockMeasureDimension(String id) {
        MeasureDimension dimension = new MeasureDimension();
        dimension.setType(DimensionType.MEASURE_DIMENSION);
        dimension.setId(id);
        dimension.setLocalRepresentation(new Representation());
        dimension.getLocalRepresentation().setEnumerationConceptScheme(mockConceptSchemeResource("agency01", id + "-conceptScheme01", "01.000"));
        dimension.setConceptIdentity(mockConceptResource("agency01", "conceptScheme01", "01.000", id + "-conceptMeasureDimension01", null));
        return dimension;
    }

    private static TimeDimension mockTimeDimension(String id) {
        TimeDimension dimension = new TimeDimension();
        dimension.setType(DimensionType.TIME_DIMENSION);
        dimension.setId(id);
        dimension.setLocalRepresentation(new Representation());
        dimension.getLocalRepresentation().setTextFormat(mockTimeTextFormatType());
        dimension.setConceptIdentity(mockConceptResource("agency01", "conceptScheme01", "01.000", id + "-conceptTimeDimension01", null));
        return dimension;
    }

    private static ResourceInternal mockCodelistResource(String agencyID, String resourceID, String version) {
        ResourceInternal codelist = new ResourceInternal();
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=" + agencyID + ":" + resourceID + "(" + version + ")");
        codelist.setId(resourceID);
        codelist.setName(mockInternationalString(resourceID));
        codelist.setKind("structuralResources#codelist");
        codelist.setSelfLink(mockResourceLink("http://apis.metamac.org/metamac-srm-web/apis/structural-resources-internal/v1.0/codelists/" + agencyID + "/" + resourceID + "/" + version));
        return codelist;
    }

    private static ResourceInternal mockConceptSchemeResource(String agencyID, String resourceID, String version) {
        ResourceInternal conceptScheme = new ResourceInternal();
        conceptScheme.setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=" + agencyID + ":" + resourceID + "(" + version + ")");
        conceptScheme.setId(resourceID);
        conceptScheme.setName(mockInternationalString(resourceID));
        conceptScheme.setKind("structuralResources#conceptScheme");
        conceptScheme.setSelfLink(mockResourceLink("http://apis.metamac.org/metamac-srm-web/apis/structural-resources-internal/v1.0/conceptschemes/" + agencyID + "/" + resourceID + "/" + version));
        return conceptScheme;
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

    private static TextFormat mockTimeTextFormatType() {
        TextFormat timeTextFormatType = new TextFormat();
        timeTextFormatType.setTextType(DataType.BASIC_TIME_PERIOD);
        timeTextFormatType.setEndTime("2013");
        return timeTextFormatType;
    }

    private static ResourceLink mockResourceLink(String link) {
        ResourceLink resourceLink = new ResourceLink();
        resourceLink.setHref(link);
        return resourceLink;
    }
}