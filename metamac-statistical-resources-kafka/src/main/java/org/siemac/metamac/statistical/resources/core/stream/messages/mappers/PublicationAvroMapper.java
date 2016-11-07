package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationRepository;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationAvro;
import org.springframework.beans.factory.annotation.Autowired;

public class PublicationAvroMapper {

    @Autowired
    static PublicationRepository PublicationRepository;

    protected PublicationAvroMapper() {
    }

    public static PublicationAvro do2Avro(Publication source) throws MetamacException {
        PublicationAvro target = PublicationAvro.newBuilder()
               .setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvroMapper.do2Avro(source.getIdentifiableStatisticalResource()))
               .setVersion(source.getVersion())
                .setVersionsUrns(getVersionsUrns(source.getVersions()))
               .build();
        return target;
    }

    protected static List<String> getVersionsUrns(List<PublicationVersion> versions) {
        List<String> versionsString = new ArrayList<String>();
        for (PublicationVersion version : versions) {
            versionsString.add(version.getLifeCycleStatisticalResource().getUrn());
        }
        return versionsString;

    }

    protected static List<PublicationAvro> PublicationListDo2Avro(List<Publication> children) throws MetamacException {
        List<PublicationAvro> list = new ArrayList<PublicationAvro>();
        for (Publication el : children) {
            list.add(do2Avro(el));
        }
        return list;
    }

    public static Publication avro2Do(PublicationAvro source) throws MetamacException {
        Publication target = new Publication();
        target.setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvroMapper.avro2Do(source.getIdentifiableStatisticalResource()));
        target.setVersion(source.getVersion());
        for (String urn : source.getVersionsUrns()) {
            PublicationVersion publicationVersion = AvroMapperUtils.retrievePublicationVersion(urn);
            System.out.println(publicationVersion);
            if (publicationVersion != null) {
                target.addVersion(publicationVersion);
            }
        }
        return target;
    }

}
