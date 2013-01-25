package org.siemac.metamac.statistical.resources.core.error;

public class ServiceExceptionParameters extends org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters {

    public static final String VERSION_TYPE = "versionType";
    /*
     * // PARAMETERS
     * public static final String DATASET_VERSION_URN = "datasetVersionUrn";
     * public static final String DATASET_VERSION_URN_TO_COPY = "datasetVersionUrnToCopy";
     * public static final String PUBLICATION_VERSION_URN = "publicationVersionUrn";
     * public static final String PUBLICATION_VERSION_URN_TO_COPY = "publicationVersionUrnToCopy";
     * // STATISTICAL_RESOURCE
     * public static final String STATISTICAL_RESOURCE = "statisticalResource";
     * public static final String STATISTICAL_RESOURCE_ID = STATISTICAL_RESOURCE + ".id";
     * public static final String STATISTICAL_RESOURCE_UUID = STATISTICAL_RESOURCE + ".uuid";
     * public static final String STATISTICAL_RESOURCE_VERSION = STATISTICAL_RESOURCE + ".version";
     * public static final String STATISTICAL_RESOURCE_CREATED_DATE = STATISTICAL_RESOURCE + ".createdDate";
     * public static final String STATISTICAL_RESOURCE_CREATED_BY = STATISTICAL_RESOURCE + ".createdBy";
     * public static final String STATISTICAL_RESOURCE_LAST_UPDATED = STATISTICAL_RESOURCE + ".lastUpdated";
     * public static final String STATISTICAL_RESOURCE_LAST_UPDATED_BY = STATISTICAL_RESOURCE + ".lastUpdatedBy";
     * // IDENTIFIABLE_STATISTICAL_RESOURCE
     * public static final String IDENTIFIABLE_RESOURCE = "identifiableStatisticalResource";
     * public static final String IDENTIFIABLE_RESOURCE_URN = IDENTIFIABLE_RESOURCE + ".urn";
     * public static final String IDENTIFIABLE_RESOURCE_CODE = IDENTIFIABLE_RESOURCE + ".code";
     * public static final String IDENTIFIABLE_RESOURCE_URI = IDENTIFIABLE_RESOURCE + ".uri";
     * // NAMEABLE_STATISTICAL_RESOURCE
     * public static final String NAMEABLE_RESOURCE = "nameableStatisticalResource";
     * public static final String NAMEABLE_RESOURCE_TITLE = NAMEABLE_RESOURCE + ".title";
     * public static final String NAMEABLE_RESOURCE_DESCRIPTION = NAMEABLE_RESOURCE + ".description";
     * // VERSIONABLE_STATISTICAL_RESOURCE
     * public static final String VERSIONABLE_RESOURCE = "versionableStatisticalResource";
     * public static final String VERSIONABLE_RESOURCE_VERSION_LOGIC = VERSIONABLE_RESOURCE + ".versionLogic";
     * public static final String VERSIONABLE_RESOURCE_VERSION_DATE = VERSIONABLE_RESOURCE + ".versionDate";
     * public static final String VERSIONABLE_RESOURCE_VERSION_RATIONALE_TYPE = VERSIONABLE_RESOURCE + ".versionRationaleType";
     * public static final String VERSIONABLE_RESOURCE_VERSION_RATIONALE = VERSIONABLE_RESOURCE + ".versionRationale";
     * // LIFE_CYCLE_STATISTICAL_RESOURCE
     * public static final String LIFE_CYCLE_RESOURCE = "lifeCycleStatisticalResource";
     * public static final String LIFE_CYCLE_RESOURCE_PROC_STATUS = LIFE_CYCLE_RESOURCE + ".procStatus";
     * // SIEMAC_MEADATA_STATISTICAL_RESOURCE
     * public static final String SIEMAC_METADATA_RESOURCE = "siemacMetadataStatisticalResource";
     * public static final String SIEMAC_METADATA_RESOURCE_LANGUAGE = SIEMAC_METADATA_RESOURCE + ".language";
     * public static final String SIEMAC_METADATA_RESOURCE_LANGUAGES = SIEMAC_METADATA_RESOURCE + ".languages";
     * public static final String SIEMAC_METADATA_RESOURCE_OPERATION = SIEMAC_METADATA_RESOURCE + ".statisticalOperation";
     * public static final String SIEMAC_METADATA_RESOURCE_OPERATION_INSTANCE = SIEMAC_METADATA_RESOURCE + ".statisticalOperationInstance";
     * public static final String SIEMAC_METADATA_RESOURCE_SUBTITLE = SIEMAC_METADATA_RESOURCE + ".subtitle";
     * public static final String SIEMAC_METADATA_RESOURCE_TITLE_ALTERNATIVE = SIEMAC_METADATA_RESOURCE + ".titleAlternative";
     * public static final String SIEMAC_METADATA_RESOURCE_ABSTRACT = SIEMAC_METADATA_RESOURCE + ".abstract";
     * public static final String SIEMAC_METADATA_RESOURCE_KEYWORDS = SIEMAC_METADATA_RESOURCE + ".keywords";
     * public static final String SIEMAC_METADATA_RESOURCE_TYPE = SIEMAC_METADATA_RESOURCE + ".type";
     * public static final String SIEMAC_METADATA_RESOURCE_MAINTAINER = SIEMAC_METADATA_RESOURCE + ".maintainer";
     * public static final String SIEMAC_METADATA_RESOURCE_CREATOR = SIEMAC_METADATA_RESOURCE + ".creator";
     * public static final String SIEMAC_METADATA_RESOURCE_CONTRIBUTOR = SIEMAC_METADATA_RESOURCE + ".contributor";
     * public static final String SIEMAC_METADATA_RESOURCE_CREATED_DATE = SIEMAC_METADATA_RESOURCE + ".resourceCreatedDate";
     * public static final String SIEMAC_METADATA_RESOURCE_LAST_UPDATE = SIEMAC_METADATA_RESOURCE + ".lastUpdate";
     * public static final String SIEMAC_METADATA_RESOURCE_CONFORMS_TO = SIEMAC_METADATA_RESOURCE + ".conformsTo";
     * public static final String SIEMAC_METADATA_RESOURCE_CONFORMS_TO_INTERNAL = SIEMAC_METADATA_RESOURCE + ".conformsToInternal";
     * public static final String SIEMAC_METADATA_RESOURCE_PUBLISHER = SIEMAC_METADATA_RESOURCE + ".publisher";
     * public static final String SIEMAC_METADATA_RESOURCE_PUBLISHER_CONTRIBUTOR = SIEMAC_METADATA_RESOURCE + ".publisherContributor";
     * public static final String SIEMAC_METADATA_RESOURCE_MEDIATOR = SIEMAC_METADATA_RESOURCE + ".mediator";
     * public static final String SIEMAC_METADATA_RESOURCE_NEWNESS_UNTIL_DATE = SIEMAC_METADATA_RESOURCE + ".newnessUntilDate";
     * public static final String SIEMAC_METADATA_RESOURCE_REPLACES = SIEMAC_METADATA_RESOURCE + ".replaces";
     * // DATASET
     * public static final String DATASET = "dataset";
     * public static final String DATASET_VERSION_NUMBER = DATASET + ".versionNumber";
     * public static final String DATASET_TITLE = DATASET + ".title";
     * public static final String DATASET_OPERATION = DATASET + ".operation";
     * // DATASET VERSION
     * public static final String DATASET_VERSION = "datasetVersion";
     * public static final String DATASET_VERSION_ID = DATASET_VERSION + ".id";
     * public static final String DATASET_VERSION_UUID = DATASET_VERSION + ".uuid";
     * public static final String DATASET_VERSION_VERSION = DATASET_VERSION + ".version";
     * public static final String DATASET_VERSION_DATASET = DATASET_VERSION + ".dataset";
     * public static final String DATASET_VERSION_RELATED_DSD = DATASET_VERSION + ".relatedDsd";
     * public static final String DATASET_VERSION_UPDATE_FREQUENCY = DATASET_VERSION + ".updateFrequency";
     * public static final String DATASET_VERSION_STATISTICAL_UNIT = DATASET_VERSION + ".statisticalUnit";
     * // DATASOURCE
     * public static final String DATASOURCE = "datasource";
     * public static final String DATASOURCE_ID = DATASOURCE + ".id";
     * public static final String DATASOURCE_UUID = DATASOURCE + ".uuid";
     * public static final String DATASOURCE_VERSION = DATASOURCE + ".version";
     * public static final String DATASOURCE_URN = DATASOURCE + ".urn";
     * public static final String DATASOURCE_DATASET = DATASOURCE + ".dataset";
     * public static final String DATASOURCE_DATASET_VERSION = DATASOURCE + ".datasetVersion";
     * // COLLECTIONS
     * public static final String PUBLICATION = "collection";
     * public static final String PUBLICATION_TITLE = PUBLICATION + ".title";
     * public static final String PUBLICATION_OPERATION = PUBLICATION + ".operation";
     * // PUBLICATION VERSION
     * public static final String PUBLICATION_VERSION = "datasetVersion";
     * public static final String PUBLICATION_VERSION_ID = PUBLICATION_VERSION + ".id";
     * public static final String PUBLICATION_VERSION_UUID = PUBLICATION_VERSION + ".uuid";
     * public static final String PUBLICATION_VERSION_VERSION = PUBLICATION_VERSION + ".version";
     * public static final String PUBLICATION_VERSION_PUBLICATION = PUBLICATION_VERSION + ".dataset";
     * // PROC STATUS
     * public static final String PROC_STATUS_DRAFT = StatisticalResourceProcStatusEnum.DRAFT.name();
     * public static final String PROC_STATUS_PRODUCTION_VALIDATION = StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.name();
     * public static final String PROC_STATUS_DIFFUSION_VALIDATION = StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.name();
     * public static final String PROC_STATUS_VALIDATION_REJECTED = StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.name();
     * public static final String PROC_STATUS_PUBLICATION_PROGRAMMED = StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED.name();
     * public static final String PROC_STATUS_PUBLICATION_PENDING = StatisticalResourceProcStatusEnum.PUBLICATION_PENDING.name();
     * public static final String PROC_STATUS_PUBLISHED = StatisticalResourceProcStatusEnum.PUBLISHED.name();
     * public static final String PROC_STATUS_PUBLICATION_FAILED = StatisticalResourceProcStatusEnum.PUBLICATION_FAILED.name();
     * public static final String PROC_STATUS_ARCHIVED = StatisticalResourceProcStatusEnum.ARCHIVED.name();
     * // QUERY
     * public static final String QUERY = "query";
     * public static final String QUERY_ID = QUERY + ".id";
     * public static final String QUERY_VERSION = QUERY + ".version";
     * public static final String QUERY_UUID = QUERY + ".uuid";
     */

}
