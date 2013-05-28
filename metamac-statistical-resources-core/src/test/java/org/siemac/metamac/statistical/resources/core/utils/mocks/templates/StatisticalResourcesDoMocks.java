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
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public abstract class StatisticalResourcesDoMocks extends MetamacMocks {

    private static final String[] MAINTAINER_MOCK                     = new String[]{"MaintainerMock"};
    private static final String   ORGANIZATION_UNIT_MOCK              = "OrganizationUnitMock";
    private static final String   DATASET_VERSION_MOCK                = "DatasetVersionMock";
    private static final String   PUBLICATION_VERSION_MOCK            = "PublicationVersionMock";
    private static final String   AGENCY_SCHEME_MOCK                  = "AgencySchemeMock";
    private static final String   CONCEPT_SCHEME_MOCK                 = "ConceptSchemeMock";
    private static final String   CODELIST_MOCK                       = "CodelistMock";
    private static final String   DSD_MOCK                            = "DsdMock";

    private static final String   URI_MOCK_PREFIX                     = "lorem/ipsum/dolor/sit/amet/";
    protected static final String USER_MOCK                           = "MockedUser";

    
    // -----------------------------------------------------------------
    // QUERY VERSION
    // -----------------------------------------------------------------
    public abstract QueryVersion mockQueryVersion(DatasetVersion datasetVersion, boolean isDatasetLastVersion);
    
    
    public QueryVersion mockQueryVersionWithGeneratedDatasetVersion() {
        DatasetVersion datasetVersion = mockDatasetVersion();
        QueryVersion query = mockQueryVersion(datasetVersion,true);
        return query;
    }
    
    public QueryVersion mockQueryVersionWithDatasetVersion(DatasetVersion datasetVersion, boolean isDatasetLastVersion) {
        return mockQueryVersion(datasetVersion, isDatasetLastVersion);
    }

    protected QuerySelectionItem mockQuerySelectionItem() {
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

        // TODO: ELiminar cuando el código del datasource se esté generando
        // We can not set code in Identifiable becasuse thera are some resources that have generated code
        datasource.getIdentifiableStatisticalResource().setCode("resource-" + mockString(10));

        return datasource;
    }


    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------

    public abstract DatasetVersion mockDatasetVersion();

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
        publication.setIdentifiableStatisticalResource(mockIdentifiableStatisticalResource(new IdentifiableStatisticalResource()));
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

    // CHAPTER
    public Chapter mockChapter() {
        Chapter chapter = new Chapter();
        chapter.setElementLevel(mockElementLevelForChapter(chapter));
        chapter.setNameableStatisticalResource(mockNameableStatisticalResorce());
        return chapter;
    }

    // CUBE
    public Cube mockCube() {
        Cube cube = new Cube();
        cube.setElementLevel(mockElementLevelForCube(cube));
        cube.setNameableStatisticalResource(mockNameableStatisticalResorce());
        return cube;
    }

    // ELEMENT LEVEL
    private ElementLevel mockElementLevel() {
        PublicationVersion publicationVersion = mockPublicationVersion();

        ElementLevel elementLevel = new ElementLevel();
        elementLevel.setOrderInLevel(Long.valueOf(1));
        elementLevel.setPublicationVersion(publicationVersion);
        elementLevel.setPublicationVersionFirstLevel(publicationVersion);
        elementLevel.setChapter(null);
        elementLevel.setCube(null);
        return elementLevel;
    }

    private ElementLevel mockElementLevelForChapter(Chapter chapter) {
        ElementLevel elementLevel = mockElementLevel();
        elementLevel.setChapter(chapter);
        return elementLevel;
    }

    private ElementLevel mockElementLevelForCube(Cube cube) {
        ElementLevel elementLevel = mockElementLevel();
        elementLevel.setCube(cube);
        return elementLevel;
    }

    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------

    protected SiemacMetadataStatisticalResource mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum type) {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        mockLifeCycleStatisticalResource(resource);

        resource.setLanguage(mockCodeExternalItem());
        resource.addLanguage(mockCodeExternalItem());

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

    protected NameableStatisticalResource mockNameableStatisticalResorce() {
        NameableStatisticalResource nameableResource = new NameableStatisticalResource();
        mockNameableStatisticalResorce(nameableResource);

        return nameableResource;
    }

    protected NameableStatisticalResource mockNameableStatisticalResorce(NameableStatisticalResource resource) {
        mockIdentifiableStatisticalResource(resource);

        resource.setTitle(mockInternationalString());
        resource.setDescription(mockInternationalString());

        return resource;
    }

    protected IdentifiableStatisticalResource mockIdentifiableStatisticalResource(IdentifiableStatisticalResource resource) {
        mockStatisticalResource(resource);

        setSpecialCasesIdentifiableStatisticalResourceMock(resource);
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

    protected abstract void setSpecialCasesIdentifiableStatisticalResourceMock(IdentifiableStatisticalResource resource);

    protected abstract void setSpecialCasesQueryVersionMock(QueryVersion query);

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
        String code = mockCode();
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
        String code = mockCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockStatisticalOperationInstanceUrn(code), TypeExternalArtefactsEnum.STATISTICAL_OPERATION_INSTANCE);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockAgencyExternalItem() {
        String code = mockCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockAgencyUrn(code), TypeExternalArtefactsEnum.AGENCY);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockCommonMetadataExternalItem() {
        String code = mockCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockCommonConfigurationUrn(code), TypeExternalArtefactsEnum.CONFIGURATION); 
        item.setVersion(Long.valueOf(0));
        return item;
    }
    
    public static ExternalItem mockOrganizationUnitExternalItem() {
        String code = mockCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockAgencyUrn(code), TypeExternalArtefactsEnum.ORGANISATION_UNIT); 
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockConceptExternalItem() {
        String code = mockCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockConceptUrn(code), TypeExternalArtefactsEnum.CONCEPT);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockConceptSchemeExternalItem() {
        String code = mockCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockConceptSchemeUrn(code), TypeExternalArtefactsEnum.CONCEPT_SCHEME);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockCodeListSchemeExternalItem() {
        String code = mockCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockCodeListUrn(code), TypeExternalArtefactsEnum.CODELIST);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockCodeExternalItem() {
        String code = mockCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockCodeUrn(code), TypeExternalArtefactsEnum.CODE);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockDsdExternalItem() {
        String code = mockCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockDsdUrn(code), TypeExternalArtefactsEnum.DATASTRUCTURE);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockDimensionExternalItem() {
        String code = mockCode();
        ExternalItem item = new ExternalItem(code, getUriMock(), mockDimensionUrn(code), TypeExternalArtefactsEnum.DIMENSION);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    private static String mockCode() {
        return mockString(8);
    }
    
    public static String mockStatisticalOperationUrn(String code) {
        return GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(code);
    }


    public static String mockStatisticalOperationInstanceUrn(String code) {
        return GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(code);
    }

    public static String mockAgencyUrn(String code) {
        return GeneratorUrnUtils.generateSdmxAgencyUrn(MAINTAINER_MOCK, AGENCY_SCHEME_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION, code);
    }
    
    public static String mockCommonConfigurationUrn(String code) {
        return GeneratorUrnUtils.generateSiemacCommonMetadataUrn(code);
    }

    public static String mockOrganizationUnitUrn(String code) {
        return GeneratorUrnUtils.generateSdmxOrganisationUnitUrn(MAINTAINER_MOCK, ORGANIZATION_UNIT_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION, code);
    }

    public static String mockConceptUrn(String code) {
        return GeneratorUrnUtils.generateSdmxConceptUrn(MAINTAINER_MOCK, CONCEPT_SCHEME_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION, code);
    }

    public static String mockConceptSchemeUrn(String code) {
        return GeneratorUrnUtils.generateSdmxConceptSchemeUrn(MAINTAINER_MOCK, code, VersionUtil.PATTERN_X_Y_INITIAL_VERSION);
    }

    public static String mockCodeListUrn(String code) {
        return GeneratorUrnUtils.generateSdmxCodelistUrn(MAINTAINER_MOCK, code, VersionUtil.PATTERN_X_Y_INITIAL_VERSION);
    }

    public static String mockCodeUrn(String code) {
        return GeneratorUrnUtils.generateSdmxCodeUrn(MAINTAINER_MOCK, CODELIST_MOCK, VersionUtil.PATTERN_X_Y_INITIAL_VERSION, code);
    }
    
    public static String mockDsdUrn(String code) {
        return GeneratorUrnUtils.generateSdmxDatastructureUrn(MAINTAINER_MOCK, code, VersionUtil.PATTERN_X_Y_INITIAL_VERSION);
    }

    public static String mockDimensionUrn(String code) {
        return GeneratorUrnUtils.generateSdmxDimensionUrn(MAINTAINER_MOCK, DSD_MOCK, VersionUtil.PATTERN_XX_YYY_INITIAL_VERSION, code);
    }

    // -----------------------------------------------------------------
    // Related Resource
    // -----------------------------------------------------------------

    public static RelatedResource mockDatasetVersionRelated(DatasetVersion datasetVersion) {
        RelatedResource resource = new RelatedResource(TypeRelatedResourceEnum.DATASET_VERSION);
        resource.setType(TypeRelatedResourceEnum.DATASET_VERSION);
        resource.setDatasetVersion(datasetVersion);
        resource.setVersion(Long.valueOf(0));
        return resource;
    }

    public static RelatedResource mockPublicationVersionRelated(PublicationVersion pubVersion) {
        RelatedResource resource = new RelatedResource(TypeRelatedResourceEnum.PUBLICATION_VERSION);
        resource.setPublicationVersion(pubVersion);
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
