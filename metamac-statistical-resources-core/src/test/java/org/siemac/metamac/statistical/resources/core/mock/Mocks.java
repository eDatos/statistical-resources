package org.siemac.metamac.statistical.resources.core.mock;

import java.util.Arrays;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeUsageStatusType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attributes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructureComponents;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Groups;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Measure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Representation;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.TextFormat;
import org.siemac.metamac.statistical.resources.core.utils.SrmMockUtils;

public class Mocks {

    // --------------------------------------------------------------------
    // LIST OF CODES
    // --------------------------------------------------------------------

    public static Codes mock_CL_DECIMALS() {
        Codes codes = new Codes();

        codes.getCodes().add(SrmMockUtils.buildCode("0", "Zero", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("1", "One", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("2", "Two", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("3", "Three", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("4", "Four", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("5", "Five", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("6", "Six", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("7", "Seven", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("8", "Eight", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("9", "Nine", "en"));

        return codes;
    }

    public static Codes mock_CL_FREQ() {
        Codes codes = new Codes();

        codes.getCodes().add(SrmMockUtils.buildCode("A", "Annual", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("B", "Daily - business week", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("D", "Daily", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("M", "Monthly", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("N", "Minutely", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("Q", "Quarterly", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("S", "Half Yearly, semester", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("W", "Weekly", "en"));

        return codes;
    }

    public static Codes mock_CL_CONF_STATUS() {
        Codes codes = new Codes();

        codes.getCodes().add(SrmMockUtils.buildCode("C", "Confidential statistical information", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("D", "Secondary confidentiality set by the sender, not for   publication", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("F", "Free", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("N", "Not for publication, restricted for internal use only", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("S", "Secondary confidentiality set and managed by the receiver, not for publication", "en"));

        return codes;
    }

    public static Codes mock_CL_OBS_STATUS() {
        Codes codes = new Codes();

        codes.getCodes().add(SrmMockUtils.buildCode("A", "Normal", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("B", "Break", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("E", "Estimated value", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("F", "Forecast value", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("I", "Imputed value (CCSA definition)", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("M", "Missing value", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("P", "Provisional value", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("S", "Strike", "en"));

        return codes;
    }

    public static Codes mock_CL_UNIT_MULT() {
        Codes codes = new Codes();

        codes.getCodes().add(SrmMockUtils.buildCode("0", "Units", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("1", "Tens", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("2", "Hundreds", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("3", "Thousands", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("4", "Tens of thousands", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("6", "Millions", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("9", "Billions", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("12", "Trillions", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("15", "Quadrillions", "en"));

        return codes;
    }

    public static Codes mock_CL_EXR_TYPE() {
        Codes codes = new Codes();

        codes.getCodes().add(SrmMockUtils.buildCode("CR00", "Official fixing", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("RR00", "Reference rate", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("SP00", "Spot", "en"));

        return codes;
    }

    public static Codes mock_CL_EXR_VAR() {
        Codes codes = new Codes();

        codes.getCodes().add(SrmMockUtils.buildCode("E", "End-of-period", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("P", "Growth rate over previous period", "en"));

        return codes;
    }

    public static Codes mock_CL_CURRENCY() {
        Codes codes = new Codes();

        codes.getCodes().add(SrmMockUtils.buildCode("CHF", "Swiss franc", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("EUR", "Euro", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("GBP", "Pound sterling", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("JPY", "Japanese yen", "en"));
        codes.getCodes().add(SrmMockUtils.buildCode("USD", "United States dollar", "en"));

        return codes;
    }

    // --------------------------------------------------------------------
    // LIST OF CONCEPTS
    // --------------------------------------------------------------------

    public static Concepts mock_CROSS_DOMAIN_CONCEPTS() {
        Concepts concepts = new Concepts();

        concepts.getConcepts().add(mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_COLL_METHOD());
        concepts.getConcepts().add(SrmMockUtils.buildConcept("CONF", "Confidentiality"));
        concepts.getConcepts().add(mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_CONF_STATUS());
        concepts.getConcepts().add(SrmMockUtils.buildConcept("CURRENCY", "Currency"));
        concepts.getConcepts().add(mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_DECIMALS());
        concepts.getConcepts().add(mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_FREQ());
        concepts.getConcepts().add(SrmMockUtils.buildConcept("OBS_VALUE", "Observation value"));
        concepts.getConcepts().add(mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_OBS_STATUS());
        concepts.getConcepts().add(SrmMockUtils.buildConcept("TIME_PERIOD", "Time Period"));
        concepts.getConcepts().add(mock_resource_SDMX_CROSS_DOMAIN_1_0_TITLE());
        concepts.getConcepts().add(mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_UNIT_MULT());
        concepts.getConcepts().add(SrmMockUtils.buildConcept("UNIT_MEASURE", "Unit of Measure"));

        return concepts;
    }

    public static Concepts mock_ECB_CONCEPTS() {
        Concepts concepts = new Concepts();

        concepts.getConcepts().add(mock_resource_ECB_CONCEPTS_1_0_CURRENCY_DENOM());
        concepts.getConcepts().add(mock_resource_ECB_CONCEPTS_1_0_EXR_VAR());
        concepts.getConcepts().add(mock_resource_ECB_CONCEPTS_1_0_EXR_TYPE());

        return concepts;
    }

    // --------------------------------------------------------------------
    // CONCEPTS: RESOURCE INTERNAL (for retrieveConceptsOfConceptScheme)
    // --------------------------------------------------------------------

    public static ItemResourceInternal mock_resource_SDMX_CROSS_DOMAIN_1_0_TITLE() {
        return SrmMockUtils.buildConcept("TITLE", "Title");
    }

    public static ItemResourceInternal mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_OBS_STATUS() {
        return SrmMockUtils.buildConcept("OBS_STATUS", "Observation value");
    }

    public static ItemResourceInternal mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_CONF_STATUS() {
        return SrmMockUtils.buildConcept("CONF_STATUS", "Confidentiality - status");
    }

    public static ItemResourceInternal mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_UNIT_MULT() {
        return SrmMockUtils.buildConcept("UNIT_MULT", "Unit Multiplier");
    }

    public static ItemResourceInternal mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_COLL_METHOD() {
        return SrmMockUtils.buildConcept("COLL_METHOD", "Data Collection Method");
    }

    public static ItemResourceInternal mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_FREQ() {
        return SrmMockUtils.buildConcept("FREQ", "Frequency");
    }

    public static ItemResourceInternal mock_resource_ECB_CONCEPTS_1_0_EXR_VAR() {
        return SrmMockUtils.buildConcept("EXR_VAR", "Series variation - EXR context");
    }

    public static ItemResourceInternal mock_resource_ECB_CONCEPTS_1_0_EXR_TYPE() {
        return SrmMockUtils.buildConcept("EXR_TYPE", "Exchange rate type");
    }

    public static ItemResourceInternal mock_resource_ECB_CONCEPTS_1_0_CURRENCY_DENOM() {
        return SrmMockUtils.buildConcept("CURRENCY_DENOM", "Currency denominator");
    }

    public static ItemResourceInternal mock_resource_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_DECIMALS() {
        return SrmMockUtils.buildConcept("DECIMALS", "Decimals");
    }

    // --------------------------------------------------------------------
    // CONCEPTS (for retrieveConcept)
    // --------------------------------------------------------------------

    public static Concept mock_SDMX_CROSS_DOMAIN_1_0_OBS_VALUE() {
        return SrmMockUtils.buildConcept("OBS_VALUE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_VALUE", "Observation value",
                SrmMockUtils.buildConceptRepresentation(DataType.STRING));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_1_0_TITLE() {
        return SrmMockUtils.buildConcept("TITLE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).TITLE", "Title",
                SrmMockUtils.buildConceptRepresentation(DataType.STRING));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_OBS_STATUS() {
        return SrmMockUtils.buildConcept("OBS_STATUS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_STATUS", "Observation value",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_OBS_STATUS(1.0)"));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_CONF_STATUS() {
        return SrmMockUtils.buildConcept("CONF_STATUS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).CONF_STATUS", "Confidentiality - status",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_CONF_STATUS(1.0)"));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_UNIT_MULT() {
        return SrmMockUtils.buildConcept("UNIT_MULT", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).UNIT_MULT", "Unit Multiplier",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_UNIT_MULT(1.0)"));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_COLL_METHOD() {
        return SrmMockUtils.buildConcept("COLL_METHOD", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).COLL_METHOD", "Data Collection Method",
                SrmMockUtils.buildConceptRepresentation(DataType.STRING));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_FREQ() {
        return SrmMockUtils.buildConcept("FREQ", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).FREQ", "Frequency",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_FREQ(1.0)"));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_DECIMALS() {
        return SrmMockUtils.buildConcept("DECIMALS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).DECIMALS", "Decimals",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_DECIMALS(1.0)"));
    }

    public static Concept mock_ECB_CONCEPTS_1_0_EXR_VAR() {
        return SrmMockUtils.buildConcept("EXR_VAR", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_VAR", "Series variation - EXR context",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:CL_EXR_VAR(1.0)"));
    }

    public static Concept mock_ECB_CONCEPTS_1_0_EXR_TYPE() {
        return SrmMockUtils.buildConcept("EXR_TYPE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_TYPE", "Exchange rate type",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:CL_EXR_TYPE(1.0)"));
    }

    public static Concept mock_ECB_CONCEPTS_1_0_CURRENCY_DENOM() {
        return SrmMockUtils.buildConcept("CURRENCY_DENOM", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).CURRENCY_DENOM", "Currency denominator",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)"));
    }

    // --------------------------------------------------------------------
    // DSD
    // --------------------------------------------------------------------

    public static DataStructure mock_DSD_ECB_EXR_RG() {
        DataStructure dataStructure = new DataStructure();

        // Dsd attributes
        dataStructure.setAgencyID("ECB");
        dataStructure.setVersion("1.0");
        dataStructure.setId("ECB_EXR_RG");
        dataStructure.setUrn("rn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR_RG(1.0)");
        dataStructure.setName(SrmMockUtils.buildInternationalStringResource("Sample Data Structure Definition for exchange rates, with sibling and rate groups defined.", "en"));

        dataStructure.setDataStructureComponents(new DataStructureComponents());
        // Components: Dimensions
        Dimensions dimensions = new Dimensions();
        dimensions.getDimensions().add(SrmMockUtils.buildDimension("FREQ", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).FREQ", null));

        dimensions.getDimensions().add(
                SrmMockUtils.buildDimension("CURRENCY", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).FREQ",
                        "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)"));

        dimensions.getDimensions().add(SrmMockUtils.buildDimension("CURRENCY_DENOM", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).CURRENCY_DENOM", null));

        dimensions.getDimensions().add(SrmMockUtils.buildDimension("EXR_TYPE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_TYPE", null));

        dimensions.getDimensions().add(SrmMockUtils.buildDimension("EXR_VAR", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_VAR", null));

        dimensions.getDimensions().add(
                SrmMockUtils.buildTimeDimension("TIME_PERIOD", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).TIME_PERIOD", DataType.OBSERVATIONAL_TIME_PERIOD));

        dataStructure.getDataStructureComponents().setDimensions(dimensions);

        // Components: Groups
        dataStructure.getDataStructureComponents().setGroups(new Groups());
        dataStructure.getDataStructureComponents().getGroups().getGroups().add(SrmMockUtils.buildGroupType("SiblingGroup", "CURRENCY", "CURRENCY_DENOM", "EXR_TYPE", "EXR_VAR"));
        dataStructure.getDataStructureComponents().getGroups().getGroups().add(SrmMockUtils.buildGroupType("RateGroup", "EXR_TYPE", "EXR_VAR"));

        // Components: Attributes
        Attributes attributes = new Attributes();

        attributes.getAttributes().add(
                SrmMockUtils.buildAttributeTypeWithGroupRelationship("COLL_METHOD", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).COLL_METHOD", null,
                        AttributeUsageStatusType.CONDITIONAL, "RateGroup"));

        attributes.getAttributes().add(
                SrmMockUtils.buildAttributeTypeWithDimensionRelationship("DECIMALS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).DECIMALS", null,
                        AttributeUsageStatusType.MANDATORY, Arrays.asList("CURRENCY", "CURRENCY_DENOM", "EXR_TYPE"), Arrays.asList("SiblingGroup")));

        attributes.getAttributes().add(
                SrmMockUtils.buildAttributeTypeWithDimensionRelationship("UNIT_MEASURE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).UNIT_MEASURE",
                        "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)", AttributeUsageStatusType.MANDATORY, Arrays.asList("CURRENCY", "CURRENCY_DENOM", "EXR_TYPE"),
                        Arrays.asList("SiblingGroup")));

        attributes.getAttributes().add(
                SrmMockUtils.buildAttributeTypeWithDimensionRelationship("UNIT_MULT", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).UNIT_MULT", null,
                        AttributeUsageStatusType.MANDATORY, Arrays.asList("CURRENCY", "CURRENCY_DENOM", "EXR_TYPE"), Arrays.asList("SiblingGroup")));

        attributes.getAttributes().add(
                SrmMockUtils.buildAttributeTypeWithPrimaryMeasureRelationship("CONF_STATUS_OBS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).CONF_STATUS", null,
                        AttributeUsageStatusType.CONDITIONAL));

        attributes.getAttributes().add(
                SrmMockUtils.buildAttributeTypeWithPrimaryMeasureRelationship("OBS_STATUS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_STATUS", null,
                        AttributeUsageStatusType.MANDATORY));

        attributes.getAttributes().add(
                SrmMockUtils.buildAttributeTypeWithGroupRelationship("TITLE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).TITLE", null,
                        AttributeUsageStatusType.MANDATORY, "SiblingGroup"));

        dataStructure.getDataStructureComponents().setAttributes(attributes);

        // Components: Primary Measure
        Measure measure = new Measure();
        measure.setPrimaryMeasure(SrmMockUtils.buildPrimaryMeasure("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_VALUE", null));
        dataStructure.getDataStructureComponents().setMeasure(measure);

        return dataStructure;
    }

    /**
     * mock the DSD_ECB_EXR_RG dsd's without mandatory in attribute observation and add a attribute observation level with OBS_NOTE code.
     * 
     * @return
     */
    public static DataStructure mock_DSD_ECB_EXR_RG_for_PX() {
        DataStructure dataStructure = mock_DSD_ECB_EXR_RG();

        for (Object attribute : dataStructure.getDataStructureComponents().getAttributes().getAttributes()) {

            if (attribute instanceof Attribute) {
                ((Attribute) attribute).setAssignmentStatus(AttributeUsageStatusType.CONDITIONAL);
            }

        }

        {
            // Add OBS_NOTE attribute
            Attribute obsNote = SrmMockUtils.buildAttributeTypeWithPrimaryMeasureRelationship("OBS_NOTE",
                    "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).CONF_STATUS", null, AttributeUsageStatusType.CONDITIONAL);

            // Not enumerated representation
            Representation simpleDataStructureRepresentationType = new Representation();
            TextFormat simpleComponentTextFormatType = new TextFormat();
            simpleComponentTextFormatType.setTextType(DataType.STRING);
            simpleDataStructureRepresentationType.setTextFormat(simpleComponentTextFormatType);
            obsNote.setLocalRepresentation(simpleDataStructureRepresentationType);

            dataStructure.getDataStructureComponents().getAttributes().getAttributes().add(obsNote);
        }

        {
            // Add OBS_CONF attribute
            Attribute obsConf = SrmMockUtils.buildAttributeTypeWithPrimaryMeasureRelationship("OBS_CONF",
                    "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).CONF_STATUS", null, AttributeUsageStatusType.CONDITIONAL);

            // Not enumerated representation
            Representation simpleDataStructureRepresentationType = new Representation();
            TextFormat simpleComponentTextFormatType = new TextFormat();
            simpleComponentTextFormatType.setTextType(DataType.STRING);
            simpleDataStructureRepresentationType.setTextFormat(simpleComponentTextFormatType);
            obsConf.setLocalRepresentation(simpleDataStructureRepresentationType);

            dataStructure.getDataStructureComponents().getAttributes().getAttributes().add(obsConf);

        }

        return dataStructure;
    }
}
