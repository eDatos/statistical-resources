package org.siemac.metamac.sdmx.data.rest.external.v2_1;

public class RestExternalConstants {

    // Wildcards values for SDMX identifying parameters
    public enum WildcardIdentifyingEnum {
        ALL, LATEST, UNKNOWN;

        public static WildcardIdentifyingEnum fromCaseInsensitiveString(String s) {
            try {
                return valueOf(s.toUpperCase());
            } catch (IllegalArgumentException ex) {
                return WildcardIdentifyingEnum.UNKNOWN;
            }
        }
    }

    public static final String DETAIL     = "detail";
    public static final String REFERENCES = "references";

}