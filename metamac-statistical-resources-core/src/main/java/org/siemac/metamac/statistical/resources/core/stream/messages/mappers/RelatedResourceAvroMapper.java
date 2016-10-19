package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationRepository;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;
import org.springframework.beans.factory.annotation.Autowired;

public class RelatedResourceAvroMapper {

    @Autowired
    private static DatasetRepository            datasetRepository;

    @Autowired
    private static PublicationRepository        publicationRepository;

    @Autowired
    private static QueryRepository              queryRepository;

    @Autowired
    private static PublicationVersionRepository publicationVersionRepository;

    @Autowired
    private static DatasetVersionRepository     datasetVersionRepository;

    @Autowired
    private static QueryVersionRepository       queryVersionRepository;

    protected RelatedResourceAvroMapper() {
    }

    public static RelatedResourceAvro do2Avro(RelatedResource source) throws MetamacException {
        TypeRelatedResourceEnum type = source.getType();
        NameableStatisticalResource nameableResource = null;
        RelatedResourceAvro target = null;
        switch (type) {
            case DATASET:
                nameableResource = datasetVersionRepository.retrieveLastVersion(source.getDataset().getIdentifiableStatisticalResource().getUrn()).getSiemacMetadataStatisticalResource();
                break;
            case PUBLICATION:
                nameableResource = publicationVersionRepository.retrieveLastVersion(source.getPublication().getIdentifiableStatisticalResource().getUrn()).getSiemacMetadataStatisticalResource();
                break;
            case QUERY:
                nameableResource = queryVersionRepository.retrieveLastVersion(source.getQuery().getIdentifiableStatisticalResource().getUrn()).getLifeCycleStatisticalResource();
                break;
            default:
                nameableResource = RelatedResourceUtils.retrieveNameableResourceLinkedToRelatedResource(source);

        }
        if (nameableResource != null) {
            target = RelatedResourceAvro.newBuilder()
                    .setCode(nameableResource.getCode())
                    .setStatisticalOperationUrn(nameableResource.getStatisticalOperation().getUrn())
                    .setTitle(InternationalStringAvroMapper.do2Avro(nameableResource.getTitle()))
                    .setType(source.getType())
                    .setUrn(nameableResource.getUrn())
                    .build();
        }
        return target;
    }

    public static RelatedResource avro2Do(RelatedResourceAvro source) throws MetamacException {
        RelatedResource target = new RelatedResource();
        target.setType(source.getType());

        switch (target.getType()) {
            case DATASET:
                target.setDataset(datasetRepository.retrieveByUrn(source.getUrn()));
                break;
            case PUBLICATION:
                target.setPublication(publicationRepository.retrieveByUrn(source.getUrn()));
                break;
            case QUERY:
                target.setQuery(queryRepository.retrieveByUrn(source.getUrn()));
                break;
            case PUBLICATION_VERSION:
                target.setPublicationVersion(publicationVersionRepository.retrieveByUrn(source.getUrn()));
                break;
            case DATASET_VERSION:
                target.setDatasetVersion(datasetVersionRepository.retrieveByUrn(source.getUrn()));
                break;
            case QUERY_VERSION:
                target.setQueryVersion(queryVersionRepository.retrieveByUrn(source.getUrn()));
                break;
            default:
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of relatedResource not supported for relation with other resource");

        }

        return target;
    }

    protected static void setDatasetRepo(DatasetRepository repo) {
        datasetRepository = repo;
    }

    protected static void setDatasetVersionRepo(DatasetVersionRepository repo) {
        datasetVersionRepository = repo;
    }

}
