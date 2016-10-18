package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;
import org.springframework.beans.factory.annotation.Autowired;

public class RelatedResourceDo2Avro {
    

    @Autowired
    private static DatasetVersionRepository     datasetVersionRepository;

    @Autowired
    private static PublicationVersionRepository publicationVersionRepository;

    @Autowired
    private static QueryVersionRepository       queryVersionRepository;

    protected RelatedResourceDo2Avro() {
    }
    
    public static RelatedResourceAvro relatedResourceDo2Avro(RelatedResource source) {
        TypeRelatedResourceEnum type = source.getType();
        NameableStatisticalResource nameableResource = null;
        RelatedResourceAvro target = null;
        try {
            switch(type) {
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
        } catch (MetamacException e) {
            nameableResource = null;
        }
        if (nameableResource != null) {
            target = RelatedResourceAvro.newBuilder()
                    .setCode(nameableResource.getCode())
                    .setStatisticalOperationUrn(nameableResource.getStatisticalOperation().getUrn())
                    .setTitle(InternationalStringDo2Avro.internationalString2Avro(nameableResource.getTitle()))
                    .setType(source.getType())
                    .setUrn(nameableResource.getUrn())
                    .build();
        }
        return target;
        
    }

}
