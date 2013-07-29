package org.siemac.metamac.statistical.resources.web.client.base.utils;

import static org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS.DATE_NEXT_UPDATE;
import static org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS.GEOGRAPHIC_COVERAGE;
import static org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS.GEOGRAPHIC_GRANULARITY;
import static org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS.STATISTIC_OFFICIALITY;
import static org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS.TEMPORAL_COVERAGE;
import static org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS.TEMPORAL_GRANULARITY;
import static org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS.UPDATE_FRECUENCY;
import static org.siemac.metamac.statistical.resources.web.client.model.ds.NameableResourceDS.DESCRIPTION;
import static org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS.COMMON_METADATA;
import static org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS.CREATOR;
import static org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS.LANGUAGE;
import static org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS.LANGUAGES;
import static org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS.PUBLISHER;
import static org.siemac.metamac.statistical.resources.web.client.model.ds.VersionableResourceDS.NEXT_VERSION;
import static org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS.RELATED_DATASET_VERSION;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

/**
 * This class contain methods that, given a procStatus of a resource, return the fields that are required to go to the next procStatus.
 */
public class RequiredFieldUtils {

    // PUBLICATION VERSION

    private static final String[] publicationFieldsToProductionValidation = new String[]{DESCRIPTION, NEXT_VERSION, LANGUAGE, LANGUAGES, CREATOR, PUBLISHER, COMMON_METADATA};
    private static final String[] publicationFieldsToDiffusionValidation  = publicationFieldsToProductionValidation;
    private static final String[] publicationFieldsToPublication          = publicationFieldsToDiffusionValidation;

    public static String[] getPublicationRequiredFieldsToNextProcStatus(ProcStatusEnum currentProcStatus) {
        switch (currentProcStatus) {
            case DRAFT:
                return publicationFieldsToProductionValidation;
            case PRODUCTION_VALIDATION:
                return publicationFieldsToDiffusionValidation;
            case DIFFUSION_VALIDATION:
                return publicationFieldsToPublication;
            default:
                return new String[]{};
        }
    }

    // DATASET VERSION

    private static final String[] datasetFieldsToProductionValidation = new String[]{DESCRIPTION, NEXT_VERSION, LANGUAGE, LANGUAGES, CREATOR, PUBLISHER, COMMON_METADATA, GEOGRAPHIC_COVERAGE,
            GEOGRAPHIC_GRANULARITY, TEMPORAL_COVERAGE, TEMPORAL_GRANULARITY, DATE_NEXT_UPDATE, UPDATE_FRECUENCY, STATISTIC_OFFICIALITY};
    private static final String[] datasetFieldsToDiffusionValidation  = datasetFieldsToProductionValidation;
    private static final String[] datasetFieldsToPublication          = datasetFieldsToDiffusionValidation;

    public static String[] getDatasetRequiredFieldsToNextProcStatus(ProcStatusEnum currentProcStatus) {
        switch (currentProcStatus) {
            case DRAFT:
                return datasetFieldsToProductionValidation;
            case PRODUCTION_VALIDATION:
                return datasetFieldsToDiffusionValidation;
            case DIFFUSION_VALIDATION:
                return datasetFieldsToPublication;
            default:
                return new String[]{};
        }
    }

    // QUERY VERSION

    private static final String[] queryFieldsToProductionValidation = new String[]{DESCRIPTION, NEXT_VERSION, LANGUAGE, LANGUAGES, CREATOR, PUBLISHER, COMMON_METADATA, RELATED_DATASET_VERSION};
    private static final String[] queryFieldsToDiffusionValidation  = queryFieldsToProductionValidation;
    private static final String[] queryFieldsToPublication          = queryFieldsToDiffusionValidation;

    public static String[] getQueryRequiredFieldsToNextProcStatus(ProcStatusEnum currentProcStatus) {
        switch (currentProcStatus) {
            case DRAFT:
                return queryFieldsToProductionValidation;
            case PRODUCTION_VALIDATION:
                return queryFieldsToDiffusionValidation;
            case DIFFUSION_VALIDATION:
                return queryFieldsToPublication;
            default:
                return new String[]{};
        }
    }
}