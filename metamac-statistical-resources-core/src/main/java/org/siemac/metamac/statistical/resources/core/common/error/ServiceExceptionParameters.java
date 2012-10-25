package org.siemac.metamac.statistical.resources.core.common.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;

public class ServiceExceptionParameters extends CommonServiceExceptionParameters {

    // STATISTICAL_RESOURCE
    public static final String STATISTICAL_RESOURCE                        = "statisticalResource";
    public static final String STATISTICAL_RESOURCE_OPERATION              = STATISTICAL_RESOURCE + ".operation";

    // IDENTIFIABLE_STATISTICAL_RESOURCE
    public static final String IDENTIFIABLE_RESOURCE                       = "identifiableStatisticalResource";
    public static final String IDENTIFIABLE_RESOURCE_CODE                  = IDENTIFIABLE_RESOURCE + ".code";

    // NAMEABLE_STATISTICAL_RESOURCE
    public static final String NAMEABLE_RESOURCE                           = "nameableStatisticalResource";
    public static final String NAMEABLE_RESOURCE_TITLE                     = NAMEABLE_RESOURCE + ".title";
    public static final String NAMEABLE_RESOURCE_DESCRIPTION               = NAMEABLE_RESOURCE + ".description";

    // VERSIONABLE_STATISTICAL_RESOURCE
    public static final String VERSIONABLE_RESOURCE                        = "versionableStatisticalResource";
    public static final String VERSIONABLE_RESOURCE_VERSION_LOGIC          = VERSIONABLE_RESOURCE + ".versionLogic";
    public static final String VERSIONABLE_RESOURCE_VERSION_DATE           = VERSIONABLE_RESOURCE + ".versionDate";
    public static final String VERSIONABLE_RESOURCE_VERSION_RATIONALE_TYPE = VERSIONABLE_RESOURCE + ".versionRationaleType";

    // LIFE_CYCLE_STATISTICAL_RESOURCE
    public static final String LIFE_CYCLE_RESOURCE                         = "lifeCycleStatisticalResource";
    public static final String LIFE_CYCLE_RESOURCE_PROC_STATUS             = LIFE_CYCLE_RESOURCE + ".procStatus";

    // SIEMAC_MEADATA_STATISTICAL_RESOURCE
    public static final String SIEMAC_METADATA_RESOURCE                    = "siemacMetadataStatisticalResource";

    // DATASET
    public static final String DATASET                                     = "dataset";
    public static final String DATASET_TITLE                               = DATASET + ".title";
    public static final String DATASET_OPERATION                           = DATASET + ".operation";

    // DATASOURCE
    public static final String DATASOURCE                                  = "datasource";
    public static final String DATASOURCE_DATASET                          = DATASOURCE + ".dataset";

    // COLLECTIONS
    public static final String COLLECTION                                  = "collection";
    public static final String COLLECTION_TITLE                            = COLLECTION + ".title";
    public static final String COLLECTION_OPERATION                        = COLLECTION + ".operation";

    // LIFECYCLE
    public static final String PROC_STATUS_DRAFT                           = StatisticalResourceProcStatusEnum.DRAFT.name();
    public static final String PROC_STATUS_PRODUCTION_VALIDATION           = StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.name();
    public static final String PROC_STATUS_DIFFUSION_VALIDATION            = StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.name();
    public static final String PROC_STATUS_VALIDATION_REJECTED             = StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.name();
    public static final String PROC_STATUS_PUBLICATION_PROGRAMMED          = StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED.name();
    public static final String PROC_STATUS_PUBLICATION_PENDING             = StatisticalResourceProcStatusEnum.PUBLICATION_PENDING.name();
    public static final String PROC_STATUS_PUBLISHED                       = StatisticalResourceProcStatusEnum.PUBLISHED.name();
    public static final String PROC_STATUS_PUBLICATION_FAILED              = StatisticalResourceProcStatusEnum.PUBLICATION_FAILED.name();
    public static final String PROC_STATUS_ARCHIVED                        = StatisticalResourceProcStatusEnum.ARCHIVED.name();

    // QUERY
    public static final String QUERY                                       = "query";

}
