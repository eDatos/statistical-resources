package org.siemac.metamac.statistical.resources.core.utils.mocks;

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
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public class StatisticalResourcesDoMocks extends MetamacMocks {

    private static final String[] MAINTAINER_MOCK            = new String[]{"MaintainerMock"};
    private static final String   STATISTICAL_OPERATION_MOCK = "StatisticalOperationMock";
    private static final String   AGENCY_MOCK                = "AgencyMock";
    private static final String   AGENCY_SCHEME_MOCK         = "AgencySchemeMock";
    private static final String   CONCEPT_SCHEME_MOCK        = "ConceptSchemeMock";
    private static final String   CONCEPT_MOCK               = "ConceptMock";
    private static final String   CODELIST_MOCK              = "CodelistMock";

    private static final String   URI_MOCK_PREFIX            = "lorem/ipsum/dolor/sit/amet/";

    // -----------------------------------------------------------------
    // DATASOURCE
    // -----------------------------------------------------------------
    public static Datasource mockDatasource() {
        Datasource datasource = new Datasource();
        
        datasource.setDatasetVersion(mockDatasetVersion());
        datasource.setIdentifiableStatisticalResource(mockIdentifiableStatisticalResource(new IdentifiableStatisticalResource()));
        
        return datasource;
    }

    // -----------------------------------------------------------------
    // DATASET
    // -----------------------------------------------------------------
    public static Dataset mockDataset() {
        return mockDataset(false);
    }
    
    public static Dataset mockDatasetWithGeneratedDatasetVersions() {
        return mockDataset(true);
    }
    
    private static Dataset mockDataset(boolean withVersion) {
        Dataset ds = new Dataset();
        if (withVersion) {
            ds.addVersion(mockDatasetVersion(ds));
        }
        return ds;
    }

    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------
    public static DatasetVersion mockDatasetVersion() {
        return mockDatasetVersion(null);
    }

    public static DatasetVersion mockDatasetVersion(Dataset dataset) {
        DatasetVersion datasetVersion = new DatasetVersion();

        datasetVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET, StatisticalResourceFormatEnum.DS));
        if (dataset != null) {
            datasetVersion.setDataset(dataset);
        } else {
            Dataset ds = mockDataset(false);
            datasetVersion.setDataset(ds);
            ds.addVersion(datasetVersion);
        }

        return datasetVersion;
    }

    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------
    public static Query mockQuery() {
        Query query = new Query();
        query.setNameableStatisticalResource(mockNameableStatisticalResorce());
        return query;
    }
    
    public static Query mockQueryWithNameableNull() {
        Query query = mockQuery();
        query.setNameableStatisticalResource(null);
        return query;
    }

    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------
    private static SiemacMetadataStatisticalResource mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum type, StatisticalResourceFormatEnum format) {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        mockLifeCycleStatisticalResource(resource);

        resource.setType(type);
        resource.setFormat(format);
        return resource;
    }

    private static LifeCycleStatisticalResource mockLifeCycleStatisticalResource(LifeCycleStatisticalResource resource) {
        mockVersionableStatisticalResource(resource);
        
        resource.setCreator(mockAgencyExternalItem());
        resource.addContributor(mockAgencyExternalItem());
        resource.addMediator(mockAgencyExternalItem());
        resource.addPublisher(mockAgencyExternalItem());
        return resource;
    }

    private static VersionableStatisticalResource mockVersionableStatisticalResource(VersionableStatisticalResource resource) {
        mockNameableStatisticalResorce(resource);

        resource.setVersionDate(new DateTime());
        resource.setVersionLogic("01.000");
        return resource;
    }

    private static NameableStatisticalResource mockNameableStatisticalResorce() {
        NameableStatisticalResource nameableResource = new NameableStatisticalResource();
        mockNameableStatisticalResorce(nameableResource);
        return nameableResource;
    }

    private static NameableStatisticalResource mockNameableStatisticalResorce(NameableStatisticalResource nameableResource) {
        mockIdentifiableStatisticalResource(nameableResource);
        
        nameableResource.setTitle(mockInternationalString());
        nameableResource.setDescription(mockInternationalString());
        return nameableResource;
    }

    private static IdentifiableStatisticalResource mockIdentifiableStatisticalResource(IdentifiableStatisticalResource resource) {
        resource.setCode("resource-" + mockString(10));

        mockStatisticalResource(resource);
        return resource;
    }

    private static StatisticalResource mockStatisticalResource(StatisticalResource resource) {
        resource.setOperation(mockStatisticalOperationItem());
        return resource;
    }

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING
    // -----------------------------------------------------------------

    public static InternationalString mockInternationalString() {
        InternationalString internationalString = new InternationalString();
        LocalisedString es = new LocalisedString();
        es.setLabel(mockString(10) + " en Español");
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
        ExternalItem item = new ExternalItem(STATISTICAL_OPERATION_MOCK, getUriMock(), mockStatisticalOperationUrn(), TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static ExternalItem mockAgencyExternalItem() {
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
        ExternalItem item = new ExternalItem(CODELIST_MOCK, getUriMock(), mockCodeListExternalItem(), TypeExternalArtefactsEnum.CODELIST);
        item.setVersion(Long.valueOf(0));
        return item;
    }

    public static String mockStatisticalOperationUrn() {
        return GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(STATISTICAL_OPERATION_MOCK);
    }

    public static String mockAgencyUrn() {
        return GeneratorUrnUtils.generateSdmxAgencyUrn(MAINTAINER_MOCK, AGENCY_SCHEME_MOCK, VersionUtil.VERSION_INITIAL_VERSION, AGENCY_MOCK);
    }

    public static String mockConceptUrn() {
        return GeneratorUrnUtils.generateSdmxConceptUrn(MAINTAINER_MOCK, CONCEPT_SCHEME_MOCK, VersionUtil.VERSION_INITIAL_VERSION, CONCEPT_MOCK);
    }

    public static String mockConceptSchemeUrn() {
        return GeneratorUrnUtils.generateSdmxConceptSchemeUrn(MAINTAINER_MOCK, CONCEPT_SCHEME_MOCK, VersionUtil.VERSION_INITIAL_VERSION);
    }

    public static String mockCodeListExternalItem() {
        return GeneratorUrnUtils.generateSdmxCodelistUrn(MAINTAINER_MOCK, CODELIST_MOCK, VersionUtil.VERSION_INITIAL_VERSION);
    }

    // -----------------------------------------------------------------
    // PRIVATE
    // -----------------------------------------------------------------
    private static String getUriMock() {
        return URI_MOCK_PREFIX + mockString(5);
    }

}
