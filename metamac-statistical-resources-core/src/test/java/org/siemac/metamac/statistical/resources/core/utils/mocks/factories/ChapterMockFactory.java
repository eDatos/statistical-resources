package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.springframework.stereotype.Component;

@Component
public class ChapterMockFactory extends StatisticalResourcesMockFactory<Chapter> {

    public static final String CHAPTER_01_BASIC_NAME = "CHAPTER_01_BASIC";
    private static Chapter     CHAPTER_01_BASIC;

    public static final String CHAPTER_02_BASIC_NAME = "CHAPTER_02_BASIC";
    private static Chapter     CHAPTER_02_BASIC;

    public static final String CHAPTER_03_BASIC_NAME = "CHAPTER_03_BASIC";
    private static Chapter     CHAPTER_03_BASIC;

    protected static Chapter getChapter01Basic() {
        if (CHAPTER_01_BASIC == null) {
            CHAPTER_01_BASIC = createChapter();
        }
        return CHAPTER_01_BASIC;
    }

    protected static Chapter getChapter02Basic() {
        if (CHAPTER_02_BASIC == null) {
            CHAPTER_02_BASIC = createChapter();
        }
        return CHAPTER_02_BASIC;
    }

    protected static Chapter getChapter03Basic() {
        if (CHAPTER_03_BASIC == null) {
            CHAPTER_03_BASIC = createChapter();
        }
        return CHAPTER_03_BASIC;
    }

    private static Chapter createChapter() {
        return getStatisticalResourcesPersistedDoMocks().mockChapter();
    }
}
