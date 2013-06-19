package org.siemac.metamac.statistical.resources.core.dataset.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;

/**
 * Entity representing DatasetVersion.
 * <p>
 * This class is responsible for the domain object related
 * business logic for DatasetVersion. Properties and associations are
 * implemented in the generated base class {@link org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionBase}.
 */
@Entity
@Table(name = "TB_DATASETS_VERSIONS")
public class DatasetVersion extends DatasetVersionBase implements HasSiemacMetadata {
    private static final long serialVersionUID = 1L;

    public DatasetVersion() {
    }

    @Override
    public LifeCycleStatisticalResource getLifeCycleStatisticalResource() {
        return getSiemacMetadataStatisticalResource();
    }
    
    public List<String> getTemporalCoverageList() {
        List<String> codes = new ArrayList<String>();
        if (!StringUtils.isEmpty(getTemporalCoverage())) {
            String[] timeCodes = getTemporalCoverage().split("#");
            for (String code : timeCodes) {
                codes.add(code);
            }
        }
        return codes;
    }
    
    public void setTemporalCoverageList(List<String> codes) {
        if (codes != null && codes.size() > 0) {
            setTemporalCoverage(StringUtils.join(codes,"#"));
        } else {
            setTemporalCoverage(null);
        }
    }
}
