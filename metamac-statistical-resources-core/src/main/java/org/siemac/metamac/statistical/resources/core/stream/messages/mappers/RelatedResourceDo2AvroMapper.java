package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;

public class RelatedResourceDo2AvroMapper {

    protected RelatedResourceDo2AvroMapper() {
    }

    public static RelatedResourceAvro do2Avro(RelatedResource source) throws MetamacException {
        RelatedResourceAvro target = null;
        if (null != source) {
            NameableStatisticalResource nameableResource = null;
            TypeRelatedResourceEnum type = source.getType();
            switch (type) {
                case DATASET:
                    nameableResource = Avro2DoMapperUtils.retrieveDatasetVersion(source.getDataset().getIdentifiableStatisticalResource().getUrn()).getSiemacMetadataStatisticalResource();
                    break;
                case PUBLICATION_VERSION:
                    PublicationVersion publication = source.getPublicationVersion();
                    IdentifiableStatisticalResource identifiableStatisticalResource = publication.getLifeCycleStatisticalResource();
                    String urn = identifiableStatisticalResource.getUrn();
                    PublicationVersion publicationVersion = Avro2DoMapperUtils.retrievePublicationVersion(urn);
                    nameableResource = publicationVersion.getSiemacMetadataStatisticalResource();
                    break;
                default:
                    nameableResource = RelatedResourceUtils.retrieveNameableResourceLinkedToRelatedResource(source);

            }
            if (nameableResource != null) {
                target = RelatedResourceAvro.newBuilder().setCode(nameableResource.getCode()).setStatisticalOperationUrn(nameableResource.getStatisticalOperation().getUrn())
                        .setTitle(InternationalStringDo2AvroMapper.do2Avro(nameableResource.getTitle())).setType(TypeRelatedResourceEnumDo2AvroMapper.do2Avro(source.getType()))
                        .setUrn(nameableResource.getUrn()).build();
            }
        }
        return target;
    }

    public static List<RelatedResourceAvro> do2Avro(List<RelatedResource> source) throws MetamacException {
        List<RelatedResourceAvro> target = new ArrayList<RelatedResourceAvro>();
        for (RelatedResource item : source) {
            target.add(do2Avro(item));
        }
        return target;
    }

}
