package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.SiemacMetadataStatisticalResourceAvro;

public class SiemacMetadataStatisticalResourceAvro2DoMapper {

    protected SiemacMetadataStatisticalResourceAvro2DoMapper() {
    }

    public static void fillMetadata(SiemacMetadataStatisticalResourceAvro source, SiemacMetadataStatisticalResource target) throws MetamacException {
        LifecycleStatisticalResourceAvro2DoMapper.fillMetadata(source.getLifecycleStatisticalResource(), target);
        target.setResourceCreatedDate(DateTimeAvro2DoMapper.avro2Do(source.getResourceCreatedDate()));
        target.setLastUpdate(DateTimeAvro2DoMapper.avro2Do(source.getLastUpdate()));
        target.setNewnessUntilDate(DateTimeAvro2DoMapper.avro2Do(source.getNewnessUntilDate()));
        target.setCopyrightedDate(source.getCopyrightedDate());
        target.setLanguage(ExternalItemAvro2DoMapper.avro2Do(source.getLanguage()));
        target.setSubtitle(InternationalStringAvro2DoMapper.avro2Do(source.getSubtitle()));
        target.setTitleAlternative(InternationalStringAvro2DoMapper.avro2Do(source.getTitleAlternative()));
        target.setAbstractLogic(InternationalStringAvro2DoMapper.avro2Do(source.getAbstractLogic()));
        target.setKeywords(InternationalStringAvro2DoMapper.avro2Do(source.getKeywords()));
        target.setCommonMetadata(ExternalItemAvro2DoMapper.avro2Do(source.getCommonMetadata()));
        target.setType(StatisticalResourceTypeEnumAvro2DoMapper.avro2Do(source.getType()));
        target.setCreator(ExternalItemAvro2DoMapper.avro2Do(source.getCreator()));
        target.setConformsTo(InternationalStringAvro2DoMapper.avro2Do(source.getConformsTo()));
        target.setConformsToInternal(InternationalStringAvro2DoMapper.avro2Do(source.getConformsToInternal()));
        target.setReplaces(RelatedResourceAvro2DoMapper.avro2Do((source.getReplaces())));
        target.setIsReplacedBy(RelatedResourceAvro2DoMapper.avro2Do(source.getIsReplacedBy()));
        target.setAccessRights(InternationalStringAvro2DoMapper.avro2Do(source.getAccessRights()));
        for (ExternalItemAvro extItem : source.getLanguages()) {
            target.addLanguage(ExternalItemAvro2DoMapper.avro2Do(extItem));
        }
        for (ExternalItemAvro extItem : source.getStatisticalOperationInstances()) {
            target.addStatisticalOperationInstance(ExternalItemAvro2DoMapper.avro2Do(extItem));
        }
        for (ExternalItemAvro extItem : source.getContributors()) {
            target.addContributor(ExternalItemAvro2DoMapper.avro2Do(extItem));
        }
        for (ExternalItemAvro extItem : source.getPublishers()) {
            target.addPublisher(ExternalItemAvro2DoMapper.avro2Do(extItem));
        }
        for (ExternalItemAvro extItem : source.getPublisherContributors()) {
            target.addPublisherContributor(ExternalItemAvro2DoMapper.avro2Do(extItem));
        }
        for (ExternalItemAvro extItem : source.getMediators()) {
            target.addMediator(ExternalItemAvro2DoMapper.avro2Do(extItem));
        }
    }

    public static SiemacMetadataStatisticalResource avro2Do(SiemacMetadataStatisticalResourceAvro source) throws MetamacException {
        SiemacMetadataStatisticalResource target = new SiemacMetadataStatisticalResource();
        fillMetadata(source, target);
        return target;
    }

}
