package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import org.joda.time.DateTime;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;

public abstract class StatisticalResourcesDoMocks extends MetamacMocks {

    private static final String[] MAINTAINER_MOCK                     = new String[]{"MaintainerMock"};
    private static final String   ORGANIZATION_UNIT_MOCK              = "OrganizationUnitMock";
    private static final String   STATISTICAL_OPERATION_MOCK          = "StatisticalOperationMock";
    private static final String   DATASET_VERSION_MOCK                = "DatasetVersionMock";
    private static final String   PUBLICATION_VERSION_MOCK            = "PublicationVersionMock";
    private static final String   STATISTICAL_OPERATION_INSTANCE_MOCK = "StatisticalOperationInstanceMock";
    private static final String   AGENCY_MOCK                         = "AgencyMock";
    private static final String   AGENCY_SCHEME_MOCK                  = "AgencySchemeMock";
    private static final String   CONCEPT_SCHEME_MOCK                 = "ConceptSchemeMock";
    private static final String   CONCEPT_MOCK                        = "ConceptMock";
    private static final String   CODELIST_MOCK                       = "CodelistMock";
    private static final String   CODE_MOCK                           = "CodeMock";
    private static final String   DSD_MOCK                            = "DsdMock";
    private static final String   DIMENSION_MOCK                            = "DimensionMock";

    private static final String   URI_MOCK_PREFIX                     = "lorem/ipsum/dolor/sit/amet/";
    protected static final String   USER_MOCK                           = "MockedUser";

    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------
    public Query mockQueryWithDatasetVersion(DatasetVersion datasetVersion) {
        Query query = mockQuery(datasetVersion);
        return query;
    }
    
    public Query mockQueryWithSelectionAndDatasetVersion(DatasetVersion datasetVersion) {
        Query resource = mockQuery(datasetVersion);
        resource.addSelection(mockQuerySelectionItem());
        
        return resource;
    }
    
    protected Query mockQuery(DatasetVersion datasetVersion) {
        Query resource = new Query();
        resource.setLifeCycleStatisticalResource(mockLifeCycleStatisticalResource(new LifeCycleStatisticalResource()));
        resource.setDatasetVersion(datasetVersion);
        resource.setType(QueryTypeEnum.FIXED);
        
        setSpecialCasesQueryMock(resource);

        return resource;
    }
    
    
    private QuerySelectionItem mockQuerySelectionItem() {
        QuerySelectionItem querySelectionItem = new QuerySelectionItem();
        querySelectionItem.setDimension("SEX");
        querySelectionItem.addCode(mockCodeItem());
        return querySelectionItem;
    }

    private CodeItem mockCodeItem() {
        CodeItem code = new CodeItem();
        code.setCode(mockString(6));
        return code;
    }

    // -----------------------------------------------------------------
    // DATASOURCE
    // -----------------------------------------------------------------
    public Datasource mockDatasource(DatasetVersion datasetVersion) {
        Datasource datasource = mockDatasource();
        datasource.setDatasetVersion(datasetVersion);

        return datasource;
    }

    protected Datasource mockDatasource() {
        Datasource datasource = new Datasource();
        datasource.setIdentifiableStatisticalResource(mockIdentifiableStatisticalResource(new IdentifiableStatisticalResource()));

        return datasource;
    }

    // -----------------------------------------------------------------
    // DATASET
    // -----------------------------------------------------------------

    public Dataset mockDatasetWithoutGeneratedDatasetVersions() {
        return mockDataset(false);
    }

    public Dataset mockDatasetWithGeneratedDatasetVersions() {
        return mockDataset(true);
    }

    private Dataset mockDataset(boolean withVersion) {
        Dataset dataset = new Dataset();
        if (withVersion) {
            dataset.addVersion(mockDatasetVersion(dataset));
        }
        return dataset;
    }

    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------

    public abstract DatasetVersion mockDatasetVersion();
    public abstract DatasetVersion mockDatasetVersion(Dataset dataset);

    protected DatasetVersion mockDatasetVersionMetadata() {
        DatasetVersion datasetVersion = new DatasetVersion();

        return datasetVersion;
    }

    // -----------------------------------------------------------------
    // PUBLICATION
    // -----------------------------------------------------------------
    public Publication mockPublicationWithoutGeneratedPublicationVersions() {
        return mockPublication(false);
    }

    public Publication mockPublicationWithGeneratedPublicationVersions() {
        return mockPublication(true);
    }

    private Publication mockPublication(boolean withVersion) {
        Publication publication = new Publication();
        if (withVersion) {
            publication.addVersion(mockPublicationVersion(publication));
        }
        return publication;
    }

    // -----------------------------------------------------------------
    // PUBLICATION VERSION
    // -----------------------------------------------------------------

    protected PublicationVersion mockPublicationVersionMetadata() {
        PublicationVersion publicationVersion = new PublicationVersion();
        return publicationVersion;
    }

    protected abstract PublicationVersion mockPublicationVersion();
    protected abstract PublicationVersion mockPublicationVersion(Publication publication);

    
    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------

    protected SiemacMetadataStatisticalResource mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum type, StatisticalResourceFormatEnum format) {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        mockLifeCycleStatisticalResource(resource);

        resource.setLanguage(mockCodeExternalItem());
        resource.addLanguage(mockCodeExternalItem());

        resource.setStatisticalOperation(mockStatisticalOperationItem());
        // TODO: KEYWORDS

        resource.setType(type);

        resource.setMaintainer(mockAgencyExternalItem());
        resource.setCreator(mockOrganizationUnitExternalItem());
        resource.addPublisher(mockOrganizationUnitExternalItem());
        resource.setRightsHolder(mockOrganizationUnitExternalItem());
        resource.setCopyrightedDate(new DateTime());
        resource.setLicense(mockInternationalString());
        resource.setAccessRights(mockInternationalString());

        setSpecialCasesSiemacMetadataStatisticalResourceMock(resource);

        return resource;
    }

    protected LifeCycleStatisticalResource mockLifeCycleStatisticalResource(LifeCycleStatisticalResource resource) {
        mockVersionableStatisticalResource(resource);

        setSpecialCasesLifeCycleStatisticalResourceMock(resource);
        return resource;
    }

    protected VersionableStatisticalResource mockVersionableStatisticalResource(VersionableStatisticalResource resource) {
        mockNameableStatisticalResorce(resource);

        setSpecialCasesVersionableStatisticalResourceMock(resource);
        return resource;
    }

    protected static NameableStatisticalResource mockNameableStatisticalResorce() {
        NameableStatisticalResource nameableResource = new NameableStatisticalResource();
        mockNameableStatisticalResorce(nameableResource);

        return nameableResource;
    }

    protected static NameableStatisticalResource mockNameableStatisticalResorce(NameableStatisticalResource resource) {
        mockIdentifiableStatisticalResource(resource);

        resource.setTitle(mockInternationalString());
        resource.setDescription(mockInternationalString());

        return resource;
    }

    protected static IdentifiableStatisticalResource mockIdentifiableStatisticalResource(IdentifiableStatisticalResource resource) {
        mockStatisticalResource(resource);

        resource.setCode("resource-" + mockString(10));

        return resource;
    }

    protected static StatisticalResource mockStatisticalResource(StatisticalResource resource) {
        return resource;
    }
    

    // -----------------------------------------------------------------
    // STATISTICAL OFFICIALITY
    // -----------------------------------------------------------------
    
    public StatisticOfficiality mockStatisticOfficiality(String identifier) {
        StatisticOfficiality mock = new StatisticOfficiality();
        mock.setDescription(mockInternationalString());
        mock.setIdentifier(identifier);
        
        setSpecialCasesStatisticOfficialityMock(mock);
        
        return mock;
    }

    // -----------------------------------------------------------------
    // ABSTRACT METHODS
    // -----------------------------------------------------------------

    protected abstract void setSpecialCasesSiemacMetadataStatisticalResourceMock(SiemacMetadataStatisticalResource resource);

    protected abstract void setSpecialCasesLifeCycleStatisticalResourceMock(LifeCycleStatisticalResource resource);

    protected abstract void setSpecialCasesVersionableStatisticalResourceMock(VersionableStatisticalResource resource);
    
    protected abstract void setSpecialCasesQueryMock(Query query);
    
    protected abstract void setSpecialCasesStatisticOfficialityMock(StatisticOfficiality officiality);

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING
    // -----------------------------------------------------------------

    public static InternationalString mockInternationalString() {
        InternationalString internationalString = new InternationalString();
        LocalisedString es = new LocalisedString();
        es.setLabel(mockString(10) + " en Espanol");
        es.setLocale("es");
        es.setVersion(Long.valueOf(0));
        LocalisedString en = new LocalisedString();
        en.setLabel(mockString(10) + " in English");
        en.setLocale("en");
        en.setVersion(Long.valueOf(0));
        internationalString.addText(es);
        internationalString.addText(en);
        internationalString.setVersion(Long.valueOf(0));
        return internationalString;
    }

    /**
     * Mock an InternationalString with one locale
     */
    public static InternationalString mockInternationalString(String locale, String label) {
        InternationalString target = new InternationalString();
        LocalisedString localisedString = new LocalisedString();
        localisedString.setLocale(locale);
        localisedString.setLabel(label);
        target.addText(localisedString);
        return target;
    }

    /**
     * Mock an InternationalString with two locales
     */
    public static InternationalString mockInternationalString(String locale01, String label01, String locale02, String label02) {
        InternationalString target = new InternationalString();
        LocalisedString localisedString01 = new LocalisedString();
        localisedString01.setLocale(locale01);
        localisedString01.setLabel(label01);
        target.addText(localisedString01);

        LocalisedString localisedString02 = new LocalisedString();
        localisedString02.setLocale(locale02);
        localisedString02.setLabel(label02);
        target.addText(localisedString02);
        return target;
    }

    
    // -----------------------------------------------------------------
    // EXTERNAL ITEM
    // -----------------------------------------------------------------

    public static ExternalItem mockStatisticalOperationItem() {
        String code = mockStatisticalOperationCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockStatisticalOperationUrn(code), TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockStatisticalOperationItem(String code) {
        ExternalItem item = new ExternalItem(code, getUriMock(), mockStatisticalOperationUrn(code), TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockStatisticalOperationInstanceItem() {
        ExternalItem item = new ExternalItem(STATISTICAL_OPERATION_INSTANCE_MOCK, getUriMock(), mockStatisticalOperationInstanceUrn(), TypeExternalArtefactsEnum.STATISTICAL_OPERATION_INSTANCE);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockAgencyExternalItem() {
        ExternalItem item = new ExternalItem(AGENCY_MOCK, getUriMock(), mockAgencyUrn(), TypeExternalArtefactsEnum.AGENCY);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockOrganizationUnitExternalItem() {
        ExternalItem item = new ExternalItem(AGENCY_MOCK, getUriMock(), mockAgencyUrn(), TypeExternalArtefactsEnum.AGENCY);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockConceptExternalItem() {
        ExternalItem item = new ExternalItem(CONCEPT_MOCK, getUriMock(), mockConceptUrn(), TypeExternalArtefactsEnum.CONCEPT);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockConceptSchemeExternalItem() {
        ExternalItem item = new ExternalItem(CONCEPT_SCHEME_MOCK, getUriMock(), mockConceptSchemeUrn(), TypeExternalArtefactsEnum.CONCEPT_SCHEME);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockCodeListSchemeExternalItem() {
        ExternalItem item = new ExternalItem(CODELIST_MOCK, getUriMock(), mockCodeListUrn(), TypeExternalArtefactsEnum.CODELIST);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockCodeExternalItem() {
        ExternalItem item = new ExternalItem(CODE_MOCK, getUriMock(), mockCodeUrn(), TypeExternalArtefactsEnum.CODE);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockDsdExternalItem() {
        ExternalItem item = new ExternalItem(DSD_MOCK, getUriMock(), mockDsdUrn(), TypeExternalArtefactsEnum.DATASTRUCTURE);
        item.setVersion(Long.valueOf(0));
        return item;
    }
    
    public static ExternalItem mockDimensionExternalItem() {
        ExternalItem item = new ExternalItem(DIMENSION_MOCK, getUriMock(), mockDimensionUrn(), TypeExternalArtefactsEnum.DIMENSION);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static String mockStatisticalOperationUrn(String code) {
        return GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(code);
    }

    public static String mockStatisticalOperationCode() {
        return mockString(8);
    }


    public static String mockStatisticalOperationInstanceUrn() {
        return GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(STATISTICAL_OPERATION_INSTANCE_MOCK);
    }

    public static String mockAgencyUrn() {
        return GeneratorUrnUtils.generateSdmxAgencyUrn(MAINTAINER_MOCK, AGENCY_SCHEME_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION, AGENCY_MOCK);
    }

    public static String mockOrganizationUnitUrn() {
        return GeneratorUrnUtils.generateSdmxOrganisationUnitUrn(MAINTAINER_MOCK, ORGANIZATION_UNIT_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION, AGENCY_MOCK);
    }

    public static String mockConceptUrn() {
        return GeneratorUrnUtils.generateSdmxConceptUrn(MAINTAINER_MOCK, CONCEPT_SCHEME_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION, CONCEPT_MOCK);
    }

    public static String mockConceptSchemeUrn() {
        return GeneratorUrnUtils.generateSdmxConceptSchemeUrn(MAINTAINER_MOCK, CONCEPT_SCHEME_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION);
    }

    public static String mockCodeListUrn() {
        return GeneratorUrnUtils.generateSdmxCodelistUrn(MAINTAINER_MOCK, CODE_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION);
    }

    public static String mockCodeUrn() {
        return GeneratorUrnUtils.generateSdmxCodeUrn(MAINTAINER_MOCK, CODELIST_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION, CODE_MOCK);
    }

    public static String mockDsdUrn() {
        return GeneratorUrnUtils.generateSdmxDatastructureUrn(MAINTAINER_MOCK, DSD_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION);
    }
    
    public static String mockDimensionUrn() {
        return GeneratorUrnUtils.generateSdmxDimensionUrn(MAINTAINER_MOCK, DSD_MOCK, VersionUtil.PATTERN_XX_YYY_INITIAL_VERSION, DIMENSION_MOCK);
    }

    // -----------------------------------------------------------------
    // Related Resource
    // -----------------------------------------------------------------

    public static RelatedResource mockDatasetVersionRelated() {
        RelatedResource resource = new RelatedResource(DATASET_VERSION_MOCK, getUriMock(), mockDatasetVersionRelatedUrn(), TypeExternalArtefactsEnum.DATASET_VERSION);
        resource.setVersion(Long.valueOf(0));
        return resource;
    }

    public static RelatedResource mockPublicationVersionRelated() {
        RelatedResource resource = new RelatedResource(PUBLICATION_VERSION_MOCK, getUriMock(), mockPublicationVersionRelatedUrn(), TypeExternalArtefactsEnum.PUBLICATION_VERSION);
        resource.setVersion(Long.valueOf(0));
        return resource;
    }

    public static String mockDatasetVersionRelatedUrn() {
        // TODO: change when dataset version urn can be generated with generatorUrnUtils
        return "TODO:mock";
    }

    public static String mockPublicationVersionRelatedUrn() {
        // TODO: change when publication version urn can be generated with generatorUrnUtils
        return "TODO:mock";
    }
    // -----------------------------------------------------------------
    // PRIVATE
    // -----------------------------------------------------------------
    private static String getUriMock() {
        return URI_MOCK_PREFIX + mockString(5);
    }

}
