package org.siemac.metamac.statistical.resources.core.common.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.TranslationMockFactory.TRANSLATIONS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.Translation;
import org.siemac.metamac.statistical.resources.core.common.domain.TranslationRepository;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class TranslationRepositoryTest extends StatisticalResourcesBaseTest implements TranslationRepositoryTestBase {

    @Autowired
    protected TranslationRepository translationRepository;

    @Override
    @Test
    @MetamacMock(TRANSLATIONS)
    public void testFindTranslationByCode() throws Exception {
        String code = StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_SEMESTER_PREFIX + ".S1";
        Translation translation = translationRepository.findTranslationByCode(code);

        assertEquals(code, translation.getCode());
        assertEquals("{yyyy} Primer semestre", translation.getTitle().getLocalisedLabel("es"));
        assertEquals("{yyyy} First semester", translation.getTitle().getLocalisedLabel("en"));
        assertEquals(null, translation.getTitle().getLocalisedLabel("it"));
    }
}
