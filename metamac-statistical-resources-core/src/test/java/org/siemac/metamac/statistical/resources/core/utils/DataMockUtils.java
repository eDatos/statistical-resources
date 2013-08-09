package org.siemac.metamac.statistical.resources.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeReferenceType;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;

import com.arte.statistic.dataset.repository.dto.ConditionObservationDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

public class DataMockUtils {

    private static final String GEOCODELIST_TEST_URN = "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=TEST:codelist-01(1.0)";
    private static final String CODE_TEST_URN_PREFIX = "urn:uuid:";

    public static void mockDsdAndDataRepositorySimpleDimensions() throws Exception {
        List<ConditionObservationDto> dimensionsCodes = new ArrayList<ConditionObservationDto>();

        dimensionsCodes.add(DsRepositoryMockUtils.mockCodeDimensions("GEO_DIM", "code-01", "code-02", "code-03"));
        dimensionsCodes.add(DsRepositoryMockUtils.mockCodeDimensions("TIME_PERIOD", "2010", "2011", "2012"));
        dimensionsCodes.add(DsRepositoryMockUtils.mockCodeDimensions("MEAS_DIM", "concept-01", "concept-02", "concept-03"));
        Mockito.when(getDatasetRepositoriesServiceFacade().findCodeDimensions(Mockito.anyString())).thenReturn(dimensionsCodes);

        DatasetRepositoryDto datasetRepoDto = DsRepositoryMockUtils.mockDatasetRepository("dsrepo-01", "GEO_DIM", "TIME_PERIOD", "MEAS_DIM");
        Mockito.when(getDatasetRepositoriesServiceFacade().retrieveDatasetRepository(Mockito.anyString())).thenReturn(datasetRepoDto);

        Mockito.when(getDatasetRepositoriesServiceFacade().countObservations(Mockito.anyString())).thenReturn(27L);

        // Mock codelist and concept Scheme

        CodelistReferenceType codelistReference = SrmMockUtils.buildCodelistRef(GEOCODELIST_TEST_URN);
        Codes codes = SrmMockUtils.buildCodes(3);
        Mockito.when(getSrmRestInternalService().retrieveCodesOfCodelistEfficiently(codelistReference.getURN())).thenReturn(codes);

        ConceptSchemeReferenceType conceptSchemeReference = SrmMockUtils.buildConceptSchemeRef("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=TEST:cshm-01(1.0)");
        Concepts concepts = SrmMockUtils.buildConcepts(3);
        Mockito.when(getSrmRestInternalService().retrieveConceptsOfConceptSchemeEfficiently(conceptSchemeReference.getURN())).thenReturn(concepts);

        // Create a datastructure with dimensions marked as measure temporal and spatial

        DataStructure dsd = SrmMockUtils.mockDsdWithGeoTimeAndMeasureDimensions("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=TFFS:CRED_EXT_DEBT(1.0)", "GEO_DIM", "TIME_PERIOD",
                "MEAS_DIM", conceptSchemeReference, codelistReference);
        Mockito.when(getSrmRestInternalService().retrieveDsdByUrn(Mockito.anyString())).thenReturn(dsd);
    }

    public static void fillDatasetVersionWithCalculatedMetadataFromData(DatasetVersion datasetVersion) {
        datasetVersion.addGeographicCoverage(buildExternalItem("code-01", TypeExternalArtefactsEnum.CODE));
        datasetVersion.addGeographicCoverage(buildExternalItem("code-02", TypeExternalArtefactsEnum.CODE));
        datasetVersion.addGeographicCoverage(buildExternalItem("code-03", TypeExternalArtefactsEnum.CODE));

        datasetVersion.addTemporalCoverage(buildTemporalCode("2010"));
        datasetVersion.addTemporalCoverage(buildTemporalCode("2011"));
        datasetVersion.addTemporalCoverage(buildTemporalCode("2012"));

        datasetVersion.addMeasureCoverage(buildExternalItem("concept-01", TypeExternalArtefactsEnum.CONCEPT));
        datasetVersion.addMeasureCoverage(buildExternalItem("concept-02", TypeExternalArtefactsEnum.CONCEPT));
        datasetVersion.addMeasureCoverage(buildExternalItem("concept-03", TypeExternalArtefactsEnum.CONCEPT));

        datasetVersion.addCoverage(new CodeDimension("MEAS_DIM", "concept-01", "Concept 01"));
        datasetVersion.addCoverage(new CodeDimension("MEAS_DIM", "concept-02", "Concept 02"));
        datasetVersion.addCoverage(new CodeDimension("MEAS_DIM", "concept-03", "Concept 03"));
        datasetVersion.addCoverage(new CodeDimension("TIME_PERIOD", "2010"));
        datasetVersion.addCoverage(new CodeDimension("TIME_PERIOD", "2011"));
        datasetVersion.addCoverage(new CodeDimension("TIME_PERIOD", "2012"));
        datasetVersion.addCoverage(new CodeDimension("GEO_DIM", "code-01", "Code 01"));
        datasetVersion.addCoverage(new CodeDimension("GEO_DIM", "code-02", "Code 02"));
        datasetVersion.addCoverage(new CodeDimension("GEO_DIM", "code-03", "Code 03"));

        datasetVersion.setFormatExtentDimensions(3);
        datasetVersion.setFormatExtentObservations(27L);
    }

    private static TemporalCode buildTemporalCode(String identifier) {
        TemporalCode code = new TemporalCode();
        code.setIdentifier(identifier);
        code.setTitle(identifier);
        return code;
    }

    private static ExternalItem buildExternalItem(String code, TypeExternalArtefactsEnum type) {
        ExternalItem item = new ExternalItem();
        item.setCode(code);
        item.setUrn(CODE_TEST_URN_PREFIX + code);
        item.setType(type);
        return item;
    }

    protected static SrmRestInternalService getSrmRestInternalService() {
        return ApplicationContextProvider.getApplicationContext().getBean(SrmRestInternalService.class);
    }

    private static DatasetRepositoriesServiceFacade getDatasetRepositoriesServiceFacade() {
        return ApplicationContextProvider.getApplicationContext().getBean(DatasetRepositoriesServiceFacade.class);
    }
}
