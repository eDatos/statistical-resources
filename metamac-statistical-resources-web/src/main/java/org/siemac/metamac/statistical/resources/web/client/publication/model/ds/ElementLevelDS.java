package org.siemac.metamac.statistical.resources.web.client.publication.model.ds;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class ElementLevelDS extends DataSource {

    public static final String TITLE                 = "str-title";
    public static final String DESCRIPTION           = "str-description";
    public static final String ORDER_IN_LEVEL        = "str-order-level";
    public static final String PARENT_CHAPTER_URN    = "str-parent-chap-urn";
    public static final String URN                   = "str-urn";

    // only suitable for cubes (not chapters!)
    public static final String RESOURCE_TYPE_TO_LINK = "str-type-link";      // Not mapped in DTO
    public static final String DATASET               = "str-dataset";
    public static final String QUERY                 = "str-query";

    public static final String INFO                  = "str-info";

    public static final String DTO                   = "str-dto";

    public ElementLevelDS() {
        DataSourceIntegerField identifier = new DataSourceIntegerField(URN, getConstants().publicationStructureElementURN());
        identifier.setPrimaryKey(true);
        addField(identifier);
    }
}
