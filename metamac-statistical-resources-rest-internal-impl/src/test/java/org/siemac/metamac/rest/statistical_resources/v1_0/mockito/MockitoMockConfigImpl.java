package org.siemac.metamac.rest.statistical_resources.v1_0.mockito;

public class MockitoMockConfigImpl implements MockitoMockConfig {

    boolean applyArgumentMatcher = false;
    boolean isExternalApi        = false;

    @Override
    public void resetToDefaultConfiguration() {
        applyArgumentMatcher = true;
        isExternalApi = false;
    }

    @Override
    public void setApplyArgumentMatcher(boolean applyArgumentMatcher) {
        this.applyArgumentMatcher = applyArgumentMatcher;
    }

    @Override
    public boolean isApplyArgumentMatcher() {
        return applyArgumentMatcher;
    }

}
