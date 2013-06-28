package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import static org.junit.Assert.fail;

import org.joda.time.DateTime;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
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
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public abstract class StatisticalResourcesDoMocks extends MetamacMocks {

    protected static final String USER_MOCK                = "MockedUser";

    // -----------------------------------------------------------------
    // QUERY VERSION
    // -----------------------------------------------------------------
    public abstract QueryVersion mockQueryVersion(DatasetVersion datasetVersion, boolean isDatasetLastVersion);

    public QueryVersion mockQueryVersionWithGeneratedDatasetVersion() {
        DatasetVersion datasetVersion = mockDatasetVersion();
        QueryVersion query = mockQueryVersion(datasetVersion, true);
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
        return mockChapter(mockElementLevel());
    }
    
    public Chapter mockChapterInParentElementLevel(ElementLevel parentElementLevel) {
        Chapter chapter = mockChapter(mockElementLevel());
        chapter.getElementLevel().setParent(parentElementLevel);
        return chapter;
    }
    
    public Chapter mockChapter(ElementLevel elementLevel) {
        Chapter chapter = new Chapter();
        
        // Element level
        chapter.setElementLevel(elementLevel);
        elementLevel.setChapter(chapter);
        
        // Metadata
        chapter.setNameableStatisticalResource(mockNameableStatisticalResorce());
        
        return chapter;
    }

    // CUBE
    private Cube mockCube() {
        return mockCube(mockElementLevel());
    }
    
    private Cube mockCube(ElementLevel elementLevel) {
        Cube cube = new Cube();
        
        // Element level
        cube.setElementLevel(elementLevel);
        elementLevel.setCube(cube);
        
        // Metadata
        cube.setNameableStatisticalResource(mockNameableStatisticalResorce());
        return cube;
    }
    
    public Cube mockDatasetCube(Dataset dataset) {
        Cube cube = mockCube();
        cube.setDataset(dataset);
        return cube;
    }
    
    public Cube mockQueryCube(Query query) {
        Cube cube = mockCube();
        cube.setQuery(query);
        return cube;
    }

    // ELEMENT LEVEL
    private ElementLevel mockElementLevel() {
        return mockElementLevel(null, null);
    }

    private ElementLevel mockElementLevel(PublicationVersion publicationVersion, ElementLevel parentElementLevel) {
        ElementLevel elementLevel = new ElementLevel();
        
        elementLevel.setOrderInLevel(Long.valueOf(1));

        if (publicationVersion == null) {
            publicationVersion = mockPublicationVersion();
        }

        // Relation with publicationVersion
        elementLevel.setPublicationVersion(publicationVersion);
        publicationVersion.addChildrenAllLevel(elementLevel);

        if (parentElementLevel == null) {
            elementLevel.setPublicationVersionFirstLevel(publicationVersion);
            publicationVersion.addChildrenFirstLevel(elementLevel);
        } else {
            elementLevel.setParent(parentElementLevel);
            parentElementLevel.addChildren(elementLevel);
        }
        
        return elementLevel;
    }

    public ElementLevel mockChapterElementLevel(PublicationVersion publicationVersion) {
        return mockChapterElementLevel(publicationVersion, null);
    }
    
    public ElementLevel mockChapterElementLevel(PublicationVersion publicationVersion, ElementLevel parentElementLevel) {
        ElementLevel elementLevel = mockElementLevel(publicationVersion, parentElementLevel);

        // Chapter relation
        elementLevel.setChapter(mockChapter(elementLevel));
        
        return elementLevel;
    }
 
    public ElementLevel mockDatasetCubeElementLevel(PublicationVersion publicationVersion, Dataset dataset) {
        ElementLevel elementLevel = mockDatasetCubeElementLevel(publicationVersion, dataset, null);
        return elementLevel;
    }
    
    public ElementLevel mockDatasetCubeElementLevel(PublicationVersion publicationVersion, Dataset dataset, ElementLevel parentElementLevel) {
        ElementLevel elementLevel = mockCubeElementLevel(publicationVersion, parentElementLevel, mockDatasetCube(dataset));
        return elementLevel;
    }
    
    public ElementLevel mockQueryCubeElementLevel(PublicationVersion publicationVersion, Query query) {
        return mockQueryCubeElementLevel(publicationVersion, query, null);
    }
    
    public ElementLevel mockQueryCubeElementLevel(PublicationVersion publicationVersion, Query query, ElementLevel parentElementLevel) {
        ElementLevel elementLevel = mockCubeElementLevel(publicationVersion, parentElementLevel, mockQueryCube(query));
        return elementLevel;
    }
    
    private ElementLevel mockCubeElementLevel(PublicationVersion publicationVersion, ElementLevel parentElementLevel, Cube cube) {
        ElementLevel elementLevel = mockElementLevel(publicationVersion, parentElementLevel);

        // Cube relation
        elementLevel.setCube(cube);
        cube.setElementLevel(elementLevel);
        
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

    public static ExternalItem mockStatisticalOperationExternalItem() {
        String code = mockCode();
        return mockStatisticalOperationExternalItem(code);
    }

    public static ExternalItem mockStatisticalOperationExternalItem(String code) {
        return mockExternalItem(code, mockStatisticalOperationUrn(code), TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
    }

    public static ExternalItem mockStatisticalOperationInstanceExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockStatisticalOperationInstanceUrn(code), TypeExternalArtefactsEnum.STATISTICAL_OPERATION_INSTANCE);
    }

    public static ExternalItem mockAgencyExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockAgencyUrn(code), TypeExternalArtefactsEnum.AGENCY);
    }

    public static ExternalItem mockOrganizationUnitExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockOrganizationUnitUrn(code), TypeExternalArtefactsEnum.ORGANISATION_UNIT);
    }

    public static ExternalItem mockConceptExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockConceptUrn(code), TypeExternalArtefactsEnum.CONCEPT);
    }

    public static ExternalItem mockConceptSchemeExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockConceptSchemeUrn(code), TypeExternalArtefactsEnum.CONCEPT_SCHEME);
    }

    public static ExternalItem mockCodeListSchemeExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockCodeListUrn(code), TypeExternalArtefactsEnum.CODELIST);
    }

    public static ExternalItem mockCodeExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockCodeUrn(code), TypeExternalArtefactsEnum.CODE);
    }

    public static ExternalItem mockDsdExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockDsdUrn(code), TypeExternalArtefactsEnum.DATASTRUCTURE);
    }

    public static ExternalItem mockDimensionExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockDimensionUrn(code), TypeExternalArtefactsEnum.DIMENSION);
    }
    
    public static ExternalItem mockConfigurationExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockCommonConfigurationUrn(code), TypeExternalArtefactsEnum.CONFIGURATION);
    }

    private static ExternalItem mockExternalItem(String code, String urn, TypeExternalArtefactsEnum type) {
        ExternalItem item = new ExternalItem(code, CoreCommonConstants.API_LATEST_WITH_SLASHES + code, urn, urn + ":internal", type, mockInternationalString(), CoreCommonConstants.URL_SEPARATOR
                + code);
        item.setVersion(Long.valueOf(0));
        
        if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(type)) {
            item.setUrnInternal(null);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(type)) {
            item.setUrnInternal(null);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(type)) {
            // nothing to do because urn and urnInternal are ok for SrmExternalItems 
        } else {
            fail("Unexpected type of ExternalItem:" + type);
        }
        
        return item;
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
}
