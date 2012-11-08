package org.siemac.metamac.statistical.resources.core.mocks;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.domain.AbstractDomainObject;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;

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
        // MockPersisterBase.mockObjects(annotation.value());
        // //mockQuery();
        // DBUnitOracleFacade mockGenerator = ApplicationContextProvider.getApplicationContext().getBean(DBUnitOracleFacade.class);
        // mockGenerator.setUpDatabase(new File(MockAnnotationRule.class.getResource("/dbunit/StatisticalResourcesServiceTest.xml").getFile()));
    }

    private void mockQuery() {
        NameableStatisticalResource resource = new NameableStatisticalResource();
        System.out.println(resource.getClass().getFields());
        for (Field field : getAllFields(new ArrayList<Field>(), resource.getClass())) {
            if (AbstractDomainObject.class.isAssignableFrom(field.getType())) {
                System.out.println("Campo " + field.getName() + " debe serializarse");
            }

        }
    }

    private MockPersister getMockPersister() {
        if (mockPersister == null) {
            mockPersister = ApplicationContextProvider.getApplicationContext().getBean(MockPersister.class);
        }
        return mockPersister;
    }

    private List<Field> getAllFields(List<Field> fields, Class<?> type) {
        for (Field field : type.getDeclaredFields()) {
            fields.add(field);
        }

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    private QueryMockFactory getQueryMockFactory() {
        return ApplicationContextProvider.getApplicationContext().getBean(QueryMockFactory.class);
    }

}