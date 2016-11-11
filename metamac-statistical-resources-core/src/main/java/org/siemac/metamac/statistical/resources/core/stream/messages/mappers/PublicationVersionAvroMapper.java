package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;

public class PublicationVersionAvroMapper {


    protected PublicationVersionAvroMapper() {
    }

    public static PublicationVersionAvro do2Avro(PublicationVersion source) throws MetamacException {
        PublicationVersionAvro target = PublicationVersionAvro.newBuilder()
               .setSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResourceAvroMapper.do2Avro(source.getSiemacMetadataStatisticalResource()))
               .setHasPart(RelatedResourceAvroMapper.do2Avro(source.getHasPart()))
               .setPublication(PublicationAvroMapper.do2Avro(source.getPublication()))
               .build();
        return target;
    }

    protected static List<PublicationVersionAvro> PublicationVersionListDo2Avro(List<PublicationVersion> children) throws MetamacException {
        List<PublicationVersionAvro> list = new ArrayList<PublicationVersionAvro>();
        for (PublicationVersion el : children) {
            list.add(do2Avro(el));
        }
        return list;
    }

    public static PublicationVersion avro2Do(PublicationVersionAvro source) throws MetamacException {
        PublicationVersion target = new PublicationVersion();
        target.setSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResourceAvroMapper.avro2Do(source.getSiemacMetadataStatisticalResource()));
        for (RelatedResourceAvro children : source.getHasPart()) {
            target.addHasPart(RelatedResourceAvroMapper.avro2Do(children));
        }
        target.setPublication(PublicationAvroMapper.avro2Do(source.getPublication()));
        return target;
    }

}
