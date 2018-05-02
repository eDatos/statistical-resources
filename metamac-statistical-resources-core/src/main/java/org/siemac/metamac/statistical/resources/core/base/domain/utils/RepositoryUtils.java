package org.siemac.metamac.statistical.resources.core.base.domain.utils;

import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

public class RepositoryUtils {

//@formatter:off
    public static final String isLastPublishedVersionConditions =
        "( " +
            " stat.proc_status = :publishedProcStatus " +
            "AND stat.Valid_From <= :now " +
            "AND (stat.Valid_To > :now or stat.Valid_To is null)" +
        ")";
    //@formatter:on

    public static String buildLastPublishedVersionCondition(String statisticalResourceName) {
        // @formatter:off
        return "( " +
        " "+statisticalResourceName+".proc_status = :publishedProcStatus " +
        "AND "+statisticalResourceName+".Valid_From <= :now " +
        "AND ("+statisticalResourceName+".Valid_To > :now or "+statisticalResourceName+".Valid_To is null)" +
        ")";
        //@formatter:on

    }

    // METAMAC-2718 - Eliminar código muerto para evitar mantenerlo
    public static String buildRelatedResourceForeignKeyBasedOnType(TypeRelatedResourceEnum type) {
        switch (type) {
            case DATASET_VERSION:
                return "related.dataset_version_fk";
            case QUERY_VERSION:
                return "related.query_version_fk";
            case PUBLICATION_VERSION:
                return "related.publication_version_fk";
            default:
                throw new UnsupportedOperationException("Resource of type " + type + " is not supported");
        }
    }
}
