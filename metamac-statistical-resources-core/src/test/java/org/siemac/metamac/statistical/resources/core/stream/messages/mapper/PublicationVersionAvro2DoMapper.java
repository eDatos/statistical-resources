package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;

public class PublicationVersionAvro2DoMapper {

    protected PublicationVersionAvro2DoMapper() {
    }

    public static PublicationVersion avro2Do(PublicationVersionAvro source) throws MetamacException {
        PublicationVersion target = new PublicationVersion();
        target.setSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResourceAvro2DoMapper.avro2Do(source.getSiemacMetadataStatisticalResource()));
        for (RelatedResourceAvro children : source.getHasPart()) {
            target.addHasPart(RelatedResourceAvro2DoMapper.avro2Do(children));
        }
        target.setPublication(PublicationAvroDo2Mapper.avro2Do(source.getPublication()));
        return target;
    }

}
