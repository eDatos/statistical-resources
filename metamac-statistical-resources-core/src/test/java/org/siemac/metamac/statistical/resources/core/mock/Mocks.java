package org.siemac.metamac.statistical.resources.core.mock;

import java.util.Arrays;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.BasicComponentDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TimeDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.UsageStatusType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.utils.SrmMockUtils;

public class Mocks {

    public static Codelist mock_CL_DECIMALS() {
        Codelist codelist = new Codelist();

        // Codelist attributes
        codelist.setAgencyID("SDMX");
        codelist.setVersion("1.0");
        codelist.setId("CL_DECIMALS");
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_DECIMALS(1.0)");

        codelist.getNames().add(SrmMockUtils.buildTextType("Code list for Decimals (DECIMALS)", "en"));
        codelist.getDescriptions().add(SrmMockUtils.buildTextType("It provides a list of values showing the number of decimal digits used in the data.", "en"));

        codelist.getCodes().add(SrmMockUtils.buildCode("0", "Zero", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("1", "One", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("2", "Two", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("3", "Three", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("4", "Four", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("5", "Five", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("6", "Six", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("7", "Seven", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("8", "Eight", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("9", "Nine", "en"));

        return codelist;
    }

    public static Codelist mock_CL_FREQ() {
        Codelist codelist = new Codelist();

        // Codelist attributes
        codelist.setAgencyID("SDMX");
        codelist.setVersion("1.0");
        codelist.setId("CL_FREQ");
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_FREQ(1.0)");

        codelist.getNames().add(SrmMockUtils.buildTextType("Code list for Frequency (FREQ)", "en"));
        codelist.getDescriptions()
                .add(SrmMockUtils
                        .buildTextType(
                                "It provides a list of values indicating the \"frequency\" of the data (e.g. monthly) and, thus, indirectly, also implying the type of \"time reference\" that could be used for identifying the data with respect time.",
                                "en"));

        codelist.getCodes().add(SrmMockUtils.buildCode("A", "Annual", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("B", "Daily - business week", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("D", "Daily", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("M", "Monthly", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("N", "Minutely", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("Q", "Quarterly", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("S", "Half Yearly, semester", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("W", "Weekly", "en"));

        return codelist;
    }

    public static Codelist mock_CL_CONF_STATUS() {
        Codelist codelist = new Codelist();

        // Codelist attributes
        codelist.setAgencyID("SDMX");
        codelist.setVersion("1.0");
        codelist.setId("CL_CONF_STATUS");
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_CONF_STATUS(1.0)");

        codelist.getNames().add(SrmMockUtils.buildTextType("code list for Confidentiality Status (CONF_STATUS)", "en"));
        codelist.getDescriptions().add(SrmMockUtils.buildTextType("this code list provides coded information about the sensitivity and confidentiality status of the data.", "en"));

        codelist.getCodes().add(SrmMockUtils.buildCode("C", "Confidential statistical information", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("D", "Secondary confidentiality set by the sender, not for   publication", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("F", "Free", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("N", "Not for publication, restricted for internal use only", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("S", "Secondary confidentiality set and managed by the receiver, not for publication", "en"));

        return codelist;
    }

    public static Codelist mock_CL_OBS_STATUS() {
        Codelist codelist = new Codelist();

        // Codelist attributes
        codelist.setAgencyID("SDMX");
        codelist.setVersion("1.0");
        codelist.setId("CL_OBS_STATUS");
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_OBS_STATUS(1.0)");

        codelist.getNames().add(SrmMockUtils.buildTextType("code list for Confidentiality Status (CONF_STATUS)", "en"));
        codelist.getDescriptions().add(SrmMockUtils.buildTextType("this code list provides coded information about the sensitivity and confidentiality status of the data.", "en"));

        codelist.getCodes().add(SrmMockUtils.buildCode("A", "Normal", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("B", "Break", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("E", "Estimated value", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("F", "Forecast value", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("I", "Imputed value (CCSA definition)", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("M", "Missing value", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("P", "Provisional value", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("S", "Strike", "en"));

        return codelist;
    }

    public static Codelist mock_CL_UNIT_MULT() {
        Codelist codelist = new Codelist();

        // Codelist attributes
        codelist.setAgencyID("SDMX");
        codelist.setVersion("1.0");
        codelist.setId("CL_UNIT_MULT");
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_UNIT_MULT(1.0)");

        codelist.getNames().add(SrmMockUtils.buildTextType("code list for the Unit Multiplier (UNIT_MULT)", "en"));

        codelist.getCodes().add(SrmMockUtils.buildCode("0", "Units", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("1", "Tens", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("2", "Hundreds", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("3", "Thousands", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("4", "Tens of thousands", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("6", "Millions", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("9", "Billions", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("12", "Trillions", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("15", "Quadrillions", "en"));

        return codelist;
    }

    public static Codelist mock_CL_EXR_TYPE() {
        Codelist codelist = new Codelist();

        // Codelist attributes
        codelist.setAgencyID("ECB");
        codelist.setVersion("1.0");
        codelist.setId("CL_EXR_TYPE");
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:CL_EXR_TYPE(1.0)");

        codelist.getNames().add(SrmMockUtils.buildTextType("code list for the exchange rates types", "en"));

        codelist.getCodes().add(SrmMockUtils.buildCode("CR00", "Official fixing", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("RR00", "Reference rate", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("SP00", "Spot", "en"));

        return codelist;
    }

    public static Codelist mock_CL_EXR_VAR() {
        Codelist codelist = new Codelist();

        // Codelist attributes
        codelist.setAgencyID("ECB");
        codelist.setVersion("1.0");
        codelist.setId("CL_EXR_VAR");
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:CL_EXR_VAR(1.0)");

        codelist.getNames().add(SrmMockUtils.buildTextType("code list for the exchange rate series variation", "en"));

        codelist.getCodes().add(SrmMockUtils.buildCode("E", "End-of-period", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("P", "Growth rate over previous period", "en"));

        return codelist;
    }

    public static Codelist mock_CL_CURRENCY() {
        Codelist codelist = new Codelist();

        // Codelist attributes
        codelist.setAgencyID("ISO");
        codelist.setVersion("1.0");
        codelist.setId("CL_CURRENCY");
        codelist.setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)");

        codelist.getNames().add(SrmMockUtils.buildTextType("code list for the exchange rate series variation", "en"));

        codelist.getCodes().add(SrmMockUtils.buildCode("CHF", "Swiss franc", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("EUR", "Euro", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("GBP", "Pound sterling", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("JPY", "Japanese yen", "en"));
        codelist.getCodes().add(SrmMockUtils.buildCode("USD", "United States dollar", "en"));

        return codelist;
    }

    public static ConceptScheme mock_CROSS_DOMAIN_CONCEPTS() {
        ConceptScheme conceptScheme = new ConceptScheme();

        // Codelist attributes
        conceptScheme.setAgencyID("SDMX");
        conceptScheme.setVersion("1.0");
        conceptScheme.setId("CROSS_DOMAIN_CONCEPTS");
        conceptScheme.setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=SDMX:CROSS_DOMAIN_CONCEPTS(1.0)");

        conceptScheme.getNames().add(SrmMockUtils.buildTextType("SDMX Cross Domain Concept Scheme", "en"));

        conceptScheme.getConcepts().add(mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_COLL_METHOD());

        conceptScheme.getConcepts().add(
                SrmMockUtils.buildConcept("CONF", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).CONF", "Confidentiality", "en",
                        SrmMockUtils.buildConceptRepresentation(BasicComponentDataType.STRING)));

        conceptScheme.getConcepts().add(mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_CONF_STATUS());

        conceptScheme.getConcepts().add(SrmMockUtils.buildConcept("CURRENCY", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).CURRENCY", "Currency", "en", null));

        conceptScheme.getConcepts().add(mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_DECIMALS());

        conceptScheme.getConcepts().add(mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_FREQ());

        conceptScheme.getConcepts().add(
                SrmMockUtils.buildConcept("OBS_VALUE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_VALUE", "Observation value", "en",
                        SrmMockUtils.buildConceptRepresentation(BasicComponentDataType.STRING)));

        conceptScheme.getConcepts().add(mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_OBS_STATUS());

        conceptScheme.getConcepts().add(
                SrmMockUtils.buildConcept("TIME_PERIOD", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).TIME_PERIOD", "Time Period", "en",
                        SrmMockUtils.buildConceptRepresentation(BasicComponentDataType.OBSERVATIONAL_TIME_PERIOD)));

        conceptScheme.getConcepts().add(mock_SDMX_CROSS_DOMAIN_1_0_TITLE());

        conceptScheme.getConcepts().add(mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_UNIT_MULT());

        conceptScheme.getConcepts().add(
                SrmMockUtils.buildConcept("UNIT_MEASURE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).UNIT_MEASURE", "Unit of Measure", "en", null));

        return conceptScheme;
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_1_0_TITLE() {
        return SrmMockUtils.buildConcept("TITLE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).TITLE", "Title", "en",
                SrmMockUtils.buildConceptRepresentation(BasicComponentDataType.STRING));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_OBS_STATUS() {
        return SrmMockUtils.buildConcept("OBS_STATUS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_STATUS", "Observation value", "en",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_OBS_STATUS(1.0)"));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_CONF_STATUS() {
        return SrmMockUtils.buildConcept("CONF_STATUS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).CONF_STATUS", "Confidentiality - status", "en",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_CONF_STATUS(1.0)"));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_UNIT_MULT() {
        return SrmMockUtils.buildConcept("UNIT_MULT", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).UNIT_MULT", "Unit Multiplier", "en",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_UNIT_MULT(1.0)"));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_COLL_METHOD() {
        return SrmMockUtils.buildConcept("COLL_METHOD", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).COLL_METHOD", "Data Collection Method", "en",
                SrmMockUtils.buildConceptRepresentation(BasicComponentDataType.STRING));
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_FREQ() {
        return SrmMockUtils.buildConcept("FREQ", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).FREQ", "Frequency", "en",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_FREQ(1.0)"));
    }

    public static ConceptScheme mock_ECB_CONCEPTS() {
        ConceptScheme conceptScheme = new ConceptScheme();

        // Codelist attributes
        conceptScheme.setAgencyID("ECB");
        conceptScheme.setVersion("1.0");
        conceptScheme.setId("ECB_CONCEPTS");
        conceptScheme.setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=ECB:ECB_CONCEPTS(1.0)");

        conceptScheme.getNames().add(SrmMockUtils.buildTextType("Concepts maintained by the ECB", "en"));

        conceptScheme.getConcepts().add(mock_ECB_CONCEPTS_1_0_CURRENCY_DENOM());

        conceptScheme.getConcepts().add(mock_ECB_CONCEPTS_1_0_EXR_VAR());

        conceptScheme.getConcepts().add(mock_ECB_CONCEPTS_1_0_EXR_TYPE());

        return conceptScheme;
    }

    public static Concept mock_ECB_CONCEPTS_1_0_EXR_VAR() {
        return SrmMockUtils.buildConcept("EXR_VAR", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_VAR", "Series variation - EXR context", "en",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:CL_EXR_VAR(1.0)"));
    }

    public static Concept mock_ECB_CONCEPTS_1_0_EXR_TYPE() {
        return SrmMockUtils.buildConcept("EXR_TYPE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_TYPE", "Exchange rate type", "en",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:CL_EXR_TYPE(1.0)"));
    }

    public static Concept mock_ECB_CONCEPTS_1_0_CURRENCY_DENOM() {
        return SrmMockUtils.buildConcept("CURRENCY_DENOM", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).CURRENCY_DENOM", "Currency denominator", "en",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)"));
    }

    public static DataStructure mock_DSD_ECB_EXR_RG() {
        DataStructure dataStructure = new DataStructure();

        // Dsd attributes
        dataStructure.setAgencyID("ECB");
        dataStructure.setVersion("1.0");
        dataStructure.setId("ECB_EXR_RG");
        dataStructure.setUrn("rn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR_RG(1.0)");
        dataStructure.getNames().add(SrmMockUtils.buildTextType("Sample Data Structure Definition for exchange rates, with sibling and rate groups defined.", "en"));

        dataStructure.setDataStructureComponents(new DataStructureComponentsType());
        // Components: Dimensions
        DimensionListType dimensionListType = new DimensionListType();
        dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(
                SrmMockUtils.buildDimension("FREQ", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).FREQ", null));

        dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(
                SrmMockUtils.buildDimension("CURRENCY", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).FREQ",
                        "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)"));

        dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(
                SrmMockUtils.buildDimension("CURRENCY_DENOM", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).CURRENCY_DENOM", null));

        dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(
                SrmMockUtils.buildDimension("EXR_TYPE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_TYPE", null));

        dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions().add(
                SrmMockUtils.buildDimension("EXR_VAR", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_CONCEPTS(1.0).EXR_VAR", null));

        dimensionListType.getDimensionsAndMeasureDimensionsAndTimeDimensions()
                .add(SrmMockUtils.buildTimeDimension("TIME_PERIOD", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).TIME_PERIOD",
                        TimeDataType.OBSERVATIONAL_TIME_PERIOD));

        dataStructure.getDataStructureComponents().setDimensionList(dimensionListType);

        // Components: Groups
        dataStructure.getDataStructureComponents().getGroups().add(SrmMockUtils.buildGroupType("SiblingGroup", "CURRENCY", "CURRENCY_DENOM", "EXR_TYPE", "EXR_VAR"));
        dataStructure.getDataStructureComponents().getGroups().add(SrmMockUtils.buildGroupType("RateGroup", "EXR_TYPE", "EXR_VAR"));

        // Components: Attributes
        AttributeListType attributeListType = new AttributeListType();

        attributeListType.getAttributesAndReportingYearStartDaies().add(
                SrmMockUtils.buildAttributeTypeWithGroupRelationship("COLL_METHOD", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).COLL_METHOD", null,
                        UsageStatusType.CONDITIONAL, "RateGroup"));

        attributeListType.getAttributesAndReportingYearStartDaies().add(
                SrmMockUtils.buildAttributeTypeWithDimensionRelationship("DECIMALS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).DECIMALS", null,
                        UsageStatusType.MANDATORY, Arrays.asList("CURRENCY", "CURRENCY_DENOM", "EXR_TYPE"), Arrays.asList("SiblingGroup")));

        attributeListType.getAttributesAndReportingYearStartDaies().add(
                SrmMockUtils.buildAttributeTypeWithDimensionRelationship("UNIT_MEASURE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).UNIT_MEASURE",
                        "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ISO:CL_CURRENCY(1.0)", UsageStatusType.MANDATORY, Arrays.asList("CURRENCY", "CURRENCY_DENOM", "EXR_TYPE"),
                        Arrays.asList("SiblingGroup")));

        attributeListType.getAttributesAndReportingYearStartDaies().add(
                SrmMockUtils.buildAttributeTypeWithDimensionRelationship("UNIT_MULT", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).UNIT_MULT", null,
                        UsageStatusType.MANDATORY, Arrays.asList("CURRENCY", "CURRENCY_DENOM", "EXR_TYPE"), Arrays.asList("SiblingGroup")));

        attributeListType.getAttributesAndReportingYearStartDaies().add(
                SrmMockUtils.buildAttributeTypeWithPrimaryMeasureRelationship("CONF_STATUS_OBS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).CONF_STATUS", null,
                        UsageStatusType.CONDITIONAL));

        attributeListType.getAttributesAndReportingYearStartDaies().add(
                SrmMockUtils.buildAttributeTypeWithPrimaryMeasureRelationship("OBS_STATUS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_STATUS", null,
                        UsageStatusType.MANDATORY));

        attributeListType.getAttributesAndReportingYearStartDaies().add(
                SrmMockUtils.buildAttributeTypeWithGroupRelationship("TITLE", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).TITLE", null,
                        UsageStatusType.MANDATORY, "SiblingGroup"));

        dataStructure.getDataStructureComponents().setAttributeList(attributeListType);

        // Components: Primary Measure
        MeasureListType measureListType = new MeasureListType();
        measureListType.setPrimaryMeasure(SrmMockUtils.buildPrimaryMeasure("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).OBS_VALUE", null));

        dataStructure.getDataStructureComponents().setMeasureList(measureListType);

        return dataStructure;
    }

    public static Concept mock_SDMX_CROSS_DOMAIN_CONCEPTS_1_0_DECIMALS() {
        return SrmMockUtils.buildConcept("DECIMALS", "urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=SDMX:CROSS_DOMAIN_CONCEPTS(1.0).DECIMALS", "Decimals", "en",
                SrmMockUtils.buildConceptRepresentation("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=SDMX:CL_DECIMALS(1.0)"));
    }
}
