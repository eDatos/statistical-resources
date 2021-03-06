package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkParameterRequired;
import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.List;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.shared.BooleanUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker.ExternalItemChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker.RelatedResourceChecker;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LifecycleChecker {

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @Autowired
    private ExternalItemChecker            externalItemChecker;

    @Autowired
    private RelatedResourceChecker         relatedResourceChecker;

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToProductionValidation(HasLifecycle resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
        checkLifeCycleMetadataSendToProductionValidation(resource, metadataName, exceptionItems);
    }

    private void checkLifeCycleMetadataSendToProductionValidation(HasLifecycle resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        if (StatisticalResourcesVersionUtils.isInitialVersion(resource)) {
            if (!checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(resource)) {
                exceptionItems.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_INCORRECT, addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES)));
            }
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToDiffusionValidation(HasLifecycle resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToValidationRejected(HasLifecycle resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToPublished(HasLifecycle resource, HasLifecycle previousVersion, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        if (!StatisticalResourcesVersionUtils.isInitialVersion(resource)) {
            checkParameterRequired(previousVersion, ServiceExceptionParameters.PREVIOUS_VERSION, exceptionItems);
        }

        checkLifeCycleMetadataAllActions(resource, metadataName, exceptionItems);

        // Statistical Operation
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getLifeCycleStatisticalResource().getStatisticalOperation(),
                addParameter(metadataName, ServiceExceptionSingleParameters.STATISTICAL_OPERATION), exceptionItems);

        // Maintainer
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getLifeCycleStatisticalResource().getMaintainer(), addParameter(metadataName, ServiceExceptionSingleParameters.MAINTAINER),
                exceptionItems);

        // Replaces Version
        if (resource.getLifeCycleStatisticalResource().getReplacesVersion() != null) {
            relatedResourceChecker.checkRelatedResourceExternallyPublished(resource.getLifeCycleStatisticalResource().getReplacesVersion(), resource.getLifeCycleStatisticalResource().getValidFrom(),
                    addParameter(metadataName, ServiceExceptionSingleParameters.REPLACES_VERSION), exceptionItems);
        }

        // Is replaced by version: It can be private. API checks that can be returned.
    }

    // ------------------------------------------------------------------------------------------------------
    // VERSIONING
    // ------------------------------------------------------------------------------------------------------

    public void checkVersioning(HasLifecycle resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // We have to check that if it already exists a posterior version, we can not versioning.
        // A simple way to do this checking is verify that this is the last version of the resource.
        if (BooleanUtils.isNotTrue(resource.getLifeCycleStatisticalResource().getLastVersion())) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT, addParameter(metadataName, ServiceExceptionSingleParameters.LAST_VERSION)));
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // PROTECTED COMMON METHODS
    // ------------------------------------------------------------------------------------------------------

    protected boolean checkOnlyCanHaveNewResourceAsVersionRationaleTypeIfAny(HasLifecycle resource) {
        if (resource.getLifeCycleStatisticalResource().getVersionRationaleTypes().size() == 1) {
            return VersionRationaleTypeEnum.MAJOR_NEW_RESOURCE.equals(resource.getLifeCycleStatisticalResource().getVersionRationaleTypes().get(0).getValue());
        } else if (resource.getLifeCycleStatisticalResource().getVersionRationaleTypes().size() == 0) {
            return true;
        }
        return false;
    }

    /*
     * This is a metadata core that should always be checked, this validations are always needed independently of the action
     */
    protected void checkLifeCycleMetadataAllActions(HasLifecycle resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        lifecycleCommonMetadataChecker.checkLifecycleCommonMetadata(resource, metadataName, exceptionItems);
    }
}
