package org.siemac.metamac.statistical.resources.core.base.validators;

import org.apache.commons.lang3.ArrayUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtils;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public abstract class ProcStatusValidator {




    // @formatter:off
    
    public final static ProcStatusEnum[] procStatusForDeleteResource                       = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED};
    public final static ProcStatusEnum[] procStatusForEditResource                         = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED, ProcStatusEnum.PRODUCTION_VALIDATION, ProcStatusEnum.DIFFUSION_VALIDATION};
    
    public final static ProcStatusEnum[] procStatusForSendResourceToProductionValidation   = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED};
    public final static ProcStatusEnum[] procStatusForSendResourceToDiffusionValidation    = {ProcStatusEnum.PRODUCTION_VALIDATION};
    public final static ProcStatusEnum[] procStatusForSendResourceToValidationRejected     = {ProcStatusEnum.PRODUCTION_VALIDATION, ProcStatusEnum.DIFFUSION_VALIDATION};
    public final static ProcStatusEnum[] procStatusForSendResourceToPublish                = {ProcStatusEnum.DIFFUSION_VALIDATION};
    public final static ProcStatusEnum[] procStatusForSendResourceToVersion                = {ProcStatusEnum.PUBLISHED};
    
    // Query
    public final static ProcStatusEnum[] procStatusForEditQueryVersion                     = ArrayUtils.addAll(procStatusForEditResource, ProcStatusEnum.PUBLISHED);
    
    // DatasetVersion
    public final static ProcStatusEnum[] procStatusForImportDatasourcesInDatasetVersion    = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED};
    
    // Publication
    public final static ProcStatusEnum[] procStatusForEditPublicationStructure             = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED, ProcStatusEnum.PRODUCTION_VALIDATION, ProcStatusEnum.DIFFUSION_VALIDATION};
    
    // @formatter:on

    // --------------------------------------------------------------------
    // StatisticalResources
    // --------------------------------------------------------------------

    public static void checkStatisticalResourceCanBeDeleted(HasLifecycle resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, procStatusForDeleteResource);
    }

    public static void checkStatisticalResourceCanBeEdited(HasLifecycle resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, procStatusForEditResource);
    }

    public static void checkStatisticalResourceCanSendToProductionValidation(HasLifecycle resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, procStatusForSendResourceToProductionValidation);
    }

    public static void checkStatisticalResourceCanSendToDiffusionValidation(HasLifecycle resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, procStatusForSendResourceToDiffusionValidation);
    }

    public static void checkStatisticalResourceCanSendToValidationRejected(HasLifecycle resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, procStatusForSendResourceToValidationRejected);
    }

    public static void checkStatisticalResourceCanSendToPublish(HasLifecycle resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, procStatusForSendResourceToPublish);
    }

    public static void checkStatisticalResourceCanSendToVersion(HasLifecycle resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, procStatusForSendResourceToVersion);
    }

    // --------------------------------------------------------------------
    // QueryVersion
    // --------------------------------------------------------------------
    public static void checkQueryVersionCanBeEdited(HasLifecycle resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, procStatusForEditQueryVersion);
    }

    // --------------------------------------------------------------------
    // DatasetVersion
    // --------------------------------------------------------------------
    public static void checkDatasetVersionCanImportDatasources(HasLifecycle resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, procStatusForImportDatasourcesInDatasetVersion);
    }

    // --------------------------------------------------------------------
    // PublicationVersion
    // --------------------------------------------------------------------
    public static void checkStatisticalResourceStructureCanBeEdited(PublicationVersion resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, procStatusForEditPublicationStructure);
    }
}
