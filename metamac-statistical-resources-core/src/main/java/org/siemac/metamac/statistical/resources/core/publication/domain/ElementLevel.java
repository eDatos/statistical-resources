package org.siemac.metamac.statistical.resources.core.publication.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Publication level
 */
@Entity
@Table(name = "TB_ELEMENTS_LEVELS", uniqueConstraints = {@UniqueConstraint(columnNames = {"ORDER_IN_LEVEL", "PUBLICATION_VERSION_FIRST_FK", "PARENT_FK"})})
public class ElementLevel extends ElementLevelBase {

    private static final long serialVersionUID = 1L;

    public ElementLevel() {
    }

    /**
     * @return True when element is a chapter
     */
    public Boolean isChapter() {
        return getChapter() != null;
    }

    /**
     * @return True when element is a publication cube (table)
     */
    public Boolean isCube() {
        return getCube() != null;
    }

    /**
     * If exists, retrieves uuid of parent chapter
     */
    public String getParentUuid() {
        return super.getParent() != null ? super.getParent().getChapter().getUuid() : null;
    }

    public String getElementUuid() {
        if (this.getChapter() != null) {
            return this.getChapter().getUuid();
        } else if (this.getCube() != null) {
            return this.getCube().getUuid();
        }
        return null;
    }
}
