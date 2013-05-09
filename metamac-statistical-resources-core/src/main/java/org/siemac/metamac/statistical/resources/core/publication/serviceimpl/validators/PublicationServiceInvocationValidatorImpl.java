package org.siemac.metamac.statistical.resources.core.publication.serviceimpl.validators;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseInvocationValidator;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public class PublicationServiceInvocationValidatorImpl extends BaseInvocationValidator {

    public static void checkCreatePublicationVersion(PublicationVersion publicationVersion, ExternalItem statisticalOperation, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkNewPublicationVersion(publicationVersion, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(statisticalOperation, ServiceExceptionParameters.PUBLICATION_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE__STATISTICAL_OPERATION, exceptions);
    }

    public static void checkUpdatePublicationVersion(PublicationVersion publicationVersion, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkExistingPublicationVersion(publicationVersion, exceptions);
    }

    public static void checkRetrievePublicationVersionByUrn(String publicationVersionUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(publicationVersionUrn, ServiceExceptionSingleParameters.URN, exceptions);
    }

    public static void checkRetrievePublicationVersions(String publicationVersionUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(publicationVersionUrn, ServiceExceptionSingleParameters.URN, exceptions);
    }

    public static void checkFindPublicationVersionsByCondition(List<ConditionalCriteria> conditions, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    public static void checkDeletePublicationVersion(String publicationVersionUrn, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(publicationVersionUrn, ServiceExceptionSingleParameters.URN, exceptions);
    }

    public static void checkVersioningPublicationVersion(String publicationVersionUrnToCopy, VersionTypeEnum versionType, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(publicationVersionUrnToCopy, ServiceExceptionSingleParameters.URN, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(versionType, ServiceExceptionParameters.VERSION_TYPE, exceptions);
    }

    private static void checkNewPublicationVersion(PublicationVersion publicationVersion, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(publicationVersion, ServiceExceptionParameters.PUBLICATION_VERSION, exceptions);

        if (publicationVersion == null) {
            return;
        }

        checkNewSiemacMetadataStatisticalResource(publicationVersion.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.PUBLICATION_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE,
                exceptions);
        checkPublicationVersion(publicationVersion, exceptions);

        // Metadata that must be empty for new entities
        StatisticalResourcesValidationUtils.checkMetadataEmpty(publicationVersion.getPublication(), ServiceExceptionParameters.PUBLICATION_VERSION__PUBLICATION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(publicationVersion.getId(), ServiceExceptionParameters.PUBLICATION_VERSION__ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataEmpty(publicationVersion.getVersion(), ServiceExceptionParameters.PUBLICATION_VERSION__VERSION, exceptions);
    }

    public static void checkExistingPublicationVersion(PublicationVersion publicationVersion, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkParameterRequired(publicationVersion, ServiceExceptionParameters.PUBLICATION_VERSION, exceptions);

        if (publicationVersion == null) {
            return;
        }

        checkExistingSiemacMetadataStatisticalResource(publicationVersion.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.PUBLICATION_VERSION, exceptions);
        checkPublicationVersion(publicationVersion, exceptions);

        // Metadata that must be filled for existing entities
        StatisticalResourcesValidationUtils.checkMetadataRequired(publicationVersion.getPublication(), ServiceExceptionParameters.PUBLICATION_VERSION__PUBLICATION, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(publicationVersion.getId(), ServiceExceptionParameters.PUBLICATION_VERSION__ID, exceptions);
        StatisticalResourcesValidationUtils.checkMetadataRequired(publicationVersion.getVersion(), ServiceExceptionParameters.PUBLICATION_VERSION__VERSION, exceptions);
    }

    private static void checkPublicationVersion(PublicationVersion publicationVersion, List<MetamacExceptionItem> exceptions) {
        StatisticalResourcesValidationUtils.checkMetadataRequired(publicationVersion.getUuid(), ServiceExceptionParameters.PUBLICATION_VERSION__UUID, exceptions);
    }
}
