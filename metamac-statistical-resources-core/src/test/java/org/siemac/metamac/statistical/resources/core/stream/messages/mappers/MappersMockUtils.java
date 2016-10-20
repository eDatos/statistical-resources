package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

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
import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValue;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.AttributeValueAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.CategorisationAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.CodeDimensionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.SiemacMetadataStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticOfficialityAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;

public class MappersMockUtils {

    protected static final String                    EXPECTED_TITLE                 = "EXPECTED_TITLE";
    protected static final String EXPECTED_FILENAME = "EXPECTED_FILENAME";
    protected static final String                    EXPECTED_IDENTIFIER            = "EXPECTED_IDENTIFIER";
    protected static final TypeRelatedResourceEnum   EXPECTED_RELATED_RESOURCE_TYPE = TypeRelatedResourceEnum.DATASET;
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
    private static final int EXPECTED_COPYRIGHT = 0;

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

    private static InternationalStringAvro mockInternationalStringAvro() {
        List<InternationalStringItemAvro> list = new ArrayList<>();
        InternationalStringItemAvro item = InternationalStringItemAvro.newBuilder().setLabel(EXPECTED_LABEL).setLocale(EXPECTED_LOCALE).build();
        list.add(item);
        InternationalStringAvro target = InternationalStringAvro.newBuilder().setLocalisedStrings(list).build();
        return target;
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
            default:
                break;
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
        NameableStatisticalResourceAvro target = NameableStatisticalResourceAvro.newBuilder()
                .setIdentifiableStatisticalResource(mockIdentifiableStatisticalResourceAvro())
                .setDescription(InternationalStringAvroMapper.do2Avro(mockInternationalString()))
                .setTitle(InternationalStringAvroMapper.do2Avro(mockInternationalString()))
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

    public static IdentifiableStatisticalResource mockIdentifiableStatisticalResource() {
        IdentifiableStatisticalResource target = new IdentifiableStatisticalResource();
        target.setCode(EXPECTED_CODE);
        target.setUrn(EXPECTED_URN);
        return target;
    }

    public static VersionableStatisticalResourceAvro mockVersionableStatisticalResourceAvro() {
        VersionableStatisticalResourceAvro target = VersionableStatisticalResourceAvro.newBuilder()
                .setNameableStatisticalResource(mockNameableStatisticalResourceAvro())
                .setNextVersion(EXPECTED_NEXT_VERSION_TYPE)
                .setNextVersionDate(EXPECTED_FUTURE_DATE)
                .setValidFrom(EXPECTED_PAST_DATE)
                .setVersionRationale(InternationalStringAvroMapper.do2Avro(mockInternationalString()))
                .setValidTo(EXPECTED_FUTURE_DATE)
                .setVersionLogic(EXPECTED_VERSION_LOGIC)
                .build();
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

    public static SiemacMetadataStatisticalResourceAvro mockSiemacMetadataStatisticalResourceAvro(TypeRelatedResourceEnum type) {
        List<ExternalItemAvro> listExternalItemAvro = mockListExternalItemAvro();
        SiemacMetadataStatisticalResourceAvro target = SiemacMetadataStatisticalResourceAvro.newBuilder()
                .setLifecycleStatisticalResource(mockLifeCycleStatisticalResourceAvro(EXPECTED_RELATED_RESOURCE_TYPE))
                .setAbstractLogic(mockInternationalStringAvro())
                .setAccessRights(mockInternationalStringAvro())
                .setCommonMetadata(mockExternalItemAvro())
                .setConformsTo(mockInternationalStringAvro())
                .setConformsToInternal(mockInternationalStringAvro())
                .setContributors(listExternalItemAvro)
                .setCopyrightedDate(EXPECTED_COPYRIGHT)
                .setCreator(mockExternalItemAvro())
                .setKeywords(mockInternationalStringAvro())
                .setLanguage(mockExternalItemAvro())
                .setLanguages(listExternalItemAvro)
                .setLastUpdate(EXPECTED_PAST_DATE)
                .setMediators(listExternalItemAvro)
                .setNewnessUntilDate(EXPECTED_FUTURE_DATE)
                .setPublisherContributors(listExternalItemAvro)
                .setPublishers(listExternalItemAvro)
                .setReplaces(mockRelatedResourceAvro(type))
                .setResourceCreatedDate(EXPECTED_PAST_DATE)
                .setStatisticalOperationInstances(listExternalItemAvro)
                .setSubtitle(mockInternationalStringAvro())
                .setTitleAlternative(mockInternationalStringAvro())
                .setType(StatisticalResourceTypeEnum.COLLECTION)
                .setUserModifiedKeywords(true)
                .build();
        return target;
    }

    public static SiemacMetadataStatisticalResource mockSiemacMetadataStatisticalResource(TypeRelatedResourceEnum type) {
        List<ExternalItem> listExternalItem = mockListExternalItem();
        SiemacMetadataStatisticalResource target = new SiemacMetadataStatisticalResource();
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
        target.setAbstractLogic(mockInternationalString());
        target.setAccessRights(mockInternationalString());
        target.setCommonMetadata(mockExternalItem());
        target.setConformsTo(mockInternationalString());
        target.setConformsToInternal(mockInternationalString());
        target.setCopyrightedDate(EXPECTED_COPYRIGHT);
        target.setCreator(mockExternalItem());
        target.setKeywords(mockInternationalString());
        target.setLanguage(mockExternalItem());
        target.setLastUpdate(EXPECTED_PAST_DATE);
        target.setNewnessUntilDate(EXPECTED_FUTURE_DATE);
        target.setReplaces(mockRelatedResource(type));
        target.setResourceCreatedDate(EXPECTED_PAST_DATE);
        target.setSubtitle(mockInternationalString());
        target.setTitleAlternative(mockInternationalString());
        target.setType(StatisticalResourceTypeEnum.COLLECTION);
        target.setUserModifiedKeywords(true);
        listExternalItem.forEach(item -> {
            target.addContributor(item);
            target.addLanguage(item);
            target.addMediator(item);
            target.addPublisher(item);
            target.addPublisherContributor(item);
            target.addStatisticalOperationInstance(item);
        });

        return target;
    }

    public static List<ExternalItem> mockListExternalItem() {
        List<ExternalItem> listExternalItem = new ArrayList<>();
        listExternalItem.add(mockExternalItem());
        listExternalItem.add(mockExternalItem());
        return listExternalItem;
    }

    public static List<ExternalItemAvro> mockListExternalItemAvro() {
        List<ExternalItemAvro> listExternalItemAvro = new ArrayList<>();
        listExternalItemAvro.add(mockExternalItemAvro());
        listExternalItemAvro.add(mockExternalItemAvro());
        return listExternalItemAvro;
    }

    public static StatisticOfficialityAvro mockStatisticOfficialityAvro() {
        StatisticOfficialityAvro target = StatisticOfficialityAvro.newBuilder().setDescription(mockInternationalStringAvro()).setIdentifier(EXPECTED_IDENTIFIER).setVersion(EXPECTED_VERSION).build();
        return target;
    }

    public static StatisticOfficiality mockStatisticOfficiality() {
        StatisticOfficiality target = new StatisticOfficiality();
        target.setDescription(mockInternationalString());
        target.setIdentifier(EXPECTED_IDENTIFIER);
        target.setVersion(EXPECTED_VERSION);
        return target;
    }

    public static DatasourceAvro mockDatasourceAvro() {
        DatasourceAvro target = DatasourceAvro.newBuilder()
                .setDatasetVersionUrn(EXPECTED_URN)
                .setDateNextUpdate(EXPECTED_FUTURE_DATE)
                .setFileName(EXPECTED_FILENAME)
                .setIdentifiableStatisticalResource(mockIdentifiableStatisticalResourceAvro())
                .setVersion(EXPECTED_VERSION)
                .build();
        return target;
    }

    public static Datasource mockDatasource() {
        Datasource target = new Datasource();
        target.setDatasetVersion(mockDatasetVersion());
        target.setDateNextUpdate(EXPECTED_FUTURE_DATE);
        target.setFilename(EXPECTED_FILENAME);
        target.setIdentifiableStatisticalResource(mockIdentifiableStatisticalResource());
        target.setVersion(EXPECTED_VERSION);
        return target;
    }

    public static CodeDimensionAvro mockCodeDimensionAvro() {
        CodeDimensionAvro target = CodeDimensionAvro.newBuilder()
                .setDatasetVersionUrn(EXPECTED_URN)
                .setDsdComponentId(EXPECTED_IDENTIFIER)
                .setIdentifier(EXPECTED_IDENTIFIER)
                .setTitle(EXPECTED_TITLE)
                .setVersion(EXPECTED_VERSION)
                .build();
        return target;
    }

    public static CodeDimension mockCodeDimension() {
        CodeDimension target = new CodeDimension();
        target.setDatasetVersion(mockDatasetVersion());
        target.setDsdComponentId(EXPECTED_IDENTIFIER);
        target.setIdentifier(EXPECTED_IDENTIFIER);
        target.setTitle(EXPECTED_TITLE);
        target.setVersion(EXPECTED_VERSION);
        return target;
    }


    public static AttributeValueAvro mockAttributeValueAvro() {
        AttributeValueAvro target = AttributeValueAvro.newBuilder()
                .setDatasetVersionUrn(EXPECTED_URN)
                .setDsdComponentId(EXPECTED_IDENTIFIER)
                .setIdentifier(EXPECTED_IDENTIFIER)
                .setTitle(EXPECTED_TITLE)
                .setVersion(EXPECTED_VERSION)
                .build();
        return target;
    }

    public static AttributeValue mockAttributeValue() {
        AttributeValue target = new AttributeValue();
        target.setDatasetVersion(mockDatasetVersion());
        target.setDsdComponentId(EXPECTED_IDENTIFIER);
        target.setIdentifier(EXPECTED_IDENTIFIER);
        target.setTitle(EXPECTED_TITLE);
        target.setVersion(EXPECTED_VERSION);
        return target;
    }

    public static CategorisationAvro mockCategorisationAvro() {
        CategorisationAvro target = CategorisationAvro.newBuilder()
                .setDatasetVersionUrn(EXPECTED_URN)
                .setCategory(mockExternalItemAvro())
                .setCreatedBy(EXPECTED_USER)
                .setCreatedDate(EXPECTED_PAST_DATE)
                .setVersion(EXPECTED_VERSION)
                .setLastUpdated(EXPECTED_PAST_DATE)
                .setLastUpdatedBy(EXPECTED_USER)
                .setMaintainer(mockExternalItemAvro())
                .setValidFromEffective(EXPECTED_PAST_DATE)
                .setValidToEffective(EXPECTED_FUTURE_DATE)
                .setVersionableStatisticalResource(mockVersionableStatisticalResourceAvro())
                .build();
        return target;
    }

    public static Categorisation mockCategorisation() {
        Categorisation target = new Categorisation();
        target.setDatasetVersion(mockDatasetVersion());
        target.setCategory(mockExternalItem());
        target.setCreatedBy(EXPECTED_USER);
        target.setCreatedDate(EXPECTED_PAST_DATE);
        target.setVersion(EXPECTED_VERSION);
        target.setLastUpdated(EXPECTED_PAST_DATE);
        target.setLastUpdatedBy(EXPECTED_USER);
        target.setMaintainer(mockExternalItem());
        target.setValidFromEffective(EXPECTED_PAST_DATE);
        target.setValidToEffective(EXPECTED_FUTURE_DATE);
        target.setVersionableStatisticalResource(mockVersionableStatisticalResource());
        return target;
    }
}
