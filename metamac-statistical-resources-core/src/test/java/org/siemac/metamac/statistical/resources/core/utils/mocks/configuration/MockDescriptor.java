package org.siemac.metamac.statistical.resources.core.utils.mocks.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class MockDescriptor {

    private Object       targetMock;
    private List<Object> dependencies;

    public MockDescriptor(Object mock) {
        this.targetMock = mock;
        this.dependencies = new ArrayList<Object>();
    }

    public MockDescriptor(Object mock, Object deps) {
        this.targetMock = mock;
        if (deps instanceof Collection) {
            this.dependencies = new ArrayList<Object>((Collection) deps);
        } else {
            this.dependencies = new ArrayList<Object>(Arrays.asList(deps));
        }
    }

    public MockDescriptor(Object mock, Object... deps) {
        this.targetMock = mock;
        this.dependencies = new ArrayList<Object>(Arrays.asList(deps));
    }

    public MockDescriptor(Object mock, List<Object> deps) {
        this.targetMock = mock;
        this.dependencies = deps;
    }

    public MockDescriptor(Object mock, MockDescriptor parentMock) {
        assert mock != null;
        this.targetMock = mock;
        this.dependencies = new ArrayList<Object>();
        dependencies.add(parentMock.targetMock);
        dependencies.addAll(parentMock.dependencies);
    }

    public Object getTargetMock() {
        return targetMock;
    }

    public List<Object> getDependencies() {
        return dependencies;
    }

    public List<Object> getAllMocks() {
        List<Object> mocks = new ArrayList<Object>();
        mocks.add(targetMock);
        mocks.addAll(dependencies);
        return mocks;
    }

}
