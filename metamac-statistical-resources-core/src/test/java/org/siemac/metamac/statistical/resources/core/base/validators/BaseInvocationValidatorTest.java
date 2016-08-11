package org.siemac.metamac.statistical.resources.core.base.validators;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

public class BaseInvocationValidatorTest extends StatisticalResourcesBaseTest {

    private static String SEMANTIC_IDENTIFIER     = "SEMANTIC-IDENTIFIER";
    private static String NONSEMANTIC_IDENTIFIER  = "1-NONSEMANTIC-IDENTIFIER-@";

    private static String METADATA_NAME_PARAMETER = ServiceExceptionParameters.DATASET_VERSION;
    private static String METADATA_NAME_ERROR     = ServiceExceptionParameters.DATASET_VERSION__CODE;

    // ----------------------------------------------------------------
    // DATASET
    // ----------------------------------------------------------------

    @Test
    public void checkDatasetSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(SEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.DATASET, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);

    }

    @Test
    public void checkDatasetNonSemanticIdentifier() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, METADATA_NAME_ERROR));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(NONSEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.DATASET, METADATA_NAME_PARAMETER, exceptionItems);

        ExceptionUtils.throwIfException(exceptionItems);
    }

    // ----------------------------------------------------------------
    // DATASET VERSION
    // ----------------------------------------------------------------

    @Test
    public void checkDatasetVersionSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(SEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.DATASET_VERSION, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);

    }

    @Test
    public void checkDatasetVersionNonSemanticIdentifier() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, METADATA_NAME_ERROR));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(NONSEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.DATASET_VERSION, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);
    }

    // ----------------------------------------------------------------
    // DATASOURCE
    // ----------------------------------------------------------------

    @Test
    public void checkDatasourceSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(SEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.DATASOURCE, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);

    }

    @Test
    public void checkDatasourceNonSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(NONSEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.DATASOURCE, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);
    }

    // ----------------------------------------------------------------
    // PUBLICATION
    // ----------------------------------------------------------------

    @Test
    public void checkPublicationSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(SEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.PUBLICATION, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);

    }

    @Test
    public void checkPublicationNonSemanticIdentifier() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, METADATA_NAME_ERROR));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(NONSEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.PUBLICATION, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);
    }

    // ----------------------------------------------------------------
    // PUBLCIATION VERSION
    // ----------------------------------------------------------------

    @Test
    public void checkPublicationVersionSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(SEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.PUBLICATION_VERSION, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);

    }

    @Test
    public void checkPublicationVersionNonSemanticIdentifier() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, METADATA_NAME_ERROR));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(NONSEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.PUBLICATION_VERSION, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);
    }

    // ----------------------------------------------------------------
    // CHAPTER
    // ----------------------------------------------------------------

    @Test
    public void checkChapterSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(SEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.CHAPTER, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);

    }

    @Test
    public void checkChapterNonSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(NONSEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.CHAPTER, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);
    }

    // ----------------------------------------------------------------
    // CUBE
    // ----------------------------------------------------------------

    @Test
    public void checkCubeSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(SEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.CUBE, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);

    }

    @Test
    public void checkCubeNonSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(NONSEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.CUBE, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);
    }

    // ----------------------------------------------------------------
    // QUERY
    // ----------------------------------------------------------------

    @Test
    public void checkQuerySemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(SEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.QUERY, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);

    }

    @Test
    public void checkQueryNonSemanticIdentifier() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, METADATA_NAME_ERROR));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(NONSEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.QUERY, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);
    }

    // ----------------------------------------------------------------
    // QUERY VERSION
    // ----------------------------------------------------------------

    @Test
    public void checkQueryVersionSemanticIdentifier() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(SEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.QUERY_VERSION, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);

    }

    @Test
    public void checkQueryVersionNonSemanticIdentifier() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, METADATA_NAME_ERROR));

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        BaseInvocationValidator.checkForSemanticIdentifier(NONSEMANTIC_IDENTIFIER, TypeRelatedResourceEnum.QUERY_VERSION, METADATA_NAME_PARAMETER, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);
    }
}