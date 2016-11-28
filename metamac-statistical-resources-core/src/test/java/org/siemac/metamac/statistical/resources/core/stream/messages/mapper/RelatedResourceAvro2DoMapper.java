package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;

public class RelatedResourceAvro2DoMapper {

    protected RelatedResourceAvro2DoMapper() {
    }

    public static RelatedResource avro2Do(RelatedResourceAvro source) throws MetamacException {
        RelatedResource target = new RelatedResource();
        target.setType(TypeRelatedResourceEnumAvro2DoMapper.avro2Do(source.getType()));

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

}
