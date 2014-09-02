package org.siemac.metamac.statistical.resources.core.constraint.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ContentConstraint;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ContentConstraintCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.RegionReference;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.constraint.api.ConstraintsService;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(ConstraintsService.BEAN_ID)
public class ConstraintsServiceImpl implements ConstraintsService {

    private final String           SPACE        = " ";
    private final String           DOUBLE_QUOTE = "\"";

    @Autowired
    private SrmRestInternalService srmRestInternalService;

    @Override
    public List<ResourceInternal> findContentConstraintsForArtefact(ServiceContext ctx, String artefactUrn) throws MetamacException {
        StringBuilder queryBuilder = new StringBuilder(ContentConstraintCriteriaPropertyRestriction.ARTEFACT_URN.value());
        queryBuilder.append(SPACE).append(ComparisonOperator.EQ).append(SPACE).append(DOUBLE_QUOTE).append(artefactUrn).append(DOUBLE_QUOTE);

        return srmRestInternalService.findContentConstraints(queryBuilder.toString(), Boolean.TRUE);
    }

    @Override
    public ContentConstraint createContentConstraint(ServiceContext ctx, ContentConstraint contentConstraint) throws MetamacException {
        return srmRestInternalService.saveContentConstraint(ctx, contentConstraint);
    }

    @Override
    public ContentConstraint retrieveContentConstraintByUrn(ServiceContext ctx, String urn, Boolean includeDraft) throws MetamacException {
        return srmRestInternalService.retrieveContentConstraintByUrn(urn, includeDraft);
    }

    @Override
    public void deleteContentConstraint(ServiceContext ctx, String urn) throws MetamacException {
        srmRestInternalService.deleteContentConstraint(ctx, urn, Boolean.FALSE);
    }

    @Override
    public void deleteContentConstraintsForArtefactUrn(ServiceContext ctx, String artefactUrn) throws MetamacException {
        List<ResourceInternal> contentConstraintsForArtefact = findContentConstraintsForArtefact(ctx, artefactUrn);
        for (ResourceInternal resourceInternal : contentConstraintsForArtefact) {
            srmRestInternalService.deleteContentConstraint(ctx, resourceInternal.getUrn(), Boolean.TRUE); // Force to remove final constraints
        }
    }

    @Override
    public void versioningContentConstraintsForArtefact(ServiceContext ctx, String artefactUrn, VersionTypeEnum versionTypeEnum) throws MetamacException {
        srmRestInternalService.versioningContentConstraintsForArtefact(ctx, artefactUrn, versionTypeEnum);
    }

    @Override
    public void publishContentConstraint(ServiceContext ctx, String urn, Boolean alsoMarkAsPublic) throws MetamacException {
        srmRestInternalService.publishContentConstraint(ctx, urn, alsoMarkAsPublic);
    }

    @Override
    public RegionReference retrieveRegionForContentConstraint(ServiceContext ctx, String contentConstraintUrn, String regionCode) throws MetamacException {
        return srmRestInternalService.retrieveRegionForContentConstraint(contentConstraintUrn, regionCode, Boolean.TRUE);
    }

    @Override
    public RegionReference saveRegionForContentConstraint(ServiceContext ctx, RegionReference regionReference) throws MetamacException {
        return srmRestInternalService.saveRegionForContentConstraint(ctx, regionReference);
    }

    @Override
    public void deleteRegion(ServiceContext ctx, String contentConstraintUrn, String regionCode) throws MetamacException {
        srmRestInternalService.deleteRegion(ctx, contentConstraintUrn, regionCode);
    }
}
