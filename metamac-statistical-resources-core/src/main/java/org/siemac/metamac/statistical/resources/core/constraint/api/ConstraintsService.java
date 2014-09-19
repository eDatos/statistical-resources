package org.siemac.metamac.statistical.resources.core.constraint.api;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ContentConstraint;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.RegionReference;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;

public interface ConstraintsService {

    public static final String BEAN_ID = "constraintsService";

    public List<ResourceInternal> findContentConstraintsForArtefact(ServiceContext ctx, String artefactUrn) throws MetamacException;
    public ContentConstraint createContentConstraint(ServiceContext ctx, ContentConstraint contentConstraint) throws MetamacException;
    public ContentConstraint retrieveContentConstraintByUrn(ServiceContext ctx, String urn, Boolean includeDraft) throws MetamacException;
    public void deleteContentConstraint(ServiceContext ctx, String urn) throws MetamacException;
    public void deleteContentConstraintsForArtefactUrn(ServiceContext ctx, String artefactUrn) throws MetamacException;
    public void versioningContentConstraintsForArtefact(ServiceContext ctx, String artefactUrn, String newAttachmentConstraintUrn, VersionTypeEnum versionTypeEnum) throws MetamacException;
    public void publishContentConstraint(ServiceContext ctx, String urn, Boolean alsoMarkAsPublic) throws MetamacException;
    public void revertContentConstraintsForArtefactToDraft(ServiceContext ctx, String artefactUr) throws MetamacException;

    public RegionReference retrieveRegionForContentConstraint(ServiceContext ctx, String contentConstraintUrn, String regionCode) throws MetamacException;
    public RegionReference saveRegionForContentConstraint(ServiceContext ctx, RegionReference regionReference) throws MetamacException;
    public void deleteRegion(ServiceContext ctx, String contentConstraintUrn, String regionCode) throws MetamacException;
}
