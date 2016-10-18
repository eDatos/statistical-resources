package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;

public class ExternalItemDo2Avro {

    protected ExternalItemDo2Avro() {
    }
    
    public static ExternalItemAvro externalItemDo2Avro(ExternalItem ei) {
        ExternalItemAvro target = ExternalItemAvro.newBuilder()
                .setCode(ei.getCode())
                .setCodeNested(ei.getCodeNested())
                .setManagementAppUrl(ei.getManagementAppUrl())
                .setTitle(InternationalStringDo2Avro.internationalString2Avro(ei.getTitle()))
                .setType(ei.getType())
                .setUrn(ei.getUrn())
                .setUrnProvider(ei.getUrnProvider())
                .setVersion(ei.getVersion())
                .build();
        return target;
        
    }
    


}
