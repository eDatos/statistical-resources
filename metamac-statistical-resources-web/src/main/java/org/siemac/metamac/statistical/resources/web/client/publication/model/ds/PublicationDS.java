package org.siemac.metamac.statistical.resources.web.client.publication.model.ds;

import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleStatisticalResourceDS;

public class PublicationDS extends LifeCycleStatisticalResourceDS {

    // CONTENT METADATA
    public static final String LANGUAGE                = "cm-lang";
    public static final String LANGUAGES               = "cm-langs";
    public static final String KEYWORDS                = "cm-keywords";
    public static final String SPATIAL_COVERAGE        = "cm-cov-spa";
    public static final String SPATIAL_COVERAGE_CODES  = "cm-cov-spa-codes";
    public static final String TEMPORAL_COVERAGE       = "cm-cov-temp";
    public static final String TEMPORAL_COVERAGE_CODES = "cm-cov-temp-codes";
    public static final String NEXT_UPDATE_DATE        = "cm-next-up";
    public static final String UPDATE_FREQUENCY        = "cm-up-freq";
    public static final String RIGHTS_HOLDER           = "cm-rights-holder";
    public static final String COPYRIGHTED_DATE        = "cm-date-copy";
    public static final String LICENSE                 = "cm-lic";
    public static final String DESCRIPTION             = "cm-desc";
    public static final String TYPE                    = "cm-type";
    public static final String FORMAT                  = "cm-format";

    public static String       DTO                     = "col-dto";

    public PublicationDS() {
    }

}
