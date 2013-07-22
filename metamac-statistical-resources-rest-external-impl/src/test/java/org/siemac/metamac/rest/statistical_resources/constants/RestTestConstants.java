package org.siemac.metamac.rest.statistical_resources.constants;

import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.rest.common.v1_0.domain.OrderOperator;

public class RestTestConstants {

    public static String NOT_EXISTS                  = "notexists";
    public static String AGENCY_1                    = "agency1";
    public static String AGENCY_2                    = "agency2";

    public static String DATASET_1_CODE              = "dataset1";
    public static String DATASET_2_CODE              = "dataset2";
    public static String DATASET_3_CODE              = "dataset3";
    public static String DATASET_4_CODE              = "dataset4";

    public static String VERSION_1                   = "01.000";
    public static String VERSION_2                   = "02.000";

    public static String QUERY_ID_LIKE_1             = "ID " + ComparisonOperator.LIKE + " \"1\"";
    public static String QUERY_ID_LIKE_1_NAME_LIKE_2 = "ID " + ComparisonOperator.LIKE + " \"1\"" + " " + LogicalOperator.AND + " " + "NAME " + ComparisonOperator.LIKE + " \"2\"";
    public static String QUERY_LATEST                = "LATEST " + ComparisonOperator.EQ + " \"true\"";

    public static String ORDER_BY_ID_DESC            = "ID " + OrderOperator.DESC;
}
