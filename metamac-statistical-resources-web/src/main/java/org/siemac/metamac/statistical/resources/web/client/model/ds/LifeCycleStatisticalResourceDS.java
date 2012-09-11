package org.siemac.metamac.statistical.resources.web.client.model.ds;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class LifeCycleStatisticalResourceDS extends DataSource {

    // IDENTITY
    public static final String ID                          = "lc-id";
    // STATISTICAL RESOURCE
    public static final String OPERATION                   = "lc-op";
    // AUDITABLE
    // public static final String CREATOR_AUDIT = "lc-creator-audit";
    // public static final String DATE_CREATED = "lc-date-creation";
    // public static final String LAST_UPDATE_USER = "lc-update-user";
    // public static final String DATE_LAST_UPDATE = "lc-last-update";
    // IDENTIFIABLE
    public static final String IDENTIFIER                  = "lc-identifier";
    public static final String URI                         = "lc-uri";
    public static final String URN                         = "lc-urn";
    public static final String TITLE                       = "lc-title";
    // VERSIONABLE
    public static final String VERSION_LOGIC               = "lc-version";
    public static final String VERSION_DATE                = "lc-date-version";
    public static final String NEXT_VERSION_DATE           = "lc-next-version";
    public static final String RATIONALE                   = "lc-rationale";
    public static final String RATIONALE_TYPE              = "lc-rationale-type";
    // LIFE CYCLE
    public static final String RESPONSABILITY_CONTRIBUTOR  = "lc-resp-contr";
    public static final String RESPONSABILITY_SUBMITTED    = "lc-resp-subm";
    public static final String RESPONSABILITY_ACCEPTED     = "lc-resp-acc";
    public static final String RESPONSABILITY_ISSUED       = "lc-resp-issued";
    public static final String RESPONSABILITY_OUT_OF_PRINT = "lc-resp-oop";
    public static final String CREATOR                     = "lc-creator";
    public static final String CONTRIBUTOR                 = "lc-contr";
    public static final String PUBLISHER                   = "lc-pub";
    public static final String MEDIATOR                    = "lc-media";
    public static final String SUBMITTED_DATE              = "lc-date-subm";
    public static final String ACCEPTED_DATE               = "lc-date-acc";
    public static final String ISSUED_DATE                 = "lc-date-issued";
    public static final String PROC_STATUS                 = "lc-proc-status";

    public LifeCycleStatisticalResourceDS() {
        DataSourceIntegerField id = new DataSourceIntegerField(ID, "id");
        id.setPrimaryKey(true);
        addField(id);
    }

}
