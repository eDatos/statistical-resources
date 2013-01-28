package org.siemac.metamac.statistical.resources.core.publication.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseValidator;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.serviceapi.validators.PublicationServiceInvocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of PublicationService.
 */
@Service("publicationService")
public class PublicationServiceImpl extends PublicationServiceImplBase {

    @Autowired
    IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;

    @Autowired
    PublicationServiceInvocationValidator     publicationServiceInvocationValidator;

    public PublicationServiceImpl() {
    }

    @Override
    public PublicationVersion createPublicationVersion(ServiceContext ctx, PublicationVersion publicationVersion) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkCreatePublicationVersion(ctx, publicationVersion);

        // Create publication
        Publication publication = new Publication();
        publication = getPublicationRepository().save(publication);

        // Fill metadata
        fillMetadataForCreatePublicationVersion(publicationVersion, publication);

        // Checks
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(publicationVersion.getSiemacMetadataStatisticalResource());

        // Save version
        publicationVersion.setPublication(publication);

        // Add version to publication
        publication.addVersion(publicationVersion);
        getPublicationRepository().save(publication);
        publicationVersion = getPublicationVersionRepository().save(publicationVersion);

        return publicationVersion;
    }

    @Override
    public PublicationVersion updatePublicationVersion(ServiceContext ctx, PublicationVersion publicationVersion) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkUpdatePublicationVersion(ctx, publicationVersion);

        // Check status
        BaseValidator.checkStatisticalResourceCanBeEdited(publicationVersion.getSiemacMetadataStatisticalResource());

        // TODO: Comprobar si el código ha cambiado, si puede cambair y si sigue siendo unico (ver ConceptsServiceImpl.java)
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(publicationVersion.getSiemacMetadataStatisticalResource());
        // TODO: Si el codigo ha cambiado debemos actualizar la URN

        // TODO: ACtualizar la fecha del optimistic locking

        publicationVersion = getPublicationVersionRepository().save(publicationVersion);
        return publicationVersion;
    }

    @Override
    public PublicationVersion retrievePublicationVersionByUrn(ServiceContext ctx, String publicationVersionUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkRetrievePublicationVersionByUrn(ctx, publicationVersionUrn);

        // Retrieve
        PublicationVersion publicationVersion = getPublicationVersionRepository().retrieveByUrn(publicationVersionUrn);
        return publicationVersion;
    }

    @Override
    public List<PublicationVersion> retrievePublicationVersions(ServiceContext ctx, String publicationVersionUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkRetrievePublicationVersions(ctx, publicationVersionUrn);

        // Retrieve
        List<PublicationVersion> publicationVersions = getPublicationVersionRepository().retrieveByUrn(publicationVersionUrn).getPublication().getVersions();

        return publicationVersions;
    }

    @Override
    public PagedResult<PublicationVersion> findPublicationVersionsByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkFindPublicationVersionsByCondition(ctx, conditions, pagingParameter);

        // Find
        if (conditions == null) {
            conditions = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class).distinctRoot().build();
        }

        PagedResult<PublicationVersion> publicationVersionPagedResult = getPublicationVersionRepository().findByCondition(conditions, pagingParameter);
        return publicationVersionPagedResult;
    }

    @Override
    public void deletePublicationVersion(ServiceContext ctx, String publicationVersionUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkDeletePublicationVersion(ctx, publicationVersionUrn);

        // Retrieve version to delete
        PublicationVersion publicationVersion = retrievePublicationVersionByUrn(ctx, publicationVersionUrn);

        // Check can be deleted
        // TODO: Determinar cuales son los estados en los que se puede borrar
        BaseValidator.checkStatisticalResourceCanBeEdited(publicationVersion.getSiemacMetadataStatisticalResource());

        // TODO: Determinar si hay algunas comprobaciones que impiden el borrado

        // TODO: Comprobar si hay que eliminar relaciones a otros recursos

        if (VersionUtil.isInitialVersion(publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic())) {
            Publication publication = publicationVersion.getPublication();
            getPublicationRepository().delete(publication);
        } else {
            // Delete version
            Publication publication = publicationVersion.getPublication();
            publication.getVersions().remove(publicationVersion);
            getPublicationVersionRepository().delete(publicationVersion);

            // Update previous version
            PublicationVersion previousPublicationVersion = getPublicationVersionRepository().retrieveByUrn(publicationVersion.getSiemacMetadataStatisticalResource().getReplacesVersion().getUrn());
            previousPublicationVersion.getSiemacMetadataStatisticalResource().setIsLastVersion(Boolean.TRUE);
            previousPublicationVersion.getSiemacMetadataStatisticalResource().setIsReplacedBy(null);
            getPublicationVersionRepository().save(previousPublicationVersion);
        }
    }

    @Override
    public PublicationVersion versioningPublicationVersion(ServiceContext ctx, String publicationVersionUrnToCopy, VersionTypeEnum versionType) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("versioningPublicationVersion not implemented");
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private static void fillMetadataForCreatePublicationVersion(PublicationVersion publicationVersion, Publication publication) {
        publicationVersion.setPublication(publication);
        // TODO: Cuando se sepa como se construye la URN llamar al método correspondiente en el GeneratorUrnUtils
        publicationVersion.getSiemacMetadataStatisticalResource().setUrn("TODO:mock");
        publicationVersion.getSiemacMetadataStatisticalResource().setUri(null);
        publicationVersion.getSiemacMetadataStatisticalResource().setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        publicationVersion.getSiemacMetadataStatisticalResource().setVersionLogic("01.000");
    }

}
