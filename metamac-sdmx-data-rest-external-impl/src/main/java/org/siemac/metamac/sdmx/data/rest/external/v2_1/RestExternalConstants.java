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

}