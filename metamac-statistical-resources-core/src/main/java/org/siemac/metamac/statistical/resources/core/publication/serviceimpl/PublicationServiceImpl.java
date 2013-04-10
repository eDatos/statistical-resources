package org.siemac.metamac.statistical.resources.core.publication.serviceimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.components.SiemacStatisticalResourceGeneratedCode;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForCreateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForVersioningResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseValidator;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.serviceapi.validators.PublicationServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.publication.utils.PublicationVersioningCopyUtils;
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
    SiemacStatisticalResourceGeneratedCode    siemacStatisticalResourceGeneratedCode;

    @Autowired
    PublicationServiceInvocationValidator     publicationServiceInvocationValidator;

    public PublicationServiceImpl() {
    }

    @Override
    public PublicationVersion createPublicationVersion(ServiceContext ctx, PublicationVersion publicationVersion, ExternalItem statisticalOperation) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkCreatePublicationVersion(ctx, publicationVersion, statisticalOperation);

        // Create publication
        Publication publication = new Publication();
        publication.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());

        // Fill metadata
        fillMetadataForCreatePublicationVersion(publicationVersion, publication, statisticalOperation, ctx);

        // Save version
        publicationVersion.setPublication(publication);

        // Add version to publication
        assignCodeAndSavePublicationVersion(publication, publicationVersion);
        publicationVersion = getPublicationVersionRepository().retrieveByUrn(publicationVersion.getSiemacMetadataStatisticalResource().getUrn());

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
        conditions = CriteriaUtils.initConditions(conditions, PublicationVersion.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

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
        BaseValidator.checkStatisticalResourceCanBeDeleted(publicationVersion.getSiemacMetadataStatisticalResource());

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
            previousPublicationVersion.getSiemacMetadataStatisticalResource().setIsReplacedBy(null);
            getPublicationVersionRepository().save(previousPublicationVersion);
        }
    }

    @Override
    public PublicationVersion versioningPublicationVersion(ServiceContext ctx, String publicationVersionUrnToCopy, VersionTypeEnum versionType) throws MetamacException {
        publicationServiceInvocationValidator.checkVersioningPublicationVersion(ctx, publicationVersionUrnToCopy, versionType);
        
        //TODO: check only published publications can be versioned
        
        PublicationVersion publicationVersionToCopy = retrievePublicationVersionByUrn(ctx, publicationVersionUrnToCopy);
        PublicationVersion publicationNewVersion = PublicationVersioningCopyUtils.copyPublicationVersion(publicationVersionToCopy);
        
        FillMetadataForVersioningResourceUtils.fillMetadataForVersioningSiemacResource(ctx, publicationVersionToCopy.getSiemacMetadataStatisticalResource(), publicationNewVersion.getSiemacMetadataStatisticalResource(), versionType);

        //PUBLICATION URN
        String[] creator = new String[]{publicationNewVersion.getSiemacMetadataStatisticalResource().getCreator().getCode()};
        publicationNewVersion.getSiemacMetadataStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionVersionUrn(creator, publicationNewVersion.getSiemacMetadataStatisticalResource().getCode(), publicationNewVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        
        //TODO: DATE_NEXT_UPDATE
        
        publicationNewVersion = getPublicationVersionRepository().save(publicationNewVersion);
        
        return publicationNewVersion;
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private static void fillMetadataForCreatePublicationVersion(PublicationVersion publicationVersion, Publication publication, ExternalItem statisticalOperation, ServiceContext ctx) {
        publicationVersion.setPublication(publication);
        FillMetadataForCreateResourceUtils.fillMetadataForCretateSiemacResource(publicationVersion.getSiemacMetadataStatisticalResource(), statisticalOperation,
                StatisticalResourceTypeEnum.COLLECTION, ctx);
    }

    private synchronized Publication assignCodeAndSavePublicationVersion(Publication publication, PublicationVersion publicationVersion) throws MetamacException {
        String code = siemacStatisticalResourceGeneratedCode.fillGeneratedCodeForCreateSiemacMetadataResource(publicationVersion.getSiemacMetadataStatisticalResource());
        String[] maintainer = new String[]{publicationVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCode()};
        
        //Fill code and urn for root and version
        publication.getIdentifiableStatisticalResource().setCode(code);
        publication.getIdentifiableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionUrn(maintainer, publication.getIdentifiableStatisticalResource().getCode()));
        
        publicationVersion.getSiemacMetadataStatisticalResource().setCode(code);
        publicationVersion.getSiemacMetadataStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionVersionUrn(maintainer, publicationVersion.getSiemacMetadataStatisticalResource().getCode(), publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));

        // Checks
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(publicationVersion.getSiemacMetadataStatisticalResource());

        // Add version to publication
        publication.addVersion(publicationVersion);
        return getPublicationRepository().save(publicationVersion.getPublication());
    }

}
