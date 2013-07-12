package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CodeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ConceptType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DataStructureDefinition;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimension;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionCode;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionCodes;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionType;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionsId;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponentType;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_resources.rest.external.invocation.SrmRestExternalFacade;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.BaseDo2RestMapperV10Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatasetsDo2RestMapperV10Impl extends BaseDo2RestMapperV10Impl implements DatasetsDo2RestMapperV10 {

    @Autowired
    private SrmRestExternalFacade srmRestExternalFacade;

    // TODO acceder desde aquí al servicio?
    @Autowired
    private DatasetService        datasetService;
    private final ServiceContext  ctx = new ServiceContext("restExternal", "restExternal", "restExternal");

    @Override
    public Dataset toDataset(DatasetVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }
        Dataset target = new Dataset();
        String datasetVersionUrn = source.getSiemacMetadataStatisticalResource().getUrn();

        target.setKind(RestExternalConstants.KIND_DATASET);
        target.setId(source.getSiemacMetadataStatisticalResource().getCode());
        target.setUrn(datasetVersionUrn);
        target.setSelfLink(toDatasetSelfLink(source));
        target.setName(toInternationalString(source.getSiemacMetadataStatisticalResource().getTitle()));
        target.setParentLink(toDatasetParentLink(source));
        target.setChildLinks(toDatasetChildLinks(source));
        // TODO resto de metadatos públicos

        // Dsd
        DataStructure dataStructure = srmRestExternalFacade.retrieveDataStructureByUrn(source.getRelatedDsd().getUrnInternal());
        target.setDataStructureDefinition(toDataStructureDefinition(source.getRelatedDsd(), dataStructure));
        // Dimensions
        target.setDimensions(toDimensions(datasetVersionUrn, dataStructure));

        return target;
    }

    private DataStructureDefinition toDataStructureDefinition(ExternalItem source, DataStructure dataStructure) {
        if (source == null) {
            return null;
        }
        DataStructureDefinition target = new DataStructureDefinition();
        toResourceExternalItemSrm(source, target);
        target.setHeading(toDimensionsId(dataStructure.getHeading()));
        target.setStub(toDimensionsId(dataStructure.getStub()));
        return target;
    }

    private DimensionsId toDimensionsId(org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimensions sources) {
        if (sources == null) {
            return null;
        }
        DimensionsId targets = new DimensionsId();
        for (LocalDimensionReferenceType source : sources.getDimensions()) {
            targets.getDimensionIds().add(toDimensionId(source));
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensionIds().size()));
        return targets;
    }

    private String toDimensionId(LocalDimensionReferenceType source) {
        if (source == null) {
            return null;
        }
        return source.getRef().getId();
    }

    private Dimensions toDimensions(String datasetVersionUrn, DataStructure dataStructure) throws MetamacException {

        List<String> dimensionsId = datasetService.retrieveDatasetVersionDimensionsIds(ctx, datasetVersionUrn);
        if (CollectionUtils.isEmpty(dimensionsId)) {
            return null;
        }

        Dimensions targets = new Dimensions();
        List<DsdDimension> dimensionsType = DsdProcessor.getDimensions(dataStructure);
        for (DsdDimension dsdDimension : dimensionsType) {
            targets.getDimensions().add(toDimension(datasetVersionUrn, dsdDimension));
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensions().size()));
        return targets;
    }

    private Dimension toDimension(String datasetVersionUrn, DsdDimension source) throws MetamacException {
        if (source == null) {
            return null;
        }
        Dimension target = new Dimension();
        target.setId(source.getComponentId());
        {
            // TODO NAME del concept identity
            InternationalString name = new InternationalString();
            LocalisedString nameLocalisedString = new LocalisedString();
            nameLocalisedString.setLang("es");
            nameLocalisedString.setValue(source.getComponentId());
            name.getTexts().add(nameLocalisedString);
            target.setName(name);
        }
        target.setType(toDimensionType(source.getType()));

        // Codes
        target.setDimensionCodes(toDimensionCodes(datasetVersionUrn, source));
        return target;
    }

    private DimensionCodes toDimensionCodes(String datasetVersionUrn, DsdDimension dimension) throws MetamacException {
        if (dimension == null) {
            return null;
        }
        DimensionCodes targets = new DimensionCodes();

        List<CodeDimension> coverages = datasetService.retrieveCoverageForDatasetVersionDimension(ctx, datasetVersionUrn, dimension.getComponentId());
        if (CollectionUtils.isEmpty(coverages)) {
            return null;
        }
        Map<String, CodeDimension> coveragesById = new HashMap<String, CodeDimension>(coverages.size());
        for (CodeDimension coverage : coverages) {
            coveragesById.put(coverage.getIdentifier(), coverage);
        }

        if (dimension.getCodelistRepresentationUrn() != null) {
            toDimensionCodeFromCodelist(coveragesById, dimension.getCodelistRepresentationUrn(), targets);
        } else if (dimension.getConceptSchemeRepresentationUrn() != null) {
            toDimensionCodeFromConceptScheme(coveragesById, dimension.getConceptSchemeRepresentationUrn(), targets);
        } else if (dimension.getTimeTextFormatType() != null) {
            toDimensionCodeFromTimeTextFormatType(coveragesById, dimension.getTimeTextFormatType(), targets);
        }

        targets.setTotal(BigInteger.valueOf(targets.getDimensionCodes().size()));
        return targets;
    }

    private void toDimensionCodeFromCodelist(Map<String, CodeDimension> coveragesById, String codelistUrn, DimensionCodes targets) throws MetamacException {
        if (codelistUrn == null) {
            return;
        }
        Codelist codelist = srmRestExternalFacade.retrieveCodelistByUrn(codelistUrn);
        for (CodeType code : codelist.getCodes()) {
            String id = code.getId();
            if (!coveragesById.containsKey(id)) {
                continue;
            }
            targets.getDimensionCodes().add(toDimensionCode(code));
        }
    }

    private void toDimensionCodeFromConceptScheme(Map<String, CodeDimension> coveragesById, String conceptSchemeUrn, DimensionCodes targets) throws MetamacException {
        if (conceptSchemeUrn == null) {
            return;
        }
        ConceptScheme conceptScheme = srmRestExternalFacade.retrieveConceptSchemeByUrn(conceptSchemeUrn);
        for (ConceptType concept : conceptScheme.getConcepts()) {
            String id = concept.getId();
            if (!coveragesById.containsKey(id)) {
                continue;
            }
            targets.getDimensionCodes().add(toDimensionCode(concept));
        }
    }

    private void toDimensionCodeFromTimeTextFormatType(Map<String, CodeDimension> coveragesById, TimeTextFormatType timeTextFormatType, DimensionCodes targets) throws MetamacException {
        if (timeTextFormatType == null) {
            return;
        }
        for (String coverageId : coveragesById.keySet()) {
            CodeDimension codeDimension = coveragesById.get(coverageId);
            targets.getDimensionCodes().add(toDimensionCode(codeDimension));
        }
    }

    private DimensionCode toDimensionCode(CodeType source) throws MetamacException {
        if (source == null) {
            return null;
        }
        DimensionCode target = new DimensionCode();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setName(toInternationalString(source.getNames()));
        // TODO resource! y resto de metadatos (representation...)
        return target;
    }

    private DimensionCode toDimensionCode(ConceptType source) throws MetamacException {
        if (source == null) {
            return null;
        }
        DimensionCode target = new DimensionCode();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setName(toInternationalString(source.getNames()));
        // TODO resource! y resto de metadatos (representation...)
        return target;
    }

    private DimensionCode toDimensionCode(CodeDimension source) throws MetamacException {
        if (source == null) {
            return null;
        }
        DimensionCode target = new DimensionCode();
        target.setId(source.getIdentifier());
        target.setUrn(null);
        {
            // TODO NAME?
            InternationalString name = new InternationalString();
            LocalisedString nameLocalisedString = new LocalisedString();
            nameLocalisedString.setLang("es"); // TODO locale?
            nameLocalisedString.setValue(source.getTitle());
            name.getTexts().add(nameLocalisedString);
            target.setName(name);
        }

        // TODO resource! y resto de metadatos (representation...)
        return target;
    }

    private DimensionType toDimensionType(DsdComponentType source) {
        switch (source) {
            case OTHER:
                return DimensionType.DIMENSION;
            case SPATIAL:
                return DimensionType.GEOGRAPHIC_DIMENSION;
            case TEMPORAL:
                return DimensionType.TIME_DIMENSION;
            case MEASURE:
                return DimensionType.MEASURE_DIMENSION;
            default:
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private ResourceLink toDatasetParentLink(DatasetVersion source) {
        return toDatasetsSelfLink(null, null, null);
    }

    private ChildLinks toDatasetChildLinks(DatasetVersion source) {
        // nothing
        return null;
    }

    private ResourceLink toDatasetsSelfLink(String agencyID, String resourceID, String version) {
        return toResourceLink(RestExternalConstants.KIND_DATASETS, toDatasetsLink(agencyID, resourceID, version));
    }

    private String toDatasetsLink(String agencyID, String resourceID, String version) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_DATASETS;
        return toStatisticalResourceLink(resourceSubpath, agencyID, resourceID, version);
    }

    private ResourceLink toDatasetSelfLink(DatasetVersion source) {
        return toResourceLink(RestExternalConstants.KIND_DATASET, toDatasetLink(source));
    }

    private String toDatasetLink(DatasetVersion source) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_DATASETS;
        return toStatisticalResourceLink(resourceSubpath, source.getSiemacMetadataStatisticalResource());
    }
}