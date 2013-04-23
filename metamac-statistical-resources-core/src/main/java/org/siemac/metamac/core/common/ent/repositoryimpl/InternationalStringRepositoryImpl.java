package org.siemac.metamac.core.common.ent.repositoryimpl;

import org.apache.commons.collections.CollectionUtils;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import javax.persistence.Query;

/**
 * Repository implementation for InternationalString
 */
@Repository("internationalStringRepository")
public class InternationalStringRepositoryImpl
    extends InternationalStringRepositoryBase {
    public InternationalStringRepositoryImpl() {
    }

    @Override
    public void deleteInternationalStringsEfficiently(Collection<Long> internationalStringToDelete) {
        if (CollectionUtils.isEmpty(internationalStringToDelete)) {
            return;
        }

        StringBuilder internationalStringToDeleteParameter = new StringBuilder();
        int count = 0;
        int countToSqlParameter = 0;
        for (Long id : internationalStringToDelete) {
            countToSqlParameter++;
            internationalStringToDeleteParameter.append(id);
            if (countToSqlParameter == CoreCommonConstants.SQL_IN_CLAUSE_MAXIMUM_NUMBER || count == internationalStringToDelete.size() - 1) {
                // LocalisedString
                Query queryDeleteLocalisedString = getEntityManager().createNativeQuery(
                        "DELETE FROM TB_LOCALISED_STRINGS where INTERNATIONAL_STRING_FK IN (" + internationalStringToDeleteParameter + ")");
                queryDeleteLocalisedString.executeUpdate();
                // InternationalString
                Query queryDeleteInternationalString = getEntityManager().createNativeQuery("DELETE FROM TB_INTERNATIONAL_STRINGS WHERE ID IN (" + internationalStringToDeleteParameter + ")");
                queryDeleteInternationalString.executeUpdate();

                // Reset parameter
                countToSqlParameter = 0;
                internationalStringToDeleteParameter = new StringBuilder();
            } else {
                internationalStringToDeleteParameter.append(",");
            }
            count++;
        }

    }
}
