package org.siemac.metamac.core.common.ent.repositoryimpl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

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
        throw new UnsupportedOperationException("Not supported efficient deletion");
    }
}
