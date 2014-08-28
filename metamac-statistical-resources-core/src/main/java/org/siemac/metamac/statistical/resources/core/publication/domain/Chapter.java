package org.siemac.metamac.statistical.resources.core.publication.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.RandomStringUtils;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;

/**
 * Publication chapter
 */
@Entity
@Table(name = "TB_CHAPTERS")
public class Chapter extends ChapterBase {

    private static final long serialVersionUID = 1L;

    private static int        CODE_MAX_LENGTH  = 10;

    public Chapter() {
    }

    public void fillCodeAndUrn() {
        String code = RandomStringUtils.randomAlphanumeric(CODE_MAX_LENGTH);
        this.getNameableStatisticalResource().setCode(code);
        this.getNameableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionChapterUrn(code));
    }

}
