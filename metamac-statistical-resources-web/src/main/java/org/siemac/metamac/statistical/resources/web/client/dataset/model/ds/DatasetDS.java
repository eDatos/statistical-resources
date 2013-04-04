package org.siemac.metamac.statistical.resources.web.client.dataset.model.ds;

import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;

public class DatasetDS extends StatisticalResourceDS {

    // CONTENT DESCRIPTORS
    public static final String GEOGRAPHIC_COVERAGE        = "ds-geo-cov";
    public static final String TEMPORAL_COVERAGE          = "ds-tem-cov";
    public static final String GEOGRAPHIC_GRANULARITY     = "ds-geo-gra";
    public static final String TEMPORAL_GRANULARITY       = "ds-tem-gra";
    public static final String DATE_START                 = "ds-date-start";
    public static final String DATE_END                   = "ds-date-end";
    public static final String STATISTICAL_UNIT           = "ds-stat-unit";
    public static final String MEASURES                   = "ds-meas";

    // CLASS DESCRIPTORS
    public static final String FORMAT_EXTENT_OBSERVATIONS = "ds-for-ext-obs";
    public static final String FORMAT_EXTENT_DIMENSIONS   = "ds-for-ext-dim";

    // PUBLICATION DESCRIPTORS
    public static final String DATE_NEXT_UPDATE           = "ds-date-next-up";
    public static final String UPDATE_FRECUENCY           = "ds-up-freq";
    public static final String STATISTIC_OFFICIALITY      = "ds-sta-off";
    public static final String BIBLIOGRAPHIC_CITATION     = "ds-bib-cit";

    // PRODUCTION DESCRIPTORS
    public static final String RELATED_DSD                        = "ds-dsd";

    // DTO
    public static final String DTO                        = "ds-dto";
}
