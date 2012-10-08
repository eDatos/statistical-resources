package org.siemac.metamac.statistical.resources.core.serviceapi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Spring based transactional test with DbUnit support.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({QueryRepositoryTest.class, StatisticalResourceServiceTest.class})
public class StatisticalResourcesSuite {
}
