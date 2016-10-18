package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
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

public class RelatedResourceAvro2Do {

    @Autowired
    private static DatasetRepository             datasetRepository;

    @Autowired
    private static PublicationRepository         publicationRepository;

    @Autowired
    private static QueryRepository               queryRepository;

    protected RelatedResourceAvro2Do() {
    }

    public static RelatedResource relatedResourceAvro2Do(RelatedResourceAvro source) throws MetamacException {
        RelatedResource target = new RelatedResource();
        target.setType(source.getType());

        switch (target.getType()) {
            case DATASET:
                target.setDataset(datasetRepository.retrieveByUrn((String)source.getUrn()));
                break;
            case PUBLICATION:
                target.setPublication(publicationRepository.retrieveByUrn((String) source.getUrn()));
                break;
            case QUERY:
                target.setQuery(queryRepository.retrieveByUrn((String) source.getUrn()));
                break;
            default:
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of relatedResource not supported for relation with other resource");

        }

        return target;
    }

}
