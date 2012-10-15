package org.siemac.metamac.statistical.resources.core.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.domain.StatisticalResource;

public class StatisticalResourcesDoMocks {

    private static final String STATISTICAL_OPERATION_MOCK = "StatisticalOperationMock";
    private static final String AGENCY_MOCK                = "AgencyMock";
    private static final String AGENCY_SCHEME_MOCK         = "AgencySchemeMock";
    private static final String CONCEPT_SCHEME_MOCK        = "ConceptSchemeMock";
    private static final String CONCEPT_MOCK               = "ConceptMock";
    private static final String CODELIST_MOCK              = "CodelistMock";

    private static final String  URI_MOCK                   = "lorem/ipsum/dolor/sit/amet";
    private static final String  URN_MOCK                   = "urn:lorem.ipsum.dolor.infomodel.package.Resource=" + MetamacMocks.mockString(10);

    public static Query mockQuery() {
        Query query = new Query();

        query.setNameableStatisticalResource(mockNameableStatisticalResorce());
        return query;
    }

    private static NameableStatisticalResource mockNameableStatisticalResorce() {
        NameableStatisticalResource nameableResource = new NameableStatisticalResource();

        nameableResource.setTitle(mockInternationalString());
        nameableResource.setDescription(mockInternationalString());

        mockIdentifiableStatisticalResource(nameableResource);

        return nameableResource;
    }

    private static void mockIdentifiableStatisticalResource(IdentifiableStatisticalResource resource) {
        resource.setCode("resource-" + MetamacMocks.mockString(10));
        resource.setUri(URI_MOCK);
        resource.setUrn(URN_MOCK);

        mockStatisticalResource(resource);
    }

    private static void mockStatisticalResource(StatisticalResource resource) {
        resource.setOperation(mockStatisticalOperationItem());

        resource.setCreatedBy("user1");
        resource.setCreatedDate(new DateTime());
        resource.setLastUpdatedBy("user2");
        resource.setLastUpdated(new DateTime());
        resource.setVersion(Long.valueOf(0));
    }

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING
    // -----------------------------------------------------------------

    public static InternationalString mockInternationalString() {
        InternationalString internationalString = new InternationalString();
        LocalisedString es = new LocalisedString();
        es.setLabel(MetamacMocks.mockString(10) + " en Español");
        es.setLocale("es");
        LocalisedString en = new LocalisedString();
        en.setLabel(MetamacMocks.mockString(10) + " in English");
        en.setLocale("en");
        internationalString.addText(es);
        internationalString.addText(en);
        return internationalString;
    }

    // -----------------------------------------------------------------
    // EXTERNAL ITEM
    // -----------------------------------------------------------------

    public static ExternalItem mockStatisticalOperationItem() {
        return new ExternalItem(STATISTICAL_OPERATION_MOCK, URI_MOCK, mockStatisticalOperationUrn(), TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
    }

    public static ExternalItem mockAgencyExternalItem() {
        return new ExternalItem(AGENCY_MOCK, URI_MOCK, mockAgencyUrn(), TypeExternalArtefactsEnum.AGENCY);
    }

    public static ExternalItem mockConceptExternalItem() {
        return new ExternalItem(CONCEPT_MOCK, URI_MOCK, mockConceptUrn(), TypeExternalArtefactsEnum.CONCEPT);
    }

    public static ExternalItem mockConceptSchemeExternalItem() {
        return new ExternalItem(CONCEPT_SCHEME_MOCK, URI_MOCK, mockConceptSchemeUrn(), TypeExternalArtefactsEnum.CONCEPT_SCHEME);
    }

    public static ExternalItem mockCodeListSchemeExternalItem() {
        return new ExternalItem(CODELIST_MOCK, URI_MOCK, mockCodeListExternalItem(), TypeExternalArtefactsEnum.CODELIST);
    }

    public static String mockStatisticalOperationUrn() {
        return GeneratorUrnUtils.generateSiemacStatisticalOperationUrn(STATISTICAL_OPERATION_MOCK);
    }
    
    public static String mockAgencyUrn() {
        return GeneratorUrnUtils.generateSdmxAgencyUrn(AGENCY_MOCK, AGENCY_SCHEME_MOCK, VersionUtil.VERSION_INITIAL_VERSION, AGENCY_MOCK);
    }

    public static String mockConceptUrn() {
        return GeneratorUrnUtils.generateSdmxConceptUrn(AGENCY_MOCK, CONCEPT_SCHEME_MOCK, VersionUtil.VERSION_INITIAL_VERSION, CONCEPT_MOCK);
    }

    public static String mockConceptSchemeUrn() {
        return GeneratorUrnUtils.generateSdmxConceptSchemeUrn(AGENCY_MOCK, CONCEPT_SCHEME_MOCK, VersionUtil.VERSION_INITIAL_VERSION);
    }

    public static String mockCodeListExternalItem() {
        return GeneratorUrnUtils.generateSdmxCodelistUrn(AGENCY_MOCK, CODELIST_MOCK, VersionUtil.VERSION_INITIAL_VERSION);
    }

    // -----------------------------------------------------------------
    // PRIVATE
    // -----------------------------------------------------------------
}
