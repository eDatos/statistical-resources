package org.siemac.metamac.statistical.resources.core.common.error;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;

public class ServiceExceptionParameters {

    public static final String IDENTIFIER                         = "identifier";
    public static final String URN                                = "urn";

    // DATASET
    public static final String DATASET                            = "dataset";
    public static final String DATASET_TITLE                      = DATASET + ".title";
    public static final String DATASET_OPERATION                  = DATASET + ".operation";

    // DATASOURCE
    public static final String DATASOURCE                         = "datasource";
    public static final String DATASOURCE_DATASET                 = DATASOURCE + ".dataset";

    // COLLECTIONS
    public static final String COLLECTION                         = "collection";
    public static final String COLLECTION_TITLE                   = COLLECTION + ".title";
    public static final String COLLECTION_OPERATION               = COLLECTION + ".operation";

    // LIFECYCLE
    public static final String PROC_STATUS_DRAFT                  = StatisticalResourceProcStatusEnum.DRAFT.name();
    public static final String PROC_STATUS_PRODUCTION_VALIDATION  = StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.name();
    public static final String PROC_STATUS_DIFFUSION_VALIDATION   = StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.name();
    public static final String PROC_STATUS_VALIDATION_REJECTED    = StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.name();
    public static final String PROC_STATUS_PUBLICATION_PROGRAMMED = StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED.name();
    public static final String PROC_STATUS_PUBLICATION_PENDING    = StatisticalResourceProcStatusEnum.PUBLICATION_PENDING.name();
    public static final String PROC_STATUS_PUBLISHED              = StatisticalResourceProcStatusEnum.PUBLISHED.name();
    public static final String PROC_STATUS_PUBLICATION_FAILED     = StatisticalResourceProcStatusEnum.PUBLICATION_FAILED.name();
    public static final String PROC_STATUS_ARCHIVED               = StatisticalResourceProcStatusEnum.ARCHIVED.name();

    // QUERY
    public static final String QUERY                              = "query";

}
