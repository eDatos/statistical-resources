package org.siemac.metamac.statistical.resources.web.shared.criteria;

import org.siemac.metamac.statistical.resources.web.shared.criteria.base.HasSchemeCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.base.HasOnlyLastVersionCriteria;

public class ItemSchemeWebCriteria extends MetamacWebCriteria implements HasOnlyLastVersionCriteria, HasSchemeCriteria {

    private static final long serialVersionUID = 1L;

    protected String          schemeUrn;

    protected boolean         onlyLastVersion;

    public ItemSchemeWebCriteria() {
        super();
    }

    public ItemSchemeWebCriteria(String criteria, String schemeUrn) {
        super(criteria);
        this.schemeUrn = schemeUrn;
    }

    @Override
    public String getSchemeUrn() {
        return schemeUrn;
    }

    @Override
    public void setSchemeUrn(String schemeUrn) {
        this.schemeUrn = schemeUrn;
    }

    @Override
    public boolean isOnlyLastVersion() {
        return onlyLastVersion;
    }

    @Override
    public void setOnlyLastVersion(boolean onlyLastVersion) {
        this.onlyLastVersion = onlyLastVersion;
    };
}
