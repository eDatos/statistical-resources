package org.siemac.metamac.statistical.resources.web.client.dataset.model.ds;

import org.siemac.metamac.statistical.resources.web.client.ResourcesWeb;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class DatasetDS extends DataSource {

    // IDENTIFIERS
    public static final String ID                         = "ds-id";
    public static final String IDENTIFIER                 = "ds-identifier";
    public static final String IDENTIFIER_VIEW            = "ds-identifier-view"; // NOT MAPPED
    public static final String URI                        = "ds-uri";
    public static final String URN                        = "ds-urn";
    public static final String VERSION_LOGIC              = "ds-version";
    public static final String TITLE                       = "ds-title";
    // CONTENT DESCRIPTORS
    public static final String DESCRIPTION                = "ds-desc";
    // CLASS DESCRIPTORS
    // PRODUCTION DESCRIPTORS
    public static final String PROC_STATUS                = "ds-status";
    public static final String PRODUCTION_VALIDATION_DATE = "ds-prod-date";
    public static final String PRODUCTION_VALIDATION_USER = "ds-prod-user";
    // DIFFUSION DESCRIPTORS
    public static final String VALID_FROM                 = "ds-valid-from";
    public static final String VALID_TO                   = "ds-valid-to";
    public static final String DIFFUSION_VALIDATION_DATE  = "ds-dif-date";
    public static final String DIFFUSION_VALIDATION_USER  = "ds-dif-user";

    public static String       DTO                        = "ds-dto";

    public DatasetDS() {
        DataSourceIntegerField code = new DataSourceIntegerField(IDENTIFIER, ResourcesWeb.getConstants().datasetIdentifier());
        code.setPrimaryKey(true);
        addField(code);
    }

}
