package org.siemac.metamac.statistical.resources.core.stream.messages;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.mappers.InternationalStringDo2Avro;

public class MappersMockUtils {
    

    protected static final long                      EXPECTED_VERSION            = 33l;
    protected static final String                    EXPECTED_URI                = "EXPECTED_URI";
    protected static final String                    EXPECTED_URN                = "EXPECTED_URN";
    protected static final String                    EXPECTED_URN_PROVIDER                = "EXPECTED_URN_PROVIDER";
    protected static final TypeExternalArtefactsEnum EXPECTED_TYPE               = TypeExternalArtefactsEnum.CONFIGURATION;
    protected static final String                    EXPECTED_LOCALE             = "EXPECTED_LOCALE";
    protected static final String                    EXPECTED_LABEL              = "EXPECTED_LABEL";
    protected static final String                    EXPECTED_MANAGEMENT_APP_URL = "EXPECTED_MANAGEMENT_APP_URL";
    protected static final String                    EXPECTED_CODE_NESTED        = "EXPECTED_CODE_NESTED";
    protected static final String                    EXPECTED_CODE               = "EXPECTED_CODE";



    public static ExternalItem mockExternalItem() {
        ExternalItem source = new ExternalItem();
        source.setCode(EXPECTED_CODE);
        source.setCodeNested(EXPECTED_CODE_NESTED);
        source.setManagementAppUrl(EXPECTED_MANAGEMENT_APP_URL);

        InternationalString is = mockInternationalString();
        source.setTitle(is);

        source.setType(EXPECTED_TYPE);
        source.setUrn(EXPECTED_URN);
        source.setVersion(EXPECTED_VERSION);
        source.setUrnProvider(EXPECTED_URN_PROVIDER);

        return source;
    }
    
    
    public static ExternalItemAvro mockExternalItemAvro() {
        ExternalItemAvro expected = ExternalItemAvro.newBuilder()
                .setCode(MappersMockUtils.EXPECTED_CODE)
                .setCodeNested(MappersMockUtils.EXPECTED_CODE_NESTED)
                .setManagementAppUrl(MappersMockUtils.EXPECTED_MANAGEMENT_APP_URL)
                .setTitle(InternationalStringDo2Avro.internationalString2Avro(MappersMockUtils.mockInternationalString()))
                .setType(TypeExternalArtefactsEnumAvro.CONFIGURATION)
                .setUrn(MappersMockUtils.EXPECTED_URN)
                .setVersion(MappersMockUtils.EXPECTED_VERSION)
                .setUrnProvider(MappersMockUtils.EXPECTED_URN_PROVIDER)
                .build();
        return expected;
    }
    
    


    public static InternationalString mockInternationalString() {
        LocalisedString ls = new LocalisedString();
        ls.setLabel(EXPECTED_LABEL);
        ls.setLocale(EXPECTED_LOCALE);
        InternationalString is = new InternationalString();
        is.addText(ls);
        return is;
    }
    
    
    public static RelatedResource mockRelatedResource(String type) {
        RelatedResource target = new RelatedResource();
        switch(type) {
            case "DATASET":
                target.setVersion(EXPECTED_VERSION);
                Dataset d = new Dataset();
                d.setIdentifiableStatisticalResource(mockNameableStatisticalResource());
                target.setType(TypeRelatedResourceEnum.DATASET);
                target.setDataset(d);
                break;
        }
        return target;
    }

    
    public static RelatedResourceAvro mockRelatedResourceAvro() {
        RelatedResourceAvro target = RelatedResourceAvro.newBuilder()
                .setCode(EXPECTED_CODE)
                .setStatisticalOperationUrn(EXPECTED_URN)
                .setTitle(InternationalStringDo2Avro.internationalString2Avro(mockInternationalString()))
                .setType(EXPECTED_TYPE.getName())
                .setUrn(EXPECTED_URN)
                .build();
        return target;
    }

    
    public static NameableStatisticalResource mockNameableStatisticalResource() {
        NameableStatisticalResource target = new NameableStatisticalResource();
        target.setCode(EXPECTED_CODE);
        target.setStatisticalOperation(mockExternalItem());
        target.setTitle(mockInternationalString());
        target.setDescription(mockInternationalString());
        target.setUrn(EXPECTED_URN);
        return target;
    }

    
    public static NameableStatisticalResourceAvro mockNameableStatisticalResourceAvro() {
        NameableStatisticalResourceAvro target = NameableStatisticalResourceAvro.newBuilder()
                .setIdentifiableStatisticalResource(mockIdentifiableStatisticalResourceAvro())
                .setDescription(InternationalStringDo2Avro.internationalString2Avro(mockInternationalString()))
                .setTitle(InternationalStringDo2Avro.internationalString2Avro(mockInternationalString()))
                .build();
        return target;
    }


    public static IdentifiableStatisticalResourceAvro mockIdentifiableStatisticalResourceAvro() {
        IdentifiableStatisticalResourceAvro target = IdentifiableStatisticalResourceAvro.newBuilder()
                .setCode(EXPECTED_CODE)
                .setUrn(EXPECTED_URN)
                .build();
        return target;
    }
    

}
