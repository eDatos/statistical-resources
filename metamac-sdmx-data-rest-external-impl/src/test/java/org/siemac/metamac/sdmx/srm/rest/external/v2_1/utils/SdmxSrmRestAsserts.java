package org.siemac.metamac.sdmx.srm.rest.external.v2_1.utils;

import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;

public class SdmxSrmRestAsserts {

    /*
     * @SuppressWarnings({"rawtypes", "unchecked"})
     * public static void assertFindConceptSchemes(ConceptsService conceptsService, String agencyID, String resourceID, String version, Structure conceptSchemesActual) throws Exception {
     * assertNotNull(conceptSchemesActual);
     * // Verify
     * ArgumentCaptor<List> conditions = ArgumentCaptor.forClass(List.class);
     * ArgumentCaptor<PagingParameter> pagingParameter = ArgumentCaptor.forClass(PagingParameter.class);
     * verify(conceptsService).findConceptSchemesByCondition(any(ServiceContext.class), conditions.capture(), pagingParameter.capture());
     * // Validate
     * MetamacRestAsserts.assertEqualsConditionalCriteria(buildExpectedConditionalCriteria(agencyID, resourceID, version), conditions.getValue());
     * MetamacRestAsserts.assertEqualsPagingParameter(buildExpectedPagingParameter(), pagingParameter.getValue());
     * }
     */
    /*
     * private static List<ConditionalCriteria> buildExpectedConditionalCriteria(String agencyID, String resourceID, String version) {
     * List<ConditionalCriteria> expected = new ArrayList<ConditionalCriteria>();
     * expected.add(ConditionalCriteriaBuilder.criteriaFor(ConceptSchemeVersion.class).distinctRoot().buildSingle());
     * expected.add(ConditionalCriteriaBuilder.criteriaFor(ConceptSchemeVersion.class).withProperty(ConceptSchemeVersionProperties.maintainableArtefact().finalLogic()).eq(Boolean.TRUE).buildSingle());
     * if (agencyID != null && !RestExternalConstants.WILDCARD.equals(agencyID)) {
     * expected.add(ConditionalCriteriaBuilder.criteriaFor(ConceptSchemeVersion.class).withProperty(ConceptSchemeVersionProperties.maintainableArtefact().maintainer().idAsMaintainer())
     * .eq(agencyID).buildSingle());
     * }
     * if (resourceID != null && !RestExternalConstants.WILDCARD.equals(resourceID)) {
     * expected.add(ConditionalCriteriaBuilder.criteriaFor(ConceptSchemeVersion.class).withProperty(ConceptSchemeVersionProperties.maintainableArtefact().code()).eq(resourceID).buildSingle());
     * }
     * if (version != null && !RestExternalConstants.WILDCARD.equals(version)) {
     * expected.add(ConditionalCriteriaBuilder.criteriaFor(ConceptSchemeVersion.class).withProperty(ConceptSchemeVersionProperties.maintainableArtefact().versionLogic()).eq(version)
     * .buildSingle());
     * }
     * return expected;
     * }
     */
    private static PagingParameter buildExpectedPagingParameter() {
        return PagingParameter.noLimits();
    }
}