package org.siemac.metamac.statistical.resources.web.shared.criteria;

public class SiemacMetadataStatisticalResourceWebCriteria extends LifeCycleStatisticalResourceWebCriteria {

    private static final long serialVersionUID = 1L;

    private String            titleAlternative;

    public SiemacMetadataStatisticalResourceWebCriteria() {
        super();
    }

    SiemacMetadataStatisticalResourceWebCriteria(String criteria) {
        super(criteria);
    }

    public String getTitleAlternative() {
        return titleAlternative;
    }

    public void setTitleAlternative(String titleAlternative) {
        this.titleAlternative = titleAlternative;
    }
}
