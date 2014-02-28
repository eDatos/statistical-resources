package org.siemac.metamac.rest.statistical_resources.v1_0.mockito;

public class MockitoMockConfigImpl implements MockitoMockConfig {

    boolean applyArgumentMatcher = false;

    public void resetToDefaultConfiguration() {
        applyArgumentMatcher = true;
    }

    public void setApplyArgumentMatcher(boolean applyArgumentMatcher) {
        this.applyArgumentMatcher = applyArgumentMatcher;
    }

    public boolean isApplyArgumentMatcher() {
        return applyArgumentMatcher;
    }

}
