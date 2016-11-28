package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationRepository;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationAvro;
import org.springframework.beans.factory.annotation.Autowired;

public class PublicationAvroDo2Mapper {

    @Autowired
    static PublicationRepository PublicationRepository;

    protected PublicationAvroDo2Mapper() {
    }

    protected static List<String> getVersionsUrns(List<PublicationVersion> versions) {
        List<String> versionsString = new ArrayList<String>();
        for (PublicationVersion version : versions) {
            versionsString.add(version.getLifeCycleStatisticalResource().getUrn());
        }
        return versionsString;

    }

    public static Publication avro2Do(PublicationAvro source) throws MetamacException {
        Publication target = new Publication();
        target.setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvro2DoMapper.avro2Do(source.getIdentifiableStatisticalResource()));
        for (String urn : source.getPublicationVersionsUrns()) {
            PublicationVersion publicationVersion = AvroMapperUtils.retrievePublicationVersion(urn);
            System.out.println(publicationVersion);
            if (publicationVersion != null) {
                target.addVersion(publicationVersion);
            }
        }
        return target;
    }

}
