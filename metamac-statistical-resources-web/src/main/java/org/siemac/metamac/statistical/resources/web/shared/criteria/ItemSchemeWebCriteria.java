package org.siemac.metamac.statistical.resources.web.shared.criteria;

import org.siemac.metamac.statistical.resources.web.shared.criteria.base.HasSchemeCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacVersionableWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class ItemSchemeWebCriteria extends MetamacVersionableWebCriteria implements HasSchemeCriteria {

    private static final long serialVersionUID = 1L;

    protected String          schemeUrn;

    public ItemSchemeWebCriteria() {
        super();
    }

    public ItemSchemeWebCriteria(MetamacWebCriteria metamacWebCriteria) {
        super();
        if (metamacWebCriteria != null) {
            setCriteria(metamacWebCriteria.getCriteria());
        }
        setOnlyLastVersion(false);
    }

    public ItemSchemeWebCriteria(String criteria, Boolean onlyLastVersion, String schemeUrn) {
        super(criteria, onlyLastVersion);
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
}
