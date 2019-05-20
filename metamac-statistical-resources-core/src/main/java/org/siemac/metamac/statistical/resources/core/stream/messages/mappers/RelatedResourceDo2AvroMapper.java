package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;

public class RelatedResourceDo2AvroMapper {

    protected RelatedResourceDo2AvroMapper() {
    }

    public static RelatedResourceAvro do2Avro(RelatedResource source) throws MetamacException {
        if (source == null) {
            return null;
        }
        RelatedResourceAvro target = null;

        NameableStatisticalResource nameableResource = null;
        switch (source.getType()) {
            case DATASET:
                nameableResource = AvroMapperUtils.getDatasetVersionRepository().retrieveLastVersion(source.getDataset().getIdentifiableStatisticalResource().getUrn())
                        .getSiemacMetadataStatisticalResource();
                break;
            case PUBLICATION:
                nameableResource = AvroMapperUtils.getPublicationVersionRepository().retrieveLastVersion(source.getPublication().getIdentifiableStatisticalResource().getUrn())
                        .getSiemacMetadataStatisticalResource();
                break;
            case QUERY:
                nameableResource = AvroMapperUtils.getQueryVersionRepository().retrieveLastVersion(source.getQuery().getIdentifiableStatisticalResource().getUrn()).getLifeCycleStatisticalResource();
                break;
            case MULTIDATASET:
                nameableResource = AvroMapperUtils.getMultidatasetVersionRepository().retrieveLastVersion(source.getMultidataset().getIdentifiableStatisticalResource().getUrn())
                        .getSiemacMetadataStatisticalResource();
                break;
            default:
                nameableResource = RelatedResourceUtils.retrieveNameableResourceLinkedToRelatedResource(source);

        }

        target = RelatedResourceAvro.newBuilder().setCode(nameableResource.getCode()).setStatisticalOperationUrn(nameableResource.getStatisticalOperation().getUrn())
                .setTitle(InternationalStringDo2AvroMapper.do2Avro(nameableResource.getTitle())).setType(TypeRelatedResourceEnumDo2AvroMapper.do2Avro(source.getType()))
                .setUrn(nameableResource.getUrn()).build();

        return target;
    }

    public static List<RelatedResourceAvro> do2Avro(List<RelatedResource> source) throws MetamacException {
        List<RelatedResourceAvro> target = new ArrayList<RelatedResourceAvro>();
        for (RelatedResource item : source) {
            target.add(do2Avro(item));
        }
        return target;
    }

    public static RelatedResourceAvro lifecycleStatisticalResourceDoToRelatedResourceAvro(LifeCycleStatisticalResource source, TypeRelatedResourceEnum type) {

        // @formatter:off
        return RelatedResourceAvro.newBuilder()
                .setCode(source.getCode())
                .setTitle(InternationalStringDo2AvroMapper.do2Avro(source.getTitle()))
                .setType(TypeRelatedResourceEnumDo2AvroMapper.do2Avro(type))
                .setUrn(source.getUrn())
                .setStatisticalOperationUrn(source.getStatisticalOperation() != null ? source.getStatisticalOperation().getUrn() : null)
                .build();
        // @formatter:on
    }

}
