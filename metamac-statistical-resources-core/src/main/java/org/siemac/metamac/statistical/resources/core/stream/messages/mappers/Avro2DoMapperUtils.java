package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.utils.RestMapper;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationRepository;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;

public class Avro2DoMapperUtils {

    private static ConfigurationService configurationService;

    private static DatasetRepository datasetRepository;
    private static DatasetVersionRepository datasetVersionRepository;

    private static PublicationRepository publicationRepository;
    private static PublicationVersionRepository publicationVersionRepository;

    private static QueryRepository queryRepository;
    private static QueryVersionRepository queryVersionRepository;

    public static DatasetRepository getDatasetRepository() {
        if (datasetRepository == null) {
            datasetRepository = ApplicationContextProvider.getApplicationContext().getBean(DatasetRepository.class);
        }
        return datasetRepository;
    }

    public static DatasetVersionRepository getDatasetVersionRepository() {
        if (datasetVersionRepository == null) {
            datasetVersionRepository = ApplicationContextProvider.getApplicationContext().getBean(DatasetVersionRepository.class);
        }
        return datasetVersionRepository;
    }

    protected static PublicationRepository getPublicationRepository() {
        if (publicationRepository == null) {
            publicationRepository = ApplicationContextProvider.getApplicationContext().getBean(PublicationRepository.class);
        }
        return publicationRepository;
    }

    protected static PublicationVersionRepository getPublicationVersionRepository() {
        if (publicationVersionRepository == null) {
            publicationVersionRepository = ApplicationContextProvider.getApplicationContext().getBean(PublicationVersionRepository.class);
        }
        return publicationVersionRepository;
    }

    protected static QueryRepository getQueryRepository() {
        if (queryRepository == null) {
            queryRepository = ApplicationContextProvider.getApplicationContext().getBean(QueryRepository.class);
        }
        return queryRepository;
    }

    protected static QueryVersionRepository getQueryVersionRepository() {
        if (queryVersionRepository == null) {
            queryVersionRepository = ApplicationContextProvider.getApplicationContext().getBean(QueryVersionRepository.class);
        }
        return queryVersionRepository;
    }

    protected static ConfigurationService getConfigurationService() {
        if (configurationService == null) {
            configurationService = ApplicationContextProvider.getApplicationContext().getBean(ConfigurationService.class);
        }
        return configurationService;
    }

    protected static DatasetVersion retrieveDatasetVersion(String datasetVersionUrn) throws MetamacException {
        DatasetVersion target = null;
        try {
            target = getDatasetVersionRepository().retrieveByUrn(datasetVersionUrn);
        } catch (MetamacException e) {
            throw new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, "DatasetVersion of Datasource not found");
        }
        return target;
    }

    public static PublicationVersion retrievePublicationVersion(String urn) throws MetamacException {
        PublicationVersion publicationVersion = null;
        try {
            publicationVersion = getPublicationVersionRepository().retrieveByUrn(urn);
        } catch (MetamacException e) {
            throw new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND);
        }
        return publicationVersion;
    }

    public static String getSelfLink(ExternalItem source) throws MetamacException {
        RestMapper restMapper = new RestMapper();
        String statisticalResourcesApiInternalEndpointV10 = getConfigurationService().retrieveStatisticalResourcesInternalApiUrlBase();
        return restMapper.createSelfLink(source, statisticalResourcesApiInternalEndpointV10);
    }

    public static RelatedResource createRelatedResourceFromRelatedResourceResult(RelatedResourceResult source) throws MetamacException {
        RelatedResource target = new RelatedResource();
        switch (source.getType()) {
            case DATASET:
                target.setDataset(getDatasetRepository().retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.DATASET);
                break;
            case DATASET_VERSION:
                target.setDatasetVersion(getDatasetVersionRepository().retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.DATASET_VERSION);
                break;
            case PUBLICATION:
                target.setPublication(getPublicationRepository().retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.PUBLICATION);
                break;
            case PUBLICATION_VERSION:
                target.setPublicationVersion(getPublicationVersionRepository().retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.PUBLICATION_VERSION);
                break;
            case QUERY:
                target.setQuery(getQueryRepository().retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.QUERY);
                break;
            case QUERY_VERSION:
                target.setQueryVersion(getQueryVersionRepository().retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.QUERY_VERSION);
                break;
            default:
                break;
        }
        return target;
    }
}
