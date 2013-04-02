package org.siemac.metamac.statistical.resources.web.client.model.ds;

public class StatisticalResourceDS extends LifeCycleResourceDS {

    // CONTENT DESCRIPTORS
    public static final String SUBTITLE             = "sr-subtitle";
    public static final String TITLE_ALTERNATIVE    = "sr-title-alt";
    public static final String ABSTRACT             = "sr-abstract";
    public static final String KEYWORDS             = "sr-keywords";

    // PRODUCTION DESCRIPTORS
    public static final String MAINTAINER           = "sr-maintainer";
    public static final String CREATOR              = "sr-creator";
    public static final String CONTRIBUTOR          = "sr-contr";
    public static final String DATE_CREATED         = "sr-date-cr";
    public static final String LAST_UPDATE          = "sr-last-up";
    public static final String CONFORMS_TO          = "sr-conf";
    public static final String CONFORMS_TO_INTERNAL = "sr-conf-int";

    // CLASS DESCRIPTORS
    public static final String TYPE                 = "sr-type";
    public static final String FORMAT               = "sr-format";

    // RESOURCE RELATION DESCRIPTORS
    public static final String SOURCE               = "sr-source";
    public static final String REPLACES             = "sr-replaces";
    public static final String IS_REPLACED_BY       = "sr-is-rep-by";
    public static final String REQUIRES             = "sr-requires";
    public static final String IS_REQUIRED_BY       = "sr-is-req-by";
    public static final String HAS_PART             = "sr-has-part";
    public static final String IS_PART_OF           = "sr-is-part-of";
    public static final String IS_REFERENCE_BY      = "sr-is-ref-by";
    public static final String REFERENCES           = "sr-references";
    public static final String IS_FORMAT_OF         = "sr-is-format-of";
    public static final String HAS_FORMAT           = "sr-has-format";
}
