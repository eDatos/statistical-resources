package org.siemac.metamac.statistical.resources.core.publication.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Publication cube
 */
@Entity
@Table(name = "TB_CUBES")
public class Cube extends CubeBase {

    private static final long serialVersionUID = 1L;

    public Cube() {
    }
}
