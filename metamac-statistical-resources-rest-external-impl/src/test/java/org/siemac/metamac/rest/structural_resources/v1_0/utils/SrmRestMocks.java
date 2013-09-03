package org.siemac.metamac.rest.structural_resources.v1_0.utils;

import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_1_GLOBAL;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_2_GLOBAL;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_3_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_4_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_5_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_6_DIMENSION;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.ATTRIBUTE_7_DIMENSION;
import static org.siemac.metamac.rest.structural_resources.v1_0.utils.RestMocks.mockInternationalString;

import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeRelationship;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attributes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
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
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Empty;
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

        DataStructureComponents components = new DataStructureComponents();
        dataStructure.setDataStructureComponents(components);
        components.setDimensions(new Dimensions());
        components.getDimensions().getDimensions().add(mockDimension("GEO_DIM", Boolean.TRUE));
        components.getDimensions().getDimensions().add(mockTimeDimension("TIME_PERIOD"));
        components.getDimensions().getDimensions().add(mockMeasureDimension("measure01"));
        components.getDimensions().getDimensions().add(mockDimension("dim01", Boolean.FALSE));

        components.setAttributes(new Attributes());
        components.getAttributes().getAttributes().add(mockAttributeDataset(ATTRIBUTE_1_GLOBAL, false));
        components.getAttributes().getAttributes().add(mockAttributeDataset(ATTRIBUTE_2_GLOBAL, false));
        components.getAttributes().getAttributes().add(mockAttributeDimension(ATTRIBUTE_3_DIMENSION, Arrays.asList("GEO_DIM"), false));
        components.getAttributes().getAttributes().add(mockAttributeDimension(ATTRIBUTE_4_DIMENSION, Arrays.asList("GEO_DIM", "dim01"), false));
        components.getAttributes().getAttributes().add(mockAttributeDimension(ATTRIBUTE_5_DIMENSION, Arrays.asList("GEO_DIM", "TIME_PERIOD"), false));
        components.getAttributes().getAttributes().add(mockAttributeDimension(ATTRIBUTE_6_DIMENSION, Arrays.asList("GEO_DIM", "TIME_PERIOD", "measure01"), false));
        components.getAttributes().getAttributes().add(mockAttributeDimension(ATTRIBUTE_7_DIMENSION, Arrays.asList("GEO_DIM", "dim01", "measure01", "TIME_PERIOD"), false));
        // components.getAttributes().getAttributes().add(mockAttributePrimaryMeasure("attribute1", false)); // TODO primary measure
        // components.getAttributes().getAttributes().add(mockAttributePrimaryMeasure("attribute3", true));
        // TODO groups

        dataStructure.setShowDecimals(Integer.valueOf(2));
        dataStructure.setShowDecimalsPrecisions(new ShowDecimalPrecisions());
        dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions().add(mockShowDecimalPrecision("agency01", "measure01-conceptScheme01", "01.000", "measure01-conceptScheme01-concept01", 4));
        dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions().add(mockShowDecimalPrecision("agency01", "measure01-conceptScheme01", "01.000", "measure01-conceptScheme01-concept05", 1));
        return dataStructure;
    }

    public static Codelist mockCodelist(String agencyID, String resourceID, String version) {
        Codelist codelist = new Codelist();
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=" + agencyID + ":" + resourceID + "(" + version + ")");
        codelist.setUrnProvider(codelist.getUrn());
        codelist.setAgencyID(agencyID);
        codelist.setId(resourceID);
        codelist.setVersion(version);
        if (resourceID.contains("GEO_DIM")) {
            codelist.setVariable(mockVariableResource("variable-" + resourceID));
        }
        return codelist;
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
            CodeResourceInternal parent = mockCodeResourceGeographical(agencyID, resourceID, version, "santa-cruz-tenerife", null, 1, true);
            codes.getCodes().add(parent);
            {
                CodeResourceInternal child = mockCodeResourceGeographical(agencyID, resourceID, version, "tenerife", parent.getUrn(), 1, false);
                codes.getCodes().add(child);
                {
                    CodeResourceInternal child2 = mockCodeResourceGeographical(agencyID, resourceID, version, "la-laguna", child.getUrn(), 1, true);
                    codes.getCodes().add(child2);
                }
                {
                    CodeResourceInternal child2 = mockCodeResourceGeographical(agencyID, resourceID, version, "santa-cruz", child.getUrn(), 2, true);
                    codes.getCodes().add(child2);
                }
            }
            {
                CodeResourceInternal child = mockCodeResourceGeographical(agencyID, resourceID, version, "la-palma", parent.getUrn(), 2, true);
                codes.getCodes().add(child);
                {
                    CodeResourceInternal child2 = mockCodeResourceGeographical(agencyID, resourceID, version, "los-llanos-de-aridane", child.getUrn(), 1, true);
                    codes.getCodes().add(child2);
                }
                {
                    CodeResourceInternal child2 = mockCodeResourceGeographical(agencyID, resourceID, version, "santa-cruz-la-palma", child.getUrn(), 2, true);
                    codes.getCodes().add(child2);
                }
            }
            {
                CodeResourceInternal child = mockCodeResourceGeographical(agencyID, resourceID, version, "la-gomera", parent.getUrn(), 3, true);
                codes.getCodes().add(child);
            }
            {
                CodeResourceInternal child = mockCodeResourceGeographical(agencyID, resourceID, version, "el-hierro", parent.getUrn(), 4, true);
                codes.getCodes().add(child);
            }
        }
        {
            CodeResourceInternal parent = mockCodeResourceGeographical(agencyID, resourceID, version, "las-palmas-gran-canaria", null, 1, false);
            codes.getCodes().add(parent);
            {
                CodeResourceInternal child = mockCodeResourceGeographical(agencyID, resourceID, version, "gran-canaria", parent.getUrn(), 1, true);
                codes.getCodes().add(child);
            }
            {
                CodeResourceInternal child = mockCodeResourceGeographical(agencyID, resourceID, version, "fuerteventura", parent.getUrn(), 2, true);
                codes.getCodes().add(child);
            }
            {
                CodeResourceInternal child = mockCodeResourceGeographical(agencyID, resourceID, version, "lanzarote", parent.getUrn(), 3, true);
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

    public static CodeResourceInternal mockCodeResourceGeographical(String agencyID, String maintainableParentID, String maintainableVersionID, String resourceID, String parentUrn, Integer order,
            Boolean open) {
        CodeResourceInternal code = mockCodeResource(agencyID, maintainableParentID, maintainableVersionID, resourceID, parentUrn, order, open);
        code.setVariableElement(mockVariableElementResource("variableElement-" + resourceID));
        return code;
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

    private static AttributeBase mockAttributeDataset(String id, boolean enumerated) {
        Attribute attribute = new Attribute();
        mockAttributeBase(id, attribute, enumerated);
        attribute.setAttributeRelationship(new AttributeRelationship());
        attribute.getAttributeRelationship().setNone(new Empty());
        return attribute;
    }

    private static AttributeBase mockAttributeDimension(String id, List<String> dimensions, boolean enumerated) {
        Attribute attribute = new Attribute();
        mockAttributeBase(id, attribute, enumerated);
        attribute.setAttributeRelationship(new AttributeRelationship());
        attribute.getAttributeRelationship().getDimensions().addAll(dimensions);
        return attribute;
    }

    // TODO mockAttributePrimaryMeasure
    // private static AttributeBase mockAttributePrimaryMeasure(String id, boolean enumerated) {
    // Attribute attribute = new Attribute();
    // mockAttributeBase(id, attribute, enumerated);
    // attribute.setAttributeRelationship(new AttributeRelationship());
    // attribute.getAttributeRelationship().setPrimaryMeasure("OBS_VALUE");
    // return attribute;
    // }

    private static void mockAttributeBase(String id, AttributeBase attribute, boolean enumerated) {
        attribute.setId(id);
        attribute.setConceptIdentity(mockConceptResource("agency01", "conceptScheme01", "01.000", id + "-concept01", null));
        attribute.setLocalRepresentation(new Representation());
        if (enumerated) {
            attribute.getLocalRepresentation().setEnumerationCodelist(mockCodelistResource("agency01", id + "-codelist01", "01.000"));
        } else {
            attribute.getLocalRepresentation().setTextFormat(mockTimeTextFormatType());
        }
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

    private static ResourceInternal mockVariableResource(String resourceID) {
        ResourceInternal variable = new ResourceInternal();
        variable.setUrn("urn:siemac:org.siemac.metamac.infomodel.structuralresources.Variable=" + resourceID);
        variable.setId(resourceID);
        variable.setName(mockInternationalString(resourceID));
        variable.setKind("structuralResources#variable");
        variable.setSelfLink(mockResourceLink("http://apis.metamac.org/metamac-srm-web/apis/structural-resources-internal/v1.0/variables/" + resourceID));
        return variable;
    }

    private static ResourceInternal mockVariableElementResource(String resourceID) {
        ResourceInternal variableElement = new ResourceInternal();
        variableElement.setUrn("urn:siemac:org.siemac.metamac.infomodel.structuralresources.Variable=variable01." + resourceID);
        variableElement.setId(resourceID);
        variableElement.setName(mockInternationalString(resourceID));
        variableElement.setKind("structuralResources#variableElement");
        variableElement.setSelfLink(mockResourceLink("http://apis.metamac.org/metamac-srm-web/apis/structural-resources-internal/v1.0/variables/variable01/variableelements/" + resourceID));
        return variableElement;
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