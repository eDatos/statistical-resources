package org.siemac.metamac.statistical.resources.core.utils.mocks.configuration;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;

public class MockAnnotationRule implements TestRule {

    private static MockPersister mockPersister;

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                if (description.getAnnotation(MetamacMock.class) != null) {
                    loadNeededMocks(description.getAnnotation(MetamacMock.class));
                }
                base.evaluate();
            }
        };
    }

    private void loadNeededMocks(MetamacMock annotation) throws Exception {
        getMockPersister().persistMocks(annotation.value());
    }

    private MockPersister getMockPersister() {
        if (mockPersister == null) {
            mockPersister = ApplicationContextProvider.getApplicationContext().getBean(MockPersister.class);
        }
        return mockPersister;
    }
}