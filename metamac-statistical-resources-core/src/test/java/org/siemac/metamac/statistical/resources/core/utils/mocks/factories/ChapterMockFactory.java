package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockProvider;

@SuppressWarnings("unused")
@MockProvider
public class ChapterMockFactory extends StatisticalResourcesMockFactory<Chapter> {

    public static final String        CHAPTER_01_BASIC_NAME                           = "CHAPTER_01_BASIC";

    public static final String        CHAPTER_02_BASIC_NAME                           = "CHAPTER_02_BASIC";

    public static final String        CHAPTER_03_BASIC_NAME                           = "CHAPTER_03_BASIC";

    public static final String        CHAPTER_04_WITH_PARENT_NAME                     = "CHAPTER_04_WITH_PARENT";

    public static final String        CHAPTER_05_EMPTY_IN_PUBLICATION_VERSION_89_NAME = "CHAPTER_05_EMPTY_IN_PUBLICATION_VERSION_89";

    private static ChapterMockFactory instance                                        = null;

    private ChapterMockFactory() {
    }

    public static ChapterMockFactory getInstance() {
        if (instance == null) {
            instance = new ChapterMockFactory();
        }
        return instance;
    }

    private static Chapter getChapter01Basic() {
        return createChapter();
    }

    private static Chapter getChapter02Basic() {
        return createChapter();
    }

    private static Chapter getChapter03Basic() {
        return createChapter();
    }

    private static Chapter getChapter04WithParent() {
        return createChapterWithParent();
    }

    private static Chapter createChapter() {
        return getStatisticalResourcesPersistedDoMocks().mockChapter();
    }

    private static Chapter createChapterWithParent() {
        return getStatisticalResourcesPersistedDoMocks().mockChapterWithParent();
    }
}
