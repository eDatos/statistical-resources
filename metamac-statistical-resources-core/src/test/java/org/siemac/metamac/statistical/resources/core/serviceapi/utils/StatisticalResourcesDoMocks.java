package org.siemac.metamac.statistical.resources.core.serviceapi.utils;

import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.Query;

public class StatisticalResourcesDoMocks {

    private static final String STATISTICAL_OPERATION_MOCK = "StatisticalOperationMock";
    private static final String AGENCY_MOCK                = "AgencyMock";
    private static final String AGENCY_SCHEME_MOCK         = "AgencySchemeMock";
    private static final String CONCEPT_SCHEME_MOCK        = "ConceptSchemeMock";
    private static final String CONCEPT_MOCK               = "ConceptMock";
    private static final String CODELIST_MOCK              = "CodelistMock";

    public static final String  URI_MOCK                   = "lorem/ipsum/dolor/sit/amet";

    
    
    public static Query mockQuery() {
        Query query = new Query();

        NameableStatisticalResource nameableResource = new NameableStatisticalResource();
        nameableResource.setOperation(new ExternalItem("OPERATION01", "http://apis.istac.org/statisticalOperations/operations/OPERATION01", "urn:siemac.org.siemac.infomodel.statisticalOperation.Operation=OPERATION01", TypeExternalArtefactsEnum.STATISTICAL_OPERATION));
        nameableResource.setCode("QUERY01");
        nameableResource.setUri("http://api.istac.org/statisticalResources/queries/urn:siemac.org.siemac.infomodel.statisticalResources.Query=QUERY1");
        nameableResource.setUrn("urn:siemac.org.siemac.infomodel.statisticalResources.Query=QUERY1");
        nameableResource.setTitle(new InternationalString("es", "Consulta 01"));
        nameableResource.setDescription(new InternationalString("es", "Descripción de consulta 01"));
        
        nameableResource.setCreatedBy("user1");
        nameableResource.setCreatedDate(MetamacMocks.mockDateTime());
        nameableResource.setLastUpdatedBy("user2");
        nameableResource.setLastUpdated(MetamacMocks.mockDateTime());
        nameableResource.setVersion(Long.valueOf(0));
        
        query.setNameableStatisticalResource(nameableResource);
        
        return query;
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
        return new ExternalItem(STATISTICAL_OPERATION_MOCK, URI_MOCK, mockAgencyUrn(), TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
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
