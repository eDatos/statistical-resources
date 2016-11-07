package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;

public class RelatedResourceAvroMapper {


    protected RelatedResourceAvroMapper() {
    }

    public static RelatedResourceAvro do2Avro(RelatedResource source) throws MetamacException {
        RelatedResourceAvro target = null;
        if (null != source) {
            NameableStatisticalResource nameableResource = null;
            TypeRelatedResourceEnum type = source.getType();
            switch (type) {
                case DATASET:
                    nameableResource = AvroMapperUtils.retrieveDatasetVersion(source.getDataset().getIdentifiableStatisticalResource().getUrn()).getSiemacMetadataStatisticalResource();
                    break;
                case PUBLICATION_VERSION:
                    PublicationVersion publication = source.getPublicationVersion();
                    IdentifiableStatisticalResource identifiableStatisticalResource = publication.getLifeCycleStatisticalResource();
                    String urn = identifiableStatisticalResource.getUrn();
                    PublicationVersion publicationVersion = AvroMapperUtils.retrievePublicationVersion(urn);
                    nameableResource = publicationVersion.getSiemacMetadataStatisticalResource();
                    break;
                default:
                    nameableResource = RelatedResourceUtils.retrieveNameableResourceLinkedToRelatedResource(source);

            }
            if (nameableResource != null) {
                target = RelatedResourceAvro.newBuilder()
                        .setCode(nameableResource.getCode())
                        .setStatisticalOperationUrn(nameableResource.getStatisticalOperation().getUrn())
                        .setTitle(InternationalStringAvroMapper.do2Avro(nameableResource.getTitle()))
                        .setType(TypeRelatedResourceEnumAvroMapper.do2Avro(source.getType()))
                        .setUrn(nameableResource.getUrn())
                        .build();
            }
        }
        return target;
    }

    public static RelatedResource avro2Do(RelatedResourceAvro source) throws MetamacException {
        RelatedResource target = new RelatedResource();
        target.setType(TypeRelatedResourceEnumAvroMapper.avro2Do(source.getType()));

        switch (target.getType()) {
            case DATASET:
                target.setDataset(AvroMapperUtils.retrieveDataset(source.getUrn()));
                break;
            case DATASET_VERSION:
                target.setDatasetVersion(AvroMapperUtils.retrieveDatasetVersion(source.getUrn()));
                break;
            case PUBLICATION_VERSION:
                target.setPublicationVersion(AvroMapperUtils.retrievePublicationVersion(source.getUrn()));
                break;
            default:
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of relatedResource not supported for relation with other resource");

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
