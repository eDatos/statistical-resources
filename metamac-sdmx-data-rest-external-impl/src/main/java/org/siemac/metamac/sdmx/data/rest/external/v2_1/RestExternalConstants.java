package org.siemac.metamac.sdmx.data.rest.external.v2_1;

public class RestExternalConstants {

    // Wildcards values for SDMX identifying parameters
    public enum WildcardIdentifyingEnum {
        ALL, UNKNOWN;

        public static WildcardIdentifyingEnum fromCaseInsensitiveString(String s) {
            try {
                return valueOf(s.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return WildcardIdentifyingEnum.UNKNOWN;
            }
        }
    }

    public static final String START_PERIOD             = "startPeriod";
    public static final String END_PERIOD               = "endPeriod";
    public static final String UPDATED_AFTER            = "updatedAfter";
    public static final String FIRST_N_OBSERVATIONS     = "firstNObservations";
    public static final String LAST_N_OBSERVATIONS      = "lastNObservations";
    public static final String DIMENSION_AT_OBSERVATION = "dimensionAtObservation";
    public static final String DETAIL                   = "detail";
    public static final String REFERENCES               = "references";

    //
    // public static final String MEDIATYPE_MESSAGE_GENERICDATA_2_1 = "application/vnd.sdmx.genericdata+xml;version=2.1";
    // public static final String MEDIATYPE_MESSAGE_STRUCTURESPECIFICDATA_2_1 = "application/vnd.sdmx.structurespecificdata+xml;version=2.1";
    // public static final String MEDIATYPE_MESSAGE_GENERICTIMESERIESDATA_2_1 = "application/vnd.sdmx.generictimeseriesdata+xml;version=2.1";
    // public static final String MEDIATYPE_MESSAGE_STRUCTURESPECIFICTIMESERIESDATA_2_1 = "application/vnd.sdmx.structurespecifictimeseriesdata+xml;version=2.1";
    // public static final String MEDIATYPE_MESSAGE_Error_2_1 = "application/vnd.sdmx.error+xml;version=2.1";

    // public static final MediaType MEDIATYPE_MESSAGE_GENERICDATA_2_1_ = new MediaType("application/vnd.sdmx.genericdata+xml;version=2.1");

}