package org.siemac.metamac.statistical.resources.core.base.constants;

import org.apache.commons.lang3.ArrayUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.BaseEnumUtils;

public class ProcStatusForActionsConstants {

    // ----------------------------------------------------------------------
    // ARRAYS
    // ----------------------------------------------------------------------

    // @formatter:off

    protected final static ProcStatusEnum[] procStatusForDeleteResource                       = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED};
    protected final static ProcStatusEnum[] procStatusForEditResource                         = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED, ProcStatusEnum.PRODUCTION_VALIDATION, ProcStatusEnum.DIFFUSION_VALIDATION};

    protected final static ProcStatusEnum[] procStatusForSendResourceToProductionValidation   = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED};
    protected final static ProcStatusEnum[] procStatusForSendResourceToDiffusionValidation    = {ProcStatusEnum.PRODUCTION_VALIDATION};
    protected final static ProcStatusEnum[] procStatusForSendResourceToValidationRejected     = {ProcStatusEnum.PRODUCTION_VALIDATION, ProcStatusEnum.DIFFUSION_VALIDATION};
    protected final static ProcStatusEnum[] procStatusForSendResourceToPublish                = {ProcStatusEnum.DIFFUSION_VALIDATION};
    protected final static ProcStatusEnum[] procStatusForSendResourceToVersion                = {ProcStatusEnum.PUBLISHED};

    // Query
    protected final static ProcStatusEnum[] procStatusForEditQueryVersion                     = ArrayUtils.addAll(procStatusForEditResource, ProcStatusEnum.PUBLISHED);

    // DatasetVersion
    protected final static ProcStatusEnum[] procStatusForImportDatasourcesInDatasetVersion    = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED};

    // Publication
    protected final static ProcStatusEnum[] procStatusForEditPublicationStructure             = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED, ProcStatusEnum.PRODUCTION_VALIDATION, ProcStatusEnum.DIFFUSION_VALIDATION};

    // Multidataset
    protected final static ProcStatusEnum[] procStatusForEditMultidatasetStructure             = {ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED, ProcStatusEnum.PRODUCTION_VALIDATION, ProcStatusEnum.DIFFUSION_VALIDATION};

    // @formatter:on

    // ----------------------------------------------------------------------
    // STRINGS
    // ----------------------------------------------------------------------

    // Statistical Resources
    public final static String              PROC_STATUS_FOR_DELETE_RESOURCE                        = BaseEnumUtils.enumToString(procStatusForDeleteResource);

    public final static String              PROC_STATUS_FOR_EDIT_RESOURCE                          = BaseEnumUtils.enumToString(procStatusForEditResource);

    public final static String              PROC_STATUS_FOR_SEND_RESOURCE_TO_PRODUCTION_VALIDATION = BaseEnumUtils.enumToString(procStatusForSendResourceToProductionValidation);
    public final static String              PROC_STATUS_FOR_SEND_RESOURCE_TO_DIFFUSION_VALIDATION  = BaseEnumUtils.enumToString(procStatusForSendResourceToDiffusionValidation);
    public final static String              PROC_STATUS_FOR_SEND_RESOURCE_TO_VALIDATION_REJECTED   = BaseEnumUtils.enumToString(procStatusForSendResourceToValidationRejected);
    public final static String              PROC_STATUS_FOR_SEND_RESOURCE_TO_PUBLISH               = BaseEnumUtils.enumToString(procStatusForSendResourceToPublish);
    public final static String              PROC_STATUS_FOR_SEND_RESOURCE_TO_VERSION               = BaseEnumUtils.enumToString(procStatusForSendResourceToVersion);

    // Query
    public final static String              PROC_STATUS_FOR_EDIT_QUERY_VERSION                     = BaseEnumUtils.enumToString(procStatusForEditQueryVersion);

    // DatasetVersion
    public final static String              PROC_STATUS_FOR_IMPORT_DATASOURCES_IN_DATASET_VERSION  = BaseEnumUtils.enumToString(procStatusForImportDatasourcesInDatasetVersion);

    // Publication
    public final static String              PROC_STATUS_FOR_EDIT_PUBLICATION_STRUCTURE             = BaseEnumUtils.enumToString(procStatusForEditPublicationStructure);

    // Multidataset
    public final static String              PROC_STATUS_FOR_EDIT_MULTIDATASET_STRUCTURE            = BaseEnumUtils.enumToString(procStatusForEditMultidatasetStructure);
}
