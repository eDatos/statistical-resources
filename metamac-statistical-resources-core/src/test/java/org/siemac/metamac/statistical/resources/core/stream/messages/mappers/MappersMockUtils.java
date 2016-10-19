package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;

public class MappersMockUtils {

    private static final TypeRelatedResourceEnum     EXPECTED_RELATED_RESOURCE_TYPE = TypeRelatedResourceEnum.DATASET;
    protected static final ProcStatusEnum            PRODUCTION_VALIDATION          = ProcStatusEnum.PRODUCTION_VALIDATION;
    protected static final boolean                   EXPECTED_LAST_VERSION          = true;
    protected static final String                    EXPECTED_USER                  = "Expected User Name";
    protected static final NextVersionTypeEnum       EXPECTED_NEXT_VERSION_TYPE     = NextVersionTypeEnum.NON_SCHEDULED_UPDATE;
    protected static final DateTime                  EXPECTED_PAST_DATE             = new DateTime(2016, 06, 01, 0, 0, 0, 0).minusDays(30);
    protected static final DateTime                  EXPECTED_FUTURE_DATE           = new DateTime(2016, 06, 01, 0, 0, 0, 0).plusDays(30);
    protected static final String                    EXPECTED_VERSION_LOGIC         = "EXPECTED_VERSION_LOGIC";
    protected static final long                      EXPECTED_VERSION               = 33l;
    protected static final String                    EXPECTED_URI                   = "EXPECTED_URI";
    protected static final String                    EXPECTED_URN                   = "EXPECTED_URN";
    protected static final String                    EXPECTED_URN_PROVIDER          = "EXPECTED_URN_PROVIDER";
    protected static final TypeExternalArtefactsEnum EXPECTED_TYPE                  = TypeExternalArtefactsEnum.CONFIGURATION;
    protected static final String                    EXPECTED_LOCALE                = "EXPECTED_LOCALE";
    protected static final String                    EXPECTED_LABEL                 = "EXPECTED_LABEL";
    protected static final String                    EXPECTED_MANAGEMENT_APP_URL    = "EXPECTED_MANAGEMENT_APP_URL";
    protected static final String                    EXPECTED_CODE_NESTED           = "EXPECTED_CODE_NESTED";
    protected static final String                    EXPECTED_CODE                  = "EXPECTED_CODE";

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
        ExternalItemAvro expected = ExternalItemAvro.newBuilder().setCode(MappersMockUtils.EXPECTED_CODE).setCodeNested(MappersMockUtils.EXPECTED_CODE_NESTED)
                .setManagementAppUrl(MappersMockUtils.EXPECTED_MANAGEMENT_APP_URL).setTitle(InternationalStringAvroMapper.do2Avro(MappersMockUtils.mockInternationalString())).setType(EXPECTED_TYPE)
                .setUrn(MappersMockUtils.EXPECTED_URN).setVersion(MappersMockUtils.EXPECTED_VERSION).setUrnProvider(MappersMockUtils.EXPECTED_URN_PROVIDER).build();
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

    public static RelatedResource mockRelatedResource(TypeRelatedResourceEnum type) {
        RelatedResource target = new RelatedResource();
        target.setVersion(EXPECTED_VERSION);
        target.setType(type);
        switch (type) {
            case DATASET:
                Dataset dataset = mockDataset();
                target.setDataset(dataset);
                break;
            case DATASET_VERSION:
                DatasetVersion datasetVersion = mockDatasetVersion();
                target.setDatasetVersion(datasetVersion);
        }
        return target;
    }

    public static RelatedResourceAvro mockRelatedResourceAvro(TypeRelatedResourceEnum type) {
        RelatedResourceAvro target = RelatedResourceAvro.newBuilder().setCode(EXPECTED_CODE).setStatisticalOperationUrn(EXPECTED_URN)
                .setTitle(InternationalStringAvroMapper.do2Avro(mockInternationalString())).setType(type).setUrn(EXPECTED_URN).build();
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
        NameableStatisticalResourceAvro target = NameableStatisticalResourceAvro.newBuilder().setIdentifiableStatisticalResource(mockIdentifiableStatisticalResourceAvro())
                .setDescription(InternationalStringAvroMapper.do2Avro(mockInternationalString())).setTitle(InternationalStringAvroMapper.do2Avro(mockInternationalString())).build();
        return target;
    }

    public static IdentifiableStatisticalResourceAvro mockIdentifiableStatisticalResourceAvro() {
        IdentifiableStatisticalResourceAvro target = IdentifiableStatisticalResourceAvro.newBuilder().setCode(EXPECTED_CODE).setUrn(EXPECTED_URN).build();
        return target;
    }

    public static IdentifiableStatisticalResource mockIdentifiableStatisticalResource() {
        IdentifiableStatisticalResource target = new IdentifiableStatisticalResource();
        target.setCode(EXPECTED_CODE);
        target.setUrn(EXPECTED_URN);
        return target;
    }

    public static VersionableStatisticalResourceAvro mockVersionableStatisticalResourceAvro() {
        VersionableStatisticalResourceAvro target = VersionableStatisticalResourceAvro.newBuilder().setNameableStatisticalResource(mockNameableStatisticalResourceAvro())
                .setNextVersion(EXPECTED_NEXT_VERSION_TYPE).setNextVersionDate(EXPECTED_FUTURE_DATE).setValidFrom(EXPECTED_PAST_DATE)
                .setVersionRationale(InternationalStringAvroMapper.do2Avro(mockInternationalString())).setValidTo(EXPECTED_FUTURE_DATE).setVersionLogic(EXPECTED_VERSION_LOGIC).build();
        return target;
    }

    public static VersionableStatisticalResource mockVersionableStatisticalResource() {
        VersionableStatisticalResource target = new VersionableStatisticalResource();
        target.setCode(EXPECTED_CODE);
        target.setStatisticalOperation(mockExternalItem());
        target.setTitle(mockInternationalString());
        target.setDescription(mockInternationalString());
        target.setUrn(EXPECTED_URN);
        target.setNextVersionDate(EXPECTED_FUTURE_DATE);
        target.setValidFrom(EXPECTED_PAST_DATE);
        target.setValidTo(EXPECTED_FUTURE_DATE);
        target.setVersionRationale(mockInternationalString());
        target.setNextVersion(EXPECTED_NEXT_VERSION_TYPE);
        target.setVersionLogic(EXPECTED_VERSION_LOGIC);
        return target;
    }

    public static LifeCycleStatisticalResource mockLifeCycleStatisticalResource(TypeRelatedResourceEnum type) {
        LifeCycleStatisticalResource target = new LifeCycleStatisticalResource();
        target.setCode(EXPECTED_CODE);
        target.setStatisticalOperation(mockExternalItem());
        target.setTitle(mockInternationalString());
        target.setDescription(mockInternationalString());
        target.setUrn(EXPECTED_URN);
        target.setNextVersionDate(EXPECTED_FUTURE_DATE);
        target.setValidFrom(EXPECTED_PAST_DATE);
        target.setValidTo(EXPECTED_FUTURE_DATE);
        target.setVersionRationale(mockInternationalString());
        target.setNextVersion(EXPECTED_NEXT_VERSION_TYPE);
        target.setVersionLogic(EXPECTED_VERSION_LOGIC);

        target.setCreationDate(EXPECTED_PAST_DATE);
        target.setCreationUser(EXPECTED_USER + "Creation");
        target.setProductionValidationDate(EXPECTED_PAST_DATE);
        target.setProductionValidationUser(EXPECTED_USER + "ProductionValidation");
        target.setDiffusionValidationDate(EXPECTED_PAST_DATE);
        target.setDiffusionValidationUser(EXPECTED_USER + "DiffusionValidation");
        target.setRejectValidationDate(EXPECTED_PAST_DATE);
        target.setRejectValidationUser(EXPECTED_USER + "RejectValidation");
        target.setPublicationDate(EXPECTED_PAST_DATE);
        target.setPublicationUser(EXPECTED_USER + "Publication");

        target.setLastVersion(EXPECTED_LAST_VERSION);
        target.setProcStatus(PRODUCTION_VALIDATION);
        target.setReplacesVersion(mockRelatedResource(type));
        target.setMaintainer(mockExternalItem());

        return target;
    }

    public static LifecycleStatisticalResourceAvro mockLifeCycleStatisticalResourceAvro(TypeRelatedResourceEnum replacesVersionType) {
        LifecycleStatisticalResourceAvro target = LifecycleStatisticalResourceAvro.newBuilder()
                .setVersionableStatisticalResource(mockVersionableStatisticalResourceAvro())
                .setCreationDate(EXPECTED_PAST_DATE)
                .setCreationUser(EXPECTED_USER + "Creation")
                .setProductionValidationDate(EXPECTED_PAST_DATE)
                .setProductionValidationUser(EXPECTED_USER + "ProductionValidation")
                .setDiffusionValidationDate(EXPECTED_PAST_DATE)
                .setDiffusionValidationUser(EXPECTED_USER + "DiffusionValidation")
                .setRejectValidationDate(EXPECTED_PAST_DATE)
                .setRejectValidationUser(EXPECTED_USER + "RejectValidation")
                .setPublicationDate(EXPECTED_PAST_DATE)
                .setPublicationUser(EXPECTED_USER + "Publication")
                .setLastVersion(EXPECTED_LAST_VERSION)
                .setProcStatus(PRODUCTION_VALIDATION)
                .setReplacesVersion(mockRelatedResourceAvro(replacesVersionType))
                .setMaintainer(mockExternalItemAvro()).build();
        return target;
    }

    public static Dataset mockDataset() {
        Dataset dataset = new Dataset();
        dataset.setIdentifiableStatisticalResource(mockNameableStatisticalResource());
        dataset.getIdentifiableStatisticalResource().setCode(EXPECTED_CODE);
        return dataset;
    }

    public static DatasetVersion mockDatasetVersion() {
        DatasetVersion d = new DatasetVersion();
        SiemacMetadataStatisticalResource siemac = new SiemacMetadataStatisticalResource();
        siemac.setCode(EXPECTED_CODE);
        siemac.setStatisticalOperation(mockExternalItem());
        siemac.setTitle(mockInternationalString());
        siemac.setUrn(EXPECTED_URN);
        d.setSiemacMetadataStatisticalResource(siemac);

        return d;
    }

}
