package org.siemac.metamac.statistical.resources.core.multidataset.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.components.SiemacStatisticalResourceGeneratedCode;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForCreateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.ProcStatusValidator;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.Multidataset;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;
import org.siemac.metamac.statistical.resources.core.multidataset.serviceapi.validators.MultidatasetServiceInvocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of MultidatasetService.
 */
@Service("multidatasetService")
public class MultidatasetServiceImpl extends MultidatasetServiceImplBase {

    @Autowired
    private IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;

    @Autowired
    private SiemacStatisticalResourceGeneratedCode    siemacStatisticalResourceGeneratedCode;

    @Autowired
    private MultidatasetServiceInvocationValidator    multidatasetServiceInvocationValidator;

    public MultidatasetServiceImpl() {
    }

    @Override
    public MultidatasetVersion createMultidatasetVersion(ServiceContext ctx, MultidatasetVersion multidatasetVersion, ExternalItem statisticalOperation) throws MetamacException {
        // Validations
        multidatasetServiceInvocationValidator.checkCreateMultidatasetVersion(ctx, multidatasetVersion, statisticalOperation);

        // Create multidataset
        Multidataset multidataset = new Multidataset();
        fillMetadataForCreateMultidataset(multidataset, statisticalOperation, ctx);

        // Fill metadata
        fillMetadataForCreateMultidatasetVersion(multidatasetVersion, statisticalOperation, ctx);

        // Add version to multidataset and Save version
        multidatasetVersion.setMultidataset(multidataset);
        assignCodeAndSaveMultidatasetVersion(multidataset, multidatasetVersion);

        // Retrieve multidatasetVersion
        multidatasetVersion = getMultidatasetVersionRepository().retrieveByUrn(multidatasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        return multidatasetVersion;
    }

    @Override
    public MultidatasetVersion updateMultidatasetVersion(ServiceContext ctx, MultidatasetVersion multidatasetVersion) throws MetamacException {
        // Validations
        multidatasetServiceInvocationValidator.checkUpdateMultidatasetVersion(ctx, multidatasetVersion);

        // Check status
        ProcStatusValidator.checkStatisticalResourceCanBeEdited(multidatasetVersion);

        // It's not necessary recreate URN because the code is assigned in the create service and never change.
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(multidatasetVersion.getSiemacMetadataStatisticalResource());

        multidatasetVersion = getMultidatasetVersionRepository().save(multidatasetVersion);
        return multidatasetVersion;
    }

    @Override
    public MultidatasetVersion retrieveMultidatasetVersionByUrn(ServiceContext ctx, String multidatasetVersionUrn) throws MetamacException {
        // Validations
        multidatasetServiceInvocationValidator.checkRetrieveMultidatasetVersionByUrn(ctx, multidatasetVersionUrn);

        // Retrieve
        MultidatasetVersion multidatasetVersion = getMultidatasetVersionRepository().retrieveByUrn(multidatasetVersionUrn);
        return multidatasetVersion;
    }

    @Override
    public MultidatasetVersion retrieveLatestMultidatasetVersionByMultidatasetUrn(ServiceContext ctx, String multidatasetUrn) throws MetamacException {
        // Validations
        multidatasetServiceInvocationValidator.checkRetrieveLatestMultidatasetVersionByMultidatasetUrn(ctx, multidatasetUrn);

        // Retrieve
        MultidatasetVersion multidatasetVersion = getMultidatasetVersionRepository().retrieveLastVersion(multidatasetUrn);
        return multidatasetVersion;
    }

    @Override
    public MultidatasetVersion retrieveLatestPublishedMultidatasetVersionByMultidatasetUrn(ServiceContext ctx, String multidatasetUrn) throws MetamacException {

        // Validations
        multidatasetServiceInvocationValidator.checkRetrieveLatestPublishedMultidatasetVersionByMultidatasetUrn(ctx, multidatasetUrn);

        // Retrieve
        MultidatasetVersion multidatasetVersion = getMultidatasetVersionRepository().retrieveLastPublishedVersion(multidatasetUrn);
        return multidatasetVersion;

    }

    @Override
    public List<MultidatasetVersion> retrieveMultidatasetVersions(ServiceContext ctx, String multidatasetVersionUrn) throws MetamacException {

        // Validations
        multidatasetServiceInvocationValidator.checkRetrieveMultidatasetVersions(ctx, multidatasetVersionUrn);

        // Retrieve
        List<MultidatasetVersion> multidatasetVersions = getMultidatasetVersionRepository().retrieveByUrn(multidatasetVersionUrn).getMultidataset().getVersions();

        return multidatasetVersions;
    }

    @Override
    public PagedResult<MultidatasetVersion> findMultidatasetVersionsByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {

        // Validations
        multidatasetServiceInvocationValidator.checkFindMultidatasetVersionsByCondition(ctx, conditions, pagingParameter);

        // Find
        conditions = CriteriaUtils.initConditions(conditions, MultidatasetVersion.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        PagedResult<MultidatasetVersion> multidatasetVersionPagedResult = getMultidatasetVersionRepository().findByCondition(conditions, pagingParameter);
        return multidatasetVersionPagedResult;

    }

    @Override
    public void deleteMultidatasetVersion(ServiceContext ctx, String multidatasetVersionUrn) throws MetamacException {
        // Validations
        multidatasetServiceInvocationValidator.checkDeleteMultidatasetVersion(ctx, multidatasetVersionUrn);

        // Retrieve version to delete
        MultidatasetVersion multidatasetVersion = retrieveMultidatasetVersionByUrn(ctx, multidatasetVersionUrn);

        // Check can be deleted
        ProcStatusValidator.checkStatisticalResourceCanBeDeleted(multidatasetVersion);

        checkCanMultidatasetVersionBeDeleted(multidatasetVersion);

        if (VersionUtil.isInitialVersion(multidatasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic())) {
            Multidataset multidataset = multidatasetVersion.getMultidataset();
            getMultidatasetRepository().delete(multidataset);
        } else {
            // Previous version
            RelatedResource previousResource = multidatasetVersion.getSiemacMetadataStatisticalResource().getReplacesVersion();
            if (previousResource.getMultidatasetVersion() != null) {
                MultidatasetVersion previousVersion = previousResource.getMultidatasetVersion();
                previousVersion.getSiemacMetadataStatisticalResource().setLastVersion(true);
                previousVersion.getSiemacMetadataStatisticalResource().setIsReplacedByVersion(null);
                getMultidatasetVersionRepository().save(previousVersion);
            }
            // Delete version
            Multidataset multidataset = multidatasetVersion.getMultidataset();
            multidataset.getVersions().remove(multidatasetVersion);
            getMultidatasetVersionRepository().delete(multidatasetVersion);
        }

    }

    private void checkCanMultidatasetVersionBeDeleted(MultidatasetVersion multidatasetVersion) throws MetamacException {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        RelatedResource resourcesIsReplacedBy = multidatasetVersion.getSiemacMetadataStatisticalResource().getIsReplacedBy();
        if (resourcesIsReplacedBy != null) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.MULTIDATASET_VERSION_IS_REPLACED_BY_OTHER_RESOURCE,
                    resourcesIsReplacedBy.getMultidatasetVersion().getLifeCycleStatisticalResource().getUrn()));
        }

        if (exceptionItems.size() > 0) {
            MetamacExceptionItem item = new MetamacExceptionItem(ServiceExceptionType.MULTIDATASET_VERSION_CANT_BE_DELETED, multidatasetVersion.getSiemacMetadataStatisticalResource().getUrn());
            item.setExceptionItems(exceptionItems);
            throw new MetamacException(Arrays.asList(item));
        }
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------
    private static void fillMetadataForCreateMultidataset(Multidataset multidataset, ExternalItem statisticalOperation, ServiceContext ctx) {
        multidataset.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        FillMetadataForCreateResourceUtils.fillMetadataForCreateIdentifiableResource(multidataset.getIdentifiableStatisticalResource(), statisticalOperation);
    }

    private static void fillMetadataForCreateMultidatasetVersion(MultidatasetVersion multidatasetVersion, ExternalItem statisticalOperation, ServiceContext ctx) {
        FillMetadataForCreateResourceUtils.fillMetadataForCretateSiemacResource(multidatasetVersion.getSiemacMetadataStatisticalResource(), statisticalOperation,
                StatisticalResourceTypeEnum.MULTIDATASET, ctx);
    }

    private synchronized Multidataset assignCodeAndSaveMultidatasetVersion(Multidataset multidataset, MultidatasetVersion multidatasetVersion) throws MetamacException {
        String code = siemacStatisticalResourceGeneratedCode.fillGeneratedCodeForCreateSiemacMetadataResource(multidatasetVersion.getSiemacMetadataStatisticalResource());
        String[] maintainer = new String[]{multidatasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};

        // Fill code and urn for root and version
        multidataset.getIdentifiableStatisticalResource().setCode(code);
        multidataset.getIdentifiableStatisticalResource()
                .setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceMultidatasetUrn(maintainer, multidataset.getIdentifiableStatisticalResource().getCode()));

        multidatasetVersion.getSiemacMetadataStatisticalResource().setCode(code);
        multidatasetVersion.getSiemacMetadataStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceMultidatasetVersionUrn(maintainer,
                multidatasetVersion.getSiemacMetadataStatisticalResource().getCode(), multidatasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));

        // Checks
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(multidatasetVersion.getSiemacMetadataStatisticalResource());

        // Add version to multidataset
        multidataset.addVersion(multidatasetVersion);
        return getMultidatasetRepository().save(multidatasetVersion.getMultidataset());
    }
}
