package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.SiemacMetadataStatisticalResourceAvro;

public class SiemacMetadataStatisticalResourceAvroMapper {

    protected SiemacMetadataStatisticalResourceAvroMapper() {
    }

    public static void fillMetadata(SiemacMetadataStatisticalResourceAvro source, SiemacMetadataStatisticalResource target) throws MetamacException {
        LifecycleStatisticalResourceAvroMapper.fillMetadata(source.getLifecycleStatisticalResource(), target);
        target.setUserModifiedKeywords(source.getUserModifiedKeywords());
        target.setResourceCreatedDate(DatetimeAvroMapper.avro2Do(source.getResourceCreatedDate()));
        target.setLastUpdate(DatetimeAvroMapper.avro2Do(source.getLastUpdate()));
        target.setNewnessUntilDate(DatetimeAvroMapper.avro2Do(source.getNewnessUntilDate()));
        target.setCopyrightedDate(source.getCopyrightedDate());
        target.setLanguage(ExternalItemAvroMapper.avro2Do(source.getLanguage()));
        target.setSubtitle(InternationalStringAvroMapper.avro2Do(source.getSubtitle()));
        target.setTitleAlternative(InternationalStringAvroMapper.avro2Do(source.getTitleAlternative()));
        target.setAbstractLogic(InternationalStringAvroMapper.avro2Do(source.getAbstractLogic()));
        target.setKeywords(InternationalStringAvroMapper.avro2Do(source.getKeywords()));
        target.setCommonMetadata(ExternalItemAvroMapper.avro2Do(source.getCommonMetadata()));
        target.setType(StatisticalResourceTypeEnumAvroMapper.avro2Do(source.getType()));
        target.setCreator(ExternalItemAvroMapper.avro2Do(source.getCreator()));
        target.setConformsTo(InternationalStringAvroMapper.avro2Do(source.getConformsTo()));
        target.setConformsToInternal(InternationalStringAvroMapper.avro2Do(source.getConformsToInternal()));
        target.setReplaces(RelatedResourceAvroMapper.avro2Do((source.getReplaces())));
        // TODO ---> Pendiente de merge de master target.setIsReplacedBy(RelatedResourceAvro2Do.relatedResourceAvro2Do(source.getIsReplacedBy()));
        target.setAccessRights(InternationalStringAvroMapper.avro2Do(source.getAccessRights()));
        for (ExternalItemAvro extItem : source.getLanguages()) {
            target.addLanguage(ExternalItemAvroMapper.avro2Do(extItem));
        }
        for (ExternalItemAvro extItem : source.getStatisticalOperationInstances()) {
            target.addStatisticalOperationInstance(ExternalItemAvroMapper.avro2Do(extItem));
        }
        for (ExternalItemAvro extItem : source.getContributors()) {
            target.addContributor(ExternalItemAvroMapper.avro2Do(extItem));
        }
        for (ExternalItemAvro extItem : source.getPublishers()) {
            target.addPublisher(ExternalItemAvroMapper.avro2Do(extItem));
        }
        for (ExternalItemAvro extItem : source.getPublisherContributors()) {
            target.addPublisherContributor(ExternalItemAvroMapper.avro2Do(extItem));
        }
        for (ExternalItemAvro extItem : source.getMediators()) {
            target.addMediator(ExternalItemAvroMapper.avro2Do(extItem));
        }
    }

    public static SiemacMetadataStatisticalResource avro2Do(SiemacMetadataStatisticalResourceAvro source) throws MetamacException {
        SiemacMetadataStatisticalResource target = new SiemacMetadataStatisticalResource();
        fillMetadata(source, target);
        return target;
    }


    public static SiemacMetadataStatisticalResourceAvro do2Avro(SiemacMetadataStatisticalResource source) throws MetamacException {
        SiemacMetadataStatisticalResourceAvro target = SiemacMetadataStatisticalResourceAvro.newBuilder()
                .setLifecycleStatisticalResource(LifecycleStatisticalResourceAvroMapper.do2Avro(source))
                .setUserModifiedKeywords(source.getUserModifiedKeywords())
                .setResourceCreatedDate(DatetimeAvroMapper.do2Avro(source.getResourceCreatedDate()))
                .setLastUpdate(DatetimeAvroMapper.do2Avro(source.getLastUpdate()))
                .setNewnessUntilDate(DatetimeAvroMapper.do2Avro(source.getNewnessUntilDate()))
                .setCopyrightedDate(source.getCopyrightedDate())
                .setLanguage(ExternalItemAvroMapper.do2Avro(source.getLanguage()))
                .setSubtitle(InternationalStringAvroMapper.do2Avro(source.getSubtitle()))
                .setTitleAlternative(InternationalStringAvroMapper.do2Avro(source.getTitleAlternative()))
                .setAbstractLogic(InternationalStringAvroMapper.do2Avro(source.getAbstractLogic()))
                .setKeywords(InternationalStringAvroMapper.do2Avro(source.getKeywords()))
                .setCommonMetadata(ExternalItemAvroMapper.do2Avro(source.getCommonMetadata()))
                .setType(StatisticalResourceTypeEnumAvroMapper.do2Avro(source.getType()))
                .setCreator(ExternalItemAvroMapper.do2Avro(source.getCreator()))
                .setConformsTo(InternationalStringAvroMapper.do2Avro(source.getConformsTo()))
                .setConformsToInternal(InternationalStringAvroMapper.do2Avro(source.getConformsToInternal()))
                .setReplaces(RelatedResourceAvroMapper.do2Avro((source.getReplaces())))
                // TODO ---> Pendiente de merge de master target.setIsReplacedBy(RelatedResourceAvro2Do.relatedResourceAvro2Do(source.getIsReplacedBy()));
                .setAccessRights(InternationalStringAvroMapper.do2Avro(source.getAccessRights()))
                .setLanguages(generateListOfLanguages(source))
                .setStatisticalOperationInstances(generateListOfStatisticalOperationInstances(source))
                .setContributors(generateListOfContributors(source))
                .setPublishers(generateListOfPublishers(source))
                .setPublisherContributors(generateListOfPublishersContributors(source))
                .setMediators(generateListOfMediators(source))
                .build();
        return target;

    }

    protected static List<ExternalItemAvro> generateListOfLanguages(SiemacMetadataStatisticalResource source) {
        List<ExternalItemAvro> list = generateListGeneric(source.getLanguages());
        return list;
    }

    protected static List<ExternalItemAvro> generateListOfStatisticalOperationInstances(SiemacMetadataStatisticalResource source) {
        List<ExternalItemAvro> list = generateListGeneric(source.getStatisticalOperationInstances());
        return list;
    }

    protected static List<ExternalItemAvro> generateListOfContributors(SiemacMetadataStatisticalResource source) {
        List<ExternalItemAvro> list = generateListGeneric(source.getContributor());
        return list;
    }

    protected static List<ExternalItemAvro> generateListOfPublishers(SiemacMetadataStatisticalResource source) {
        List<ExternalItemAvro> list = generateListGeneric(source.getPublisher());
        return list;
    }

    protected static List<ExternalItemAvro> generateListOfPublishersContributors(SiemacMetadataStatisticalResource source) {
        List<ExternalItemAvro> list = generateListGeneric(source.getPublisherContributor());
        return list;
    }

    protected static List<ExternalItemAvro> generateListOfMediators(SiemacMetadataStatisticalResource source) {
        List<ExternalItemAvro> list = generateListGeneric(source.getMediator());
        return list;
    }

    private static List<ExternalItemAvro> generateListGeneric(List<ExternalItem> source) {
        List<ExternalItemAvro> list = new ArrayList<ExternalItemAvro>();
        for (ExternalItem extItem : source) {
            list.add(ExternalItemAvroMapper.do2Avro(extItem));
        }
        return list;
    }


}
